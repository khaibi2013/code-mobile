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

public class SQLiteManagerObservation extends SQLiteOpenHelper {

    private static SQLiteManagerObservation sqLiteManagerObservation;

    private static final String DATABASE_NAME = "NoteCostDB1";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Cost";
    private static final String COUNTER = "Counter";

    private static final String ID_FIELD = "id";

    private static final String TYPE_COST_FIELD = "typeCost";

    private static final String COST_FIELD = "cost";
    private static final String COMMENT_FIELD = "comment";

    private static final String DATE_TIME_FIELD = "datetime";

    private static final String NOTE_ID_FIELD = "noteId";

    private static final String DELETED_FIELD = "deleted";


    @SuppressLint("SimpleDateFormat")
    private static final DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

    public SQLiteManagerObservation(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public static SQLiteManagerObservation instanceOfDatabase(Context context)
    {
        if(sqLiteManagerObservation == null)
            sqLiteManagerObservation = new SQLiteManagerObservation(context);

        return sqLiteManagerObservation;
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
                .append(TYPE_COST_FIELD)
                .append(" TEXT, ")
                .append(COST_FIELD)
                .append(" TEXT, ")
                .append(COMMENT_FIELD)
                .append(" TEXT, ")
                .append(DATE_TIME_FIELD)
                .append(" TEXT, ")
                .append(NOTE_ID_FIELD)
                .append(" INT, ")
                .append(DELETED_FIELD)
                .append(" TEXT)");

        db.execSQL(sql.toString());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addCostToDatabase(Observation observation){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, observation.getId());
        contentValues.put(TYPE_COST_FIELD, observation.getTypeCost());
        contentValues.put(COST_FIELD, observation.getCost());
        contentValues.put(COMMENT_FIELD, observation.getComment());
        contentValues.put(DATE_TIME_FIELD, observation.getDateTime());
        contentValues.put(NOTE_ID_FIELD, observation.getNoteId());
        contentValues.put(DELETED_FIELD, getStringFromDate(observation.getDeleted()));

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);


    }

    public void populateCostsListArray(int nodeIds)
    {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        try (Cursor result = sqLiteDatabase.rawQuery( "SELECT * FROM Cost WHERE noteId =  " + String.valueOf(nodeIds),null))
        {


            Observation.listObservations.clear();
            if(result.getCount() != 0)
            {
                while (result.moveToNext())
                {
                    int id = result.getInt(1);
                    String typeCost = result.getString(2);
                    String cost = result.getString(3);
                    String comment = result.getString(4);
                    String dateTime = result.getString(5);

                    String stringDeleted = result.getString(6);
                    Date deleted = getDateFromString(stringDeleted);
                    Observation observation = new Observation(id,typeCost,cost,comment,dateTime,deleted);
                    Observation.listObservations.add(observation);
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    public void updateObservationInDB(Observation cost)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, cost.getId());
        contentValues.put(TYPE_COST_FIELD,cost.getTypeCost());
        contentValues.put(COST_FIELD, cost.getCost());
        contentValues.put(COMMENT_FIELD, cost.getComment());
        contentValues.put(DATE_TIME_FIELD, cost.getDateTime());
        contentValues.put(NOTE_ID_FIELD,cost.getNoteId());
        contentValues.put(DELETED_FIELD, getStringFromDate(cost.getDeleted()));

        sqLiteDatabase.update(TABLE_NAME, contentValues, "noteId" + " = ? AND " + "id" + " = ?" , new String[]{String.valueOf(cost.getNoteId()), String.valueOf(cost.getId())});
//        sqLiteDatabase.update(TABLE_NAME, contentValues, NOTE_ID_FIELD + " =? " , new String[]{String.valueOf(cost.getNoteId())});
    }


    public void deleteCost(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + NOTE_ID_FIELD + "=?"  ,new String[]{String.valueOf(id)});
        db.close();
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
