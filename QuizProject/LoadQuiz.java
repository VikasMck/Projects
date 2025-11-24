/* Simple load instance which mainly holds the GUI stuff
 * 
 */

package QuizApp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

import javax.swing.text.Highlighter;

public class LoadQuiz extends JPanel{

	private static final long serialVersionUID = 1L;
	
	//GUI instances
	JTextArea txt_yourquizzes, txt_autoquizzes;
	
	JLabel lbl_yourquizes, lnl_autoquizzes, lbl_pickquiz;
	
	//Thing that highlights text
	Highlighter highlighter;
	
	//Arrays
	String[] savesLinesArray;

	String[] autoSavesLinesArray;
	
	public LoadQuiz() {
				
		//Copied code from previous instance which just divides the file into array values from the "database" files
		ArrayList<String> savesLines = new ArrayList<>();
        try (BufferedReader reader1 = new BufferedReader(new FileReader("SavesQuiz"))) {
            String line;
            while ((line = reader1.readLine()) != null) {
            	savesLines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        savesLinesArray = savesLines.toArray(new String[0]);
        
        ArrayList<String> autoSavesLines = new ArrayList<>();
        try (BufferedReader reader2 = new BufferedReader(new FileReader("AutoSavesQuiz"))) {
            String line;
            while ((line = reader2.readLine()) != null) {
            	autoSavesLines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        autoSavesLinesArray = autoSavesLines.toArray(new String[0]);
		
		//GUI stuff
		this.setLayout(null);
		
		txt_yourquizzes = new JTextArea();
		txt_yourquizzes.setEnabled(false);
		txt_yourquizzes.setBounds(69, 117, 145, 439);
		
		this.add(txt_yourquizzes);
		
		JLabel lblYourQuizes = new JLabel("Your created quizzes");
		lblYourQuizes.setBounds(69, 88, 145, 16);
		this.add(lblYourQuizes);
		
		txt_autoquizzes = new JTextArea();
		txt_autoquizzes.setEnabled(false);
		txt_autoquizzes.setBounds(286, 117, 145, 439);
		this.add(txt_autoquizzes);
		
		lnl_autoquizzes = new JLabel("Your auto-generated quizzes");
		lnl_autoquizzes.setBounds(286, 88, 195, 16);
		this.add(lnl_autoquizzes);
		
		
		lbl_pickquiz = new JLabel("Pick a quiz! (Case sensitive)");
		lbl_pickquiz.setToolTipText("Input the name of the quiz that you want to practice!");
		lbl_pickquiz.setBounds(579, 282, 183, 16);
		this.add(lbl_pickquiz);
		
		//Adding array values to the TextArea
		for (String i : savesLines) {
		    txt_yourquizzes.append(i + "\n");
		}
		
		for (String i : autoSavesLines) {
			txt_autoquizzes.append(i + "\n");
		}

	}

}
