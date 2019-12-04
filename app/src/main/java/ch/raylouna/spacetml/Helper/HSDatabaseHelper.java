package ch.raylouna.spacetml.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class HSDatabaseHelper extends SQLiteOpenHelper {


    private static final String TABLE_SCORES = "t_scores";
    private static final String COL_ID = "idScore";
    private static final String COL_NAME = "scoName";
    private static final String COL_AMOUNT = "scoAmount";

    private static final String CREATEDB = "CREATE TABLE " + TABLE_SCORES + " ("
                                            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                            + COL_NAME + " TEXT NOT NULL, "
                                            + COL_AMOUNT + " INTEGER );";

    public HSDatabaseHelper(Context context) {
        super(context, "db_scores", null, 2);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATEDB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_SCORES + ";");
        onCreate(sqLiteDatabase);
    }

    public long insertScore(String name, int score){
        ContentValues values = new ContentValues();

        values.put(COL_NAME, name);
        values.put(COL_AMOUNT, score);

        return this.getWritableDatabase().insert(TABLE_SCORES, null, values);
    }

    public int removeScore(int id){
        return this.getWritableDatabase().delete(TABLE_SCORES, COL_ID + " = " +id, null);
    }

    public Cursor getScores(int limit){
        return this.getWritableDatabase().query(TABLE_SCORES, new String[] {COL_ID, COL_NAME, COL_AMOUNT},  null, null, null, null, COL_AMOUNT + " DESC");
    }
}
