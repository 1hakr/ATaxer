package dev.dworks.apps.ataxer.entity;

import static dev.dworks.apps.ataxer.provider.TaxerDatabase.COMMA_SEP;
import static dev.dworks.apps.ataxer.provider.TaxerDatabase.TYPE_INTEGER;
import static dev.dworks.apps.ataxer.provider.TaxerDatabase.TYPE_TEXT;
import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;
import dev.dworks.apps.ataxer.provider.TaxerContract;


public class TaxSuggestion{
	
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.ataxer.taxsuggestions";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.ataxer.taxsuggestion";
    public static final Uri CONTENT_URI =
            TaxerContract.BASE_CONTENT_URI.buildUpon().appendPath(TaxerContract.PATH_TAXSUGGESTIONS).build();

    public static final String TABLE_NAME = "taxsuggestion";
    
	public static final class TaxSuggestionColumns implements BaseColumns {
	    public static final String NAME = "name";
	    public static final String DETAILS = "details";
	    public static final String TAX_COMPONENT_ID = "tax_component_id";
	    public static final String TAX_COMPONENT_NAME = "tax_component_name";
	    public static final String TAX_LIMIT = "tax_limit";
	}
	
    /** SQL statement to create "taxsuggestion" table. */
    public static final String SQL_CREATE_TAXSUGGESTIONS =
            "CREATE TABLE IF NOT EXISTS " + TaxSuggestion.TABLE_NAME + " (" +
                    TaxSuggestionColumns._ID + " INTEGER PRIMARY KEY," +
                    TaxSuggestionColumns.NAME + TYPE_TEXT + COMMA_SEP +
                    TaxSuggestionColumns.DETAILS + TYPE_TEXT + COMMA_SEP +
                    TaxSuggestionColumns.TAX_COMPONENT_ID + TYPE_INTEGER + COMMA_SEP +
                    TaxSuggestionColumns.TAX_COMPONENT_NAME + TYPE_TEXT + COMMA_SEP +
                    TaxSuggestionColumns.TAX_LIMIT + TYPE_INTEGER + 
                    ")";
    
    /** SQL statement to drop "taxsuggestion" table. */
    public static final String SQL_DELETE_TAXSUGGESTIONS =
            "DROP TABLE IF EXISTS " + TaxSuggestion.TABLE_NAME;

}