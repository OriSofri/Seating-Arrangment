package SA;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.Iterator;

public class EventHall {
	
	//maps size of table (number of seats), to a list of tables with that capacity; 
	private HashMap<Integer, LinkedList<Table>> tables;
	//map by group to the guest list of that group;  
	private HashMap<String, GuestList> groomGuests;
	private HashMap<String, GuestList> brideGuests;

	public EventHall() {
		this.groomGuests = new  HashMap<String, GuestList>(); 
		this.brideGuests = new  HashMap<String, GuestList>(); 
		this.tables = new  HashMap<Integer,LinkedList<Table>>();
	}
	
	
	public HashMap<String, GuestList> getGroomGuests(){
		return this.groomGuests;
	}
	
	public HashMap<String, GuestList> getBrideGuests(){
		return this.brideGuests;
	}
	
	public HashMap<Integer, LinkedList<Table>> getTables(){
		return this.tables;
	}
	
	public void addGuest(Guest guest) {
		if(guest.whichSide().equalsIgnoreCase("groom")) {
			addGroomGuest(guest); 
		}
		else {
			addBrideGuest(guest); 
		}
	}
	
	public void addGroomGuest(Guest guest) {
		if(this.groomGuests.containsKey(guest.getGroup())) {
			this.groomGuests.get(guest.getGroup()).addGuest(guest); 
		}
		else {
			GuestList newList = new GuestList(guest); 
			this.groomGuests.put(guest.getGroup(), newList); 
		}
	}
	
	public void addBrideGuest(Guest guest) {
		if(this.brideGuests.containsKey(guest.getGroup())) {
			this.brideGuests.get(guest.getGroup()).addGuest(guest); 
		}
		else {
			GuestList newList = new GuestList(guest); 
			this.brideGuests.put(guest.getGroup(), newList); 
		}
	}
	
	public void addTable(Table table) {
		if(this.tables.containsKey(table.getSize())) {
			this.tables.get(table.getSize()).add(table); 
		}
		else {
			LinkedList<Table> newList = new LinkedList<Table>();
			newList.add(table);
			this.tables.put(table.getSize(), newList); 
		}
	}
	
	public boolean removeGuest(Guest guest) {
		if(guest.whichSide().equalsIgnoreCase("groom")) {
			return removeGroomGuest(guest); 
		}
		else {
			return removeBrideGuest(guest); 
		}
	}
	
	public boolean removeGroomGuest(Guest guest) {
		if(this.groomGuests.containsKey(guest.getGroup())) {
			return this.groomGuests.get(guest.getGroup()).removeGuest(guest); 
		}
		else {
			return false; 
		}
	}
	
	
	public boolean removeBrideGuest(Guest guest) {
		if(this.brideGuests.containsKey(guest.getGroup())) {
			return this.brideGuests.get(guest.getGroup()).removeGuest(guest); 
		}
		else {
			return false; 
		}
	}
	
	public boolean removeTable(Table table) {
		if(this.tables.containsKey(table.getSize())) {
			return(this.tables.get(table.getSize())).remove(table);
			}
		else {
			return false;
		}
	}

}
