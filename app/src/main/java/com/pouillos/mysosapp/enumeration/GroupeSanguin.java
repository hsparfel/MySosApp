package com.pouillos.mysosapp.enumeration;

public enum GroupeSanguin {

    //Objets directement construits
    Default(0,"?"),
    APositif(1,"A+"),
    ANegatif(2,"A-"),
    BPositif(3,"B+"),
    BNegatif(4,"B-"),
    ABPositif(5,"AB+"),
    ABNegatif(6,"AB-"),
    OPositif(7,"O+"),
    ONegatif(8,"O-");

    public long id;
    public String name = "";

    //Constructeur
    GroupeSanguin(long id, String name){
        this.id = id;
        this.name = name;
    }

    public String toString(){
        return name;
    }

}
