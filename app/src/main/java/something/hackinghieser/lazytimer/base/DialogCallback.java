package something.hackinghieser.lazytimer.base;

import android.app.Dialog;
import android.content.DialogInterface;
import android.test.mock.MockDialogInterface;

/**
 * Created by Alexander Hieser on 11.12.2016.
 */

public interface DialogCallback {

    void yes(DialogInterface dialog);
    void no(DialogInterface dialog);
}
