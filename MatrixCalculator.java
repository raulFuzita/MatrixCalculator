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

/*
* @author Raul Fuzita
*/

public class MatrixCalculator extends RuntimeException{
	
	
	String file = "matrix2.txt";
	Map<String, Matrix> matrices = new HashMap<>();
	
	MatrixCalculator(String message){
		super(message);
	}
	
	public MatrixCalculator() {
		menuHome();
		System.out.println("\n************************************************\n"
				+"* Thank you so much for using Raul's software! *\n"
				+"* Bye and see next time :D                     *"
				+"\n************************************************\n");
	}

	public static void main(String[] args) {
		new MatrixCalculator();
	}
	
	// =========== MENU HOME ===========
	public void menuHome() {
		
		int option = 0;
		
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
		
			option = askUserInt("Enter one of the option above: ");
			
			switch(option) {
				case 1: // Matrix option
					matrixDataSource();
				break;
			
				case 2: // Add two Matrices
					cleanMatrices(matrices);
					importMatrix();
					
					if(matrices.size() > 0) {
						System.out.println("\n--------------------------------------------------------------\n"
								+"A Matrix addition requires matrices of the same size.\n"
								+"Next, you'll be asked to choose two matrices from a list."
								+"\n--------------------------------------------------------------\n");

						listMatrices();

						String a = askUserText("Choose a Matrix to sum: ","[a-zA-Z]");
						listMatrices();

						String b = askUserText("Choose a Matrix to be added: ","[a-zA-Z]");
						

						a = String.valueOf(a.toUpperCase().charAt(0));
						b = String.valueOf(b.toUpperCase().charAt(0));
						

						if(matrices.containsKey(a) && matrices.containsKey(b)) {
							System.out.println("You chose the Matrix...\n\n" + matrices.get(a));
							System.out.println("\nand the Matrix...\n\n" + matrices.get(b));
							int row = matrices.get(a).getRow();
							int column = matrices.get(b).getColumn();

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
					

					cleanMatrices(matrices);

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

					System.out.println("This section is exiting... wait please...");
				break;
				
				default:
					System.out.println("There is no such option listed!");
					
			}
			
			askUserText("Press any button to continue...");
		} while(option != 8);
		
	}
	

	public void answerCalculation(String message, List<Double> answer, int row, int column) {
		

		Matrix matrix = new Matrix(row, column);
		

		matrix.addAll(answer);
		System.out.println(message); 
		System.out.println("\n"+matrix.toString()+"\n");
		

		if(askUserText("Would you like to save your Matrices in a file? (Y/N): ")
				.equalsIgnoreCase("y")) {
			

			String fileName = askUserText("Give a name to the file or press enter for an automatic name: ");
			

			if(!fileName.equals("")) {
				

				file = fileName;
				 System.out.println("The file name is '" + file + "' and is going to be saved in the root folder.");

				if(saveMatrix(matrix))
					System.out.println("The answer has been saved successfully");
				else
					System.out.println("Something happened. You're answer wasn't been saved\n"
							+ "The problem can be with the file or the Matrix integrity.");
			} else {

				fileName = "answer";
				String adjunct = "";
				int count = 1;
				int fileLimit = 100; 
				
				do {
					
					
					if(count > fileLimit) {
						System.out.println(
								"-----------------------------------------------------------------\n"
								+"Sorry, you have more than " + fileLimit 
								+ " answer files in the same folder.\n"
								+"File cannot be generated automatically!\n"
								+"-----------------------------------------------------------------");
						break;
					}
					
					if(fileExist(fileName + adjunct +".txt")) {
						adjunct = "(" + count + ")";
						count++;
					} else {
						
						file = fileName + adjunct + ".txt";
						System.out.println(
								"---------------------------------------------------------------------------------\n"
								+"The file name is '" + file + "' and is going to be saved in the root folder.");
						saveMatrix(matrix);
						System.out.println("The answer has been saved successfully"
								+"\n---------------------------------------------------------------------------------");
						break;
					}
				}while(count <= fileLimit);
			}
		} else {
			System.out.println("You're going to be taken to the menu option again.");
		}
	}
	
	public void matrixDataSource() {
		
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

					createMatrix();

					if(askUserText("Would you like to save your Matrices in a file? (Y/N): ")
							.equalsIgnoreCase("y")) {

						listMatrices();

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

					String fileName = askUserText("Enter the name of a file or "
							+ "copy, and \npaste its file path without the .txt extension: ", "^((?!.txt).)*$");
					
					if(askUserText("You entered the file name as '" 
							+ fileName + "' you're sure about? (Y/N): ").equalsIgnoreCase("y")) {
						file = fileName + ".txt";

						importMatrix();
					} else {
						System.out.println("You're going to be taken to the menu option again.");
					}
				break;
				
				case 3: // Delete a Matrix
					deleteMatrix();
				break;
				
				case 4: // List all stored Matrix
					listMatrices();
				break;
				
				case 5: // Clean matrices cache memory
					System.out.println("This option doesn't affect the files, only the cache memory.");
					if(askUserText("Would you like to clean the Matrices data? (Y/N): ")
							.equalsIgnoreCase("y")) {
						cleanMatrices(matrices);
					} else {
						System.out.println("You're going to be taken to the menu option again.");
					}
				break;
				
				case 6:
					System.out.println("This section is exiting... wait please...");
				break;
				
				default:
					System.out.println("There is no such option listed!");
					
			}
			askUserText("Press any button to continue...");
		} while(option != 6);
		System.out.println("option Data Source exited successfully.");
	}
	
