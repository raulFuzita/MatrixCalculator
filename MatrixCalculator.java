package com.matrixcalculator.cct;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Stream;

/* I made my class extend the RuntimeException in order to have a
 * customized exception handler.*/
public class MatrixCalculator extends RuntimeException{
	
	/* A variable type of String that is global in order to be
	 * accessed throughout the code.
	 * An object type of Map that is an instance of HashMap has
	 * two parameter in the diamond operator. One for the key
	 * and the other one for the values. The values are set up
	 * as the class Matrix.*/
	
	String file = "matrix2.txt";
	Map<String, Matrix> matrices = new HashMap<>();
	
	// MatrixCalculator extends RuntiemeException
	/* When a class extends the Runtime is required to 
	 * implements a constructor with a String as an argument
	 * to be the message of my class exception.*/
	MatrixCalculator(String message){
		super(message);
	}
	
	/* I preferred divide the constructor and the menu, that's why
	 * I create a method that will be called by the constructor.*/
	public MatrixCalculator() {
		menuHome();
		// When the system ends a friendly message is shown.
		System.out.println("\n************************************************\n"
				+"* Thank you so much for using Raul's software! *\n"
				+"* Bye and see next time :D                     *"
				+"\n************************************************\n");
	}

	// The main method invokes the constructor.
	public static void main(String[] args) {
		new MatrixCalculator();
	}
	
