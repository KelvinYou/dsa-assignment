/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import adt.DoublyLinkedList;
import adt.ListInterface;

import entity.Donor;
import entity.DonationCategory;

import utility.NewScanner;
import utility.ConsoleStyles;
/**
 *
 * @author Acer
 */
public class TidyHandling {
    /**
     * @param args the command line arguments
     */
    private ListInterface<Donor> donorList;
    private ListInterface<DonationCategory> categoryList;
    
    private DonorMaintenance donorModule;
    private DonationCategoryMenu donationCategory;
    
    private final NewScanner scanner = new NewScanner();
    private final ConsoleStyles print = new ConsoleStyles();
    
    public TidyHandling() {
        donorList = new DoublyLinkedList();
        addDummyDonors();
        
        categoryList = new DoublyLinkedList();
        addDunnmyCategory();
        
        donorModule = new DonorMaintenance(donorList, categoryList);
        donationCategory = new DonationCategoryMenu(categoryList);
    }
    
    public void addDummyDonors() {
        Donor d1 = new Donor("D1001", "Jason", 22, "0123812123", "Donation", 2000, "01-12-2020");
        Donor d2 = new Donor("D1002", "Kelvin", 20, "0123812739", "Clothes", 11, "21-05-2021");
        Donor d3 = new Donor("D1003", "James", 30, "0141387211", "Donation", 1500, "05-04-2021");
        Donor d4 = new Donor("D1004", "Joe", 42, "0182418731", "Clothes", 5, "21-08-2021");
        Donor d5 = new Donor("D1005", "Eunice", 34, "0128318234", "Donation", 5000, "17-08-2021");
        Donor d6 = new Donor("D1006", "Alice", 19, "0179412001", "Clothes", 7, "16-03-2021");
        Donor d7 = new Donor("D1007", "Maribelle", 51, "0152381902", "Donation", 8000, "09-04-2020");
        Donor d8 = new Donor("D1008", "Stella", 23, "0192731793", "Clothes", 3, "05-03-2019");
        Donor d9 = new Donor("D1009", "Darren", 25, "0192381237", "Donation", 1700, "30-07-2021");
        Donor d10 = new Donor("D1010", "Celine", 18, "0142397101", "Donation", 4000, "31-08-2021");
    
        donorList.addAll(d1, d2, d3, d4, d5, d6, d7, d8, d9, d10);
    }
    
    public void addDunnmyCategory() {
        categoryList.add(new DonationCategory("C1001", "Donation", "Ringgit Malaysia (RM)", "10-02-2021"));
        categoryList.add(new DonationCategory("C1002", "Clothes", "Quantity", "11-03-2021"));
        categoryList.add(new DonationCategory("C1003", "Food", "Quantity", "13-06-2021"));
    }
    
    public void run() {
        int desire;
        do {
            print.tableHeader("TIDY HANDLING DONATION SYSTEM", 50);
            
            System.out.println("1. Donation Category");
            System.out.println("2. Donor Maintenance");
            System.out.println("3. Exit");
            desire = scanner.nextInt("Enter Your Choice: ", "Please select valid menu choice", 1, 3);
        
            switch(desire) {
                case 1 -> donationCategory.main();
                case 2 -> donorModule.main();
                default -> {
                }
            }
        
        } while(desire != 3);
        
        print.otherMsg("Thank you for using our system", 1);
    }
    
    public static void main(String[] args) {
        new TidyHandling().run();
    }
    
    public static String getDivider(char symbol, int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < length; i++) {
            stringBuilder.append(symbol);
        }
        return stringBuilder.toString();
    }
}
