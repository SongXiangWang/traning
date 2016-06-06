package tw.edu.itu.traning;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by kaden on 2016/6/6.
 */
public class SqliteDB extends SQLiteOpenHelper {
    public SqliteDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion>=newVersion)return;

        Cursor cur=db.rawQuery("Select name from sqlite_master where type='table'",null);
        cur.moveToFirst();
        if(cur.getCount()>0){
            do{
                Log.d("myApp","Drop Table:" + cur.getString(0));
                if(!cur.getString(0).equals("android_metadata") && !cur.getString(0).equals("sqlite_sequence"))
                    db.execSQL("drop table if exists " + cur.getString(0));
            } while(cur.moveToNext());
        }

        cur.close();
        createTables(db);
    }

    private void createTables(SQLiteDatabase db){
        String sql = "CREATE TABLE IF NOT EXISTS [bus] ("
                + "[_id] INTEGER PRIMARY KEY autoincrement,"
                + "[LineNo] INTEGER,"
                + "[Company] VARCHAR(50),"
                + "[BusInfo] VARCHAR(100),"
                + "[Time] INTEGER,"
                + "[EatTime] VARCHAR(50))"
                ;
        db.execSQL(sql);
    }

    public void execSQL(String sql){
        this.getWritableDatabase().execSQL(sql);
    }

    public void Insert(String tbName,ContentValues values){
        this.getWritableDatabase().insertOrThrow(tbName, null, values);
    }

    public void Delete(String tbName,long key){
        this.getWritableDatabase().delete(tbName, "_id="+ key, null);
    }

    public void Update(String tbName, ContentValues values, String whereClause){
        this.getWritableDatabase().update(tbName, values, whereClause, null);
    }

    public Cursor query(String sql, String[] args)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, args);

        return cursor;
    }

}
