package com.sem.csounds;

/**
 * Created by Slaton on 5/23/2017.
 */



import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {

    private ArrayList<Professor> itemList;
    private Context context;

    public RecyclerViewAdapter(Context context, ArrayList<Professor> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_professor, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        holder.professorName.setText(itemList.get(position).getName());
        holder.professorPicture.setImageResource(itemList.get(position).getImageId());
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}