/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import adt.DoublyLinkedList;
import adt.ListInterface;
import adt.OrderClause;
import entity.DonationCategory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import utility.ConsoleStyles;
import utility.NewScanner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



import java.util.Iterator;
/**
 *
 * @author Acer
 */
public class DonationCategoryMenu {
    private ListInterface<DonationCategory> categoryList;
    private final NewScanner scanner = new NewScanner();
    private final ConsoleStyles print = new ConsoleStyles();
    
    private int categoryIndex = 1004;
    private static final int TABLE_WIDTH = 40, LIST_TABLE_WIDTH = 76, SPECIFY_TABLE_WIDTH = 50;
    
    public DonationCategoryMenu(ListInterface<DonationCategory> categoryList) {
        this.categoryList = categoryList;
    }
    
    public void main() {
        int desire;
        
        do {
            print.tableHeader("DONATION CATEGORY", TABLE_WIDTH);
            System.out.println("1. Add Category");
            System.out.println("2. Remove Category");
            System.out.println("3. Edit Category's details");
            System.out.println("4. Search Category");
            System.out.println("5. Sort Product List By");
            System.out.println("6. Display Category List");
            System.out.println("7. Exit");
            desire = scanner.nextInt("Enter Your Choice: ", "Please enter a number between 1 to 7", 1, 7);
            
            switch(desire) {
                case 1 -> addCategory();
                case 2 -> removeCategory();
                case 3 -> editCategory();
                case 4 -> searchCategory();
                case 5 -> sortByList();
                case 6 -> showCategoryList();
                default -> {
                }
            }
            
        } while (desire != 7);
    }
    
    public void addCategory() {
        print.tableHeader("ADD CATEGORY", TABLE_WIDTH);
        print.otherMsg("New Category Details", 1);
        
        // Get category Id
        String categoryId = "C" + String.format("%4d", categoryIndex++);
        System.out.println("Category ID         : " + categoryId);
        
        // Get category name
        String categoryName = scanner.nextLine("Enter Category Name : ");
        
        // Get unit type
        print.otherMsg("Units In This Category", 0);
        System.out.println("1. Ringgit Malaysia (RM)");
        System.out.println("2. Quantity");
        int unitTypeNum = scanner.nextInt("Enter Your Choice: ", "Please enter a number between 1 to 2", 1, 2);
        String unitType = null;
        
        if (unitTypeNum == 1)
            unitType = "Ringgit Malaysia (RM)";
        else
            unitType = "Quantity";
        
        // Get current date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime now = LocalDateTime.now();
        
        // Show the details
        print.tableHeader("New Category Details", SPECIFY_TABLE_WIDTH);
        System.out.printf("| %-18s | %-25s |\n", "Category ID", categoryId);
        System.out.printf("| %-18s | %-25s |\n", "Category Name", categoryName);
        System.out.printf("| %-18s | %-25s |\n", "Unit Type", unitType);
        System.out.printf("| %-18s | %-25s |\n", "Date", dtf.format(now));
        print.tableFooter(SPECIFY_TABLE_WIDTH);
        
        // Confirmation
        print.otherMsg("Please make sure that all the details given above is correct", 1);        
        print.hint("The above details can be edited in the future");

        if (scanner.confirmation("Sure want to add category above (Y = yes / N = no)? >> ")) {
            categoryList.add(new DonationCategory(categoryId, categoryName, unitType, dtf.format(now)));
            print.success("The above category have been added to the list");
        } else {
            print.cancelled("Adding the above category has been cancelled");
        }
    }
    
    public void removeCategory() {
        // Check whether is empty
        if (categoryList.isEmpty()) {
            print.failed("No Category In The List");
        } else {
            // Show all category in the list
            print.tableHeader("REMOVE CATEGORY", LIST_TABLE_WIDTH);
            displayList(categoryList);
            print.tableFooter(LIST_TABLE_WIDTH);
            print.otherMsg(String.format("Total Number Of Category: %d", categoryList.sizeOf()), 0);
            
            // Get the category id
            String categoryId = scanner.nextLine("\nEnter The Category ID To Remove (e.g. C1001): ");
            DonationCategory toRemove = categoryList.firstOrDefault(d -> d.getCategoryId().equalsIgnoreCase(categoryId));
        
            // Compare the id from list
            if(toRemove == null) {
                print.failed("Category ID Not Found");
            } else {
                // Show the list that search by user
                displaySpecification("category to remove", toRemove);
                
                if(scanner.confirmation("Sure want to remove category above (Y = yes / N = no)? >> ")) {
                    categoryList.remove(toRemove);
                    print.success("The above category have been removed to the list of category");
                } else {
                    print.cancelled("The removal the above category has been cancelled");
                }
            }
        }
    }
    
