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
public interface WhereClause<T> {
    /**
     * Task: Match 1 or more element(s)
     * 
     * @param element
     * @return 
     */
    boolean match(T element);
}
