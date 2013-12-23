package dev.dworks.apps.ataxer.wizard.pages;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import dev.dworks.apps.ataxer.wizard.fragments.DeductionOneFragment;
import dev.dworks.lib.wizard.model.ReviewItem;
import dev.dworks.lib.wizard.model.WizardModelCallbacks;
import dev.dworks.lib.wizard.model.page.Page;

/**
 * A page asking for other income
 */
public class DeductionOnePage extends Page {
	public static final String TITLE_DISPLAY = "Savings-I";
	public static final String TITLE = TITLE_DISPLAY + " (sec 16)";
    public static final String DEDUCTION_ENTERTAINMENT_ALLOWANCE_DATA_KEY = "deduction_entertainment_allowance";
    public static final String DEDUCTION_TAX_ON_EMPLOYMENT_DATA_KEY = "deduction_tax_on_employment";
    public static final String DEDUCTION_ONE_TOTAL = "deduction_one_total";
    
    public DeductionOnePage(WizardModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return DeductionOneFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem(TITLE_DISPLAY, String.valueOf(mData.getLong(DEDUCTION_ONE_TOTAL)), getKey(), -1));
    }
    
    @Override
    public boolean isCompleted() {
    	return true;//mData.getLong(DEDUCTION_TAX_ON_EMPLOYMENT_DATA_KEY) != 0;
    }
}