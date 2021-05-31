
/**
	 * This class implements a stack using an array.
	 * @author Zicong Qu
	 */
// The following header is provided by the instruction.
public class ArrayStack<T> implements ArrayStackADT<T> {
	private T[] stack; //This array stores the data items of the stack.
	private int top;   //stores the position of the last data item in the stack
	public static String sequence = "";
	
	/** Creates an empty stack, and initialize top to -1.
	 */
	public ArrayStack() {
		stack = (T[])(new Object[14]); //The default initial capacity of the array used to store the items of the stack is 14
		top = -1;
		sequence = "";
	}
	
	/** Creates an empty stack using an array of length equal to the value of the parameter.
	 *   @param Length of the array.
	 */
	public ArrayStack(int initialCapacity) {
		stack = (T[])(new Object[initialCapacity]);
		top = -1;
		sequence="";
	}

	/** Creates an empty stack using an array of length equal to the value of the parameter.
	 *   @param New data to add to the top of the stack
	 */
	public void push(T dataItem) {
		T[] tempStack; // temporary stack to transfer the data
		
		//If the capacity of the array is smaller than 50
		if (stack.length < 50 && (stack.length < top + 2)) {
			tempStack = (T[])(new Object[stack.length + 10]); // add 10 to the length
			for (int i = 0; i <= top; i++) {
				tempStack[i] = stack[i];
			}
			stack = tempStack;
		}
		
		//If the capacity of the array is greater or equals 50
		if (stack.length >= 50 && (stack.length < top + 2)) {
			tempStack = (T[])(new Object[stack.length * 2]); // double the length
			for (int i = 0; i <= top; i++) {
				tempStack[i] = stack[i];
			}
			stack = tempStack;
		}
		
		// add the data
		stack[top + 1] = dataItem;
		top++;
		
		// Used in TestSearch to make sure I am following a correct path
		if (dataItem instanceof MapCell) {
			sequence += "push" + ((MapCell)dataItem).getIdentifier();
		}
		else {
			sequence += "push" + dataItem.toString();
		}
	}
	
	/** Removes and returns the data item at the top of the stack. An EmptyStackException is thrown if the stack is empty. 
	 * @throws EmptyStackException
	 */
	public T pop() throws EmptyStackException {
		// Check if the stack is empty
		if (isEmpty()) {
			throw new EmptyStackException("The stack is empty. No data to pop.");
		}
		
		//remove and return the top element
		T result = stack[top];
		stack[top] = null;
		top--;
		
		T[] tempStack; // temporary stack to transfer the data
		if (top + 1 < stack.length / 4) {
			if (stack.length < 28) {
				tempStack = (T[])(new Object[14]);
			}
			else {
				tempStack = (T[])(new Object[stack.length / 2]); 
			}
			
			for (int i = 0; i <= top; i++) {
				tempStack[i] = stack[i];
				}
			stack = tempStack;
		}
		
		if (result instanceof MapCell) {
			sequence += "pop" + ((MapCell)result).getIdentifier();
		}
		else {
			sequence += "pop" + result.toString();
		}
		
		return result;
	}
		
	/** Returns the data item at the top of the stack without removing it. An EmptyStackException is thrown if the stack is empty
	 * @throws EmptyStackException
	 */
	public T peek() throws EmptyStackException {
		if (isEmpty()) {
			throw new EmptyStackException("The stack is empty. No data to pop.");
		}
		return stack[top];
}

	
	/** Returns true if the stack is empty and returns false otherwise.
	 * @return Returns true if the stack is empty and returns false otherwise.
	 */
	public boolean isEmpty() {
		return (stack[0] == null);	// if stack is empty, returns true, else returns false
	}
	
	/** Returns the number of data items in the stack.
	 * @return return the number of data itens in the stack
	 */
	public int size() {
		return top + 1;
	}
	
	/** Returns the capacity of the array stack
	 * @return Returns the capacity of the array stack
	 */
	public int length() {
		return stack.length;
	}
	
	/** Returns a String representation of the stack.
	 * @return Returns a String representation of the stack.
	 */
	public String toString() {
		String result = "Stack: ";
		
		for (int i = 0; i < top; i++) {
			result += stack[i] + ", ";
		}
		result += stack[top];
		return result;
	}
}
