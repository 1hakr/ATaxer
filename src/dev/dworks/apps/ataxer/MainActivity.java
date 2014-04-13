package dev.dworks.apps.ataxer;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.crashlytics.android.Crashlytics;
import dev.dworks.apps.ataxer.misc.PinViewHelper;
import dev.dworks.apps.ataxer.misc.PinViewHelper.PINDialogFragment;
import dev.dworks.apps.ataxer.misc.Utils;
import dev.dworks.libs.actionbarplus.dialog.SimpleDialogFragment;
import dev.dworks.libs.actionbarplus.misc.AppRate;
import dev.dworks.libs.actionbartoggle.ActionBarToggle;

public class MainActivity extends ActionBarActivity {
	private Fragment fragment;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private CharSequence mDrawerTitle;
	private ActionBarToggle mDrawerToggle;
	private View mNavigationLayout;
	private int mPosition;
	private int[] mSectionImages;
	private String[] mSectionTitles;
	private CharSequence mTitle;
	private boolean authenticated;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
Crashlytics.start(this);
		/*		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
		.detectAll()
		.penaltyFlashScreen()
		.penaltyLog()
		.build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
		.detectAll()
		.penaltyDeath()
		.penaltyLog()
		.build());*/
		setContentView(R.layout.activity_main);
		if(null != savedInstanceState){
			authenticated = savedInstanceState.getBoolean("authenticated");
		}
		initProtection();
		initControls(savedInstanceState);
	}

	private void initProtection() {

		if(authenticated || !Utils.isPinEnabled(this)){
			return;
		}
        final Dialog d = new Dialog(this, R.style.Theme_Ataxer_DailogPIN);
        d.setContentView(new PinViewHelper((LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE), null, null) {
            public void onEnter(String password) {
                super.onEnter(password);
                if (Utils.checkPin(MainActivity.this, password)) {
                	authenticated = true;
                	d.dismiss();
                }
                else {
                    Toast.makeText(MainActivity.this, getString(R.string.incorrect_pin), Toast.LENGTH_SHORT).show();
                }
            };
            
            public void onCancel() {
                super.onCancel();
                finish();
                d.dismiss();
            };
        }.getView(), new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        
        PINDialogFragment pinFragment = new PINDialogFragment();
        pinFragment.setDialog(d);
        pinFragment.show(getSupportFragmentManager(), "PIN Dialog");
	}

	private void initControls(Bundle savedInstanceState) {
		mDrawerTitle = getTitle();
		mTitle = getTitle();
		mSectionTitles = getResources().getStringArray(R.array.section_array);
		mSectionImages = getResources().getIntArray(R.array.section_images_array);
		mDrawerLayout = ((DrawerLayout) findViewById(R.id.drawer_layout));
		mNavigationLayout = findViewById(R.id.navigation_frame);
		mDrawerList = ((ListView) findViewById(R.id.left_drawer));
		
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		mDrawerList.setAdapter(new NavigationArrayAdapter(this, R.layout.item_list, mSectionTitles));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		
		//getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		//getSupportActionBar().setHomeButtonEnabled(true);
		mDrawerToggle = new ActionBarToggle(this, mDrawerLayout, R.drawable.ic_drawer_light, R.string.drawer_open, R.string.drawer_close) {
			
			@Override
			public void closeView() {
				super.closeView();
				getSupportActionBar().setTitle(mTitle);
				if (fragment != null)
					fragment.setHasOptionsMenu(true);
				supportInvalidateOptionsMenu();
			}
			@Override
			public void openView() {
				super.openView();
				getSupportActionBar().setTitle(mDrawerTitle);
				if (fragment != null)
					fragment.setHasOptionsMenu(false);
				supportInvalidateOptionsMenu();
			}
		};
		
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerToggle.setDrawerIndicatorEnabled(false);
		if (savedInstanceState != null){
			mPosition = savedInstanceState.getInt("position", 0);
		}
		selectItem(mPosition);
	}

	@Override
	public void onConfigurationChanged(Configuration configuration) {
		super.onConfigurationChanged(configuration);
		//mDrawerToggle.onConfigurationChanged(configuration);
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		//mDrawerToggle.syncState();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
        AppRate.with(this)
        .text("Like ATaxer? Rate It!")
        .listener(new AppRate.OnShowListener() {
            @Override
            public void onRateAppShowing() {
                // View is shown
            }

            @Override
            public void onRateAppDismissed() {
                // User has dismissed it
            }

            @Override
            public void onRateAppClicked() {
    			Intent intentMarket = new Intent("android.intent.action.VIEW");
    			intentMarket.setData(Uri.parse("market://details?id=dev.dworks.apps.ataxer"));
    			startActivity(intentMarket);
            }
        }).checkAndShow();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("position", mPosition);
		outState.putBoolean("authenticated", authenticated);
	}
	
	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		switch (item.getItemId()) {
		case R.id.action_about:
			startActivity(new Intent(getApplicationContext(), AboutActivity.class));
			break;
		case R.id.action_tax:
			((SimpleDialogFragment.SimpleDialogBuilder) SimpleDialogFragment.createBuilder(this, getSupportFragmentManager())
					.setTitle("How is Tax Calculated?")
					.setMessage(R.string.tax_calculation_description)
					.hideDefaultButton(true)
					.setRequestCode(2131296492)).show();
			break;
		case R.id.action_setting:
			startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
			break;
		}
		return super.onOptionsItemSelected(item);
	}

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

	private void selectItem(int selection) {
		mPosition = selection;
		String clazzName = "";
		switch (selection) {
		case 0:
			clazzName = TaxListFragment.class.getName();
			break;
		case 1:
			clazzName = TaxOverviewFragment.class.getName();
			break;
		}
		fragment = Fragment.instantiate(this, clazzName, new Bundle());
		getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
		mDrawerList.setItemChecked(selection, true);
		setTitle(mSectionTitles[selection]);
		mDrawerLayout.closeDrawer(mNavigationLayout);
	}
	
	public class NavigationArrayAdapter extends ArrayAdapter<String> {

		public NavigationArrayAdapter(Context context, int textViewResourceId, String[] objects) {
			super(context, textViewResourceId, objects);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = super.getView(position, convertView, parent);
			((TextView)view.findViewById(16908308)).setCompoundDrawablesWithIntrinsicBounds(mSectionImages[position], 0, 0, 0);
			return view;
		}
	}
}