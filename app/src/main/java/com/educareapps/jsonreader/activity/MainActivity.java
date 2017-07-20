package com.educareapps.jsonreader.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.educareapps.jsonreader.R;
import com.educareapps.jsonreader.adapter.ItemAdapter;
import com.educareapps.jsonreader.dao.Item;
import com.educareapps.jsonreader.dao.Task;
import com.educareapps.jsonreader.dao.TaskPack;
import com.educareapps.jsonreader.manager.DatabaseManager;
import com.educareapps.jsonreader.manager.IDatabaseManager;
import com.educareapps.jsonreader.share.Share;
import com.educareapps.jsonreader.utilitis.DialogNavBarHide;
import com.educareapps.jsonreader.utilitis.StaticAccess;
import com.limbika.material.dialog.FileDialog;
import com.limbika.material.dialog.SelectorDialog;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class MainActivity extends Activity implements View.OnClickListener {
    ImageButton ibtnImportId, ibtnShareId;
    MainActivity activity;
    ProgressDialog pDialog;
    Share share;
    private IDatabaseManager databaseManager;
    ItemAdapter itemAdapter;
    ArrayList<TaskPack> allTaskPackArr;
    ArrayList<Item> allItemArr;
    ListView lvItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        share = new Share(activity);
        databaseManager = new DatabaseManager(activity);
        ibtnImportId = (ImageButton) findViewById(R.id.ibtnImportId);
        ibtnShareId = (ImageButton) findViewById(R.id.ibtnShareId);
        lvItem = (ListView) findViewById(R.id.lvItem);
        reloadItems();

        ibtnImportId.setOnClickListener(this);
        ibtnShareId.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtnImportId:
//                taskPackDelete();
                new DeleteAsync().execute();

                break;
            case R.id.ibtnShareId:
                break;

        }
    }

    private class JsonReading extends AsyncTask<String, String, String> {
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
            reloadItems();

        }

    }

    private class DeleteAsync extends AsyncTask<String, String, String> {


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
            taskPackDelete();


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
            reloadItems();

            FileDialog fileDialog = new FileDialog(activity, FileDialog.Strategy.FILE);
            fileDialog.setCancelable(false);
            fileDialog.show();
            fileDialog.setOnSelectListener(new SelectorDialog.OnSelectListener() {
                @Override
                public void onSelect(String filePath) {
                    if (filePath != null && filePath.endsWith(StaticAccess.DOT_JSON.toUpperCase())) {
                        new JsonReading(filePath).execute();
                    } else {
                        Toast.makeText(activity, "no file path", Toast.LENGTH_LONG).show();
                    }

                    Toast.makeText(activity, filePath, Toast.LENGTH_LONG).show();
                }
            });

        }

    }


    // delete all data
    public void taskPackDelete() {
        allTaskPackArr = new ArrayList<>();
        allTaskPackArr = (ArrayList<TaskPack>) databaseManager.listTaskPacks();
        if (allTaskPackArr != null) {
            for (TaskPack taskPack : allTaskPackArr) {

                ArrayList<Task> tasks = (ArrayList<Task>) databaseManager.listTasksByTAskPackId(taskPack.getId());
                if (tasks != null) {
                    for (Task task : tasks) {
                        LinkedHashMap<Long, Item> items = databaseManager.loadTaskWiseItem(task);
                        if (items != null) {
                            for (Map.Entry<Long, Item> itemValue : items.entrySet()) {
                                Item item = itemValue.getValue();
                                databaseManager.deleteItemById(item.getId());
                            }
                        }
                        databaseManager.deleteTaskById(task.getId());
                    }
                }
                databaseManager.deleteTaskPackById(taskPack.getId());
            }
        }


        //fabMenu.close(true);

    }

    private void reloadItems() {
        allItemArr = new ArrayList<>();
        allItemArr = (ArrayList<Item>) databaseManager.listItems();

        itemAdapter = new ItemAdapter(activity, allItemArr);
        lvItem.setAdapter(itemAdapter);
        itemAdapter.notifyDataSetChanged();
        lvItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                allItemArr.get(position);
                

            }
        });

    }



}
