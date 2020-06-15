package com.pouillos.mysosapp.enumeration;

public enum SigneMorse {

    //Objets directement construits
    Default(0,"?"),
    Point(1,"."),
    Tiret(2,"-");

    public long id;
    public String name = "";

    //Constructeur
    SigneMorse(long id, String name){
        this.id = id;
        this.name = name;
    }

    public String toString(){
        return name;
    }

}
