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
public class RentProperty implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    
    private String id;
    private String owner;
    private String property_type;
    private String address;
    private String area;
    private String description;
    private double price_per_month;
    private int floor_size;
    private String furnishing;
    private String no_of_bedrooms;
    private String no_of_bathrooms;
    private String property_status;
    private int duration;

    public RentProperty(String id, String owner, String property_type, String address, String area, String description, double price_per_month, int floor_size, String furnishing, String no_of_bedrooms, String no_of_bathrooms, String property_status, int duration) {
        this.id = id;
        this.owner = owner;
        this.property_type = property_type;
        this.address = address;
        this.area = area;
        this.description = description;
        this.price_per_month = price_per_month;
        this.floor_size = floor_size;
        this.furnishing = furnishing;
        this.no_of_bedrooms = no_of_bedrooms;
        this.no_of_bathrooms = no_of_bathrooms;
        this.property_status = property_status;
        this.duration = duration;
    }

    public RentProperty() {
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

    public double getPrice_per_month() {
        return price_per_month;
    }

    public void setPrice_per_month(double price_per_month) {
        this.price_per_month = price_per_month;
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

    public String getProperty_status() {
        return property_status;
    }

    public void setProperty_status(String property_status) {
        this.property_status = property_status;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
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
        if (!(object instanceof RentProperty)) {
            return false;
        }
        RentProperty other = (RentProperty) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.RentProperty[ id=" + id + " ]";
    }
    
}
