package com.example.project;

import static com.example.project.HomeActivity.logList;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    FirebaseAnalytics analytics;
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
        analytics = FirebaseAnalytics.getInstance(this.context);

        // Add click listener to navigate to the respective activity
        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("Lcd","Lcd Message");
            analytics.logEvent("Lcd_Message", bundle);
            Intent intent = null;

            switch (item.getTitle()) {
                case "LIGHT":
                    logList.add("Light_Activity " + new SimpleDateFormat().format(Calendar.getInstance().getTime()));
                    bundle.putString("Light","Light Activity");
                    analytics.logEvent("Light_Activity", bundle);
                    intent = new Intent(context, LightActivity.class);
                    break;
                case "FAN":
                    logList.add("Fan_Activity " + new SimpleDateFormat().format(Calendar.getInstance().getTime()));
                    bundle.putString("Fan","Fan Activity");
                    analytics.logEvent("Fan_Activity", bundle);
                    intent = new Intent(context, com.example.project.FanActivity.class);
                    break;
                case "PASSWORD":
                    logList.add("Password_Activity " + new SimpleDateFormat().format(Calendar.getInstance().getTime()));
                    bundle.putString("Password","Password Activity");
                    analytics.logEvent("Password_Activity", bundle);
                    intent = new Intent(context, PasswordActivity.class);
                    break;
                case "TEMPERATURE":
                    logList.add("Temperature_Activity " + new SimpleDateFormat().format(Calendar.getInstance().getTime()));
                    bundle.putString("Temperature","Temperature Activity");
                    analytics.logEvent("Temperature_Activity", bundle);
                    intent = new Intent(context, TemperatureActivity.class);
                    break;
                case "LCD":
                    logList.add("LCD_Activity is " + new SimpleDateFormat().format(Calendar.getInstance().getTime()));
                    bundle.putString("Lcd","Lcd Activity");
                    analytics.logEvent("Lcd_Activity", bundle);
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
