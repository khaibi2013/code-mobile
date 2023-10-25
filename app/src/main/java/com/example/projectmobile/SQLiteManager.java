package com.example.projectmobile;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SQLiteManager extends SQLiteOpenHelper {

    SQLiteDatabase db;
    private static SQLiteManager sqLiteManager;

    private static final String DATABASE_NAME = "NoteTheTripDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Note";
    private static final String COUNTER = "Counter";

    private static final String ID_FIELD = "id";
    private static final String NAME_FIELD = "name";

    private static final String DESTINATION_FIELD = "destination";
    private static final String DESC_FIELD = "desc";

    private static final String PURPOSE_FIELD = "purpose";

    private static final String RISKY_FIELD = "risky";
    private static final String DATE_TIME_FIELD = "datetime";

    private static final String TIME_FIELD = "time";
    private static final String DELETED_FIELD = "deleted";


    @SuppressLint("SimpleDateFormat")
    private static final DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

    public SQLiteManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public static SQLiteManager instanceOfDatabase(Context context)
    {
        if(sqLiteManager == null)
            sqLiteManager = new SQLiteManager(context);

        return sqLiteManager;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder sql;
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append("(")
                .append(COUNTER)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ID_FIELD)
                .append(" INT, ")
                .append(NAME_FIELD)
                .append(" TEXT, ")
                .append(DESTINATION_FIELD)
                .append(" TEXT, ")
                .append(DESC_FIELD)
                .append(" TEXT, ")
                .append(PURPOSE_FIELD)
                .append(" TEXT, ")
                .append(RISKY_FIELD)
                .append(" TEXT, ")
                .append(DATE_TIME_FIELD)
                .append(" TEXT, ")
                .append(TIME_FIELD)
                .append(" TEXT, ")
                .append(DELETED_FIELD)
                .append(" TEXT)");

        db.execSQL(sql.toString());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addTripIntoDatabase(Trip note){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, note.getId());
        contentValues.put(NAME_FIELD, note.getTitle());
        contentValues.put(DESTINATION_FIELD, note.getDestination());
        contentValues.put(DESC_FIELD, note.getDescription());
        contentValues.put(PURPOSE_FIELD, note.getPurpose());
        contentValues.put(RISKY_FIELD, note.getGroupRisky());
        contentValues.put(DATE_TIME_FIELD, note.getDatetime());
        contentValues.put(TIME_FIELD, note.getTime());
        contentValues.put(DELETED_FIELD, getStringFromDate(note.getDeleted()));

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

    }



    public void searchNote(String keyword)
    {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();


        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM "+ TABLE_NAME + " WHERE "+ NAME_FIELD +  " LIKE ? "  , new String[] { "%" + keyword + "%" } ))
        {

            Trip.searchNotes.clear();
            if(result.getCount() != 0)
            {
                while (result.moveToNext())
                {
                    int id = result.getInt(1);
                    String title = result.getString(2);
                    String destination = result.getString(3);
                    String desc = result.getString(4);
                    String purpose = result.getString(5);
                    String risk = result.getString(6);
                    String date = result.getString(7);
                    String time = result.getString(8);
                    String stringDeleted = result.getString(9);
                    Date deleted = getDateFromString(stringDeleted);
                    Trip note = new Trip(id,title,destination,desc,purpose,risk,date,time,deleted);
                    Trip.searchNotes.add(note);
                }
            }
        }
    }


    public void showTripList()
    {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME , null))
        {

            Trip.notes.clear();
            if(result.getCount() != 0)
            {
                while (result.moveToNext())
                {
                    int id = result.getInt(1);
                    String title = result.getString(2);
                    String destination = result.getString(3);
                    String desc = result.getString(4);
                    String purpose = result.getString(5);
                    String risk = result.getString(6);
                    String date = result.getString(7);
                    String time = result.getString(8);
                    String stringDeleted = result.getString(9);
                    Date deleted = getDateFromString(stringDeleted);
                    Trip note = new Trip(id,title,destination,desc,purpose,risk,date,time,deleted);
                    Trip.notes.add(note);
                }
            }
        }
    }

    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME); //delete all rows in a table
        db.close();
    }

    public void deleteATrip(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + ID_FIELD + "=?"  ,new String[]{String.valueOf(id)});
        db.close();
    }

    public void updateTripDB(Trip note)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, note.getId());
        contentValues.put(NAME_FIELD, note.getTitle());
        contentValues.put(DESTINATION_FIELD, note.getDestination());
        contentValues.put(DESC_FIELD, note.getDescription());
        contentValues.put(PURPOSE_FIELD, note.getPurpose());
        contentValues.put(RISKY_FIELD, note.getGroupRisky());
        contentValues.put(DATE_TIME_FIELD, note.getDatetime());
        contentValues.put(TIME_FIELD, note.getTime());
        contentValues.put(DELETED_FIELD, getStringFromDate(note.getDeleted()));


        sqLiteDatabase.update(TABLE_NAME, contentValues, ID_FIELD + " =? ", new String[]{String.valueOf(note.getId())});
    }





    private String getStringFromDate(Date date)
    {
        if(date == null)
            return null;
        return dateFormat.format(date);
    }

    private Date getDateFromString(String string)
    {
        try
        {
            return dateFormat.parse(string);
        }
        catch (ParseException | NullPointerException e)
        {
            return null;
        }
    }

}
