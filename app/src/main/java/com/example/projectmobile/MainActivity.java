package com.example.projectmobile;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView noteListView;
    ArrayList<Trip> nonDelete = new ArrayList<>();
    EditText search;
    NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniWidget();
        loadFromDB();
        setNoteTripAdapter();
        setOnClickListerner();
    }

    private void setOnClickListerner() {
        noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Trip selectedNote = (Trip) noteListView.getItemAtPosition(position);
                Intent editNote = new Intent(getApplicationContext(), TripDetailActivity.class);
                editNote.putExtra(Trip.NOTE_EDIT_EXTRA, selectedNote.getId());

                startActivity(editNote);
            }
        });
    }



    private void loadFromDB() {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.showTripList();

    }


    private void setNoteTripAdapter() {
        noteAdapter = new NoteAdapter(getApplicationContext(), Trip.nonDeleteNote());
        noteListView.setAdapter(noteAdapter);

    }

    private void iniWidget() {

        noteListView = findViewById(R.id.noteListView);
        search = findViewById(R.id.search_bar);



    }


    public void newNote(View view) {


        Intent newNoteIntent = new Intent(this, TripDetailActivity.class);
        startActivity(newNoteIntent);

    }

    @Override
    protected void onResume(){
        super.onResume();
        setNoteTripAdapter();

    }


    public void searchKeyword(View view) {
        String searchKeyWord;
        searchKeyWord = String.valueOf(search.getText());

        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.searchNote(searchKeyWord);
        noteAdapter = new NoteAdapter(getApplicationContext(), Trip.searchNotes());
        noteListView.setAdapter(noteAdapter);
    }


    public void deleteAllTrip(View view) {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.deleteAll();
        Intent back = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(back);

    }

}