/* This program reads in a text file and allow the user to edit or see the contents.
 * The commands are : "display", "add", "delete", "clear" and "exit" . 
 * The user will have to provide the name of the text file if the file does not exist
 * it will create one. This program assumes that the user will not
 * key in an empty line. A line of text is also assume to follow right
 * after the "add" command if not an empty line will be shown.
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.InputMismatchException;

import java.io.IOException;

public class TextBuddy {
	private static final String WELCOME_MESSAGE = "Welcome to TextBuddy. %1$s is ready for use";
	private static final String PROMPT_COMMAND = "command: ";
	private static final String FILE_ADDED = "added to %1$s: \"%2$s\"";
	private static final String FILE_DELETED = "deleted from %1$s: \"%2$s\"";
	private static final String FILE_CLEARED = "all content deleted from %1$s";
	private static final String INVALID_COMMAND = "invalid command";
	private static final String FILE_EMPTY = "%1$s is empty";
	private static final String FILE_SORTED = "%1$s is sorted";
	private static final String TASK_FOUND = "%1$s task(s) found";
	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		String file_name = new String(args[0]);
		showToUser(String.format(WELCOME_MESSAGE, file_name));
		System.out.print(PROMPT_COMMAND);
		processCommand(sc.next(), file_name);
	}
	public static void processCommand(String command, String file_name) {	
		while (!(command.equals("exit"))) {
			switch (command) {
			case "display" : 
				displayFile(file_name);
				break;
			
			case "add" :
				addToFile(file_name);
				break;
			
			case "delete" :
				deleteFromFile(file_name);
				break;
			
			case "clear" :
				clearFile(file_name);
				break;
				
			case "sort" :
				sortFile(file_name);
				break;
				
			case "search" :
				searchFile(file_name);
				break;
				
			default :
				showToUser(INVALID_COMMAND);
				sc.nextLine();
				break;
			}

			System.out.print(PROMPT_COMMAND);
			command = sc.next();
		}
	}
	
	public static void showToUser(String text) {
		System.out.println(text);
	}
	
	public static void displayFile(String file_name) {
		try {
			WriteFile data = new WriteFile(file_name);
			ArrayList<String> textLines = data.Openfile();
			if (textLines.isEmpty()) {
				showToUser(String.format(FILE_EMPTY, file_name));
			}

			else {
				for (int i = 0; i < textLines.size(); i++) {
					System.out.println((i + 1) + ". " + textLines.get(i));
				}
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	//This method adds the given line to the file
	public static void addToFile(String file_name) {
		String newLine = sc.nextLine().trim();
		try {
			WriteFile data = new WriteFile(file_name, true);
			data.writeToFile(newLine);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		showToUser(String.format(FILE_ADDED, file_name, newLine));
	}
	
	//This method is to delete the specified numbered line given by the user
	public static void deleteFromFile(String file_name) {
		try {
			int index = sc.nextInt();
			WriteFile file = new WriteFile(file_name);
			if (file.readLines() == 0) {
				showToUser(INVALID_COMMAND + ": " + String.format(FILE_EMPTY, file_name));
				return;
			}
			
			ArrayList<String> textLines = file.Openfile();
			
			if (index - 1 >= textLines.size() || index - 1 < 0) {
				showToUser("invalid index");
				return;
			}
			String removedLine = textLines.remove(index - 1);
			//When last line of text is being removed
			if (textLines.isEmpty()) {
				WriteFile data = new WriteFile(file_name);
				data.clearFile();
				showToUser(String.format(FILE_DELETED, file_name, removedLine));
			}

			else {
				WriteFile data = new WriteFile(file_name);
				for (int i = 0; i < textLines.size(); i++) {
					if (i == 1)
						data.changeValue(true);
					data.writeToFile(textLines.get(i));
				}

				showToUser(String.format(FILE_DELETED, file_name, removedLine));
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (InputMismatchException e) {
			System.out.println("invalid input");
			sc.nextLine();
		}
	}
	
	//This method deletes everything inside the file
	public static void clearFile(String file_name) {
		try {
			WriteFile data = new WriteFile(file_name);
			data.clearFile();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		showToUser(String.format(FILE_CLEARED, file_name));
	}
	
	public static void sortFile(String file_name) {
		try {
			WriteFile data = new WriteFile(file_name);
			ArrayList<String> textLines = data.Openfile();
			if (textLines.size() == 0){
				showToUser(INVALID_COMMAND);
				showToUser(String.format(FILE_EMPTY, file_name));
				return;
			}
			Collections.sort(textLines);
			data.clearFile();
			data.changeValue(true);
			for (int i = 0; i < textLines.size(); i++) {
				data.writeToFile(textLines.get(i));
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		showToUser(String.format(FILE_SORTED, file_name));
	}
	
	public static void searchFile(String file_name) {
		try {
			CharSequence toFind = sc.nextLine();
			int count = 0;
			WriteFile data = new WriteFile(file_name);
			ArrayList<String> textLines = data.Openfile();
			if (textLines.size() == 0) {
				showToUser(INVALID_COMMAND);
				showToUser(String.format(FILE_EMPTY, file_name));
				return;
			}
			ArrayList<String> linesFound = new ArrayList<String>();
			for (int i = 0; i < textLines.size(); i++) {
				if (textLines.get(i).contains(toFind)) {
					linesFound.add(textLines.get(i));
					count++;
				}
			}
			showToUser(String.format(TASK_FOUND, count));
			if (count == 0)
				return;
			for (int i = 0; i < count; i++) {
				System.out.println((i + 1) + ". " + linesFound.get(i));
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}