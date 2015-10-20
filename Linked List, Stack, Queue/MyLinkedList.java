import java.util.*;

//my linkedlist class
public class MyLinkedList<T> {

	class Node<T> {
		T data;
		Node<T> next;
		public Node(T data, Node<T> next) {
			this.data = data;
			this.next = next;
		}
	}

	private Node<T> head;

	//constructor
	public MyLinkedList() {
		head = null;
	}

	//check if list is empty
	public boolean isEmpty() {
		if (head == null) {
			return true;
		}
		return false;
	}

	public void clear() {
		head = null;
	}

	//add to front
	public void addFront(T data) {
		head = new Node(data, head);
	}

	//returns head's data
	public T getHead() {
		if(isEmpty()) {
			return null;
		}
		return head.data;
	}

	//removes head and return's removed data
	public T removeHead() {
		T ret = getHead();
		head = head.next;
		return ret;
	}

	//add node at the end of the list
	public void addLast(T data) {
		if(isEmpty()) {
			head = new Node<T>(data,null);
		} else {
			Node<T> curr = head;
			while(curr.next!=null) {
				curr = curr.next;
			}
			curr.next = new Node(data, null);
		}
	}

	//get the data of the tail
	public T getTail() {
		if(isEmpty()) {
			return null;
		}
		Node<T> curr = head;
		while(curr.next!=null) {
			curr = curr.next;
		}
		return curr.data;
	}

	//remove specific value
	public void remove(T data) {
		if(isEmpty()) {
			throw new RuntimeException("Empty!");
		}
		if(head.data.equals(data)){
			head = head.next;
		}
		Node<T> curr = head;
		Node<T> prev = null;
		while(curr.next!=null) {
			prev = curr;
			curr = curr.next;
			if(curr.data.equals(data)) {
				// System.out.println(prev.data+" "+curr.data);
				prev.next = curr.next;
			}
		}

	}

	//check if list contains a specific item
	public boolean contains(T data) {
		Node<T> curr = head;
		while(curr!=null) {
			if(curr.data.equals(data)) {
				return true;
			}
			curr = curr.next;
		}
		return false;
	}


	//print myLinkedList
	public void printList() {
		Node<T> curr = head;
		while(curr!=null) {
			System.out.println(curr.data);
			curr = curr.next;
		}
	}


public static void main (String[] args) {
	MyLinkedList list = new MyLinkedList();
	list.addLast(1);
	list.addLast(2);
	list.addLast(3);
	list.addLast(6);
	list.addLast(8);
	list.addLast(9);
	list.addLast(19);
	// list.remove(1);
	// list.remove(2);
	list.getHead();
	System.out.println("Remove head " +list.removeHead());
	System.out.println("Tail " + list.getTail());
	System.out.println("List contains 3 " + list.contains(3));
	System.out.println("List contains 22 " + list.contains(22));
	
	list.printList();	
	list.clear();
	System.out.println("get head from empty list: "+list.getHead());
}

}

