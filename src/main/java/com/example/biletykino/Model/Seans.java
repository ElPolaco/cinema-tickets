package com.example.biletykino.Model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Seans")
public class Seans {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String tytuł;
    private int czas;//czas trwania seansu w minutach
    private Date data;//data seansu

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCzas() {
        return czas;
    }

    public void setCzas(int czas) {
        this.czas = czas;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public SalaKinowa getSalaKinowa() {
        return salaKinowa;
    }

    public void setSalaKinowa(SalaKinowa salaKinowa) {
        this.salaKinowa = salaKinowa;
    }

    @OneToOne
    @JoinColumn(name = "sala_id",referencedColumnName = "id")
    private SalaKinowa salaKinowa;

    public Integer getId() {
        return id;
    }

    public void setTytuł(String tytuł) {
        this.tytuł = tytuł;
    }

    public String getTytul() {
        return tytuł;
    }
}

