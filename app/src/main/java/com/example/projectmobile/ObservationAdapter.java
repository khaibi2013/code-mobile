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

public class ObservationAdapter extends ArrayAdapter<Observation> {

    public ObservationAdapter(Context context, ArrayList<Observation> list) {

        super(context,0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Observation observation = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.observation_cell, parent, false);

        }

        TextView typeCost = convertView.findViewById(R.id.cellTypeCost);
        TextView cost = convertView.findViewById(R.id.cellCost);
        TextView comment = convertView.findViewById(R.id.cellComment);
        TextView dateTime = convertView.findViewById(R.id.cellDateTimeCost);
//        TextView noteId = convertView.findViewById(R.id.cellNoteId);


        typeCost.setText(observation.getTypeCost());
        cost.setText(observation.getCost() + " km");
        comment.setText(observation.getComment());
        dateTime.setText(observation.getDateTime());
//        noteId.setText(costs.getNoteId());

        return convertView;
    }
}
