package dev.dworks.apps.ataxer.wizard.pages;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import dev.dworks.apps.ataxer.wizard.fragments.DeductionFourFragment;
import dev.dworks.lib.wizard.model.ReviewItem;
import dev.dworks.lib.wizard.model.WizardModelCallbacks;
import dev.dworks.lib.wizard.model.page.Page;

/**
 * A page asking for other income
 */
public class DeductionFourPage extends Page {
	public static final String TITLE_DISPLAY = "Savings-IV";
	public static final String TITLE = TITLE_DISPLAY + " (sec 24)";
	public static final String DEDUCTION_80EE_DATA_KEY = "deduction_80ee";
    public static final String DEDUCTION_80E_DATA_KEY = "deduction_80e";
    public static final String DEDUCTION_80U_DATA_KEY = "deduction_80u";
    public static final String DEDUCTION_80G_DATA_KEY = "deduction_80g";
    public static final String DEDUCTION_80GG_DATA_KEY = "deduction_80gg";
    public static final String DEDUCTION_80TTA_DATA_KEY = "deduction_80tta";
    public static final String DEDUCTION_FOUR_TOTAL = "deduction_four_total";
    
    public DeductionFourPage(WizardModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return DeductionFourFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem(TITLE_DISPLAY, String.valueOf(mData.getLong(DEDUCTION_FOUR_TOTAL)), getKey(), -1));
    }
}