package dev.dworks.apps.ataxer.entity;

import static dev.dworks.apps.ataxer.provider.TaxerDatabase.COMMA_SEP;
import static dev.dworks.apps.ataxer.provider.TaxerDatabase.TYPE_DECIMAL;
import static dev.dworks.apps.ataxer.provider.TaxerDatabase.TYPE_INTEGER;
import static dev.dworks.apps.ataxer.provider.TaxerDatabase.TYPE_TEXT;
import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;
import dev.dworks.apps.ataxer.provider.TaxerContract;


public class TaxCalculation{
	
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.ataxer.taxcalculations";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.ataxer.taxcalculation";
    public static final Uri CONTENT_URI =
            TaxerContract.BASE_CONTENT_URI.buildUpon().appendPath(TaxerContract.PATH_TAXCALCULATIONS).build();

    public static final String TABLE_NAME = "taxcalculation";
    
	public static final class TaxCalculationColumns implements BaseColumns {
	    public static final String NAME = "name";
	    public static final String CATEGORY = "category";
	    public static final String AGE = "age";
	    public static final String SEX = "sex";
	    public static final String PAN = "pan";
	    public static final String METRO = "metro";
	    public static final String RESIDENT = "resident";
	    public static final String FINANCIAL_YR = "financial_yr";
	    
	    public static final String INCOME_SALARY = "income_salary";
	    public static final String INCOME_SALARY_BASIC = "income_salary_basic";
	    public static final String INCOME_SALARY_HRA = "income_salary_hra";
	    public static final String INCOME_SALARY_CONVEYANCE = "income_salary_conveyance";
	    public static final String INCOME_SALARY_LTA = "income_salary_lta";
	    public static final String INCOME_SALARY_MEDICAL = "income_salary_medical";
	    public static final String INCOME_SALARY_PF = "income_salary_pf";
	    public static final String INCOME_OTHER = "income_other";
	    
	    public static final String ALLOWANCE_RENT_PAID = "allowance_rent_paid";
	    public static final String ALLOWANCE_HRA = "allowance_hra";
	    public static final String ALLOWANCE_TRANSPORT = "allowance_transport";
	    public static final String ALLOWANCE_GRATUITY = "allowance_gratuity";
	    public static final String ALLOWANCE_CHILD_EDUCATION = "allowance_child_education";
	    public static final String ALLOWANCE_LEAVE_ENCASHMENT = "allowance_leave_encashment";
	    public static final String ALLOWANCE_LTA = "allowance_lta";
	    public static final String ALLOWANCE_FOOD_COUPON = "allowance_food_coupon";
	    		
	    public static final String DEDUCTION_ENTERTAINMENT = "deduction_entertainment";
	    public static final String DEDUCTION_TAX_ON_EMPLOYMENT = "deduction_tax_on_employment";
	    
	    public static final String DEDUCTION_80C_LIC = "deduction_80c_lic";
	    public static final String DEDUCTION_80C_ULIP = "deduction_80c_ulip";
	    public static final String DEDUCTION_80C_PPF = "deduction_80c_public_provident_fund";
	    public static final String DEDUCTION_80C_EPF = "deduction_80c_employee_provident_fund";
	    public static final String DEDUCTION_80C_VPF = "deduction_80c_voluntary_provident_fund";
	    public static final String DEDUCTION_80C_MUTUAL_FUND = "deduction_80c_mutual_fund";
	    public static final String DEDUCTION_80C_INFRASTRUCTURE_BONDS = "deduction_80c_infrastructure_bonds";
	    public static final String DEDUCTION_80C_FIXED_DEPOSIT = "deduction_80c_fixed_deposit";
	    public static final String DEDUCTION_80C_NSC = "deduction_80c_national_saving_certificate";
	    public static final String DEDUCTION_80C_CHILD_TUTION_FEES = "deduction_80c_child_tution_fees";
	    public static final String DEDUCTION_80C_HLPR = "deduction_80c_house_load_principal_repayment";
	    public static final String DEDUCTION_80C_NSC_INTEREST = "deduction_80c_nsc_interest";

	    public static final String DEDUCTION_80CCC = "deduction_80ccc";
	    public static final String DEDUCTION_80CCD = "deduction_80ccd";
	    public static final String DEDUCTION_80CCG = "deduction_80ccg";
	    public static final String DEDUCTION_80D = "deduction_80d";
	    public static final String DEDUCTION_80DD = "deduction_80dd";
	    public static final String DEDUCTION_80DDB = "deduction_80ddb";
	    
	    public static final String DEDUCTION_80E = "deduction_80e";
	    public static final String DEDUCTION_80EE = "deduction_80ee";
	    public static final String DEDUCTION_80U = "deduction_80u";
	    public static final String DEDUCTION_80G = "deduction_80g";
	    public static final String DEDUCTION_80GG = "deduction_80gg";
	    public static final String DEDUCTION_80TTA = "deduction_80tta";
	    
