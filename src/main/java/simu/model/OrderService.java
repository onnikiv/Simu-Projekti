package simu.model;

import java.util.HashMap;
import java.util.Map;

import simu.Dao.MenuDao;

public class OrderService {
    private final MenuDao dao;

    private int startersOrdered = 0;
    private int mainOrdered = 0;
    private int dessertsOrdered = 0;

    private final Map<String, Integer> listStarters = new HashMap<>();
    private final Map<String, Integer> listMains = new HashMap<>();
    private final Map<String, Integer> listDesserts = new HashMap<>();

    public OrderService(MenuDao dao) {
        this.dao = dao;
    }

    public MenuItem getRandomStarter() {
        MenuItem starter = dao.getRandomStarter();
        if (starter != null) {
            startersOrdered++;
            addMealCount(listStarters, starter.getName());
        }
        return starter;
    }

    public MenuItem getRandomMainMeal() {
        MenuItem mainMeal = dao.getRandomMainMeal();
        if (mainMeal != null) {
            mainOrdered++;
            addMealCount(listMains, mainMeal.getName());
        }
        return mainMeal;
    }

    public MenuItem getRandomDessert() {
        MenuItem dessert = dao.getRandomDessert();
        if (dessert != null) {
            dessertsOrdered++;
            addMealCount(listDesserts, dessert.getName());
        }
        return dessert;
    }

    private void addMealCount(Map<String, Integer> mealMap, String mealName) {
        mealMap.put(mealName, mealMap.getOrDefault(mealName, 0) + 1);
    }

    public void getAllMealResults() {
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
    }

    public Map<String, Integer> getStarterCounts() {
        return new HashMap<>(listStarters);
    }

    public Map<String, Integer> getMainMealCounts() {
        return new HashMap<>(listMains);
    }

    public Map<String, Integer> getDessertCounts() {
        return new HashMap<>(listDesserts);
    }
}