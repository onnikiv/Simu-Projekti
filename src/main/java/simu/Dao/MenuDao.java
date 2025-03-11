package simu.Dao;

import simu.datasource.MariaDbConnection;
import simu.model.MenuItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) class for accessing menu items from the database.
 */

public class MenuDao {
    public Connection conn = MariaDbConnection.getConnection();

    /**
     * Retrieves all starter items from the menu.
     * @return a list of starter menu items
     */

    public List<MenuItem> getAllStarters() {
        return getMenuItemsByCategory(1);
    }

    /**
     * Retrieves all main meal items from the menu.
     * @return a list of main meal menu items
     */
    public List<MenuItem> getAllMainMeals() {
        return getMenuItemsByCategory(2);
    }

    /**
     * Retrieves all dessert items from the menu.
     * @return a list of dessert menu items
     */

    public List<MenuItem> getAllDesserts() {
        return getMenuItemsByCategory(3);
    }

    /**
     * Retrieves a random meal item from the database based on the specified category ID.
     * @param id the category ID
     * @return a random menu item from the specified category
     */

    public MenuItem getRandomMealFromDb(int id) {
        return randomMealFromDb(id);
    }

    /**
     * Retrieves menu items from the database based on the specified category ID.
     * @param categoryId the category ID
     * @return a list of menu items from the specified category
     */

    private List<MenuItem> getMenuItemsByCategory(int categoryId) {
        List<MenuItem> menuItems = new ArrayList<>();
        String query = "SELECT id, name, prep_time_minutes FROM menu_items WHERE category_id = '" + categoryId + "'";
        try (Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery(query)) {
            while (rs.next()) {
                MenuItem item = new MenuItem(rs.getInt("id"), rs.getString("name"), rs.getInt("prep_time_minutes"));
                menuItems.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menuItems;
    }

    /**
     * Retrieves a random menu item from the database based on the specified category ID.
     * @param id the category ID
     * @return a random menu item from the specified category
     */

    private MenuItem randomMealFromDb(int id) {
        String query = "SELECT id, name, prep_time_minutes FROM menu_items WHERE category_id = ? ORDER BY RAND() LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new MenuItem(rs.getInt("id"), rs.getString("name"), rs.getInt("prep_time_minutes"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}


