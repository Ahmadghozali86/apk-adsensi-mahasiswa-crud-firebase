package com.example.absensi_mahasiswa;

public class DataMahasiswa {

    private String Absensi;
    private String key;
    private String Nama_Mahasiswa;
    private String NIM_Mahasiswa;
    private String selectedOption;

    public DataMahasiswa(){

    }
    public DataMahasiswa(String nama_Mahasiswa, String NIM_Mahasiswa) {
        Nama_Mahasiswa = nama_Mahasiswa;
        this.NIM_Mahasiswa = NIM_Mahasiswa;
        this.selectedOption = ""; // Default tidak ada yang dipilih

    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getNama_Mahasiswa() {
        return Nama_Mahasiswa;
    }

    public void setNama_Mahasiswa(String nama_Mahasiswa) {
        Nama_Mahasiswa = nama_Mahasiswa;
    }

    public String getNIM_Mahasiswa() {
        return NIM_Mahasiswa;
    }

    public void setNIM_Mahasiswa(String NIM_Mahasiswa) {
        this.NIM_Mahasiswa = NIM_Mahasiswa;
    }
    public String getSelectedOption() {
        return Absensi;
    }

    public void setSelectedOption(String selectedOption) {
        this.Absensi = selectedOption;
    }


}
