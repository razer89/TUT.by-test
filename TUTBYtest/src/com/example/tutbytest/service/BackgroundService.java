package com.example.tutbytest.service;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;

public class BackgroundService extends Service {

	private static BackgroundService instance;
	private static final Object WAIT = new Object();
	private int eraseDelay;
	private long lastEraseTime;
	private SharedPreferences prefs;
	private EraseTimer eraseTimer;
	private ArrayList<BackgroundServiceListener> listeners = new ArrayList<BackgroundServiceListener>();
	
	private static final String LAST_ERASE_TIME = "LAST.ERASE.TIME";
	private static final String ERASE_DELAY = "ERASE.DELAY";
	
	/**
	 * If it's your first call, you must do this in another thread!!!
	 * @param context - your activity context
	 */
	
	public static BackgroundService get(Context context) {
		if (instance == null) {
			context.startService(new Intent(context, BackgroundService.class));
			synchronized (WAIT) {
				try {
					WAIT.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		return instance;
	}
	
	public interface BackgroundServiceListener {
		public void onUpdate (ArrayList<Long> dateArray);
	}
	
	private class EraseTimer extends Timer {
		
		private TimerTask task;
		
		public void startEraseWithDelay(int delay) {
			android.util.Log.d("logd", "startEraseWithDelay()");
			if (task != null) {
				task.cancel();
			}
			task = new TimerTask() {
				
				@Override
				public void run() {
					erase();
				}
			};
			scheduleAtFixedRate(task, delay, eraseDelay);
		}
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		eraseDelay = prefs.getInt(ERASE_DELAY, 300000);	// 5 minutes
		lastEraseTime = prefs.getLong(LAST_ERASE_TIME, -1);
		eraseTimer = new EraseTimer();
		eraseIfNeed();
		eraseTimer.startEraseWithDelay(eraseDelay);
		DBHelper.getInstance(getApplicationContext()); // Create DB
		instance = this;
		synchronized (WAIT) {
			WAIT.notifyAll();
		}
	}
	
	public void setEraseDelay(int delay) {
		eraseDelay = delay;
		prefs.edit().putInt(ERASE_DELAY, delay).commit();
		eraseIfNeed();
		eraseTimer.startEraseWithDelay(delay);
	}
	
	public int getEraseDelay() {
		return eraseDelay;
	}
	
	private void setLastEraseTime(long time) {
		lastEraseTime = time;
		prefs.edit().putLong(LAST_ERASE_TIME, time).commit();
	}
	
	private void erase() {
		setLastEraseTime(System.currentTimeMillis());
		DBHelper.getInstance(getApplicationContext()).eraseDB();
		for (BackgroundServiceListener listener : listeners) {
			listener.onUpdate(DBHelper.getInstance().getAllDates());
		}
	}
	
	private void eraseIfNeed() {
		if (lastEraseTime == -1) {
			setLastEraseTime(System.currentTimeMillis());
			return;
		}
		if (System.currentTimeMillis() > lastEraseTime + eraseDelay) {
			erase();
		}
	}

	public void addListener(BackgroundServiceListener listener) {
		listeners.add(listener);
	}
	
	public void addDate(long date) {
		DBHelper.getInstance().insert(date);
		for (BackgroundServiceListener listener : listeners) {
			listener.onUpdate(DBHelper.getInstance().getAllDates());
		}
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
