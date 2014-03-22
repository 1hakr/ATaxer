package dev.dworks.apps.ataxer.wizard.fragments;

import static dev.dworks.apps.ataxer.misc.Utils.getFormattedString;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import dev.dworks.apps.ataxer.R;
import dev.dworks.apps.ataxer.ui.TextCurrency;
import dev.dworks.apps.ataxer.wizard.pages.DeductionFourPage;
import dev.dworks.apps.ataxer.wizard.pages.DetailsPage;
import dev.dworks.apps.ataxer.wizard.pages.ExemptionPage;
import dev.dworks.apps.ataxer.wizard.pages.IncomePage;
import dev.dworks.betterpickers.numberpicker.NumberPickerBuilder;
import dev.dworks.betterpickers.numberpicker.NumberPickerDialogFragment;
import dev.dworks.libs.awizard.model.PageFragmentCallbacks;

public class ExemptionFragment extends Fragment implements
		NumberPickerDialogFragment.NumberPickerDialogHandler, OnClickListener {
	private static final String ARG_KEY = "key";

	private PageFragmentCallbacks mCallbacks;
	private String mKey;
	private ExemptionPage mPage;
	private DeductionFourPage mDeductionFourPage;

	private NumberPickerBuilder npb;
	private TextCurrency allowance_rent_paid;
	private TextCurrency allowance_transport;
	private TextCurrency allowance_gratuity;
	private TextCurrency allowance_child_education;
	private TextCurrency allowance_leave_encashment;
	private TextCurrency allowance_lta;

	private IncomePage mIncomePage;

	private TextCurrency allowance_food_coupon;

	public static ExemptionFragment create(String key) {
		Bundle args = new Bundle();
		args.putString(ARG_KEY, key);

		ExemptionFragment fragment = new ExemptionFragment();
		fragment.setArguments(args);
		return fragment;
	}

	public ExemptionFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle args = getArguments();
		mKey = args.getString(ARG_KEY);
		mPage = (ExemptionPage) mCallbacks.onGetPage(mKey);
		mIncomePage = ((IncomePage) mCallbacks.onGetPage("Income"));
		mDeductionFourPage = (DeductionFourPage) mCallbacks.onGetPage(DeductionFourPage.TITLE);
		
		npb = new NumberPickerBuilder()
		.setFragmentManager(getChildFragmentManager())
		.setStyleResId(R.style.BetterPickersDialogFragment_Light)
		.setTargetFragment(ExemptionFragment.this)
		.setPlusMinusVisibility(View.INVISIBLE)
		.setDecimalVisibility(View.INVISIBLE)
		.setLabelText(getString(R.string.rupee));
		
		if(null == savedInstanceState){
			//FIXME
			//getTotal(mPage);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_page_exemption, container, false);
		
		((TextView) rootView.findViewById(android.R.id.title)).setText(mPage
				.getTitle());

		allowance_rent_paid = ((TextCurrency) rootView.findViewById(R.id.allowance_rent_paid));
		allowance_rent_paid.setValueText(getFormattedNumber(ExemptionPage.ALLOWANCE_RENT_PAID_DATA_KEY));
		allowance_rent_paid.setInfo(getString(R.string.rendpaid_title), getString(R.string.rentpaid_info));
        
		allowance_transport = ((TextCurrency) rootView.findViewById(R.id.allowance_transport));
		allowance_transport.setValueText(getFormattedNumber(ExemptionPage.ALLOWANCE_TRANSPORT_DATA_KEY));
		allowance_transport.setInfo(getString(R.string.transport_title), getString(R.string.transport_info));
        
		allowance_gratuity = ((TextCurrency) rootView.findViewById(R.id.allowance_gratuity));
		allowance_gratuity.setValueText(getFormattedNumber(ExemptionPage.ALLOWANCE_GRATUITY_DATA_KEY));
		allowance_gratuity.setInfo(getString(R.string.gratuity_title), getString(R.string.gratuity_info));
        
		allowance_child_education = ((TextCurrency) rootView.findViewById(R.id.allowance_child_education));
		allowance_child_education.setValueText(getFormattedNumber(ExemptionPage.ALLOWANCE_CHILD_EDUCATION_DATA_KEY));
		allowance_child_education.setInfo(getString(R.string.childeducation_title), getString(R.string.childeducation_info));
        
		allowance_leave_encashment = ((TextCurrency) rootView.findViewById(R.id.allowance_leave_encashment));
		allowance_leave_encashment.setValueText(getFormattedNumber(ExemptionPage.ALLOWANCE_LEAVE_ENCASHMENT_DATA_KEY));
		allowance_leave_encashment.setInfo(getString(R.string.leave_title), getString(R.string.leave_info));
        
		allowance_lta = ((TextCurrency) rootView.findViewById(R.id.allowance_lta));
		allowance_lta.setValueText(getFormattedNumber(ExemptionPage.ALLOWANCE_LTA_DATA_KEY));
		allowance_lta.setInfo(getString(R.string.ltaspent_title), getString(R.string.ltaspent_info));
        
		allowance_food_coupon = ((TextCurrency) rootView.findViewById(R.id.allowance_food_coupon));
		allowance_food_coupon.setValueText(getFormattedNumber(ExemptionPage.ALLOWANCE_FOOD_COUPON_DATA_KEY));
		allowance_food_coupon.setInfo(getString(R.string.food_title), getString(R.string.food_info));
        
		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		if (!(activity instanceof PageFragmentCallbacks)) {
			throw new ClassCastException(
					"Activity must implement PageFragmentCallbacks");
		}

		mCallbacks = (PageFragmentCallbacks) activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mCallbacks = null;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		allowance_rent_paid.setOnClickListener(this);
		allowance_transport.setOnClickListener(this);
		allowance_gratuity.setOnClickListener(this);
		allowance_child_education.setOnClickListener(this);
		allowance_leave_encashment.setOnClickListener(this);
		allowance_lta.setOnClickListener(this);
		allowance_food_coupon.setOnClickListener(this);
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			
			if (allowance_transport != null) {
				allowance_transport.setValueText(getFormattedNumber(ExemptionPage.ALLOWANCE_TRANSPORT_DATA_KEY));
			}
			if (allowance_lta != null) {
				allowance_lta.setValueText(getFormattedNumber(ExemptionPage.ALLOWANCE_LTA_DATA_KEY));
			}
			if (allowance_transport != null) {
				allowance_transport.setValueText(getFormattedNumber(ExemptionPage.ALLOWANCE_TRANSPORT_DATA_KEY));
			}
		}
	}

	@Override
	public void onDialogNumberSet(int reference, long number, double decimal,
			boolean isNegative, double fullNumber) {

		switch (reference) { 
		case R.id.allowance_rent_paid:
			allowance_rent_paid.setValueText(getFormattedString(number));
			mPage.getData().putLong(ExemptionPage.ALLOWANCE_RENT_PAID_DATA_KEY,
					(number != 0) ? number : 0);
			mPage.notifyDataChanged();
			calculateHRA();
			break;

		case R.id.allowance_transport:
			allowance_transport.setValueText(getFormattedString(number));
			mPage.getData().putLong(
					ExemptionPage.ALLOWANCE_TRANSPORT_DATA_KEY,
					(number != 0) ? number : 0);
			mPage.notifyDataChanged();
			break;

		case R.id.allowance_gratuity:
			allowance_gratuity.setValueText(getFormattedString(number));
			mPage.getData().putLong(
					ExemptionPage.ALLOWANCE_GRATUITY_DATA_KEY,
					(number != 0) ? number : 0);
			mPage.notifyDataChanged();
			break;

		case R.id.allowance_child_education:
			allowance_child_education.setValueText(getFormattedString(number));
			mPage.getData().putLong(
					ExemptionPage.ALLOWANCE_CHILD_EDUCATION_DATA_KEY,
					(number != 0) ? number : 0);
			mPage.notifyDataChanged();
			break;

		case R.id.allowance_leave_encashment:
			allowance_leave_encashment.setValueText(getFormattedString(number));
			mPage.getData().putLong(
					ExemptionPage.ALLOWANCE_LEAVE_ENCASHMENT_DATA_KEY,
					(number != 0) ? number : 0);
			mPage.notifyDataChanged();
			break;

		case R.id.allowance_lta:
			allowance_lta.setValueText(getFormattedString(number));
			mPage.getData().putLong(ExemptionPage.ALLOWANCE_LTA_DATA_KEY,
					(number != 0) ? number : 0);
			mPage.notifyDataChanged();
			break;
			
		case R.id.allowance_food_coupon:
			allowance_food_coupon.setValueText(getFormattedString(number));
			mPage.getData().putLong(ExemptionPage.ALLOWANCE_FOOD_COUPON_DATA_KEY, (number != 0) ? number : 0);
			mPage.notifyDataChanged();
			break;
		}
		getTotal(mPage);
	}

	@Override
	public void onClick(View v) {
		npb.setReference(v.getId());
		switch (v.getId()) {
		case R.id.allowance_rent_paid:
			if (mIncomePage.getData().getLong(IncomePage.INCOME_SALARY_HRA_DATA_KEY) == 0) {
				Toast.makeText(getActivity(), "Not allowed. HRA is not entered. Enter Rent Paid in Saving-IV.", Toast.LENGTH_SHORT).show();
			} else {
				npb.setMaxNumber(null);
				npb.setNumber(getNumber(ExemptionPage.ALLOWANCE_RENT_PAID_DATA_KEY)).show();
			}
			break;
			
		case R.id.allowance_transport:
			npb.setMaxNumber(9600);
			npb.setNumber(getNumber(ExemptionPage.ALLOWANCE_TRANSPORT_DATA_KEY)).show();
			break;
			
		case R.id.allowance_gratuity:
			npb.setMaxNumber(null);
			npb.setNumber(getNumber(ExemptionPage.ALLOWANCE_GRATUITY_DATA_KEY)).show();
			break;

		case R.id.allowance_child_education:
			npb.setMaxNumber(null);
			npb.setNumber(getNumber(ExemptionPage.ALLOWANCE_CHILD_EDUCATION_DATA_KEY)).show();
			break;

		case R.id.allowance_leave_encashment:
			npb.setMaxNumber(null);
			npb.setNumber(getNumber(ExemptionPage.ALLOWANCE_LEAVE_ENCASHMENT_DATA_KEY)).show();
			break;

		case R.id.allowance_lta:
			npb.setMaxNumber(12000);
			//npb.setMaxNumber((int) mIncomePage.getData().getLong(IncomePage.INCOME_SALARY_LTA_DATA_KEY));
			npb.setNumber(getNumber(ExemptionPage.ALLOWANCE_LTA_DATA_KEY)).show();
			break;
			
		case R.id.allowance_food_coupon:
			npb.setMaxNumber(20000);
			npb.setNumber(getNumber(ExemptionPage.ALLOWANCE_FOOD_COUPON_DATA_KEY)).show();
			break;
		}
	}
	
	private void calculateHRA() {
		DetailsPage mDetailsPage = (DetailsPage) mCallbacks.onGetPage(DetailsPage.TITLE);
		IncomePage mIncomePage = (IncomePage) mCallbacks.onGetPage(IncomePage.TITLE);
		long hra = mIncomePage.getData().getLong(IncomePage.INCOME_SALARY_HRA_DATA_KEY, 0);
		long basic = mIncomePage.getData().getLong(IncomePage.INCOME_SALARY_BASIC_DATA_KEY, 0);
		long rent_paid = mPage.getData().getLong(ExemptionPage.ALLOWANCE_RENT_PAID_DATA_KEY, 0);
		boolean is_metro = mDetailsPage.getData().getInt(DetailsPage.METRO_DATA_KEY, 0) == 1;
		long basic_part  = (basic * (is_metro ? 50 : 40)) / 100;
		long extra_part = rent_paid - (basic * 10) / 100;
		
		long minimum = hra;
		
		if(rent_paid == 0){
			minimum = 0;	
		}
		else{
			if(hra != 0){
				if(minimum > basic_part){
					minimum = basic_part;
				}
				if(minimum > extra_part){
					minimum = extra_part;
				}
				mDeductionFourPage.getData().putLong(DeductionFourPage.DEDUCTION_80GG_DATA_KEY, 0);
				mDeductionFourPage.notifyDataChanged();
			}
			else{
				mDeductionFourPage.getData().putLong(DeductionFourPage.DEDUCTION_80GG_DATA_KEY, rent_paid);
				mDeductionFourPage.notifyDataChanged();
			}
		}
		
		mPage.getData().putLong(ExemptionPage.ALLOWANCE_HRA_DATA_KEY, minimum);
		mPage.notifyDataChanged();
	}

	public String getFormattedNumber(String key){
		return getFormattedString(mPage.getData().getLong(key));
	}
	
	public String getNumber(String key){
		return String.valueOf(mPage.getData().getLong(key));
	}
	
	public static int getTotal(ExemptionPage mPage) {
		int total = 0;
        for (String key : mPage.getData().keySet()) {
        	if(key == ExemptionPage.ALLOWANCE_TOTAL || key == ExemptionPage.ALLOWANCE_RENT_PAID_DATA_KEY){
        		continue;
        	}
        	total += mPage.getData().getLong(key, 0);
        }
		mPage.getData().putLong(ExemptionPage.ALLOWANCE_TOTAL, total);
		mPage.notifyDataChanged();
		return total;
	}
}