import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;



public class AVLTree<E extends Comparable> implements BinaryTree<E> {
	private AVLNode<E> root;
	private int size;
	
	private class AVLNode<E> {
		E data;
		AVLNode<E> left , right;
		int height, bf;
		
		public AVLNode(E data) {
			this.data = data;
		}
		
	}

	/**
	 * Adds the item to the tree.  Duplicate items and null items should not be added.
	 * 
	 * @param item the item to add
	 * @return true if item added, false if it was not
	 */
	@Override
	public boolean add(E item) {
		if(item == null) {
			return false;
		}
		if(contains(item)){
			return false;
		}
		root = add(item, root);
		size++;
		return true;
	}
	
	/**
	 * helper method for recursive add
	 * @return 
	 */
	private AVLNode<E> add(E item, AVLNode<E> node) {
		if (node == null) {
            AVLNode<E> a = new AVLNode<E>(item);
            return a;
		}
		if(compare(item, node.data) < 0) {
			if (node.left != null) {
				node.left = add(item, node.left);
				node.left = rotate(node.left);
			} else {
				node.left = add(item,null);
			}
			} else {
				if (node.right != null) {
					node.right = add(item, node.right);
					node.right = rotate(node.right);
				} else {
					node.right = add(item,null);
				}
				}
		updateTree(node);

		return rotate(node);
		
		}
	
			
	/**
	 * returns the maximum element held in the tree.  null if tree is empty.
	 * @return maximum item or null if empty
	 */
	@Override
	public E max() {
		if(isEmpty()) {
			return null;
		} else if(root.right == null) {
			return root.data;
		} else 
			return findMax(root).data;
	}
	
	/**
	 * helper method for finding max
	 * @param node
	 * @return
	 */
	  private AVLNode<E> findMax(AVLNode<E> node) { 
	        if (node.right == null) 
	            return node; 
	        else return findMax(node.right); 
	    } 

	/**
	 * returns the number of items in the tree
	 * @return 
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * 
	 * @return true if tree has no elements, false if tree has anything in it.
	 */
	@Override
	public boolean isEmpty() {
		return root == null;
	}

	/**
	 * @return the minimum element in the tree or null if empty
	 */
	@Override
	public E min() {
		if(isEmpty()) {
			return null;
		} else if(root.left == null) {
			return root.data;
		} else {
			return findMin(root).data;
		}
	}
	
	private AVLNode<E> findMin(AVLNode<E> node) {
		if(node.left == null) {
			return node;
		} else {
			return findMin(node.left);
		}
	}
	/**
	 * Checks for the given item in the tree.
	 * @param item the item to look for
	 * @return true if item is in tree, false otherwise
	 */
	@Override
	public boolean contains(E item) {
		return checkContains(item, root);
	}
	
	private boolean checkContains(E item, AVLNode<E> node) {
		if(node == null) {
			return false;
		} else if (node.data == null && item == null) {
			return true;
		} else if (item != null && item.equals(node.data)) {
			return true;
		} 
		if ((item != null || node.data != null) && (compare(item, node.data) > 0)) {
			return checkContains(item, node.right);
		} else {
			return checkContains(item, node.left);
		}
	}
		
	/**
	 * removes the given item from the tree
	 * @param item the item to remove
	 * @return true if item removed, false if item not found
	 */
	public boolean remove(E item) {
		if(item == null) {
			return false;
		}
		if(!contains(item)) {
			return false;
		} 
		if(root == null) {
			return false;
		}
		root = removeRecurse(item, root);
		return true;
	}
	
