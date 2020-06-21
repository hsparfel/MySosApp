package com.pouillos.mysosapp.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Contact {

    @Id
    private Long id;

    @NotNull
    private String nom;

    @NotNull
    private String telephone;

    private boolean isContactAccident;
    private boolean isContactEnlevement;

    @Generated(hash = 157678141)
    public Contact(Long id, @NotNull String nom, @NotNull String telephone,
            boolean isContactAccident, boolean isContactEnlevement) {
        this.id = id;
        this.nom = nom;
        this.telephone = telephone;
        this.isContactAccident = isContactAccident;
        this.isContactEnlevement = isContactEnlevement;
    }

    @Generated(hash = 672515148)
    public Contact() {
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

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return nom + " - " + telephone;
    }

    public boolean getIsContactAccident() {
        return this.isContactAccident;
    }

    public void setIsContactAccident(boolean isContactAccident) {
        this.isContactAccident = isContactAccident;
    }

    public boolean getIsContactEnlevement() {
        return this.isContactEnlevement;
    }

    public void setIsContactEnlevement(boolean isContactEnlevement) {
        this.isContactEnlevement = isContactEnlevement;
    }


}
