/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adt;

import java.util.Iterator;
/**
 *
 * @author Acer
 */
public interface ListInterface<T> {
    
    /** 
     * Task: clear all
     */
    void clear();
    
    /**
     * Task: Add a new entry to last of the list
     * 
     * @param newElement
     */
    boolean add(T newElement);
    
    /**
     * Task: Add a new entry to any place of the list
     * 
     * @param index
     * @param newElement
     */
    boolean add(int index, T newElement);
    
    /**
     * Task: Add all the new entries to the list
     * 
     * @param newElements
     */
    boolean addAll(T... newElements);
    
    /**
     * Task: Get the record contains the specified character or string
     * 
     * @param element
     * @return records
     */
    boolean contains(T element);
    
    /**
     * Task: Get the specified record in the list
     * 
     * @param index
     * @return the specified record
     */
    T get(int index);
    
    /**
     * Task: Get the record's index in the list
     * 
     * @param element
     * @return number
     */
    int indexOf(T element);
    
    /**
     * Task: Check the list whether is empty
     * 
     * @return true or false
     */
    boolean isEmpty();
    
    /**
     * Task: Remove the specified record from the list
     * 
     * @param element
     */
    boolean remove(T element);
    
    /**
     * Task: Remove the record from the list by index
     * 
     * @param index
     */
    boolean remove(int index);
    
    /**
     * Task: Remove all elements related to the record from the list
     * 
     * @param elements
     * @return 
     */
    boolean removeAll(T... elements);
    
    /**
     * Task: Set new record with index to the list
     * 
     * @param index
     * @param newElement
     * @return 
     */
    boolean set(int index, T newElement);
    
    /**
     * Task: Get number of records in the list
     * 
     * @return 
     */
    int sizeOf();
    
    /**
     * 
     * 
     * @param list
     * @return 
     */
    ListInterface where(WhereClause<T> list);
    
    /**
     * Task: sort list order by
     * 
     * @param list 
     */
    void orderBy(OrderClause<T> list);
    
    /**
     * Task: Get first or default
     * 
     * @param list
     * @return 
     */
    T firstOrDefault(FirstOrDefaultClause<T> list);
    
    /**
     * Task: Get Iterator
     * 
     * @return 
     */
    Iterator<T> getIterator();
}