	public boolean listMatrices() {

		System.out.println("============== Matrix List =============");
		
		if(!matrices.isEmpty()) {
			for(Map.Entry<String, Matrix> entry: matrices.entrySet()) {
				String key = entry.getKey();
				Matrix matrix = entry.getValue();
				
				System.out.println("Matrix (" + key + ") \n" + matrix.toString()
						+ "\n----------------------");
			}
			return true;
		} else {
			System.out.println("Sorry, you haven't stored any matrix yet.");
		}
		return false;
	}
	
	public void deleteMatrix() {

		boolean exit = false;
		do {
			if(listMatrices()) {
				
				System.out.println("Choose one Matrix by its letter to be deleted.");
				String option = askUserText("Enter the Matrix letter or type 0 (zero) to exit: ", "[a-zA-Z0-9]");
			
				option = String.valueOf(option.toUpperCase().charAt(0));
				
				if(matrices.containsKey(option)) {
					if(matrices.remove(option) != null)
						System.out.println("The Matrix (" + option + ") has been deleted.");
					else
						System.out.println("The Matrix (" + option + ") wasn't find. Something happened!");
					
				} else if(option.equals("0")) {
					exit = true;
				} else {
					System.out.println("This option doesn't exist: " + "(" + option + ")");
				}
			} else {
				System.out.println("The Matrix list is empty! Let's store some Matrices ;)");
				exit = true;
			}
		} while(!exit);
		System.out.println("You left the Delete Option.");
	}
	
	public void createMatrix() {
		
		String rank = ""; // This variable will store the data and then be broken down in single data.
		String[] size = askUserText("Enter the number of row and column (2x2): ", "^[1-9]?\\d{1}[xX]\\d{1}")
				.replaceAll("\\s", "")
				.split("[xX]");
		int fillRank = Integer.parseInt(size[0]);
		for(int i=0; i < fillRank; i++) {
			
			rank += askUserText("Enter de numbers of " 
					+ (i+1) + "º row (1, -5, 0, 12): ", "(-?\\d*,?\\s?){" + size[1] + "}") + ";";
		}
		
		double[] filledRank = Stream.of(rank.replaceAll("(-?[^-\\d])+", ",")
				.split(",|;"))
                .mapToDouble(Double::parseDouble).toArray();

		int row = Integer.parseInt(size[0]);
		int column = Integer.parseInt(size[1]);
		
		Matrix matrix = new Matrix(row, column);
		
		for (double i : filledRank) {
			matrix.add(i);
		}
	
		storeMatrices(matrix);
	}
	
