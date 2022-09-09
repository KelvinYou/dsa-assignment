/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Objects;
import adt.*;
/**
 *
 * @author Acer
 */
public class DonationCategory {
    private String categoryId;
    private String categoryName;
    private String unit;
    private String date;
    
    private ListInterface<Donor> donorList = new DoublyLinkedList<>();
    
    public DonationCategory(String categoryId, String categoryName, String unit, String date) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.unit = unit;
        this.date = date;
    }
    
    public String getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
    
    public String getCategoryName() {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public String getUnit() {
        return unit;
    }
    
    public void setUnit(String unit) {
        this.unit = unit;
    }
    
    public String getDate() {
        return date;
    }
    
    public void setDate(String date) {
        this.date = date;
    }    
    
    public ListInterface<Donor> getDonorList() {
        return donorList;
    }
    
    public void setDonorList(ListInterface<Donor> donorList) {
        this.donorList = donorList;
    }
    
    @Override
    public String toString() {
        return "| " + String.format("%-5s", categoryId)
                + " | " + String.format("%-20s", categoryName)
                + " | " + String.format("%-25s", unit)
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
        
        final DonationCategory other = (DonationCategory) obj;
        if (!Objects.equals(this.categoryId, other.categoryId))
            return false;
        return true;
    }
    
    public void addDonor(Donor donor) {
        donorList.add(donor);
    }
    
    public void removeDonor(Donor donor) {
        donorList.remove(donor);
    }
    
    public Donor getDonor(String donorId) {
        for (int i = 0; i < donorList.sizeOf(); i++) {
            if (donorList.get(i).getDonorId().equals(donorId))
                return donorList.get(i);
        }
        return null;
    }
}
