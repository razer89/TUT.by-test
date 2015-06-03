package com.example.tutbytest.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tutbytest.R;
import com.example.tutbytest.utils.ThemeUtil;

public class SettingsFragment extends Fragment {

	private TextView minutes;
	private RadioGroup themesGroup;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.settings_fragment, null);
		minutes = (TextView) view.findViewById(R.id.frequency);
		themesGroup = (RadioGroup) view.findViewById(R.id.radioGroup1);
		int themeID = 0;
		switch (ThemeUtil.getCurrentTheme(getActivity())) {
		case ThemeUtil.THEME_BLUE_GRAY:
			themeID = R.id.radio0;
			break;
		case ThemeUtil.DEEP_ORANGE:
			themeID = R.id.radio1;
			break;
		}
		themesGroup.check(themeID);
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
		Button ok = (Button) view.findViewById(R.id.ok);
		ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int checkedId = themesGroup.getCheckedRadioButtonId();
				switch (checkedId) {
				case R.id.radio0:
					ThemeUtil.setTheme(getActivity(), ThemeUtil.THEME_BLUE_GRAY);
					break;

				case R.id.radio1:
					ThemeUtil.setTheme(getActivity(), ThemeUtil.DEEP_ORANGE);
					break;
				}
				Toast.makeText(getActivity(), getActivity().getString(R.string.theme_success),
						Toast.LENGTH_SHORT).show();
			}
		});
		Button cancel = (Button) view.findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), "What must do this button? O_o", Toast.LENGTH_SHORT).show();
			}
		});
		return view;
	}
}