	public boolean saveMatrix(Matrix matrix) {
		try {
			String content = matrix.getRow() + " " + matrix.getColumn() + "\n";
			content  += matrix.toString();
		
			if(!fileExist(file)) {
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
	
	public void importMatrix() {
		
		try {
			
			List<String> validateMatrix;
			String body = ""; 
			
			int countMatrix = 0;
			validateMatrix = Arrays.asList(getFile(file).split("\n"));
			
			int matricesLength = validateMatrix.size();
			int row = 0, column = 0;
			
			
			for(int i=0; i < matricesLength; i++) {
				
				if(validateMatrix.get(i).matches("^([1-9]\\s[1-9])$")) {
					
					String[] head = validateMatrix.get(i).replaceAll("\\s+", ",").split(",");
					
					row = Integer.parseInt(head[0]);
					column = Integer.parseInt(head[1]);
					
					if(row * column !=0) {
						i++;
						
						int times = Math.abs(column-1);
						
						for(int j = 0; j < row; j++, i++) {

							if(validateMatrix.get(i).matches("(-?\\d*[,?\\s?]){" + times + "}-?\\d+(?![^\\d])$")) {
								body += validateMatrix.get(i) + ";";
							}else {
								throw new MatrixCalculator("The data is not compatible with a Matrix.\n"
										+ "Error in the line " + (i+1) + " of the file " + file);
							}
						}
						
						Matrix matrix = new Matrix(row, column);

						double[] filledRank = Stream.of(body.replaceAll("(-?[^-\\d])+", ",").split(",|;"))
								.mapToDouble(Double::parseDouble)
								.toArray();

						for (double data : filledRank) {
							matrix.add(data);
						}
						
						if(storeMatrices(matrix)) {
							
							countMatrix++;
							
							System.out.println(countMatrix 
									+ " matrix/ matrices " + matrix.getRow() +"x" + matrix.getRow() 
									+ " have imported successfully!");
						} else {
							System.out.println("Matrix wasn't upload in the Matrices Collection");
						}
					}
					
				}  else if (!validateMatrix.get(i).contains("@")){
					throw new MatrixCalculator("The rank format is not compatible.\n"
							+ "Error in the line " + (i+1) + " of the file " + file);
				}
				body = "";
			}
		
		} catch (FileNotFoundException | MatrixCalculator e) {
			System.out.println(e.getMessage());
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Index " + e.getMessage() + " is bigger than the List");
		}
	}
	
	public boolean storeMatrices(Matrix matrix) {
		int ascii = 65;
		char key = 'A';
		boolean successfulStore = false;
		
		do {
			
			key = (char)ascii;
			
			if(matrices.containsKey(String.valueOf(key))) {
				ascii++;
			} else {
				matrices.put(String.valueOf(key), matrix);
				successfulStore = true;
			}
		
		} while(ascii > 65 && ascii < 90 && !successfulStore);
		
		if(!successfulStore) {
			System.out.println("Sorry, it's not possible to store more matrix.\n"
					+"Delete some matrix or import a new file.");
		}
		
		return successfulStore;
	}
	

	public boolean cleanMatrices(Map<String, Matrix> matrices) {
		matrices.clear();
		return matrices.size() > 0 ? true : false;
	}
	
	public String askUserText(String prompt) {
		Scanner input = new Scanner(System.in);
		System.out.println(prompt);
		return input.nextLine();
	}
	
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

	public boolean setFile(String content, String fileName, boolean notOverwrite) {

		try (BufferedWriter writer = new BufferedWriter( new FileWriter(fileName, notOverwrite))){

			writer.write(content);
			writer.newLine();

			writer.close();
			return true;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public String getFile(String fileName) throws FileNotFoundException {
		String content = "";
		
		Scanner reader = new Scanner(new FileReader(fileName));
			
			while(reader.hasNext()) {

				content += reader.nextLine() + "\n";
			}
		
		reader.close();
		return content;
	}
	
	public boolean fileExist(String fileName) {
		File file = new File(fileName);
		return !file.exists() ? false : true;
	}
	
	
}
