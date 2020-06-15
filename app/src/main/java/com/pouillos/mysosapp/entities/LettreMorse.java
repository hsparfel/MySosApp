package com.pouillos.mysosapp.entities;

import com.pouillos.mysosapp.enumeration.SigneMorse;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.converter.PropertyConverter;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class LettreMorse {

    @Id
    private Long id;

    @NotNull
    @Convert(converter = SigneMorseConverter.class, columnType = Long.class)
    private SigneMorse premierCaractere;

    @Convert(converter = SigneMorseConverter.class, columnType = Long.class)
    private SigneMorse deuxiemeCaractere;

    @Convert(converter = SigneMorseConverter.class, columnType = Long.class)
    private SigneMorse troisiemeCaractere;

    @Convert(converter = SigneMorseConverter.class, columnType = Long.class)
    private SigneMorse quatriemeCaractere;

    @Convert(converter = SigneMorseConverter.class, columnType = Long.class)
    private SigneMorse cinquiemeCaractere;

    @Convert(converter = SigneMorseConverter.class, columnType = Long.class)
    private SigneMorse sixiemeCaractere;

    @Generated(hash = 29033894)
    public LettreMorse(Long id, @NotNull SigneMorse premierCaractere, SigneMorse deuxiemeCaractere,
            SigneMorse troisiemeCaractere, SigneMorse quatriemeCaractere, SigneMorse cinquiemeCaractere,
            SigneMorse sixiemeCaractere) {
        this.id = id;
        this.premierCaractere = premierCaractere;
        this.deuxiemeCaractere = deuxiemeCaractere;
        this.troisiemeCaractere = troisiemeCaractere;
        this.quatriemeCaractere = quatriemeCaractere;
        this.cinquiemeCaractere = cinquiemeCaractere;
        this.sixiemeCaractere = sixiemeCaractere;
    }

    @Generated(hash = 1809085306)
    public LettreMorse() {
    }

    public static class SigneMorseConverter implements PropertyConverter<SigneMorse, Long> {
        @Override
        public SigneMorse convertToEntityProperty(Long databaseValue) {
            if (databaseValue == null) {
                return null;
            }
            for (SigneMorse signeMorse : SigneMorse.values()) {
                if (signeMorse.id == databaseValue) {
                    return signeMorse;
                }
            }
            return SigneMorse.Default;
        }

        @Override
        public Long convertToDatabaseValue(SigneMorse entityProperty) {
            return entityProperty == null ? null : entityProperty.id;
        }
    }

    @Override
    public String toString() {
        return premierCaractere.name() + deuxiemeCaractere.name() + troisiemeCaractere.name() +
                quatriemeCaractere.name() +  cinquiemeCaractere.name() + sixiemeCaractere.name();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SigneMorse getPremierCaractere() {
        return this.premierCaractere;
    }

    public void setPremierCaractere(SigneMorse premierCaractere) {
        this.premierCaractere = premierCaractere;
    }

    public SigneMorse getDeuxiemeCaractere() {
        return this.deuxiemeCaractere;
    }

    public void setDeuxiemeCaractere(SigneMorse deuxiemeCaractere) {
        this.deuxiemeCaractere = deuxiemeCaractere;
    }

    public SigneMorse getTroisiemeCaractere() {
        return this.troisiemeCaractere;
    }

    public void setTroisiemeCaractere(SigneMorse troisiemeCaractere) {
        this.troisiemeCaractere = troisiemeCaractere;
    }

    public SigneMorse getQuatriemeCaractere() {
        return this.quatriemeCaractere;
    }

    public void setQuatriemeCaractere(SigneMorse quatriemeCaractere) {
        this.quatriemeCaractere = quatriemeCaractere;
    }

    public SigneMorse getCinquiemeCaractere() {
        return this.cinquiemeCaractere;
    }

    public void setCinquiemeCaractere(SigneMorse cinquiemeCaractere) {
        this.cinquiemeCaractere = cinquiemeCaractere;
    }

    public SigneMorse getSixiemeCaractere() {
        return this.sixiemeCaractere;
    }

    public void setSixiemeCaractere(SigneMorse sixiemeCaractere) {
        this.sixiemeCaractere = sixiemeCaractere;
    }
}
