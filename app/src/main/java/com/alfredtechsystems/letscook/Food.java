package com.alfredtechsystems.letscook;

public class Food {
    private String name, price, description, image;

    public  Food(){

    }
    public Food(String name, String price, String description, String image){
        this.name=name;
        this.description=description;
        this.price=price;
        this.image=image;
        }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

