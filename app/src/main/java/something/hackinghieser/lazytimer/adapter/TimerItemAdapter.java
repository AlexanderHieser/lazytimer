package something.hackinghieser.lazytimer.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import something.hackinghieser.lazytimer.R;
import something.hackinghieser.lazytimer.database.DB;
import something.hackinghieser.lazytimer.model.Timer;

/**
 * Created by Alexander Hieser on 03.12.2016.
 */

public class TimerItemAdapter extends RecyclerView.Adapter<TimerItemAdapter.TimerViewHolder> {

    private ArrayList<Timer> source;

    public TimerItemAdapter(ArrayList<Timer> source) {
        this.source = source;
    }

    @Override
    public TimerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_item, null, false);
        return new TimerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TimerViewHolder holder, int position) {
        holder.bind(source.get(position));
    }

    @Override
    public int getItemCount() {
        return source.size();
    }


    class TimerViewHolder extends RecyclerView.ViewHolder {

        private TextView Clock;
        private TextView Sound;
        private ImageButton ClockButton;
        private ImageButton CloseButton;
        private ImageButton SoundButton;

        public TimerViewHolder(View itemView) {
            super(itemView);
            Clock = (TextView) itemView.findViewById(R.id.clock);
            Sound = (TextView) itemView.findViewById(R.id.sound);
            ClockButton = (ImageButton) itemView.findViewById(R.id.clockButton);
            CloseButton = (ImageButton) itemView.findViewById(R.id.closeButton);
            SoundButton = (ImageButton) itemView.findViewById(R.id.soundButton);
        }

        public void bind(final Timer timer) {
            Clock.setText(timer.Clock);
            Sound.setText(timer.sound);

            if (timer.isActive) {
                ClockButton.setImageDrawable(itemView.getContext().getDrawable(R.drawable.clock_active));
            } else {
                ClockButton.setImageDrawable(itemView.getContext().getDrawable(R.drawable.clock_inactive));
            }

            CloseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new DB(itemView.getContext()).deleteAlarm(timer._id);
                    source.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                }
            });

            ClockButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    timer.isActive = !timer.isActive;
                    new DB(itemView.getContext()).updateAlarm(timer);
                    notifyItemChanged(getAdapterPosition());
                }
            });

            SoundButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Tone");
                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, (Uri) null);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
