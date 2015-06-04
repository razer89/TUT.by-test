package com.example.tutbytest.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tutbytest.R;
import com.example.tutbytest.activity.MainActivity;
import com.example.tutbytest.service.BackgroundService;
import com.example.tutbytest.utils.Utils;

public class ScreenFragment extends BaseFragment {

	private final String DATE_KEY = "DATE_KEY";
	private long date;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		View view = inflater.inflate(R.layout.screen_fragment, null);
		TextView currentDate = (TextView) view.findViewById(R.id.current_date);
		Bundle args = getArguments();
		if (args == null) {
			if (savedInstanceState == null) {
				date = System.currentTimeMillis();
				BackgroundService.get(getActivity()).addDate(date);
			} else {
				date = savedInstanceState.getLong(DATE_KEY);
			}
			currentDate.setText(Utils.convertDate(date));
		} else {
			currentDate.setText(args.getString(ScreenFragment.class.getSimpleName()));
		}
		return view;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putLong(DATE_KEY, date);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			getActivity().onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public String getTitle() {
		return getString(R.string.screen);
	}
}
