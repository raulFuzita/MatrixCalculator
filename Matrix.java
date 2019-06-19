package com.matrixcalculator.cct;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/*
* @author Raul Fuzita
*/

public class Matrix {
	
	private int row;
	private int column;
	private int length;
	private List<Double> rank = new ArrayList<>();
	
	public Matrix() {
		
	}
	
	public Matrix(int row, int column){
		
		this.row = row;
		this.column = column;
		this.length = row * column;
	}
	
	
	public boolean isSquareGrid() {
		return this.row == this.column ? true : false;
	}
	
	
	public List<Double> addition(Matrix matrix) {
		
		List<Double> resultMatrix = new ArrayList<>();
		
		if(matrix.getRow() == this.getRow() && matrix.getColumn() == this.getColumn()) {

			for(int i=0; i<this.length(); i++) {
				resultMatrix.add(this.get(i) + matrix.get(i));
			}
			
			return resultMatrix;
		} else {
			System.out.println("Matrices must be of the same size.");
		}
		return resultMatrix;
	}
	

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
	

	public List<Double> scalarMultiplication(double multiplier) {
		
		List<Double> resultMatrix = new ArrayList<>();
		
		for (Double data : this.getRank()) {
			resultMatrix.add(multiplier *  data);
		}
		
		return resultMatrix;
	}
	

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
						if(n==0) {
							resultMatrix.add(index, (this.get(i, n) * matrix.get(n, j)));
						} else {
							resultMatrix.set(index, resultMatrix.get(index) + (this.get(i, n) * matrix.get(n, j)));
						}
					}
				}
			}
			
			return resultMatrix;


		} else {
			System.out.println("The number of columns in the first matrix "
					+ "should be equal to the number of rows in the second.");
		}
		return resultMatrix;
		
	}
	


	public List<Double> inverse() {
		
		List<Double> resultMatrix = new ArrayList<>();
		
		
		if(!this.isSquareGrid()) {
			throw new IllegalArgumentException("The matrix is not square");
		}else if(this.getRow() == 2 && this.getColumn() == 2) {
			
			double determinante = ((this.get(0)*this.get(3) - this.get(1)*this.get(2))/1);
			
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
	
	public void removeAll() {
		this.getRank().removeAll(rank);
	}
	
	

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
	

	public boolean add(int row, int column, double num) {
		if(this.rank.size() <= this.length()) {
			int index = row * this.column + column;
			this.rank.add(index, num);
			return true;
		}
		return false;
	}
	
	public void set(int row, int column, double newNum) {
		int index = row * this.column + column;
		this.rank.set(index, newNum);
	}
	
	public boolean addAll(List<Double> numbers) {
		if(numbers.size() <= this.length && numbers.size() <= (this.length - rank.size())) {
			return this.rank.addAll(numbers);
		}
		return false;
	}
	
	
		@Override
		public String toString() {
			
			String content = "";
			int breakLine = 0;
			
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
