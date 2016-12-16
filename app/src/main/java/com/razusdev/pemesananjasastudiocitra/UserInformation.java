package com.razusdev.pemesananjasastudiocitra;

/**
 * Created by RAZUS on 08/12/2016.
 */

public class UserInformation {

    public String nm;
    public String almt;

    public UserInformation(){
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    //method untuk menginisialisasi data apa saja yg akan disimpan
    public UserInformation(String nama, String alamat){
        this.nm = nama;
        this.almt = alamat;
    }


}
