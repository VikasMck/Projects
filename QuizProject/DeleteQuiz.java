/* Class which holds the GUI for the delete panel
 * 
 */
package QuizApp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

public class DeleteQuiz extends JPanel{

	private static final long serialVersionUID = 1L;
	
	//GUI
	JLabel lbl_chooseToDel;
	
	JTextArea txt_deleteChoices;
	
	//Arrays
	String[] savesLinesArray;

	String[] autoSavesLinesArray;
	
	public DeleteQuiz() {
				
		//Same as previous classes
		ArrayList<String> savesLines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("SavesQuiz"))) {
            String line;
            while ((line = br.readLine()) != null) {
            	savesLines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        savesLinesArray = savesLines.toArray(new String[0]);
        
        ArrayList<String> autoSavesLines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("AutoSavesQuiz"))) {
            String line;
            while ((line = br.readLine()) != null) {
            	autoSavesLines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        autoSavesLinesArray = autoSavesLines.toArray(new String[0]);
		
        //GUI
		this.setLayout(null);
		
		lbl_chooseToDel = new JLabel("Pick a Quiz That You Wish to Delete");
		lbl_chooseToDel.setBounds(131, 131, 225, 16);
		this.add(lbl_chooseToDel);
		
		txt_deleteChoices = new JTextArea();
		txt_deleteChoices.setEnabled(false);
		txt_deleteChoices.setBounds(131, 159, 232, 405);
		this.add(txt_deleteChoices);
		
		//Show output
		for (String i : savesLines) {
			txt_deleteChoices.append(i + "\n");
		}
		
		for (String i : autoSavesLines) {
			txt_deleteChoices.append(i + "\n");
		}
		
		
	}
	


}