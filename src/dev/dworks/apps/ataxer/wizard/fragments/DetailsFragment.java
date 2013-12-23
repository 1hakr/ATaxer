package dev.dworks.apps.ataxer.wizard.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.actionbarsherlock.internal.widget.IcsAdapterView;
import com.actionbarsherlock.internal.widget.IcsAdapterView.OnItemSelectedListener;
import com.actionbarsherlock.internal.widget.IcsSpinner;

import dev.dworks.apps.ataxer.R;
import dev.dworks.apps.ataxer.wizard.pages.DetailsPage;
import dev.dworks.lib.wizard.model.PageFragmentCallbacks;

public class DetailsFragment extends Fragment 
	implements OnItemSelectedListener, OnCheckedChangeListener{
    private static final String ARG_KEY = "key";

    private PageFragmentCallbacks mCallbacks;
    private String mKey;
    private DetailsPage mPage;
    private EditText name;
	private IcsSpinner financial_year;

	private EditText age;
	private RadioGroup radio_group_sex;
	private RadioGroup radio_group_metro;
	private RadioButton radio_male;
	private RadioButton radio_female;
	private boolean defaultSelection = false;

    public static DetailsFragment create(String key) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public DetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mKey = args.getString(ARG_KEY);
        mPage = (DetailsPage) mCallbacks.onGetPage(mKey);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page_details, container, false);
        ((TextView) rootView.findViewById(android.R.id.title)).setText(mPage.getTitle());

        name = (EditText) rootView.findViewById(R.id.name);
        age = (EditText) rootView.findViewById(R.id.age);
        radio_group_sex = (RadioGroup) rootView.findViewById(R.id.radio_group_sex);
        radio_male = (RadioButton) rootView.findViewById(R.id.radio_male);
        radio_female = (RadioButton) rootView.findViewById(R.id.radio_female);
        radio_group_metro = (RadioGroup) rootView.findViewById(R.id.radio_group_metro);
        financial_year = (IcsSpinner)rootView.findViewById(R.id.financial_year);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.financial_yr_options, R.layout.item_year_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        financial_year.setAdapter(adapter);
        
        name.setText(mPage.getData().getString(DetailsPage.NAME_DATA_KEY));
        int ageNumber = mPage.getData().getInt(DetailsPage.AGE_DATA_KEY);
        if(ageNumber != 0){
        	age.setText(String.valueOf(ageNumber));
        }

        int sex = mPage.getData().getInt(DetailsPage.SEX_DATA_KEY, 0);
        switch (sex) {
		case 1:
			radio_group_sex.check(R.id.radio_male);
			radio_male.setText("Male ");
			break;

		case 2:
			radio_group_sex.check(R.id.radio_female);
			radio_female.setText("Female ");
			break;
		}

        int metro = mPage.getData().getInt(DetailsPage.METRO_DATA_KEY, 0);
        switch (metro) {
		case 1:
			radio_group_metro.check(R.id.radio_metro);
			break;

		case 2:
			radio_group_metro.check(R.id.radio_non_metro);
			break;
		}

        financial_year.setSelection(mPage.getData().getInt(DetailsPage.FINANCIAL_YR_DATA_KEY, 0));
        
        return rootView;
    }

	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!(activity instanceof PageFragmentCallbacks)) {
            throw new ClassCastException("Activity must implement PageFragmentCallbacks");
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

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mPage.getData().putString(DetailsPage.NAME_DATA_KEY,
                        (editable != null) ? editable.toString() : null);
                mPage.notifyDataChanged();
            }
        });

        age.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
            	if(TextUtils.isEmpty(editable.toString()) || !TextUtils.isDigitsOnly(editable.toString())){
                    mPage.getData().putInt(DetailsPage.CATEGORY_DATA_KEY, 0);
                    mPage.notifyDataChanged();
            		return;
            	}
            	int number = Integer.valueOf(editable.toString());
                mPage.getData().putInt(DetailsPage.AGE_DATA_KEY,
                		(number != 0) ? number : null);
                mPage.notifyDataChanged();
    	        calculateCategory();
            }
        });
        
        financial_year.setOnItemSelectedListener(this);
        radio_group_sex.setOnCheckedChangeListener(this);
        radio_group_metro.setOnCheckedChangeListener(this);
    }	

	private void calculateCategory() {
		int age = mPage.getData().getInt(DetailsPage.AGE_DATA_KEY, 0);
		int sex = mPage.getData().getInt(DetailsPage.SEX_DATA_KEY, 0);
		int category = 0;
		if(age != 0 && sex != 0){
			if(age < 60){
				if(sex == 1){
					category = 1;
				}
				else if(sex == 2){
					category = 2;
				}
			}
			else if(age >= 60 && age < 80){
				category = 3;
			} 
			else if(age >= 80){
				category = 4;
			} 			
		}
        mPage.getData().putInt(DetailsPage.CATEGORY_DATA_KEY, category);
        mPage.notifyDataChanged();
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (group.getId()) {
		case R.id.radio_group_sex:
			int value =  checkedId == R.id.radio_male ? 1 : 2;
			switch (value) {
			case 1:
				radio_male.setText("Male ");
				radio_female.setText(null);
				break;
			case 2:
				radio_male.setText(null);
				radio_female.setText("Female ");				
				break;
			}
			hideSoftKeyboard();
	        mPage.getData().putInt(DetailsPage.SEX_DATA_KEY, value);
	        mPage.notifyDataChanged();
	        calculateCategory();
			break;
		
		case R.id.radio_group_metro:
			int value2 =  checkedId == R.id.radio_metro ? 1 : 2;
			hideSoftKeyboard();
	        mPage.getData().putInt(DetailsPage.METRO_DATA_KEY, value2);
	        mPage.notifyDataChanged();
			break;
		
		}
	}

	@Override
	public void onItemSelected(IcsAdapterView<?> parent, View view, int position, long id) {
		hideSoftKeyboard();
		switch (parent.getId()) {
		case R.id.category:
	        mPage.getData().putInt(DetailsPage.CATEGORY_DATA_KEY, position);
	        mPage.notifyDataChanged();
			break;
			
		case R.id.financial_year:
			if(!defaultSelection){
				defaultSelection = true;
			}
			else{
				mPage.getData().putInt(DetailsPage.FINANCIAL_YR_DATA_KEY, position);
	        	mPage.notifyDataChanged();
			}
			break;
		}		
	}

	@Override
	public void onNothingSelected(IcsAdapterView<?> parent) {
		
	}

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
    	super.setUserVisibleHint(isVisibleToUser);
        if (name != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (!isVisibleToUser) {
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            }
        }
    }
    
	private void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null ) {
            inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        }
	}
}