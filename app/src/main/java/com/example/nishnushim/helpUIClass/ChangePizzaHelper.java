package com.example.nishnushim.helpUIClass;

import com.example.nishnushim.R;

import java.util.ArrayList;
import java.util.List;

public class ChangePizzaHelper {

    List<ChangePizzaType> changePizzaTypes;

    public ChangePizzaHelper() {

        this.changePizzaTypes = new ArrayList<>();


        ChangePizzaType cornPizzaType = new ChangePizzaType(1,"תירס", R.drawable.corn_pizza_change);
        ChangePizzaType blackOlivesPizzaType = new ChangePizzaType(2,"זיתים שחורים", R.drawable.black_olives_pizza_change);
        ChangePizzaType greenOlivesPizzaType = new ChangePizzaType(3,"זיתים ירוקים", R.drawable.green_olives_pizza_change);
        ChangePizzaType onionPizzaType = new ChangePizzaType(4 ,"בצל", R.drawable.onion_pizza_change);
        ChangePizzaType tomatoPizzaType = new ChangePizzaType(5,"עגבניות", R.drawable.tomato_pizza_change);
        ChangePizzaType pinapplePizzaType = new ChangePizzaType(6,"אננס", R.drawable.pineapple_pizza_change);
        ChangePizzaType bulgarianPizzaType = new ChangePizzaType(7,"גבינה בולגרית", R.drawable.bulgarian_cheese_pizza_change);
        ChangePizzaType hotPepperPizzaType = new ChangePizzaType(8,"פלפל חריף", R.drawable.hot_pepper_pizza_change);
        ChangePizzaType cheesePizzaType = new ChangePizzaType(9,"גבינה צהובה", R.drawable.cheese_pizza_change);


        this.changePizzaTypes.add(cornPizzaType);
        this.changePizzaTypes.add(blackOlivesPizzaType);
        this.changePizzaTypes.add(greenOlivesPizzaType);
        this.changePizzaTypes.add(onionPizzaType);
        this.changePizzaTypes.add(tomatoPizzaType);
        this.changePizzaTypes.add(pinapplePizzaType);
        this.changePizzaTypes.add(bulgarianPizzaType);
        this.changePizzaTypes.add(hotPepperPizzaType);
        this.changePizzaTypes.add(cheesePizzaType);

    }

    public List<ChangePizzaType> getChangePizzaTypes() {
        return changePizzaTypes;
    }

}