	// =========== MENU HOME ===========
	public void menuHome() {
		
		// This variable store the option of the menu and stops the loop.
		int option = 0;
		
		/* A Do-while was used to keep displaying the menu until the 
		 * exit option be entered.*/
		do {
		
			System.out.println("\n================= Home ================");
			System.out.println("(1) Matrix option");
			System.out.println("(2) Add two Matrices");
			System.out.println("(3) Subtract Matrices");
			System.out.println("(4) Scalar Multiplication of a Matrix");
			System.out.println("(5) Multiply two matrices");
			System.out.println("(6) Scalar Division");
			System.out.println("(7) Inverse of a 2 x 2 Matrix");
			System.out.println("(8) QUIT PROGRAM");
			System.out.println("---------------------------------------");
		
			// I stick with the method displays the prompt and get the input of the keyboard.
			option = askUserInt("Enter one of the option above: ");
			
			/* Switches are faster than IFs to use as menu. It is often use for making bots 
			 * with option multiple choices.*/
			switch(option) {
				case 1: // Matrix option
					/* This method will display a bunch of tools to have a better 
					 * experience using this system.*/
					matrixDataSource();
				break;
			
				case 2: // Add two Matrices
					
					/* The method cleanMatrices will clean any data before import a new one.
					 * This will avoid any undesirable concatenation.*/
					cleanMatrices(matrices);
					/* The method importMatrix is called to import matrices from a file.*/
					importMatrix();
					
					/* This precondition will check if thre's any matrix to make a calculation.
					 * If size is equal to zero you'll be taken to the main menu.*/
					if(matrices.size() > 0) {
						System.out.println("\n--------------------------------------------------------------\n"
								+"A Matrix addition requires matrices of the same size.\n"
								+"Next, you'll be asked to choose two matrices from a list."
								+"\n--------------------------------------------------------------\n");
						/* Breaking programming problems in modules help to isolate it.
						 * The method listMatrices is able to display a list of matrices any time,
						 * and any where as long as we have at least one matrix stored. */
						listMatrices();
						/* The variable 'a' will get a letter from an input. If the corresponding
						 * value match with the Map key a matrix will be selected.
						 * The regular expression guarantee the entered value must be a latter
						 * in uppercase or lowercase.
						 * */
						String a = askUserText("Choose a Matrix to sum: ","[a-zA-Z]");
						listMatrices();
						/* The String 'b' does the same as 'a' one.*/
						String b = askUserText("Choose a Matrix to be added: ","[a-zA-Z]");
						
						/* The method charAt will get the first letter typed and then the
						 * toUpperCase method will convert the input to uppercase.
						 * Unfortunately, we have to convert from a char to a String to do so,
						 * we can use the property valueOf of a String class. */
						a = String.valueOf(a.toUpperCase().charAt(0));
						b = String.valueOf(b.toUpperCase().charAt(0));
						
						/* A precondition is set to make sure there are both required keys to
						 * get the corresponding matrices. */
						if(matrices.containsKey(a) && matrices.containsKey(b)) {
							System.out.println("You chose the Matrix...\n\n" + matrices.get(a));
							System.out.println("\nand the Matrix...\n\n" + matrices.get(b));
							int row = matrices.get(a).getRow();
							int column = matrices.get(b).getColumn();
							/* I realized that to save an answer after a calculation I had to
							 * repeat the same step over and over again. So, I decided to create
							 * a method in charge of doing it.*/
							String text = "\nThe Addtion of the two chosen Matrices are: ";
							answerCalculation(text, matrices.get(a).addition(matrices.get(b)), row, column);
						} else {
							System.out.println("One of the chosen matrices do not exist");
						}
					} else {
						System.out.println("Sorry, you don't have any matrix :(");
					}
				break;
				
				case 3: // Subtract Matrices
					
					/* The method cleanMatrices will clean any data before import a new one.
					 * This will avoid any undesirable concatenation.*/
					cleanMatrices(matrices);
					/* This option does pretty much the same as the previous one, except for the 
					 * subtraction method.*/
					importMatrix();
					
					if(matrices.size() > 0) {
						System.out.println("\n--------------------------------------------------------------\n"
								+ "A Matrix subtraction requires matrices of the same size.\n"
								+"Next, you'll be asked to choose two matrices from a list."
								+"\n--------------------------------------------------------------\n");
						listMatrices();
						String a = askUserText("Choose a Matrix to subtract: ","[a-zA-Z]");
						listMatrices();
						String b = askUserText("Choose a Matrix to be subtracted: ","[a-zA-Z]");
						
						a = String.valueOf(a.toUpperCase().charAt(0));
						b = String.valueOf(b.toUpperCase().charAt(0));
						
						if(matrices.containsKey(a) && matrices.containsKey(b)) {
							System.out.println("You chose the Matrix...\n\n" + matrices.get(a));
							System.out.println("\nand the Matrix...\n\n" + matrices.get(b));
							int row = matrices.get(a).getRow();
							int column = matrices.get(b).getColumn();
							String text = "\nThe Subtract of the two chosen Matrices are: ";
							answerCalculation(text, matrices.get(a).subtraction(matrices.get(b)), row, column);
						} else {
							System.out.println("One of the chosen matrices do not exist");
						}
					} else {
						System.out.println("Sorry, you don't have any matrix :(");
					}
				break;
				
				case 4: // Scalar Multiplication of a Matrix
					/* Scalar multiplication keeps the same structure such as, import a file,
					 * display a list of matrices, and check if the entered value matches with
					 * the Map keys. Then it saves the answer.*/
					
					/* The method cleanMatrices will clean any data before import a new one.
					 * This will avoid any undesirable concatenation.*/
					cleanMatrices(matrices);
					importMatrix();
					
					if(matrices.size() > 0) {
						System.out.println(
								"\n--------------------------------------------------------------\n"
								+"Scalar multiplication doesn't require any matrix size rules.\n"
								+"A number is picked to multiply the matrix entries.\n"
								+"The number picked is called scalar. Choose any matrix from\n"
								+"the list and enter a number for the operation."
								+"\n--------------------------------------------------------------\n");
						listMatrices();
						String a = askUserText("Choose a Matrix to be multiplyed: ","[a-zA-Z]");
						
						a = String.valueOf(a.toUpperCase().charAt(0));
						
						if(matrices.containsKey(a)) {
							System.out.println("You chose the Matrix...\n\n" + matrices.get(a));
							double times = askUserDouble("Enter an integer number to multiply the chosen Matrix: ");
							int row = matrices.get(a).getRow();
							int column = matrices.get(a).getColumn();
							String text = "\nThe Scalar Multiplication of the Matrix by " + times + " is: ";
							answerCalculation(text, matrices.get(a).scalarMultiplication(times), row, column);
						} else {
							System.out.println("One of the chosen matrices do not exist");
						}
					} else {
						System.out.println("Sorry, you don't have any matrix :(");
					}
				break;
				
				case 5: // Multiply two matrices

					/* And again, it's the same structure of the previous ones.*/
					/* The method cleanMatrices will clean any data before import a new one.
					 * This will avoid any undesirable concatenation.*/
					cleanMatrices(matrices);
					importMatrix();
					
					if(matrices.size() > 0) {
						System.out.println(
								"\n--------------------------------------------------------------\n"
								+"The matrix multiplication requires the number of columns\n"
								+"of the first matrix has to be equal to the number of rows\n"
								+"in the second matrix and vice versa. Choose two matrices\n"
								+"which can apply the matrix multiplication rules."
								+"\n--------------------------------------------------------------\n");
						listMatrices();
						String a = askUserText("Choose a Matrix to multiply: ","[a-zA-Z]");
						
						listMatrices();
						String b = askUserText("Choose a Matrix to be multiplyed: ","[a-zA-Z]");
						
						a = String.valueOf(a.toUpperCase().charAt(0));
						b = String.valueOf(b.toUpperCase().charAt(0));
						
						if(matrices.containsKey(a) && matrices.containsKey(b)) {
							System.out.println("You chosen the Matrix...\n\n" + matrices.get(a));
							System.out.println("\nand the Matrix...\n\n" + matrices.get(b));
							int row = matrices.get(a).getRow();
							int column = matrices.get(b).getColumn();
							String text = "\nThe Multiplication of the two chosen Matrices are: ";
							answerCalculation(text, matrices.get(a).matrixMultiplication(matrices.get(b)), row, column);
						} else {
							System.out.println("One of the chosen matrices do not exist");
						}
					} else {
						System.out.println("Sorry, you don't have any matrix :(");
					}
				break;
				
				case 6: // Scalar Division
					/* The structure still the same, that's the advantage of using methods
					 * to break it up into smaller tasks. There ware just a few differences
					 * between each option. Sometimes the method for the calculation or
					 * the parameters required.*/
					
					/* The method cleanMatrices will clean any data before import a new one.
					 * This will avoid any undesirable concatenation.*/
					cleanMatrices(matrices);
					importMatrix();
					
					if(matrices.size() > 0) {
						System.out.println(
								"\n--------------------------------------------------------------\n"
								+"Scalar division doesn't require any matrix size rules.\n"
								+"A number is picked to multiply the matrix entries.\n"
								+"The number picked is called scalar. Choose any matrix from\n" 
								+"the list and enter a number for the operation."
								+"\n--------------------------------------------------------------\n");
						listMatrices();
						String a = askUserText("Choose a Matrix to be divided: ","[a-zA-Z]");
						
						a = String.valueOf(a.toUpperCase().charAt(0));
						
						if(matrices.containsKey(a)) {
							System.out.println("You chose the Matrix...\n\n" + matrices.get(a));
							double divisor = askUserDouble("Enter an integer number to divide the chosen Matrix: ");
							int row = matrices.get(a).getRow();
							int column = matrices.get(a).getColumn();
							String text = "\nThe Scalar Division of the Matrix by " + divisor + " is: ";
							answerCalculation(text, matrices.get(a).scalarMultiplication(divisor), row, column);
						} else {
							System.out.println("One of the chosen matrices do not exist");
						}
					} else {
						System.out.println("Sorry, you don't have any matrix :(");
					}
				break;
				
				case 7: // Inverse of a 2 x 2 Matrix
					/* Last option for matrix calculation but not less important.
					 * It follows the previous structure. */
					
					/* The method cleanMatrices will clean any data before import a new one.
					 * This will avoid any undesirable concatenation.*/
					cleanMatrices(matrices);
					importMatrix();
					
					if(matrices.size() > 0) {
						System.out.println(
								"\n--------------------------------------------------------------\n"
								+"The inverse of a matrix requires a squared one. For this option,\n"
								+"it's just allowed to invert a 2x2 matrix. Choose a matrix from\n"
								+"the list which corresponds to the inverse of 2x2 matrix rules."
								+"\n--------------------------------------------------------------\n");
						listMatrices();
						
						/* I didn't mention but create an overload for my method askUserText
						 * that has two parameters, one for the text and another one for 
						 * the regular expression gave me a flexible solution to solve other
						 * problems throughout the whole system.*/
						String a = askUserText("Choose a Matrix to be inversed: ","[a-zA-Z]");
						
						a = String.valueOf(a.toUpperCase().charAt(0));
						
						if(matrices.containsKey(a)) {
							System.out.println("You chose the Matrix...\n\n" + matrices.get(a));
							int row = matrices.get(a).getRow();
							int column = matrices.get(a).getColumn();
							String text = "\nThe Scalar Division of the Matrix by inverse is: ";
							answerCalculation(text, matrices.get(a).inverse(), row, column);
						} else {
							System.out.println("One of the chosen matrices do not exist");
						}
					} else {
						System.out.println("Sorry, you don't have any matrix :(");
					}
				break;
				
				case 8: // QUIT PROGRAM
					/* If the option 8 is chosen a message will be displayed 
					 * informing the user what is going on in the system.*/
					System.out.println("This section is exiting... wait please...");
				break;
				
				default:
					System.out.println("There is no such option listed!");
					
			}
			
			/* This calling is just to pause the code execution.*/
			askUserText("Press any button to continue...");
		} while(option != 8);
		
	}
	
