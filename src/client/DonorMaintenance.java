/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import adt.DoublyLinkedList;
import adt.ListInterface;
import adt.OrderClause;
import entity.Donor;
import entity.DonationCategory;
import utility.NewScanner;
import utility.ConsoleStyles;

import java.util.Iterator;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 *
 * @author Acer
 */

public class DonorMaintenance {
    private ListInterface<Donor> donorList;
    private ListInterface<DonationCategory> categoryList;
    private final NewScanner scanner = new NewScanner();
    private final ConsoleStyles print = new ConsoleStyles();
    
    private int donorIndex = 1011;
    private static final int TABLE_WIDTH = 40, LIST_TABLE_WIDTH = 97, SPECIFY_TABLE_WIDTH = 45, 
            MODIFY_REPORT_TABLE_WIDTH = 55, SUMMARY_TABLE_WIDTH = 60;
    
    private String newAddedDonor = "";
    private String newEditedDonor = "";
    private String newRemovedDonor = "";
    
    
    public DonorMaintenance(ListInterface<Donor> donorList, ListInterface<DonationCategory> categoryList) {
        this.donorList = donorList;
        this.categoryList = categoryList;
        
    }
    
    public void main() {
        int desire;
        
        do {
            print.tableHeader("DONOR MAINTENANCE", TABLE_WIDTH);
            System.out.println("1. Add Donor");
            System.out.println("2. Remove Donor");
            System.out.println("3. Edit Donor's Details");
            System.out.println("4. Search For Donor(s)");
            System.out.println("5. Sort Donor List By");
            System.out.println("6. Display All Donor List");
            System.out.println("7. Display Donor List By Category");
            System.out.println("8. Display Reports");
            System.out.println("9. Exit");
            desire = scanner.nextInt("Enter Your Choice: ", "Please enter a number between 1 to 9", 1, 9);
            
            switch(desire) {
                case 1 -> addDonor();
                case 2 -> removeDonor();
                case 3 -> editDonor();
                case 4 -> searchDonor();
                case 5 -> sortByList();
                case 6 -> showAllList();
                case 7 -> showDonorList();
                case 8 -> showReports();
                default -> {
                }
            }
        } while(desire != 9);
    }
    
    public void showReports() {
        print.tableHeader("REPORT LIST", TABLE_WIDTH);
        System.out.println("1. Today's Modify Report");
        System.out.println("2. Overall Summary Report");
        System.out.println("3. Back To Donor Mainenance Main Page");
        
        int desire = scanner.nextInt("Enter Your Choice: ", "Please enter a number between 1 to 3", 1, 3);
        
        switch(desire) {
            case 1 -> todayModify();
            case 2 -> overallSummaryReport();
            default -> {
            }
        }
    }
    
    public void todayModify() {
        print.tableHeader("TODAY'S MODIFY REPORT", MODIFY_REPORT_TABLE_WIDTH);
        
        // Added List
        print.toCenter("Added Donor Today", MODIFY_REPORT_TABLE_WIDTH);
        print.tableMiddleLine(MODIFY_REPORT_TABLE_WIDTH);
        System.out.println(String.format("| %5s | %20s | %20s |", "ID", "NAME", "CATEGORY"));
        print.tableMiddleLine(MODIFY_REPORT_TABLE_WIDTH);
        if ("".equals(newAddedDonor)) {
            print.toCenter("No Added Record Today", MODIFY_REPORT_TABLE_WIDTH);
            System.out.println();
        } else
            System.out.println(newAddedDonor);
        print.tableFooter(MODIFY_REPORT_TABLE_WIDTH);
        
        // Edited List
        print.toCenter("Edited Donor Today", MODIFY_REPORT_TABLE_WIDTH);
        print.tableMiddleLine(MODIFY_REPORT_TABLE_WIDTH);
        System.out.println(String.format("| %5s | %20s | %20s |", "ID", "NAME", "CATEGORY"));
        print.tableMiddleLine(MODIFY_REPORT_TABLE_WIDTH);
        if ("".equals(newEditedDonor)) {
            print.toCenter("No Edited Record Today", MODIFY_REPORT_TABLE_WIDTH);
            System.out.println();
        } else
            System.out.println(newEditedDonor);
        print.tableFooter(MODIFY_REPORT_TABLE_WIDTH);
        
        // Removed List
        print.toCenter("Removed Donor Today", MODIFY_REPORT_TABLE_WIDTH);
        print.tableMiddleLine(MODIFY_REPORT_TABLE_WIDTH);
        System.out.println(String.format("| %5s | %20s | %20s |", "ID", "NAME", "CATEGORY"));
        print.tableMiddleLine(MODIFY_REPORT_TABLE_WIDTH);
        if ("".equals(newRemovedDonor)){
            print.toCenter("No Removed Record Today", MODIFY_REPORT_TABLE_WIDTH);
            System.out.println();
        } else
            System.out.println(newRemovedDonor);
        print.tableFooter(MODIFY_REPORT_TABLE_WIDTH);
    }
    
