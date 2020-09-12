package com.example.noteapp.Adapters;

import android.app.LauncherActivity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteapp.Entities.CheckList;
import com.example.noteapp.Entities.CheckListItem;
import com.example.noteapp.Listeners.ListItemListener;
import com.example.noteapp.R;

import java.sql.Time;
import java.util.List;
import java.util.Timer;
import java.util.zip.Inflater;

public class ListItemsAdapter extends RecyclerView.Adapter<ListItemsAdapter.ListViewHolder> {

    private List<CheckListItem> listItem;
    private ListItemListener itemListener;
    private Timer timer;
    private List<CheckListItem> listItemSource;

    public ListItemsAdapter(List<CheckListItem> listItem, ListItemListener itemListener) {
        this.listItem = listItem;
        this.itemListener = itemListener;
        listItemSource = listItem;
    }

    @NonNull
    @Override
    public ListItemsAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListItemsAdapter.ListViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_innerlist,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.setListItem(listItem.get(position));
        holder.layoutListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListItemListener.(listItem.get(position), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ListViewHolder extends RecyclerView.ViewHolder {
        TextView itemDetail, itemDateTime;
        LinearLayout layoutListItem;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            itemDetail = itemView.findViewById(R.id.itemDetail);
            itemDateTime = itemView.findViewById(R.id.itemDateTime);
            layoutListItem = itemView.findViewById(R.id.layoutlistItem);
        }

        void setListItem(CheckListItem listItem) {
            itemDetail.setText(listItem.getListText());
            itemDateTime.setText(listItem.getDateTime());

            /*GradientDrawable gradientDrawable = (GradientDrawable) layoutCheckList.getBackground();
            if (checkList.getColor() != null) {
                gradientDrawable.setColor(Color.parseColor(checkList.getColor()));
            } else {
                gradientDrawable.setColor(Color.parseColor("#333333"));
            }

             */

        }
    }
}
