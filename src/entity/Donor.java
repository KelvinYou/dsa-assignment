/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Objects;
/**
 *
 * @author Acer
 */
public class Donor {
    private String donorId;
    private String donorName;
    private int age;
    private String phoneNumber;
    private String category;
    private int amount;
    private String date;
    
    public Donor(String donorId, String donorName, int age, String phoneNumber, String category, int amount, String date) {
        this.donorId = donorId;
        this.donorName = donorName;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.category = category;
        this.amount = amount;
        this.date = date;
    }
    
    public String getDonorId() {
        return donorId;
    }
    
    public String getDonorName() {
        return donorName;
    }
    
    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public int getAmount() {
        return amount;
    }
    
    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    public String getDate() {
        return date;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    @Override
    public String toString() {
            return "| " + String.format("%-5s", donorId) 
                + " | " + String.format("%-20s", donorName) 
                + " | " + String.format("%-5d", age)
                + " | " + String.format("%-20s", String.valueOf(phoneNumber).replaceFirst("(\\d{1})(\\d{2})(\\d{3})(\\d+)", "(+6$1)$2-$3 $4"))
                + " | " + String.format("%-15d", amount)
                + " | " + String.format("%-13s", date) + " |";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        
        final Donor other = (Donor) obj;
        if (!Objects.equals(this.donorId, other.donorId))
            return false;
        return true;
    }
    
}