    public void overallSummaryReport() {
        Iterator<DonationCategory> categoryItr = categoryList.getIterator();
        
        print.tableHeader("OVERALL SUMMARY REPORT", SUMMARY_TABLE_WIDTH);
        print.toCenter(String.format("Total of donor: %d", donorList.sizeOf()), SUMMARY_TABLE_WIDTH);
        print.tableMiddleLine(SUMMARY_TABLE_WIDTH);
        
        System.out.println(String.format("| %20s | %15s | %15s |", "Category", "Unit", "Amount"));
        print.tableMiddleLine(SUMMARY_TABLE_WIDTH);
        
        int totalAmount;
        
        while (categoryItr.hasNext()) {
            DonationCategory dc = categoryItr.next();
            
            String unitType;
            if (dc.getUnit().equals("Ringgit Malaysia (RM)"))
                unitType = "Amount (RM)";
            else
                unitType = dc.getUnit();
            
            Iterator<Donor> donorItr = donorList.getIterator();
            
            totalAmount = 0;
            
            while (donorItr.hasNext()) {
                Donor dr = donorItr.next();

                if (dc.getCategoryName().equals(dr.getCategory()))
                    totalAmount += dr.getAmount();
            }
            System.out.println(String.format("| %20s | %15s | %15s |", dc.getCategoryName(), unitType, totalAmount));
        }
        print.tableFooter(SUMMARY_TABLE_WIDTH);
    }
    
    public void addDonor() {
        print.tableHeader("ADD DONOR", TABLE_WIDTH);
        print.otherMsg("New Donor Details", 1);
        
        // generate donor id
        String donorId = "D" + String.format("%4d", donorIndex++);
        System.out.println("Donor ID             : " + donorId);
        
        // Get donor name
        String donorName = validateName("Enter Donor Name     : ");
        
        // Get donor age
        int age = scanner.nextInt("Enter Donor Age      : ", "Please enter a valid age which between 1 to 200", 1, 200);
        
        // Get donor phone number
        String phoneNumber = validatePhoneNumber("Enter Phone Number   : ");
        
        // Choose type
        print.otherMsg("Choose The Donation Category", 0);
        displayCategoryList();
        int categoryNum = scanner.nextInt("Enter The Category   : ", String.format("Please enter a number between 1 to %d", categoryList.sizeOf()), 1, categoryList.sizeOf());
        String categoryName = categoryList.get(categoryNum - 1).getCategoryName();
        
        // Get amount from donor
        DonationCategory dc = categoryList.firstOrDefault(c -> c.getCategoryName().equalsIgnoreCase(categoryName));
        int amount;
        
        if (dc.getUnit().equals("Ringgit Malaysia (RM)"))
            amount = scanner.nextInt("Enter Donation Amount: RM ", "Please enter a valid donation amount which between RM 1 to RM 1,000,000,000", 1, 1000000000);
        else
            amount = scanner.nextInt("Enter The Quantity   : ", "Please enter a valid quantity which between 1 to 100,000", 1, 100000);
        
        // Get local date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDateTime now = LocalDateTime.now();
        
        // Display result will be added
        print.tableHeader("New Donor Details", SPECIFY_TABLE_WIDTH);
        System.out.printf("| %-18s | %-20s |\n", "Donor ID", donorId);
        System.out.printf("| %-18s | %-20s |\n", "Donor Name ", donorName);
        System.out.printf("| %-18s | %-20d |\n", "Donor Age", age);
        System.out.printf("| %-18s | %-20s |\n", "Donor Phone Number", phoneNumber);
        if (dc.getUnit().equals("Ringgit Malaysia (RM)"))
            System.out.printf("| %-18s | RM %-17d |\n", "Amount (RM)", amount);
        else
            System.out.printf("| %-18s | %-20d |\n", "Quantity", amount);
        System.out.printf("| %-18s | %-20s |\n", "Date", dtf.format(now));
        print.tableFooter(SPECIFY_TABLE_WIDTH);
        
        // Confirmation
        print.otherMsg("Please make sure that all the details given above is correct", 1);
        if (scanner.confirmation("Sure want to add donor above (Y = yes / N = no)? >> ")) {
            
            donorList.add(new Donor(donorId, donorName, age, phoneNumber, categoryName, amount, dtf.format(now)));
            newAddedDonor += String.format("| %5s | %20s | %20s |\n", donorId, donorName, categoryName);
            
            print.success("The above donor have been added to the list");
        } else {
            print.cancelled("Adding the above donor has been cancelled");
        }
    }
    
