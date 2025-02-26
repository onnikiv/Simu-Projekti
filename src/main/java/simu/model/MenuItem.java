package simu.model;

public class MenuItem {
    private int id;
    private String name;
    private double prepTime;

    public MenuItem(int id, String name, double prepTime) {
        this.id = id;
        this.name = name;
        this.prepTime = prepTime;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrepTimeMinutes() {
        return prepTime;
    }

    public void setPrepTimeMinutes(int prepTimeMinutes) {
        this.prepTime = prepTimeMinutes;
    }
}
