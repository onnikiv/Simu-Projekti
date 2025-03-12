package simu.model;

import java.util.HashMap;
import java.util.Map;

import simu.Dao.MenuDao;

/**
 * OrderService Class that receives orders from the Waiter and prepares the meals.
 * @see Waiter Class that delivers orders to the OrderService.
 * @see MenuItem Items that are prepared in the OrderService. Taken from the MenuDao.
 *
 * @version 1.0
 */

public class OrderService {
    private final MenuDao dao;

    private int startersOrdered = 0;
    private int mainOrdered = 0;
    private int dessertsOrdered = 0;

    private final Map<String, Integer> listStarters = new HashMap<>();
    private final Map<String, Integer> listMains = new HashMap<>();
    private final Map<String, Integer> listDesserts = new HashMap<>();

    /**
     * Constructs an OrderService with the specified MenuDao.
     *
     * @param dao the MenuDao
     */

    public OrderService(MenuDao dao) {
        this.dao = dao;
    }

    /**
     * A method that receives the order from the Waiter and adds it to the orderMap.
     * @param id the ID of the menu item
     * @return a random menu item from the specified category
     */

    public MenuItem getRandomMeal(int id) {
        MenuItem meal = dao.getRandomMealFromDb(id);
        if (meal != null) {
            switch (id) {
                case 1 -> {
                    startersOrdered++;
                    addMealCount(listStarters, meal.getName());
                }
                case 2 -> {
                    mainOrdered++;
                    addMealCount(listMains, meal.getName());
                }
                case 3 -> {
                    dessertsOrdered++;
                    addMealCount(listDesserts, meal.getName());
                }
                default -> System.out.println("Invalid meal category");
            }
        } else {
            System.out.println("Meal not found");
        }
        return meal;
    }

    /**
     * A method that adds the meal for the list of meals ordered.
     * @param mealMap the map of meals
     * @param mealName the name of the meal
     */


    private void addMealCount(Map<String, Integer> mealMap, String mealName) {
        mealMap.put(mealName, mealMap.getOrDefault(mealName, 0) + 1);
    }

    /**
     * A method that returns the total amount of meals ordered.
          * @return 
          */
     
    public Map<String, Map<String, Integer>> getAllMealResults() {
        System.out.println("TOTAL AMOUNT OF MEALS ORDERED:\n");
        System.out.print("Starters: " + startersOrdered + " - ");
        System.out.print(", Main meals: " + mainOrdered + " - ");
        System.out.print(", Desserts: " + dessertsOrdered + " - ");
        System.out.println();

        System.out.println("\nSTARTERS: ");
        for (Map.Entry<String, Integer> entry : listStarters.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        System.out.println("\nMAINS: ");
        for (Map.Entry<String, Integer> entry : listMains.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        System.out.println("\nDESSERTS");
        for (Map.Entry<String, Integer> entry : listDesserts.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        Map<String, Map<String, Integer>> allMeals = new HashMap<>();
        allMeals.put("Starters", listStarters);
        allMeals.put("Mains", listMains);
        allMeals.put("Desserts", listDesserts);

        return allMeals;
    }

    public int getStartersCount() {
        return startersOrdered;
    }

    public int getMainsCount() {
        return mainOrdered;
    }

    public int getDessertsCount() {
        return dessertsOrdered;
    }

    /**
     * A method that returns the total amount of starters ordered.
     * @return a map of starters and their counts
     */

    public Map<String, Integer> getStarterCounts() {
        return new HashMap<>(listStarters);
    }

    /**
     * A method that returns the total amount of main meals ordered.
     * @return a map of main meals and their counts
     */

    public Map<String, Integer> getMainMealCounts() {
        return new HashMap<>(listMains);
    }

    /**
     * A method that returns the total amount of desserts ordered.
     * @return a map of desserts and their counts
     */

    public Map<String, Integer> getDessertCounts() {
        return new HashMap<>(listDesserts);
    }
}