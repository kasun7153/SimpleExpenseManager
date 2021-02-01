package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

public class FeedReaderDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "mini_project.db";

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        System.out.println("databse Table created");

        db.execSQL("CREATE TABLE " + "account_details" + " (" +
                "account_number" + " TEXT PRIMARY KEY," +
                "bank" + " TEXT," +
                "account_holder" + " TEXT," +
                "balance" + " REAL);");

        db.execSQL("CREATE TABLE " + "transactions" + " (" +
                "date" + " NUMERIC," +
                "accountNo" + " TEXT," +
                "expenseType" + " TEXT," +
                "amount" + " REAL);");
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        //db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}