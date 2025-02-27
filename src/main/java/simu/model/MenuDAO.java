package simu.model;

import simu.datasource.MariaDbConnection;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuDAO {

    public List<MenuItem> getAllItems() {
        Connection conn = MariaDbConnection.getConnection();
        List<MenuItem> menuItems = new ArrayList<>();
        String query = "SELECT * FROM menu_items";

        try {
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(query);

            while (rs.next()) {
                String itemName = rs.getString(1);
                int itemCategory = rs.getInt(2);
                double itemPrepTime = rs.getDouble(3);
                MenuItem item = new MenuItem(itemCategory, itemName, itemPrepTime);
                menuItems.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error fetching items from database");
        }
        return menuItems;
    }

    public MenuItem getItem(int id) {
        Connection conn = MariaDbConnection.getConnection();
        String query = "SELECT * FROM menu_items WHERE id = ?";
        String itemName = null;
        int itemCategory = 0;
        double itemPrepTime = 0.0;

        int count = 0;

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                count++;
                itemName = rs.getString(1);
                itemCategory = rs.getInt(2);
                itemPrepTime = rs.getDouble(3);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error fetching items from database");
        }

        if (count == 1) {
            return new MenuItem(itemCategory, itemName, itemPrepTime);
        } else {
            return null;
        }
    }
}
