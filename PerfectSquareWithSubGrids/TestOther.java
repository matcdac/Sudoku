package sudoku.square;

public class TestOther {

	public static void main(String[] args) {
		//long decimal = convertBiggestNumberWithUniqueDigitsOfBaseNToBase10((short) 9);
		convertBase10ToBase2(987654321);
		
		int number = 987654321;
		System.out.println(number);
		
	}
	
	private static long convertBiggestNumberWithUniqueDigitsOfBaseNToBase10(short n) {
		long decimal = 0;
		System.out.print("Base " + n + "  : ");
		for (short number = (short) (n-1); number > -1; number--) {
		//for (short number = (short) (n); number > 0; number--) {
			System.out.print(number);
			decimal = (long) (decimal + Math.pow(n, number) * number);
		}
		System.out.print("\nBase 10 : ");
		System.out.println(decimal);
		return decimal;
	}
	
	private static long convertBase10ToBase2Long(long decimal) {
		System.out.print("Base " + 2 + "  : ");
		long binary = 0;
		short digitsHoChukeH = 0;
		while (decimal > 0) {
			byte rem = (byte) (decimal % 2);
			binary += (long) (rem * Math.pow(10, digitsHoChukeH));
			digitsHoChukeH++;
			decimal /= 2;
		}
		System.out.println(binary);
		return binary;
	}
	
	private static String convertBase10ToBase2(long decimal) {
		System.out.print("Base " + 2 + "  : ");
		String binary = new String();
		byte rem = 0;
		while (decimal > 0) {
			rem = (byte) (decimal % 2);
			binary = rem + binary;
			decimal /= 2;
		}
		System.out.println(binary);
		System.out.println(binary.length());
		return binary;
	}

}
