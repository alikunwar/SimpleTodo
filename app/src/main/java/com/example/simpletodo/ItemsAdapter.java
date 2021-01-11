package com.example.simpletodo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

public interface OnLongClickListener {
    void onItemLongClicked(int position);
}

    public interface OnClickListener {
        void onItemClicked(int position);
    }


    List<String> items;
    OnLongClickListener longClickListener;
    OnClickListener clickListener;
    public ItemsAdapter(List<String> items, OnLongClickListener longClickListener, OnClickListener clickListener) {
        this.items = items;
        this.longClickListener = longClickListener;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent,  false);
        //wrapping it inside viewHolder and return it
        return new ViewHolder(todoView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String item = items.get(position);
        holder.bind(item);
    }


    //let the RV how many items are in the list
    @Override
    public int getItemCount() {
        return items.size();
    }

    //define viewHolder such that container
    // to provide easy access to views that represent each row of the list

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tItem;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            tItem = itemView.findViewById(android.R.id.text1);
        }

// update view inside of the view holder
        public void bind(String item) {
            tItem.setText(item);
            tItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClicked(getAdapterPosition());

                }
            });


            tItem.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v) {

                 longClickListener.onItemLongClicked(getAdapterPosition());

                    return true;
                }
            });


        }
    }
}
