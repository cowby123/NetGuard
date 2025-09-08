package com.example.connectionlogger;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

/**
 * RecyclerView adapter for displaying log items.
 */
public class LogAdapter extends ListAdapter<LogItem, LogAdapter.ViewHolder> {

    public LogAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<LogItem> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<LogItem>() {
                @Override
                public boolean areItemsTheSame(@NonNull LogItem oldItem, @NonNull LogItem newItem) {
                    return oldItem.time == newItem.time;
                }

                @Override
                public boolean areContentsTheSame(@NonNull LogItem oldItem, @NonNull LogItem newItem) {
                    return oldItem.equals(newItem);
                }
            };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_log, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_message);
        }

        void bind(LogItem item) {
            textView.setText(item.message);
        }
    }
}
