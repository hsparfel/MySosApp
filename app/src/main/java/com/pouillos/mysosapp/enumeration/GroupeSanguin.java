package com.pouillos.mysosapp.enumeration;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public List<GroupeSanguin> listAll() {
        List<GroupeSanguin> listToReturn = new ArrayList<>();
        List<GroupeSanguin> listGroupeSanguin = Arrays.asList(GroupeSanguin.values());
        for (GroupeSanguin groupeSanguin : listGroupeSanguin) {
            if (groupeSanguin != GroupeSanguin.Default) {
                listToReturn.add(groupeSanguin);
            }
        }
        return listToReturn;
    }

}
