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

import dev.dworks.apps.ataxer.entity.TaxCalculation;
import dev.dworks.apps.ataxer.entity.TaxDocument;
import dev.dworks.apps.ataxer.entity.TaxSuggestion;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaxerDatabase extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "taxer.db";

    public static final String TYPE_TEXT = " TEXT";
    public static final String TYPE_INTEGER = " INTEGER";
    public static final String TYPE_DECIMAL = " DECIMAL";
    public static final String TYPE_BOOLEAN = " BOOLEAN";
    public static final String COMMA_SEP = ",";

    public TaxerDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TaxCalculation.SQL_CREATE_TAXCALUTATIONS);
        db.execSQL(TaxDocument.SQL_CREATE_TAXDOCUMENTS);
        db.execSQL(TaxSuggestion.SQL_CREATE_TAXSUGGESTIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TaxCalculation.SQL_DELETE_TAXCALUTATIONS);
        db.execSQL(TaxDocument.SQL_DELETE_TAXDOCUMENTS);
        db.execSQL(TaxSuggestion.SQL_DELETE_TAXSUGGESTIONS);
        onCreate(db);
    }
}