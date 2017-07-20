package com.educareapps.jsonreader.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.educareapps.jsonreader.R;
import com.educareapps.jsonreader.utilitis.DialogNavBarHide;

/**
 * Created by RK-REAZ on 7/20/2017.
 */

public class EditActivity extends Activity {
    EditActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        activity = this;
    }


    public void dialogForText() {
        final Dialog dialog = new Dialog(activity, R.style.CustomAlertDialog);
        dialog.setContentView(R.layout.dialog_edit_text_mode);
        dialog.setCancelable(false);

        final EditText edtTextEdit = (EditText) dialog.findViewById(R.id.edtTextEdit);
        final Button btnCancelEdit = (Button) dialog.findViewById(R.id.btnCancelEdit);
        final Button btnOkEdit = (Button) dialog.findViewById(R.id.btnOkEdit);



        btnCancelEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnOkEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtTextEdit.getText().toString().length() == 0) {
                    Toast.makeText(activity, "Please Add Text", Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog.dismiss();
            }
        });
        DialogNavBarHide.navBarHide(activity, dialog);

    }
}
