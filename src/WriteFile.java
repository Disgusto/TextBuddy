/* This class is made to read and edit the file.
 */
import java.io.IOException;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;

import java.util.ArrayList;

public class WriteFile {
	private String path;
	private boolean append_to_file = false;

	// Constructors
	
	//This is the default constructor
	public WriteFile(String file_path) {
		path = file_path;
	}
	
	//This constructor can set the append_value
	public WriteFile(String file_path, boolean append_value) {
		path = file_path;
		append_to_file = append_value;
	}

	// To change the append value: True or False
	public void changeValue(boolean append_value) {
		append_to_file = append_value;
	}

	// Reads the file and put the contents into an ArrayList and returns it
	public ArrayList<String> Openfile() throws IOException {
		File file = new File(path);
		if (!(file.exists())) {
			file.createNewFile();
		}
		FileReader fr = new FileReader(path);
		BufferedReader textReader = new BufferedReader(fr);

		int numberOfLines = readLines();
		ArrayList<String> textData = new ArrayList<String>(numberOfLines);

		for (int i = 0; i < numberOfLines; i++) {
			textData.add(textReader.readLine());
		}

		textReader.close();
		return textData;
	}

	// To count the number of lines in the file
	public int readLines() throws IOException {
		FileReader file_to_read = new FileReader(path);
		BufferedReader bf = new BufferedReader(file_to_read);

		String aLine;
		int numberOfLines = 0;

		while ((aLine = bf.readLine()) != null) {
			numberOfLines++;
		}
		bf.close();

		return numberOfLines;
	}

	// To take in the input line and add it into the text file
	public void writeToFile(String textLine) throws IOException {
		FileWriter write = new FileWriter(path, append_to_file);
		PrintWriter print_line = new PrintWriter(write);

		print_line.printf("%s" + "%n", textLine);

		print_line.close();
	}

	// To clear the whole file by overwriting it
	public void clearFile() throws IOException {
		FileWriter write = new FileWriter(path, append_to_file);
		PrintWriter print_line = new PrintWriter(write);
		print_line.close();
	}
}
