package com.pouillos.mysosapp.entities;

import com.pouillos.mysosapp.enumeration.GroupeSanguin;
import com.pouillos.mysosapp.enumeration.SigneMorse;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.converter.PropertyConverter;

@Entity
public class Utilisateur {

    @Id
    private Long id;

    @NotNull
    private String nom;

    @NotNull
    private String prenom;

    @NotNull
    private String adresse;

    @NotNull
    private String cp;

    @NotNull
    private String ville;

    @NotNull
    private String telephone;

    @NotNull
    private Date dateNaissance;

    @NotNull
    @Convert(converter = GroupeSanguinConverter.class, columnType = Long.class)
    private GroupeSanguin groupeSanguin;

    @NotNull
    private boolean isDonneurOrgane;

    

    @Generated(hash = 1178321886)
    public Utilisateur() {
    }

    @Generated(hash = 260647874)
    public Utilisateur(Long id, @NotNull String nom, @NotNull String prenom, @NotNull String adresse,
            @NotNull String cp, @NotNull String ville, @NotNull String telephone,
            @NotNull Date dateNaissance, @NotNull GroupeSanguin groupeSanguin,
            boolean isDonneurOrgane) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.cp = cp;
        this.ville = ville;
        this.telephone = telephone;
        this.dateNaissance = dateNaissance;
        this.groupeSanguin = groupeSanguin;
        this.isDonneurOrgane = isDonneurOrgane;
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

    public String getPrenom() {
        return this.prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCp() {
        return this.cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getVille() {
        return this.ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Date getDateNaissance() {
        return this.dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public GroupeSanguin getGroupeSanguin() {
        return this.groupeSanguin;
    }

    public void setGroupeSanguin(GroupeSanguin groupeSanguin) {
        this.groupeSanguin = groupeSanguin;
    }

    public boolean getIsDonneurOrgane() {
        return this.isDonneurOrgane;
    }

    public void setIsDonneurOrgane(boolean isDonneurOrgane) {
        this.isDonneurOrgane = isDonneurOrgane;
    }

    public static class GroupeSanguinConverter implements PropertyConverter<GroupeSanguin, Long> {
        @Override
        public GroupeSanguin convertToEntityProperty(Long databaseValue) {
            if (databaseValue == null) {
                return null;
            }
            for (GroupeSanguin groupeSanguin : GroupeSanguin.values()) {
                if (groupeSanguin.id == databaseValue) {
                    return groupeSanguin;
                }
            }
            return GroupeSanguin.Default;
        }

        @Override
        public Long convertToDatabaseValue(GroupeSanguin entityProperty) {
            return entityProperty == null ? null : entityProperty.id;
        }
    }
}
