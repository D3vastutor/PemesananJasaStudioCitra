package com.razusdev.pemesananjasastudiocitra;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //mendefinisikan tampilan objek
    private EditText editTextEmail, editTextPassword;
    private Button buttonDaftar;
    private ProgressDialog progressDialog;

    private TextView textViewSignIn;

    //mendefinisikan FIrebase Auth
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inisialisasi Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        //untuk kondisi saat user sudah login, jadi gak perlu login lagi
        if (firebaseAuth.getCurrentUser() != null) {
            finish();

            //lalu buka activity profile
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        //menginisialisasi tampilan
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewSignIn = (TextView) findViewById(R.id.textSignIn);
        buttonDaftar = (Button) findViewById(R.id.buttonSignUp);

        progressDialog = new ProgressDialog(this);

        //menambahkan listener pada button
        buttonDaftar.setOnClickListener(this);
        textViewSignIn.setOnClickListener(this);
    }

    private void registerUser() {

        //mengambil data email dan password dari edit text
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        //untuk mengatasi jika email dan password kosong
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Tolong masukan Email", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Tolong masukan Password", Toast.LENGTH_LONG).show();
            return;
        }
        //jika password dan Email tidak kosong akan menampilkan progres Dialog
        progressDialog.setMessage("Sedang mendaftarkan...");
        progressDialog.show();

        //method untuk membuat user baru
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        //mengecek jika pendaftaran sukses
                        if (task.isSuccessful()) {
                            finish();
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        } else {
                            Toast.makeText(MainActivity.this, "Gagal Mendaftarkan", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    public void onClick(View view) {

        if (view == buttonDaftar) {
            //memanggil method register on click
            registerUser();
        }
        if (view == textViewSignIn) {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
