package com.example.tutbytest.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tutbytest.R;
import com.example.tutbytest.activity.MainActivity;

public class MainFragment extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.main_fragment, null);
		Button openScreen = (Button) view.findViewById(R.id.open_screen);
		openScreen.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((MainActivity)getActivity()).changeFragment(new ScreenFragment(), true);
			}
		});
		return view;
	}

	@Override
	public String getTitle() {
		return getString(R.string.app_name);
	}
}