    public void removeDonor() {
        // Check
        if (donorList.isEmpty()) {
            print.failed("No Donors In The List");
        } else {
            print.tableHeader("REMOVE DONOR", LIST_TABLE_WIDTH);
            displayList(donorList, categoryList);
            print.otherMsg(String.format("Total Number Of Donor: %d", donorList.sizeOf()), 0);
            
            // Get the donor id
            String donorId = scanner.nextLine("\nEnter The Donor ID To Remove: ");
            Donor toRemove = donorList.firstOrDefault(d -> d.getDonorId().equalsIgnoreCase(donorId));
        
            // Compare the id from list
            if(toRemove == null) {
                print.failed("Donor ID Not Found");
            } else {
                // Show the list that search by user
                displaySpecification(toRemove, categoryList);
                
                if(scanner.confirmation("Sure want to remove donor above (Y = yes / N = no)? >> ")) {
                    donorList.remove(toRemove);
                    newRemovedDonor += String.format("| %5s | %20s | %20s |\n", toRemove.getDonorId(), toRemove.getDonorName(), toRemove.getCategory());
                    print.success("The above donor have been removed to the list of donors");
                } else {
                    print.cancelled("The removal the above donor has been cancelled");
                }
            }
        }
    }
    
    public void editDonor() {
        if (donorList.isEmpty()) {
            print.failed("No Donors In The List");
        } else {
            print.tableHeader("EDIT DONOR", LIST_TABLE_WIDTH);
            displayList(donorList, categoryList);
            print.otherMsg(String.format("Total Number Of Donor: %d", donorList.sizeOf()), 0);
            
            String donorId = scanner.nextLine("Enter The Donor ID To Edit: ");
            Donor toEdit = donorList.firstOrDefault(d -> d.getDonorId().equalsIgnoreCase(donorId));
            
            if(toEdit == null) {
                print.failed("Donor ID not found");
            } else {
                // Enter new donor name
                String donorName = validateName("Enter New Donor Name      : ");
                int age = scanner.nextInt("Enter New Donor Age       : ", "Please enter a valid age which between 1 to 200", 1, 200);
                String phoneNumber = validatePhoneNumber("Enter New Phone Number    : ");
                
                print.otherMsg("Choose The Donation Category", 0);
                displayCategoryList();
                int categoryNum = scanner.nextInt("Enter The Category   : ", String.format("Please enter a number between 1 to %d", categoryList.sizeOf()), 1, categoryList.sizeOf());
                String categoryName = categoryList.get(categoryNum - 1).getCategoryName();
                DonationCategory dc = categoryList.firstOrDefault(c -> c.getCategoryName().equalsIgnoreCase(categoryName));
                int amount;
                if (dc.getUnit().equals("Ringgit Malaysia (RM)"))
                    amount = scanner.nextInt("Enter Donation Amount: RM ", "Please enter a valid donation amount which between RM 1 to RM 1,000,000,000", 1, 1000000000);
                else
                    amount = scanner.nextInt("Enter The Quantity   : ", "Please enter a valid quantity which between 1 to 100,000", 1, 100000);
                
                // Conformation
                if(scanner.confirmation("Sure to edit donor detail above (Y = yes / N = no)? >> ")) {
                    toEdit.setDonorName(donorName);
                    toEdit.setAge(age);
                    toEdit.setPhoneNumber(phoneNumber);
                    toEdit.setCategory(categoryName);
                    toEdit.setAmount(amount);
                    newEditedDonor += String.format("| %5s | %20s | %20s |\n", toEdit.getDonorId(), donorName, categoryName);
                    print.success("The above donor has been edited with new details");
                } else {
                    print.cancelled("Edit the above donor has been cancelled");
                }
            }
        }
    }
    
