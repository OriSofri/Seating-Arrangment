package SA; 
import java.util.Scanner;
public class Main {

	public static void main(String[] args) {
		String done = "done"; 
		System.out.println("please fill in the guest list found in <path to SA>/Seadting Arranger/resoureces and save changes."); 
		Scanner myScanner = new Scanner(System.in); 
		System.out.println("when guest list is ready type done:"); 
		while(true) {
			if(myScanner.nextLine().equalsIgnoreCase(done)) {
				break; 
			}
		}
	//	while(myScanner.nextLine().equalsIgnoreCase(yes));
		System.out.println("Great Success"); 
		myScanner.close();
	}

}
