package com.example.biletykino.Model;

import javax.persistence.*;

@Entity
@Table(name = "SalaKinowa")
public class SalaKinowa {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "rzędy")
    private int rzędy;//Ilość rzędów

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "miejsca")
    private int miejsca;//Ilość miejsc w każdym rzędzie

    public int getRzędy() {
        return rzędy;
    }

    public int getMiejsca() {
        return miejsca;
    }

    public int getId() {
        return id;
    }

    public void setRzędy(int rzędy) {
        this.rzędy = rzędy;
    }

    public void setMiejsca(int miejsca) {
        this.miejsca = miejsca;
    }
}
