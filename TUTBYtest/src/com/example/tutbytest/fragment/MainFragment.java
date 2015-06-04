package com.example.tutbytest.fragment;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tutbytest.R;
import com.example.tutbytest.activity.MainActivity;
import com.example.tutbytest.service.BackgroundService;
import com.example.tutbytest.service.BackgroundService.BackgroundServiceListener;
import com.example.tutbytest.utils.Utils;

public class MainFragment extends BaseFragment {

	private ListView listView;
	private TextView lastClear;
	private LastEraseUpdater eraseUpdater;

	private class LastEraseUpdater extends Timer {
		
		private TimerTask task;
		
		public void startUpdate() {
			if (task != null) {
				task.cancel();
			}
			task = new TimerTask() {
				
				@Override
				public void run() {
					if (getActivity() == null) {
						this.cancel();
						return;
					}
					getActivity().runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							lastClear.setText(BackgroundService.get(getActivity()).getNextEraseInfo());							
						}
					});
				}
			};
			scheduleAtFixedRate(task, 0, 1000);
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.main_fragment, null);
		listView = (ListView) view.findViewById(R.id.time_list);
		lastClear = (TextView) view.findViewById(R.id.last_clear);
		Button openScreen = (Button) view.findViewById(R.id.open_screen);
		openScreen.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((MainActivity)getActivity()).changeFragment(new ScreenFragment(), true);
			}
		});
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				BackgroundService.get(getActivity()).addListener(new BackgroundServiceListener() {
					
					@Override
					public void onUpdate(ArrayList<Long> dateArray) {
						setDataToList(dateArray);
					}
				});
				setDataToList(BackgroundService.get(getActivity()).getAllDates());
				eraseUpdater = new LastEraseUpdater();
				eraseUpdater.startUpdate();
			}
		}).start();
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String item = (String) listView.getAdapter().getItem(position);
				Bundle args = new Bundle();
				args.putString(ScreenFragment.class.getSimpleName(), item);
				BaseFragment fragment = new ScreenFragment();
				fragment.setArguments(args);
				((MainActivity)getActivity()).changeFragment(fragment, true);
			}
		});
		return view;
	}
	
	private void setDataToList(final ArrayList<Long> data) {
		if (getActivity() == null) return;
		final ArrayList<String> stringList = new ArrayList<String>();
		for (Long current : data) {
			stringList.add(Utils.convertDate(current));
		}
		getActivity().runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				if (listView == null) return;
				listView.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.main_item, stringList));				
			}
		});
	}

	@Override
	public String getTitle() {
		return getString(R.string.app_name);
	}
}
