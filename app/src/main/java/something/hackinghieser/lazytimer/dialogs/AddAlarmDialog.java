package something.hackinghieser.lazytimer.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import something.hackinghieser.lazytimer.R;

/**
 * Created by Alexander Hieser on 04.12.2016.
 */

public class AddAlarmDialog extends DialogFragment {

    public interface NoticeDialogListener {
        void onDialogPositiveClick(DialogFragment dialog,String start, String end, String intervall);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    NoticeDialogListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_add_alarms,null,false);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.intervall_check);
        final TextView time = (TextView) view.findViewById(R.id.time);
        final EditText start = (EditText) view.findViewById(R.id.start_time);
        final EditText end = (EditText) view.findViewById(R.id.end_time);
        final EditText editText = (EditText) view.findViewById(R.id.intervall_edit);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    time.setVisibility(View.VISIBLE);
                    editText.setVisibility(View.VISIBLE);
                }else {
                    time.setVisibility(View.GONE);
                    editText.setVisibility(View.GONE);
                }
            }
        });
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("Übernehmen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogPositiveClick(AddAlarmDialog.this,start.getText().toString(),end.getText().toString(),editText.getText().toString());
                    }
                })
                .setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogNegativeClick(AddAlarmDialog.this);
                        AddAlarmDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface

    }
 }
