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

	public static final String GUESTS_FILE = "C:\\Users\\osofri\\Desktop\\git projects\\java_project\\SA\\resources\\GuestList.xlsx"; 
	public static final String TABLES_FILE = "C:\\Users\\osofri\\Desktop\\git projects\\java_project\\SA\\resources\\EventList.xlsx"; 
	
	public static void main(String[] args) throws IOException, InvalidFormatException {
		getLists(); 
		init(); 
        System.out.println("finished the code"); 
	}
	
	//waiting to get guest list from user. 
	public static void getLists() {
		String done = "done"; 
		System.out.println("please fill in the guest list."); 
		Scanner myScanner = new Scanner(System.in); 
		System.out.println("when guest list is ready type \"done\": "); 
		boolean hasGuestList= false; 
		while(true) {
			if(myScanner.nextLine().equalsIgnoreCase(done)) {
				if(hasGuestList) {
					break;
				} 
				else {
					hasGuestList = true; 
					System.out.println("please fill in the event list");
					System.out.println("when guest list is ready type \"done\": "); 
				}
			}
		}
	//	while(myScanner.nextLine().equalsIgnoreCase(yes));
		System.out.println("Guest list is done starts in seating arrangement"); 
		myScanner.close();
	}
	
	
	public static void init() throws EncryptedDocumentException, InvalidFormatException, IOException {
        // Creating a Workbook from an Excel file
        Workbook workbook = WorkbookFactory.create(new File(GUESTS_FILE));
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
        		try {
            		String name = dataFormatter.formatCellValue(itr.next()); 
            		String howMany = (dataFormatter.formatCellValue(itr.next()));
            		String side = dataFormatter.formatCellValue(itr.next()); 
            		String group; 
            		//group can be empty
            		if(!itr.hasNext()) {
            			group = "Random"; 
            		}
            		else {
            			group = dataFormatter.formatCellValue(itr.next()); 
            		}
            		//splitting name to first and last name.
            		String[] splitName = splitName(name, row.getRowNum()); 
            		String firstName = splitName[0]; 
            		String lastName = getLastName(splitName); 
            		ValidGuest(splitName, howMany, side, row.getRowNum()); 
        		}catch(NoSuchElementException e) {
        			throw new NoSuchElementException("missing values in Guest list file in row: " + row.getRowNum()); 
        		}

        	}
        }
	}
        
    private static void ValidGuest(String[] names,  String howMany, String side, int rowNum) {
    	 for(int i =0 ; i< names.length; i++) {
    		 if(!isStringOnlyAlphabet(names[i])) {
    			 throw new RuntimeException("please use only chars to write name in row: " + rowNum); 
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
    		throw new RuntimeException("not a numeric value in number of guest cell in row" + rowNum) ; 
    	}
		 
    }
    
    public static String[] splitName(String name, int rowNum) {
		try {
			String[] splitName = name.split("\\s+");
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
    		output = (output.concat(splitName[i])).concat(" ");
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
