package com.example.projectmobile;

import android.app.AlertDialog;
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

public class GenerateCost extends AppCompatActivity {


    private EditText typeCostEdit, costEdit, commentEdit;
    private TextView error;


    private int passedCostId;
    Button btnDatePicker;

    private int mYear, mMonth, mDay;


    Costs selectedCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_cost);

        initWidgets();
        checkForEdit();

    }

    private void checkForEdit() {

        Intent previousIntent = getIntent();

        passedCostId = previousIntent.getIntExtra(Costs.INPUT_COST_EXTRA, -1);

        System.out.println(passedCostId);


    }

    private void initWidgets() {
        error = findViewById(R.id.errorCreateCost);
        costEdit = findViewById(R.id.costEdit);
        commentEdit = findViewById(R.id.commentEdit);
        typeCostEdit = findViewById(R.id.costTypeEdit);
        btnDatePicker=(Button)findViewById(R.id.btn_date);


        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        btnDatePicker.setText(mDay + "-" + (mMonth + 1) + "-" + mYear);

    }


    public void saveCost(View view) {
        SQLiteManagerCost sqLiteManager = SQLiteManagerCost.instanceOfDatabase(this);
        String comment, cost, typeCost, dateTime;
        if(TextUtils.isEmpty(commentEdit.getText()) || TextUtils.isEmpty(costEdit.getText()) || TextUtils.isEmpty(typeCostEdit.getText())){
            error.setText("Enter all the entries");
        }else {
            comment = String.valueOf(commentEdit.getText());
            cost = String.valueOf(costEdit.getText());
            typeCost = String.valueOf(typeCostEdit.getText());
            dateTime = String.valueOf(btnDatePicker.getText());

            int id = Costs.listCosts.size();

            Costs newCost = new Costs(id,typeCost,cost, comment, dateTime ,passedCostId);
            Costs.listCosts.add(newCost);
            sqLiteManager.addCostToDatabase(newCost);

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