	// =============== SAVE THE MATRIX ANSWER =====================
	/* The method answerCalculation requires a message to display before the results, and then it's
	 * required an answer stored in a List type double. The fact the method doesn't require a 
	 * object Matrix makes it reusable and flexible. The parameter row and column will set 
	 * the inner Matrix size.*/
	public void answerCalculation(String message, List<Double> answer, int row, int column) {
		
		/* An object type Matrix is created and instances the class Matrix.*/
		Matrix matrix = new Matrix(row, column);
		
		/* The answer is stored by the property addAll which will check if the data is not
		 * bigger than the Matrix size.*/
		matrix.addAll(answer);
		System.out.println(message); // The message passed will be displayed right here.
		// Here we calls the toString that was Override.
		System.out.println("\n"+matrix.toString()+"\n");
		
		/* The precondition will verify if the user wants to save the answer or not.*/
		if(askUserText("Would you like to save your Matrices in a file? (Y/N): ")
				.equalsIgnoreCase("y")) {
			
			/* Once the user agree with saving the answer in a file they'll be asked
			 * to enter a file name or just press enter and let the system decides the name
			 * automatically.*/
			String fileName = askUserText("Give a name to the file or press enter for an automatic name: ");
			
			/* If nothing is entered it's understood that a automatically name will choose.
			 * But, be aware that the first precondition is true if the filename value is different
			 * from empty.*/
			if(!fileName.equals("")) {
				
				/* Having a String to get the file name and then attribute it to the global
				 * variable file prevents an incorrect parameter be entered and causes 
				 * a crash in the system.*/
				file = fileName;
				// I find a nice practice to inform the user what is going on before keep going.
				 System.out.println("The file name is '" + file + "' and is going to be saved in the root folder.");
				 /* The method created to handle any requirement to save a matrix is called if 
				  * everything goes well the data is saved if not the user will be warned.*/
				if(saveMatrix(matrix))
					System.out.println("The answer has been saved successfully");
				else
					System.out.println("Something happened. You're answer wasn't been saved\n"
							+ "The problem can be with the file or the Matrix integrity.");
			} else {
				/* If the fileName variable is empty a logical process starts to decide the file name
				 * generated automatically.
				 * The base name is answer, it makes sense. The automatic file name generator will
				 * try to save a file with the base name if a file exists with that name an increasing
				 * pattern will be used.*/
				fileName = "answer";
				String adjunct = "";
				int count = 1;
				int fileLimit = 100; // decide how many files with the set pattern is allowed.
				
				do {
					
					/* In case of the limit be reached the loop is stopped by the command break. */
					if(count > fileLimit) {
						System.out.println(
								"-----------------------------------------------------------------\n"
								+"Sorry, you have more than " + fileLimit 
								+ " answer files in the same folder.\n"
								+"File cannot be generated automatically!\n"
								+"-----------------------------------------------------------------");
						break;
					}
					
					/* While exist files with the name the increasing pattern keep going.
					 * The name structure is for instance: answer.txt, answer(1).txt,
					 * answer(2).txt, and so forth.*/
					if(fileExist(fileName + adjunct +".txt")) {
						adjunct = "(" + count + ")";
						count++;
					} else {
						/* Finally, when a valid name is found a file is created.*/
						file = fileName + adjunct + ".txt";
						System.out.println(
								"---------------------------------------------------------------------------------\n"
								+"The file name is '" + file + "' and is going to be saved in the root folder.");
						saveMatrix(matrix);
						System.out.println("The answer has been saved successfully"
								+"\n---------------------------------------------------------------------------------");
						// the break command will get you out of the loop.
						break;
					}
				}while(count <= fileLimit);
			}
		} else {
			System.out.println("You're going to be taken to the menu option again.");
		}
	}
	
