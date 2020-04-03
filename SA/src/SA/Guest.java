package SA; 
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

	//getters starts; 
	public String whichSide() {
		return this.side; 
	}
	
	public String getFirstName() {
		return this.first_name;
	}
	
	public String getLastName() {
		return this.last_name; 
	}
	
	public String getFullName() {
		String full_name;
		full_name = this.first_name + " " + this.last_name; 
		return full_name; 
	}
	
	public String getGroup() {
		return this.group;
	}
	
	public int howMany() {
		return this.how_many; 
	}
	//getters ends; 

}
