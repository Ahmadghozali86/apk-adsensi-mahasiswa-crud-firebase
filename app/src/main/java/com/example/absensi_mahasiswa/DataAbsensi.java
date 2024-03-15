package com.example.absensi_mahasiswa;

public class DataAbsensi {
    private String Nama;
    private String Keterangan;
    private String key;

    public DataAbsensi(){

    }

    public DataAbsensi(String nama, String keterangan) {
        Nama = nama;
        Keterangan = keterangan;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getKeterangan() {
        return Keterangan;
    }

    public void setKeterangan(String keterangan) {
        Keterangan = keterangan;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
