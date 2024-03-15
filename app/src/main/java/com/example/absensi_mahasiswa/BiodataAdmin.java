package com.example.absensi_mahasiswa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;

public class BiodataAdmin extends AppCompatActivity {


    TextInputEditText emailAdmin, namaAdmin, kelaminAdmin, nipAdmin, pendidikanAdmin;
    Button buttonSimpan;
    ProgressBar progressBar;
    DataAdmin userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biodata_admin);

        emailAdmin = findViewById(R.id.emailku);
        namaAdmin = findViewById(R.id.nama);
        kelaminAdmin = findViewById(R.id.kelamin);
        nipAdmin = findViewById(R.id.nip);
        pendidikanAdmin = findViewById(R.id.pendidikan);

        buttonSimpan = findViewById(R.id.btn_simpan);
        progressBar = findViewById(R.id.progressBar);

        getData();

        buttonSimpan.setOnClickListener((v -> {
            setNama();
        }));



    }
    void setNama(){
        String adminEmail = emailAdmin.getText().toString();
        String adminNama = namaAdmin.getText().toString();
        String adminKelamin = kelaminAdmin.getText().toString();
        String adminNip = nipAdmin.getText().toString();
        String adminPendidikan = pendidikanAdmin.getText().toString();

        userModel= new DataAdmin(adminEmail, adminNama, adminKelamin, adminNip, adminPendidikan);
        setInProgress(true);

        FirebaseUtil.currentUserDetails().set(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                setInProgress(false);
                if(task.isSuccessful()){
                    Intent intent = new Intent(BiodataAdmin.this,HomeMenu.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                    startActivity(intent);
                }
            }
        });
    }
    void setInProgress(boolean inProgress){
        if(inProgress){
            progressBar.setVisibility(View.VISIBLE);
            buttonSimpan.setVisibility(View.GONE);
        }else{
            progressBar.setVisibility(View.GONE);
            buttonSimpan.setVisibility(View.VISIBLE);
        }
    }
    void getData(){
        setInProgress(true);
        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                setInProgress(false);
                if(task.isSuccessful()){
                    userModel =    task.getResult().toObject(DataAdmin.class);
                    if(userModel!=null){
                        emailAdmin.setText(userModel.getEmail());
                        namaAdmin.setText(userModel.getNama());
                        kelaminAdmin.setText(userModel.getKelamin());
                        nipAdmin.setText(userModel.getNip());
                        pendidikanAdmin.setText(userModel.getPendidikan());

                    }
                }
            }
        });
    }
}