    public void searchDonor() {
        print.tableHeader("SEARCH DONOR", TABLE_WIDTH);
        System.out.println("1. Search Donor ID");
        System.out.println("2. Search Donor Name");
        System.out.println("3. Search Donor Age");
        System.out.println("4. Search Donor Phone Number");
        System.out.println("5. Back To Donor Mainenance Main Page");
        
        int desire = scanner.nextInt("Enter Your Choice: ", "Please enter a number between 1 to 5", 1, 5);
        
        switch(desire) {
            case 1 -> searchDonorId();
            case 2 -> searchDonorName();
            case 3 -> searchDonorAge();
            case 4 -> searchDonorPhoneNumber();
//            case 5 -> searchDonorDonationAmount();
            default -> {
            }
        }
    }
    
    public void searchDonorId() {
        print.tableHeader("SEARCH DONOR ID", TABLE_WIDTH);
        String donorId = scanner.nextLine("Enter donor id to search (e.g. D0001): ");
        
        Donor dr = donorList.firstOrDefault(d -> d.getDonorId().equalsIgnoreCase(donorId));
    
        if(dr != null)
            displaySpecification(dr, categoryList);
        else
            print.failed("Donor ID not found");
    }
    
    public void searchDonorName() {
        print.tableHeader("SEARCH DONOR NAME", TABLE_WIDTH);
        System.out.println("1. Search Name Starts With");
        System.out.println("2. Search Name Ends With");
        System.out.println("3. Search Name Contains");
        System.out.println("4. Back To Search Main Page");
        int desire = scanner.nextInt("Enter Your Choice: ", "Please enter a number between 1 to 4", 1, 4);
        
        if(desire != 4) {
            final String donorName = scanner.nextLine("Enter name to search: ").toUpperCase();
            ListInterface<Donor> dList = new DoublyLinkedList<>();
            
            switch(desire) {
                case 1:
                    dList = donorList.where(d -> d.getDonorName().toUpperCase().startsWith(donorName));
                    break;
                case 2:
                    dList = donorList.where(d -> d.getDonorName().toUpperCase().endsWith(donorName));
                    break;
                case 3:
                    dList = donorList.where(d -> d.getDonorName().toUpperCase().contains(donorName));
                    break;
                default: break;
            }
            
            if(dList.isEmpty())
                System.out.println("Donor not found");
            else
                displaySpecification(dList);
        }
    }
    
    public void searchDonorAge() {
        print.tableHeader("SEARCH DONOR AGE", TABLE_WIDTH);
        int startRange = scanner.nextInt("Enter starting range: ");
        int endRange = scanner.nextInt("Enter ending range  : ");
        
        ListInterface<Donor> dList = new DoublyLinkedList<>();
        
        if(startRange <= endRange) {
            dList = donorList.where(d -> d.getAge() >= startRange && d.getAge() <= endRange);
        } else {
            System.out.println("\n< Out of range >");
        }
        
        if(dList.isEmpty())
            System.out.println("\n< Donor not found >");
        else
            displaySpecification(dList);
    }
    
