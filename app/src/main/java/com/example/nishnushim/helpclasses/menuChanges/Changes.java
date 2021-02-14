package com.example.nishnushim.helpclasses.menuChanges;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Changes implements Serializable {


    public enum ChangesTypesEnum {
        MULTIPLE,
        ONE_CHOICE,
        VOLUME,
        CLASSIFICATION_CHOICE,
        DISH_CHOICE,
        PIZZA,
        CHOICE_ALL
    }


    String changeName;
    public int freeSelection = 0;
    ChangesTypesEnum changesTypesEnum;

    List<Object> changesByTypesList = new ArrayList<>();


    public Changes() {
    }


    public Changes(String changeName, ChangesTypesEnum changesTypesEnum,List<Object> changesByTypesList) {
        this.changeName = changeName;
        this.changesTypesEnum = changesTypesEnum;
        this.changesByTypesList = changesByTypesList;



    }


    public String getChangeName() {
        return changeName;
    }

    public void setChangeName(String changeName) {
        this.changeName = changeName;
    }

    public ChangesTypesEnum getChangesTypesEnum() {
        return changesTypesEnum;
    }

    public void setChangesTypesEnum(ChangesTypesEnum changesTypesEnum) {
        this.changesTypesEnum = changesTypesEnum;
    }


    public List<Object> getChangesByTypesList() {
        return changesByTypesList;
    }

    public void setChangesByTypesList(List<Object> changesByTypesList) {
        this.changesByTypesList = changesByTypesList;
    }

    public int getFreeSelection() {
        return freeSelection;
    }

    public void setFreeSelection(int freeSelection) {
        this.freeSelection = freeSelection;
    }

}
