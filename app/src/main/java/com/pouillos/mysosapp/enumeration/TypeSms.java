package com.pouillos.mysosapp.enumeration;

public enum TypeSms {

    //Objets directement construits
    Default(0,"?"),
    Accident(1,"Accident"),
    Enlevement(2,"Enlevement");

    public long id;
    public String name = "";

    //Constructeur
    TypeSms(long id, String name){
        this.id = id;
        this.name = name;
    }

    public String toString(){
        return name;
    }

}
