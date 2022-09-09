/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import utility.ConsoleStyles;

import java.util.Scanner;
import java.util.InputMismatchException;

/**
 *
 * @author Acer
 */
public class NewScanner {
    private final Scanner scanner = new Scanner(System.in);
    private ConsoleStyles print = new ConsoleStyles();

    public String nextLine(String question) {   // get value from key in
        System.out.print(question);
        return scanner.nextLine();
    }
    
    public char nextChar(String question) {     // get value from key in
        System.out.print(question);
        return scanner.nextLine().charAt(0);
    }
    
    public char nextChar(String question, String errorMessage, char... c) {
        boolean retry;                          // retry if any error
        char getValue;                          // get value from key in
        
        do {
            getValue = NewScanner.this.nextChar(question);
            boolean correct = false;            // set default to incorrect
            for (char ch : c) {
                if (getValue == ch) {
                    correct = true;
                }
            }
            
            if (correct)
                retry = false;
            else {
                print.error(errorMessage);
                retry = true;
            }
        } while(retry);
        return getValue;
    }
    
    public boolean confirmation(String question) {
        boolean retry;                          // retry if any error
        char getValue;                          // get value from key in
        
        do {
            System.out.print(question);
            getValue = scanner.nextLine().charAt(0);
            if (getValue != 'Y' && getValue != 'y' && getValue != 'N' && getValue != 'n') {
                retry = true;
                print.error("Confirmation Option Is Invalid");
            } else
                retry = false;
        } while(retry);
        return getValue == 'Y' || getValue == 'y';
    }
    
    private void clear() {
        scanner.nextLine();
    }
    
    public double nextDouble(String question) {
        boolean retry;                          // retry if any error
        double getValue = -1;                   // get value from key in
            
        do {
            
            System.out.print(question);
            try {
                retry = false;
                getValue = scanner.nextDouble();
            } catch (InputMismatchException ex) {
                print.error("Wrong data type, double required (e.g. 12.34)");
                retry = true;
            } finally {
                clear();
            }
        } while(retry);
        
        return getValue;
    }

    public double nextDouble(String question, String errorMessage, double min, double max) {
        boolean retry;                          // retry if any error
        double getValue;                        // get value from key in
        
        do {
            retry = false;
            getValue = NewScanner.this.nextDouble(question);
            if (getValue < min || getValue > max) {
                print.error(errorMessage);
                retry = true;
            }
        } while(retry);
        
        return getValue;
    }
    
    public int nextInt(String question) {
        boolean retry;                          // retry if any error
        int getValue = -1;                      // get value from key in
        
        do {
            retry = false;
            System.out.print(question);
            try {
                getValue = scanner.nextInt();
            } catch (InputMismatchException ex) {
                print.error("Wrong data type, integer required!");
                retry = true;
            } finally {
                clear();
            }
        } while(retry);
        return getValue;
    }

    public int nextInt(String question, String errorMessage, int min, int max) {
        boolean retry;                          // retry if any error
        int getValue;                           // get value from key in
        
        do {
            getValue = NewScanner.this.nextInt(question);
            if (getValue < min || getValue > max) {
                print.error(errorMessage);
                retry = true;
            } else
                retry = false;
        } while(retry);
        
        return getValue;
    }
}
