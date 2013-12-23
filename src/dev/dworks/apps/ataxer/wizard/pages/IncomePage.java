package dev.dworks.apps.ataxer.wizard.pages;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import dev.dworks.apps.ataxer.wizard.fragments.IncomeFragment;
import dev.dworks.lib.wizard.model.ReviewItem;
import dev.dworks.lib.wizard.model.WizardModelCallbacks;
import dev.dworks.lib.wizard.model.page.Page;

/**
 * A page asking for income
 */
public class IncomePage extends Page {
	public static final String TITLE = "Income";
    public static final String INCOME_SALARY_DATA_KEY = "income_salary";
    public static final String INCOME_SALARY_BASIC_DATA_KEY = "income_salary_basic";
    public static final String INCOME_SALARY_HRA_DATA_KEY = "income_salary_hra";
    public static final String INCOME_SALARY_CONVEYANCE_DATA_KEY = "income_salary_conveyance";
    public static final String INCOME_SALARY_LTA_DATA_KEY = "income_salary_lta";
    public static final String INCOME_SALARY_MEDICAL_DATA_KEY = "income_salary_medical";
    public static final String INCOME_SALARY_PF_DATA_KEY = "income_salary_pf";
    
    public static final String INCOME_OTHER_DATA_KEY = "income_other";
    
    public IncomePage(WizardModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return IncomeFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem("Income - Salary", String.valueOf(mData.getLong(INCOME_SALARY_DATA_KEY)), getKey(), -1));
        dest.add(new ReviewItem("Income - Others", String.valueOf(mData.getLong(INCOME_OTHER_DATA_KEY)), getKey(), -1));
    }
    
    @Override
    public boolean isCompleted() {
        return mData.getLong(INCOME_SALARY_DATA_KEY) != 0;
    }
}