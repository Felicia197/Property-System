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
public class Owner implements Serializable {

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
    private String account_status;
    @OneToMany
    private ArrayList<RentProperty> rent_properties = new ArrayList<RentProperty>();
    @OneToMany
    private ArrayList<SellProperty> sell_properties = new ArrayList<SellProperty>();
    
    // Constructor
    public Owner(String id, String password, String phone_number, String date_of_birth, String gender, double balance, String account_status) {
        this.id = id;
        this.password = password;
        this.phone_number = phone_number;
        this.date_of_birth = date_of_birth;
        this.gender = gender;
        this.balance = balance;
        this.account_status = account_status;
    }

    public Owner() {
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

    public String getAccount_status() {
        return account_status;
    }

    public void setAccount_status(String account_status) {
        this.account_status = account_status;
    }

    public ArrayList<RentProperty> getRent_properties() {
        return rent_properties;
    }

    public void setRent_properties(ArrayList<RentProperty> rent_properties) {
        this.rent_properties = rent_properties;
    }

    public ArrayList<SellProperty> getSell_properties() {
        return sell_properties;
    }

    public void setSell_properties(ArrayList<SellProperty> sell_properties) {
        this.sell_properties = sell_properties;
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
        if (!(object instanceof Owner)) {
            return false;
        }
        Owner other = (Owner) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Owner[ id=" + id + " ]";
    }
    
}
