package org.example.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Termin {

    private int termin_id;
    private int izvodjenje_id;
    private LocalDate datum;
    private LocalTime vremePocetka;
    private LocalTime vremeZavrsetka;

    public Termin(int termin_id, int izvodjenje_id, LocalDate datum, LocalTime vremePocetka, LocalTime vremeZavrsetka) {
        this.termin_id = termin_id;
        this.izvodjenje_id = izvodjenje_id;
        this.datum = datum;
        this.vremePocetka = vremePocetka;
        this.vremeZavrsetka = vremeZavrsetka;
    }

    public int getTermin_id() { return termin_id; }
    public void setTermin_id(int termin_id) { this.termin_id = termin_id; }

    public int getIzvodjenje_id() { return izvodjenje_id; }
    public void setIzvodjenje_id(int izvodjenje_id) { this.izvodjenje_id = izvodjenje_id; }

    public LocalDate getDatum() { return datum; }
    public void setDatum(LocalDate datum) { this.datum = datum; }

    public LocalTime getVremePocetka() { return vremePocetka; }
    public void setVremePocetka(LocalTime vremePocetka) { this.vremePocetka = vremePocetka; }

    public LocalTime getVremeZavrsetka() { return vremeZavrsetka; }
    public void setVremeZavrsetka(LocalTime vremeZavrsetka) { this.vremeZavrsetka = vremeZavrsetka; }
}