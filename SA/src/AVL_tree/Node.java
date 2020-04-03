package AVL_tree;
import java.util.Iterator;
import java.util.LinkedList;

import SA.Guest;

class Node {
    private LinkedList<Guest> guests = new LinkedList<Guest>();
    private Node left;
    private Node right;
    private int hight; 
    
 
    public Node(Guest guest) {
        this.guests.add(guest);
        right = null;
        left = null;
        hight = 0; 
    }
        
    //***********Getters starts************* 
    private Guest getGuest() {
    	return this.guests.getFirst(); 
    }
    
    public LinkedList<Guest> getGuests() {
    	return this.guests; 
    }
    
    public int getHight() {
    	return this.hight; 
    }
    //************Getters ends****************
    
    //************private methods*************
    //returns number of guests each Guest in this node LinkedList represents 
    private int GetNodeInvitedGuest() {
    	return this.getGuest().howMany(); 
    }
    
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
    
    //added a boolean to know if the tree has a new leaf and balance check is needed. 
    private boolean helpInsert(Guest guestToInsert) {
    	int currentGuestNumber = this.GetNodeInvitedGuest(); 
		int numOfGuestsToInsert = guestToInsert.howMany();
		//need those to know if we got higher on not. 
		int leftOriginHight = this.left.getHight();
		int rightOriginSize = this.right.getHight();
		boolean checkHight = false; 
		//if we already have a node that represent this guest number we just added to the list of that node. 
		if(this.GetNodeInvitedGuest() == numOfGuestsToInsert) {
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
				//if this is this node first child than he made himself higher, and there for all of his ancestors  
				if(this.right == null) {
					this.hight++; 
					return true;
				}
				else {
					return false; 
				}
				    
			}
			//walking on the left subtree. 
			else {
				checkHight  = this.left.helpInsert(guestToInsert);
				if(checkHight ) {
					//if the left was equal or higher originally from the right subtree so the new node make me higher. 
					if(leftOriginHight >= rightOriginSize ) {
						this.hight++;
						//checking if balance is needed
						int leftCurrentHight = this.left.getHight(); 
						int rightCurrentSize = this.right.getHight();
						//balance is needed.  
						if(leftCurrentHight- rightCurrentSize >=2) {
							//now go through rotation cases- 
							//1. check if left child has left child that is higher than left child right child than right rotation
							//2. check if left child has right child that is higher than left child left child that double rotation. 
						}
						
					}
				}
			}
			
		}
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
	public boolean removeGuest (Guest guestToRemove) {
		// TODO Auto-generated constructor stub  
	}
    
    
}
