package com.example.interview_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends BaseAdapter {

    private final Context context;
    private final List<CardItem> originalList;
    private final List<CardItem> filteredList;

    public CardAdapter(Context context, List<CardItem> cardList) {
        this.context = context;
        this.originalList = new ArrayList<>(cardList);
        this.filteredList = new ArrayList<>(cardList);
    }

    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_card, parent, false);
        }

        TextView title = convertView.findViewById(R.id.title);
        TextView description = convertView.findViewById(R.id.description);
        CardView cardView = convertView.findViewById(R.id.cardContainer);

        CardItem item = filteredList.get(position);

        title.setText(item.getTitle());
        description.setText(item.getDescription());

        cardView.setOnClickListener(v -> handleCardClick(item));

        return convertView;
    }

    private void handleCardClick(CardItem item) {
        DbHelper dbHelper = new DbHelper(context);

        SharedPreferences prefs = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        int userId = (int) prefs.getLong("user_id", -1);

        if (userId == -1) {
            Toast.makeText(context, "Please log in again.", Toast.LENGTH_SHORT).show();
            return;
        }

        int interviewId = dbHelper.getInterviewIdByTitle(item.getTitle());

        if (interviewId == -1) {
            Toast.makeText(context, "Interview not found in database.", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean added = dbHelper.addUserInterviewIfNotExists(userId, interviewId, 0, "Good");

        if (added) {
            Toast.makeText(context, "Interview added successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Interview already exists.", Toast.LENGTH_SHORT).show();
        }


        Intent intent = new Intent(context, InterviewActivity.class);
        intent.putExtra("title", item.getTitle());
        intent.putExtra("desc", item.getDescription());
        context.startActivity(intent);
    }

    // Search filter
    public void filter(String query) {
        filteredList.clear();
        if (query == null || query.trim().isEmpty()) {
            filteredList.addAll(originalList);
        } else {
            String lowerQuery = query.toLowerCase();
            for (CardItem item : originalList) {
                if (item.getTitle().toLowerCase().contains(lowerQuery) ||
                        item.getDescription().toLowerCase().contains(lowerQuery)) {
                    filteredList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }
}
