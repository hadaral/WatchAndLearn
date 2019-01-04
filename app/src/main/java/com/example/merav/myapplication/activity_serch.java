package com.example.merav.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
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

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.provider.Contacts.SettingsColumns.KEY;


public class activity_serch extends AppCompatActivity {

    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    String lattitude,longitude;


    ConstraintLayout myLayout;
    AnimationDrawable animationDrawable;

    Button start_searching;
    RadioGroup radio_search_group;
    RadioButton radio_by_teacher;
    RadioButton radio_by_price;
    RadioButton radio_by_profession;
    RadioButton radio_by_area;
    RadioButton radio_by_area2;

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

        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);


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
//        input.setText(null);
        radio_by_area2 = (RadioButton)findViewById(R.id.radio_by_area2);

        start_searching.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
                int selectedId = radio_search_group.getCheckedRadioButtonId();

                // find the radiobutton by returned id

                String textEntered = input.getText().toString();
                choice = (RadioButton) findViewById(selectedId);
//                int choiceNum = choice.getId() ;

                if(R.id.radio_by_area2 == radio_search_group.getCheckedRadioButtonId()){
                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        buildAlertMessageNoGps();

                    } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        getLocation();
                    }
                }
                else {
                    if (input.getText()==(null) ||input.getText().toString().length()==0 || input.getText().equals("")|| input.getText().equals(" ")|| selectedId == -1) {
                        Toast.makeText(activity_serch.this, "Please choose first", Toast.LENGTH_LONG).show();
                    }
                    else {
                        if (selectedId == radio_by_teacher.getId()) {
                            searchForTeacher(textEntered, "name_lower");
                        } else if (selectedId == radio_by_price.getId()) {
                            searchForTeacher(textEntered, "cost");
                        } else if (selectedId == radio_by_profession.getId()) {
                            searchForTeacher(textEntered, "profession_lower");
                        } else if (selectedId == radio_by_area.getId()) {
                            searchForTeacher(textEntered, "area_lower");
                        }
                    }
                }

            }

        });

    }

    public String hereLocation(double lat, double lon){

        String cityName = "";
        Geocoder geocoder = new Geocoder(this,Locale.getDefault());
        List<Address> addresses;
        try{
            addresses = geocoder.getFromLocation(lat,lon,10);
            if(addresses.size()>0){
                for (Address adr: addresses){
                    if(adr.getLocality()!=null && adr.getLocality().length()>0){
                        cityName = adr.getLocality();
                        break;
                    }
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        Toast.makeText(this,""+cityName, Toast.LENGTH_SHORT).show();
        return cityName;
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(activity_serch.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (activity_serch.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity_serch.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            Location location2 = locationManager.getLastKnownLocation(LocationManager. PASSIVE_PROVIDER);

            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

//                textView.setText("Your current location is"+ "\n" + "Lattitude = " + lattitude
//                        + "\n" + "Longitude = " + longitude);
                searchForTeacher(hereLocation(latti,longi),"area_lower");


            } else  if (location1 != null) {
                double latti = location1.getLatitude();
                double longi = location1.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

//                textView.setText("Your current location is"+ "\n" + "Lattitude = " + lattitude
//                        + "\n" + "Longitude = " + longitude);

                searchForTeacher(hereLocation(latti,longi),"area_lower");


            } else  if (location2 != null) {
                double latti = location2.getLatitude();
                double longi = location2.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                searchForTeacher(hereLocation(latti,longi),"area_lower");


//                textView.setText("Your current location is"+ "\n" + "Lattitude = " + lattitude
//                        + "\n" + "Longitude = " + longitude);

            }else{

                Toast.makeText(this,"Unble to Trace your location",Toast.LENGTH_SHORT).show();

            }
        }
    }

    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
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