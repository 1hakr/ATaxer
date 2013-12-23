package dev.dworks.apps.ataxer.entity;

import static dev.dworks.apps.ataxer.provider.TaxerDatabase.COMMA_SEP;
import static dev.dworks.apps.ataxer.provider.TaxerDatabase.TYPE_INTEGER;
import static dev.dworks.apps.ataxer.provider.TaxerDatabase.TYPE_TEXT;
import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;
import dev.dworks.apps.ataxer.provider.TaxerContract;


public class TaxDocument{
	
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.ataxer.taxdocuments";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.ataxer.taxdocument";
    public static final Uri CONTENT_URI =
            TaxerContract.BASE_CONTENT_URI.buildUpon().appendPath(TaxerContract.PATH_TAXDOCUMENTS).build();

    public static final String TABLE_NAME = "taxdocument";
    
	public static final class TaxDocumentColumns implements BaseColumns {
	    public static final String NAME = "name";
	    public static final String DETAILS = "details";
	    public static final String TAX_COMPONENT_ID = "tax_component_id";
	    public static final String TAX_COMPONENT_NAME = "tax_component_name";
	    public static final String URL = "url";
	    public static final String PATH = "path";
	}
	
    /** SQL statement to create "entry" table. */
    public static final String SQL_CREATE_TAXDOCUMENTS =
            "CREATE TABLE IF NOT EXISTS " + TaxDocument.TABLE_NAME + " (" +
                    TaxDocumentColumns._ID + " INTEGER PRIMARY KEY," +
                    TaxDocumentColumns.NAME + TYPE_TEXT + COMMA_SEP +
                    TaxDocumentColumns.DETAILS + TYPE_TEXT + COMMA_SEP +
                    TaxDocumentColumns.TAX_COMPONENT_ID + TYPE_INTEGER + COMMA_SEP +
                    TaxDocumentColumns.TAX_COMPONENT_NAME + TYPE_TEXT + COMMA_SEP +
                    TaxDocumentColumns.URL + TYPE_TEXT + COMMA_SEP +
                    TaxDocumentColumns.PATH + TYPE_TEXT +
                    ")";
    
    /** SQL statement to drop "entry" table. */
    public static final String SQL_DELETE_TAXDOCUMENTS =
            "DROP TABLE IF EXISTS " + TaxDocument.TABLE_NAME;

}