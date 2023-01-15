package com.example.gestion_de_stock;

import com.example.gestion_de_stock.models.Category;


public class Article {
    private Integer id;
    private String libelle;
    private Double prix_unitaire;
    private Category category;

    public Article(Integer id, String libelle, Double prix_unitaire, Category category) {
        this.id = id;
        this.libelle = libelle;
        this.prix_unitaire = prix_unitaire;
        this.category = category;
    }

    public Article() {
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                ", prix_unitaire=" + prix_unitaire +
                ", category=" + category +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Double getPrix_unitaire() {
        return prix_unitaire;
    }

    public void setPrix_unitaire(Double prix_unitaire) {
        this.prix_unitaire = prix_unitaire;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


}
