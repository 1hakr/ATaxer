package dev.dworks.apps.ataxer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import dev.dworks.apps.ataxer.misc.Utils;
import dev.dworks.apps.ataxer.wizard.TaxCalculationActivity;
import dev.dworks.libs.awizard.model.PageFragmentCallbacks;
import dev.dworks.libs.awizard.model.page.Page;
import dev.dworks.libs.actionbarplus.dialog.BaseDialogFragment.SimpleDialogListener;
import dev.dworks.libs.actionbarplus.dialog.SimpleDialogFragment;

public class TaxViewActivity extends ActionBarActivity implements PageFragmentCallbacks, SimpleDialogListener, ActionBar.TabListener {
	private Bundle args;
	private TabsPagerAdapter mTabsAdapter;
	private ViewPager mViewPager;
	private Uri uri;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_taxview);
		uri = getIntent().getData();
		args = new Bundle();
		args.putParcelable(Utils.DATA, uri);
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		//actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		mViewPager = ((ViewPager) findViewById(R.id.pager));
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			public void onPageSelected(int paramAnonymousInt) {
				actionBar.setSelectedNavigationItem(paramAnonymousInt);
			}
		});
		mTabsAdapter = new TabsPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mTabsAdapter);
		for (int i = 0;i < mTabsAdapter.getCount(); i++) {
			if (savedInstanceState != null){
				//FIXME
				//mViewPager.setCurrentItem(savedInstanceState.getInt("tab"));
			}
			//actionBar.addTab(actionBar.newTab().setText(mTabsAdapter.getPageTitle(i)).setTabListener(this));
		}
	}

	public boolean onCreateOptionsMenu(Menu paramMenu) {
		getMenuInflater().inflate(R.menu.tax_view, paramMenu);
		return super.onCreateOptionsMenu(paramMenu);
	}

	public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
		switch (paramMenuItem.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		case R.id.action_edit:
			Intent localIntent = new Intent(this, TaxCalculationActivity.class);
			localIntent.setData(uri);
			startActivityForResult(localIntent, 0);
			break;
		case R.id.action_send:
			break;
		case R.id.action_delete:
			((SimpleDialogFragment.SimpleDialogBuilder) SimpleDialogFragment.createBuilder(this, getSupportFragmentManager())
					.setMessage("Delete Tax Calculations?")
					.setPositiveButtonText("Delete")
					.setNegativeButtonText("Cancel")
					.setRequestCode(R.id.action_delete)).show();
			break;
		}
		return super.onOptionsItemSelected(paramMenuItem);
	}

	public Page onGetPage(String paramString) {
		return null;
	}

	public void onNegativeButtonClicked(int paramInt) {
	}
	
	public void onPositiveButtonClicked(int paramInt) {
		switch (paramInt) {
		case R.id.action_delete:
			if (getContentResolver().delete(uri, null, null) == 1)
				finish();
			break;
		}
	}

	protected void onSaveInstanceState(Bundle paramBundle) {
		super.onSaveInstanceState(paramBundle);
		paramBundle.putInt("tab", mViewPager.getCurrentItem());
	}

	public void onTabReselected(ActionBar.Tab paramTab, FragmentTransaction paramFragmentTransaction) {
	}

	public void onTabSelected(ActionBar.Tab paramTab, FragmentTransaction paramFragmentTransaction) {
		mViewPager.setCurrentItem(paramTab.getPosition());
	}

	public void onTabUnselected(ActionBar.Tab paramTab, FragmentTransaction paramFragmentTransaction) {
	}

	private class TabsPagerAdapter extends FragmentPagerAdapter {
		public TabsPagerAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		public int getCount() {
			return 1;
		}

		public Fragment getItem(int paramInt) {
			Fragment fragment = null;
			switch (paramInt) {
			case 0:
				fragment = new TaxViewFragment();
				break;
			case 1:
				fragment = new TaxDocumentFragment();
				break;
			case 2:
				fragment = new TaxSuggestionFragment();
				break;
			}
			fragment.setArguments(args);
			return fragment;
		}

		public CharSequence getPageTitle(int paramInt) {
			String title = "";
			switch (paramInt) {
			case 0:
				title = "Details";
				break;
			case 1:
				title = "Documents";
				break;
			case 2:
				title = "Suggestions";
				break;
			}
			return title;
		}
	}
}