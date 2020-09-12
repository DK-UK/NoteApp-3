package com.example.noteapp.Adapters;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteapp.Entities.CheckList;
import com.example.noteapp.Entities.Note;
import com.example.noteapp.Listeners.CheckListListener;
import com.example.noteapp.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CheckListAdapter extends RecyclerView.Adapter<CheckListAdapter.CheckListViewHolder> {

    private List<CheckList> checkList;
    private CheckListListener checkListListener;
    private Timer timer;
    private List<CheckList> checkListSource;

    public CheckListAdapter(List<CheckList> checkList, CheckListListener checkListListener) {
        this.checkList = checkList;
        this.checkListListener = checkListListener;
        checkListSource = checkList;
    }

    @NonNull
    @Override
    public CheckListAdapter.CheckListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CheckListAdapter.CheckListViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_checklist,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull CheckListAdapter.CheckListViewHolder holder, final int position) {
        holder.setCheckList(checkList.get(position));
        holder.layoutCheckList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkListListener.onCheckListClicked(checkList.get(position), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return checkList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class CheckListViewHolder extends RecyclerView.ViewHolder {
        TextView listTitle, listDateTime;
        LinearLayout layoutCheckList;
        ImageView imageCheckList;

        public CheckListViewHolder(@NonNull View itemView) {
            super(itemView);

            listTitle = itemView.findViewById(R.id.textTitle);
            listDateTime = itemView.findViewById(R.id.textDateTime);
            layoutCheckList = itemView.findViewById(R.id.layoutCheckList);
        }

        void setCheckList(CheckList checkList) {
            listTitle.setText(checkList.getTitle());
            listDateTime.setText(checkList.getDateTime());

            GradientDrawable gradientDrawable = (GradientDrawable) layoutCheckList.getBackground();
            if (checkList.getColor() != null) {
                gradientDrawable.setColor(Color.parseColor(checkList.getColor()));
            } else {
                gradientDrawable.setColor(Color.parseColor("#333333"));
            }

        }
    }

    public void searchLists(final String searchKeyword) {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (searchKeyword.trim().isEmpty()) {
                    checkList = checkListSource;
                } else {
                    ArrayList<CheckList> temp = new ArrayList<>();
                    for (CheckList checkList : checkList) {
                        if (checkList.getTitle().toLowerCase().contains(searchKeyword.toLowerCase())
                                || checkList.getListText().toLowerCase().contains(searchKeyword.toLowerCase()))
                        {
                            temp.add(checkList);
                        }
                    }
                    checkList = temp;
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });

            }
        }, 500);
    }

    public void cancelTimer(){
        if (timer != null){
            timer.cancel();
        }
    }


}
