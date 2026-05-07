package org.example.model;

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