/**
 * 
 * @author YongHui
 *
 * @param <T>
 */
public class CircularArrayBuffer<T> implements CircularBuffer<T> {
	
	@SuppressWarnings("unchecked")
	private T[] arr = (T[]) new Object[10];
	private int count, front, rear;
	private BufferGrowMode mode = BufferGrowMode.REGROW;
	
    /**
     * @return true if buffer is empty (has no elements) false otherwise
     */
	@Override
	public boolean isEmpty() {
		return count == 0;
	}

	/**
     * @return the maximum number of elements that this buffer can hold without 
     *         either a regrow or an overwrite
     */
	@Override
	public int capacity() {
		return arr.length;
	}

    /**
     * @return the number of actual elements in the buffer
     */
	@Override
	public int size() {
		return count;
	}

	/**
     * Sets the correct action for the buffer to take when it is full
     * 
     * @param newMode the mode to use when t pahe buffer gets full
     */
	@Override
	public void setMode(BufferGrowMode newMode) {
		if(newMode == BufferGrowMode.REGROW) {
			mode = BufferGrowMode.REGROW;
		} else if (newMode == BufferGrowMode.OVERWRITE) {
			mode = BufferGrowMode.OVERWRITE;
		}
	}

	   /**
     * Adds an item to the buffer.  This may cause either a regrow operation or an overwrite of the information
     * in the buffer.
     * 
     * @param item the item to add to the buffer
     */
	@Override
	public void add(T item) {
		 if(size() == capacity()) {
			if(mode == BufferGrowMode.REGROW) {
				System.out.println("regrow");
				regrow();
				rear = count;
				arr[rear] = item;
				rear = (rear+1) % arr.length;
				count++;
			}
			if (mode == BufferGrowMode.OVERWRITE) {
				System.out.println("overwrite");
				rear = (rear+1) % arr.length;
				arr[front] = item;
				front = (front+1) % arr.length;
			}
		} else {
			System.out.println("else");
			arr[rear] = item;
			rear = (rear+1) % arr.length;
			count++;
		}
		
	}

	   /**
     * Removes the oldest item (front) from the buffer.  This also removes that item from the buffer.
     * 
     * @return the oldest item in the buffer,  or null if buffer is empty
     */
	@Override
	public T remove() {
		if(!isEmpty()){
			front = (front+1) % arr.length;
			count--;
			return arr[front-1];
		}
		return null;
	}

	   /**
     * Tests whether this buffer has the requested item
     * @param item the item to check for
     * @return true if buffer has this item stored, false otherwise
     */
	@Override
	public boolean contains(T item) {
		for(int i = 0; i<size(); i++) {
			if(arr[(front+i)%arr.length].equals(item)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * regrows the array by making new array 2 * original size and moves all elements to new array
	 * 
	 */
	private void regrow (){
		@SuppressWarnings("unchecked")
		T[] newArr = (T[]) new Object[2* arr.length];
		for(int i = 0; i<size();i++) {
			newArr[i] = arr[(front+i)%arr.length];
		}
		front = 0;
		rear = size()+1;
		arr = newArr;
	}


}
