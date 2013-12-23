package dev.dworks.apps.ataxer.wizard.pages;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import dev.dworks.apps.ataxer.wizard.fragments.ExemptionFragment;
import dev.dworks.lib.wizard.model.ReviewItem;
import dev.dworks.lib.wizard.model.WizardModelCallbacks;
import dev.dworks.lib.wizard.model.page.Page;

/**
 * A page asking for other income
 */
public class ExemptionPage extends Page {
	public static final String TITLE_DISPLAY = "Exemptions";
	public static final String TITLE = TITLE_DISPLAY + " (sec 10)";
	public static final String ALLOWANCE_RENT_PAID_DATA_KEY = "allowance_rent_paid";
    public static final String ALLOWANCE_HRA_DATA_KEY = "allowance_hra";
    public static final String ALLOWANCE_TRANSPORT_DATA_KEY = "allowance_transport";
    public static final String ALLOWANCE_GRATUITY_DATA_KEY = "allowance_gratuity";
    public static final String ALLOWANCE_CHILD_EDUCATION_DATA_KEY = "allowance_child_education";
    public static final String ALLOWANCE_LEAVE_ENCASHMENT_DATA_KEY = "allowance_leave_encashment";
    public static final String ALLOWANCE_LTA_DATA_KEY = "allowance_lta";
    public static final String ALLOWANCE_FOOD_COUPON_DATA_KEY = "allowance_food_coupon";
    public static final String ALLOWANCE_TOTAL = "allowance_total";
    
    public ExemptionPage(WizardModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return ExemptionFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem(TITLE_DISPLAY, String.valueOf(mData.getLong(ALLOWANCE_TOTAL)), getKey(), -1));
    }
    
/*    @Override
    public boolean isCompleted() {
    	return mData.getLong(ALLOWANCE_RENT_PAID_DATA_KEY) != 0;
    }*/
}