package something.hackinghieser.lazytimer;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ViewPropertyAnimatorCompatSet;

public class AlarmWizardActivity extends AppCompatActivity {

    private ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_wizard);
        init();
    }

    private void init() {
        viewpager = (ViewPager) findViewById(R.id.wizard_pager);
    }
}
