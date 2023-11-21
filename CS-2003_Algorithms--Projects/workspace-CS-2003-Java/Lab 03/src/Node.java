/* 
Name: David Fantin
Lab Section: 2000-03
Lecture Section: 2003-02
Date: 1/23/19
Assignment: Lab 03
*/

/**
 * class Node for the implementation of simple linked list. The node contains
 * only an item and a reference to the following Node
 */

public class Node<E> {

	/** Object contained in the Node */
	private E item;
	/** reference to the following node */
	private Node<E> next;

	/**
	 * Contructor: create a new Node containing the specified item
	 * 
	 * @param newItem
	 *            the item encapsulated in the node
	 */

	private Node<E> previous;

	public Node(E newItem) {
		item = newItem;
		next = null;
		previous = null;
	} // end constructor

	/**
	 * Constructor: creates a new Node containing the specified item and links
	 * this node to the specified node: this -> nextNode
	 * 
	 * @param newItem
	 *            the item encapsulated in the node
	 * @param nextNode
	 *            the successor node of this node.
	 */
	public Node(E newItem, Node<E> nextNode, Node<E> previousNode) {
		item = newItem;
		next = nextNode;
		previous = previousNode;
	} // end constructor

	/**
	 * set the item in the node
	 * 
	 * @param newItem
	 *            the new item to be encapsulated in the node.
	 */
	public void setItem(E newItem) {
		item = newItem;
	} // end setItem

	/**
	 * get the item encapsulated in the node
	 * 
	 * @return the object encapsulated in the node
	 */
	public E getItem() {
		return item;
	} // end getItem

	/**
	 * set the reference to the following node
	 * 
	 * @param new
	 *            following node
	 */

	/******************** Previous *********************/

	public void setPrevious(Node<E> previousNode) {
		previous = previousNode;
	}

	/**
	 * get the following node
	 * 
	 * @returns the following node
	 */
	public Node<E> getPrevious() {
		return previous;
	}

	/********************** Next ***********************/
	public void setNext(Node<E> nextNode) {
		next = nextNode;
	} // end setNext

	/**
	 * get the following node
	 * 
	 * @returns the following node
	 */
	public Node<E> getNext() {
		return next;
	} // end getNext

} // end class Node
