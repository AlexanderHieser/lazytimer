package something.hackinghieser.lazytimer;

import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TimePicker;

import com.astuetz.PagerSlidingTabStrip;
import java.util.ArrayList;
import java.util.Calendar;

import something.hackinghieser.lazytimer.adapter.SimplePagerAdapter;
import something.hackinghieser.lazytimer.base.BaseFragment;
import something.hackinghieser.lazytimer.base.DialogCallback;
import something.hackinghieser.lazytimer.database.DB;
import something.hackinghieser.lazytimer.dialogs.AddAlarmDialog;
import something.hackinghieser.lazytimer.model.Timer;
import something.hackinghieser.lazytimer.utils.Days;

public class MainActivity extends AppCompatActivity implements AddAlarmDialog.NoticeDialogListener {

    private ImageButton addAlarm;
    private ImageButton addAlarms;

    private ImageButton dropAlarms;
    private SimplePagerAdapter adapter;
    private ViewPager pager;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pager = (ViewPager) findViewById(R.id.pager);
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        ArrayList<BaseFragment> fragments = initFragments();
        adapter = new SimplePagerAdapter(getSupportFragmentManager(),fragments);
        tabs.setTextColor(getColor(R.color.secondary_text));
        pager.setAdapter(adapter);
        tabs.setViewPager(pager);
        init();

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

        addAlarms = (ImageButton) findViewById(R.id.addAlarms);
        addAlarms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        Timer timer = new Timer();
                        BaseFragment fragment =((BaseFragment)adapter.getItem(pager.getCurrentItem()));
                        timer.Day = fragment.day;
                        timer.isActive = true;
                        timer.Clock = ""+selectedHour+":"+selectedMinute;
                        timer.sound = "Default";
                        new DB(getApplicationContext()).saveAlarm(timer);
                        fragment.doRefresh();
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        dropAlarms = (ImageButton) findViewById(R.id.drop_day);
        dropAlarms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                defaultAlartDialog("Löschen", "Alle Wecker löschen ?", new DialogCallback() {
                    @Override
                    public void yes(DialogInterface dialogInterface) {
                        BaseFragment fragment =((BaseFragment)adapter.getItem(pager.getCurrentItem()));
                        new DB(getApplicationContext()).dropWholeDay(fragment.day);
                        fragment.doRefresh();
                        dialogInterface.dismiss();
                    }

                    @Override
                    public void no(DialogInterface dialogInterface) {
                        dialogInterface.dismiss();
                    }
                });

            }
        });
    }

    private void defaultAlartDialog(String titel, String message, final DialogCallback callback) {
        AlertDialog.Builder  builder = new AlertDialog.Builder(MainActivity.this)
                .setTitle(titel)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        callback.yes(dialogInterface);
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        callback.no(dialogInterface);
                    }
                });
        builder.show();
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
