package simu.model;

/**
 * The MenuItem class represents a menu item in the restaurant.
 * It contains the ID, name, and preparation time of the menu item.
 */

public class MenuItem {
    private int id;
    private String name;
    private double prepTime;

    /**
     * Constructs a MenuItem with the specified ID, name, and preparation time.
     *
     * @param id the ID of the menu item
     * @param name the name of the menu item
     * @param prepTime the preparation time of the menu item
     */

    public MenuItem(int id, String name, double prepTime) {
        this.id = id;
        this.name = name;
        this.prepTime = prepTime;
    }


    // Getters and setters

    /**
     * Returns the ID of the menu item.
     *
     * @return the ID
     */

    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the menu item.
     *
     * @param id the ID
     */

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the name of the menu item.
     *
     * @return the name
     */

    public String getName() {
        return name;
    }

    /**
     * Sets the name of the menu item.
     *
     * @param name the name
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the preparation time of the menu item.
     *
     * @return the preparation time
     */

    public double getPrepTimeMinutes() {
        return prepTime;
    }

    /**
     * Sets the preparation time of the menu item.
     *
     * @param prepTimeMinutes the preparation time
     */

    public void setPrepTimeMinutes(int prepTimeMinutes) {
        this.prepTime = prepTimeMinutes;
    }
}
