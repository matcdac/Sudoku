package sudoku.square;

public class Generate {
	
	// should be perfect square
	private static byte gridSize = 9;
	
	public static void main(String[] args) {
		start();
	}

	private static void start() {
		if (perfectSquareCheck())
			proceed();
		else
			System.out.println(gridSize + " is not a valid perfect square");
	}

	private static boolean perfectSquareCheck() {
		int sqrt = (int) Math.sqrt(gridSize);
		return sqrt*sqrt == gridSize;
	}

	private static void proceed() {
		System.out.println(gridSize + " x " + gridSize);
		System.out.println();
		
		RowPossibilities rowPossibilities = new RowPossibilities(gridSize);
		rowPossibilities.generateRowPossibilities();
		
		/*System.out.println();
		System.out.println("Row Possibilities : ");
		for (Byte[] rowPossibility : rowPossibilities.getRowPossibilities()) {
			for (Byte digit : rowPossibility) {
				System.out.print(digit + " ");
			}
			System.out.println();
		}*/
		
		System.out.println();
		
		GridPossibilities gridPossibilities = new GridPossibilities(gridSize, rowPossibilities.getRowPossibilities());
		gridPossibilities.generateGridPossibilities();
		
		System.out.println();
		
		//printPossibleSudokus(gridPossibilities);
	}

	private static void printPossibleSudokus(GridPossibilities gridPossibilities) {
		System.out.println("-----------------------------------------\n");
		System.out.println("All Possibilities :\n");
		for (Byte[][] gridPossibility : gridPossibilities.getGridPossibilities()) {
			for (Byte[] row : gridPossibility) {
				for (Byte element : row) {
					System.out.print(element + " ");
				}
				System.out.println();
			}
			System.out.println();
		}
	}
	
}
