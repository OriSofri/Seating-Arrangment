package SA;

import AVL_tree.AVLTree;

public class GuestList {
	
	private String group;
	private AVLTree guests; 
	private String side;
	
	
	public GuestList(Guest guest) {
		this.guests = new AVLTree(guest);
		this.group = guest.getGroup();
		this.side = guest.whichSide(); 
	}
	
	public GuestList() {
		this.guests = new AVLTree();
		this.group = ""; 
		this.side = ""; 
	}
	//Getters
	public AVLTree getGuests() {
		return this.guests; 
	}
	
	public String getGroup() {
		return this.group; 
	}
	
	public String whichSide() {
		return this.side; 
	}
	//Getters ends 
	//private methods
	public boolean checkGroup(Guest guest) {
		return (guest.getGroup().equals(this.getGroup()));
	}
	//private methods ends
	//return true if guest was inserted false otherwise. 
	public boolean addGuest(Guest guest) {
		if(checkGroup(guest)) {
			this.guests.insert(guest);
			return true; 
		}
		return false; 
	}
	
	public boolean removeGuest(Guest guestToRemove) {
		if(checkGroup(guestToRemove)) {
			this.guests.removeGuest(guestToRemove);
			return true; 
		}
		return false; 
	}
	
	//returns the guest if existed in this guestlist and null otherwise
	public Guest findGuest(int value, String lastName) {
		return this.guests.findGuest(value, lastName); 
	}
	//return the node with the closest value to value param- by default if two nodes has the same difference from value will choose the smaller value.  
	public Guest getClosestGuest(int value, String lastName) {
		return this.getClosestGuest(value, lastName); 
	}
	
	public int howManyGuests() {
		return this.guests.GetAllGuests(); 
	}

}
