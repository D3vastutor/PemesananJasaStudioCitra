package com.razusdev.pemesananjasastudiocitra;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.TextView;



public class CalendarActivity extends AppCompatActivity {

    CalendarView calendarView;
    TextView textViewTanggal;
    public String dateSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        calendarView = (CalendarView) findViewById(R.id.kalender);
        textViewTanggal = (TextView) findViewById(R.id.textViewTanggal);


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {

                textViewTanggal.setText("Tanggal : " + dayOfMonth + " / " + (month + 1) + " / " + year + " ?");

                dateSelected = "" + month + "/" + dayOfMonth + "/" + year;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
