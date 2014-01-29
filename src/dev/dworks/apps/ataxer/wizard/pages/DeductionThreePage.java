package dev.dworks.apps.ataxer.wizard.pages;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import dev.dworks.apps.ataxer.wizard.fragments.DeductionThreeFragment;
import dev.dworks.libs.awizard.model.ReviewItem;
import dev.dworks.libs.awizard.model.WizardModelCallbacks;
import dev.dworks.libs.awizard.model.page.Page;

/**
 * A page asking for other income
 */
public class DeductionThreePage extends Page {
	public static final String TITLE_DISPLAY = "Savings-III";
	public static final String TITLE = TITLE_DISPLAY + " (sec 24)";
    public static final String DEDUCTION_80CCG_DATA_KEY = "deduction_80ccg";
    public static final String DEDUCTION_80D_DATA_KEY = "deduction_80d";
    public static final String DEDUCTION_80DD_DATA_KEY = "deduction_80dd";
    public static final String DEDUCTION_80DDB_DATA_KEY = "deduction_80ddb";
    public static final String DEDUCTION_THREE_TOTAL = "deduction_three_total";
    
    public DeductionThreePage(WizardModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return DeductionThreeFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem(TITLE_DISPLAY, String.valueOf(mData.getLong(DEDUCTION_THREE_TOTAL)), getKey(), -1));
    }
}