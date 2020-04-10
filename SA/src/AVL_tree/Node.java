package AVL_tree;
import java.util.Iterator;
import java.util.LinkedList;

import javax.management.RuntimeErrorException;

import SA.Guest;

class Node {
    private LinkedList<Guest> guests = new LinkedList<Guest>();
    private Node left;
    private Node right;
    private Node parent; 
    private int Height = -1; 
    
 
    public Node(Guest guest) {
        this.guests.add(guest);
        right = null;
        left = null;
        Height = 0; 
        parent = null; 
    }
    
    public Node() {
    	this.right = null; 
    	this.left = null; 
    	this.parent = null;
    }
        
    //***********Getters starts************* 
    private Guest getGuest() {
    	return this.guests.getFirst(); 
    }
    
	public Node getLeft() {
		return this.left;
	}
	
	public Node getRight() {
		return this.right;
	}
    
    public LinkedList<Guest> getGuests() {
    	return this.guests; 
    }
    
    public int getHeight() {
    	return this.Height; 
    }
    
    public int getLeftHeight() {
    	if(this.left == null) {
    		return -1; 
    	}
    	else {
    		return this.left.getHeight(); 
    	}
    }
    
    public int getRightHeight() {
    	if(this.right == null) {
    		return -1; 
    	}
    	else {
    		return this.right.getHeight(); 
    	}
    }
    
    public int GetAllGuests() {
    	int output = GetNodeInvitedGuest() * (this.guests.size());
    	if(this.left != null) {
    		output = output + this.left.GetAllGuests(); 
    	}
    	if(this.right != null) {
    		output = output + this.right.GetAllGuests(); 
    	}
    	return output; 
    }
    
    public int GetNodeInvitedGuest() {
    	return this.getGuest().howMany(); 
    }
    //************Getters ends****************
    
    //************private methods*************
    //returns number of guests each Guest in this node LinkedList represents 
   
    
    //returns the guest with lastName if exist and null if don't. 
    private Guest findByLastName(String lastName) {
    	Guest guest; 
    	Iterator<Guest> itr = this.guests.iterator(); 
    	while(itr.hasNext()) {
    		guest= itr.next();
    		if (guest.getLastName().equalsIgnoreCase(lastName)) {
    			return guest; 
    		}
    	}
    	return null; 
    }
    //returns the guest with last name or a random guest if such does not exist 
    private Guest returnValue(Guest guest, String lastName) {
		if(!lastName.isEmpty()) {
			guest = findByLastName(lastName); 
			if(guest != null) {
				return guest; 
			}
		}
		return this.getGuest();
    }
    
    //************************************
    private void updateHeight() {
		 this.Height = Math.max(getLeftHeight(), getRightHeight()) + 1; 
    }
    //*************************************
    

