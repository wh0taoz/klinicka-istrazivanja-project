package org.example.model;

public class Istrazivanje {

    private int istrazivanje_id;
    private String naziv;
    private int protokol_id;

    public Istrazivanje(int istrazivanje_id, String naziv, int protokol_id) {
        this.istrazivanje_id = istrazivanje_id;
        this.naziv = naziv;
        this.protokol_id = protokol_id;
    }

    public int getIstrazivanje_id() { return istrazivanje_id; }
    public void setIstrazivanje_id(int istrazivanje_id) { this.istrazivanje_id = istrazivanje_id; }

    public String getNaziv() { return naziv; }
    public void setNaziv(String naziv) { this.naziv = naziv; }

    public int getProtokolId() { return protokol_id; }
    public void setProtokolId(int protokol_id) { this.protokol_id = protokol_id; }

    @Override
    public String toString() { return naziv; }
}