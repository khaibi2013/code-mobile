package com.example.projectmobile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.Console;
import java.util.Calendar;

public class EditCost extends AppCompatActivity {
    private EditText typeCostEdit, costEdit, commentEdit;
    private TextView error;

    private Button deleteCButton, dateTimeButton;
    int noteID;

    private int mYear, mMonth, mDay;

    Costs selectedCost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cost);
        initWidgets();
        checkForEdit();


    }

    private void checkForEdit() {

        Intent previousIntent = getIntent();
        int passedCostId = previousIntent.getIntExtra(Costs.COST_EDIT_EXTRA, -1);
        noteID = previousIntent.getIntExtra(Costs.INPUT_COST_EXTRA, -1);



        selectedCost  = Costs.getCostForID(passedCostId);

        costEdit.setText(selectedCost.getCost());
        commentEdit.setText(selectedCost.getComment());
        dateTimeButton.setText(selectedCost.getDateTime());

        typeCostEdit.setText(selectedCost.getTypeCost());
        System.out.println(noteID);

    }

    private void initWidgets() {
        costEdit = findViewById(R.id.costFix);
        commentEdit = findViewById(R.id.commentFix);
        error = findViewById(R.id.errorEditCost);
        typeCostEdit = findViewById(R.id.typeFix);
        dateTimeButton = findViewById(R.id.btn_date_Fix);

    }

    public void updateCost(View view) {
        SQLiteManagerCost sqLiteManager = SQLiteManagerCost.instanceOfDatabase(this);
        String comment, cost, typeCost, dateTime;

        if(TextUtils.isEmpty(typeCostEdit.getText()) || TextUtils.isEmpty(commentEdit.getText()) || TextUtils.isEmpty(costEdit.getText())){
            error.setText("Enter all the entries");
        }else {
            typeCost = String.valueOf(typeCostEdit.getText());
            comment = String.valueOf(commentEdit.getText());
            cost = String.valueOf(costEdit.getText());
            dateTime= String.valueOf(dateTimeButton.getText());



            selectedCost.setTypeCost(typeCost);
            selectedCost.setCost(cost);
            selectedCost.setComment(comment);
            selectedCost.setDateTime(dateTime);
            selectedCost.setNoteId(noteID);
            sqLiteManager.updateCostInDB(selectedCost);

            finish();

        }




    }


    public void openDatesPickerEdit(View view) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        dateTimeButton.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.show();

    }

//    public void deleteNoteCost(View view) {
//        SQLiteManagerCost sqLiteManager = SQLiteManagerCost.instanceOfDatabase(this);
//        sqLiteManager.deleteCost(noteID);
//
//        Intent backHome = new Intent(getApplicationContext(), MainActivity.class);
//        startActivity(backHome);
//
//    }
}