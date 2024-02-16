/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Felicia
 */
@Entity
public class SellProperty implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    
    private String id;
    private String owner;
    private String property_type;
    private String address;
    private String area;
    private String description;
    private double price;
    private int floor_size;
    private String furnishing;
    private String no_of_bedrooms;
    private String no_of_bathrooms;
    private String sold_date;
    private String property_status;

    public SellProperty(String id, String owner, String property_type, String address, String area, String description, double price, int floor_size, String furnishing, String no_of_bedrooms, String no_of_bathrooms, String sold_date, String property_status) {
        this.id = id;
        this.owner = owner;
        this.property_type = property_type;
        this.address = address;
        this.area = area;
        this.description = description;
        this.price = price;
        this.floor_size = floor_size;
        this.furnishing = furnishing;
        this.no_of_bedrooms = no_of_bedrooms;
        this.no_of_bathrooms = no_of_bathrooms;
        this.sold_date = sold_date;
        this.property_status = property_status;
    }

    public SellProperty() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getProperty_type() {
        return property_type;
    }

    public void setProperty_type(String property_type) {
        this.property_type = property_type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getFloor_size() {
        return floor_size;
    }

    public void setFloor_size(int floor_size) {
        this.floor_size = floor_size;
    }

    public String getFurnishing() {
        return furnishing;
    }

    public void setFurnishing(String furnishing) {
        this.furnishing = furnishing;
    }

    public String getNo_of_bedrooms() {
        return no_of_bedrooms;
    }

    public void setNo_of_bedrooms(String no_of_bedrooms) {
        this.no_of_bedrooms = no_of_bedrooms;
    }

    public String getNo_of_bathrooms() {
        return no_of_bathrooms;
    }

    public void setNo_of_bathrooms(String no_of_bathrooms) {
        this.no_of_bathrooms = no_of_bathrooms;
    }

    public String getSold_date() {
        return sold_date;
    }

    public void setSold_date(String sold_date) {
        this.sold_date = sold_date;
    }

    public String getProperty_status() {
        return property_status;
    }

    public void setProperty_status(String property_status) {
        this.property_status = property_status;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SellProperty)) {
            return false;
        }
        SellProperty other = (SellProperty) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.SellProperty[ id=" + id + " ]";
    }
    
}
