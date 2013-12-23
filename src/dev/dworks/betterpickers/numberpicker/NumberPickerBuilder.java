package dev.dworks.betterpickers.numberpicker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

/**
 * User: derek Date: 5/2/13 Time: 7:55 PM
 */
public class NumberPickerBuilder {

    private FragmentManager manager; // Required
    private Integer styleResId; // Required
    private Fragment targetFragment;
    private Integer minNumber;
    private Integer maxNumber;
    private String setNumber;
    private Integer plusMinusVisibility;
    private Integer decimalVisibility;
    private Integer maxVisibility;
    private String labelText;
    private int mReference;

    public NumberPickerBuilder setFragmentManager(FragmentManager manager) {
        this.manager = manager;
        return this;
    }

    public NumberPickerBuilder setStyleResId(int styleResId) {
        this.styleResId = styleResId;
        return this;
    }

    public NumberPickerBuilder setTargetFragment(Fragment targetFragment) {
        this.targetFragment = targetFragment;
        return this;
    }

    public NumberPickerBuilder setReference(int reference) {
        this.mReference = reference;
        return this;
    }

    public NumberPickerBuilder setMinNumber(Integer minNumber) {
        this.minNumber = minNumber;
        return this;
    }

    public NumberPickerBuilder setMaxNumber(Integer maxNumber) {
        this.maxNumber = maxNumber;
        return this;
    }
    
    public NumberPickerBuilder setNumber(String number) {
    	
        this.setNumber = number;
        return this;
    }

    public NumberPickerBuilder setPlusMinusVisibility(int plusMinusVisibility) {
        this.plusMinusVisibility = plusMinusVisibility;
        return this;
    }

    public NumberPickerBuilder setDecimalVisibility(int decimalVisibility) {
        this.decimalVisibility = decimalVisibility;
        return this;
    }
    
    public NumberPickerBuilder setMaxVisibility(int maxVisibility) {
        this.maxVisibility = maxVisibility;
        return this;
    }

    public NumberPickerBuilder setLabelText(String labelText) {
        this.labelText = labelText;
        return this;
    }

    public void show() {
        if (manager == null || styleResId == null) {
            Log.e("NumberPickerBuilder", "setFragmentManager() and setStyleResId() must be called.");
            return;
        }
        final FragmentTransaction ft = manager.beginTransaction();
        final Fragment prev = manager.findFragmentByTag("number_dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        final NumberPickerDialogFragment fragment = NumberPickerDialogFragment
                .newInstance(mReference, styleResId, minNumber, maxNumber, plusMinusVisibility, decimalVisibility, maxVisibility, labelText, setNumber);
        if (targetFragment != null) {
            fragment.setTargetFragment(targetFragment, 0);
        }
        fragment.show(ft, "number_dialog");
    }
}
