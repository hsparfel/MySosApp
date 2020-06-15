package com.pouillos.mysosapp.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Antecedent {

    @Id
    private Long id;

    @NotNull
    private String nom;

    @Generated(hash = 1641070997)
    public Antecedent(Long id, @NotNull String nom) {
        this.id = id;
        this.nom = nom;
    }

    @Generated(hash = 1096248326)
    public Antecedent() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