	// ============ SOME OPTION TO MANANGE THE MATRIX ==============
	/* The matrixDataSource was created to add more features to the 
	 * matrix calculator. I thought it'd be cool have tools to 
	 * import data, list all matrices, delete some, create a matrix by
	 * yourself, and maybe in the future add more useful features.*/
	public void matrixDataSource() {
		
		/* The menu structure is basically the same since it works 
		pretty well. A do-while, switch, and a menu displayed.
		Everything a need for good menu.*/
		int option = 0;
		
		do {
			
			System.out.println("============= Data Source =============");
			System.out.println("(1) Create a Matrix");
			System.out.println("(2) Import a Matrix from a file");
			System.out.println("(3) Delete a Matrix");
			System.out.println("(4) List all stored Matrix");
			System.out.println("(5) Clean matrices cache memory");
			System.out.println("(6) Exit");
			System.out.println("---------------------------------------");
			
			option = askUserInt("Enter one of the option above: ");
			
			switch(option) {
				case 1: // Create a Matrix
					/* A method that is able to create a matrix is called. If I need to
					 * create a matrix I just have to call this method.*/
					createMatrix();
					/* When the createMethod ends it's asked to decide if such matrix will
					 * be saved or not. I'm using the property equasIgnoreCase because it
					 * doesn't matter if the user will enter a lowercase or upppercase letter.*/
					if(askUserText("Would you like to save your Matrices in a file? (Y/N): ")
							.equalsIgnoreCase("y")) {
						/* Again, listMatrices is called to display the matrix created and the ones
						 * that were imported.*/
						listMatrices();
						/* A regular expression is used to get a select of matrices to save in a file.
						 * The letter which represent the matrices can be separated by comma or space.*/
						String matrixName = askUserText("Enter a corresponding letter of the list: ", "([a-zA-Z]*,?\\s?)+");
						String[] selectedMatrix = matrixName.replaceAll("([^a-zA-Z])+", ",").split(",");
						
						for (String s : selectedMatrix) {
							
							System.out.println("Selected: " + s);
							String a = String.valueOf(s.toUpperCase().charAt(0));
							
							if(matrices.containsKey(a)) {
								System.out.println("You chose the Matrix...\n" + matrices.get(a).toString());
								saveMatrix(matrices.get(a));
							} else {
								System.out.println("One of the chosen matrices do not exist");
							}
						}
						
					} else {
						System.out.println("You're going to be taken to the menu option.");
					}
				break;
				
				case 2: // Import a Matrix from a file
					/* This is a repetitive process, it's more like calling methods to do a task required.*/
					String fileName = askUserText("Enter the name of a file or "
							+ "copy, and \npaste its file path without the .txt extension: ", "^((?!.txt).)*$");
					
					/* Just a ordinary validation to make sure the user wants to do it.
					 * I think is nice when you allow the user to have the chance to
					 * cancel it or go on.*/
					if(askUserText("You entered the file name as '" 
							+ fileName + "' you're sure about? (Y/N): ").equalsIgnoreCase("y")) {
						file = fileName + ".txt";
						/* Again, I create a String called fileName not to make any change until
						 * everything be right.*/
						importMatrix();
					} else {
						System.out.println("You're going to be taken to the menu option again.");
					}
				break;
				
				case 3: // Delete a Matrix
					/* Here the method deleteMatrix is called, more information in the method itself.*/
					deleteMatrix();
				break;
				
				case 4: // List all stored Matrix
					/* Here the method listMatrices is called, more information in the method itself.*/
					listMatrices();
				break;
				
				case 5: // Clean matrices cache memory
					/* This option will clean the cache memory of the object type HashMap*/
					System.out.println("This option doesn't affect the files, only the cache memory.");
					if(askUserText("Would you like to clean the Matrices data? (Y/N): ")
							.equalsIgnoreCase("y")) {
						// Call the method to clean the global object HashMap. 
						cleanMatrices(matrices);
					} else {
						System.out.println("You're going to be taken to the menu option again.");
					}
				break;
				
				case 6:
					/* Always make a exit option available*/
					System.out.println("This section is exiting... wait please...");
				break;
				
				/*If there is no option related displays a message warning the user.*/
				default:
					System.out.println("There is no such option listed!");
					
			}
			// Just give a pause to keep a nice experience using the system.
			askUserText("Press any button to continue...");
		} while(option != 6); // When the option is equal to 6 the loop is stopped.
		System.out.println("option Data Source exited successfully.");
	}
	
