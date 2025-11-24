/* Main instance for showing the created quiz
 * 
 */

package QuizApp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

public class StartQuiz extends JPanel implements ActionListener, MouseListener{
	
	private static final long serialVersionUID = 1L;

	//GUI initialisations
	JLabel lbl_question, lbl_youranwer, lbl_correct;
	
	JTextArea txt_question, txt_answer, txt_realAnswer;
	
	JButton btn_revealanswer, btn_nextquestion, btn_previousquestion, btn_yes, btn_no;
	
	static JProgressBar progressBar;
	
	//Instances
	FileManage fileMng = new FileManage();
		
    Random random = new Random();

    Algorithms algo = new Algorithms();
    
    //Variables
    int questionCount = 1;

	String[] linesArray;
	
    int randomNumber = random.nextInt(100000) + 1;

    int progressCount = 0;

    boolean firstPress = false;
    
	int autocount = 1;
	
	boolean autoAlrCreated = false;
	
	public StartQuiz(String filename) {
		
		//Dividing each line of the file into array values
		ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        linesArray = lines.toArray(new String[0]);
		
        //GUI stuff
		this.setLayout(null);
		
		JLabel lblQuiz = new JLabel("Quiz: " + filename);
		lblQuiz.setBounds(263, 26, 136, 30);
		this.add(lblQuiz);
		
		lbl_question = new JLabel("Question:");
		lbl_question.setBounds(53, 94, 146, 16);
		this.add(lbl_question);
		
		lbl_youranwer = new JLabel("Your anwer:");
		lbl_youranwer.setBounds(508, 94, 117, 16);
		this.add(lbl_youranwer);
		
		lbl_correct = new JLabel("Did you get it correct");
		lbl_correct.setBounds(508, 419, 146, 30);
		this.add(lbl_correct);
		
		txt_question = new JTextArea();
		txt_question.setBounds(53, 135, 378, 179);
		this.add(txt_question);
		txt_question.setColumns(10);
		txt_question.setText(linesArray[0]);
		
		txt_answer = new JTextArea();
		txt_answer.setColumns(10);
		txt_answer.setBounds(508, 135, 378, 179);
		this.add(txt_answer);
//		txt_answer.setText(linesArray[1]);  //This is handy for testing
				
		
		btn_revealanswer = new JButton("Reveal answer");
		btn_revealanswer.setBounds(53, 339, 136, 29);
		btn_revealanswer.addActionListener(this);
		this.add(btn_revealanswer);
		
		btn_yes = new JButton("Yes");
		btn_yes.setBounds(462, 461, 117, 29);
		btn_yes.addActionListener(this);
		this.add(btn_yes);
		
		btn_no = new JButton("No");
		btn_no.setBounds(590, 461, 117, 29);
		btn_no.addActionListener(this);
		this.add(btn_no);
		
		
		progressBar = new JProgressBar();
		progressBar.setBounds(462, 538, 304, 30);
		this.add(progressBar);
		
		btn_nextquestion = new JButton("Next Question");
		btn_nextquestion.setBounds(784, 538, 146, 29);
		btn_nextquestion.addActionListener(this);
		this.add(btn_nextquestion);
		
		btn_previousquestion = new JButton("Previous Question");
		btn_previousquestion.setBounds(784, 568, 146, 29);
		btn_previousquestion.addActionListener(this);
		this.add(btn_previousquestion);
		
		
		txt_realAnswer = new JTextArea();
		txt_realAnswer.setColumns(10);
		txt_realAnswer.setBounds(53, 414, 378, 179);
		this.add(txt_realAnswer);
	
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		//When pressed it reveals the answer in a TextArea and highlights the words which matched
		if(e.getSource() == btn_revealanswer) {
			txt_realAnswer.setText(linesArray[questionCount]);
			
			Algorithms.checkWordComparison(txt_answer.getText(), txt_realAnswer.getText(), txt_answer);
			
		}
		
		//Goes to the next question
		if(e.getSource() == btn_nextquestion) {
			
			
			if(progressCount != algo.lineCount(MainMenu.quizNameOutput) / 2 - 1)
			{
				btn_previousquestion.setEnabled(true);

				//When navigating between next and previous it is important to know whether the button is pressed for the first time otherwise a double click is needed
				if(firstPress == true) {

					questionCount = questionCount + 2;
					questionCount++;
					txt_question.setText(linesArray[questionCount]);
					questionCount++;
//					txt_answer.setText(linesArray[questionCount]); //This is handy for testing
					txt_realAnswer.setText("");
					progressCount++;
					progressBar.setValue(progressCount);

					firstPress = false;
				
				}
				else {

					questionCount++;
					txt_question.setText(linesArray[questionCount]);
					questionCount++;
//					txt_answer.setText(linesArray[questionCount]);
					txt_realAnswer.setText("");
					progressCount++;
					progressBar.setValue(progressCount);


					firstPress = false;
				}
				

			}
			else {
				JOptionPane.showMessageDialog(this, "The quiz is finished");
				btn_nextquestion.setEnabled(false);

			}

		}
		
		//Same as next just values switch up as it is reading the file backwards
		if(e.getSource() == btn_previousquestion) {
		    if(questionCount > 0) {
				btn_nextquestion.setEnabled(true);

		    	if(firstPress == false) {
		    		
			    	
			    	questionCount = questionCount - 2;
					txt_answer.setText(linesArray[questionCount]);
					questionCount--;
					txt_question.setText(linesArray[questionCount]);
					questionCount--;
					txt_realAnswer.setText("");
					progressCount--;
					progressBar.setValue(progressCount);

					firstPress = true;
		    	}
		    	else {
		    		txt_answer.setText(linesArray[questionCount]);
					questionCount--;
					txt_question.setText(linesArray[questionCount]);
					questionCount--;
	
					txt_realAnswer.setText("");

					progressCount--;
					progressBar.setValue(progressCount);
					firstPress = true;

		    	}
		    	
				
		    	
		    } else {
		        JOptionPane.showMessageDialog(this, "This is the first question.");
				btn_previousquestion.setEnabled(false);

		    }
		}

		

		//If such button is pressed an auto file is created where the question is added
		if(e.getSource() == btn_no) {
			
			int temp = randomNumber;

		
			if(autoAlrCreated == false) {
				

				fileMng.createFile("Auto" + temp);
				autocount++;
				autoAlrCreated = true;
				try {
					fileMng.writeFile(("Auto" + temp), "AutoSavesQuiz");
					fileMng.writeFile(linesArray[questionCount - 1],"Auto" + temp);
					fileMng.writeFile(linesArray[questionCount], "Auto" + temp);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
			else {
				try {
					fileMng.writeFile(linesArray[questionCount - 1],"Auto" + temp);
					fileMng.writeFile(linesArray[questionCount], "Auto" + temp);

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
		if(e.getSource() == btn_yes) {
	        JOptionPane.showMessageDialog(this, "Good job!");

		}
		
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		txt_answer.setText("");
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		txt_answer.setText("");
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
