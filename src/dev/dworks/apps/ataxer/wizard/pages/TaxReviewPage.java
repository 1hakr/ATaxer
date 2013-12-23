package dev.dworks.apps.ataxer.wizard.pages;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import dev.dworks.apps.ataxer.wizard.fragments.TaxReviewFragment;
import dev.dworks.lib.wizard.model.ReviewItem;
import dev.dworks.lib.wizard.model.WizardModelCallbacks;
import dev.dworks.lib.wizard.model.page.ReviewPage;

public class TaxReviewPage extends ReviewPage{
    
    public TaxReviewPage(WizardModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return TaxReviewFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
    }
    
    @Override
    public boolean isCompleted() {
    	return mData.getBoolean(PROCESS_DATA_KEY);
    }
}
