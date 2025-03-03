package simu.Dao;

import simu.Dao.MenuDao;
import simu.model.MenuItem;

import java.util.List;

public class TestCustomer {


    public TestCustomer() {

    }

    public void placeOrder() {
        MenuDao dao = new MenuDao();

        List<MenuItem> starters = dao.getAllStarters();
        List<MenuItem> mainMeals = dao.getAllMainMeals();
        List<MenuItem> desserts = dao.getAllDesserts();

        if (starters.isEmpty() || mainMeals.isEmpty() || desserts.isEmpty()) {
            System.out.println("Not enough menu items available for order.");
            return;
        }


        MenuItem starter = starters.get(0);
        MenuItem mainMeal = mainMeals.get(0);
        MenuItem dessert = desserts.get(0);

        double totalPrepTime = starter.getPrepTimeMinutes() + mainMeal.getPrepTimeMinutes() + dessert.getPrepTimeMinutes();
        System.out.println("Starter: " + starter.getName() + " (" + starter.getPrepTimeMinutes() + " min)");
        System.out.println("Main Meal: " + mainMeal.getName() + " (" + mainMeal.getPrepTimeMinutes() + " min)");
        System.out.println("Dessert: " + dessert.getName() + " (" + dessert.getPrepTimeMinutes() + " min)");
        System.out.println("Total Preparation Time: " + totalPrepTime + " minutes.");
    }

    public static void main(String[] args) {
        TestCustomer customer = new TestCustomer();
        customer.placeOrder();
    }
}