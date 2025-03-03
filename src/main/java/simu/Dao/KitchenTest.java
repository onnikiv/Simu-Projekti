package simu.Dao;

import simu.model.MenuItem;
import java.util.List;

public class KitchenTest {

    public KitchenTest() {

    }

    public void orderStarters() {
        MenuDao dao = new MenuDao();
        List<MenuItem> getAllStarters = dao.getAllStarters();


        for (MenuItem starters : getAllStarters) {
            System.out.println(starters.getName() + " " + starters.getPrepTimeMinutes());
        }

    }

    public void orderMeals() {
        MenuDao dao = new MenuDao();
        List<MenuItem> getAllMainMeals = dao.getAllMainMeals();


        for (MenuItem meals : getAllMainMeals) {
            System.out.println(meals.getName() + " " + meals.getPrepTimeMinutes());
        }

    }

    public void orderDeserts() {
        MenuDao dao = new MenuDao();
        List<MenuItem> getAllDeserts = dao.getAllDesserts();


        for (MenuItem deserts : getAllDeserts) {
            System.out.println(deserts.getName() + " " + deserts.getPrepTimeMinutes());
        }

    }

}