    public void searchDonorPhoneNumber() {
        print.tableHeader("SEARCH DONOR PHONE NUMBER", TABLE_WIDTH);
        System.out.println("1. Phone Number starts with");
        System.out.println("2. Phone Number ends with");
        System.out.println("3. Phone Number contains");
        System.out.println("4. Back to search donor function");
        int desire = scanner.nextInt("Enter Your Choice: ", "Please enter a number between 1 to 4", 1, 4);
        
        if(desire != 4) {
            final String donorPhoneNumber = scanner.nextLine("Enter phone number to search: ").toUpperCase();
            ListInterface<Donor> dList = new DoublyLinkedList<>();
            
            switch(desire) {
                case 1:
                    dList = donorList.where(d -> d.getPhoneNumber().toUpperCase().startsWith(donorPhoneNumber));
                    break;
                case 2:
                    dList = donorList.where(d -> d.getPhoneNumber().toUpperCase().endsWith(donorPhoneNumber));
                    break;
                case 3:
                    dList = donorList.where(d -> d.getPhoneNumber().toUpperCase().contains(donorPhoneNumber));
                    break;
                default: break;
            }
            
            if(dList.isEmpty())
                print.failed("Donor phone number not found");
            else
                displaySpecification(dList);
        }
    }
    
//    public void searchDonorDonationAmount() {
//        print.tableHeader("SEARCH DONOR DONATION AMOUNT", TABLE_WIDTH);
//        
//        int startRange = scanner.nextInt("Enter starting range: ");
//        int endRange = scanner.nextInt("Enter ending range  : ");
//        
//        ListInterface<Donor> dList = new DoublyLinkedList<>();
//        
//        if(startRange <= endRange) {
//            dList = donorList.where(d -> d.getAmount() >= startRange && d.getAmount() <= endRange);
//        } else {
//            print.failed("Out of range");
//        }
//        
//        if(dList.isEmpty())
//            print.failed("Donor not found");
//        else
//            displaySpecification(dList);
//    }
    
    public void sortByList() {
        print.tableHeader("SORT DONOR LIST BY", TABLE_WIDTH);
        
        System.out.println("1. Sort By Donor ID");
        System.out.println("2. Sort By Donor Name");
        System.out.println("3. Sort By Donor Age");
        System.out.println("4. Sort By Amount");
        System.out.println("5. Sort By Date");
        System.out.println("6. Back To Donor Mainenance Main Page");
        
        int desire = scanner.nextInt("Enter Your Choice: ", "Please enter a number between 1 to 6", 1, 6);
        
        if (desire != 6) {
            print.otherMsg("Sort By List", 1);
            System.out.println("1. Ascending Order");
            System.out.println("2. Descending Order");
            System.out.println("3. Back To Donor Mainenance Main Page");

            int sequenceType = scanner.nextInt("Enter Your Choice: ", "Please enter a number between 1 to 3", 1, 3);

            if (sequenceType != 3) {
                switch(desire) {
                    case 1 -> sortById(sequenceType);
                    case 2 -> sortByName(sequenceType);
                    case 3 -> sortByAge(sequenceType);
                    case 4 -> sortByDonationAmount(sequenceType);
                    case 5 -> sortByDate(sequenceType);
                    default -> {
                    }
                }
                print.success("Sort successfully, press 6 or 7 to view donor list");
            }
        }
    }
    
    public void sortById(int sequenceType) {
        switch(sequenceType) {
            case 1 -> donorList.orderBy((Donor dr1, Donor dr2) -> {
                    int val = dr1.getDonorId().compareToIgnoreCase(dr2.getDonorId());
                    
                    if (val == 0) {
                        return dr1.getDonorName().compareToIgnoreCase(dr2.getDonorName()) < 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    } else {
                        return val < 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    }
                });
            case 2 -> donorList.orderBy((Donor dr1, Donor dr2) -> {
                    int val = dr1.getDonorId().compareToIgnoreCase(dr2.getDonorId());
                    
                    if (val == 0) {
                        return dr1.getDonorName().compareToIgnoreCase(dr2.getDonorName()) < 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    } else {
                        return val > 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    }
                });
            default -> {
            }
        }
    }
    
