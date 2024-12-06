package com.example.vvvvv;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<ActionItem> itemList;

    public RecyclerViewAdapter(Context context, List<ActionItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ActionItem item = itemList.get(position);
        holder.title.setText(item.getTitle());
        holder.icon.setImageResource(item.getImageResourceId());

        // Add click listener to navigate to the respective activity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = null;
            switch (item.getTitle()) {
                case "LIGHT":
                    intent = new Intent(context, LightActivity.class);
                    break;
                case "FAN":
                    intent = new Intent(context, FanActivity.class);
                    break;
                case "PASSWORD":
                    intent = new Intent(context, PasswordActivity.class);
                    break;
                case "TEMPERATURE":
                    intent = new Intent(context, TemperatureActivity.class);
                    break;
                case "LCD":
                    intent = new Intent(context, LcdActivity.class);
                    break;
                // Add more cases as needed
            }
            if (intent != null) {
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            icon = itemView.findViewById(R.id.icon);
        }
    }
}