    public void editCategory() {
        // Check whether is empty
        if (categoryList.isEmpty()) {
            print.failed("No Category In The List");
        } else {
            // Show all category in the list
            print.tableHeader("EDIT CATEGORY", LIST_TABLE_WIDTH);
            displayList(categoryList);
            print.tableFooter(LIST_TABLE_WIDTH);
            print.otherMsg(String.format("Total Number Of Category: %d", categoryList.sizeOf()), 0);

            // Get the category id
            String categoryId = scanner.nextLine("\nEnter The Category ID To Edit (e.g. C1001): ");
            DonationCategory toEdit = categoryList.firstOrDefault(d -> d.getCategoryId().equalsIgnoreCase(categoryId));
            
            // Get the category id
            if (toEdit == null) {
                print.failed("Category ID Not Found");
            } else {
                displaySpecification("Category to edit", toEdit);
                // Enter new name
                String categoryName = scanner.nextLine("Enter New Category Name      : ");
                print.otherMsg("Units In This Category", 0);
                System.out.println("1. Ringgit Malaysia (RM)");
                System.out.println("2. Quantity");
                int unitTypeNum = scanner.nextInt("Enter Your Choice: ", "Please enter a number between 1 to 2", 1, 2);
                String unitType = null;

                if (unitTypeNum == 1)
                    unitType = "Ringgit Malaysia (RM)";
                else
                    unitType = "Quantity";
                
                if(scanner.confirmation("Sure want to edit category above (Y = yes / N = no)? >> ")) {
                    // Set the details into the list
                    toEdit.setCategoryName(categoryName);
                    toEdit.setUnit(unitType);
                    
                    print.success("The above category have been edited with the new details");
                } else {
                    print.cancelled("Edit the above category has been cancelled");
                }
            }
        }
    }
    
    public void searchCategory() {
        // Choose search by
        print.tableHeader("SEARCH CATEGORY", TABLE_WIDTH);
        System.out.println("1. Search By Category ID");
        System.out.println("2. Search By Category Name");
        System.out.println("3. Search By Category Unit");
        System.out.println("4. Back To Donation Category Main Page");
        
        int desire = scanner.nextInt("Enter Your Choice: ", "Please enter a number between 1 to 4", 1, 4);
        
        switch(desire) {
            case 1 -> searchCategoryId();
            case 2 -> searchCategoryName();
            case 3 -> searchUnit();           
            default -> {
            }
        }
    }
    
    public void searchCategoryId() {
        print.tableHeader("SEARCH CATEGORY ID", TABLE_WIDTH);
        String categoryId = scanner.nextLine("Enter category id to search (e.g. C1001): ");
        
        // Check id
        DonationCategory dc = categoryList.firstOrDefault(d -> d.getCategoryId().equalsIgnoreCase(categoryId));
    
        // Show result
        if(dc != null)
            displaySpecification("category search result", dc);
        else
            print.failed("Category ID not found");
    }
    
    public void searchCategoryName() {
        print.tableHeader("SEARCH CATEGORY NAME", TABLE_WIDTH);
        System.out.println("1. Search Name Starts With");
        System.out.println("2. Search Name Ends With");
        System.out.println("3. Search Name Contains");
        System.out.println("4. Back To Search Main Page");
        int desire = scanner.nextInt("Enter Your Choice: ", "Please enter a number between 1 to 4", 1, 4);
        
        if(desire != 4) {
            String categoryName = scanner.nextLine("Enter name to search: ").toUpperCase();
            ListInterface<DonationCategory> cList = new DoublyLinkedList<>();
            
            // Get all category which related with the input
            switch(desire) {
                case 1: 
                    cList = categoryList.where(d -> d.getCategoryName().toUpperCase().startsWith(categoryName));
                    break;
                case 2: 
                    cList = categoryList.where(d -> d.getCategoryName().toUpperCase().endsWith(categoryName));
                    break;
                case 3: 
                    cList = categoryList.where(d -> d.getCategoryName().toUpperCase().contains(categoryName));
                    break;
                default: break;
            }
            
            // Show result
            if(cList.isEmpty())
                print.failed("Category name not found");
            else
                displaySpecification(cList);
        }
    }
    
    public void searchUnit() {
        print.tableHeader("SEARCH CATEGORY UNIT", TABLE_WIDTH);
        System.out.println("1. Ringgit Malaysia (RM)");
        System.out.println("2. Quantity");
        System.out.println("3. Back To Search Main Page");
        int desire = scanner.nextInt("Enter Your Choice: ", "Please enter a number between 1 to 3", 1, 3);
        
        String unitPair;
        if (desire == 1)
            unitPair = "Ringgit Malaysia (RM)";
        else
            unitPair = "Quantity";
        
        if (desire != 3) {
            final String categoryUnit = unitPair;
            print.error(categoryUnit);
            ListInterface<DonationCategory> cList = new DoublyLinkedList<>();
            
            // Get all category which related with the input
            cList = categoryList.where(d -> d.getUnit().equalsIgnoreCase(categoryUnit));
            
            // Show result
            displaySpecification(cList);
            
        }
    }
    
