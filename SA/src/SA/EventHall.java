package SA;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.Iterator;

public class EventHall {
	
	//maps size of table (number of seats), to a list of tables with that capacity; 
	private HashMap<Integer, LinkedList<Table>> tables;
	//map by group to the guest list of that group;  
	private HashMap<String, GuestList> guests;

	public EventHall() {
		this.guests = new  HashMap<String, GuestList>(); 
		this.tables = new  HashMap<Integer,LinkedList<Table>>();
	}
	
	public EventHall(Table table, Guest guest) {
		this.guests = new  HashMap<String, GuestList>(); 
		this.tables = new  HashMap<Integer,LinkedList<Table>>();
		LinkedList<Table> tableList = new LinkedList<Table>();
		tableList.add(table); 
		GuestList guestList = new GuestList(guest); 
		this.guests.put(guestList.getGroup(), guestList); 
		this.tables.put(table.getSize(), tableList); 
	}
	
	public HashMap<String, GuestList> getGuests(){
		return this.guests;
	}
	
	public HashMap<Integer, LinkedList<Table>> getTables(){
		return this.tables;
	}
	
	public void addGuest(Guest guest) {
		if(this.guests.containsKey(guest.getGroup())) {
			this.guests.get(guest.getGroup()).addGuest(guest); 
		}
		else {
			GuestList newList = new GuestList(guest); 
			this.guests.put(guest.getGroup(), newList); 
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
		if(this.guests.containsKey(guest.getGroup())) {
			return this.guests.get(guest.getGroup()).removeGuest(guest); 
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