    public void sortByName(int sequenceType) {
        switch(sequenceType) {
            case 1 -> donorList.orderBy((Donor dr1, Donor dr2) -> {
                    int val = dr1.getDonorName().compareToIgnoreCase(dr2.getDonorName());
                    
                    if (val == 0) {
                        return dr1.getDonorName().compareToIgnoreCase(dr2.getDonorName()) < 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    } else {
                        return val < 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    }
                });
            case 2 -> donorList.orderBy((Donor dr1, Donor dr2) -> {
                    int val = dr1.getDonorName().compareToIgnoreCase(dr2.getDonorName());
                    
                    if (val == 0) {
                        return dr1.getDonorName().compareToIgnoreCase(dr2.getDonorName()) < 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    } else {
                        return val > 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    }
                });
            default -> {
            }
        }
    }
    
    public void sortByAge(int sequenceType) {
        switch(sequenceType) {
            case 1 -> donorList.orderBy((Donor dr1, Donor dr2) -> {
                    int val = dr1.getAge() - dr2.getAge();
                    
                    if (val == 0) {
                        return dr1.getDonorName().compareToIgnoreCase(dr2.getDonorName()) < 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    } else {
                        return val < 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    }
                });
            case 2 -> donorList.orderBy((Donor dr1, Donor dr2) -> {
                    int val = dr1.getAge() - dr2.getAge();
                    
                    if (val == 0) {
                        return dr1.getDonorName().compareToIgnoreCase(dr2.getDonorName()) < 0  ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    } else {
                        return val > 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    }
                });
            default -> {
            }
        }
    }
    
    public void sortByDonationAmount(int sequenceType) {
        switch(sequenceType) {
            case 1 -> donorList.orderBy((Donor dr1, Donor dr2) -> {
                    int val = dr1.getAmount() - dr2.getAmount();
                    
                    if (val == 0) {
                        return dr1.getDonorName().compareToIgnoreCase(dr2.getDonorName()) < 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    } else {
                        return val < 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    }
                });
            case 2 -> donorList.orderBy((Donor dr1, Donor dr2) -> {
                    int val = dr1.getAmount() - dr2.getAmount();
                    
                    if (val == 0) {
                        return dr1.getDonorName().compareToIgnoreCase(dr2.getDonorName()) < 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    } else {
                        return val > 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    }
                });
            default -> {
            }
        }
    }
    
    public void sortByDate(int sequenceType) {
        SimpleDateFormat dtf = new SimpleDateFormat("dd-MM-yyyy");
        
        switch(sequenceType) {
            case 1 -> donorList.orderBy((Donor dr1, Donor dr2) -> {
                    int val = 0;
                    try{ 
                        val = dtf.parse(dr1.getDate()).compareTo(dtf.parse(dr2.getDate()));
                    } catch (ParseException e) {
                    }
                    
                    if (val == 0) {
                        return dr1.getDonorName().compareToIgnoreCase(dr2.getDonorName()) < 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    } else {
                        return val < 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    }
                });
            case 2 -> donorList.orderBy((Donor dr1, Donor dr2) -> {
                    int val = 0;
                    try{ 
                        val = dtf.parse(dr1.getDate()).compareTo(dtf.parse(dr2.getDate()));
                    } catch (ParseException e) {
                    }
                    
                    if (val == 0) {
                        return dr1.getDonorName().compareToIgnoreCase(dr2.getDonorName()) < 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    } else {
                        return val > 0 ? OrderClause.MOVE_FORWARD : OrderClause.MOVE_BACKWARD;
                    }
                });
            default -> {
            }
        }
    }
    
