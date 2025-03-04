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

    public void orderDesserts() {
        MenuDao dao = new MenuDao();
        List<MenuItem> getAllDesserts = dao.getAllDesserts();


        for (MenuItem desserts : getAllDesserts) {
            System.out.println(desserts.getName() + " " + desserts.getPrepTimeMinutes());
        }

    }

}
