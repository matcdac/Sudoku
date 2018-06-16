package sudoku.square;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GridPossibilities {
	
	private byte n;
	private Set<Byte[]> nDigitRowPossibilities;
	private Set<Byte[][]> gridPossibilities;
	
	private static Map<Byte, Set<Byte[][]>> possibilitiesOfRowsCombined = new HashMap<Byte, Set<Byte[][]>>();
	
	public GridPossibilities(byte n, Set<Byte[]> nDigitRowPossibilities) {
		this.n = n;
		this.nDigitRowPossibilities = nDigitRowPossibilities;
		gridPossibilities = new HashSet<Byte[][]>();
	}
	
	public Set<Byte[][]> getGridPossibilities() {
		return gridPossibilities;
	}
	
	public void generateGridPossibilities() {
		generateAllPossibilitiesOfNonRepeatingRowsDigits(n);
		gridPossibilities = possibilitiesOfRowsCombined.get((byte) n);
		possibilitiesOfRowsCombined.remove(1);
		possibilitiesOfRowsCombined.remove(n-1);
		possibilitiesOfRowsCombined.remove(n);
	}
	
	private void generateAllPossibilitiesOfNonRepeatingRowsDigits(byte x) {
		getAllForOneRow();
		System.out.println("When chosing 1 unique row at a time from " + nDigitRowPossibilities.size() + " possible rows, for making a valid grid of 1 row, there are " + possibilitiesOfRowsCombined.get((byte) 1).size() + " possibilities");
		for (byte i = 2; i <= x; i++) {
			getAllForIRow(i);
			//System.out.println("When chosing " + i + " unique rows at a time from " + nDigitRowPossibilities.size() + " possible rows, for making a valid grid of " + i + " rows, there are " + possibilitiesOfRowsCombined.get((byte) i).size() + " possibilities");
			
			/*
			if (i % Math.sqrt(n) == 0.0)
				performSmallerGridCheck(i);
			System.out.println("-> After " + "When chosing " + i + " unique rows at a time from " + nDigitRowPossibilities.size() + " possible rows, for making a valid grid of " + i + " rows, there are " + possibilitiesOfRowsCombined.get((byte) i).size() + " possibilities");
			*/
			
			performSmallerGridCheckTillNow(i);
			System.out.println("When chosing " + i + " unique rows at a time from " + nDigitRowPossibilities.size() + " possible rows, for making a valid grid with sub-grids of " + i + " rows, there are " + possibilitiesOfRowsCombined.get((byte) i).size() + " possibilities");
			
		}
	}

	private void getAllForIRow(byte i) {
		if (i != 3)
			possibilitiesOfRowsCombined.remove((byte) (i - 2));
		Set<Byte[][]> setOfIMinus1Row = possibilitiesOfRowsCombined.get((byte) (i - 1));
		Set<Byte[][]> setOfIRow = new HashSet<Byte[][]>();
		for (Byte[][] possibilityOfIMinus1 : setOfIMinus1Row) {
			for (Byte[][] rowPossibilityOf1 : possibilitiesOfRowsCombined.get((byte) 1)) {
				boolean repeatingRow = false;
				Byte[] rowOf1 = rowPossibilityOf1[0];
				for (Byte[] rowOfPossibilityOfIMinus1 : possibilityOfIMinus1) {
					if (anyDigitMatchesInBothRows(rowOf1, rowOfPossibilityOfIMinus1)) {
						repeatingRow = true;
						break;
					}
				}
				if (repeatingRow) {
					continue;
				} else {
					setOfIRow.add(addNewRowByAppending(rowOf1, possibilityOfIMinus1));
				}
			}
		}
		possibilitiesOfRowsCombined.put((byte) i, setOfIRow);
	}

	private boolean anyDigitMatchesInBothRows(Byte[] rowOf1, Byte[] rowOfPossibilityOfIMinus1) {
		for (int i = 0; i < rowOf1.length; i++)
			if (rowOf1[i] == rowOfPossibilityOfIMinus1[i])
				return true;
		return false;
	}
	
	private Byte[][] addNewRowByAppending(Byte[] rowOf1, Byte[][] possibilityOfIMinus1) {
		Byte[][] anotherPossibility = new Byte[possibilityOfIMinus1.length + 1][rowOf1.length];
		for (int i=0; i < possibilityOfIMinus1.length; i++)
			anotherPossibility[i] = possibilityOfIMinus1[i];
		anotherPossibility[possibilityOfIMinus1.length] = rowOf1;
		return anotherPossibility;
	}

	private void getAllForOneRow() {
		Set<Byte[][]> setOf1Row = new HashSet<Byte[][]>(n);
		for (Byte[] rowPossibility : nDigitRowPossibilities) {
			Byte[][] possibility = new Byte[][] { rowPossibility };
			setOf1Row.add(possibility);
		}
		possibilitiesOfRowsCombined.put((byte) 1, setOf1Row);
	}
	
	private void performSmallerGridCheck(byte i) {
		//System.out.println("-> Performing smaller grid check after generating grid possibilities of " + i + " rows at a time");
		byte fromGridRow = (byte) (i - (byte) Math.sqrt(n));
		byte tillGridRow = (byte) (i - 1);
		//System.out.println("-> from " + fromGridRow + " till " + tillGridRow);
		
		Set<Byte[][]> setOfIRow = possibilitiesOfRowsCombined.get((byte) i);
		
		Set<Byte[][]> setOfIRowToBeDiscarded = new HashSet<Byte[][]>();
		
		for (Byte[][] gridPossibility : setOfIRow) {
			//printIt(gridPossibility);
			
			boolean hasMoreCols = true;
			byte fromCol = 0;
			byte tillCol = (byte) (Math.sqrt(n) - 1);
			while (hasMoreCols) {
				
				Set<Byte> possibilities = new HashSet<Byte>();
				for (byte number = 1; number <= n; number++)
					possibilities.add(number);
				
				boolean disrupted = false;
				
				for (byte row = fromGridRow; row <= tillGridRow; row++) {
					for (byte col = fromCol; col <= tillCol; col++) {
						Byte value = gridPossibility[row][col];
						if (possibilities.contains(value)) {
							possibilities.remove(value);
						} else {
							//System.out.println("-> row " + row + " col " + col);
							setOfIRowToBeDiscarded.add(gridPossibility);
							//printIt(gridPossibility);
							disrupted = true;
							break;
						}
					}
					if (disrupted)
						break;
				}
				
				if (disrupted)
					break;
				
				if (n-1 == tillCol) {
					hasMoreCols = false;
					//System.out.println("");
				} else {
					fromCol = (byte) (tillCol + 1);
					tillCol = (byte) (tillCol + Math.sqrt(n));
				}
			}
		}
		
		//System.out.println("-> Removing " + setOfIRowToBeDiscarded.size() + " invalid possibilities due to sub grid");
		setOfIRow.removeAll(setOfIRowToBeDiscarded);
		
		possibilitiesOfRowsCombined.put((byte) i, setOfIRow);
		
	}
	
	private void performSmallerGridCheckTillNow(byte i) {
		
		//System.out.println("-> Performing smaller grid check after generating grid possibilities of " + i + " rows at a time");
		byte temp = 0;
		while (temp < i)
			temp = (byte) (temp + (byte) Math.sqrt(n));
		
		byte fromGridRow = (byte) (temp - (byte) Math.sqrt(n));
		byte tillGridRow = (byte) (i - 1);
		
		if (fromGridRow == tillGridRow)
			return;
		
		//System.out.println("-> from " + fromGridRow + " till " + tillGridRow);
		
		Set<Byte[][]> setOfIRow = possibilitiesOfRowsCombined.get((byte) i);
		
		Set<Byte[][]> setOfIRowToBeDiscarded = new HashSet<Byte[][]>();
		
		for (Byte[][] gridPossibility : setOfIRow) {
			//printIt(gridPossibility);
			
			boolean hasMoreCols = true;
			byte fromCol = 0;
			byte tillCol = (byte) (Math.sqrt(n) - 1);
			while (hasMoreCols) {
				
				Set<Byte> possibilities = new HashSet<Byte>();
				for (byte number = 1; number <= n; number++)
					possibilities.add(number);
				
				boolean disrupted = false;
				
				for (byte row = fromGridRow; row <= tillGridRow; row++) {
					for (byte col = fromCol; col <= tillCol; col++) {
						Byte value = gridPossibility[row][col];
						if (possibilities.contains(value)) {
							possibilities.remove(value);
						} else {
							//System.out.println("-> row " + row + " col " + col);
							setOfIRowToBeDiscarded.add(gridPossibility);
							//printIt(gridPossibility);
							disrupted = true;
							break;
						}
					}
					if (disrupted)
						break;
				}
				
				if (disrupted)
					break;
				
				if (n-1 == tillCol) {
					hasMoreCols = false;
					//System.out.println("");
				} else {
					fromCol = (byte) (tillCol + 1);
					tillCol = (byte) (tillCol + Math.sqrt(n));
				}
			}
		}
		
		//System.out.println("-> Removing " + setOfIRowToBeDiscarded.size() + " invalid possibilities due to sub grid");
		setOfIRow.removeAll(setOfIRowToBeDiscarded);
		
		possibilitiesOfRowsCombined.put((byte) i, setOfIRow);
		
	}

	private void printIt(Byte[][] gridPossibility) {
		for (Byte[] row : gridPossibility) {
			for (Byte element : row) {
				System.out.print(element + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
}
