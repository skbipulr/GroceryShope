package com.bipul.groceryshope.model;

public class Groceries {
    private int groceriesImage;
    private String groceriesTitle;

    public Groceries(int groceriesImage, String groceriesTitle) {
        this.groceriesImage = groceriesImage;
        this.groceriesTitle = groceriesTitle;
    }

    public int getGroceriesImage() {
        return groceriesImage;
    }

    public void setGroceriesImage(int groceriesImage) {
        this.groceriesImage = groceriesImage;
    }

    public String getGroceriesTitle() {
        return groceriesTitle;
    }

    public void setGroceriesTitle(String groceriesTitle) {
        this.groceriesTitle = groceriesTitle;
    }
}
