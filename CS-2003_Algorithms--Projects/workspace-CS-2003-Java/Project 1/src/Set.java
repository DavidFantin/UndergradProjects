//Name: David Fantin
//Lab Section: 2000-03
//Lecture Section: 2003-02
//Date: 2/3/19
//Assignment: Project 1

import java.util.Iterator;

public class Set<E> implements SetInterface<E> 
{
	private ListReferenceBased<E> items;
	
/************************CONSTRUCTORS**************************/	
	
	/**
	 * This is a default constructor to initiate the variable "items".
	 */
	public Set()
	{
		items = new ListReferenceBased<E>();	
	}
	
	/**
	 * This is a copy constructor, to copy an inputed set.
	 * @param L is an object of type Set<E> that will be copied.
	 */
	public Set(Set<E> L)
	{
		items = new ListReferenceBased<E>(L.items);
	}
	
	/**
	 * This is a single argument constructor that takes in E type variable.
	 * @param elm is an element of type E to be put into the "ListReferenceBased<E>()" method.
	 */
	public Set(E elm)
	{
		items = new ListReferenceBased<E>(elm);
	}
	
/*************************************************************/	
	/**
	 * setSize is a method that returns the number of elements in the
	 * current set as an integer value.
	 * @return returns the size of "items"
	 */
	public int setSize()
	{
		return items.size();
	}
	
	/**
	 * print is a method that prints out the contents of the current set.
	 */
	public void print()
	{
		for(int i = 1; i <= setSize(); i++)
		{
			System.out.print(items.get(i) + ", ");
		}
	}
	/**
	 * insert is a method that takes an element of type E and inserts it
	 * into the set.
	 * @param elm is an element of type E to be inserted into the current set.
	 */
	public void insert(E elm)
	{
		items.append(elm);
	}
	
	/**
	 * arrayInsert is a method that takes an array of elements of type E and
	 * inserts them into the set.
	 * @param elm is an array of type E[] to be inserted into the current set.
	 */
	public void arrayInsert(E[] elm)
	{
		for(E items: elm)
		{
			insert(items);
		}
	}
	/**
	 * union is a method that takes a Set as an argument and returns
	 * a new Set that is the union of the current set and the argument.
	 * @param altSet is a set that the user inputs in order to add it with
	 * the current set.
	 * @return newSet is the union of the current set and altSet.
	 */
	public Set<E> union(Set altSet)
	{
		Set<E> newSet = new Set<E>(altSet);
		
		for(int i = 1; i <= setSize(); i++)
		{
			newSet.insert(items.get(i));
		}
		return newSet;
	}
	
	/**
	 * intersection is a method that takes a Set as an argument and
	 * returns a new Set that is the intersection of the current set
	 * and the argument.
	 * @param altSet is a set that the user inputs in order to contrast 
	 * with the current set.
	 * @return newSet is the intersection of the current set and altSet.
	 */
	public Set<E> intersection(Set altSet)
	{	
		Set<E> newSet = new Set<E>();
		for(int i = 1; i <= setSize(); i++)
		{
			for(int j = 1; j <= altSet.setSize(); j++)
			{
				if(altSet.items.get(i).equals(altSet.items.get(j)))
				{
					if(!newSet.in(items.get(i)))
					{
						newSet.insert(items.get(i));
					}
				}
			}
		}
		return newSet;
	}
	
	/**
	 * difference is a method that takes a Set as an argument and returns
	 * a new Set that is the difference of the current set and the argument.
	 * @param altSet is a set that the user inputs in order to contrast 
	 * with the current set.
	 * @return newSet is the difference of the current set and altSet.
	 */
	public Set<E> difference(Set altSet)
	{
		Set<E> newSet = new Set<E>(this);
		for(int i = 1; i <= setSize(); i++)
		{
			if(altSet.in(items.get(i)))
				newSet.items.delete(items.get(i));
		}
		return newSet;
	}
	
	/**
	 * in is a method that takes an element of type E and returns a boolean
	 * value depending on whether the given E is contained in the current set
	 * or not.
	 * @param elm is an element of type E that is tested to see if it is in the
	 * current set.
	 * @return false if not in set, true if it is.
	 */
	public boolean in(E elm)
	{
			if(items.contains(elm) > -1)
			{
				return true;
			}
			else{
				return false;
			}
	}
}