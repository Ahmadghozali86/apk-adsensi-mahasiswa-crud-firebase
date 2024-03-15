package com.example.absensi_mahasiswa;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context context;
    private String currentSelectedOption = ""; // Deklarasi currentSelectedOption di sini

    private List<DataMahasiswa> dataList;
    private List<DataAbsensi> dataAbsensiList; // Menyimpan data absensi



    public MyAdapter(Context context, List<DataMahasiswa> dataList) {
        this.context = context;
        this.dataList = dataList;
        dataAbsensiList = new ArrayList<>(); // Inisialisasi list DataAbsensi

    }

    public List<DataAbsensi> getDataAbsensiList() {
        return dataAbsensiList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DataMahasiswa currentItem = dataList.get(position);


        ///menamppilkan...
        holder.namaView.setText(currentItem.getNama_Mahasiswa());
        holder.nimView.setText(currentItem.getNIM_Mahasiswa());

        holder.hapusDataMahasiswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickedPosition = holder.getAdapterPosition(); // Mendapatkan posisi item yang diklik
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    DataMahasiswa clickedItem = dataList.get(clickedPosition);
                    String key = clickedItem.getKey(); // Ambil kunci item untuk dihapus dari Firebase

                    if (key != null && !TextUtils.isEmpty(key)) {
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("MAHASISWA").child(key);
                        databaseReference.removeValue() // Hapus data dari Firebase
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // Hapus item dari RecyclerView
                                        dataList.remove(clickedPosition);
                                        notifyItemRemoved(clickedPosition);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Penanganan jika gagal menghapus dari Firebase
                                        Log.e("MyAdapter", "Error deleting data: " + e.getMessage());
                                    }
                                });
                    }
                }
            }
        });


        holder.radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = group.findViewById(checkedId);
            if (radioButton != null && radioButton.isChecked()) {
                String namaMahasiswa = currentItem.getNama_Mahasiswa();
                String selectedOption = radioButton.getText().toString();

                // Membuat objek DataAbsensi baru
                DataAbsensi dataAbsensi = new DataAbsensi(namaMahasiswa, selectedOption);

                // Menambahkan objek DataAbsensi ke dalam list
                dataAbsensiList.add(dataAbsensi);
            }
        });
    }
    public void clearAllRadioButtons() {
        for (int i = 0; i < dataList.size(); i++) {
            dataList.get(i).setSelectedOption(""); // Setiap data di dalam list diset menjadi kosong (tidak dipilih)
            notifyItemChanged(i); // Memperbarui tampilan item di RecyclerView
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView namaView, nimView;
        CardView recCard;
        RadioGroup radioGroup;
        ImageView hapusDataMahasiswa;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recCard = itemView.findViewById(R.id.recCard);
            namaView = itemView.findViewById(R.id.namaVIEW);
            nimView = itemView.findViewById(R.id.nimVIEW);
            radioGroup = itemView.findViewById(R.id.radioGroup); // Ganti dengan ID RadioGroup di layout recycler_item.xml
            hapusDataMahasiswa = itemView.findViewById(R.id.hapus);
        }
    }
}

