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

public class SQLiteManagerCost extends SQLiteOpenHelper {

    private static SQLiteManagerCost sqLiteManagerCost;

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

    public SQLiteManagerCost(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public static SQLiteManagerCost instanceOfDatabase(Context context)
    {
        if(sqLiteManagerCost == null)
            sqLiteManagerCost = new SQLiteManagerCost(context);

        return sqLiteManagerCost;
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

    public void addCostToDatabase(Costs costs){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, costs.getId());
        contentValues.put(TYPE_COST_FIELD,costs.getTypeCost());
        contentValues.put(COST_FIELD, costs.getCost());
        contentValues.put(COMMENT_FIELD, costs.getComment());
        contentValues.put(DATE_TIME_FIELD, costs.getDateTime());
        contentValues.put(NOTE_ID_FIELD,costs.getNoteId());
        contentValues.put(DELETED_FIELD, getStringFromDate(costs.getDeleted()));

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);


    }

    public void populateCostsListArray(int nodeIds)
    {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        try (Cursor result = sqLiteDatabase.rawQuery( "SELECT * FROM Cost WHERE noteId =  " + String.valueOf(nodeIds),null))
        {


            Costs.listCosts.clear();
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
                    Costs costs = new Costs(id,typeCost,cost,comment,dateTime,deleted);
                    Costs.listCosts.add(costs);
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    public void updateCostInDB(Costs cost)
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
