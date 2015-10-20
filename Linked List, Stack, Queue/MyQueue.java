//my queue implementaion using two stacks
//enqueue take O(n) time and dequeue takes O(1)
public class MyQueue<T> {
	MyStack<T> s1 = new MyStack<T>();
	MyStack<T> s2 = new MyStack<T>();

	int size = 0;

	public boolean isEmpty() {
		return size == 0;
	}

	public void enqueue(T data){
		while(!s1.isEmpty()) {
			s2.push(s1.pop());
		}		
		s1.push(data);
		while(!s2.isEmpty()) {
			s1.push(s2.pop());
		}
		size++;
	}

	public T dequeue() {
		size--;
		return s1.pop();
	}

	public T peek() {
		return s1.peek();
	}

	public int getSize() {
		return size;
	}

	public static void main(String[] args) {
		MyQueue q = new MyQueue();
		q.enqueue(1);
		q.enqueue(2);
		q.enqueue(3);
		q.enqueue(4);
		q.enqueue(5);
		q.enqueue(6);
		while(!q.isEmpty()){
			System.out.println(q.dequeue());
		}
		q.enqueue(13);
		System.out.println(q.peek());

	}


}