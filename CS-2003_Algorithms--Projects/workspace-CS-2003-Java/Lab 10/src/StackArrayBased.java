public class StackArrayBased<E> implements StackInterface<E>{
  final int MAX_STACK = 50;  // maximum size of stack
  private E items[];
  private int top;

  public StackArrayBased() {
    items = (E[]) new Object[MAX_STACK]; 
    top = -1; 
  }  // end default constructor

  public boolean isEmpty() {
    return top < 0;
  }  // end isEmpty

  public boolean isFull() {
    return top == MAX_STACK-1;
  }  // end isFull

  public void push(E newItem) throws StackException {
    if (!isFull()) {
      items[++top] = newItem;
    } 
    else {
      throw new StackException("StackException on " +
                               "push: stack full");
    }  // end if
  }  // end push

  public void popAll() {
    items = (E[]) new Object[MAX_STACK]; 
    top = -1; 
  }  // end popAll
  
  public E pop() throws StackException {
    if (!isEmpty()) {
      return items[top--];
    }
    else {
      throw new StackException("StackException on " +
                               "pop: stack empty");
    }  // end if
  }  // end pop

  public E peek() throws StackException {
    if (!isEmpty()) {
      return items[top];
    }
    else {
      throw new StackException("Stack exception on " +
                               "peek - stack empty");
    }  // end if
  }  // end peek
}  // end StackArrayBased
