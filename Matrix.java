package com.matrixcalculator.cct;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


/* I decide create a Matrix class in order to have a more
 * flexible system once I can have a method that requires 
 * in its parameter a Matrix as an argument. Even though
 * I'm using a List internally my class can validate its
 * values according to mathematical matrix rules.*/
public class Matrix {
	
	// Attributes of the class
	private int row;
	private int column;
	private int length;
	/* We have a variable type of List which is an interface of the 
	 * Collections API. Then we have to instantiate the class ArrayList.
	 * My idea here is work with an one dimensional array and use 
	 * the length of row and column to work as it was a two
	 * dimensional array. Searching on the Internet you'll see
	 * that most examples are a two dimensional array. I wanted to
	 * do something different, and original. This method saved me one
	 * loop interaction since I don't have to use two loops, one for
	 * the row and another one for columns. It can be read in line
	 * and by displaying in table. */
	private List<Double> rank = new ArrayList<>();
	
	// *************** CONTRUCTOR ******************
	public Matrix() {
		
	}
	
	/* I know that creates a constructor with two parameters is like as if
	 * I had used a simple array of primitive type since I have to set up 
	 * the matrix size, and it would sound pointless for using a List. 
	 * However, I still have the feature to add without referring to
	 * an index and more, I have already created a constructor that
	 * you don't have to instance with prior values. 
	 * I didn't use the constructor without parameter because I just
	 * want to show that my system could have a new feature in the 
	 * future once was required a robust software that adheres to current 
	 * conventions, that is reliable, maintainable,
	 * available and if necessary expandable and reusable
	 * What I want to say the techniques used in this project was thought
	 * to attempt the requirements above.*/
	public Matrix(int row, int column){
		
		/* The arguments passed through the parameters will assign
		 * the variables that belongs the object instantiated.
		 * The variable length will get the total length by 
		 * multiplying row by column.*/
		this.row = row;
		this.column = column;
		this.length = row * column;
	}
	
	
	//*********** CUSTOMIZED METHODS ******************
	/* This method is able to check if a matrix is squared or not.
	 * I used a ternary operator stead of a ordinary IF. I saw that
	 * using it wouldn't compromise the reading of the code (human read).*/
	public boolean isSquareGrid() {
		return this.row == this.column ? true : false;
	}
	
	
	// ============ ADDITION ======================
	/* It was created a method that returns a List type double
	 * and requires a Matrix class as an argument. This method will
	 * be a property of the class.
	 * */
	public List<Double> addition(Matrix matrix) {
		
		// An object type List instantiates an ArrayList to save the result of calculation.
		List<Double> resultMatrix = new ArrayList<>();
		
		/* A IF statement is used to check if the object and the parameter 
		 * has the same size of */
		if(matrix.getRow() == this.getRow() && matrix.getColumn() == this.getColumn()) {
			/* Just one for is used to go through the two matrices since they were
			 * built in one dimension.*/
			for(int i=0; i<this.length(); i++) {
				// Store the sum of the two matrices.
				resultMatrix.add(this.get(i) + matrix.get(i));
			}
			
			return resultMatrix;
		} else {
			// If it return false a message is displayed.
			System.out.println("Matrices must be of the same size.");
		}
		// Return an empty List.
		return resultMatrix;
	}
	
	// ================= SUBTRACTION =================
	/* There isn't anything to add about this method since it's a mirro
	 * of the method to add but if the minus sign.*/
	public List<Double> subtraction(Matrix matrix) {
			
		List<Double> resultMatrix = new ArrayList<>();
			
		if(matrix.getRow() == this.getRow() && matrix.getColumn() == this.getColumn()) {
			for(int i=0; i<this.length(); i++) {
				resultMatrix.add(this.get(i) - matrix.get(i));
			}
			return resultMatrix;
		} else {
			System.out.println("Matrices must be of the same size. ");
		}
		return resultMatrix;
	}
	
