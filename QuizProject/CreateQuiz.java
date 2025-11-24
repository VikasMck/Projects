/* Create quiz instance which allows the user to make a quiz, input both question and an answer
 * 
 * 
 */

package QuizApp;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.*;

public class CreateQuiz extends JPanel implements ActionListener, MouseListener{

	private static final long serialVersionUID = 1L;

	//GUI initialisations 
	JPanel pnl_startquiz;
	
	JButton btn_quizname;

	static JButton btn_addtofile;

	static JButton btn_finish;
	
	JTextArea txt_quizname;

	static JTextArea txt_quizquestion;

	static JTextArea txt_quizanswer;
	
	JLabel lbl_inputquestion, lbl_newlabel;
	
	
	//Instances
    FileManage fileMng = new FileManage();
    
    
    //Variables
	int count = 1, questionLimit = 10;
	
	String quizNameOutput;
	
	public CreateQuiz(){
	
		
		//GUI stuff
		pnl_startquiz = new JPanel();

		
		btn_addtofile = new JButton("Submit question:" + count + "/" + questionLimit);
		btn_finish = new JButton("Finish");
		
		txt_quizquestion = new JTextArea("Question");
		txt_quizanswer = new JTextArea("Answer");
		
		lbl_inputquestion = new JLabel("Input a question");
		lbl_newlabel = new JLabel("Input an answer");
		
		
		lbl_inputquestion.setBounds(68, 107, 150, 16);
		this.add(lbl_inputquestion);
		
		lbl_newlabel.setBounds(555, 107, 150, 16);
		this.add(lbl_newlabel);
				
		
		txt_quizquestion.setEnabled(false);
		txt_quizquestion.setBounds(44, 124, 350, 152);
		txt_quizquestion.addMouseListener(this);
		
		txt_quizanswer.setEnabled(false);
		txt_quizanswer.setBounds(524, 124, 350, 152);
		txt_quizanswer.addMouseListener(this);
		
		btn_addtofile.setEnabled(false);
		btn_addtofile.setBounds(375, 288, 175, 29);
		btn_addtofile.addActionListener(this);
		
		txt_quizquestion.setPreferredSize(new Dimension(300, 100));
		txt_quizanswer.setPreferredSize(new Dimension(300, 100));
		

		
		btn_finish.setEnabled(false);
		btn_finish.setBounds(388, 361, 150, 29);
		btn_finish.addActionListener(this);
			
		this.add(btn_finish);
		
		this.add(txt_quizanswer);
		
		this.add(txt_quizquestion);
		
		this.add(btn_addtofile);
				
		this.setLayout(null);

		
		
		

	}

	public String getQuizNameOutput() {
		return quizNameOutput;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btn_addtofile) {
		    	        			
			//Addition inputs to a file
		    if (count < questionLimit) {
		        try {
		            fileMng.writeFile(txt_quizquestion.getText(), MainMenu.quizNameOutput);
		            fileMng.writeFile(txt_quizanswer.getText(), MainMenu.quizNameOutput);
		            txt_quizquestion.setText("");
		            txt_quizanswer.setText("");
		            count++;
		            btn_addtofile.setText("Submit question: " + count + "/" + questionLimit);
		        } catch (IOException e1) {
		            e1.printStackTrace();
		        }
		    }

		    if (count >= questionLimit) {
		        btn_addtofile.setEnabled(false);
		    }
		    
		    
		}
		
		//Button for when the user thinks the quiz is finished
		if(e.getSource() == btn_finish) {
			
			
			count = 1;
			
			MainMenu.btn_quizname.setEnabled(true);
			btn_addtofile.setEnabled(false);
			txt_quizquestion.setEnabled(false);
			txt_quizanswer.setEnabled(false);
			MainMenu.btn_startquizfromcreate.setEnabled(true);
			btn_finish.setEnabled(false);
			
            btn_addtofile.setText("Submit question: " + count + "/" + questionLimit);




			
		}
		
	}

	//Mouse listener for convenience
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == txt_quizquestion) {
			txt_quizquestion.setText("");
		}
		if(e.getSource() == txt_quizanswer) {
			txt_quizanswer.setText("");

		}
		if(e.getSource() == txt_quizname) {
			txt_quizname.setText("");		

		}		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
