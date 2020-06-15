package com.pouillos.mysosapp.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class TempoMorse {

    @Id
    private Long id;

    @NotNull
    private long point;

    @NotNull
    private long tiret;

    @NotNull
    private long intervalSigne;

    @NotNull
    private long intervalLettre;

    @NotNull
    private long intervalMot;

    @Generated(hash = 957495687)
    public TempoMorse(Long id, long point, long tiret, long intervalSigne,
            long intervalLettre, long intervalMot) {
        this.id = id;
        this.point = point;
        this.tiret = tiret;
        this.intervalSigne = intervalSigne;
        this.intervalLettre = intervalLettre;
        this.intervalMot = intervalMot;
    }

    @Generated(hash = 1357624110)
    public TempoMorse() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getPoint() {
        return this.point;
    }

    public void setPoint(long point) {
        this.point = point;
    }

    public long getTiret() {
        return this.tiret;
    }

    public void setTiret(long tiret) {
        this.tiret = tiret;
    }

    public long getIntervalSigne() {
        return this.intervalSigne;
    }

    public void setIntervalSigne(long intervalSigne) {
        this.intervalSigne = intervalSigne;
    }

    public long getIntervalLettre() {
        return this.intervalLettre;
    }

    public void setIntervalLettre(long intervalLettre) {
        this.intervalLettre = intervalLettre;
    }

    public long getIntervalMot() {
        return this.intervalMot;
    }

    public void setIntervalMot(long intervalMot) {
        this.intervalMot = intervalMot;
    }



}
