/* Main instance for the quiz application
 */

package QuizApp;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

public class MainMenu extends JFrame implements ActionListener, MouseListener{

	private static final long serialVersionUID = 1L;
	
	JFrame mainframe;
	
	//All panels
	JPanel pnl_mainmenu, pnl_createquiz, pnl_loadquiz, pnl_deletequiz, pnl_startquiz;

	
	//JButton for various activities
	JButton btn_gotocreate, btn_gotoload, btn_gotodelete, btn_exit, btn_startquizfromload, btn_submitToDel;

	//Static since it can be shared to other classes
	static JButton btn_startquizfromcreate;
	static JButton btn_quizname;
	static JTextArea txt_quizname;
	
	
	//Buttons for backtracking, 1 button cannot have same functionality without use of complex code, so there are for each panel
	JButton btn_gobackfromcreate, btn_gobackfromstart, btn_gobackfromload, btn_gobackfromloadstart, btn_gobackfromdelete;

	
	//Text fields
	JTextField txt_pickquiz, txt_deleteChoice;
	
	//Card layout which manages all the switching between panels
    CardLayout cardlayout = new CardLayout();

    
    
    
    
    //All the instances used
    FileManage fileMng = new FileManage();
    
	File savedQuiz = new File("SavesQuiz");
	
	File autoSavedQuiz = new File("AutoSavesQuiz");
	
	Algorithms algo = new Algorithms();

	//Variables
	static String quizNameOutput;
	
