package com.example.nishnushim.helpclasses;

import com.example.nishnushim.helpclasses.enums.TypeOfRestaurantEnum;

public class RestaurantTypeClass {

    String title;
    TypeOfRestaurantEnum typeOfRestaurantEnum;
    int srcFirstImageId;
    int srcHighLightImageId;

    public RestaurantTypeClass() {
    }

    public RestaurantTypeClass(String title, TypeOfRestaurantEnum typeOfRestaurantEnum, int srcImageId, int srcHighLightImageId) {
        this.title = title;
        this.typeOfRestaurantEnum = typeOfRestaurantEnum;
        this.srcFirstImageId = srcImageId;
        this.srcHighLightImageId = srcHighLightImageId;
    }


    public TypeOfRestaurantEnum getTypeOfRestaurantEnum() {
        return typeOfRestaurantEnum;
    }

    public void setTypeOfRestaurantEnum(TypeOfRestaurantEnum typeOfRestaurantEnum) {
        this.typeOfRestaurantEnum = typeOfRestaurantEnum;
    }

    public int getSrcFirstImageId() {
        return srcFirstImageId;
    }

    public void setSrcFirstImageId(int srcFirstImageId) {
        this.srcFirstImageId = srcFirstImageId;
    }

    public int getSrcHighLightImageId() {
        return srcHighLightImageId;
    }

    public void setSrcHighLightImageId(int srcHighLightImageId) {
        this.srcHighLightImageId = srcHighLightImageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
