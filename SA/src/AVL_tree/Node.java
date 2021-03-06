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
    
    //************private methods************
   
    
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
    

    private boolean helpInsert(Guest guestToInsert, AVLTree tree) {
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
				checkHeight  = this.left.helpInsert(guestToInsert, tree);
				if(checkHeight ) {
						//checking if balance is needed
						int leftCurrentHeight = this.getLeftHeight(); 
						int rightCurrentHeight = this.getRightHeight();
						//balance is needed.  
						if(Math.abs(leftCurrentHeight- rightCurrentHeight) >=2) {
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
								rotation(1, tree); 
								//fixed the balance no checking is needed
								return false; 
							}
							//case 3: perform left than right 
							else {
								rotation(3, tree); 
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
				checkHeight  = this.right.helpInsert(guestToInsert, tree);
				if(checkHeight ) {
						//checking if balance is needed
						int leftCurrentHeight = this.getLeftHeight(); 
						int rightCurrentHeight = this.getRightHeight();
						//balance is needed.  
						if(Math.abs(rightCurrentHeight- leftCurrentHeight) >=2) {
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
								rotation(2, tree); 
								//fixed the balance no checking is needed
								return false; 
							}
							//case 3: perform left than right 
							else {
								rotation(4, tree); 
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
    
    private void rotateRoot(int rotation, AVLTree tree) {
    	Node temp; 
    	switch(rotation) {
    	//right rotation 
    	case 1: 
    		temp = this.left.right; 
    		Node leftChild = this.left;
  			leftChild.parent = parent;
			//i become my left child right child. his originally right child is saved in temp.  
    		leftChild.right = this; 
    		//my parent become my left child. 
    		this.parent = leftChild;
    		//my left child become my left child right child. 
    		this.left = temp; 
    		if(temp != null) {
    			temp.parent =this;
    		}
    		updateHeight(); 
    		this.parent.updateHeight();
    		tree.setRoot(leftChild);
    		break;
    		
    	case 2:
    		temp = this.right.left;  
    		Node rightChild = this.right;
			rightChild.parent = parent;
			//i become my right child left child. his originally left child is saved in temp.  
    		rightChild.left = this; 
    		//my parent become my right child. 
    		this.parent = rightChild;
    		//my right child become my right child left child. 
    		this.right = temp;
    		if(temp != null) {
    			temp.parent =this;
    		}
    		updateHeight();  
    		this.parent.updateHeight();
    		tree.setRoot(rightChild);
    		break;
    		
    	case 3:
    	case 4:
    		doubleRotation(rotation, tree); 
    		break;
    	default: 
    		System.out.println("Wrong rotation case was inserted");
    		break;
    	}
    }
    
    
    
    //rotation: perform the wanted rotation- 1.right rotation 2. left rotation 3.left than right 4. right than left. 
    private void rotation(int rotation, AVLTree tree) {
    	Node parent = this.parent; 
    	Node temp; 
    	//if we are dealing with the root
    	if(parent == null) {
    		rotateRoot(rotation, tree); 
    	}
    	else {
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
        		if(temp != null) {
        			temp.parent =this;
        		} 
        		updateHeight(); 
        		this.parent.updateHeight();
        		break;
        		
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
        		if(temp != null) {
        			temp.parent =this;
        		} 
        		updateHeight();  
        		this.parent.updateHeight();
        		break;
        	case 3:
        	case 4:
        		doubleRotation(rotation, tree); 
        		break;
        	 
        	default: 
        		System.out.println("Wrong rotation case was inserted");
        		break; 
        	}
    	}
    	
    }
    //************************\\
    private void doubleRotation(int rotation, AVLTree tree) {
    	if(rotation == 3) {
    		this.left.rotation(2, tree);
    		rotation(1, tree);
    	}
    	else {
    		this.right.rotation(1, tree);
    		rotation(2, tree); 
    	}
		updateHeight(); 
		this.parent.updateHeight();
    	
    }
    
    

    private Node getsuccessor(Node node) {
    	 if(node.left == null) {
    		 return node; 
    	 }
    	 else {
    		 return getsuccessor(node.left); 
    	 }
    	
    }
    
    private void updateParent(Node node) {
    	Node parent = this.parent;
    	if(parent != null) {
    		if(parent.left == this) {
    			parent.left = node;

    		}
        	else {
        		parent.right = node; 
        	}
    		
    		parent.updateHeight();
    	}
    	this.parent = null;
    	if(node != null) {
    		node.parent =parent;
    	}
    }	 
    
    //***********private methods ends**************
    
    // insert a new node and balance the tree if needed.
    //null root already been checked by the Tree 
	public void insert (Guest guestToInsert, AVLTree tree) {
		helpInsert(guestToInsert, tree); 
		
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
	public boolean removeNode(Node nodeToRemove, AVLTree tree) {
		if(this == nodeToRemove) {
			if(this.left == null) {
				if(this.right == null) { 
					 // deleting a leaf. 
					this.updateParent(null); //updating the parent to point to null.
				}
				else {
					//one right child. 
					updateParent(this.right); 
				} 
			}
			//left is not null
			else {
				if(this.right == null) {
					//one left child
					updateParent(this.left); 
				}
				else {
					//two children - switching this with it successor. 
					Node successor = this.getsuccessor(this.right);
					removeNode(successor, tree); 
					successor.left = this.left; 
					successor.right = this.right; 
					successor.updateHeight();
					updateParent(successor); 
				}
				
			}
			return true; 
		}
		//we need to search right subtree
		else if(this.GetNodeInvitedGuest() < nodeToRemove.GetNodeInvitedGuest()) {
			if(this.right == null) {
				return false; 
			}
			boolean checkHeight = this.right.removeNode(nodeToRemove, tree); 
			if(checkHeight) {
				int leftCurrentHeight = this.getLeftHeight(); 
				int rightCurrentHeight = this.getRightHeight();
				//balance is needed means we deleted a node in the right subtree. 
				if(Math.abs(leftCurrentHeight- rightCurrentHeight) >=2) {
					Node leftChild = this.left; 
					//now go through rotation cases- 
					//case 1: check if left child has left child that is higher or equal to left child right child than right rotation
					//case 3: check if left child has right child that is higher than left child left child than double rotation. 
					int leftLeftHeight = leftChild.getLeftHeight(); 
					int leftRightHeight = leftChild.getRightHeight();
					if(leftLeftHeight >= leftRightHeight) {
						//in this case we need to do a right rotation on this and his left child.
						rotation(1, tree);
					}
					else {
						rotation(3, tree); 
					} 
				}
				return true; 
			}
			return false; 
			
		}
		//we need to go through left subtree
		else {
			if(this.left == null) {
				return false; 
			}
			boolean checkHeight = this.left.removeNode(nodeToRemove, tree);
			if(checkHeight) {
				int leftCurrentHeight = this.getLeftHeight(); 
				int rightCurrentHeight = this.getRightHeight();
				//balance is needed means we deleted a node in the left subtree. 
				if(Math.abs(leftCurrentHeight- rightCurrentHeight) >=2) {
					Node rightChild = this.right; 
					//now go through rotation cases- 
					//case 1: check if left child has left child that is higher or equal to left child right child than right rotation
					//case 3: check if left child has right child that is higher than left child left child than double rotation. 
					int rightLeftHeight = rightChild.getLeftHeight(); 
					int rightRightHeight = rightChild.getRightHeight();
					if(rightRightHeight >= rightLeftHeight) {
						//in this case we need to do a left rotation on this and his right child.
						rotation(2, tree);
					}
					else {
						rotation(4, tree); 
					} 
				}
				return true; 
			}
			return false; 
		}
		
	}
	
	public void removeRoot(AVLTree tree) {
		if(this.left == null) {
			if(this.right == null) { 
				 // deleting a leaf. 
				tree.setRoot(null);
			}
			else {
				//one right child. 
				this.right.parent = null; 
				tree.setRoot(this.right); 
			} 
		}
		//left is not null
		else {
			if(this.right == null) {
				//one left child
				this.left.parent = null; 
				tree.setRoot(left);
			}
			else {
				//two children - switching this with it successor. 
				Node successor = this.getsuccessor(this.right);
				removeNode(successor, tree); 
				successor.left = this.left; 
				successor.right = this.right; 
				successor.updateHeight();
				successor.parent = null; 
				tree.setRoot(successor);
			}
			
		}
	}
 
}
