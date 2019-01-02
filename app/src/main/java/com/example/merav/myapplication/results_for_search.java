package com.example.merav.myapplication;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import static android.provider.Contacts.SettingsColumns.KEY;

public class results_for_search extends AppCompatActivity {

    private static final String TAG = "results_for_search";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_for_search);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Watch&Learn");

        Intent intent=getIntent();
        ArrayList<teacher> teachers_list;
        teachers_list = (ArrayList<teacher>) intent.getSerializableExtra("KEY");

//        Toast.makeText(results_for_search.this,""+teachers_list.get(0).getName()+"  blabla", Toast.LENGTH_LONG).show();

        Log.d(TAG, "oCreate: started.");
        initImageBitmaps(teachers_list);

    }
    private void initImageBitmaps(ArrayList<teacher> teachers_list){
        Log.d(TAG,"initImageBitmaps: preparong bitmaps.");
        initRecycleriew(teachers_list);
    }

    private void initRecycleriew(ArrayList<teacher> teachers_list){
        Log.d(TAG,"initRecyclerView: init recyclerview.");
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerViewAdapter adapter = new recyclerViewAdapter(this,teachers_list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
    }
}