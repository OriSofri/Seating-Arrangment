
public class Guest {

	private String first_name;
	private String last_name; 
	//0 for groom and 1 for bride. 
	private String side; 
	private char gender; 
	private String group; 
	private int how_many; 
	
	public Guest(String first_name, String last_name, String side, char gender, String group, int how_many) {
		this.first_name= first_name; 
		this.last_name = last_name; 
		this.side = side; 
		this.gender = gender; 
		this.group = group; 
		this.how_many = how_many; 

	}
	
	//getters 
	private String whichSide() {
		return this.side; 
	}
	
	private String getFirstName() {
		return this.first_name;
	}
	
	private String getLastName() {
		return this.last_name; 
	}
	
	private String getFullName() {
		String full_name;
		full_name = this.first_name + " " + this.last_name; 
		return full_name; 
	}
	
	private String getGroup() {
		return this.group;
	}
	
	private int howMany() {
		return this.how_many; 
	}
	//end with getters; 

}
