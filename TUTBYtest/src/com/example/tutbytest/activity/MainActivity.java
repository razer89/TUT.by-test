package com.example.tutbytest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.tutbytest.R;
import com.example.tutbytest.fragment.MainFragment;
import com.example.tutbytest.fragment.SettingsFragment;
import com.example.tutbytest.service.BackgroundService;
import com.example.tutbytest.utils.ThemeUtil;

public class MainActivity extends AppCompatActivity {

	private DrawerLayout drawerLayout;
	private ActionBarDrawerToggle toggler;
	private ListView drawerList;
	private ArrayAdapter<String> drawerAdapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Initialize NavigationDrawer and Toolbar
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    	setSupportActionBar(toolbar);
    	toggler = new ActionBarDrawerToggle(this, drawerLayout,
    			R.string.drawer_open, R.string.drawer_close);
        drawerLayout.setDrawerListener(toggler);
    	drawerLayout.post(new Runnable() {
			
			@Override
			public void run() {
				toggler.syncState();
			}
		});
        drawerList = (ListView) drawerLayout.findViewById(R.id.left_drawer);
        drawerAdapter = new ArrayAdapter<String>(this, R.layout.drawer_item,
        		getResources().getStringArray(R.array.drawer_items));
        drawerList.setAdapter(drawerAdapter);
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        
        // Set current theme and current fragment
    	ThemeUtil.setTheme(this);
        if (savedInstanceState == null) {
            selectItem(0);
            BackgroundService.get(this);	// Start service
        } else {
        	int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
        	if (backStackCount > 0) {
        		setDrawerIndicatorEnabled(false);
        	}
        }
    }
    
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        Fragment fragment = null;
        
        switch (position) {
		case 0:
			fragment = new MainFragment();
			break;
		case 1:
			fragment = new SettingsFragment();
			break;
		}

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                       .replace(R.id.content_frame, fragment)
                       .commit();

        drawerList.setItemChecked(position, true);
        getSupportActionBar().setTitle(position == 0 ? getString(R.string.app_name) : drawerAdapter.getItem(position));
        drawerLayout.closeDrawer(drawerList);
    }
    
    public void setDrawerBackgrondColor(int color) {
    	drawerList.setBackgroundColor(color);
    }
    
    public void setDrawerIndicatorEnabled(boolean isEnabled) {
    	toggler.setDrawerIndicatorEnabled(isEnabled);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	if (toggler.onOptionsItemSelected(item)) {
    		return true;
    	}
    	return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void onBackPressed() {
    	super.onBackPressed();
    	setDrawerIndicatorEnabled(true);
    }
}