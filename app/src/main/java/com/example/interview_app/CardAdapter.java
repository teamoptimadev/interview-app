package com.example.interview_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends BaseAdapter {

    private Context context;
    private List<CardItem> originalList;
    private List<CardItem> filteredList;

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

        cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, InterviewActivity.class);
            intent.putExtra("title", item.getTitle());
            intent.putExtra("desc", item.getDescription());
            context.startActivity(intent);
        });

        return convertView;
    }

    // Search filter logic
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
