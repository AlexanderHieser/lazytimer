package something.hackinghieser.lazytimer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import something.hackinghieser.lazytimer.model.Timer;
import something.hackinghieser.lazytimer.model.TimerDB;
import something.hackinghieser.lazytimer.utils.Days;

/**
 * Created by Alexander Hieser on 03.12.2016.
 */

public class DB extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "LazyTimer.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String TYPE_INT = " INTEGER";

    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TimerDB.TimerEntry.TABLE_NAME + " (" +
                    TimerDB.TimerEntry._ID + " INTEGER PRIMARY KEY," +
                    TimerDB.TimerEntry.COLUMN_NAME_DAY + TEXT_TYPE + COMMA_SEP +
                    TimerDB.TimerEntry.COLUMN_NAME_CLOCK + TEXT_TYPE + COMMA_SEP +
                    TimerDB.TimerEntry.COLUMN_NAME_SOUND + TEXT_TYPE + COMMA_SEP +
                    TimerDB.TimerEntry.COLUMN_NAME_ACTIVE + TYPE_INT + COMMA_SEP +
                    TimerDB.TimerEntry.COLUMN_NAME_DATE + TEXT_TYPE + ")";

    public DB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        onUpgrade(sqLiteDatabase, i, i1);
    }

    public void saveAlarm(Timer timer) {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TimerDB.TimerEntry.COLUMN_NAME_DAY, timer.Day.getText());
        values.put(TimerDB.TimerEntry.COLUMN_NAME_ACTIVE, "1");
        values.put(TimerDB.TimerEntry.COLUMN_NAME_CLOCK, timer.Clock);
        values.put(TimerDB.TimerEntry.COLUMN_NAME_SOUND, timer.sound);
        long newRowId = db.insert(TimerDB.TimerEntry.TABLE_NAME, null, values);
        Log.i("DB", "Alaram saved");
    }

    public ArrayList<Timer> readAlarms(Days d) {
        Log.i("DB","Get Alarams for day: "+d.getText());
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TimerDB.TimerEntry.TABLE_NAME + " WHERE day='"+d.getText()+"';";
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<Timer> alarms = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String _id = cursor.getString(0);
                String sActive = cursor.getString(4);
                Timer timer = new Timer();
                timer.Day = getDay(cursor.getString(1));
                if (sActive.equals("1")) {
                    timer.isActive = true;
                } else {
                    timer.isActive = false;
                }
                timer.Clock = cursor.getString(2);
                timer.sound = cursor.getString(3);
                timer._id = _id;
                alarms.add(timer);
                Log.i("DB", timer.toString());

            } while (cursor.moveToNext());
        }
        Log.i("DB", ""+alarms.size());
        cursor.close();
        return alarms;
    }

    public void deleteAlarm(String id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TimerDB.TimerEntry.TABLE_NAME,"_id=?",new String[]{id});
    }

    public void updateAlarm(Timer timer) {
        ContentValues cv = new ContentValues();
        cv.put("timer",timer.Clock);
        cv.put("sound",timer.sound);
        cv.put("active",timer.isActive);
        cv.put("day",timer.Day.getText());
        SQLiteDatabase db = getWritableDatabase();
        db.update(TimerDB.TimerEntry.TABLE_NAME,cv,"_id="+timer._id,null);
    }

    public Days getDay(String s) {
        if (s.equals("Monday")) {
            return Days.MONDAY;
        }
        if (s.equals("Tuesday")) {
            return Days.TUESDAY;
        }
        if (s.equals("Wednesday")) {
            return Days.WEDNESDAY;
        }
        if (s.equals("Thursday")) {
            return Days.THURSTDAY;
        }
        if (s.equals("Friday")) {
            return Days.FRIDAY;
        }
        if (s.equals("Saturday")) {
            return Days.SATURDAY;
        }
        if (s.equals("Sunday")) {
            return Days.SUNDAY;
        }
        return Days.MONDAY;
    }
}
