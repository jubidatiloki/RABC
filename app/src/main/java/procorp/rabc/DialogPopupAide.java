package procorp.rabc;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by benja on 28/02/2018.
 */

public class DialogPopupAide extends DialogFragment {

    AlertDialog.Builder builder;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        builder = new AlertDialog.Builder(getActivity(), R.style.MyDialog);
        String tag = getTag();
        switch (tag){
            case "tag":
                tagHelper();
                break;
            case "ing":
                ingHelper();
                break;
            default:
                break;
        }


        return builder.create();
    }

    public void tagHelper(){
        builder.setTitle(R.string.aide_tag_title);
        builder.setMessage(R.string.aide_tag_msg);
    }

    public void ingHelper(){
        builder.setTitle(R.string.aide_ing_title);
        builder.setMessage(R.string.aide_ing_msg);
    }

    private final class okOnClickListener implements  DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int id) {

        }
    }
}

