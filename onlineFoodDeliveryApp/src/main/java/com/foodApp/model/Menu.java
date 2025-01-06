package com.foodApp.model;

public class Menu {
    private int menuId;
    private int restaruntId;
    private String name;
    private String description;
    private int price;
    private boolean isAvailable;
    private String imagePath;

    public Menu() {
    }

    public Menu(int menuId, int restaruntId, String name, String description, int price, boolean isAvailable, String imagePath) {
        this.menuId = menuId;
        this.restaruntId = restaruntId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.isAvailable = isAvailable;
        this.imagePath = imagePath;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public int getRestaurantId() {
        return restaruntId;
    }

    public void setRestaruntId(int restaruntId) {
        this.restaruntId = restaruntId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return menuId + "    " + restaruntId + "    " + name + "    " + description + 
        		"    " + price + "    " + isAvailable + "    " + imagePath;
    }
}
