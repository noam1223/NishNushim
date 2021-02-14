package com.example.nishnushim.helpclasses;

public class MenuSingleton {

    private static final MenuSingleton menuSingleton = new MenuSingleton();

    public static MenuSingleton getInstance(){
        return menuSingleton;
    }

    private Menu menu;

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

}
