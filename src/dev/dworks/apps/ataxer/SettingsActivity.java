package dev.dworks.apps.ataxer;

import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.actionbarsherlock.view.MenuItem;

import dev.dworks.apps.ataxer.misc.PinViewHelper;
import dev.dworks.apps.ataxer.misc.Utils;

public class SettingsActivity extends SherlockPreferenceActivity {
	private static final boolean ALWAYS_SIMPLE_PREFS = false;
	private Preference pin_set_preference;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		setupSimplePreferencesScreen();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@SuppressWarnings("deprecation")
	private void setupSimplePreferencesScreen() {
		if (!isSimplePreferences(this)) {
			return;
		}
		// Add 'general' preferences.
		addPreferencesFromResource(R.xml.pref_general);
		pin_set_preference = findPreference("pin_set");
		pin_set_preference.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			
			@Override
			public boolean onPreferenceClick(Preference preference) {
				checkPin();
				return false;
			}
		});
		pin_set_preference.setSummary(Utils.isPinProtected(SettingsActivity.this) ? R.string.pin_set : R.string.pin_disabled);
	}

	/** {@inheritDoc} */
	@Override
	public boolean onIsMultiPane() {
		return isXLargeTablet(this) && !isSimplePreferences(this);
	}

	private static boolean isXLargeTablet(Context context) {
		return ((context.getResources().getConfiguration().screenLayout & 
			    Configuration.SCREENLAYOUT_SIZE_MASK) > Configuration.SCREENLAYOUT_SIZE_LARGE);
	}

	private static boolean isSimplePreferences(Context context) {
		return ALWAYS_SIMPLE_PREFS || Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB || !isXLargeTablet(context);
	}

	/** {@inheritDoc} */
	@Override
	public void onBuildHeaders(List<Header> target) {
		if (!isSimplePreferences(this)) {
			loadHeadersFromResource(R.xml.pref_headers, target);
		}
	}
	
	/**
	 * This fragment shows general preferences only. It is used when the
	 * activity is showing a two-pane settings UI.
	 */
	public static class GeneralPreferenceFragment extends PreferenceFragment {
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.pref_general);
		}
	}
	
    void confirmPin(final String pin) {
    	final Dialog d = new Dialog(this, R.style.Theme_Ataxer_DailogPIN);	
    	d.getWindow().setWindowAnimations(R.style.DialogEnterNoAnimation);
    	PinViewHelper pinViewHelper = new PinViewHelper((LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE), null, null) {
            public void onEnter(String password) {
                super.onEnter(password);
                if (pin.equals(password)) {
                	Utils.setPin(SettingsActivity.this, password);
                	pin_set_preference.setSummary(Utils.isPinProtected(SettingsActivity.this) ? R.string.pin_set : R.string.pin_disabled);
                    if (password != null && password.length() > 0){
                        Toast.makeText(SettingsActivity.this, getString(R.string.pin_set), Toast.LENGTH_SHORT).show();
                        setInstruction(R.string.pin_set);
                    }
                    d.dismiss();
                    return;
                }
                Toast.makeText(SettingsActivity.this, getString(R.string.pin_mismatch), Toast.LENGTH_SHORT).show();
                setInstruction(R.string.pin_mismatch);
            };
            
            public void onCancel() {
                super.onCancel();
                d.dismiss();
            };
        };
        View view = pinViewHelper.getView();
        view.findViewById(R.id.logo).setVisibility(View.GONE);
        pinViewHelper.setInstruction(R.string.confirm_pin);
		d.setContentView(view);
        d.show();
    }
    
    void setPin() {
    	final Dialog d = new Dialog(this, R.style.Theme_Ataxer_DailogPIN);
    	d.getWindow().setWindowAnimations(R.style.DialogExitNoAnimation);
        View view = new PinViewHelper((LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE), null, null) {
            public void onEnter(String password) {
                super.onEnter(password);
                confirmPin(password);
                d.dismiss();
            };
            
            public void onCancel() {
                super.onCancel();
                d.dismiss();
            };
        }.getView();
        view.findViewById(R.id.logo).setVisibility(View.GONE);
		d.setContentView(view);
        d.show();
    }

    void checkPin() {
        if (Utils.isPinProtected(SettingsActivity.this)) {
            final Dialog d = new Dialog(this, R.style.Theme_Ataxer_DailogPIN);
            View view = new PinViewHelper((LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE), null, null) {
                public void onEnter(String password) {
                    super.onEnter(password);
                    if (Utils.checkPin(SettingsActivity.this, password)) {
                        super.onEnter(password);
                        Utils.setPin(SettingsActivity.this, "");
                        pin_set_preference.setSummary(R.string.pin_disabled);
                        Toast.makeText(SettingsActivity.this, getString(R.string.pin_disabled), Toast.LENGTH_SHORT).show();
                        setInstruction(R.string.pin_disabled);
                        d.dismiss();
                        return;
                    }
                    Toast.makeText(SettingsActivity.this, getString(R.string.incorrect_pin), Toast.LENGTH_SHORT).show();
                    setInstruction(R.string.incorrect_pin);
                };
                
                public void onCancel() {
                    super.onCancel();
                    d.dismiss();
                };
            }.getView();
            view.findViewById(R.id.logo).setVisibility(View.GONE);
			d.setContentView(view);
			d.show();
        }
        else {
            setPin();
        }
    }
}