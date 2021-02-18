package com.example.nishnushim.helpclasses.helpInterfaces;

import com.example.nishnushim.helpclasses.Restaurant;

import java.util.List;

public interface OnSearchItemClicked {
    public void onSearchClicked(List<Restaurant> copiedRestaurants, List<String> copiedKeys, String searchWord);
}
