package com.pouillos.mysosapp.entities;

import com.pouillos.mysosapp.dao.ContactDao;
import com.pouillos.mysosapp.dao.DaoSession;
import com.pouillos.mysosapp.dao.HistoriqueSmsDao;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Date;

@Entity
public class HistoriqueAppel {

    @Id
    private Long id;

    @NotNull
    private String typeAppel;

    @NotNull
    private String numeroTel;

    @NotNull
    private Date date;

    @NotNull
    private String dateString;

    @NotNull
    private double latitude;

    @NotNull
    private double longitude;

    @NotNull
    private String adresseGeo;

    @Generated(hash = 303024697)
    public HistoriqueAppel(Long id, @NotNull String typeAppel,
            @NotNull String numeroTel, @NotNull Date date,
            @NotNull String dateString, double latitude, double longitude,
            @NotNull String adresseGeo) {
        this.id = id;
        this.typeAppel = typeAppel;
        this.numeroTel = numeroTel;
        this.date = date;
        this.dateString = dateString;
        this.latitude = latitude;
        this.longitude = longitude;
        this.adresseGeo = adresseGeo;
    }

    @Generated(hash = 1837061812)
    public HistoriqueAppel() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeAppel() {
        return this.typeAppel;
    }

    public void setTypeAppel(String typeAppel) {
        this.typeAppel = typeAppel;
    }

    public String getNumeroTel() {
        return this.numeroTel;
    }

    public void setNumeroTel(String numeroTel) {
        this.numeroTel = numeroTel;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateString() {
        return this.dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAdresseGeo() {
        return this.adresseGeo;
    }

    public void setAdresseGeo(String adresseGeo) {
        this.adresseGeo = adresseGeo;
    }
}
