package com.example.merav.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.support.v4.content.ContextCompat.startActivity;

public class recyclerViewAdapter extends RecyclerView.Adapter<recyclerViewAdapter.ViewHolder>{
    private static final String TAG = "recyclerViewAdapter";

    private ArrayList<teacher> teachers ;
    private Context mContext;

    public recyclerViewAdapter(Context mContext, ArrayList<teacher> teachers_list) {
//        super();
        this.mContext = mContext;
        this.teachers = teachers_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.activity_teacher_item, null);
        recyclerViewAdapter.ViewHolder holder = new recyclerViewAdapter.ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final teacher te = teachers.get(i);

        Log.d(TAG, "onBindViewHolder: called");
//        Glide.with(mContext).asBitmap().load(te).into(viewHolder.image);

        viewHolder.imageName.setText(te.getName());
//        viewHolder.subject.setText();
        viewHolder.area.setText("I am teaching at: "+te.getArea()+"\n"+
                "I can teach you: "+te.getProfession());
        viewHolder.reting.setRating(te.rank);
        if(!te.getImage().isEmpty())
            Picasso.get().load(te.getImage()).into(viewHolder.image);
        else
            Picasso.get().load(R.drawable.logoo).into(viewHolder.image);

        viewHolder.read_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mIntent = new Intent(mContext, teacher_page.class);
                mIntent.putExtra("user",te);
                mContext.startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return teachers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView imageName;
        //        TextView subject;
        TextView area;
        Button read_more;
        RatingBar reting;
//        RelativeLayout parent_layout;

        public ViewHolder(View itemView){
            super(itemView);
            image = itemView.findViewById(R.id.pucture);
            imageName = itemView.findViewById(R.id.teacher_name);
//            subject = itemView.findViewById(R.id.subject);
            area = itemView.findViewById(R.id.area);
            reting = itemView.findViewById(R.id.reting);
//            parent_layout = itemView.findViewById(R.id.parent_layout);
            read_more = itemView.findViewById(R.id.read_more);

        }
    }
}