    private boolean helpInsert(Guest guestToInsert) {
    	int currentGuestNumber = this.GetNodeInvitedGuest(); 
		int numOfGuestsToInsert = guestToInsert.howMany();
		//need those to know if we got higher on not. 

		boolean checkHeight = false; 
		//if we already have a node that represent this guest number we just added to the list of that node. 
		if(currentGuestNumber == numOfGuestsToInsert) {
			this.guests.add(guestToInsert); 
			return false; 
		}
		//need to go to the left subtree. 
		else if(currentGuestNumber > numOfGuestsToInsert ) {
			//add a new left child
			if(this.left == null) {
				//need to create a new node. and to inform all nodes in the path they are higher now or not.  
				Node newNode = new Node(guestToInsert); 
				this.left = newNode; 
				newNode.parent = this; 
				//if this is this node first child than he made himself higher, and there for all of his ancestors  
				if(this.right == null) {
					this.Height++; 
					return true;
				}
				else {
					return false; 
				}
				    
			}
			//walking on the left subtree. 
			else {
				checkHeight  = this.left.helpInsert(guestToInsert);
				if(checkHeight ) {
						//checking if balance is needed
						int leftCurrentHeight = this.getLeftHeight(); 
						int rightCurrentSize = this.getRightHeight();
						//balance is needed.  
						if(leftCurrentHeight- rightCurrentSize >=2) {
							Node leftChild = this.left; 
							//now go through rotation cases- 
							//case 1: check if left child has left child that is higher than left child right child than right rotation
							//case 3: check if left child has right child that is higher than left child left child than double rotation. 
							int leftLeftHeight = leftChild.getLeftHeight(); 
							int leftRightHeight = leftChild.getRightHeight(); 
							//can't be means we did a mistake in previous insertion/deletion. 
							if(leftLeftHeight == leftRightHeight) {
								throw new RuntimeErrorException(null, "AVL tree is unbalanced"); 
							}
							//case 1 perform right rotation
							else if(leftLeftHeight > leftRightHeight) {
								rotation(1, true); 
								//fixed the balance no checking is needed
								return false; 
							}
							//case 3: perform left than right 
							else {
								rotation(3, true); 
								//fixed the balance no checking is needed
								return false; 
							}
						}
						//check if we got higher and if do updates our height.
						updateHeight(); 
						//returns true maybe another node in the path need balance 
						return checkHeight; 
						 
					}
					
				//returns false- we didn't enter the if(checkHeight) condition so we we don't need to check others in the path from now on. 
					return checkHeight; 
				}
			}	
		
		//****** end of left subtree case needs to go right.****** 
		else { 
			//add a new right child
			if(this.right == null) {
				//need to create a new node. and to inform all nodes in the path to check if they need balance now or not.  
				Node newNode = new Node(guestToInsert); 
				this.right = newNode; 
				newNode.parent =this; 
				//if this is this node first child than he made himself higher, and there for all of his ancestors  
				if(this.left == null) {
					this.Height++; 
					return true;
				}
				else {
					//we didn't increase the height of any subtree so no balance is needed. 
					return false; 
				}
				    
			}
			//walking on the right subtree. 
			else {
				checkHeight  = this.right.helpInsert(guestToInsert);
				if(checkHeight ) {
						//checking if balance is needed
						int leftCurrentHeight = this.getLeftHeight(); 
						int rightCurrentSize = this.getRightHeight();
						//balance is needed.  
						if(rightCurrentSize- leftCurrentHeight >=2) {
							Node rightChild = this.right; 
							//now go through rotation cases- 
							//case 2: check if right child has right child that is higher than right child left child than left rotation
							//case 4: check if right child has left child that is higher than right child right child than double rotation. 
							int rightRightHeight = rightChild.getRightHeight(); 
							int rightLeftHeight = rightChild.getLeftHeight(); 
							//can't be means we did a mistake in previous insertion/deletion. 
							if(rightRightHeight == rightLeftHeight) {
								throw new RuntimeErrorException(null, "AVL tree is unbalanced"); 
							}
							//case 1 perform left rotation
							else if(rightRightHeight >= rightLeftHeight) {
								rotation(2, true); 
								//fixed the balance no checking is needed
								return false; 
							}
							//case 3: perform left than right 
							else {
								rotation(4, true); 
								//fixed the balance no checking is needed
								return false; 
							}
						}
						updateHeight(); 
						//returns true maybe another node in the path need balance 
						return checkHeight; 
						 
					}
				//returns false- we didn't enter the if(checkHeight) condition so we we don't need to check others in the path from now on. 
					return checkHeight; 
				}
		}
    }
    //helpInsert ends
    
    
    //rotation: perform the wanted rotation- 1.right rotation 2. left rotation 3.left than right 4. right than left. 
    private void rotation(int rotation, boolean insertion) {
    	Node parent = this.parent; 
    	Node temp; 
    	switch(rotation) {
    	//right rotation
    	case 1: 
    		temp = this.left.right; 
    		Node leftChild = this.left; 
    		//checking if I'm a left or right child
    		if(parent.left == this) {
    			//update my parent to point on my left child instead of me
    			parent.left = leftChild;  
    		}
    		else {
    			//update my parent to point on my left child instead of me
    			parent.right = leftChild;  
    		}
			leftChild.parent = parent;
			//i become my left child right child. his originally right child is saved in temp.  
    		leftChild.right = this; 
    		//my parent become my left child. 
    		this.parent = leftChild;
    		//my left child become my left child right child. 
    		this.left = temp; 
    		temp.parent =this; 
    		updateHeight(); 
    		this.parent.updateHeight();
    		
    	//left rotation	
    	case 2:	
    		temp = this.right.left;  
    		Node rightChild = this.right; 
    		//checking if I'm a left or right child
    		if(parent.left == this) {
    			//update my parent to point on my right child instead of me
    			parent.left = rightChild;  
    		}
    		else {
    			//update my parent to point on my right child instead of me
    			parent.right = rightChild;  
    		}
			rightChild.parent = parent;
			//i become my right child left child. his originally left child is saved in temp.  
    		rightChild.left = this; 
    		//my parent become my right child. 
    		this.parent = rightChild;
    		//my right child become my right child left child. 
    		this.right = temp; 
    		temp.parent =this; 
    		if(insertion) {
    			updateHeight();  
    			this.parent.updateHeight();
    		}
    	case 3:
    	case 4:
    		doubleRotation(rotation); 
    	 
    	default: 
    		System.out.println("Wrong rotation case was inserted");
    	}
    }
    //************************\\
    private void doubleRotation(int rotation) {
    	if(rotation == 3) {
    		this.left.rotation(2, true);
    		rotation(1, true);
    	}
    	else {
    		this.right.rotation(1,true);
    		rotation(2, true); 
    	}
		updateHeight(); 
		this.parent.updateHeight();
    	
    }
    
    
    //3 possible events: 1. erasing a leaf
    //					 2. erasing a node with one child
    //					 3. erasing a node with two children. 
    private void EraseNode(int event) {
    	Node parent = this.parent; 
    	switch(event){
    		case 1: //leaf
    			updateParent(null); 
    		case 2: //one child
    			if(this.left != null) {
    				updateParent(this.left); 
    			}
    			else {
    				updateParent(this.right);
    			}
    		case 3: 
    			Node succesor = getSuccesor(this.right);
    			if(succesor.right == null) {// succesor is a leaf. 
    				succesor.EraseNode(1);
    			}
    			else {
    				succesor.EraseNode(2);
    			}
    			
    	}
    	
    }
    