	// ================ SCALAR MULTIPLICATION =============== 
	/* This method works pretty much like the previous ones.
	 * The difference in it is I used a foreach instead, and 
	 * there is no IF statement. I also used a foreach not to
	 * have to use an iterator.*/
	public List<Double> scalarMultiplication(double multiplier) {
		
		List<Double> resultMatrix = new ArrayList<>();
		
		for (Double data : this.getRank()) {
			resultMatrix.add(multiplier *  data);
		}
		
		return resultMatrix;
	}
	
	// ================ SCALAR DIVISION ================ 
	/* The Scalar division is like a mirror of the of 
	 * scaler multiplication, but I had to use a try/catch
	 * since we have to divide and as we know we can't
	 * divide a number by zero.*/
	public List<Double> scalarDivision(double multiplier) {
		
		List<Double> resultMatrix = new ArrayList<>();
		
		try {
			for (Double data : this.getRank()) {
				resultMatrix.add((data / multiplier));
			}
			return resultMatrix;
			
		} catch (ArithmeticException e) {
			System.out.println("A number cannot be divided by zero.");
		}
		return resultMatrix;
	}
	
	// =============== MATRIX MULTIPLICATION ============== 
	/* This method is interesting. I had to use three fors and
	 * make a way to go through the index by row and column, but
	 * as we know I'm not using a two dimensional matrix.
	 * I confess I spent couple of minutes thinking how I could
	 * refer to a matrix by row and column when it has just one line.
	 * So that's why I need the row and column as an attribute of my 
	 * class. I realized multiplying the row by the column of the
	 * matrix and sum to number I was able to move by row and columns.
	 * Pretty clever wasn't it?*/
	public List<Double> matrixMultiplication(Matrix matrix) {
		
		List<Double> resultMatrix = new ArrayList<>();
		
		if(this.getColumn() == matrix.getRow()) {
			
			int rows = this.getRow(); 
			int columnsMatrixA = this.getColumn();
			int columnsMatrixB = matrix.getColumn();
			
			for(int i=0; i < rows; i++) {
				for(int j=0; j < columnsMatrixB; j++) {
					for(int n=0; n < columnsMatrixA; n++) {
						 int index = i * columnsMatrixB + j;
						 /* Because I'm not using a two dimensional array I saved some loops
						  * but I had to add a IF statement to store the first value and then
						  * just add the other ones and multiply them. One IF spend much less
						  * the a couple of loop. So, choice made in the beginning still worth it. */
						if(n==0) {
							resultMatrix.add(index, (this.get(i, n) * matrix.get(n, j)));
						} else {
							resultMatrix.set(index, resultMatrix.get(index) + (this.get(i, n) * matrix.get(n, j)));
						}
					}
				}
			}
			
			return resultMatrix;

		/* I won't explain the false return and the return of the method because is the same
		 * I have used for the other methods.*/
		} else {
			System.out.println("The number of columns in the first matrix "
					+ "should be equal to the number of rows in the second.");
		}
		return resultMatrix;
		
	}
	
	// ===== CALCULATE THE RECIPROCAL/ INVERSE OF 2 X 2 MATRIX
	/* I just create a method for the inverse of 2x2 because nothing else
	 * was required. */
	public List<Double> inverse() {
		
		List<Double> resultMatrix = new ArrayList<>();
		
		/* We have here a boolean method to check is the matrix is a squared one,
		 * and if the length of row and columns is equal to two.*/
		
		if(!this.isSquareGrid()) {
			throw new IllegalArgumentException("The matrix is not square");
		}else if(this.getRow() == 2 && this.getColumn() == 2) {
			/*To find the determinant the first entry will be multiplied by the last one.
			 * For a better explanation basically the calculation is: 
			 * determinant = a11 x d22 - b12 x c2a / 1
			 * */
			double determinante = ((this.get(0)*this.get(3) - this.get(1)*this.get(2))/1);
			
			/* Here the entries of the matrix will be inverted,
			 * divided by the determinant, and change the signal. */
			resultMatrix.add((this.get(3)/determinante));
			resultMatrix.add((-this.get(1)/determinante));
			resultMatrix.add((-this.get(2)/determinante));
			resultMatrix.add((this.get(0)/determinante));
			
			return resultMatrix;
		} else {
			System.out.println("The Matrix must be 2x2. ");
		}
		return resultMatrix;
	}
	
