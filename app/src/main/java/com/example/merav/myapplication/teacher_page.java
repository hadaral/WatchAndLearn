package com.example.merav.myapplication;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class teacher_page extends AppCompatActivity implements View.OnClickListener {
    TextView name,data;
    Button submit,rate;
    RatingBar ratingBar,showRatingBar;
    teacher user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_page);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Watch&Learn");
        Intent intent=getIntent();
        user=(teacher)intent.getSerializableExtra("user");
        ImageView mImage=(ImageView)findViewById(R.id.photo);
        rate=(Button)findViewById(R.id.Rate);
        submit=(Button)findViewById(R.id.Submit);
        showRatingBar = (RatingBar) findViewById(R.id.show_rating_bar);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        name = (TextView) findViewById(R.id.name);
        data = (TextView) findViewById(R.id.data);
        name.setText(user.getName());
        data.setText("Profession: "+user.getProfession()+"\n"+"Area: "+user.getArea()+"\n"+"Cost per lesson: "+user.getCost()+"\n"+"Age: "+user.getAge()+"\n"+ "Phone Number: "+user.getPhone()+"\n");
        showRatingBar.setRating(user.getRank());

        Picasso.get().load(user.getImage()).into(mImage);
        showRatingBar.setIsIndicator(true);
       // Glide.with(getBaseContext()).load(user.getImage()).into(mImage);
        rate.setOnClickListener(this);
        submit.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v==rate){
            rate.setVisibility(View.INVISIBLE);
            ratingBar.setVisibility(View.VISIBLE);
            submit.setVisibility(View.VISIBLE);
        }
        if(v==submit){
            final DatabaseReference db = FirebaseDatabase.getInstance().getReference();
            user.setCount(user.getCount()+1);
            user.setRank((ratingBar.getRating()+(user.getRank()*(user.getCount()-1)))/(user.getCount()));
            ratingBar.setIsIndicator(true);
            showRatingBar.setRating(user.getRank());


            rate.setEnabled(false);
            rate.setVisibility(View.VISIBLE);
            ratingBar.setVisibility(View.INVISIBLE);
            submit.setVisibility(View.INVISIBLE);

            db.child("Teachers").child(user.getEmail().replace(".", "|")).setValue(user);
            db.child("Users").child(user.getEmail().replace(".", "|")).setValue(user);
            for(String s:user.getProfession()){
                db.child("Subjects").child(s).child(user.getEmail().replace(".", "|")).setValue(user);
            }


        }

    }
}