    public void displaySpecification(Donor donor, ListInterface<DonationCategory> categoryList) {                     // Search for 1 result
        Iterator<DonationCategory> categoryItr = categoryList.getIterator();
        String categoryUnit = null;
        
        while (categoryItr.hasNext()) {
            DonationCategory dc = categoryItr.next();
            
            if (donor.getCategory().compareToIgnoreCase(dc.getCategoryName()) == 0) {
                if (dc.getUnit().compareToIgnoreCase("Ringgit Malaysia (RM)") == 0)
                    categoryUnit = "AMOUNT (RM)";
                else if (dc.getUnit().compareToIgnoreCase("Quantity") == 0)
                    categoryUnit = "QUANTITY";
            }
        }
        
        print.tableHeader("DONOR SEARCH RESULT", SPECIFY_TABLE_WIDTH);
        System.out.printf("| %-18s | %-20s |\n", "Donor ID", donor.getDonorId());
        System.out.printf("| %-18s | %-20s |\n", "Donor Name ", donor.getDonorName());
        System.out.printf("| %-18s | %-20d |\n", "Donor Age", donor.getAge());
        System.out.printf("| %-18s | %-20s |\n", "Donor Phone Number", String.valueOf(donor.getPhoneNumber()).replaceFirst("(\\d{1})(\\d{2})(\\d{3})(\\d+)", "(+6$1)$2-$3 $4"));
        System.out.printf("| %-18s | %-20s |\n", "Donation Category", donor.getCategory());
        System.out.printf("| %-18s | %-20d |\n", categoryUnit, donor.getAmount());
        System.out.printf("| %-18s | %-20s |\n", "Date", donor.getDate());
        print.tableFooter(SPECIFY_TABLE_WIDTH);
    }
    
    public void displaySpecification(ListInterface<Donor> donor) {      // Search for more than 1 results
        print.tableHeader("DONOR SEARCH RESULT", LIST_TABLE_WIDTH);
        displayList(donor, categoryList);
    }
    
    public void showDonorList() {
        Iterator<DonationCategory> categoryItr = categoryList.getIterator();
        print.tableHeader("DISPLAY DONOR LIST", TABLE_WIDTH);
        
        int backNum = 1;
        for (int i = 0; categoryItr.hasNext(); i++) {
            System.out.println(String.format("%d. Only Display %s Category", (i + 1), categoryItr.next().getCategoryName()));
            backNum++;
        }
        
        System.out.printf("%d. Back To Donor Mainenance Main Page\n", backNum);
        
        int desire = scanner.nextInt("Enter Your Choice: ", String.format("Please enter a number between 1 to %d", backNum), 1, backNum);
        
        if (desire != backNum) {
            int numOf = 0;
            // Get category name and category unit
            String categoryName = categoryList.get(desire - 1).getCategoryName();
            String categoryUnit = categoryList.get(desire - 1).getUnit();
            
            // Replace the shorter unit to understand easier 
            if (categoryUnit.equals("Ringgit Malaysia (RM)"))
                categoryUnit = "Amount (RM)";
            
            print.tableHeader(String.format("DONOR LIST (%s)", categoryName.toUpperCase()), LIST_TABLE_WIDTH);

            Iterator<Donor> donorItr = donorList.getIterator();
            
            System.out.printf("| %-5s | %-20s | %-5s | %-20s | %-15s | %-13s |\n", "ID", "NAME", "AGE", "PHONE NUMBER", categoryUnit, "DATE");
            print.tableMiddleLine(LIST_TABLE_WIDTH);
            while (donorItr.hasNext()) {
                Donor dr = donorItr.next();
                if (categoryName.compareToIgnoreCase(dr.getCategory()) == 0) {
                    System.out.println(dr);
                    numOf++;
                }
            }   
            
            if (numOf == 0)
                print.toCenter(String.format("No Record Found In %s Category", categoryName), LIST_TABLE_WIDTH);

            print.tableFooter(LIST_TABLE_WIDTH);
            print.otherMsg(String.format("Total Number Of %s Donor: %d", categoryName, numOf), 0);
        }
    }
    
    // Display all donor list
    public void showAllList() {
        print.tableHeader("ALL DONOR LIST", LIST_TABLE_WIDTH);
        displayList(donorList, categoryList);
        print.otherMsg(String.format("Total Number Of Donor: %d", donorList.sizeOf()), 0);
    }