    public void sortByList() {
        print.tableHeader("SORT CATEGORY LIST BY", TABLE_WIDTH);
        
        System.out.println("1. Sort By Category ID");
        System.out.println("2. Sort By Category Name");
        System.out.println("3. Sort By Category Unit");
        System.out.println("4. Sort By Date");
        System.out.println("5. Back To Category List Main Page");
        
        int desire = scanner.nextInt("Enter Your Choice: ", "Please enter a number between 1 to 5", 1, 5);
    
        if (desire != 5) {
            print.otherMsg("Sort Category List By", 1);
            System.out.println("1. Ascending Order");
            System.out.println("2. Descending Order");
            System.out.println("3. Back To Category List Main Page");

            int sequenceType = scanner.nextInt("Enter Your Choice: ", "Please enter a number between 1 to 3", 1, 3);

            if (sequenceType != 3) {
                switch(desire) {
                    case 1 -> sortById(sequenceType);
                    case 2 -> sortByName(sequenceType);
                    case 3 -> sortByUnit(sequenceType);                    
                    case 4 -> sortByDate(sequenceType);
                    default -> {
                    }
                }
                print.success("Sort successfully, press 6 to view category list");
            }
        }
    }
    
    public void sortById(int sequenceType) {
        switch (sequenceType) {
            case 1 -> categoryList.orderBy((c1, c2) -> 
                    c1.getCategoryId().compareToIgnoreCase(c2.getCategoryId()) < 0 ? 
                            OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD);
            case 2 -> categoryList.orderBy((c1, c2) -> 
                    c1.getCategoryId().compareToIgnoreCase(c2.getCategoryId()) > 0 ? 
                            OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD);
            default -> {
            }
        }
    }
    
    public void sortByName(int sequenceType) {
        switch (sequenceType) {
            case 1 -> categoryList.orderBy((c1, c2) -> 
                    c1.getCategoryName().compareToIgnoreCase(c2.getCategoryName()) < 0 ? 
                            OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD);
            case 2 -> categoryList.orderBy((c1, c2) -> 
                    c1.getCategoryName().compareToIgnoreCase(c2.getCategoryName()) > 0 ? 
                            OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD);
            default -> {
            }
        }
    }
    
    public void sortByUnit(int sequenceType) {
        switch (sequenceType) {
            case 1 -> categoryList.orderBy((c1, c2) -> 
                    c1.getUnit().compareToIgnoreCase(c2.getUnit()) < 0 ? 
                            OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD);
            case 2 -> categoryList.orderBy((c1, c2) -> 
                    c1.getUnit().compareToIgnoreCase(c2.getUnit()) > 0 ? 
                            OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD);
            default -> {
            }
        }
    }
    
    public void sortByDate(int sequenceType) {
        SimpleDateFormat dtf = new SimpleDateFormat("dd-MM-yyyy");

        switch (sequenceType) {
            case 1 -> categoryList.orderBy((c1, c2) -> {
                int val = 0;
                try{ 
                    val = dtf.parse(c1.getDate()).compareTo(dtf.parse(c2.getDate()));
                } catch (ParseException e) {
                }
               return val < 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
            });
            case 2 -> categoryList.orderBy((c1, c2) -> {
                int val = 0;
                try{ 
                    val = dtf.parse(c1.getDate()).compareTo(dtf.parse(c2.getDate()));
                } catch (ParseException e) {
                }
               return val > 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
            });
            default -> {
            }
        }
    }
    
    public void showCategoryList() {
        print.tableHeader("CATEGORY LIST", LIST_TABLE_WIDTH);
        displayList(categoryList);
        print.tableFooter(LIST_TABLE_WIDTH);
        print.otherMsg(String.format("Total Number Of Category: %d", categoryList.sizeOf()), 0);
    }
    
    public void displaySpecification(String title, DonationCategory category) {
        print.tableHeader(title.toUpperCase(), SPECIFY_TABLE_WIDTH);
        System.out.printf("| %-18s | %-25s |\n", "Category ID", category.getCategoryId());
        System.out.printf("| %-18s | %-25s |\n", "Category Name", category.getCategoryName()); 
        System.out.printf("| %-18s | %-25s |\n", "Unit Type", category.getUnit());
        System.out.printf("| %-18s | %-25s |\n", "Date", category.getDate());
        print.tableFooter(SPECIFY_TABLE_WIDTH);
    }
    
    public void displaySpecification(ListInterface<DonationCategory> category) {
        print.tableHeader("CATEGORY SEARCH RESULT", LIST_TABLE_WIDTH);
        displayList(category);
        print.tableFooter(LIST_TABLE_WIDTH);
    }
    
    public void displayList(ListInterface<DonationCategory> categoryList) {
        Iterator<DonationCategory> itr = categoryList.getIterator();
        
        System.out.printf("| %-5s | %-20s | %-25s | %-13s |\n", "ID", "NAME", "UNIT TYPE", "DATE");
        print.tableMiddleLine(LIST_TABLE_WIDTH);
        
        while(itr.hasNext()) {
            DonationCategory dc = itr.next();
            System.out.println(dc.toString());
        }
    }
}
