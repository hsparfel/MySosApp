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


    private String adresse;


    private String cp;


    private String ville;


    private String telephone;

    @NotNull
    private Date dateNaissance;


    @Convert(converter = GroupeSanguinConverter.class, columnType = Long.class)
    private GroupeSanguin groupeSanguin;

    
    private boolean isDonneurOrgane;

    private String infos;

    

    @Generated(hash = 1178321886)
    public Utilisateur() {
    }

    @Generated(hash = 687792878)
    public Utilisateur(Long id, @NotNull String nom, @NotNull String prenom, String adresse, String cp,
            String ville, String telephone, @NotNull Date dateNaissance, GroupeSanguin groupeSanguin,
            boolean isDonneurOrgane, String infos) {
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
        this.infos = infos;
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

    public String getInfos() {
        return this.infos;
    }

    public void setInfos(String infos) {
        this.infos = infos;
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