    private Node getSuccesor(Node node) {
    	 if(node.left == null) {
    		 return node; 
    	 }
    	 else {
    		 return getSuccesor(node.left); 
    	 }
    	
    }
    
    private void updateParent(Node node) {
    	Node parent = this.parent;
    	if(parent.left == this) {
			parent.left = node;

		}
    	else {
    		parent.right = node; 
    	}
		this.parent = null; 
		parent.updateHeight();
    }	 
    
    //***********private methods ends**************
    
    // insert a new node and balance the tree if needed.
    //null root already been checked by the Tree 
	public void insert (Guest guestToInsert) {
		helpInsert(guestToInsert); 
		
	}
	
	//return the node with value equals to value and if exist with the same last Name as lastName if no such Node return NUll 
	public Guest findGuest(int value, String lastName) {
		Guest guest =null;  
		//empty node shouldn't exist but for safety reason this case exists. 
		if(this.guests.isEmpty()) {
			return null; 
		}
		//we found a Guest that represents the wanted guests number in value 
		else if (this.GetNodeInvitedGuest() == value) {
			//if we have a guest with the required lastName he will be returned otherwise a random guest will be returned. 
			return this.returnValue(guest,lastName); 
		}
		
		//if we are smaller than value we need to search the right subtree.  
		else if (this.GetNodeInvitedGuest() < value) {
			if(this.right == null) {
				return null; 
			}
			else {
				return this.right.findGuest(value, lastName);
			}
			
		}
		//if we are bigger than value we need to search the left subtree.  
		else {
			if(this.left == null) {
				return null;
			}
			else {
				return this.left.findGuest(value, lastName); 
			}
		}
		
	}
	
	//return the node with the closest value to value param- by default if two nodes has the same difference from value will choose the smaller value.  
	public Guest findClosestGuest(int value, String lastName) {
		Guest guest =null;
		if(this.guests.isEmpty()) {
			return null; 
		}
		//we found a Guest that represents the wanted guests number in value 
		if (this.GetNodeInvitedGuest() == value) {
			//if we have a guest with the required lastName he will be returned otherwise a random guest will be returned. 
			return returnValue(guest, lastName); 
		}
		
		int guestsAmount = this.GetNodeInvitedGuest(); 
		//calculating my distance from value in absolute value
		int myDistance = Math.abs(value-guestsAmount); 
		//if we are smaller than value we need to search the right subtree.  
		if (this.GetNodeInvitedGuest() < value) {
			//if we have no one that is bigger than me and I'm smaller than value-> I'm the closest to value
			if(this.right == null) {
				return returnValue(guest, lastName); 
			}
			else {
				//comparing the Distance from value between me and right subtree best node to decide which to return.
				Guest rightGuest = this.right.findClosestGuest(value, lastName); 
				int rightGuestNumber = rightGuest.howMany(); 
				int rightDistance  = Math.abs(value-rightGuestNumber);
				if(rightDistance < myDistance) {
					//rightGuest already went through lastName searching process 
					return rightGuest; 
				}
				else {
					//return me. 
					return returnValue(guest, lastName); 
					
				}
				
			}
			
		}
		//if we are bigger than value we need to search the left subtree.  
		else {
			//if we have no one that is smaller than me and I'm bigger than value-> I'm the closest to value
			if(this.left == null) {
				return returnValue(guest, lastName);
			}
			else {
				//comparing the Distance from value between me and left subtree best node to decide which to return.
				Guest leftGuest = this.left.findClosestGuest(value, lastName); 
				int leftGuestNumber = leftGuest.howMany(); 
				int leftDistance  = Math.abs(value-leftGuestNumber);
				if(leftDistance < myDistance) {
					//leftGuest already went through lastName searching process in recurse 
					return leftGuest; 
				}
				else {
					//return me. 
					return returnValue(guest, lastName); 
					
				}
				
			}
		} 
	}
	
	//remove node from the tree if node exist in the tree. 
	public boolean removeNode(Node nodeToRemove) {
		if(this == nodeToRemove) {
			if(this.left == null) {
				if(this.right == null) { 
					 // deleting a leaf. 
					this.updateParent(null); //updating the parent to point ot null.
					return true; 
				}
				//one right child. 
				updateParent(this.right); 
				return true; 
			}
			//left is not null
			else {
				if(this.right == null) {
					//one left child
					updateParent(this.left); 
				}
				else {
					//two children 
					Node succesor = this.getSuccesor(this.right);
					removeNode(succesor); 
					succesor.left = this.left; 
					succesor.right = this.right; 
					updateParent(succesor); 
				}
				return true; 
				
			}
		}
		//we need to search left subtree
		else if(this.GetNodeInvitedGuest() > nodeToRemove.GetNodeInvitedGuest()) {
			if(this.left == null) {
				return false; 
			}
			boolean checkHeight = removeNode(this.left); 
			if(checkHeight) {
				
			}
		}
	}
	
	public void removeRoot() {
		
	}
 
}
