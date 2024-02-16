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
public class Rating implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    
    // Variables
    private Long id;
    private String property_id;
    private String owner_id;
    private String customer_id;
    private String customerRating;
    private String customerComment;
    private String ownerRating;
    private String ownerComment;

    public Rating(Long id, String property_id, String owner_id, String customer_id, String customerRating, String customerComment, String ownerRating, String ownerComment) {
        this.id = id;
        this.property_id = property_id;
        this.owner_id = owner_id;
        this.customer_id = customer_id;
        this.customerRating = customerRating;
        this.customerComment = customerComment;
        this.ownerRating = ownerRating;
        this.ownerComment = ownerComment;
    }

    public Rating(String property_id, String owner_id, String customer_id, String customerRating, String customerComment, String ownerRating, String ownerComment) {
        this.property_id = property_id;
        this.owner_id = owner_id;
        this.customer_id = customer_id;
        this.customerRating = customerRating;
        this.customerComment = customerComment;
        this.ownerRating = ownerRating;
        this.ownerComment = ownerComment;
    }
    
    

    public Rating() {
    }

    
    
    // Getter & Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProperty_id() {
        return property_id;
    }

    public void setProperty_id(String property_id) {
        this.property_id = property_id;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomerRating() {
        return customerRating;
    }

    public void setCustomerRating(String customerRating) {
        this.customerRating = customerRating;
    }

    public String getCustomerComment() {
        return customerComment;
    }

    public void setCustomerComment(String customerComment) {
        this.customerComment = customerComment;
    }

    public String getOwnerRating() {
        return ownerRating;
    }

    public void setOwnerRating(String ownerRating) {
        this.ownerRating = ownerRating;
    }

    public String getOwnerComment() {
        return ownerComment;
    }

    public void setOwnerComment(String ownerComment) {
        this.ownerComment = ownerComment;
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
        if (!(object instanceof Rating)) {
            return false;
        }
        Rating other = (Rating) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Rating[ id=" + id + " ]";
    }
    
}
