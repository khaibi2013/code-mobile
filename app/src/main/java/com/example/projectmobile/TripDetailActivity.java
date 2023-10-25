package com.example.projectmobile;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Calendar;

public class TripDetailActivity extends AppCompatActivity {

    private EditText titleEdit , descEdit, purposeEdit, destinationEdit;
    private  RadioButton radioButtonTrip;

    private int year, mMonth, mDay;

    private RadioGroup radioGroupTrip;
    private RadioButton radioButtonYes;
    private RadioButton radioButtonNo;
    private RadioButton radioButtonNothing;
    private int mHour, mMinute;

    private Button deleteButton, listCostButton;
    private TextView error;
    Trip selectedNote;

    private Button dateButton, timeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);


        initWidgets();
        checkForEdit();
    }

    private void checkForEdit() {

        Intent previousIntent = getIntent();

        int passedNoteId = previousIntent.getIntExtra(Trip.NOTE_EDIT_EXTRA, -1);
        selectedNote  = Trip.getNoteForID(passedNoteId);

        System.out.println(selectedNote);


        if (selectedNote != null){

            if(radioButtonYes.getText().toString().equals(selectedNote.getGroupRisky())){
                radioButtonYes.setChecked(true);

            } else if (radioButtonNo.getText().toString().equals(selectedNote.getGroupRisky())) {
                radioButtonNo.setChecked(true);

            } else if (radioButtonNothing.getText().toString().equals(selectedNote.getGroupRisky())) {
                radioButtonNothing.setChecked(true);

            }
            purposeEdit.setText(selectedNote.getPurpose());
            titleEdit.setText(selectedNote.getTitle());
            destinationEdit.setText(selectedNote.getDestination());
            dateButton.setText(selectedNote.getDatetime());
            timeButton.setText(selectedNote.getTime());
            descEdit.setText(selectedNote.getDescription());
        }else {
            deleteButton.setVisibility(View.INVISIBLE);
            listCostButton.setVisibility(View.INVISIBLE);


        }
    }

    private void initWidgets()
    {

        error = findViewById(R.id.error);
        titleEdit = findViewById(R.id.titleEdit);
        deleteButton = findViewById(R.id.deleteNoteButton);
        descEdit = findViewById(R.id.descriptionEdit);
        listCostButton = findViewById(R.id.list_cost);
        purposeEdit = findViewById(R.id.purposeEdit);
        destinationEdit = findViewById(R.id.destinationEdit);


        dateButton = findViewById(R.id.datePickerButton);
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        dateButton.setText(mDay + "-" + (mMonth + 1) + "-" + year);


        timeButton = findViewById(R.id.btn_time);
        final Calendar time = Calendar.getInstance();
        mHour = time.get(Calendar.HOUR_OF_DAY);
        mMinute = time.get(Calendar.MINUTE);
        timeButton.setText(mHour + ":" + mMinute);


        radioGroupTrip= (RadioGroup) this.findViewById(R.id.radioGroup_trip);
        radioButtonYes = (RadioButton) this.findViewById(R.id.radioButton_yes);
        radioButtonNo  =  (RadioButton)this.findViewById(R.id.radioButton_no);
        radioButtonNothing =  (RadioButton)this.findViewById(R.id.radioButton_nothing);
        this.radioButtonNothing.setChecked(true);



        this.radioGroupTrip.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                groupTrip(group, checkedId);
            }
        });

    }

    private void groupTrip(RadioGroup group, int checkedId) {
        int checkedRadioId = group.getCheckedRadioButtonId();

        if(checkedRadioId == R.id.radioButton_yes ) {
            this.radioButtonYes.setChecked(true);
        } else if(checkedRadioId == R.id.radioButton_no) {
            this.radioButtonNo.setChecked(true);
        } else if(checkedRadioId == R.id.radioButton_nothing) {
            this.radioButtonNothing.setChecked(true);
        }

    }

    public void saveNote(View view) {
        String title, desc, date,time, groupTrip, purpose , destination;
        int groupTripCheckedRadioButtonId = this.radioGroupTrip.getCheckedRadioButtonId();
        radioButtonTrip = (RadioButton) this.findViewById(groupTripCheckedRadioButtonId);

        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);

        if(TextUtils.isEmpty(titleEdit.getText()) || TextUtils.isEmpty(descEdit.getText()) || TextUtils.isEmpty(purposeEdit.getText()) || TextUtils.isEmpty(destinationEdit.getText()) ){
            error.setText("Enter all the entries");

        }else {

            groupTrip = String.valueOf(radioButtonTrip.getText());
            destination = String.valueOf(destinationEdit.getText());
            title = String.valueOf(titleEdit.getText());
            desc = String.valueOf(descEdit.getText());
            date = String.valueOf(dateButton.getText());
            time = String.valueOf(timeButton.getText());
            purpose = String.valueOf(purposeEdit.getText());

            if (selectedNote == null){
                int id =  Trip.notes.size();

                Trip newNote = new Trip(id,title,desc,date,time,groupTrip,purpose,destination);
                Trip.notes.add(newNote);
                sqLiteManager.addTripIntoDatabase(newNote);

            }else{

                selectedNote.setTitle(title);
                selectedNote.setDatetime(date);
                selectedNote.setDestination(destination);
                selectedNote.setTime(time);
                selectedNote.setPurpose(purpose);
                selectedNote.setGroupRisky(groupTrip);
                selectedNote.setDescription(desc);
                sqLiteManager.updateTripDB(selectedNote);

            }

            finish();
        }





    }

    public void deleteNote(View view) {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);

        sqLiteManager.deleteATrip(selectedNote.getId());
        Intent backHome = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(backHome);

    }



    public void openTime(View view) {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        timeButton.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();


    }

    public void listCost(View view) {

        Intent listCostIntent = new Intent(this, ListOfExpenses.class);
        listCostIntent.putExtra(Costs.COST_LIST_EXTRA, selectedNote.getId());

        startActivity(listCostIntent);

    }

    public void openDatePicker(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        dateButton.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, year, mMonth, mDay);

        datePickerDialog.show();


    }


}