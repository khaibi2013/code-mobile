package com.example.projectmobile;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class ListOfObservation extends AppCompatActivity {

    private ListView costListView;
    int passedCostId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_observation);

        iniWidget();
        loadFromDBToMemory();
        setNoteAdapter();
        setOnClickListerner();

    }

    private void setOnClickListerner() {
        costListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Observation selectedCost = (Observation) costListView.getItemAtPosition(position);
                Intent editCost = new Intent(getApplicationContext(), EditObservation.class);

                editCost.putExtra(Observation.COST_EDIT_EXTRA, selectedCost.getId());
                editCost.putExtra(Observation.INPUT_COST_EXTRA, passedCostId );

                startActivity(editCost);
            }
        });
    }

    private void loadFromDBToMemory() {
        Intent previousIntent = getIntent();
        passedCostId = previousIntent.getIntExtra(Observation.COST_LIST_EXTRA, -1);

        SQLiteManagerObservation sqLiteManager = SQLiteManagerObservation.instanceOfDatabase(this);
        sqLiteManager.populateCostsListArray(passedCostId);
    }

    private void iniWidget() {

        costListView = findViewById(R.id.listObservationView);

    }
    private void setNoteAdapter() {
        ObservationAdapter noteAdapter = new ObservationAdapter(getApplicationContext(), Observation.nonDeleteCost());
        costListView.setAdapter(noteAdapter);

    }

    @Override
    protected void onResume(){
        super.onResume();
        setNoteAdapter();

    }


    public void newObservation(View view) {
        Intent listObservationIntent = new Intent(this, GenerateObservation.class);
        listObservationIntent.putExtra(Observation.INPUT_COST_EXTRA, passedCostId);

        System.out.println(passedCostId);

        startActivity(listObservationIntent);

    }

    public void deleteNoteObservation(View view) {
        SQLiteManagerObservation sqLiteManager = SQLiteManagerObservation.instanceOfDatabase(this);
        sqLiteManager.deleteCost(passedCostId);

        Intent backHome = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(backHome);

    }
}