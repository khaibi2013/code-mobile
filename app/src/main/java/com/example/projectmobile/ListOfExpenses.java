package com.example.projectmobile;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class ListOfExpenses extends AppCompatActivity {

    private ListView costListView;
    int passedCostId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_expenses);

        iniWidget();
        loadFromDBToMemory();
        setNoteAdapter();
        setOnClickListerner();

    }

    private void setOnClickListerner() {
        costListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Costs selectedCost = (Costs) costListView.getItemAtPosition(position);
                Intent editCost = new Intent(getApplicationContext(), EditCost.class);

                editCost.putExtra(Costs.COST_EDIT_EXTRA, selectedCost.getId());
                editCost.putExtra(Costs.INPUT_COST_EXTRA, passedCostId );

                startActivity(editCost);
            }
        });
    }

    private void loadFromDBToMemory() {
        Intent previousIntent = getIntent();
        passedCostId = previousIntent.getIntExtra(Costs.COST_LIST_EXTRA, -1);

        SQLiteManagerCost sqLiteManager = SQLiteManagerCost.instanceOfDatabase(this);
        sqLiteManager.populateCostsListArray(passedCostId);
    }

    private void iniWidget() {

        costListView = findViewById(R.id.listCostView);

    }
    private void setNoteAdapter() {
        CostAdapter noteAdapter = new CostAdapter(getApplicationContext(), Costs.nonDeleteCost());
        costListView.setAdapter(noteAdapter);

    }

    @Override
    protected void onResume(){
        super.onResume();
        setNoteAdapter();

    }


    public void newCost(View view) {
        Intent listCostIntent = new Intent(this, GenerateCost.class);
        listCostIntent.putExtra(Costs.INPUT_COST_EXTRA, passedCostId);

        System.out.println(passedCostId);

        startActivity(listCostIntent);

    }

    public void deleteNoteCost(View view) {
        SQLiteManagerCost sqLiteManager = SQLiteManagerCost.instanceOfDatabase(this);
        sqLiteManager.deleteCost(passedCostId);

        Intent backHome = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(backHome);

    }
}