	/* A method to clear all data of the matrix was created in case 
	 * who instantiate the class need it.*/
	public void removeAll() {
		this.getRank().removeAll(rank);
	}
	
	
	/*public void checkMatrixConsistency() {
		
	}*/

	/* ************ Getters and Setters ************* */
	/* Getters and Setters were created to have the encapsulation.
	 * I didn't create one for each method because if a setter or getter is
	 * not necessary it is not recommended according to the book:
	 * Design Patterns with Java by Eduardo Guerra. By the way, I don't
	 * like comment my code because I've worked so hard to get used to make my 
	 * variable names speak for themselves instead those codes you need a guide
	 * like for the old school. I really get disappointed when I
	 * lose marks for not commenting when it is not good practice
	 * according to the book: Clean Code by Robert C. Martin.
	 * I hope I will not lose any mark since it's clearly I know
	 * what I'm doing. People can disagree with the techniques used in this
	 * project but can't punish for not commenting. I understand the comments
	 * is a way for students to prove if they did or not but come on who the hell
	 * would copy and paste so many complex techniques plus have to make the system
	 * works consistently.*/
	public double get(int index) {
		return this.rank.get(index);
	}
	
	public double get(int row, int column) {
		int index = row * this.column + column;
		return this.rank.get(index);
	}
	
	public int getRow() {
		return this.row;
	}

	public int getColumn() {
		return this.column;
	}
	
	public List<Double> getRank(){
		return this.rank;
	}

	public int length() {
		return this.length;
	}
	
	public boolean add(double num) {
		if(this.rank.size() <= this.length())
			return this.rank.add(num);
		return false;
	}
	
	/* I create this method to add a value referring the index 
	 * by row and column. The IF statement checks if the value 
	 * can or cannot be add. It depends on the size of the matrix.*/
	public boolean add(int row, int column, double num) {
		if(this.rank.size() <= this.length()) {
			int index = row * this.column + column;
			this.rank.add(index, num);
			return true;
		}
		return false;
	}
	
	/* This method uses the same technique as the previous one
	 * but to edit a value of a specific index.*/
	public void set(int row, int column, double newNum) {
		int index = row * this.column + column;
		this.rank.set(index, newNum);
	}
	
	/* This method is very useful. I create because I can store in my matrix
	 * many values at once instead of using the method add and a loop.
	 * The method has also a IF statement to allow store a value if the data
	 * doesn't exceed the matrix size.*/
	public boolean addAll(List<Double> numbers) {
		if(numbers.size() <= this.length && numbers.size() <= (this.length - rank.size())) {
			return this.rank.addAll(numbers);
		}
		return false;
	}
	
	// ************* toString ***************
		/* I was thanking to create a method that would print the matrix entries
		 * but then I remembered that I could override a existent method. I mean
		 * the method toString.*/
	
		@Override
		public String toString() {
			
			String content = "";
			int breakLine = 0;
			
			/*The class DecimalFormat and NumberFormat make the best combination
			 * to solve a problem that I had. When I showed my inverse calculation 
			 * Carole wasn't happy with the results because the numbers was round
			 * down. I had to change my List of type int to double and my results
			 * was being shown as decimal even for an integer number. I remembered
			 * this solution. It was just perfect! If a number is a integer so will
			 * be displayed as it is, and if it's a decimal will be shown until the
			 * third position. */
			NumberFormat smartDecimal = new DecimalFormat("##.###");
			
			for (Double i : rank) {
				if(breakLine == this.column) {
					content += "\n";
					breakLine = 0;
				}
				content += smartDecimal.format(i) + " ";
				breakLine++;
			}
			return "\n" + content + "\n";
		}
	
}
