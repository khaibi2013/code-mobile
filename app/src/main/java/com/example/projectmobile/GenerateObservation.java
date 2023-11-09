package com.example.projectmobile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Calendar;

public class GenerateObservation extends AppCompatActivity {


    private EditText typeobservationEdit, observationEdit, commentEdit;
    private TextView error;


    private int passedCostId;
    Button btnDatePicker;

    private int mYear, mMonth, mDay;


    Observation selectedCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_observation);

        initWidgets();
        checkForEdit();

    }

    private void checkForEdit() {

        Intent previousIntent = getIntent();

        passedCostId = previousIntent.getIntExtra(Observation.INPUT_COST_EXTRA, -1);

        System.out.println(passedCostId);


    }

    private void initWidgets() {
        error = findViewById(R.id.errorCreateObservation);
        observationEdit = findViewById(R.id.observationEdit);
        commentEdit = findViewById(R.id.commentEdit);
        typeobservationEdit = findViewById(R.id.observationTypeEdit);
        btnDatePicker=(Button)findViewById(R.id.btn_date);


        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        btnDatePicker.setText(mDay + "-" + (mMonth + 1) + "-" + mYear);

    }


    public void saveObservation(View view) {
        SQLiteManagerObservation sqLiteManager = SQLiteManagerObservation.instanceOfDatabase(this);
        String comment, cost, typeCost, dateTime;
        if(TextUtils.isEmpty(commentEdit.getText()) || TextUtils.isEmpty(observationEdit.getText()) || TextUtils.isEmpty(typeobservationEdit.getText())){
            error.setText("Enter all the entries");
        }else {
            comment = String.valueOf(commentEdit.getText());
            cost = String.valueOf(observationEdit.getText());
            typeCost = String.valueOf(typeobservationEdit.getText());
            dateTime = String.valueOf(btnDatePicker.getText());

            int id = Observation.listObservations.size();

            Observation newObservation = new Observation(id,typeCost,cost, comment, dateTime ,passedCostId);
            Observation.listObservations.add(newObservation);
            sqLiteManager.addCostToDatabase(newObservation);

            finish();

        }

    }

    public void openDatesPicker(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        btnDatePicker.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.show();


    }




}