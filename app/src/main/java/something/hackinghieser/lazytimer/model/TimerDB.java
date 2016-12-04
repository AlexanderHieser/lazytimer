package something.hackinghieser.lazytimer.model;

import android.provider.BaseColumns;

/**
 * Created by Alexander Hieser on 03.12.2016.
 */

public final class TimerDB  {

    private TimerDB() {};


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TimerEntry.TABLE_NAME;

    public static class TimerEntry implements BaseColumns {
        public static final String TABLE_NAME = "timer";
        public static final String COLUMN_NAME_DAY = "day";
        public static final String COLUMN_NAME_CLOCK = "timer";
        public static final String COLUMN_NAME_SOUND = "sound";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_ACTIVE = "active";
    }
}
