package tju.session.utils;

import java.math.BigDecimal;

public class DoubleRoundUp {
	public static int DecimalPlaces = 4;
	/**
	 * round up a double value with k decimal places
	 * @param val
	 * @param k
	 * @return
	 */
	public static double roundUP(Double val,int k){
		String strVal = Double.toString(val);
		BigDecimal bigDecimal = new BigDecimal(strVal);
		bigDecimal = bigDecimal.setScale(k, BigDecimal.ROUND_HALF_UP);
		return bigDecimal.doubleValue();
	}
	/**
	 * round up a double value with default decimal places
	 * @param val
	 * @return
	 */
	public static double roundUP(Double val){
		String strVal = Double.toString(val);
		BigDecimal bigDecimal = new BigDecimal(strVal);
		bigDecimal = bigDecimal.setScale(DecimalPlaces, BigDecimal.ROUND_HALF_UP);
		return bigDecimal.doubleValue();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Double   abc   =   4.01456;   //4.025 
		System.out.println(roundUP(abc, 2));
	}
}

