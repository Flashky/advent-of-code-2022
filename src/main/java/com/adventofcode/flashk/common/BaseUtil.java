package com.adventofcode.flashk.common;

import java.math.BigInteger;

public final class BaseUtil {

	private BaseUtil() {}
	
	public static Integer binaryToDecInteger(String bin) {
	    return Integer.parseInt(bin, 2);
	}
	
	public static Long binaryToDec(String bin) {
	    return Long.parseLong(bin, 2);
	}
	
	/**
	 * Converts from hexadecimal to binary.
	 * <p>
	 * Any hexadecimal number can be represented with 4 binary digits (<code>6 hex = 0110 bin</code>).
	 * </p>
	 * <p>
	 * Leading zero digits won't be included in the converted number.
	 * Use {@link #hexToBinaryPadLeft(String)} if they need to be included.
	 * </p>
	 * <p>
	 * 	<b>Examples:</b>
	 * </p>
	 * <pre>
	 * 	BaseUtil.hexToBinary("6") = "110"
	 * </pre>
	 * @param hex
	 * @return
	 * @see #hexToBinaryPadLeft(String)
	 */
	public static String hexToBinary(String hex) {
	    return new BigInteger(hex, 16).toString(2);
	}
	
	/**
	 * Converts from hexadecimal to binary adding zero padding at the leading.
	 * <p>
	 * Any hexadecimal number can be represented with 4 binary digits (<code>6 hex = 0110 bin</code>).
	 * </p>
	 * </p>
	 * Leading zero digits will be included in the converted number.
	 * Use {@link #hexToBinary(String)} if they need to be removed from the result.
	 * </p>
	 * <p>
	 * 	<b>Examples:</b> 
	 * </p>
	 * <pre>
	 * 	BaseUtil.hexToBinaryPadLeft("6") = "0110"
	 * </pre>
	 * @param hex
	 * @return
	 * @see #hexToBinary(String)
	 */
	public static String hexToBinaryPadLeft(String hex) {
	    String unpaddedBinary = BaseUtil.hexToBinary(hex);
	    int size = hex.length() * 4;
	    return String.format("%"+size+"s", unpaddedBinary).replace(" ", "0");
	}
}
