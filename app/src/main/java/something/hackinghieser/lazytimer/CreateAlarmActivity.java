package something.hackinghieser.lazytimer;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;

import something.hackinghieser.lazytimer.adapter.SimplePagerAdapter;
import something.hackinghieser.lazytimer.adapter.SimpleTimePagerAdapter;
import something.hackinghieser.lazytimer.fragments.TimeBaseFragment;
import something.hackinghieser.lazytimer.fragments.TimeFragment;

public class CreateAlarmActivity extends AppCompatActivity {

    private TimePicker end;
    private TimePicker start;
    private Button save;
    private NumberPicker np;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_alarm);
        init();
    }

    private void init() {
        np = (NumberPicker) findViewById(R.id.intervall_picker);
        np.setMaxValue(59);
        np.setMinValue(1);
        start = (TimePicker) findViewById(R.id.start);
        end = (TimePicker) findViewById(R.id.end);
        start.setIs24HourView(true);
        end.setIs24HourView(true);
        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.putExtra("starthour",start.getHour());
                i.putExtra("startminute",start.getMinute());
                i.putExtra("endhour",end.getHour());
                i.putExtra("endminute",end.getMinute());
                i.putExtra("inter", np.getValue());
                setResult(RESULT_OK,i);
                finish();
            }
        });
    }


}
