package sudoku.square;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RowPossibilities {
	
	private byte n;
	private Set<Byte[]> rowPossibilities;
	
	private static Map<Byte, Set<Byte[]>> possibilitiesOfDigitsCombined = new HashMap<Byte, Set<Byte[]>>();
	
	public RowPossibilities(byte n) {
		this.n = n;
		rowPossibilities = new HashSet<Byte[]>();
	}
	
	public Set<Byte[]> getRowPossibilities() {
		return rowPossibilities;
	}

	public void generateRowPossibilities() {
		generateAllPossibilitiesOfNonRepeatingDigits(n);
		rowPossibilities = possibilitiesOfDigitsCombined.get((byte) n);
		possibilitiesOfDigitsCombined.remove(n-1);
		possibilitiesOfDigitsCombined.remove(n);
	}

	private void generateAllPossibilitiesOfNonRepeatingDigits(byte x) {
		getAllForOneDigit();
		System.out.println("When chosing 1 unique element at a time from [1-" + x + "], for making a valid row of 1 element, there are " + possibilitiesOfDigitsCombined.get((byte) 1).size() + " possibilities");
		for (byte i = 2; i <= x; i++) {
			getAllForIDigit(i);
			System.out.println("When chosing " + i + " unique elements at a time from [1-" + x + "], for making a valid row of " + i + " elements, there are " + possibilitiesOfDigitsCombined.get((byte) i).size() + " possibilities");
		}
	}

	private void getAllForIDigit(byte i) {
		possibilitiesOfDigitsCombined.remove((byte) (i - 2));
		Set<Byte[]> setOfIMinus1Digit = possibilitiesOfDigitsCombined.get((byte) (i - 1));
		Set<Byte[]> setOfIDigit = new HashSet<Byte[]>(i * (i-1));
		for (Byte[] possibilityOfIMinus1 : setOfIMinus1Digit) {
			for (byte x = 1; x <= n; x++) {
				boolean repeatingDigit = false;
				for (Byte digitOfPossibilityOfIMinus1 : possibilityOfIMinus1) {
					if (digitOfPossibilityOfIMinus1 == x) {
						repeatingDigit = true;
						break;
					}
				}
				if (repeatingDigit) {
					continue;
				} else {
					Byte[] possibilityOfI = new Byte[i];
					for (byte y = 0; y < i-1; y++) {
						possibilityOfI[y] = possibilityOfIMinus1[y];
					}
					possibilityOfI[i-1] = x;
					setOfIDigit.add(possibilityOfI);
					//System.out.println("-> Size of " + i + " digits right now is " + setOfIDigit.size());
				}
			}
		}
		possibilitiesOfDigitsCombined.put((byte) i, setOfIDigit);
	}

	private void getAllForOneDigit() {
		Set<Byte[]> setOf1Digit = new HashSet<Byte[]>(n);
		for (byte i = 1; i <= n; i++) {
			Byte[] possibility = new Byte[] { i };
			setOf1Digit.add(possibility);
		}
		possibilitiesOfDigitsCombined.put((byte) 1, setOf1Digit);
	}
	
}
