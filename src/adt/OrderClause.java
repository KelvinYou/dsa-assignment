/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adt;

/**
 *
 * @author Acer
 */
public interface OrderClause<T> {
    
    /**
    * Task: Compare the record
    * 
    * return move to the front or move to the back
    */
    public static final int MOVE_FORWARD = -1;
    public static final int MOVE_BACKWARD = 1;
    
    int compare(T t1, T t2);
}
