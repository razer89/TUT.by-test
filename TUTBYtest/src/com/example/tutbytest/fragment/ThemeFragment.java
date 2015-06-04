package com.example.tutbytest.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.tutbytest.R;
import com.example.tutbytest.utils.ThemeUtil;

public class ThemeFragment extends BaseFragment {

	private RadioGroup themesGroup;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View view = inflater.inflate(R.layout.theme_fragment, null);
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
				getActivity().onBackPressed();
			}
		});
		Button cancel = (Button) view.findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getActivity().onBackPressed();
			}
		});
		return view;
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
		return getString(R.string.theme);
	}
}
