package SA; 
import java.util.Scanner;
import java.util.regex.PatternSyntaxException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class Main {

	static String GUESTS_FILE;
	
	public static void main(String[] args) throws IOException, InvalidFormatException {
		getLists(); 
		init(); 
        System.out.println("finished the code"); 
	}
	
	//waiting to get guest list from user. 
	public static void getLists() {
		Scanner myScanner = new Scanner(System.in); 
		System.out.println("please fill in the guest list in the following format: \n"
				+ "1. Create a xlsx (excel) file with a sheet named : \"GuestList\"\n"
				+ "2. Create a second sheet called: \\\"EventList\\\" \n"
				+ "3. In the GustList sheet make your guest list with the following columns (in that order):\n"
				+ "\t a. Full Name \n"
				+ "\t b. Number of Guests \n"
				+ "\t c. Side Groom/Bride \n"
				+ "\t d. Group \n"
				+ "4. Fill in the table with your guests, all colunms are mendatory except Group column \n"
				+ "4. In the EventList sheet make your table list with the following columns (in that order): \n"
				+ "\t a. Table capacity \n"
				+ "\t b. Number of tables \n"
				+ "5. when you done please inset the file full path, for example: \n"
				+ "\t  C:\\Users\\osofri\\Desktop\\GuestList.xlsx \n"
				+ "6. Insert file path: ");  
		GUESTS_FILE = myScanner.nextLine();
	//	while(myScanner.nextLine().equalsIgnoreCase(yes));
		System.out.println("Guest list is done starts in seating arrangement"); 
		myScanner.close();
	}
	
	
	public static void init() throws EncryptedDocumentException, InvalidFormatException, IOException {
		EventHall eventHall = new EventHall(); 
        // Creating a Workbook from an Excel file
        Workbook workbook = WorkbookFactory.create(new File(GUESTS_FILE));
		initGuests(eventHall, workbook); 
		initTables(eventHall, workbook); 
	}
	
	
	
	public static void initGuests(EventHall eventHall, Workbook workbook) throws EncryptedDocumentException, InvalidFormatException, IOException{
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        Sheet sheet = null;
        while (sheetIterator.hasNext()) {
            sheet = sheetIterator.next();
            if(sheet.getSheetName().equals("GuestList")) {
            	break;
            }
        }
        // Create a DataFormatter to format and get each cell's value as String
        DataFormatter dataFormatter = new DataFormatter();
        //assuming excel file structure wasn't change. 
        for(Row row: sheet) {
        	if(row.getRowNum() != 0 && !isRowEmpty(row)) {
        		Iterator<Cell> itr = row.iterator();
        		//if one of the must values is missing we will throw an exception 
        		String name;
        		String howMany;
        		String side;
        		String group; 
        		String firstName;
        		String lastName; 
        		try {
            		name = dataFormatter.formatCellValue(itr.next()); 
            		howMany = (dataFormatter.formatCellValue(itr.next()));
            		side = dataFormatter.formatCellValue(itr.next()); 
            		//group can be empty
            		if(!itr.hasNext()) {
            			group = "Random"; 
            		}
            		else {
            			group = dataFormatter.formatCellValue(itr.next()); 
            		}
            		//splitting name to first and last name.
            		String[] splitName = splitName(name, row.getRowNum()); 
            		firstName = splitName[0]; 
            		lastName = getLastName(splitName); 
            		ValidGuest(splitName, howMany, side, row.getRowNum()); 
        		}catch(NoSuchElementException e) {
        			throw new NoSuchElementException("missing values in Guestlist sheet in row: " + row.getRowNum()); 
        		}
        		//we can safely parse howMany to int because it was already checked. 
        		int howManyAsInteger = Integer.parseInt(howMany); 
        		Guest guest = new Guest(firstName, lastName,  side, group, howManyAsInteger);  
        		System.out.println(guest.toString()); 
        		eventHall.addGuest(guest);

        	}
        }
	}
	
	public static void initTables(EventHall eventHall, Workbook workbook) {
	       Iterator<Sheet> sheetIterator = workbook.sheetIterator();
	        Sheet sheet = null;
	        while (sheetIterator.hasNext()) {
	            sheet = sheetIterator.next();
	            if(sheet.getSheetName().equals("EventList")) {
	            	break;
	            }
	        }
	        // Create a DataFormatter to format and get each cell's value as String
	        DataFormatter dataFormatter = new DataFormatter();
	        //assuming excel file structure wasn't change. 
	        for(Row row: sheet) {
	        	if(row.getRowNum() != 0 && !isRowEmpty(row)) {
	        		Iterator<Cell> itr = row.iterator();
	        		//if one of the must values is missing we will throw an exception
	    	        String tableCapacity; 
	    	        String numOfTables; 
	    	        try {
	    	        	tableCapacity = dataFormatter.formatCellValue(itr.next()); 
	    	        	numOfTables = (dataFormatter.formatCellValue(itr.next()));
	    	        	if(isNumericValue(tableCapacity, row.getRowNum()) &&  isNumericValue(numOfTables, row.getRowNum())){
	    	        		int tableCapacityAsInteger = Integer.parseInt(tableCapacity); 
	    	        		int numOfTablesAsInteger = Integer.parseInt(numOfTables); 
	    	        	}
	    	        }
	    	        catch(NoSuchElementException e) {
	        			throw new NoSuchElementException("missing values in Eventlist sheet in row: " + row.getRowNum()); 
	        		}
	        	}
	        }

	}
        
    private static void ValidGuest(String[] names,  String howMany, String side, int rowNum) {
    	 for(int i =0 ; i< names.length; i++) {
    		 if(!isStringOnlyAlphabet(names[i])) {
    			 throw new RuntimeException("please use only Alphabet chars to write name in row: " + rowNum); 
    		 }
    	 }
    	//checking number of guest is a a number 
    	isNumericValue(howMany, rowNum);
    	checkSide(side, rowNum); 
 	 
    }
    
    public static void checkSide(String str, int rowNum) {
    	if((str.equals("")) 
                || (str == null)
                	|| !(str.equalsIgnoreCase("groom") || str.equalsIgnoreCase("bride") )) {
    		throw new RuntimeException("please assign side to guest in row: " + rowNum); 
    	}
    }
    
    public static boolean isStringOnlyAlphabet(String str) 
    { 
        return ((!str.equals("")) 
                && (str != null) 
                && (str.matches("^[a-zA-Z]*$"))); 
    } 
    
    public static boolean isNumericValue(String value, int rowNum) {
		try {
    		Integer.parseInt(value);
    		return true;
    	}catch(NumberFormatException e) {
    		throw new RuntimeException("not a numeric value in" + rowNum) ; 
    	}
		 
    }
    
    public static String[] splitName(String name, int rowNum) {
		try {
			String[] splitName = name.trim().split("\\s+");
			if(splitName.length < 2) {
				throw new RuntimeException("please write full name in row: " + rowNum); 
    		}
			return splitName; 
		}
		catch(PatternSyntaxException e) {
			throw new RuntimeException("please write full name in row: " + rowNum); 
		}
    }
	
    public static String getLastName(String[] splitName){
    	String output =""; 
    	for(int i=1; i<splitName.length; i++) {
    		if(i == (splitName.length -1)) {
    			output = output.concat(splitName[i]);
    		}
    		else {
    			output = (output.concat(splitName[i])).concat(" ");
    		}
    		
    	}
    	return output; 
    }
    
    
	@SuppressWarnings("deprecation")
	public static boolean isRowEmpty(Row row){
        int firstCol = row.getFirstCellNum();
        for(int cnt = 0; cnt<4 ; cnt++){
            Cell cell = row.getCell(firstCol+cnt);
            if(cell!=null && cell.getCellType() != Cell.CELL_TYPE_BLANK){
                return false;
            }
        }
        return true;
   }

}
