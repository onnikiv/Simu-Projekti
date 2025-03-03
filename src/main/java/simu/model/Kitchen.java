package simu.model;

import java.util.List;

public class Kitchen {




    public void order() {
        MenuDAO dao = new MenuDAO();

        List<MenuItem> items = dao.getAllItems();


        for (MenuItem item : items) {
            System.out.println(item.getName() + " " + item.getPrepTimeMinutes());
        }

        MenuItem item = dao.getItem(2);
        System.out.println(item.getName() + " " + item.getPrepTimeMinutes());
    }

    public static void main(String[] args) {
        Kitchen kitchen = new Kitchen();

        kitchen.order();
    }
}
