package tju.session.utils;

import java.util.List;

public class ListUtil {
	/**
	 * return a string from a list, separated by 
	 * @param list
	 * @return
	 */
	public static String listDouble2String(List<Double> list, char separator){
		if(list == null || list.size()==0)
			return null;
		String str = String.valueOf(list.get(0));
		for(int i=1;i<list.size();i++){
			str += (separator+String.valueOf(list.get(i)));
		}
		return str;
	}
	/**
	 * return a string from a list, separated by 
	 * @param list
	 * @return
	 */
	public static String listStr2String(List<String> list, char separator){
		if(list == null || list.size()==0)
			return null;
		String str = String.valueOf(list.get(0));
		for(int i=1;i<list.size();i++){
			str += (separator+String.valueOf(list.get(i)));
		}
		return str;
	}
	
	/**
	 * get the mean and the variance of the list of double number<br>
	 * return an array, where array[0] is mean, array[1] is the variance.
	 * @param list
	 * @return
	 */
	public static double[] getMeanVariance(List<Double> list){
		if(list==null||list.size()==0)
			return null;
		double sum = 0;
		double mean = 0;
		double variance = 0;
		double squareSum = 0;
		int num = list.size();
		for(Double ele:list){
			sum += ele;
		}
		mean = sum/num;
		for(Double ele:list){
			squareSum += Math.pow(ele-mean, 2);
		}
		variance = (Math.sqrt(squareSum))/sum;
		double[] array = {mean,variance};
		return array;
	}
	
}
