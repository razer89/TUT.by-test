package com.example.tutbytest.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.example.tutbytest.R;
import com.example.tutbytest.activity.MainActivity;

public class SettingsFragment extends BaseFragment {

	private TextView minutes;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.settings_fragment, null);
		minutes = (TextView) view.findViewById(R.id.frequency);
		SeekBar seekBar = (SeekBar) view.findViewById(R.id.seekBar1);
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				if (fromUser) {
					minutes.setText("" + (progress + 1));
				}
			}
		});
		Button theme = (Button) view.findViewById(R.id.theme);
		theme.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((MainActivity)getActivity()).changeFragment(new ThemeFragment(), true);
			}
		});
		return view;
	}

	@Override
	public String getTitle() {
		return getString(R.string.settings);
	}
}
