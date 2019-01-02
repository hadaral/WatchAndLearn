package com.example.merav.myapplication;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.view.View.OnClickListener;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;

import static android.provider.Contacts.SettingsColumns.KEY;


public class activity_serch extends AppCompatActivity {

    ConstraintLayout myLayout;
    AnimationDrawable animationDrawable;

    Button start_searching;
    RadioGroup radio_search_group;
    RadioButton radio_by_teacher;
    RadioButton radio_by_price;
    RadioButton radio_by_profession;
    RadioButton radio_by_area;

    RadioButton choice;
    EditText input;
//    RadioButton name;
//    RadioButton price;
//    RadioButton profession;
//    RadioButton area;

    private DatabaseReference teacher_database;

    private RecyclerView mResultList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serch);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Watch&Learn");

        myLayout = (ConstraintLayout) findViewById(R.id.myLayout);

        animationDrawable = (AnimationDrawable) myLayout.getBackground();
        animationDrawable.start();

        teacher_database=FirebaseDatabase.getInstance().getReference("Teachers");

        addListenerOnButton();
    }

    public void addListenerOnButton() {

        radio_search_group = (RadioGroup) findViewById(R.id.radio_search_group);
        radio_by_teacher = (RadioButton) findViewById(R.id.radio_by_teacher);
        radio_by_price =(RadioButton) findViewById(R.id.radio_by_price);
        radio_by_profession = (RadioButton) findViewById(R.id.radio_by_profession);
        radio_by_area = (RadioButton) findViewById(R.id.radio_by_area);
        start_searching = (Button) findViewById(R.id.button_start_search);
        input = (EditText) findViewById(R.id.edit_choice);

        start_searching.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
                int selectedId = radio_search_group.getCheckedRadioButtonId();

                // find the radiobutton by returned id

                String textEntered = input.getText().toString();
                choice = (RadioButton) findViewById(selectedId);
//                int choiceNum = choice.getId() ;

                if (input.getText().toString().equals("") || selectedId==-1 ){
                    Toast.makeText(activity_serch.this,"Please choose first", Toast.LENGTH_LONG).show();
                }
                if (selectedId==radio_by_teacher.getId()){
                    searchForTeacher(textEntered,"name_lower");
                }
                else if(selectedId == radio_by_price.getId()){
                    searchForTeacher(textEntered,"cost");
                }
                else if(selectedId == radio_by_profession.getId()){
                    searchForTeacher(textEntered,"profession_lower");
                }
                else if(selectedId == radio_by_area.getId()){
                    searchForTeacher(textEntered,"area_lower");
                }

            }

        });

    }

    private void searchForTeacher(String textEntered, String choice) {
        final ArrayList<teacher> mList_teachers = new ArrayList<teacher>();
        Query firebaseSearchQuery = teacher_database.orderByChild(choice).startAt(textEntered.toLowerCase()).endAt(textEntered.toLowerCase()+'~');
        firebaseSearchQuery.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    teacher models = data.getValue(teacher.class);
                    mList_teachers.add(models);
//                    Toast.makeText(activity_serch.this,""+mList_teachers.get(0).getAge(), Toast.LENGTH_LONG).show();
                }
                if(mList_teachers.isEmpty()){
                    Toast.makeText(activity_serch.this,"No results!", Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), results_for_search.class);
                    intent.putExtra("KEY", (Serializable) mList_teachers);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    };

}