	// ============ LIST ALL MATRIX STORED IN THE VARIABLE MATRICES ============
	/* We have seen this method be invoked many times, now time to see how it works*/
	public boolean listMatrices() {

		System.out.println("============== Matrix List =============");
		
		/* If the HashMap is empty there is no point to run a loop, 
		 * besides the fact we would get an index bound exception */
		if(!matrices.isEmpty()) {
			/* This foreach is just a little bit different from the ones
			 * we have seen so far. In order to access the Map keys,
			 * we have to create a temporary variable type of Map that
			 * uses the property Entry with a diamond operator that has two
			 * parameters, a String for the key index and the value type of
			 * Matrix. This variable will go through the Map using the 
			 * property entrySet. This way we're able to get the 
			 * values separately. */
			for(Map.Entry<String, Matrix> entry: matrices.entrySet()) {
				String key = entry.getKey(); // here we get the keys (index)
				Matrix matrix = entry.getValue(); // here we get the values
				
				/* Look how fantastic was override the method toString. This method
				 * is always able to display a matrix entries as long as the class is 
				 * a Matrix.*/
				System.out.println("Matrix (" + key + ") \n" + matrix.toString()
						+ "\n----------------------");
			}
			// If everything goes well is returned true.
			return true;
		} else {
			System.out.println("Sorry, you haven't stored any matrix yet.");
		}
		return false;
	}
	
	// ============= DELETE A MATRIX =============
	public void deleteMatrix() {

		boolean exit = false;
		do {
			if(listMatrices()) {
				
				System.out.println("Choose one Matrix by its letter to be deleted.");
				/* A regular expression to prevent spaces and others invalid characters */
				String option = askUserText("Enter the Matrix letter or type 0 (zero) to exit: ", "[a-zA-Z0-9]");
			
				option = String.valueOf(option.toUpperCase().charAt(0));
				
				/* This precondition works the same as ones used to calculate matrices.*/
				if(matrices.containsKey(option)) {
					// If the passed key is found the matrix selected will be removed.
					if(matrices.remove(option) != null)
						System.out.println("The Matrix (" + option + ") has been deleted.");
					else
						System.out.println("The Matrix (" + option + ") wasn't find. Something happened!");
					
				/* If the user enter zero the option is left*/
				} else if(option.equals("0")) {
					exit = true;
				} else {
					/* Any unpredictable option will warn the user. */
					System.out.println("This option doesn't exist: " + "(" + option + ")");
				}
			} else {
				/* If the user chooses the option to delete but there is no matrix they'll be
				 * warned.*/
				System.out.println("The Matrix list is empty! Let's store some Matrices ;)");
				exit = true; // exit now is equal true to stop the loop
			}
		} while(!exit);
		System.out.println("You left the Delete Option.");
	}
	
