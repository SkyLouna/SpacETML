/**
 * ETML
 * Author: Lucas Charbonnier & Trana Valentin
 */

package ch.raylouna.spacetml.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HSDatabaseHelper extends SQLiteOpenHelper {


    private static final String TABLE_SCORES = "t_scores";                      //Table name
    private static final String COL_ID = "idScore";                             //Id col name
    private static final String COL_NAME = "scoName";                           //Name col name
    private static final String COL_AMOUNT = "scoAmount";                       //Amount col name

    private static final String CREATEDB = "CREATE TABLE " + TABLE_SCORES + " ("
                                            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                            + COL_NAME + " TEXT NOT NULL, "
                                            + COL_AMOUNT + " INTEGER );"; //Table create script

    /**
     * HSDatabaseHelper constructor
     * @param context
     */
    public HSDatabaseHelper(Context context) {
        super(context, "db_scores", null, 2);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Execute table create script
        sqLiteDatabase.execSQL(CREATEDB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        //Drop score table
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_SCORES + ";");

        //Call create database
        onCreate(sqLiteDatabase);
    }

    public long insertScore(String name, int score){

        //Create a insert row
        ContentValues values = new ContentValues();

        //add values to the row
        values.put(COL_NAME, name);
        values.put(COL_AMOUNT, score);

        return this.getWritableDatabase().insert(TABLE_SCORES, null, values);
    }

    /**
     * Remove a score by id
     * @param id
     * @return
     */
    public int removeScore(int id){
        return this.getWritableDatabase().delete(TABLE_SCORES, COL_ID + " = " +id, null);
    }

    /**
     * Gets all the scores with a limit
     * @param limit
     * @return
     */
    public Cursor getScores(int limit){
        return this.getWritableDatabase().query(TABLE_SCORES, new String[] {COL_ID, COL_NAME, COL_AMOUNT},  null, null, null, null, COL_AMOUNT + " DESC");
    }
}
