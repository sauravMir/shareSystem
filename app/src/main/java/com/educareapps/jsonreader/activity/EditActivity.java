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



}
