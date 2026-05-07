package org.example.model;

import java.time.LocalDate;

public class Izvodjenje {

    public enum Status {
        planirano, zapoceto, otkazano, uspesno, neuspesno
    }

    private int izvodjenje_id;
    private int istrazivanje_id;
    private int bolnica_id;
    private LocalDate datum;
    private Status status;

    public Izvodjenje(int izvodjenje_id, int istrazivanje_id, int bolnica_id, LocalDate datum, Status status) {
        this.izvodjenje_id = izvodjenje_id;
        this.istrazivanje_id = istrazivanje_id;
        this.bolnica_id = bolnica_id;
        this.datum = datum;
        this.status = status;
    }

    public int getIzvodjenje_id() { return izvodjenje_id; }
    public void setIzvodjenje_id(int izvodjenje_id) { this.izvodjenje_id = izvodjenje_id; }

    public int getIstrazivanje_id() { return istrazivanje_id; }
    public void setIstrazivanje_id(int istrazivanje_id) { this.istrazivanje_id = istrazivanje_id; }

    public int getBolnica_id() { return bolnica_id; }
    public void setBolnica_id(int bolnica_id) { this.bolnica_id = bolnica_id; }

    public LocalDate getDatum() { return datum; }
    public void setDatum(LocalDate datum) { this.datum = datum; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}