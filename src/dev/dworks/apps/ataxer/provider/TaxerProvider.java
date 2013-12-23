/*
 * Copyright 2013 Hari Krishna Dulipudi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.dworks.apps.ataxer.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import dev.dworks.apps.ataxer.entity.TaxCalculation;
import dev.dworks.apps.ataxer.entity.TaxCalculation.TaxCalculationColumns;
import dev.dworks.apps.ataxer.entity.TaxDocument;
import dev.dworks.apps.ataxer.entity.TaxDocument.TaxDocumentColumns;
import dev.dworks.apps.ataxer.entity.TaxSuggestion.TaxSuggestionColumns;
import dev.dworks.apps.ataxer.entity.TaxSuggestion;

public class TaxerProvider extends ContentProvider {
    TaxerDatabase mDatabaseHelper;

    /**
     * Content authority for this provider.
     */
    private static final String AUTHORITY = TaxerContract.CONTENT_AUTHORITY;

    // The constants below represent individual URI routes, as IDs. Every URI pattern recognized by
    // this ContentProvider is defined using sUriMatcher.addURI(), and associated with one of these
    // IDs.
    //
    // When a incoming URI is run through sUriMatcher, it will be tested against the defined
    // URI patterns, and the corresponding route ID will be returned.
    /**
     * URI ID for route: /taxcalculations
     */
    public static final int TAXCALCULATIONS = 1;

    /**
     * URI ID for route: /taxcalculations/{ID}
     */
    public static final int TAXCALCULATIONS_ID = 2;
    
    public static final int TAXDOCUMENTS = 3;
    public static final int TAXDOCUMENTS_ID = 4;
    
    public static final int TAXSUGGESTIONS = 5;
    public static final int TAXSUGGESTIONS_ID = 6;

    /**
     * UriMatcher, used to decode incoming URIs.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI(AUTHORITY, "taxcalculations", TAXCALCULATIONS);
        sUriMatcher.addURI(AUTHORITY, "taxcalculations/*", TAXCALCULATIONS_ID);
        sUriMatcher.addURI(AUTHORITY, "taxdocuments", TAXDOCUMENTS);
        sUriMatcher.addURI(AUTHORITY, "taxdocuments/*", TAXDOCUMENTS_ID);
        sUriMatcher.addURI(AUTHORITY, "taxsuggestions", TAXSUGGESTIONS);
        sUriMatcher.addURI(AUTHORITY, "taxsuggestions/*", TAXSUGGESTIONS_ID);
    }

    @Override
    public boolean onCreate() {
        mDatabaseHelper = new TaxerDatabase(getContext());
        return true;
    }

    /**
     * Determine the mime type for tax calculation returned by a given URI.
     */
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case TAXCALCULATIONS:
                return TaxCalculation.CONTENT_TYPE;
            case TAXCALCULATIONS_ID:
                return TaxCalculation.CONTENT_ITEM_TYPE;
            case TAXDOCUMENTS:
                return TaxDocument.CONTENT_TYPE;
            case TAXDOCUMENTS_ID:
                return TaxDocument.CONTENT_ITEM_TYPE;
            case TAXSUGGESTIONS:
                return TaxSuggestion.CONTENT_TYPE;
            case TAXSUGGESTIONS_ID:
                return TaxSuggestion.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    /**
     * Perform a database query by URI.
     *
     * <p>Currently supports returning all tax calculation (/taxcalculation) and individual tax calculation by ID
     * (/taxcalculation/{ID}).
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        SelectionBuilder builder = new SelectionBuilder();
        int uriMatch = sUriMatcher.match(uri);
        String id = "";
        Cursor cursor = null;
        Context context = getContext();
        switch (uriMatch) {
            case TAXCALCULATIONS_ID:
                // Return a single tax calculation, by ID.
                id = uri.getLastPathSegment();
                builder.where(TaxCalculationColumns._ID + "=?", id);
            case TAXCALCULATIONS:
                // Return all known tax calculations.
                builder.table(TaxCalculation.TABLE_NAME)
                       .where(selection, selectionArgs);
                cursor = builder.query(db, projection, sortOrder);
                // Note: Notification URI must be manually set here for loaders to correctly
                // register ContentObservers.
                cursor.setNotificationUri(context.getContentResolver(), uri);
                return cursor;
            case TAXDOCUMENTS_ID:
                id = uri.getLastPathSegment();
                builder.where(TaxDocumentColumns._ID + "=?", id);
            case TAXDOCUMENTS:
                builder.table(TaxDocument.TABLE_NAME)
                       .where(selection, selectionArgs);
                cursor = builder.query(db, projection, sortOrder);
                cursor.setNotificationUri(context.getContentResolver(), uri);
                return cursor;
            case TAXSUGGESTIONS_ID:
                id = uri.getLastPathSegment();
                builder.where(TaxSuggestionColumns._ID + "=?", id);
            case TAXSUGGESTIONS:
                builder.table(TaxSuggestion.TABLE_NAME)
                       .where(selection, selectionArgs);
                cursor = builder.query(db, projection, sortOrder);
                cursor.setNotificationUri(context.getContentResolver(), uri);
                return cursor;                  
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    /**
     * Insert a new tax calculation into the database.
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        assert db != null;
        final int match = sUriMatcher.match(uri);
        Uri result;
        long id;
        switch (match) {
            case TAXCALCULATIONS:
                id = db.insertOrThrow(TaxCalculation.TABLE_NAME, null, values);
                result = Uri.parse(TaxCalculation.CONTENT_URI + "/" + id);
                break;
            case TAXCALCULATIONS_ID:
                throw new UnsupportedOperationException("Insert not supported on URI: " + uri);
            case TAXDOCUMENTS:
                id = db.insertOrThrow(TaxCalculation.TABLE_NAME, null, values);
                result = Uri.parse(TaxCalculation.CONTENT_URI + "/" + id);
                break;
            case TAXDOCUMENTS_ID:
                throw new UnsupportedOperationException("Insert not supported on URI: " + uri);
            case TAXSUGGESTIONS:
                id = db.insertOrThrow(TaxCalculation.TABLE_NAME, null, values);
                result = Uri.parse(TaxCalculation.CONTENT_URI + "/" + id);
                break;
            case TAXSUGGESTIONS_ID:
                throw new UnsupportedOperationException("Insert not supported on URI: " + uri);
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Send broadcast to registered ContentObservers, to refresh UI.
        Context ctx = getContext();
        assert ctx != null;
        ctx.getContentResolver().notifyChange(uri, null, false);
        return result;
    }

    /**
     * Delete an tax calculation by database by URI.
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SelectionBuilder builder = new SelectionBuilder();
        final SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int count;
        switch (match) {
            case TAXCALCULATIONS:
                count = builder.table(TaxCalculation.TABLE_NAME)
                        .where(selection, selectionArgs)
                        .delete(db);
                break;
            case TAXCALCULATIONS_ID:
                String id = uri.getLastPathSegment();
                count = builder.table(TaxCalculation.TABLE_NAME)
                       .where(TaxCalculationColumns._ID + "=?", id)
                       .where(selection, selectionArgs)
                       .delete(db);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Send broadcast to registered ContentObservers, to refresh UI.
        Context ctx = getContext();
        assert ctx != null;
        ctx.getContentResolver().notifyChange(uri, null, false);
        return count;
    }

    /**
     * Update an tax calculation in the database by URI.
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SelectionBuilder builder = new SelectionBuilder();
        final SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int count;
        switch (match) {
            case TAXCALCULATIONS:
                count = builder.table(TaxCalculation.TABLE_NAME)
                        .where(selection, selectionArgs)
                        .update(db, values);
                break;
            case TAXCALCULATIONS_ID:
                String id = uri.getLastPathSegment();
                count = builder.table(TaxCalculation.TABLE_NAME)
                        .where(TaxCalculationColumns._ID + "=?", id)
                        .where(selection, selectionArgs)
                        .update(db, values);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        Context ctx = getContext();
        assert ctx != null;
        ctx.getContentResolver().notifyChange(uri, null, false);
        return count;
    }
}