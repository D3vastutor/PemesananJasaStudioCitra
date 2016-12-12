package com.razusdev.pemesananjasastudiocitra;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    //mendefinisikan objek Firebase
    private FirebaseAuth firebaseAuth;

    //mendefinisikan objek
    private TextView textViewUserEmail;
    private Button tombolLogout;

    //mendefinisikan database reference
    private DatabaseReference databaseReference;

    //objek untuk penyimpanan database
    private EditText editTextNama, editTextAlamat;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //inisialisasi FIrebase
        firebaseAuth = FirebaseAuth.getInstance();

        //memberikan kondisi jika blum ada user yang login
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();

        editTextNama = (EditText) findViewById(R.id.editTextNama);
        editTextAlamat = (EditText) findViewById(R.id.editTextAlamat);
        buttonSave = (Button) findViewById(R.id.tombolSave);

        //memanggil user yang sedang login
        FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        tombolLogout = (Button) findViewById(R.id.tombolLogOut);

        //menampilkan salam kepada user yang sudah login
        textViewUserEmail.setText("Selamat Datang " + user.getEmail());

        //menambahkan listener pada tombol
        tombolLogout.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
    }

    //method untuk menyimpan informasi kedalam database
    private void saveUserInformation() {

        //mengambil value
        String name = editTextNama.getText().toString().trim();
        String address = editTextAlamat.getText().toString().trim();

        //membuat objek userInformation
        UserInformation userInformation = new UserInformation(name, address);

        //mengambil data user yang sedang login
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //menyimpan data ke firebase
        /*
        pertama membuat child baru di firebase dengan unique id dari login user,
        lalu di bawahnyalah tempat menyimpan user information.
        menyimpan data ini menggunakan setValue method, method ini menggunakan java objek
         */
        databaseReference.child(user.getUid()).setValue(userInformation);

        //pesan jika berhasil
        Toast.makeText(this, "Informasi tersimpan !", Toast.LENGTH_LONG).show();
    }

    //method yang memberikan kondisi saat ditekan tombol Logout
    @Override
    public void onClick(View view) {

        if (view == buttonSave) {
            saveUserInformation();
        }
        if (view == tombolLogout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    //method untuk memvberikan kondisi pada tombol back pada ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
                finish();
                startActivity(new Intent(this, NavigasiActivity.class));
        return super.onOptionsItemSelected(item);
    }
}