	private AVLNode<E> removeRecurse(E item, AVLNode<E> node) {
		if (node == null) {
			return node;
		}
		if (compare(item, node.data) < 0) {
			node.left = removeRecurse(item, node.left);
		} else if (compare(item, node.data) > 0) {
			node.right = removeRecurse(item, node.right);
		} else if (node.left != null && node.right != null) {
			AVLNode<E> newRoot = findMin(node.right);
			node.data = newRoot.data;
			node.right = removeRecurse(node.data, node.right);
			updateTree(newRoot.right);
			rotate(newRoot.right);
		} else {
			node = (node.left != null) ? node.left : node.right;
		}
		updateTree(node);
		size--;
		return rotate(node);
	}
	
	
	 private void updateTree(AVLNode<E> node) { 
         if (node!=null) { 
             if (node.left==null && node.right==null) {
                 node.height = 0;                   
                 node.bf = 0;
             } else if (node.left !=null && node.right!=null) { 
            	 updateTree(node.left);
            	 updateTree(node.right);
                 node.height = Math.max(node.left.height, node.right.height) + 1; 
                 node.bf = node.left.height - node.right.height; 
             }	else if (node.left != null && node.right == null) {
            	 updateTree(node.left);
                 node.height = (node.left.height + 1); 
                 node.bf = node.left.height + 1;
             } else { 
            	 updateTree(node.right);
                 node.height = node.right.height + 1;
                 node.bf = -1 - node.right.height;
             } 
         } 
     } 
	/**
     * returns an iterator over this collection
     * iterator is based on an in-order traversal
     */
	@Override
	public Iterator<E> iterator() {
		List<E> list = inOrder();
		return list.iterator();
	}
	
	
	/**
	 * inOrder method to help Iterator
	 * @return list
	 */
	public List<E> inOrder() { 
		List<E> list = new LinkedList<E>(); 
        inOrder(root,list); 
        return list; 
    } 
	
	/**
	 * recursive inorder method
	 * @param node
	 * @param list
	 */
    private void inOrder(AVLNode<E> node, List<E> list) { 
        if (node==null) return; 
        inOrder(node.left,list); 
        list.add(node.data); 
        inOrder(node.right,list); 
    } 


	/**
	 * @return a list of the data in post-order traversal order
	 */
	@Override
	public List getPostOrder() {
		List<E> list = new ArrayList<E>();
		postorder(root, list);
		return list;
	}
	
	private void postorder(AVLNode<E> node, List<E> list) {
		if(node == null) return;
		postorder(node.left, list);
		postorder(node.right, list);
		list.add(node.data);
	}

	/**
	    * 
	    * @return a list of the data in level-order traversal order
	    */
	@Override
	public List getLevelOrder() {
		Queue<AVLNode<E>> queue = new LinkedList<AVLNode<E>>();
		List<E> list= new ArrayList<E>();
		if(root == null) {
			return list;
		}
		queue.offer(root);
		while(!queue.isEmpty()){
			AVLNode<E> node = queue.poll();
			list.add(node.data);
			if(node.left != null) {
				queue.offer(node.left);
			}
			if(node.right != null) {
				queue.offer(node.right);
			}
		}
		return list;
	}
	
	/**
     * @return a list of the data in pre-order traversal order
     */
	@Override
	public List getPreOrder() {
		List<E> list = new ArrayList<E>();
		preorder(root, list);
		return list;
	}
	
	private void preorder(AVLNode<E> node, List<E> list) {
		if (node == null) return;
		list.add(node.data);
		preorder(node.left, list);
		preorder(node.right, list);
	}

	 /**
     * Removes all the elements from this tree
     */
	@Override
	public void clear() {
		root = null;
		size = 0;
	}
	
	/**
	 * compare method
	 * @param a
	 * @param b
	 * @return
	 */
	private int compare(E a, E b) {
		return a.compareTo(b);
	}
	
	private AVLNode<E> rotate(AVLNode<E> node) { 
		if(node == null) {
			return node;
		}
		if (node.bf > 1) { 
        	  if (node.left.bf > 0) { 
        		  node = rotateRight(node); 
              } else { 
            	  node = rotateLeftRight(node); 
              }
		} else if (node.bf < -1) {
        	  if (node.right.bf < 0) {
              	  node = rotateLeft(node); 
        	  } else {
                  node = rotateRightLeft(node);
              }
		}
		return node; 
	}
	
	private AVLNode<E> rotateLeft (AVLNode<E> node) { 
		System.out.println("left " + node.data);
        AVLNode<E> newRoot = node.right; 
        node.right = newRoot.left;
        newRoot.left = node;
        return newRoot; 
    } 

	private AVLNode<E> rotateRight (AVLNode<E> node) { 
		System.out.println("right " + node.data);
        AVLNode<E> newRoot = node.left; 
        node.left = newRoot.right;
        newRoot.right = node;
        return newRoot; 
    } 

	private AVLNode<E> rotateLeftRight(AVLNode<E> node) {
	   node.left = rotateLeft(node.left);
	   return rotateRight(node);
	}	
	private AVLNode<E> rotateRightLeft(AVLNode<E> node) {
	   node.right= rotateRight(node.right);
	   return rotateLeft(node);
	}
	
	
}
