package dev.dworks.apps.ataxer.wizard.pages;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import dev.dworks.apps.ataxer.misc.Utils;
import dev.dworks.apps.ataxer.wizard.fragments.DetailsFragment;
import dev.dworks.lib.wizard.model.ReviewItem;
import dev.dworks.lib.wizard.model.WizardModelCallbacks;
import dev.dworks.lib.wizard.model.page.Page;

public class DetailsPage extends Page {
	public static final String AGE_DATA_KEY = "age";
	public static final String CATEGORY_DATA_KEY = "category";
	public static final String FINANCIAL_YR_DATA_KEY = "financial_yr";
	public static final String METRO_DATA_KEY = "metro";
	public static final String NAME_DATA_KEY = "name";
	public static final String SEX_DATA_KEY = "sex";
	public static final String TITLE = "Details";

	public DetailsPage(WizardModelCallbacks paramWizardModelCallbacks, String paramString) {
		super(paramWizardModelCallbacks, paramString);
	}

	public Fragment createFragment() {
		return DetailsFragment.create(getKey());
	}

	public void getReviewItems(ArrayList<ReviewItem> paramArrayList) {
		paramArrayList.add(new ReviewItem("Name", mData.getString(NAME_DATA_KEY), getKey(), -1));
		paramArrayList.add(new ReviewItem("Category", Utils.getCategory(mData.getInt(CATEGORY_DATA_KEY)), getKey(), -1));
		paramArrayList.add(new ReviewItem("Financial Year", Utils.getFinancialYr(mData.getInt(FINANCIAL_YR_DATA_KEY)), getKey(), -1));
	}

	public boolean isCompleted() {
		return (!TextUtils.isEmpty(mData.getString(NAME_DATA_KEY))) 
				&& (mData.getInt(CATEGORY_DATA_KEY) != 0) 
				&& (mData.getInt(FINANCIAL_YR_DATA_KEY) != 0)
				&& (mData.getInt(METRO_DATA_KEY) != 0);
	}
}