package com.pouillos.mysosapp.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Parametres {

    @Id
    private Long id;

    @NotNull
    private String numeroEurope;

    @NotNull
    private String numeroPompier;

    @NotNull
    private String numeroPolice;

    @NotNull
    private String numeroSamu;

    @NotNull
    private String numeroAeronautique;

    @NotNull
    private String numeroMer;

    @NotNull
    private String numeroSourd;

    @NotNull
    private String smsAccident;

    @NotNull
    private String smsEnlevement;

    @NotNull
    private String smsInformationAccidentDebut;

    @NotNull
    private String smsInformationEnlevementDebut;

    @NotNull
    private String smsInformationAccidentFin;

    @NotNull
    private String smsInformationEnlevementFin;

    @Generated(hash = 880007602)
    public Parametres(Long id, @NotNull String numeroEurope,
            @NotNull String numeroPompier, @NotNull String numeroPolice,
            @NotNull String numeroSamu, @NotNull String numeroAeronautique,
            @NotNull String numeroMer, @NotNull String numeroSourd,
            @NotNull String smsAccident, @NotNull String smsEnlevement,
            @NotNull String smsInformationAccidentDebut,
            @NotNull String smsInformationEnlevementDebut,
            @NotNull String smsInformationAccidentFin,
            @NotNull String smsInformationEnlevementFin) {
        this.id = id;
        this.numeroEurope = numeroEurope;
        this.numeroPompier = numeroPompier;
        this.numeroPolice = numeroPolice;
        this.numeroSamu = numeroSamu;
        this.numeroAeronautique = numeroAeronautique;
        this.numeroMer = numeroMer;
        this.numeroSourd = numeroSourd;
        this.smsAccident = smsAccident;
        this.smsEnlevement = smsEnlevement;
        this.smsInformationAccidentDebut = smsInformationAccidentDebut;
        this.smsInformationEnlevementDebut = smsInformationEnlevementDebut;
        this.smsInformationAccidentFin = smsInformationAccidentFin;
        this.smsInformationEnlevementFin = smsInformationEnlevementFin;
    }

    @Generated(hash = 1779120727)
    public Parametres() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroEurope() {
        return this.numeroEurope;
    }

    public void setNumeroEurope(String numeroEurope) {
        this.numeroEurope = numeroEurope;
    }

    public String getNumeroPompier() {
        return this.numeroPompier;
    }

    public void setNumeroPompier(String numeroPompier) {
        this.numeroPompier = numeroPompier;
    }

    public String getNumeroPolice() {
        return this.numeroPolice;
    }

    public void setNumeroPolice(String numeroPolice) {
        this.numeroPolice = numeroPolice;
    }

    public String getNumeroSamu() {
        return this.numeroSamu;
    }

    public void setNumeroSamu(String numeroSamu) {
        this.numeroSamu = numeroSamu;
    }

    public String getNumeroAeronautique() {
        return this.numeroAeronautique;
    }

    public void setNumeroAeronautique(String numeroAeronautique) {
        this.numeroAeronautique = numeroAeronautique;
    }

    public String getNumeroMer() {
        return this.numeroMer;
    }

    public void setNumeroMer(String numeroMer) {
        this.numeroMer = numeroMer;
    }

    public String getNumeroSourd() {
        return this.numeroSourd;
    }

    public void setNumeroSourd(String numeroSourd) {
        this.numeroSourd = numeroSourd;
    }

    public String getSmsAccident() {
        return this.smsAccident;
    }

    public void setSmsAccident(String smsAccident) {
        this.smsAccident = smsAccident;
    }

    public String getSmsEnlevement() {
        return this.smsEnlevement;
    }

    public void setSmsEnlevement(String smsEnlevement) {
        this.smsEnlevement = smsEnlevement;
    }

    public String getSmsInformationAccidentDebut() {
        return this.smsInformationAccidentDebut;
    }

    public void setSmsInformationAccidentDebut(String smsInformationAccidentDebut) {
        this.smsInformationAccidentDebut = smsInformationAccidentDebut;
    }

    public String getSmsInformationEnlevementDebut() {
        return this.smsInformationEnlevementDebut;
    }

    public void setSmsInformationEnlevementDebut(
            String smsInformationEnlevementDebut) {
        this.smsInformationEnlevementDebut = smsInformationEnlevementDebut;
    }

    public String getSmsInformationAccidentFin() {
        return this.smsInformationAccidentFin;
    }

    public void setSmsInformationAccidentFin(String smsInformationAccidentFin) {
        this.smsInformationAccidentFin = smsInformationAccidentFin;
    }

    public String getSmsInformationEnlevementFin() {
        return this.smsInformationEnlevementFin;
    }

    public void setSmsInformationEnlevementFin(String smsInformationEnlevementFin) {
        this.smsInformationEnlevementFin = smsInformationEnlevementFin;
    }



    
}
