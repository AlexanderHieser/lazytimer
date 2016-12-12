package something.hackinghieser.lazytimer;

import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

public class MainActivity extends AppCompatActivity {

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
        adapter = new SimplePagerAdapter(getSupportFragmentManager(), fragments);
        tabs.setTextColor(getColor(R.color.secondary_text));
        pager.setAdapter(adapter);
        tabs.setViewPager(pager);
        init();
        showChangelog();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(getApplicationContext());
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.changelog: {
                showChangelog();
                return true;
            }
            case R.id.Options: {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 7337) {
            if (resultCode == RESULT_OK) {
                try {
                    int starthour = data.getIntExtra("starthour", 0);
                    int startmin = data.getIntExtra("startmiute", 0);
                    int endhour = data.getIntExtra("endhour", 0);
                    int endmin = data.getIntExtra("endminute", 0);
                    int intervall = data.getIntExtra("inter", 0);
                    createAlarms(starthour, startmin, endhour, endmin, intervall);
                } catch (Exception e) {

                }
            }
        }
    }

    private void init() {
        addAlarm = (ImageButton) findViewById(R.id.addAlarm);
        addAlarm.setOnClickListener(new View.OnClickListener() {
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
                        BaseFragment fragment = ((BaseFragment) adapter.getItem(pager.getCurrentItem()));
                        timer.Day = fragment.day;
                        timer.isActive = true;
                        String h, m;
                        if (String.valueOf(selectedHour).length() == 1) {
                            h = "0" + selectedHour;
                        } else {
                            h = String.valueOf(selectedHour);
                        }

                        if (String.valueOf(selectedMinute).length() == 1) {
                            m = "0" + selectedMinute;
                        } else {
                            m = String.valueOf(selectedMinute);
                        }
                        timer.Clock = "" + h + ":" + m;
                        timer.sound = "Default";
                        new DB(getApplicationContext()).saveAlarm(timer);
                        fragment.doRefresh();
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        addAlarms = (ImageButton) findViewById(R.id.addAlarms);
        addAlarms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CreateAlarmActivity.class);
                startActivityForResult(i, 7337);

            }
        });
        dropAlarms = (ImageButton) findViewById(R.id.drop_day);
        dropAlarms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                defaultAlartDialog("Löschen", "Alle Wecker löschen ?", new DialogCallback() {
                    @Override
                    public void yes(DialogInterface dialogInterface) {
                        BaseFragment fragment = ((BaseFragment) adapter.getItem(pager.getCurrentItem()));
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
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
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

    private void showChangelog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Changelog");
        builder.setView(R.layout.changelog_view);
        builder.setPositiveButton("Alles klar !", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
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


    private void createAlarms(int starthour, int startmin, int endhour, int endmin, int interv) {
        int hourdiff = endhour - starthour;
        int mindoff = endmin - startmin;

        int sum = (hourdiff * 60) + mindoff;
        int count = sum / interv;
        for (int i = 0; i < count + 1; i++) {
            Timer timer = new Timer();
            int hour = ((i * interv) / 60) + (mindoff / 60);
            String minutes;
            String hours;
            if (String.valueOf(((startmin + i * interv) - hour * 60)).length() == 1) {
                minutes = "0" + String.valueOf((startmin + i * interv) - hour * 60);
            } else {
                minutes = String.valueOf((startmin + i * interv) - hour * 60);
            }


            if (String.valueOf(starthour + hour).length() == 1) {
                hours = "0" + String.valueOf(starthour + hour);
            } else {
                hours = String.valueOf(starthour + hour);
            }

            timer.Clock = hours + ":" + minutes;

            BaseFragment fragment = ((BaseFragment) adapter.getItem(pager.getCurrentItem()));
            timer.Day = fragment.day;
            timer.isActive = true;
            timer.sound = "Default";
            new DB(getApplicationContext()).saveAlarm(timer);
            fragment.doRefresh();
        }
    }

}
