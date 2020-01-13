package com.example.lecturerapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {



    Context context;
    ArrayList<Information> informationList;
    OnItemClickListener removeListener;
    public Adapter(Context c, ArrayList<Information> inf, OnItemClickListener mremoveListener)
    {
        removeListener= mremoveListener;
        context = c;
        informationList = inf;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.row,viewGroup,false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.bind(informationList.get(i),removeListener);









    }

    @Override
    public int getItemCount() {
        return informationList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text;
        ImageView removeValue;


        public MyViewHolder(@NonNull View itemView) {


            super(itemView);
            text=itemView.findViewById(R.id.text);
            removeValue=itemView.findViewById(R.id.delete);


        }
        public void bind(final Information item, final OnItemClickListener listener)
        {
            text.setText(item.getBody());
            removeValue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);

                }
            });

        }



    }


    public interface OnItemClickListener{
        void onItemClick(Information item);
    }

}
