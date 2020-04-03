package AVL_tree;

import SA.Guest;

public class AVLTree {
	private Node root = null;
	int allGuests =0; 
	
	public AVLTree(Guest root) {
		this.root = new Node(root); 
		this.allGuests = root.howMany(); 
		
	}
	
	//getters starts
	public Node getRoot() {
		return this.root; 
	}
	//getters ends
	
	private boolean rootIsNull() {
		return (this.root == null); 
	}
	
	public void insert (Guest guestToInsert) {
		if(rootIsNull()) {
			
			this.root = new Node(guestToInsert);
			this.allGuests = guestToInsert.howMany(); 
		}
		else {
			this.root.insert(guestToInsert); 
			this.allGuests = this.allGuests + guestToInsert.howMany(); 
		}
		
	}
	
	//return the node with Guest.howMany equals to value if no such Node return NUll.  
	public Guest findGuest(int value, String lastName) {
		if(rootIsNull()) {
			return null; 
		}
		return this.root.findGuest(value, lastName); 
	}
	
	//return the node with the closest value to value param- by default if two nodes has the same difference from value will choose the smaller value.  
	public Guest getClosestGuest(int value, String lastName) {
		if(rootIsNull()) {
			return null; 
		}
		return this.root.findClosestGuest(value, lastName); 
	}
	
	//remove node from the tree if node exist in the tree. 
	public boolean removeNode (Guest guestToRemove) {
		if(rootIsNull()) {
			return false; 
		}
		boolean removed = this.root.removeGuest(guestToRemove);
		//if guest has been removed we need to decrease the number of people this guest represents; 
		if(removed) {
			this.allGuests = this.allGuests - guestToRemove.howMany(); 
			return removed; 
		}
		return removed; 
	}
	
	public int GetAllGuests() {
		if(this.rootIsNull()) {
			return 0;
		}
		return this.allGuests; 
	}

} 				
