/* Class which holds some of the algorithms
 * 
 * 
 */

package QuizApp;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;
import javax.swing.text.JTextComponent;

public class Algorithms {
	
	
	//Checking whether a word matches, as this is uncovered material, some assistance was gathered from various forums, not a particular one
	public static void checkWordComparison(String name1, String name2, JTextComponent textComponent) {
		name1 = name1.toLowerCase();
		name2 = name2.toLowerCase();
		
	    String[] str1words = name1.split(" ");
	    String[] str2words = name2.split(" ");
	    
	    Set<String> set = new HashSet<>(Arrays.asList(str1words)); //Copied code
	    
	    Highlighter highlighter = textComponent.getHighlighter();
	    for (int i = 0; i < str2words.length; i++) {
	        if (!set.add(str2words[i])) {
	            HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.green);
	            int startIndex = textComponent.getText().toLowerCase().indexOf(str2words[i]); 
	            int endIndex = startIndex + str2words[i].length();
	            try {
	                highlighter.addHighlight(startIndex, endIndex, painter);
	            } catch (BadLocationException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	}
	
	//Counting the amount of lines the file has
	public int lineCount(String filename) {
        int lineCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            while (reader.readLine() != null) {
                lineCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lineCount;
		}
	
	
	//Checking whether a line already exists in a file
	public boolean fileHasLine(String filename, String line) {
        try (BufferedReader reader1 = new BufferedReader(new FileReader(filename))) {
            String currentLine;
            while ((currentLine = reader1.readLine()) != null) {
                if (currentLine.equals(line)) {
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
	

	//Delete line from inside a file, some inspiration from various forums
	public void deleteMatchingLine(String filename, String matchline) throws IOException {
	    File file = new File(filename);
	    BufferedReader reader = new BufferedReader(new FileReader(file));
	    StringBuffer stringBuffer = new StringBuffer();
	    String line;
	    boolean matchFound = false;
	    
	    while ((line = reader.readLine()) != null) {
	        if (line.matches(matchline) && !matchFound) {
	            matchFound = true;
	            continue;
	        }
	        stringBuffer.append(line);
	        stringBuffer.append(System.lineSeparator());
	    }
	    reader.close();
	    if (matchFound) {
	        FileWriter fileWriter = new FileWriter(file);
	        fileWriter.write(stringBuffer.toString());
	        fileWriter.close();
	    }
	}

}
