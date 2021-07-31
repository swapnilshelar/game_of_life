package com.assignment.gameoflife;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Simulation {
	int rowSize;
	int colSize;
	int matrix[][];

	// Initializing the size of row and column.
	// Initializing two-dimensional orthogonal grid which named as matrix

	Simulation(int rowSize, int colSize) {
		this.rowSize = rowSize;
		this.colSize = colSize;
		matrix = new int[rowSize][colSize];
	}

	// Declare live cells in the matrix, by initializing it by 1

	public void liveCell(int row, int col) {
		matrix[row][col] = 1;
	}

	// Declare dead cells in matrix, by initializing it by 0

	public void deadCell(int row, int col) {
		matrix[row][col] = 0;
	}

	// Displaying the grid
	// Live cells indicated by '*'
	// Dead cells indicated by '.'

	public void printMatrix() {
		for (int i = 0; i < rowSize; i++) {
			for (int j = 0; j < colSize; j++) {

				// checking condition whether cell is alive or dead

				if (matrix[i][j] == 0) {
					System.out.print("." + " ");
				} else if (matrix[i][j] == 1) {
					System.out.print("*" + " ");
				}
			}
			System.out.println();
		}
	}

	// Display current state of individual cell whether it is alive or dead

	public void currentStateOfCell(int row, int col) {
		if (this.matrix[row][col] == 1)
			System.out.println("Current state of cell " + row + col + " is alive");
		else
			System.out.println("Current state of cell " + row + col + " is dead");
	}

	// Counting all alive neighbors which are directly horizontally, vertically, or
	// diagonally adjacent

	public int countAllAliveNeighbors(int row, int col) {
		// Initializing counter to zero
		int count = 0;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				count += isOutOfBound(row + i, col + j);
			}
		}

		// Current cell need to be subtracted from count because it was counted above
		count -= matrix[row][col];

		return count;
	}

	// Checking whether index passed is not out of bound
	// If index is less than 0 or greater than equal to size it will return zero
	// If it is OK then return given cell at it is
	// It will help while counting alive neighbors

	public int isOutOfBound(int row, int col) {
		if (row < 0 || row >= rowSize) {
			return 0;
		}
		if (col < 0 || col >= colSize) {
			return 0;
		}
		return this.matrix[row][col];
	}

	// Implementing logic for future generation

	public void reshufflingOfCells() {
		int newMatrix[][] = new int[rowSize][colSize];
		for (int j = 0; j < colSize; j++) {
			for (int i = 0; i < rowSize; i++) {
				int aliveNeighbours = this.countAllAliveNeighbors(i, j);

				// Live cell with fewer than two live neighbors dies, as if by loneliness.

				if ((this.matrix[i][j] == 1) && (aliveNeighbours < 2))
					newMatrix[i][j] = 0;

				// Live cell with more than three live neighbors dies, as if by overcrowding.

				else if ((this.matrix[i][j] == 1) && (aliveNeighbours > 3))
					newMatrix[i][j] = 0;

				// Dead cell with exactly three live neighbors comes to life

				else if ((this.matrix[i][j] == 0) && (aliveNeighbours == 3))
					newMatrix[i][j] = 1;

				// Others will remains the same
				else
					newMatrix[i][j] = this.matrix[i][j];

			}
		}
		this.matrix = newMatrix;
	}

	public static void main(String[] args) {
		try {
			boolean flag = true;
			Scanner sc = new Scanner(System.in);

			// Enter size of two-dimensional orthogonal grid

			System.out.println("Enter the size of row and column ");
			int rowSize = sc.nextInt();
			int colSize = sc.nextInt();
			Simulation grid = new Simulation(rowSize, colSize);

			// Enter number of live cells you want in grid

			System.out.println("Enter number of live cells you want in grid");
			int num = sc.nextInt();

			// Enter positions of live cell one by one

			System.out.println("Enter the indices for each live cell ");
			for (int i = 1; i <= num; i++) {
				int rowInd = sc.nextInt();
				int colInd = sc.nextInt();
				grid.liveCell(rowInd, colInd);
			}

			// Display current generation

			System.out.println("Current generation is here ");
			grid.printMatrix();

			// Display future generations

			while (flag) {
				// Choose whether to see future generation
				System.out.println("Do you want to see future generation if yes press 1 otherwise press 0");
				int pressNum = sc.nextInt();
				if (pressNum == 1) {
					grid.reshufflingOfCells();
					grid.printMatrix();
					flag = true;
					System.out.println("_______________________________");
				} else {
					flag = false;
				}
			}

			// Display current status of individual cell

			System.out.println("Enter the indices of cell to get current status ");
			int rowInd = sc.nextInt();
			int colInd = sc.nextInt();
			grid.currentStateOfCell(rowInd, colInd);

			// Handling Exceptions and giving proper message to user

		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println(e.getMessage() + " , please enter valid index");
		} catch (InputMismatchException e) {
			System.out.println("Please enter valid input");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
