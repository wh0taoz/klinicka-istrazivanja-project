package org.example.model;

import lombok.Getter;
import lombok.Setter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDate;
@Getter
@Setter
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

    @Override
    public String toString() {
        return naziv;
    }
}