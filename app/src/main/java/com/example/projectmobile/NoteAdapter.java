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

public class NoteAdapter extends ArrayAdapter<Trip> {

    public NoteAdapter(Context context, ArrayList<Trip> list) {

        super(context,0, list);
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Trip note = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.note_cell, parent, false);

        }

        TextView title = convertView.findViewById(R.id.cellTitle);
        TextView destination = convertView.findViewById(R.id.cellDestination);
        TextView desc = convertView.findViewById(R.id.cellDesc);
        TextView purpose = convertView.findViewById(R.id.cellPurpose);
        TextView risk = convertView.findViewById(R.id.cellRisk);
        TextView datetime = convertView.findViewById(R.id.cellDateTime);
        TextView time = convertView.findViewById(R.id.cellTime);




        title.setText(note.getTitle());
        destination.setText(note.getDestination());
        desc.setText(note.getDescription());
        purpose.setText(note.getPurpose());
        risk.setText("Is the trip risky: " + note.getGroupRisky());
        datetime.setText( note.getDatetime());
        time.setText(note.getTime());


        return convertView;
    }


}
