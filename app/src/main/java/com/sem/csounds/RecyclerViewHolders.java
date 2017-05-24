package com.sem.csounds;

/**
 * Created by Slaton on 5/23/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RecyclerViewHolders extends RecyclerView.ViewHolder {

    public TextView professorName;
    public ImageView professorPicture;
    public ImageButton soundListButton;

    public RecyclerViewHolders(View itemView) {
        super(itemView);
        //itemView.setOnClickListener(this);
        final Context context = itemView.getContext();
        professorName = (TextView)itemView.findViewById(R.id.professor_name);
        professorPicture = (ImageView)itemView.findViewById(R.id.professor_picture);
        soundListButton = (ImageButton)itemView.findViewById(R.id.sound_list_button);
        soundListButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent intent = new Intent(context, ProfessorActivity.class);
                intent.putExtra(ProfessorActivity.PROFESSSOR, professorName.getText());
                context.startActivity(intent);
            }

        });
    }

//    @Override
//    public void onClick(View view) {
//        Toast.makeText(view.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();
//    }
}