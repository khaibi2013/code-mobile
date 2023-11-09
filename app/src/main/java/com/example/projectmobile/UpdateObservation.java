package com.example.projectmobile;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class UpdateObservation extends AppCompatActivity {

    private EditText observationEdit, commentEdit;

    private Button deleteCButton;

    private int passedCostId;

    Observation selectedCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_observation);

        initWidgets();
        checkForEdit();
    }

    private void checkForEdit() {

        Intent previousIntent = getIntent();

        passedCostId = previousIntent.getIntExtra(Observation.COST_EDIT_EXTRA, -1);


        observationEdit.setText(selectedCost.getCost());
        commentEdit.setText(selectedCost.getComment());

    }

    private void initWidgets() {
        observationEdit = findViewById(R.id.observationEdit);
        commentEdit = findViewById(R.id.commentEdit);
        deleteCButton = findViewById(R.id.deleteCost);

    }


    public void updateObservation(View view) {

        SQLiteManagerObservation sqLiteManager = SQLiteManagerObservation.instanceOfDatabase(this);
        String comment, cost;

        comment = String.valueOf(commentEdit.getText());
        cost = String.valueOf(observationEdit.getText());

        selectedCost.setCost(cost);
        selectedCost.setComment(comment);
        sqLiteManager.updateObservationInDB(selectedCost);

        finish();
    }


    public void deleteCost(View view)
    {

    }

}