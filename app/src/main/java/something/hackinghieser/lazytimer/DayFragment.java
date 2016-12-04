package something.hackinghieser.lazytimer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import something.hackinghieser.lazytimer.adapter.TimerItemAdapter;
import something.hackinghieser.lazytimer.base.BaseFragment;
import something.hackinghieser.lazytimer.database.DB;
import something.hackinghieser.lazytimer.model.Timer;

/**
 * Created by Alexander Hieser on 03.12.2016.
 */

public class DayFragment extends BaseFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_template,container,false);
        init(view);
        return view;
    }

    private void init(View view) {
        RecyclerView list = (RecyclerView) view.findViewById(R.id.list);
        TimerItemAdapter adapter = new TimerItemAdapter(createMock());
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setAdapter(adapter);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createMock();
    }

    private ArrayList<Timer>  createMock() throws NullPointerException {
        if(day != null) {
            return new DB(getActivity()).readAlarms(day);
        }else throw new NullPointerException("Day is null");
    }
}