    // Display donor list base on category
    public void displayList(ListInterface<Donor> donorList, ListInterface<DonationCategory> categoryList) {
        Iterator<DonationCategory> categoryItr = categoryList.getIterator();
        int numOf;
        
        while (categoryItr.hasNext()) {
            DonationCategory dc = categoryItr.next();
            print.toCenter(dc.getCategoryName().toUpperCase(), LIST_TABLE_WIDTH);
            String categoryUnit = null;
            print.tableMiddleLine(LIST_TABLE_WIDTH);
            
            if (dc.getUnit().compareToIgnoreCase("Ringgit Malaysia (RM)") == 0)
                categoryUnit = "AMOUNT (RM)";
            else if (dc.getUnit().compareToIgnoreCase("Quantity") == 0)
                categoryUnit = "QUANTITY";
            
            System.out.printf("| %-5s | %-20s | %-5s | %-20s | %-15s | %-13s |\n", "ID", "NAME", "AGE", "PHONE NUMBER", categoryUnit, "DATE");
            print.tableMiddleLine(LIST_TABLE_WIDTH);
        
            Iterator<Donor> donorItr = donorList.getIterator();
            
            numOf = 0;
            
            while (donorItr.hasNext()) {
                Donor dr = donorItr.next();
                if (dc.getCategoryName().compareToIgnoreCase(dr.getCategory()) == 0) {
                    System.out.println(dr);
                    numOf++;
                }
            }
            
            if (numOf == 0)
                print.toCenter("No record for this category", LIST_TABLE_WIDTH);
            print.tableFooter(LIST_TABLE_WIDTH);
        }
    }
    
    public void displayCategoryList() {
        Iterator<DonationCategory> categoryItr = categoryList.getIterator();
        
        for (int i = 0; categoryItr.hasNext(); i++) {
            System.out.println(String.format("%d. %s", (i + 1), categoryItr.next().getCategoryName()));
        }
    }
    
    // Validation of Name
    public String validateName(String promptInfo) {
        boolean validName = false, unicodeName = true;
        String mainErrorName = "Please enter a valid donor name (e.g. James)";
        int numChar = 0;
        String donorName;
        
        do {
            donorName = scanner.nextLine(promptInfo);
            
            unicodeName = true;                                     // make sure donor's name only contains char and space
            for (int i = 0; i < donorName.length(); i++) {
                if (Character.isLetter(donorName.charAt(i)) == false && donorName.charAt(i) != ' ') {
                    unicodeName = false;
                } else if (Character.isLetter(donorName.charAt(i))){
                    numChar++;
                }
            }
            
            if (!unicodeName) {                                     // prompt invalid message and retry
                print.error(mainErrorName);
                print.error("Donor name cannot contain special symbols or numbers");
            } else if (donorName.length() <= 0 || numChar == 0) {
                print.error(mainErrorName);
                print.error("Donor name cannot be empty");
            } else if (donorName.length() >= 20) {
                print.error(mainErrorName);
                print.error("For record purposes, the donor’s name must not exceed 20 characters");
            } else
                validName = true;
            
        } while(!validName);
        
        return donorName;
    }
    
    public String validatePhoneNumber(String promptInfo) {
        boolean validPhoneNumber = false;
        String mainErrorPhoneNumber = "Please enter a valid donor phone number (e.g. 01234567890)";
        String phoneNumber;
        
        do {
            phoneNumber = scanner.nextLine(promptInfo);
            
            if(!phoneNumber.matches("[0-9]+")) {
                print.error(mainErrorPhoneNumber);
                print.error("Donor phone number cannot contain characters or special symbols");
            } else if (phoneNumber.charAt(0) != '0' || phoneNumber.charAt(1) != '1') {
                print.error(mainErrorPhoneNumber);
                print.error("Donor phone number should start with 01");
            } else if (phoneNumber.length() < 10) {
                print.error(mainErrorPhoneNumber);
                print.error("Donor phone number cannot less than 10 digits");
            } else if (phoneNumber.length() > 11) {
                print.error(mainErrorPhoneNumber);
                print.error("Donor phone number cannot more than 11 digits");
            } else
                validPhoneNumber = true;
        } while (!validPhoneNumber);
        
        return phoneNumber;
    }
}
