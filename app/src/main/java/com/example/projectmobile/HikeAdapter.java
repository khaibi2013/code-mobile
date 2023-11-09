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

public class HikeAdapter extends ArrayAdapter<Hike> {

    public HikeAdapter(Context context, ArrayList<Hike> list) {

        super(context,0, list);
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Hike note = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.hike_cell, parent, false);

        }

        TextView title = convertView.findViewById(R.id.cellTitle);
        TextView destination = convertView.findViewById(R.id.cellDistanceDifficulty);
        TextView desc = convertView.findViewById(R.id.cellDistanceLength);
        TextView purpose = convertView.findViewById(R.id.cellDescribeDistance);
        TextView risk = convertView.findViewById(R.id.cellParking);
        TextView datetime = convertView.findViewById(R.id.cellDateTime);
        TextView time = convertView.findViewById(R.id.cellTime);
        TextView location = convertView.findViewById(R.id.location);




        title.setText(note.getTitle());
        destination.setText(note.getDestination());
        desc.setText(note.getDescription());
        purpose.setText(note.getPurpose());
        risk.setText("Is the trip risky: " + note.getGroupRisky());
        datetime.setText( note.getDatetime());
        time.setText(note.getTime());
        location.setText(note.getLocation());


        return convertView;
    }


}
