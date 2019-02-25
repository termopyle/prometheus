package org.kobic.omics.viewer.com;

public class Utilities {
	public static boolean isNumeric(String string) throws IllegalArgumentException {
		boolean isNumeric = false;

		if (string != null && !string.equals("")) {
			isNumeric = true;
			char chars[] = string.toCharArray();

			for(int d = 0; d < chars.length; d++)	{
				isNumeric &= Character.isDigit(chars[d]);

				if( !isNumeric )	break;
			}
		}
		return isNumeric;
	}
	
	public static boolean isNumericWithExp(String str) {
		try {
			Double.parseDouble(str);
		} catch(NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}
