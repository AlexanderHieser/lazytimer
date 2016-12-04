package something.hackinghieser.lazytimer.model;

import java.util.Date;

import something.hackinghieser.lazytimer.utils.Days;

/**
 * Created by Alexander Hieser on 03.12.2016.
 */

public class Timer  {

    public String Clock;
    public Date Alarm;
    public String sound;
    public boolean isActive;
    public Days Day;
    public String _id;

    @Override
    public String toString() {
        return "Timer{" +
                "Clock='" + Clock + '\'' +
                ", Alarm=" + Alarm +
                ", sound='" + sound + '\'' +
                ", isActive=" + isActive +
                ", Day=" + Day.getText() +
                '}';
    }
}
