package com.example.nishnushim.helpclasses.menuChanges;

import java.io.Serializable;

public class ChangesByTypes<T> implements Serializable {

    private T changesTypeList;

    public ChangesByTypes() {
    }

    public ChangesByTypes(T changesTypeList) {
        this.changesTypeList = changesTypeList;
    }

    public T getChangesTypeList() {
        return changesTypeList;
    }

    public void setChangesTypeList(T changesTypeList) {
        this.changesTypeList = changesTypeList;
    }

}
