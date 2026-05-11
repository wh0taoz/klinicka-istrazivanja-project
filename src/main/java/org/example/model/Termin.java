package org.example.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class Termin {

    private int termin_id;
    private int izvodjenje_id;
    private LocalDate datum;
    private LocalTime vremePocetka;
    private LocalTime vremeZavrsetka;
    private String nazivIstrazivanja;

    public Termin(int termin_id, int izvodjenje_id, LocalDate datum, LocalTime vremePocetka, LocalTime vremeZavrsetka) {
        this.termin_id = termin_id;
        this.izvodjenje_id = izvodjenje_id;
        this.datum = datum;
        this.vremePocetka = vremePocetka;
        this.vremeZavrsetka = vremeZavrsetka;
    }

    public static List<Termin> readAllSaNazivom(Connection connection) {
        String query = """
            SELECT t.termin_id, t.izvodjenje_id, t.datum, t.vreme_pocetka, t.vreme_zavrsetka,
                   i.naziv
            FROM termin t
            JOIN izvodjenje iz ON t.izvodjenje_id = iz.izvodjenje_id
            JOIN istrazivanje i ON iz.istrazivanje_id = i.istrazivanje_id
            """;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            List<Termin> termini = new ArrayList<>();
            while (rs.next()) {
                int terminId        = rs.getInt("termin_id");
                int izvodjenjeId    = rs.getInt("izvodjenje_id");
                LocalDate datum     = rs.getDate("datum").toLocalDate();
                LocalTime pocetak   = rs.getTime("vreme_pocetka").toLocalTime();
                LocalTime zavrsetak = rs.getTime("vreme_zavrsetka").toLocalTime();

                Termin t = new Termin(terminId, izvodjenjeId, datum, pocetak, zavrsetak);
                t.setNazivIstrazivanja(rs.getString("naziv"));
                termini.add(t);
            }
            return termini;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}