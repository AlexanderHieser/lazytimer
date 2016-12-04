package something.hackinghieser.lazytimer.base;

import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import something.hackinghieser.lazytimer.R;
import something.hackinghieser.lazytimer.utils.Days;

/**
 * Created by Alexander Hieser on 03.12.2016.
 */

public abstract class BaseFragment extends Fragment {


    public BaseFragment() {
        day = Days.MONDAY;
    }


    public String getTitel() {
        switch (day) {
            case MONDAY: return "Montag";
            case TUESDAY: return "Dienstag";
            case WEDNESDAY: return "Mittwoch";
            case THURSTDAY: return "Donnerstag";
            case FRIDAY: return "Freitag";
            case SATURDAY: return "Samstag";
            case SUNDAY: return "Sonntag";
            default: return "Best day ever";
        }
    }

    public Days day;


}