	// ============ CREATE A MATRIX BY USER ==================
	/* If there were two methods that made me take a lot of time to create I have no doubt they're
	 * the method to create a matrix and import a matrix. Due to the complexity to predict any 
	 * combination from a file or input value I had to use a lot from my programming background and
	 * advanced techniques. Some I never thought I would have to use.*/
	public void createMatrix() {
		
		String rank = ""; // This variable will store the data and then be broken down in single data.
		/*The regular expression here allow just numbers between 1 and 9 and one digit separated by
		 * the letters x or X. Because is not possible to have a matrix with zero row and column
		 * I couldn't allow the number zero. I decided one digit for row size and column to avoid 
		 * unnecessary and huge matrices, but I can make it bigger if I want to.
		 * I decided use as a separator the letter x in order to look nicer.*/
		String[] size = askUserText("Enter the number of row and column (2x2): ", "^[1-9]?\\d{1}[xX]\\d{1}")
				.replaceAll("\\s", "") // After a data is entered all spaces are removed.
				.split("[xX]"); // Entered values are split in the Xs and stored into a String array.
		
		/* Here I have to get the size of the row to repeat until the user define all the entries of 
		 * matrix. Because the variable size is an array of String I have to convert to integer using
		 * the class Integer and property parseInt.*/
		int fillRank = Integer.parseInt(size[0]);
		/* An ordinary loop will repeat according to the size of the row.*/
		for(int i=0; i < fillRank; i++) {
			/* Each interaction of the loop the value the user entered will be stored into the 
			 * variable rank. The regular expression allows negative numbers, any size following
			 * by comma or space. However, is not allowed enter more elements than the column size.
			 * Using regular expression save me a lot of time creating preconditions and loops or 
			 * something else.*/
			
			rank += askUserText("Enter de numbers of " 
					+ (i+1) + "º row (1, -5, 0, 12): ", "(-?\\d*,?\\s?){" + size[1] + "}") + ";";
		}
		
		/* This was the advanced technique I thought I would never use so soon.
		 * Basically what this piece of code does is use the class Stream with the 
		 * property of that gets the rank, but before that the rank will replace some
		 * characters by a comma. Any character that isn't a number or minus signal following
		 * a digit will be replaced by a comma. This means any other inconsistent format
		 * will be rid of. I deserve a full score for this technique lol.
		 * Then all comma sign will be a separator. By this point we have an array of String that
		 * will be passed to a property calls mapToDouble that will convert all data which was 
		 * stored singularly into an array WHITHOUT ANY LOOP! then be converted into an array 
		 * since we have used the property mapToDouble. Come on, I deserve a high score for
		 * the next three years for such brillant idea in a simple Matrix project. All
		 * kidding aside, let's keep going  */
		double[] filledRank = Stream.of(rank.replaceAll("(-?[^-\\d])+", ",")
				.split(",|;"))
                .mapToDouble(Double::parseDouble).toArray();

		/* Here I'm reusing the variable size to get the row and column length.*/
		int row = Integer.parseInt(size[0]);
		int column = Integer.parseInt(size[1]);
		
		/* An object that instantiates my class Matrix.*/
		Matrix matrix = new Matrix(row, column);
		
		/* A foreach again to add each entry of the filledRank to the matrix.*/
		for (double i : filledRank) {
			matrix.add(i);
		}
		
		/* Each matrix created will be stored first into the Matrices type of HashMap*/
		storeMatrices(matrix);
	}
	
	
	// ========== STORE A MATRIX IN A FILE ==============
	/* The method saveMatrix has a class Matrix as a parameter.*/
	public boolean saveMatrix(Matrix matrix) {
		/* A try/catch for a undesirable exception.*/
		try {
			/* The idea here is display the size of the matrix and than 
			 * display its entries.*/
			String content = matrix.getRow() + " " + matrix.getColumn() + "\n";
			content  += matrix.toString();
		
			/*Here we have an interesting technique to save a first data of a file and
			 * to add the matrix separator for more the one matrix. The assessment requires
			 * the @ symbol to separate matrices in a file.*/
			if(!fileExist(file)) {
				/*The first argument of the setFile method requires the content to be written into 
				 * the file. The second one requires the file name and the third one requires a boolean 
				 * to decide whether the content will overwrite or be written at the end of the file.
				 * False represent the replacement of the whole content and true writes at the end.*/
				setFile(content, file, false);
				return true;
			} else {
				setFile("@\n" + content, file, true);
				return true;
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	// ============ IMPORT A MATRIX FROM A FILE ===========
	/* This method might be the longest in this project. I don't think I'll spend
	 * too much time explaining the whole method since I already did in the previous one
	 * and this method uses many used techniques.*/
	public void importMatrix() {
		
		/* A try/catch is used to catch many exceptions here such complexity to read a file
		 * that couldn't even be a file with matrices.*/
		try {
			
			/* An object the type of List is created without instance an ArrayList.
			 * It just is not necessary.*/
			List<String> validateMatrix;
			/* The variable body will deal with the matrix data. 
			 * As a file or document I decided to divide it in a heading for the matrix size and
			 * a body for the matrix content. */
			String body = ""; 
			
			/* It counts how many matrix has been imported successfully.*/
			int countMatrix = 0;
			/*Yes, I still have one more interesting technique to show. 
			 * The method getFile reads a file, of course. Then the data is broken down by 
			 * any breaking line that is represented by the regular expression '\n'.
			 * I wanted to avoid loops to add the file content into the List
			 * and try to use more resources from the existent APIs.
			 * Why reinvent the wheel using many loops? Arrays.asList does this job.*/
			validateMatrix = Arrays.asList(getFile(file).split("\n"));
			/* I need this variable to get the length of the List and be a precondition to 
			 * stop the For loop.*/
			
			int matricesLength = validateMatrix.size();
			int row = 0, column = 0;
			
			
			for(int i=0; i < matricesLength; i++) {
				
				/*With a regular expression I can check with the first line is a number between
				 * 1 and 9 separated by space. The regular expression will also check the whole line.*/
				if(validateMatrix.get(i).matches("^([1-9]\\s[1-9])$")) {
					
					/* The first line will be the heading where the matrix size is.
					 * The replaceAll will replace any space and extra space into a comma. The comma will
					 * be the separator. I chose a comma just to make easier to handle the data*/
					String[] head = validateMatrix.get(i).replaceAll("\\s+", ",").split(",");
					
					/*Just convert String into integer and define the row and column length.*/
					row = Integer.parseInt(head[0]);
					column = Integer.parseInt(head[1]);
					
					/* In order to prevent any other problem the multiplication of row and column has to be
					 * bigger than zero.*/
					if(row * column !=0) {
						/* The variable 'i' is incremented to read read the next line.*/
						i++;
						
						/* The variable times will define the number of entries for each line.
						 * I used the class Math and property abs that means absolute by the way,
						 * to change the signal with the result is negative*/
						int times = Math.abs(column-1);
						
						/* This for will check the format of each line. It must be allowed by the regular expression*/
						for(int j = 0; j < row; j++, i++) {
							
							/* The property get will get the stored value and check its structure using
							 * the property matches. The regular expression just allow negative numbers, there isn't 
							 * a specific size, each value has to be followed by comma, space or both together.
							 * The variable times as mentioned before will limit the digits for each column.
							 * The last part of the expression requires a number to end it otherwise it will be
							 * considered wrong.*/
							if(validateMatrix.get(i).matches("(-?\\d*[,?\\s?]){" + times + "}-?\\d+(?![^\\d])$")) {
								/* Just to handle easier the data I used a semicolon to define where each line
								 * ends.*/
								body += validateMatrix.get(i) + ";";
							}else { // It's a body
								/* If something goes wrong a warning message fires and point out where is
								 * the problem in the file. Which line is wrong. 
								 * Our customized exception that implements the class Runtime is being used right now.
								 * If I had used a boolean all mistakes in the file would be shown straightway.
								 * My idea is help the user fixes each mistake at a time. Once the MatrixCalculator
								 * exception is fired the method is stopped. */
								throw new MatrixCalculator("The data is not compatible with a Matrix.\n"
										+ "Error in the line " + (i+1) + " of the file " + file);
							}
						}
						
						/* The object Matrix I won't explain I already did.
						 * The Stream.of I already explained I won't do it again and also the
						 * foreach after that.*/
						Matrix matrix = new Matrix(row, column);

						double[] filledRank = Stream.of(body.replaceAll("(-?[^-\\d])+", ",").split(",|;"))
								.mapToDouble(Double::parseDouble)
								.toArray();

						for (double data : filledRank) {
							matrix.add(data);
						}
						
						/* Here the method storesMatrices will store each matrix into my object HashMap
						 * to use it later.*/
						if(storeMatrices(matrix)) {
							
							countMatrix++;
							
							/* Display the number of matrices imported so far and their size*/
							System.out.println(countMatrix 
									+ " matrix/ matrices " + matrix.getRow() +"x" + matrix.getRow() 
									+ " have imported successfully!");
						} else {
							System.out.println("Matrix wasn't upload in the Matrices Collection");
						}
					}
					
				/* If any regular expression matches to the line it'll be checked if there isn't
				 * the @ symbol that indicates there is another matrix. If there isn't the symbol
				 * it means there is no valid matrix.*/
				}  else if (!validateMatrix.get(i).contains("@")){
					throw new MatrixCalculator("The rank format is not compatible.\n"
							+ "Error in the line " + (i+1) + " of the file " + file);
				}
				body = "";
			}
		
		/* Some exceptions to prevent crashes in the system.*/
		} catch (FileNotFoundException | MatrixCalculator e) {
			System.out.println(e.getMessage());
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Index " + e.getMessage() + " is bigger than the List");
		}
	}
	
	
	// ==== STORE A MATRIX OR MATRICES INTO A VARIABLE THE TYPE OF MAP =====
	/* This method will generate the keys for the HashMap.*/
	public boolean storeMatrices(Matrix matrix) {
		// ASCII letters from 65 to 90;
		// Using a ASCII code to create the keys.
		int ascii = 65;
		char key = 'A'; // Key for default
		boolean successfulStore = false; // If everything is ok returns true.
		
		/* A do-while will be checking and generating the keys for the HashMap.*/
		do {
			
			// Just cast a integer to a char type.
			key = (char)ascii;
			
			/* If the key already exist the variable ascii will be increased one.
			 * The Class String with the property valueOf will convert char to String.*/
			if(matrices.containsKey(String.valueOf(key))) {
				/* I have to use a integer variable to generate the letter alphabetically.*/
				ascii++;
			} else {
				/* Once we find an available latter it's defined as a key.*/
				matrices.put(String.valueOf(key), matrix);
				successfulStore = true;
			}
		
		/* If all keys have already been generated the loop will stop.*/
		} while(ascii > 65 && ascii < 90 && !successfulStore);
		
		/* If there is no latter available or any other problem a message will be displayed.*/
		if(!successfulStore) {
			System.out.println("Sorry, it's not possible to store more matrix.\n"
					+"Delete some matrix or import a new file.");
		}
		
		return successfulStore;
	}
	
	// =========== CLEAN ALL DATA OF THE MATRIX TYPE MAP ===========
	/* This method clean all data of the matrix type Map passed 
	 * as argument.*/
	public boolean cleanMatrices(Map<String, Matrix> matrices) {
		matrices.clear();
		/* A ternary operator is used to return true if the matrix
		 * has been cleaned successfully or false if it hasn't.*/
		return matrices.size() > 0 ? true : false;
	}
	
	// ========= INPUT A STRING WITH ONLY ONE PARAMETER =========
	/* A method to display a question and the user input a value 
	 * the type of String.*/
	public String askUserText(String prompt) {
		Scanner input = new Scanner(System.in);
		System.out.println(prompt);
		return input.nextLine();
	}
	
	// ======== OVERLOAD OF THE ASKUSERTEXT METHOD ==========
	/* Basically, the same method but with one more parameter.
	 * This method uses a regular expression to validate the input.
	 * If the input is incorrect the user will be asked to try again.*/
	public String askUserText(String prompt, String regEx) {
		Scanner input = new Scanner(System.in);
		String text = "";
		do {
			System.out.println(prompt);
			text = input.nextLine();
			if(!text.matches(regEx))
				System.out.println("The value entered doesn't match! Please, try again.\n"
					+ "----------------------------------------------\n");
			else
				break;
		} while(true);
		return text;
	}
	
	// ====== INPUT AN INTEGER WITH ONLY ONE PARAMETER =====
	/* This method works as the previous one but for integer numbers.
	 * A try/catch is fired if the user enter a invalid value. 
	 * The catch block will ask the method itself. This is known as recursion. */
	public int askUserInt(String prompt) {

		System.out.println(prompt);
		try {
			Scanner input = new Scanner(System.in);
			return input.nextInt();
		} catch (InputMismatchException e) {
			return askUserInt("The entered value doesn't match. Please, enter an integer number!" 
					+ prompt);
		}
	}
	
	// ====== INPUT A DOUBLE VALUE WITH ONLY ONE PARAMENTER ======
	/* This method does the same other ones but it is for double values.*/
	public double askUserDouble(String prompt) {
		
		System.out.println(prompt);
		try {
			Scanner input = new Scanner(System.in);
			return input.nextDouble();
		} catch (InputMismatchException e) {
			return askUserDouble("The entered value doesn't match. Please, enter an integer number!" 
					+ prompt);
		}
	}

	// ======= STORE ANY DATA IN A FILE ============
	/* The method setFile has three parameters. First one to stores the content into the file.
	 * Second one to find a file with the given name. Third one writes the content at the end of the
	 * file or replace all the content to a new one.*/
	public boolean setFile(String content, String fileName, boolean notOverwrite) {
		/* A bufferedWriter is used to write different type of data. */
		try (BufferedWriter writer = new BufferedWriter( new FileWriter(fileName, notOverwrite))){
			// The property write writes the content into the file
			writer.write(content);
			// The property newLine moves to the next one.
			writer.newLine();
			/* The close property empty the memory used in this process. 
			 * It is good practice even though we are using it in a try/catch block.
			 * We never know when the Garbage Collector will clean here.*/
			writer.close();
			return true;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	// ========= GET A ANY DATA FROM A FILE =============
	/* This method read the content of a file and returns the content by the return.*/
	public String getFile(String fileName) throws FileNotFoundException {
		String content = "";
		
		/* I decided use Scanner to read the file instead of the bufferedReader
		 * because it just easier.*/
		Scanner reader = new Scanner(new FileReader(fileName));
			
			/* The property hasNext will check if exist the next line.*/
			while(reader.hasNext()) {
				/* Here I have to concatenate each read line to the variable plus
				 * the braking line regular expression. As you saw I need this structure for the
				 * other methods.*/
				content += reader.nextLine() + "\n";
			}
		
		/* I already explained the property close, I don1t have to explain again.*/
		reader.close();
		return content;
	}
	
	// ======== CHECK IF THE FILE REQUERED EXIST =========
	/* This method will check if the file already exist.
	 * A ternary operator is used to return false or true.*/
	public boolean fileExist(String fileName) {
		File file = new File(fileName);
		return !file.exists() ? false : true;
	}
	
	
}
