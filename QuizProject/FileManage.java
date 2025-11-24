/* This class holds the main file handling functions
 * 
 */

package QuizApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileManage {

	
	//Creating a file
	public void createFile(String quizNameOutput) {
		try {
			File myFile = new File (quizNameOutput);
			
			if(myFile.createNewFile()) {
				System.out.println("The file " + quizNameOutput + " is created");
			}
			else{
				System.out.println("No file created");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Adding input to a file
	public void writeFile(String text, String file) throws IOException{
		
		try {
			FileWriter writer = new FileWriter(file, true);
			
			writer.write(text);
			
			writer.append("\n");
			
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	

	//Just reading a file
	public String readFile(String filename) {
				
		try {
			File myFile = new File ("TestQuiz");
				
			Scanner reader = new Scanner(myFile);
			
			
			while (reader.hasNextLine()) {
				System.out.println(reader.nextLine());
				System.out.println("\n");
				
			}
			reader.close();
						
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	

	
	//Wiping the file from existence
	public void deleteFile(String filename){
		try {
			File myFile = new File(filename);

			if (myFile.delete()) {
				System.out.println("File is gone");
			}
			else {
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
	
}


