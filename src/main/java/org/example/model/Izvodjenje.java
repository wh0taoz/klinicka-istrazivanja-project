package org.example.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Izvodjenje {

    public enum Status {
        planirano, zapoceto, otkazano, uspesno, neuspesno
    }

    private int izvodjenje_id;
    private int istrazivanje_id;
    private int bolnica_id;
    private LocalDate datum;
    private Status status;
    private String naziv;

    public Izvodjenje(int izvodjenje_id, int istrazivanje_id, int bolnica_id, LocalDate datum, Status status) {
        this.izvodjenje_id = izvodjenje_id;
        this.istrazivanje_id = istrazivanje_id;
        this.bolnica_id = bolnica_id;
        this.datum = datum;
        this.status = status;
    }

    public static List<Izvodjenje> readAllSaNazivom(Connection connection) {
        String query = """
            SELECT iz.izvodjenje_id, iz.istrazivanje_id, iz.bolnica_id, iz.datum, iz.status,
                   i.naziv
            FROM izvodjenje iz
            JOIN istrazivanje i ON iz.istrazivanje_id = i.istrazivanje_id
            """;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            List<Izvodjenje> izvodjenja = new ArrayList<>();
            while (rs.next()) {
                int id             = rs.getInt("izvodjenje_id");
                int istrazivanjeId = rs.getInt("istrazivanje_id");
                int bolnicaId      = rs.getInt("bolnica_id");
                LocalDate datum    = rs.getDate("datum").toLocalDate();
                Status status      = Status.valueOf(rs.getString("status"));

                Izvodjenje izv = new Izvodjenje(id, istrazivanjeId, bolnicaId, datum, status);
                izv.setNaziv(rs.getString("naziv")); // vidi dole
                izvodjenja.add(izv);
            }
            return izvodjenja;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    public String getNaziv() {
        return naziv;
    }
    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @Override
    public String toString() {
        return naziv;
    }
}