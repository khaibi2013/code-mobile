package com.example.projectmobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CostAdapter extends ArrayAdapter<Costs> {

    public CostAdapter(Context context, ArrayList<Costs> list) {

        super(context,0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Costs costs = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cost_cell, parent, false);

        }

        TextView typeCost = convertView.findViewById(R.id.cellTypeCost);
        TextView cost = convertView.findViewById(R.id.cellCost);
        TextView comment = convertView.findViewById(R.id.cellComment);
        TextView dateTime = convertView.findViewById(R.id.cellDateTimeCost);
//        TextView noteId = convertView.findViewById(R.id.cellNoteId);


        typeCost.setText(costs.getTypeCost());
        cost.setText(costs.getCost() + " $");
        comment.setText(costs.getComment());
        dateTime.setText(costs.getDateTime());
//        noteId.setText(costs.getNoteId());

        return convertView;
    }
}
