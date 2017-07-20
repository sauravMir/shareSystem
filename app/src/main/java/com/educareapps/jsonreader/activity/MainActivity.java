package com.educareapps.jsonreader.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.educareapps.jsonreader.R;
import com.educareapps.jsonreader.manager.DatabaseManager;
import com.educareapps.jsonreader.manager.IDatabaseManager;
import com.educareapps.jsonreader.share.Share;
import com.educareapps.jsonreader.utilitis.StaticAccess;
import com.limbika.material.dialog.FileDialog;
import com.limbika.material.dialog.SelectorDialog;

import java.io.IOException;

public class MainActivity extends Activity implements View.OnClickListener{
ImageButton ibtnImportId,ibtnShareId;
    MainActivity activity;
    ProgressDialog pDialog;
    Share share;
    private IDatabaseManager databaseManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity=this;
        share = new Share(activity);
        databaseManager=new DatabaseManager(activity);
        ibtnImportId=(ImageButton)findViewById(R.id.ibtnImportId);
        ibtnShareId=(ImageButton)findViewById(R.id.ibtnShareId);


        ibtnImportId.setOnClickListener(this);
        ibtnShareId.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ibtnImportId:

                FileDialog fileDialog = new FileDialog(activity, FileDialog.Strategy.FILE);
                fileDialog.setCancelable(false);
                fileDialog.show();
                fileDialog.setOnSelectListener(new SelectorDialog.OnSelectListener() {
                    @Override
                    public void onSelect(String filePath) {
                        if (filePath != null && filePath.endsWith(StaticAccess.DOT_JSON)) {
                            new JsonReading(filePath).execute();
                        } else{
                            Toast.makeText(activity, "no file path", Toast.LENGTH_LONG).show();
                        }

                        Toast.makeText(activity, filePath, Toast.LENGTH_LONG).show();
                    }
                });
                break;
              case R.id.ibtnShareId:
                break;

        }
    }

  private   class JsonReading extends AsyncTask<String, String, String> {
        String filePath;


        public JsonReading(String filePath) {
            this.filePath = filePath;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(activity);
            pDialog.setMessage(getResources().getString(R.string.importPdialog));
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {

            share.readSharedTaskPackJSONtoDatabase(filePath);
//            share.deleteReceivedFolder();

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }

        }

    }


    // delete all data


}
