
/* 
Name: David Fantin
Lab Section: 2000-03
Lecture Section: 2003-02
Date: 1/16/19
Assignment: Lab 02
*/

import java.util.ArrayList;
import java.util.Iterator;

public class ListArrayListBased<E> implements ListInterface<E>, Iterable<E> 
{

	/** array on which the list is based */
	private ArrayList<E> items;

	/** default Constructor */	
	public ListArrayListBased() 
	{
		items = new ArrayList<E>();
	}

	/**
	 * constructor with the first item: constructs a list with the specified
	 * item as single element of this list
	 * 
	 * @param item
	 *            first element of the list
	 */
	public ListArrayListBased(E item) 
	{
		items = new ArrayList<E>();
		items.add(item);
	}

	/**
	 * copy constructor: create a duplicate of the specified list
	 * 
	 * @param list
	 *            to be copied
	 */	
	public ListArrayListBased(ListArrayListBased<E> L) 
	{
		items = new ArrayList(L.items);
	}

	/**
	 * Tests if this list has no elements.
	 * 
	 * @return <code>true</code> if this list has no elements;
	 *         <code>false</code> otherwise.
	 */	
	public boolean isEmpty() 
	{
		return items.isEmpty();
	} // end isEmpty

	/**
	 * Returns the number of elements in this list.
	 * 
	 * @return the number of elements in this list.
	 */
	public int size() 
	{
		return items.size();
	} // end size

	/**
	 * Remove all the elements in this list.
	 */	
	public void removeAll() 
	{
		items.clear();
	} // end removeAll

	/**
	 * Inserts the specified element at the specified position in this list.
	 * Shifts the element currently at that position (if any) and any subsequent
	 * elements to the right (adds one to their indices).
	 * 
	 * @param index
	 *            index at which the specified element is to be inserted.
	 * @param item
	 *            element to be inserted.
	 * @throws IndexOutOfBoundsException
	 *             - if index is out of range (index < 0 || index > size()).
	 */	
	public void add(int index, E item) throws ListIndexOutOfBoundsException 
	{
		if (index >= 1 && index <= items.size() + 1) 
		{
			items.add(index - 1, item);
		} else { // index out of range
			throw new ListIndexOutOfBoundsException("ListIndexOutOfBoundsException on add");
		} // end if
	} // end add


	/**
	 * Returns the element at the specified position in this list.
	 * 
	 * @param index
	 *            index of element to return.
	 * @return the element at the specified position in this list.
	 * @throws IndexOutOfBoundsException
	 *             - if index is out of range (index < 0 || index > size()).
	 */	
	public E get(int index) throws ListIndexOutOfBoundsException 
	{
		if (index >= 1 && index <= items.size()) 
		{
			return items.get(index - 1);
		} else { // index out of range
			throw new ListIndexOutOfBoundsException("ListIndexOutOfBoundsException on get");
		} // end if
	} // end get

	/**
	 * Removes the element at the specified position in this list. Shifts any
	 * subsequent elements to the left (subtracts one from their indices).
	 * 
	 * @param index
	 *            the index of the element to remove
	 * @throws IndexOutOfBoundsException
	 *             - if index is out of range (index < 0 || index > size()).
	 */	
	public void remove(int index) throws ListIndexOutOfBoundsException 
	{
		if (index >= 1 && index <= items.size()) 
		{
			// delete item by shifting all items at
			// positions > index toward the beginning of the list
			// (no shift if index == size)
			items.remove(index - 1);
		} else { // index out of range
			throw new ListIndexOutOfBoundsException("ListIndexOutOfBoundsException on remove");
		} // end if
	} // end remove


	/** method to make the class iterable */
	public Iterator<E> iterator() 
	{
		return items.iterator();
	}

	/**
	 * appends the specified element to the end of this list.
	 * 
	 * @param elt
	 *            element to be added at the end of the list
	 */
	@Override
	public void append(E elt) 
	{
		items.add(elt);
	}
	
	/**
	 * contains Looks for the index of the specified element in this list. If
	 * the element is not present, the method returns <code>-1</code>
	 * 
	 * @param elt
	 *            the element which index is looked for.
	 * @return either the index of the location in the list where the argument
	 *         is present or <code>-1</code> if the argument is not contained in
	 *         the list.
	 */
	@Override
	public int contains(E elt) 
	{
		int index = 0;
		for (int i = 0; i <= items.size()-1; i++)
		{
			if(elt.equals(items.get(i)))
				return i;
		}
		return -1;		
	}
	
	/**
	 * delete delete the the specified element in this list if exists. Shifts
	 * any subsequent elements to the left (subtracts one from their indices).
	 * 
	 * @param elt
	 *            the element, if it exists, to delete
	 */
	@Override
	public void delete(E elt) 
	{
		if(items.contains(elt)){
			items.remove(elt);
		}	
	}

	/**
	 * display Prints all the elements in this list on the console in sequence
	 */
	@Override
	public void display() 
	{
		for( int i = 0; i <= items.size()-1; i++)
		{
			System.out.print(items.get(i)+ ", ");
		}
		System.out.println();
	}
} // end ListVectorBased