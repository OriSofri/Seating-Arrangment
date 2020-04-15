package SA;

public class Chair {
	
	private Guest guest; 
	private boolean occupied; 

	public Chair() {
		this.guest = null; 
		this.occupied= false; 
	}
	
	//Getters 
	public Guest getGuest() {
		return this.guest;
	}
	
	
	public boolean isOccupied() {
		return this.occupied; 
	}
	//Getters ends
	
	//return false if the seat is taken and take the seat otherwise. 
	public boolean takeASeat(Guest guest) {
		if(isOccupied()) {
			return false;
		}
		else {
			this.guest = guest; 
			this.occupied = true; 
			return true; 
		}
	}
	
	//return the guest who is seated if exist and null otherwise
	public Guest removeGuest() {
		if(isOccupied()) {
			this.occupied = false; 
			this.guest = null; 
			return this.guest; 
		}
		else {
			return null;
		}
	}

}
