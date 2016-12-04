package something.hackinghieser.lazytimer;

import android.app.DialogFragment;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.astuetz.PagerSlidingTabStrip;
import java.util.ArrayList;
import something.hackinghieser.lazytimer.adapter.SimplePagerAdapter;
import something.hackinghieser.lazytimer.base.BaseFragment;
import something.hackinghieser.lazytimer.database.DB;
import something.hackinghieser.lazytimer.dialogs.AddAlarmDialog;
import something.hackinghieser.lazytimer.model.Timer;
import something.hackinghieser.lazytimer.utils.Days;

public class MainActivity extends AppCompatActivity implements AddAlarmDialog.NoticeDialogListener {

    private ImageButton addAlarm;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        ArrayList<BaseFragment> fragments = initFragments();
        SimplePagerAdapter adapter = new SimplePagerAdapter(getSupportFragmentManager(),fragments);
        tabs.setTextColor(getColor(R.color.secondary_text));
        pager.setAdapter(adapter);
        tabs.setViewPager(pager);
        init();
        DB db = new DB(getApplicationContext());
        for(int i = 0; i < 10;i++) {
            Timer t = new Timer();
            t.Clock = "19:00";
            t.sound = "Enterprise Intro";
            t.Day = Days.MONDAY;
            t.isActive = true;
            db.saveAlarm(t);
        }

        for(int i = 0; i < 10;i++) {
            Timer t = new Timer();
            t.Clock = "18:00";
            t.sound = "Enterprise Intro";
            t.Day = Days.TUESDAY;
            t.isActive = true;
            db.saveAlarm(t);
        }

        for(int i = 0; i < 10;i++) {
            Timer t = new Timer();
            t.Clock = "18:00";
            t.sound = "Enterprise Intro";
            t.Day = Days.THURSTDAY;
            t.isActive = true;
            db.saveAlarm(t);
        }

        for(int i = 0; i < 10;i++) {
            Timer t = new Timer();
            t.Clock = "14:00";
            t.sound = "Enterprise Intro";
            t.Day = Days.SUNDAY;
            t.isActive = true;
            db.saveAlarm(t);
        }

    }

    private void init() {
        addAlarm = (ImageButton) findViewById(R.id.addAlarm);
        addAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddAlarmDialog dialog = new AddAlarmDialog();
                dialog.show(getFragmentManager(),"AddAlaram");
            }
        });
    }

    private ArrayList<BaseFragment> initFragments() {
        ArrayList<BaseFragment> list = new ArrayList<>();

        DayFragment monday = new DayFragment();
        monday.day = Days.MONDAY;
        list.add(monday);

        DayFragment tuesday = new DayFragment();
        tuesday.day = Days.TUESDAY;
        list.add(tuesday);

        DayFragment wednesday = new DayFragment();
        wednesday.day = Days.WEDNESDAY;
        list.add(wednesday);

        DayFragment thursday = new DayFragment();
        thursday.day = Days.THURSTDAY;
        list.add(thursday);

        DayFragment friday = new DayFragment();
        friday.day = Days.FRIDAY;
        list.add(friday);

        DayFragment saturday = new DayFragment();
        saturday.day = Days.SATURDAY;
        list.add(saturday);

        DayFragment sunday = new DayFragment();
        sunday.day = Days.SUNDAY;
        list.add(sunday);

        return list;
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Log.i("addAlarm","Add Alaram positive");
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        Log.i("addAlarm","Add Alaram positive");
    }
}
