package com.example.gestion_de_stock.models;

public class Category {
    private Integer id;
    private String designation;

    public Category(Integer id, String designation) {
        this.id = id;
        this.designation = designation;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", designation='" + designation + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Category() {
    }
}
