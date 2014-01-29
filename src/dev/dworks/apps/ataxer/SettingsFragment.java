package dev.dworks.apps.ataxer;

import dev.dworks.apps.ataxer.misc.PinViewHelper;
import dev.dworks.apps.ataxer.misc.Utils;
import dev.dworks.libs.actionbarplus.app.PreferenceFragment;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

public class SettingsFragment extends PreferenceFragment {

	private Preference pin_set_preference;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        addPreferencesFromResource(R.xml.pref_general);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		pin_set_preference = findPreference("pin_set");
		pin_set_preference.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			
			@Override
			public boolean onPreferenceClick(Preference preference) {
				checkPin();
				return false;
			}
		});
		pin_set_preference.setSummary(Utils.isPinProtected(getActionBarActivity()) ? R.string.pin_set : R.string.pin_disabled);
	}
    void confirmPin(final String pin) {
    	final Dialog d = new Dialog(getActionBarActivity(), R.style.Theme_Ataxer_DailogPIN);	
    	d.getWindow().setWindowAnimations(R.style.DialogEnterNoAnimation);
    	PinViewHelper pinViewHelper = new PinViewHelper((LayoutInflater)getActionBarActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE), null, null) {
            public void onEnter(String password) {
                super.onEnter(password);
                if (pin.equals(password)) {
                	Utils.setPin(getActionBarActivity(), password);
                	pin_set_preference.setSummary(Utils.isPinProtected(getActionBarActivity()) ? R.string.pin_set : R.string.pin_disabled);
                    if (password != null && password.length() > 0){
                        Toast.makeText(getActionBarActivity(), getString(R.string.pin_set), Toast.LENGTH_SHORT).show();
                        setInstruction(R.string.pin_set);
                    }
                    d.dismiss();
                    return;
                }
                Toast.makeText(getActionBarActivity(), getString(R.string.pin_mismatch), Toast.LENGTH_SHORT).show();
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
    	final Dialog d = new Dialog(getActionBarActivity(), R.style.Theme_Ataxer_DailogPIN);
    	d.getWindow().setWindowAnimations(R.style.DialogExitNoAnimation);
        View view = new PinViewHelper((LayoutInflater)getActionBarActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE), null, null) {
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
        if (Utils.isPinProtected(getActionBarActivity())) {
            final Dialog d = new Dialog(getActionBarActivity(), R.style.Theme_Ataxer_DailogPIN);
            View view = new PinViewHelper((LayoutInflater)getActionBarActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE), null, null) {
                public void onEnter(String password) {
                    super.onEnter(password);
                    if (Utils.checkPin(getActionBarActivity(), password)) {
                        super.onEnter(password);
                        Utils.setPin(getActionBarActivity(), "");
                        pin_set_preference.setSummary(R.string.pin_disabled);
                        Toast.makeText(getActionBarActivity(), getString(R.string.pin_disabled), Toast.LENGTH_SHORT).show();
                        setInstruction(R.string.pin_disabled);
                        d.dismiss();
                        return;
                    }
                    Toast.makeText(getActionBarActivity(), getString(R.string.incorrect_pin), Toast.LENGTH_SHORT).show();
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