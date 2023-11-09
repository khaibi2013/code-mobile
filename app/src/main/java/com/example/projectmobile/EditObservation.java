package com.example.projectmobile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Calendar;

public class EditObservation extends AppCompatActivity {
    private EditText typeobservationEdit, observationEdit, commentEdit;
    private TextView error;

    private Button deleteCButton, dateTimeButton;
    int noteID;

    private int mYear, mMonth, mDay;

    Observation selectedCost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_observation);
        initWidgets();
        checkForEdit();


    }

    private void checkForEdit() {

        Intent previousIntent = getIntent();
        int passedCostId = previousIntent.getIntExtra(Observation.COST_EDIT_EXTRA, -1);
        noteID = previousIntent.getIntExtra(Observation.INPUT_COST_EXTRA, -1);



        selectedCost  = Observation.getCostForID(passedCostId);

        observationEdit.setText(selectedCost.getCost());
        commentEdit.setText(selectedCost.getComment());
        dateTimeButton.setText(selectedCost.getDateTime());

        typeobservationEdit.setText(selectedCost.getTypeCost());
        System.out.println(noteID);

    }

    private void initWidgets() {
        observationEdit = findViewById(R.id.observationFix);
        commentEdit = findViewById(R.id.commentFix);
        error = findViewById(R.id.errorEditObservation);
        typeobservationEdit = findViewById(R.id.typeFix);
        dateTimeButton = findViewById(R.id.btn_date_Fix);

    }

    public void updateObservation(View view) {
        SQLiteManagerObservation sqLiteManager = SQLiteManagerObservation.instanceOfDatabase(this);
        String comment, cost, typeCost, dateTime;

        if(TextUtils.isEmpty(typeobservationEdit.getText()) || TextUtils.isEmpty(commentEdit.getText()) || TextUtils.isEmpty(observationEdit.getText())){
            error.setText("Enter all the entries");
        }else {
            typeCost = String.valueOf(typeobservationEdit.getText());
            comment = String.valueOf(commentEdit.getText());
            cost = String.valueOf(observationEdit.getText());
            dateTime= String.valueOf(dateTimeButton.getText());



            selectedCost.setTypeCost(typeCost);
            selectedCost.setCost(cost);
            selectedCost.setComment(comment);
            selectedCost.setDateTime(dateTime);
            selectedCost.setNoteId(noteID);
            sqLiteManager.updateObservationInDB(selectedCost);

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

//    public void deleteNoteObservation(View view) {
//        SQLiteManagerCost sqLiteManager = SQLiteManagerCost.instanceOfDatabase(this);
//        sqLiteManager.deleteCost(noteID);
//
//        Intent backHome = new Intent(getApplicationContext(), MainActivity.class);
//        startActivity(backHome);
//
//    }
}