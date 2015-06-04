package com.example.tutbytest.service;

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
	
	private static final String LAST_ERASE_TIME = "LAST.ERASE.TIME";
	private static final String ERASE_DELAY = "ERASE.DELAY";
	
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
	
	private class EraseTimer extends Timer {
		
		private TimerTask task;
		
		public void startEraseWithDelay(int delay) {
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
		eraseTimer.startEraseWithDelay(eraseDelay);
		eraseOnStart();
		instance = this;
		synchronized (WAIT) {
			WAIT.notifyAll();
		}
	}
	
	public void setEraseDelay(int delay) {
		eraseDelay = delay;
		//TODO: start timer to erase here
	}
	
	private void erase() {
		//TODO: erase here
	}
	
	private void eraseOnStart() {
		//TODO: erase, if need
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
