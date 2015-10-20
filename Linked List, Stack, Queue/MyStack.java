//my stack implementation using linked list
// push pop peek clear O(1) time using linked list
public class MyStack<T> {
	MyLinkedList<T> stack = new MyLinkedList<T>();

	private int size;

	public MyStack() {
		size = 0;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public void clear() {
		stack = new MyLinkedList<T>();
	}

	public void push(T data) {
		stack.addFront(data);
		size++;
	}

	public T pop() {
		T ret = stack.removeHead();
		size--;
		return ret;
	}

	public T peek() {
		return stack.getHead();
	}

	public int getSize() {
		return size;
	}

	public static void main(String[] args) {
		MyStack s = new MyStack();
		s.push(1);
		s.push(3);
		s.push(5);
		s.push(7);
		System.out.println("stack size: "+s.getSize());
		System.out.println("peek " + s.peek());
		while(!s.isEmpty()) {
			System.out.println(s.pop());
		}
		System.out.println("stack size: "+s.getSize());

		s.push(1);
		s.push(3);
		s.push(5);
		s.push(7);
		s.clear();
		while(!s.isEmpty()) {
			System.out.println(s.pop());
		}
	}


} 