package com.example.todo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


//Responsible for taking the data and putting it on a viewholder.
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    public interface OnLongClickListner{

        void onItemLongClicked(int position);
    }


    List<String> items;
    OnLongClickListner longClickListner;

    public ItemAdapter(List<String> items, OnLongClickListner longClickListner) {
        this.items = items;
        this.longClickListner = longClickListner;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Use layout inflator to inflate a view

        View toDoView =  LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1,parent,false);

        return new ViewHolder(toDoView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //grab the item at the viewholder.
        String item = items.get(position);

        holder.bind(item);


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // Container to provide easy access to views that represent each row of the list.

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(android.R.id.text1);

        }

        public void bind(String item) {

            tvItem.setText(item);
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    //notify adapter
                    longClickListner.onItemLongClicked(getAdapterPosition());
                    return true;
                }
            });

        }
    }
}
