package com.indapp.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;


import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;


public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    // Database Name
//    private static final String DATABASE_NAME = "islamicnames_db.sqlite";

    // Contacts table name
    private static final String TABLE_CONTACTS = "boomark";
    private static final String TABLE_FAV = "favourite";

    // Contacts Table Columns names
    private static final String KEY_ID = "rowid";
    private static final String KEY_PAGENO = "pageno";
    private static final String KEY_TITLE = "title";


    public static String DB_NAME = "quran_v19.sqlite";
//private static String DB_NAME = "test.txt";
    public static String DB_PATH = Environment.getExternalStorageDirectory() + "/appdb/";//"/sdcard/appdb/";
    Context context;

    public DatabaseHandler(Context context) {
//        super(context, "/sdcard/iname/islamicnames_db.sqlite", null, DATABASE_VERSION);

        super(context, DB_NAME, null, 1);
        this.context = context;
        boolean dbexist = checkdatabase(context);
        Log.v(Constants1.TAG,"dbexist----------->"+dbexist);
//        if (dbexist)
        {
            System.out.println("Database exists");
            Log.v(Constants1.TAG,"Database exists");
            opendatabase(context);
        }
//        else
//            {
//            System.out.println("Database doesn't exist");
//            Log.v(Constants1.TAG,"Database doesn't exist");
//            createdatabase(context);
//        }

    }

    public void createdatabase(Context context) {
        try {


            boolean dbexist = false;;//checkdatabase(context);
            if (dbexist) {
                System.out.println(" Database exists.");
            } else {
                this.getReadableDatabase();
                try {
                    copydatabase();
                } catch (Exception e) {
                    throw new Error("Error copying database");
                }
            }
        } catch (Exception e) {
            Log.v(Constants1.TAG, "Database--------->" + e);
        }

    }

    private boolean checkdatabase(Context context) {

//        boolean checkdb = false;
//        try {
//            String myPath = DB_PATH + DB_NAME;
//            Log.v(Constants1.TAG,"myPath----"+myPath);
//            File dbfile = new File(myPath);
//            checkdb = dbfile.exists();
//        } catch (SQLiteException e) {
//            System.out.println("Database doesn't exist");
//        }
//        return checkdb;

        boolean checkdb = false;
        try {
            File mainDire=context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS+"/MyFiles/"+ DB_NAME);
            checkdb = mainDire.exists();
        } catch (SQLiteException e) {
            System.out.println("Database doesn't exist");
        }
        return checkdb;
    }

    private void copydatabase() {

        try {

            Toast.makeText(context,"Initiated",Toast.LENGTH_SHORT).show();
            File mainDire = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS + "/MyFiles/");
            if (!mainDire.exists()) {
                mainDire.mkdirs();
                Log.v(Constants1.TAG, "directory created------------------------>");
            } else {
                Log.v(Constants1.TAG, "directory exist------------------------>");
            }
            File subDire = new File(mainDire.getAbsolutePath(),DB_NAME);
            subDire.createNewFile();
            Log.v(Constants1.TAG,"SubDir------>"+subDire.getPath());
            if(subDire.isDirectory())
            {
                Log.v(Constants1.TAG,"Directory Created----------->");
            }
            else
            {
                Log.v(Constants1.TAG,"Directory Not Created------->");
            }
//        if(!subDire.exists())
//        {
//
//            subDire.mkdirs();
//            Log.v(Constants1.TAG,"myfiles created------------------------>");
//        }
//        else
//        {
//            Log.v(Constants1.TAG,"myfiles Exist------------------------>");
//        }
//        Open your local db as the input stream
        InputStream myinput = context.getAssets().open(DB_NAME);
        // Path to the just created empty db
        String outfilename = DB_PATH + DB_NAME;
        //Open the empty db as the output stream
        FileOutputStream myoutput = new FileOutputStream(subDire);

        // transfer byte to inputfile to outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myinput.read(buffer)) > 0) {
            myoutput.write(buffer, 0, length);
        }
        //Close the streams
        myoutput.flush();
        myoutput.close();
        myinput.close();
        Log.v(Constants1.TAG,"COMPLETED--------->");
            Toast.makeText(context,"CREATED",Toast.LENGTH_LONG).show();
        }
        catch (Exception e)
        {
            Log.v(Constants1.TAG,"Error in Copying DB----->"+e);
            Toast.makeText(context,""+e,Toast.LENGTH_LONG).show();
        }


    }


    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    public void addData() {

//	 DatabaseHandler db=new DatabaseHandler(t);

//	 addContact_Fav(new Contact("", "Al-Fatiha |  سورة الفاتحة   |2","Al-Fatiha |  سورة الفاتحة   |2"));
//     addContact_Fav(new Contact("", "Al-Kahf |  سورة الكهف   |294","Al-Kahf |  سورة الكهف   |294"));
//     addContact_Fav(new Contact("", "Al-Mulk |  سورة الملك   |563","Al-Mulk |  سورة الملك   |563"));
//     addContact_Fav(new Contact("", "Al-Mulk |  سورة الملك   |563","Al-Mulk |  سورة الملك   |563"));

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
//
//        // Create tables again
//        onCreate(db);
    }

    public SQLiteDatabase opendatabase(Context context) throws SQLException {
        //Open the database
        String mypath = DB_PATH + DB_NAME;



        Log.v(Constants1.TAG,"Database Path----->"+context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)+"/MyFiles/"+ DB_NAME);
