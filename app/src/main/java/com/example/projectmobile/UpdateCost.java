package com.example.projectmobile;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class UpdateCost extends AppCompatActivity {

    private EditText costEdit, commentEdit;

    private Button deleteCButton;

    private int passedCostId;

    Costs selectedCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_cost);

        initWidgets();
        checkForEdit();
    }

    private void checkForEdit() {

        Intent previousIntent = getIntent();

        passedCostId = previousIntent.getIntExtra(Costs.COST_EDIT_EXTRA, -1);


        costEdit.setText(selectedCost.getCost());
        commentEdit.setText(selectedCost.getComment());

    }

    private void initWidgets() {
        costEdit = findViewById(R.id.costEdit);
        commentEdit = findViewById(R.id.commentEdit);
        deleteCButton = findViewById(R.id.deleteCost);

    }


    public void updateCost(View view) {

        SQLiteManagerCost sqLiteManager = SQLiteManagerCost.instanceOfDatabase(this);
        String comment, cost;

        comment = String.valueOf(commentEdit.getText());
        cost = String.valueOf(costEdit.getText());

        selectedCost.setCost(cost);
        selectedCost.setComment(comment);
        sqLiteManager.updateCostInDB(selectedCost);

        finish();
    }


    public void deleteCost(View view)
    {

    }

}