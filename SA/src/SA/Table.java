package SA;

import java.util.Iterator;
import java.util.LinkedList;

public class Table {

	private LinkedList<Chair> chairs = new LinkedList<Chair>();
	int size;
	private boolean full; 
	int remainingSeats; 
	
	
	public Table(int size) {
		this.size = size; 
		this.full = false; 
		for(int i=0; i<size; i++) {
			chairs.add(new Chair()); 
		}
		this.remainingSeats = size; 
	}
	
	public Table() {
		this.size =0; 
		this.full =false; 
	}
	
	public int getSize() {
		return this.size; 
	}
	
	public boolean isFull() {
		return this.full;
	}
	
	public int getRemainingSeats() {
		return this.remainingSeats;
	}
	
	public boolean addGuest(Guest guest) {
		if(guest.howMany() > getRemainingSeats()) {
			return false; 
		}
		else {
			Iterator<Chair> itr = chairs.iterator(); 
			int numberOfSeats = guest.howMany(); 
			while(itr.hasNext() && numberOfSeats>0) {
				Chair chair = itr.next();
				if(!chair.isOccupied()) {
					chair.takeASeat(guest);
					numberOfSeats--; 
					this.remainingSeats++;
				}
			}
			return true; 
		}
	}
	//return null if the guest does not exist
	public boolean removeGuest(Guest guest) {
		Iterator<Chair> itr = chairs.iterator(); 
		boolean output = false; 
		while(itr.hasNext()) {
			Chair chair = itr.next(); 
			if(chair.getGuest().equals(guest)) {
				chair.removeGuest();
				output = true; 
				this.remainingSeats--;
			}
		}
		return output; 
	}

}
