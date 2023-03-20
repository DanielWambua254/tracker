package com.example.tracker;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tracker.R;
import com.example.tracker.calender_adapter;

public class calendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public   final TextView dayOfMonth;
    private final calender_adapter.onItemListener onItemListener;
    public calendarViewHolder(@NonNull View itemView, calender_adapter.onItemListener onItemListener) {
        super(itemView);

        dayOfMonth = itemView.findViewById(R.id.dateTv);
        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onItemListener.onItemClick(getAdapterPosition(),(String) dayOfMonth.getText());
    }
}
