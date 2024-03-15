package com.example.absensi_mahasiswa;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FragmentAbsen extends Fragment {

    EditText namaBaru, nimBaru;
    Button dataKirim;
    TextView kirimAbsensi;
    RecyclerView recyclerView;
    List<DataMahasiswa> dataList;
    MyAdapter adapter;
    DatabaseReference databaseReference;

    public FragmentAbsen() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.absen, container, false);

        namaBaru = view.findViewById(R.id.addnamamahasiswa);
        nimBaru = view.findViewById(R.id.addnimmahasiswa);
        dataKirim = view.findViewById(R.id.tambahdata);
        kirimAbsensi = view.findViewById(R.id.kirimAbsensi);
        recyclerView = view.findViewById(R.id.recyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        dataList = new ArrayList<>();
        adapter = new MyAdapter(requireContext(), dataList);
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("MAHASISWA");

        // Mengambil data dari Firebase dan menampilkannya di RecyclerView
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    DataMahasiswa dataMahasiswa = itemSnapshot.getValue(DataMahasiswa.class);
                    dataMahasiswa.setKey(itemSnapshot.getKey());
                    dataList.add(dataMahasiswa);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Makanan", "Error: Gagal mengambil data dari Firebase: " + error.getMessage());
            }
        });

        // Menambahkan data mahasiswa baru ke Firebase
        dataKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String namaKirim = namaBaru.getText().toString();
                String nimKirim = nimBaru.getText().toString();

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
                String currentDate = sdf.format(new Date());

                DataMahasiswa dataMahasiswa = new DataMahasiswa(namaKirim, nimKirim);

                DatabaseReference mahasiswaRef = FirebaseDatabase.getInstance().getReference("MAHASISWA").child(currentDate);
                mahasiswaRef.push().setValue(dataMahasiswa)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    namaBaru.setText("");
                                    nimBaru.setText("");
                                    Toast.makeText(requireContext(), "Data mahasiswa berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(requireContext(), "Gagal menambahkan data mahasiswa", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


        // Mengirim data absensi ke Firebase saat tombol "Kirim Absensi" ditekan
        kirimAbsensi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<DataAbsensi> absensiList = adapter.getDataAbsensiList();
                //adapter.clearAllRadioButtons(); // Membersihkan semua pilihan radio button di RecyclerView
                kirimDataKeFirebase(absensiList);
            }
        });

        return view;
    }
    // Fungsi untuk mengirim data ke Firebase dengan child yang diubah
    private void kirimDataKeFirebase(List<DataAbsensi> absensiList) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        String currentDate = sdf.format(new Date());
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("DataAbsensi").child(currentDate);

        // Lakukan iterasi pada setiap objek DataAbsensi dalam absensiList
        for (DataAbsensi dataAbsensi : absensiList) {
            // Menyimpan data absensi ke Firebase
            databaseReference.push().setValue(dataAbsensi)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Tindakan yang diambil jika penyimpanan ke Firebase berhasil
                                Log.d("Firebase", "Data absensi berhasil dikirim ke Firebase");
                            } else {
                                // Tindakan yang diambil jika penyimpanan ke Firebase gagal
                                Log.e("Firebase", "Gagal mengirim data absensi ke Firebase: " + task.getException());
                            }
                        }
                    });
        }

        // Tampilkan pesan atau lakukan tindakan lain setelah mengirim data ke Firebase
        Toast.makeText(requireContext(), "Data absensi terkirim ke Firebase", Toast.LENGTH_SHORT).show();
    }
}