	    public static final String TOTAL_INCOME = "total_income";
	    public static final String TOTAL_ALLOWANCE = "total_allowance";
	    public static final String TOTAL_DEDUCATION = "total_deducation";
	    public static final String TOTAL_DEDUCATION_ONE = "total_deducation_one";
	    public static final String TOTAL_DEDUCATION_TWO = "total_deducation_two";
	    public static final String TOTAL_DEDUCATION_THREE = "total_deducation_three";
	    public static final String TOTAL_DEDUCATION_FOUR = "total_deducation_four";
	    
	    
	    public static final String TOTAL_TAXABLE_INCOME = "total_taxable_income";
	    public static final String TAX_PAYABLE = "tax_payable";
	    public static final String REBATE = "rebate";
	    public static final String SURCHARGE = "surcharge";
	    public static final String EDUCATIONAL_CESS = "educational_cess";
	    public static final String TOTAL_TAX_PAYABLE = "total_tax_payable";
	    public static final String CREATED_AT = "created_at";
	    public static final String MODIFIED_AT = "modified_at";
	}
	
    /** SQL statement to create "taxcalculation" table. */
    public static final String SQL_CREATE_TAXCALUTATIONS =
            "CREATE TABLE IF NOT EXISTS " + TaxCalculation.TABLE_NAME + " (" +
                    TaxCalculationColumns._ID + " INTEGER PRIMARY KEY," +
                    TaxCalculationColumns.NAME + TYPE_TEXT + COMMA_SEP +
                    TaxCalculationColumns.CATEGORY + TYPE_INTEGER + COMMA_SEP +
                    TaxCalculationColumns.AGE + TYPE_INTEGER + COMMA_SEP +
                    TaxCalculationColumns.SEX + TYPE_INTEGER + COMMA_SEP +
                    TaxCalculationColumns.PAN + TYPE_TEXT + COMMA_SEP +
                    TaxCalculationColumns.METRO + TYPE_INTEGER + COMMA_SEP +
                    TaxCalculationColumns.RESIDENT + TYPE_INTEGER + COMMA_SEP +
                    TaxCalculationColumns.FINANCIAL_YR + TYPE_INTEGER + COMMA_SEP +
                    
                    TaxCalculationColumns.TOTAL_INCOME + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.TOTAL_ALLOWANCE + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.TOTAL_DEDUCATION + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.TOTAL_DEDUCATION_ONE + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.TOTAL_DEDUCATION_TWO + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.TOTAL_DEDUCATION_THREE + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.TOTAL_DEDUCATION_FOUR + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.TOTAL_TAXABLE_INCOME + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.TAX_PAYABLE + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.TOTAL_TAX_PAYABLE + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.REBATE + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.SURCHARGE + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.EDUCATIONAL_CESS + TYPE_DECIMAL + COMMA_SEP +
                    
                    TaxCalculationColumns.INCOME_SALARY + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.INCOME_SALARY_BASIC + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.INCOME_SALARY_HRA + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.INCOME_SALARY_CONVEYANCE + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.INCOME_SALARY_LTA + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.INCOME_SALARY_MEDICAL + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.INCOME_SALARY_PF + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.INCOME_OTHER + TYPE_DECIMAL + COMMA_SEP +
                    
                    TaxCalculationColumns.ALLOWANCE_RENT_PAID + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.ALLOWANCE_HRA + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.ALLOWANCE_TRANSPORT + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.ALLOWANCE_GRATUITY + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.ALLOWANCE_CHILD_EDUCATION + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.ALLOWANCE_LEAVE_ENCASHMENT + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.ALLOWANCE_LTA + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.ALLOWANCE_FOOD_COUPON + TYPE_DECIMAL + COMMA_SEP +

                    TaxCalculationColumns.DEDUCTION_TAX_ON_EMPLOYMENT + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.DEDUCTION_ENTERTAINMENT + TYPE_DECIMAL + COMMA_SEP +
                    
                    TaxCalculationColumns.DEDUCTION_80C_LIC + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.DEDUCTION_80C_ULIP + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.DEDUCTION_80C_PPF + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.DEDUCTION_80C_EPF + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.DEDUCTION_80C_VPF + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.DEDUCTION_80C_MUTUAL_FUND + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.DEDUCTION_80C_INFRASTRUCTURE_BONDS + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.DEDUCTION_80C_FIXED_DEPOSIT + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.DEDUCTION_80C_NSC + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.DEDUCTION_80C_CHILD_TUTION_FEES + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.DEDUCTION_80C_HLPR + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.DEDUCTION_80C_NSC_INTEREST + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.DEDUCTION_80CCC + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.DEDUCTION_80CCD + TYPE_DECIMAL + COMMA_SEP +
                    
                    TaxCalculationColumns.DEDUCTION_80CCG + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.DEDUCTION_80D + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.DEDUCTION_80DD + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.DEDUCTION_80DDB + TYPE_DECIMAL + COMMA_SEP +
                    
                    TaxCalculationColumns.DEDUCTION_80E + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.DEDUCTION_80EE + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.DEDUCTION_80U + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.DEDUCTION_80G + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.DEDUCTION_80GG + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.DEDUCTION_80TTA + TYPE_DECIMAL + COMMA_SEP +
                    TaxCalculationColumns.CREATED_AT + TYPE_INTEGER + COMMA_SEP +
                    TaxCalculationColumns.MODIFIED_AT + TYPE_INTEGER +
                    ")";
    
    /** SQL statement to drop "taxcalculation" table. */
    public static final String SQL_DELETE_TAXCALUTATIONS =
            "DROP TABLE IF EXISTS " + TaxCalculation.TABLE_NAME;

}