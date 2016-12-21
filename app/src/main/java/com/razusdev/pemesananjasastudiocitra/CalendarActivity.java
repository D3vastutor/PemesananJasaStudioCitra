package com.razusdev.pemesananjasastudiocitra;

import android.app.DatePickerDialog;
import java.util.Calendar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;


public class CalendarActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    CalendarView calendarView;
    TextView textViewTanggal;
    Button btnDatePicker;
    Button btnPesan;
    private int mYear, mMonth, mDay;

    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        /*calendarView = (CalendarView) findViewById(R.id.kalender);
        textViewTanggal = (TextView) findViewById(R.id.textViewTanggal);*/

        btnDatePicker = (Button) findViewById(R.id.Pilih);
        btnPesan = (Button) findViewById(R.id.Pesan);

        /*calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {

                textViewTanggal.setText("Tanggal : " + dayOfMonth + " / " + (month + 1) + " / " + year + " ?");
                dateSelected = "" + month + "/" + dayOfMonth + "/" + year;


            }
        });*/
        btnDatePicker.setOnClickListener(this);
        btnPesan.setOnClickListener(this);
    }
    private void tanggalPesan(){
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        final DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int iyear, int imonthOfYear, int idayOfMonth) {
                String day = String.valueOf(idayOfMonth);
                String month = String.valueOf(imonthOfYear + 1);
                String year = String.valueOf(iyear);

                DataPemesanan dataPemesanan = new DataPemesanan(day + month + year);

                FirebaseUser user = firebaseAuth.getCurrentUser();

                DatabaseReference Tanggal = databaseReference.child(user.getUid()).child("Tanggal");
                Tanggal.setValue(dataPemesanan);
                //btnDatePicker.setEnabled(false);

            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();

        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        Toast.makeText(this, "Kapan tanggal acaranya?", Toast.LENGTH_LONG).show();


        /*datePickerDialog.getDatePicker().setEnabled(false);*/

        /*SimpleDateFormat dateFormatddMMyyyy = new SimpleDateFormat("ddMMyyyy");
        String dateString = dateFormatddMMyyyy.format(dateSelected);

        DataPemesanan dataPemesanan = new DataPemesanan(dateString);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        DatabaseReference Tanggal = databaseReference.child(user.getUid()).child("Tangal");
        Tanggal.setValue(dataPemesanan);

        Toast.makeText(this, "Berhasil !!", Toast.LENGTH_LONG).show();*/

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

    @Override
    public void onClick(View view) {
        if (view == btnDatePicker){
            tanggalPesan();
        }
        if (view == btnPesan){

        }

    }
}
