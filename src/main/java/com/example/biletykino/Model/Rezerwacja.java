package com.example.biletykino.Model;

import javax.persistence.*;

@Entity
@Table(name = "Rezerwacja")
public class Rezerwacja {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    public Integer getId() {
        return id;
    }

    @OneToOne
    @JoinColumn(name = "seans_id",referencedColumnName = "id")
    private Seans seans;
    private int rząd;//numer rzędu
    private int miejsce;//numer miejsca w rzędzie
    @Column(name = "imię")
    private String Imię=null;//Imię rezerwującego
    @Column(name = "nazwisko")
    private String Nazwisko=null;//Nazwisko rezerwującego

    public Seans getSeans() {
        return seans;
    }

    public void setSeans(Seans seans) {
        this.seans = seans;
    }

    public int getRząd() {
        return rząd;
    }

    public void setRząd(int rząd) {
        this.rząd = rząd;
    }

    public int getMiejsce() {
        return miejsce;
    }

    public void setMiejsce(int miejsce) {
        this.miejsce = miejsce;
    }

    public String getImię() {
        return Imię;
    }

    public void setImię(String imię) {
        Imię = imię;
    }

    public String getNazwisko() {
        return Nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        Nazwisko = nazwisko;
    }
}
