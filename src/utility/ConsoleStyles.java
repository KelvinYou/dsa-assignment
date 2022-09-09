/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import javax.swing.JOptionPane;

/**
 *
 * @author Acer
 */
public class ConsoleStyles {
    // Reset
    public static final String RESET = "\033[0m";  // Text Reset

    // Regular Colors
    public static final String BLACK = "\033[0;30m";   // BLACK
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String PURPLE = "\033[0;35m";  // PURPLE
    public static final String CYAN = "\033[0;36m";    // CYAN
    public static final String WHITE = "\033[0;37m";   // WHITE
    
    // Get success to green color
    public void success(String message) {
        System.out.println(GREEN + "\n<SUCCESS: " + message + " >" + RESET);
    }
    
    // Get success message to purple color
    public void cancelled(String message) {
        System.out.println(PURPLE + "\n<CANCELLED: " + message + " >" + RESET);
    }
    
    // Get failed message to purple color
    public void failed(String message) {
        System.out.println(PURPLE + "\n<FAILED: " + message + " >" + RESET);
    }
    
    // Get error message to purple color
    public void error(String message) {
        System.out.println(PURPLE + "<ERROR: " + message + " >" + RESET);
    }

    // Get warning message to purple color
    public void warning(String message) {
        System.out.println(PURPLE + "<WARNING: " + message + " >" + RESET);
    }
    
    // Get hint message to blue color
    public void hint(String message) {
        System.out.println(BLUE + "<HINT: " + message + " >" + RESET);
    }
    
    // Get other message to blue color
    public void otherMsg(String message, int nextLine) {
        for (int i = 0; i < nextLine; i++)
            System.out.println();
        System.out.println(BLUE + "< " + message + " >" + RESET);
    }
    
    // Get element to center
    public void toCenter(String title, int width) {
        int difference = width - title.length(), backWidth, frontWidth;
        
        frontWidth = backWidth = (difference - 2)/2;
        
        if (difference % 2 == 1)
            backWidth += 1;
        
        System.out.println("|" + getDivider(' ', frontWidth) + title + getDivider(' ', backWidth) + "|");

    }
    
    // Get table header's style
    public void tableHeader(String title, int tableWidth) {
        int difference = tableWidth - title.length(), backWidth, frontWidth;
        
        frontWidth = backWidth = (difference - 2)/2;
        
        if (difference % 2 == 1)
            backWidth += 1;
        
        System.out.println("\n\n" + getDivider('=', tableWidth));
        System.out.println("|" + getDivider(' ', frontWidth) + title + getDivider(' ', backWidth) + "|");
        System.out.println(getDivider('=', tableWidth));
    }
    
    // Get table divider line
    public void tableMiddleLine(int tableWidth) {
        System.out.println(getDivider('-', tableWidth));
    }
    
    // Get table bottom line
    public void tableFooter(int tableWidth) {
        System.out.println(getDivider('=', tableWidth));
    }
    
    // Get divident
    public static String getDivider(char symbol, int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < length; i++) {
            stringBuilder.append(symbol);
        }
        return stringBuilder.toString();
    }
    
    // Get Info Box
    public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
}
