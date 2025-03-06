package simu.Dao;

import simu.datasource.MariaDbConnection;
import simu.model.MenuItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuDao {
    public Connection conn = MariaDbConnection.getConnection();

    public List<MenuItem> getAllStarters() {
        return getMenuItemsByCategory(1);
    }

    public List<MenuItem> getAllMainMeals() {
        return getMenuItemsByCategory(2);
    }

    public List<MenuItem> getAllDesserts() {
        return getMenuItemsByCategory(3);
    }

    public MenuItem getRandomStarter() {
        return randomStarter();
    }

    public MenuItem getRandomMainMeal() {
        return randomMainMeal();
    }

    public MenuItem getRandomDessert() {
        return randomDessert();
    }

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

    private MenuItem randomStarter() {
        String query = "SELECT id, name, prep_time_minutes FROM menu_items WHERE category_id = 1 ORDER BY RAND() LIMIT 1";
        try (Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery(query)) {
            if (rs.next()) {
                return new MenuItem(rs.getInt("id"), rs.getString("name"), rs.getInt("prep_time_minutes"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private MenuItem randomMainMeal() {
        String query = "SELECT id, name, prep_time_minutes FROM menu_items WHERE category_id = 2 ORDER BY RAND() LIMIT 1";
        try (Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery(query)) {
            if (rs.next()) {
                return new MenuItem(rs.getInt("id"), rs.getString("name"), rs.getInt("prep_time_minutes"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private MenuItem randomDessert() {
        String query = "SELECT id, name, prep_time_minutes FROM menu_items WHERE category_id = 3 ORDER BY RAND() LIMIT 1";
        try (Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery(query)) {
            if (rs.next()) {
                return new MenuItem(rs.getInt("id"), rs.getString("name"), rs.getInt("prep_time_minutes"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}


