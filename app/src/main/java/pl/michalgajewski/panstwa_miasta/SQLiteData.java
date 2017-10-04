package pl.michalgajewski.panstwa_miasta;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.session.PlaybackState;
import android.widget.Toast;

/**
 * Created by Malczan on 27.07.2017.
 */

public class SQLiteData extends SQLiteOpenHelper {

    private Context con;

    public SQLiteData(Context context) {
        super(context, "game.db", null, 1);
        con = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table users("+
             "id integer primary key autoincrement,"+
             "login text," +
              "password text);"
        );
        
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void addUser(String login, String password){
        if (!isUserExist(login)){
            SQLiteDatabase database = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("login", login);
            contentValues.put("password", password);
            database.insertOrThrow("users", null, contentValues);
        }else {
            Toast.makeText(con, "Login jest już zajęty", Toast.LENGTH_SHORT).show();
        }


    }
    public boolean checkPassword(String login, String password) {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query("users", new String[]{"login, password"}, "login=?", new String[] {login}, null, null, null, null );
        if (cursor.getCount()>0){
            return true;
        }else {
            Toast.makeText(con, "Podano błędne dane", Toast.LENGTH_SHORT).show();
            return false;
        }

    }
    public boolean isUserExist(String login){
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query("users", new String[]{"login"}, "login=?", new String[]{login}, null, null,null,null);
        return cursor.getCount() == 0 ? false : true;
    }
}
