package org.example.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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

    public static List<Termin> readAll(Connection connection) {
        String query = "SELECT termin_id, izvodjenje_id, datum, vreme_pocetka, vreme_zavrsetka FROM termin";
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            List<Termin> termini = new ArrayList<>();
            while (rs.next()) {
                int terminId      = rs.getInt("termin_id");
                int izvodjenjeId  = rs.getInt("izvodjenje_id");
                LocalDate datum   = rs.getDate("datum").toLocalDate();
                LocalTime pocetak = rs.getTime("vreme_pocetka").toLocalTime();
                LocalTime zavrsetak = rs.getTime("vreme_zavrsetka").toLocalTime();

                termini.add(new Termin(terminId, izvodjenjeId, datum, pocetak, zavrsetak));
            }
            return termini;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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