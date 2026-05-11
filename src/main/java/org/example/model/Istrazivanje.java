package org.example.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class Istrazivanje {

    @Getter
    private int istrazivanje_id;
    private String naziv;
    private int protokol_id;

    public Istrazivanje(int istrazivanje_id, String naziv, int protokol_id) {
        this.istrazivanje_id = istrazivanje_id;
        this.naziv = naziv;
        this.protokol_id = protokol_id;
    }

    public static List<Istrazivanje> readAll(Connection connection) {
        String query = "SELECT istrazivanje_id, naziv, protokol_id FROM istrazivanje";
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            List<Istrazivanje> istrazivanja = new ArrayList<>();
            while (rs.next()) {
                int id          = rs.getInt("istrazivanje_id");
                String naziv      = rs.getString("naziv");
                int protokol_id  = rs.getInt("protokol_id");


                istrazivanja.add(new Istrazivanje(id, naziv, protokol_id));
            }
            return istrazivanja;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() { return naziv; }
}