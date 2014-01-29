package dev.dworks.apps.ataxer.wizard.pages;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import dev.dworks.apps.ataxer.wizard.fragments.DeductionTwoFragment;
import dev.dworks.libs.awizard.model.ReviewItem;
import dev.dworks.libs.awizard.model.WizardModelCallbacks;
import dev.dworks.libs.awizard.model.page.Page;

/**
 * A page asking for other income
 */
public class DeductionTwoPage extends Page {
	public static final String TITLE_DISPLAY = "Savings-II";
	public static final String TITLE = TITLE_DISPLAY + " (sec 24)";
    public static final String DEDUCTION_LIC_DATA_KEY = "deduction_lic";
    public static final String DEDUCTION_ULIP_DATA_KEY = "deduction_ulip";
    public static final String DEDUCTION_PPF_DATA_KEY = "deduction_ppf";
    public static final String DEDUCTION_EPF_DATA_KEY = "deduction_epf";
    public static final String DEDUCTION_VPF_DATA_KEY = "deduction_vpf";
    public static final String DEDUCTION_MUTUAL_FUNDS_DATA_KEY = "deduction_mutual_funds";
    public static final String DEDUCTION_INFRASTRUCTURE_BONDS_DATA_KEY = "deduction_infrastructure_bonds";
    
    public static final String DEDUCTION_FIXED_DEPOSIT_DATA_KEY = "deduction_fixed_deposit";
    public static final String DEDUCTION_NSC_DATA_KEY = "deduction_nsc";
    public static final String DEDUCTION_CHILD_TUTION_FEE_DATA_KEY = "deduction_child_tution_fee";
    public static final String DEDUCTION_HLPR_DATA_KEY = "deduction_hlpr";
    public static final String DEDUCTION_NSC_INTEREST_DATA_KEY = "deduction_nsc_interest";
    
    public static final String DEDUCTION_80CCC_DATA_KEY = "deduction_80ccc";
    public static final String DEDUCTION_80CCD_DATA_KEY = "deduction_80ccd";
    
    public static final String DEDUCTION_TWO_TOTAL = "deduction_two_total";
    
    public DeductionTwoPage(WizardModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return DeductionTwoFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem(TITLE_DISPLAY, String.valueOf(mData.getLong(DEDUCTION_TWO_TOTAL)), getKey(), -1));
    }
}