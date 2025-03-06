package simu.model;

import simu.Dao.MenuDao;

public class OrderService {
    private final MenuDao dao;

    public OrderService(MenuDao dao) {
        this.dao = dao;
    }

    public MenuItem getRandomStarter() {
        return dao.getRandomStarter();
    }

    public MenuItem getRandomMainMeal() {
        return dao.getRandomMainMeal();
    }

    public MenuItem getRandomDessert() {
        return dao.getRandomDessert();
    }
}
