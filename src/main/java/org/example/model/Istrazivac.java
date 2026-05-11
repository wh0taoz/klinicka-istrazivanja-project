package org.example.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Istrazivac {

    public enum Tip {
        dizajner, izvodjac, oba
    }

    private int istrazivac_id;
    private String ime;
    private String prezime;
    private String email;
    private String kvalifikacije;
    private Tip tip;

    public Istrazivac(int istrazivac_id, String ime, String prezime, String email, String kvalifikacije, Tip tip) {
        this.istrazivac_id = istrazivac_id;
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.kvalifikacije = kvalifikacije;
        this.tip = tip;
    }

    public static List<Istrazivac> readAll(Connection connection) {
        String query = "SELECT istrazivac_id, ime, prezime, email, kvalifikacije, tip FROM istrazivac";
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            List<Istrazivac> istrazivaci = new ArrayList<>();
            while (rs.next()) {
                int id          = rs.getInt("istrazivac_id");
                String ime      = rs.getString("ime");
                String prezime  = rs.getString("prezime");
                String email    = rs.getString("email");
                String kval     = rs.getString("kvalifikacije");
                Istrazivac.Tip tip = Istrazivac.Tip.valueOf(rs.getString("tip"));

                istrazivaci.add(new Istrazivac(id, ime, prezime, email, kval, tip));
            }
            return istrazivaci;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getIstrazivac_id() { return istrazivac_id; }
    public void setIstrazivac_id(int istrazivac_id) { this.istrazivac_id = istrazivac_id; }

    public String getIme() { return ime; }
    public void setIme(String ime) { this.ime = ime; }

    public String getPrezime() { return prezime; }
    public void setPrezime(String prezime) { this.prezime = prezime; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getKvalifikacije() { return kvalifikacije; }
    public void setKvalifikacije(String kvalifikacije) { this.kvalifikacije = kvalifikacije; }

    public Tip getTip() { return tip; }
    public void setTip(Tip tip) { this.tip = tip; }

    public String getPunoIme() { return ime + " " + prezime; }

    @Override
    public String toString() { return getPunoIme(); }
}