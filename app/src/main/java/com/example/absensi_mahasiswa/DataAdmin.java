package com.example.absensi_mahasiswa;

public class DataAdmin {
    private String email;
    private String nama;
    private  String kelamin;
    private String nip;
    private String pendidikan;

    public DataAdmin(){}


    public DataAdmin(String email, String nama, String kelamin, String nip, String pendidikan) {
        this.email = email;
        this.nama = nama;
        this.kelamin = kelamin;
        this.nip = nip;
        this.pendidikan = pendidikan;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKelamin() {
        return kelamin;
    }

    public void setKelamin(String kelamin) {
        this.kelamin = kelamin;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getPendidikan() {
        return pendidikan;
    }

    public void setPendidikan(String pendidikan) {
        this.pendidikan = pendidikan;
    }
}
