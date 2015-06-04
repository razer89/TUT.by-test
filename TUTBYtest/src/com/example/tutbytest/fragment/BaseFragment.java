package com.example.tutbytest.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tutbytest.activity.MainActivity;


public abstract class BaseFragment extends Fragment {

	protected abstract String getTitle();
	
	public String getBackStackTag() {
		return this.getClass().getSimpleName();
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		((MainActivity)getActivity()).getSupportActionBar().setTitle(getTitle());
		super.onActivityCreated(savedInstanceState);
	}
}