//        File mainDire=;
        return SQLiteDatabase.openDatabase(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)+"/MyFiles/"+ DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
//        return SQLiteDatabase.openDatabase(mainDire, null, SQLiteDatabase.OPEN_READWRITE)
    }


    // Deleting single contact
    public void deleteContact(String ROWID) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[]{ROWID});
        db.close();
    }

    public void deleteExistingBookmark(String pageNO) {
////        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
////        		+KEY_PAGENO + " TEXT,"
////                + KEY_TITLE + " TEXT" + ")";
//        
//        
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_PAGENO + " = ?",
                new String[]{pageNO});
        db.close();
    }

    public void deleteContact_Fav(String ROWID) {
        SQLiteDatabase db = opendatabase(context);//getWritableDatabase();
        db.delete(TABLE_FAV, KEY_PAGENO + " = ?",
                new String[]{ROWID});
        db.close();
    }

    // Getting contacts Count
    public int getContactsCount(String no) {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS + " where " + KEY_PAGENO + " = '" + no + "'";
        SQLiteDatabase db = opendatabase(context);
        ;//getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        // return count
        return count;
    }


    public Cursor getName(String CATEGORY) {
        String countQuery = "SELECT  * FROM namelist where CATEGORY_ID in ('" + CATEGORY + "')";
        SQLiteDatabase db = opendatabase(context);
        ;//getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        return cursor;
    }

    public Cursor getNameBySRs(String SRs) {
        String countQuery = "SELECT  * FROM namelist where SR in (" + SRs + ")";
        SQLiteDatabase db = opendatabase(context);
        ;//getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor;
    }

    public Cursor getNamebySearch(String serachText) {
        String countQuery = "SELECT  * FROM namelist where Name like '%" + serachText + "%'";
        SQLiteDatabase db = opendatabase(context);
        ;//getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor;
    }

    public int updateFavourite(String query,SQLiteDatabase db)
    {
        ;;//getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();
        return  count;
    }

    public Cursor getData(String query, SQLiteDatabase db) {


        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
//                Toast.makeText(activityName.this, "Table Name=> "+c.getString(0), Toast.LENGTH_LONG).show();
                Log.v(Constants1.TAG,""+ "Table Name=> "+c.getString(0));
                c.moveToNext();
            }
        }




//        SQLiteDatabase db = opendatabase();;//getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();
        // return count
        return cursor;
    }


}
