package camt.se331.shoppingcart.entity;

import org.hibernate.annotations.*;


import javax.persistence.*;
import javax.persistence.Entity;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by Dto on 2/7/2015.
 */
@Entity
public class Product implements Comparable {
    @Id
    @GeneratedValue
    Long id;
    String name;
    String description;
    Double totalPrice;
    @OneToMany(fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    Set<Image> images = new HashSet<>();

    public Product(Long id, String name, String description, Double price) {
        this.name = name;
        this.description = description;
        this.totalPrice = price;
        this.id = id;
    }

    public Product(Long id, String name, String description, Double price, Image image) {
        this.name = name;
        this.description = description;
        this.totalPrice = price;
        this.id = id;
        this.images.add(image);
    }

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product() {

    }

    public Double getNetPrice() {
        return getTotalPrice() * (1 - VatEntity.getInstance().getVat());
    }

    public Double getTax() {
        return 0.0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;

        Product product = (Product) o;

        if (description != null ? !description.equals(product.description) : product.description != null) return false;
        if (id != null ? !id.equals(product.id) : product.id != null) return false;
        if (name != null ? !name.equals(product.name) : product.name != null) return false;
        if (totalPrice != null ? !totalPrice.equals(product.totalPrice) : product.totalPrice != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (totalPrice != null ? totalPrice.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
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

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public int compareTo(Object o) {

        return (int) (this.getId() - ((Product) o).getId());
    }
}
