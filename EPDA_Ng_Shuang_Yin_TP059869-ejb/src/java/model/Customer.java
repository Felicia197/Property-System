/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Felicia
 */
@Entity
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    
    // Variables
    private String id;
    private String password;
    private String phone_number;
    private String date_of_birth;
    private String gender;
    private double balance;
    @OneToMany
    private ArrayList<RentProperty> renting_properties = new ArrayList<RentProperty>();
    @OneToMany
    private ArrayList<SellProperty> purchased_properties = new ArrayList<SellProperty>();

    // Constructor
    public Customer(String id, String password, String phone_number, String date_of_birth, String gender, double balance) {
        this.id = id;
        this.password = password;
        this.phone_number = phone_number;
        this.date_of_birth = date_of_birth;
        this.gender = gender;
        this.balance = balance;
    }

    public Customer() {
    }
    
    // Getter & Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public ArrayList<RentProperty> getRenting_properties() {
        return renting_properties;
    }

    public void setRenting_properties(ArrayList<RentProperty> renting_properties) {
        this.renting_properties = renting_properties;
    }

    public ArrayList<SellProperty> getPurchased_properties() {
        return purchased_properties;
    }

    public void setPurchased_properties(ArrayList<SellProperty> purchased_properties) {
        this.purchased_properties = purchased_properties;
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
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Customer[ id=" + id + " ]";
    }
    
}