	public MainMenu() {
		
		
		//These files are to act as a database, therefore they are automatically created upon first run
		if(savedQuiz.exists() == false) {
			fileMng.createFile("SavesQuiz");
		}
		if(autoSavedQuiz.exists() == false) {
			fileMng.createFile("AutoSavesQuiz");
		}
		
		//Basic GUI managing
		mainframe = new JFrame();
		
		pnl_mainmenu = new JPanel();
		pnl_createquiz = new JPanel();
		pnl_loadquiz = new JPanel();
		pnl_startquiz = new JPanel();
		
		btn_gotocreate = new JButton("Create a Quiz");
		btn_gotocreate.addActionListener(this);
		
		btn_gotoload = new JButton("Load a Quiz");
		btn_gotoload.addActionListener(this);
		
		btn_gotodelete = new JButton("Delete a Quiz");
		btn_gotodelete.addActionListener(this);
		
		btn_gobackfromcreate = new JButton("Back");
		btn_gobackfromcreate.addActionListener(this);
		
		btn_gobackfromstart = new JButton("Back");
		btn_gobackfromstart.addActionListener(this);
		
		btn_gobackfromload = new JButton("Back");
		btn_gobackfromload.addActionListener(this);
		
		btn_gobackfromdelete = new JButton("Back");
		btn_gobackfromdelete.addActionListener(this);
		
		btn_gobackfromloadstart = new JButton("Back");
		btn_gobackfromloadstart.addActionListener(this);
		
		btn_exit = new JButton("Exit");
		btn_exit.addActionListener(this);
		
		btn_quizname = new JButton("Submit");
		
		btn_quizname.addActionListener(this);
		
		
		btn_startquizfromcreate = new JButton("Start Quiz");
		btn_startquizfromcreate.addActionListener(this);
		
		btn_startquizfromload = new JButton("Start Quiz");
		btn_startquizfromload.addActionListener(this);
		
		btn_submitToDel = new JButton("Delete");
		btn_submitToDel.addActionListener(this);
		
		txt_quizname = new JTextArea("Quiz name?");
		txt_quizname.setBounds(388, 39, 150, 24);
		txt_quizname.setPreferredSize(new Dimension(150, 24));
		txt_quizname.addMouseListener(this);

		txt_pickquiz = new JTextField();
		
		txt_deleteChoice = new JTextField();

		
		
		
		pnl_mainmenu.setLayout(new GridLayout(4, 1));
		pnl_mainmenu.add(btn_gotocreate);
		pnl_mainmenu.add(btn_gotoload);
		pnl_mainmenu.add(btn_gotodelete);
		pnl_mainmenu.add(btn_exit);
		
		
		
		
		//Card layout GUI creation
		Container contentPane = getContentPane();
        
		contentPane.setLayout(cardlayout);
        
        contentPane.add(pnl_mainmenu, "mainPanel");
        
        contentPane.add(pnl_createquiz, "newQuiz");
        
        contentPane.add(pnl_loadquiz, "loadQuiz");
        
        contentPane.add(pnl_startquiz, "startQuiz");

        cardlayout.show(contentPane, "mainPanel");
		

        
        mainframe.add(contentPane);
		
		
		mainframe.setSize(1000, 700);
		mainframe.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		//Close the app
		if(e.getSource() == btn_exit) {
			System.exit(0);
			
		}
		//When pressed, goes to the other panel by creating an instance of it
		if(e.getSource() == btn_gotocreate) {			
			CreateQuiz toCreateQuiz = new CreateQuiz();
									
			getContentPane().add(toCreateQuiz, "newQuiz");
			cardlayout.show(getContentPane(), "newQuiz");
			
			toCreateQuiz.add(btn_quizname);
			
			toCreateQuiz.add(btn_startquizfromcreate);
			
			toCreateQuiz.add(btn_gobackfromcreate);
			
			toCreateQuiz.add(txt_quizname);
			
			toCreateQuiz.setLayout(null);
			
			btn_quizname.setBounds(572, 38, 88, 29);
			
			btn_gobackfromcreate.setBounds(68, 38, 175, 29);
	        
			btn_startquizfromcreate.setBounds(375, 496, 175, 52);
			
			btn_startquizfromcreate.setEnabled(false);

			
		}
		//When pressed, goes to the other panel by creating an instance of it
		if(e.getSource() == btn_gotoload) {
			LoadQuiz toLoadQuiz = new LoadQuiz();
			
			getContentPane().add(toLoadQuiz, "loadQuiz");
			cardlayout.show(getContentPane(), "loadQuiz");
			
			toLoadQuiz.add(btn_startquizfromload);
			
			toLoadQuiz.add(btn_gobackfromload);
			
			toLoadQuiz.add(txt_pickquiz);
			
			toLoadQuiz.setLayout(null);
			
			btn_gobackfromload.setBounds(68, 38, 175, 29);
	        
			btn_startquizfromload.setBounds(579, 346, 183, 54);
			
			txt_pickquiz.setBounds(579, 306, 183, 26);
			txt_pickquiz.setColumns(10);


		}
		
		//When pressed, goes to the other panel by creating an instance of it
		if(e.getSource() == btn_gotodelete) {
			DeleteQuiz toDeleteQuiz = new DeleteQuiz();
			
			getContentPane().add(toDeleteQuiz, "deleteQuiz");
			cardlayout.show(getContentPane(), "deleteQuiz");
			
			toDeleteQuiz.add(btn_gobackfromdelete);
			
			btn_gobackfromdelete.setBounds(68, 38, 175, 29);

			toDeleteQuiz.add(btn_submitToDel);
			
			btn_submitToDel.setBounds(547, 284, 152, 45);
			
			toDeleteQuiz.add(txt_deleteChoice);
			
			txt_deleteChoice.setBounds(547, 246, 152, 26);
			
			
		}
		//This button deletes the file from the "database" files and the file itself
		if(e.getSource() == btn_submitToDel) {
			fileMng.deleteFile(txt_deleteChoice.getText());
			JOptionPane.showMessageDialog(pnl_deletequiz, "The quiz \"" + txt_deleteChoice.getText() +"\" is deleted");
			try {
				algo.deleteMatchingLine("SavesQuiz", txt_deleteChoice.getText());
				algo.deleteMatchingLine("AutoSavesQuiz", txt_deleteChoice.getText());

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			
		}
		
		//Main button which gets creates the quiz panel, when pressed it read the TextArea located in other panel and makes an instance using that name
		if(e.getSource() == btn_quizname) {			

			//Checking whether such file already exists to avoid cluttering
			if(algo.fileHasLine("SavesQuiz", txt_quizname.getText()) == true) {
				JOptionPane.showMessageDialog(pnl_createquiz, "The quiz \"" + txt_quizname.getText() + "\" already exists. You can continue addint questions");
				CreateQuiz.btn_finish.setEnabled(true);
				CreateQuiz.btn_addtofile.setEnabled(true);
				CreateQuiz.txt_quizanswer.setEnabled(true);
				CreateQuiz.txt_quizquestion.setEnabled(true);
				btn_quizname.setEnabled(false);
				quizNameOutput = txt_quizname.getText();

			}
			else {
				fileMng.createFile(txt_quizname.getText()); 
				
				try {
					fileMng.writeFile(txt_quizname.getText(), "SavesQuiz");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				quizNameOutput = txt_quizname.getText();
	
				//Some GUI modification which help for validation as button get disabled when they are not needed
				JOptionPane.showMessageDialog(pnl_createquiz, "The quiz \"" + txt_quizname.getText() + "\" is created");
				CreateQuiz.btn_finish.setEnabled(true);
				CreateQuiz.btn_addtofile.setEnabled(true);
				CreateQuiz.txt_quizanswer.setEnabled(true);
				CreateQuiz.txt_quizquestion.setEnabled(true);
				btn_quizname.setEnabled(false);
			}
			btn_startquizfromcreate.setEnabled(false);

		
		}
		
		//Using the startquiz from create panel
		if(e.getSource() == btn_startquizfromcreate ) {
		   
			StartQuiz toStartQuiz = new StartQuiz(txt_quizname.getText());
		   
			//since the file has a question and an answer, it is divided by 2 to get the total questions, - 1 since it begins with 0
			StartQuiz.progressBar.setMaximum(algo.lineCount(txt_quizname.getText()) / 2 - 1);
			
			getContentPane().add(toStartQuiz, "startQuiz");
			cardlayout.show(getContentPane(), "startQuiz");
			
			toStartQuiz.add(btn_gobackfromstart);
			
			btn_gobackfromstart.setBounds(68, 38, 175, 29);
			
			

			
		}
		
		//using the start quiz from load panel
		if(e.getSource() == btn_startquizfromload ) {
			   
			StartQuiz toStartQuiz = new StartQuiz(txt_pickquiz.getText());
		   
			StartQuiz.progressBar.setMaximum(algo.lineCount(txt_pickquiz.getText()) / 2 - 1);

			quizNameOutput = txt_pickquiz.getText();

			
			getContentPane().add(toStartQuiz, "startQuiz");
			cardlayout.show(getContentPane(), "startQuiz");
			
			toStartQuiz.add(btn_gobackfromloadstart);
			
			btn_gobackfromloadstart.setBounds(68, 38, 175, 29);

			
		}
		
		
		//Changing between other panels
		if(e.getSource() == btn_gobackfromcreate) {
			cardlayout.show(getContentPane(), "mainPanel");
		}
		
		if(e.getSource() == btn_gobackfromstart) {
			cardlayout.show(getContentPane(), "newQuiz");
		}
		if(e.getSource() == btn_gobackfromload) {
			cardlayout.show(getContentPane(), "mainPanel");
		}
		if(e.getSource() == btn_gobackfromdelete) {
			cardlayout.show(getContentPane(), "mainPanel");
		}
		if(e.getSource() == btn_gobackfromloadstart) {
			cardlayout.show(getContentPane(), "mainPanel");
		}
		
	}


	//Mouse events for convenience
	@Override
	public void mouseClicked(MouseEvent e) {

		txt_quizname.setText("");
		txt_deleteChoice.setText("");

	}

	@Override
	public void mousePressed(MouseEvent e) {

		txt_quizname.setText("");
		txt_deleteChoice.setText("");
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
