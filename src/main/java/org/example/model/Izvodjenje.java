package org.example.model;

import lombok.Getter;
import lombok.Setter;

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

    public Izvodjenje(int izvodjenje_id, int istrazivanje_id, int bolnica_id, LocalDate datum, Status status) {
        this.izvodjenje_id = izvodjenje_id;
        this.istrazivanje_id = istrazivanje_id;
        this.bolnica_id = bolnica_id;
        this.datum = datum;
        this.status = status;
    }
}