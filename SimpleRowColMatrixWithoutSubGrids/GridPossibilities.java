package sudoku.simple;

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
			System.out.println("When chosing " + i + " unique rows at a time from " + nDigitRowPossibilities.size() + " possible rows, for making a valid grid of " + i + " rows, there are " + possibilitiesOfRowsCombined.get((byte) i).size() + " possibilities");
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
	
}
