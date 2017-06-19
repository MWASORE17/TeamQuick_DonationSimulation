package com.simulasi_donasi.model.entity;

import java.io.Serializable;
import java.util.ArrayList;


public class Data implements Serializable {
    private int id;
    private int img;
    private String judul;
    private String kategori;
    private String informasi;
    private String lokasi;


    public static int _id = 1;

    public static ArrayList<Data> datas = new ArrayList<>();

    public Data(int img, String judul,String kategori, String informasi, String lokasi) {
        this.img = img;
        this.judul = judul;
        this.kategori = kategori;
        this.informasi = informasi;
        this.lokasi = lokasi;
        this.id = _id;
        _id++;
    }
    public int getId() {
        return id;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getInformasi() {
        return informasi;
    }

    public void setInformasi(String informasi) {
        this.informasi = informasi;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

}