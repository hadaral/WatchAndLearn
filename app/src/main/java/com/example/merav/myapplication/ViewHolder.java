package com.example.merav.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static android.support.v4.content.ContextCompat.startActivity;

public class ViewHolder extends RecyclerView.ViewHolder{
    View v;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        v = itemView;
    }

    public void setdetails (final Context c, final teacher model ,String name, String profession, String area, String image, float rank){
        TextView data = v.findViewById(R.id.data);
        ImageView imageV = v.findViewById(R.id.image);
        Button button = v.findViewById(R.id.bn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(c, teacher_page.class);
                mIntent.putExtra("user",model);
                c.startActivity(mIntent);
            }
        });

        RatingBar ratingBar = v.findViewById(R.id.ratingBar);
        data.setText("Name: " + name + '\n' +
                "Profession: " + profession + '\n' +
                "Area: " + area + '\n');
        ratingBar.setIsIndicator(true);
        ratingBar.setRating(rank);

        Picasso.get().load(image).into(imageV);


    }

}