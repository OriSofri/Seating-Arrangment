package AVL_tree;

import SA.Guest;

public class AVLTree {
	private Node root = null; 
	
	public AVLTree(Guest root) {
		this.root = new Node(root);  
		
	}
	
	public AVLTree () {
		this.root = null; 
	}
	
	//getters starts
	public Node getRoot() {
		return this.root; 
	}
	
	public void setRoot(Node root) {
		this.root = root; 
	}
	//getters ends
	
	private boolean rootIsNull() {
		return (this.root == null); 
	}
	
	public void insert (Guest guestToInsert) {
		if(rootIsNull()) {
			
			this.root = new Node(guestToInsert);
		}
		else {
			this.root.insert(guestToInsert); 
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
	
	//remove a guest from the tree, if a node needs to be removed following this guest removal remove the node as well. 
	public void removeGuest (Guest guestToRemove) {
		removeGuestHelper(this.root, guestToRemove); 	
	}
	
	private void removeGuestHelper(Node node, Guest guestToRemove) {
		int searchedValue = guestToRemove.howMany(); 
		if(node == null) {
			return;
		}
		if(node.GetNodeInvitedGuest() == searchedValue) {
			//remove the guest.
			node.getGuests().remove(guestToRemove);
			//we need to remove node if it has no guests. 
			if(node.getGuests().isEmpty()) {
				if(node == root) {
					root.removeRoot(); 
				}
				root.removeNode(node); 
			}
		}
		else if(node.GetNodeInvitedGuest() > searchedValue) {
			removeGuestHelper(node.getLeft(), guestToRemove);
		}
		else {
			removeGuestHelper(node.getRight(), guestToRemove);
		}
	}
	
	public int GetAllGuests() {
		if(this.rootIsNull()) {
			return 0;
		} 
		return root.GetAllGuests();
	}

} 				
