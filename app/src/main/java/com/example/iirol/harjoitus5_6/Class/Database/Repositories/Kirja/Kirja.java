package com.example.iirol.harjoitus5_6.Class.Database.Repositories.Kirja;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Kirja {

    private Integer id;
    private int numero;
    private String nimi;
    private int painos;
    private Date hankintapvm;
    private SimpleDateFormat sdf;

    public Kirja(Integer id, int numero, String nimi, int painos, Date hankintapvm) {
        this.id = id;
        this.numero = numero;
        this.nimi = nimi;
        this.painos = painos;
        this.hankintapvm = hankintapvm;

        this.sdf = new SimpleDateFormat("dd.MM.yyyy - h:ma");
    }
    public Kirja(Integer id, int numero, String nimi, int painos, long hankintapvmms) {
        this(id, numero, nimi, painos, new java.util.Date(hankintapvmms));
    }
    public Kirja(int numero, String nimi, int painos, Date hankintapvm) {
        this(null, numero, nimi, painos, hankintapvm);
    }
    public Kirja(int numero, String nimi, int painos, long hankintapvmms) {
        this(null, numero, nimi, painos, new java.util.Date(hankintapvmms));
    }

    public Integer getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getNumero() {
        return this.numero;
    }
    public void setNumero(int numero) {
        this.numero = numero;
    }
    public String getNimi() {
        return this.nimi;
    }
    public void setNimi(String nimi) {
        this.nimi = nimi;
    }
    public int getPainos() {
        return this.painos;
    }
    public void setPainos(int painos) {
        this.painos = painos;
    }
    public Date getHankintapvm() {
        return this.hankintapvm;
    }
    public long getHankintapvmUnixTime() {
        return this.hankintapvm.getTime() / 1000L;
    }
    public String getHankintapvmString() {
        return this.sdf.format(this.hankintapvm);
    }
    public void setHankintapvm(Date hankintapvm) {
        this.hankintapvm = hankintapvm;
    }
    public void setHankintapvm(long hankintapvmUnixTime) {
        this.hankintapvm = new java.util.Date(hankintapvmUnixTime);
    }

    // @Object
    @Override public String toString() {
        return this.numero + ". " + this.nimi;
    }
}
