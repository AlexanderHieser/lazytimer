package something.hackinghieser.lazytimer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import something.hackinghieser.lazytimer.R;
import something.hackinghieser.lazytimer.base.BaseFragment;

/**
 * Created by Alexander Hieser on 12.12.2016.
 */

public class TimeFragment extends TimeBaseFragment {


    private TimePicker picker;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        picker = (TimePicker) view.findViewById(R.id.timer);
        picker.setIs24HourView(true);
    }

    @Override
    public int[] getTime() {
        int minutes = picker.getMinute();
        int hours = picker.getHour();
        return new int[]{minutes, hours};
    }
}
