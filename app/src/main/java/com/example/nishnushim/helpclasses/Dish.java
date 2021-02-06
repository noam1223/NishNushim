package com.example.nishnushim.helpclasses;

import android.net.Uri;


import com.example.nishnushim.helpclasses.menuChanges.Changes;

import java.io.Serializable;
import java.util.List;

public class Dish implements Serializable, Cloneable {

    String name;
    String details;
    List<Changes> changes;
    int price;
    Uri image;


    public Dish() {
    }

    public Dish(String name, String details, List<Changes> changes, int price, Uri image) {
        this.name = name;
        this.details = details;
        this.changes = changes;
        this.price = price;
        this.image = image;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public List<Changes> getChanges() {
        return changes;
    }

    public void setChanges(List<Changes> changes) {
        this.changes = changes;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }


    public Object clone()
    {
        try
        {
            return super.clone();
        }
        catch( CloneNotSupportedException e )
        {
            e.printStackTrace();
            return null;
        }
    }
}

