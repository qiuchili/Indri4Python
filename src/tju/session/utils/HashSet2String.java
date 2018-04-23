package tju.session.utils;

import java.util.HashSet;

public class HashSet2String {
	/**
	 * transfer hashset to string
	 * @param set
	 * @return
	 */
	public static String toString(HashSet<String> set){
		String str = "";
		for(String element:set){
			str += (element+",");
		}
		str = str.substring(0,str.length()-1);
		return str;
	}
}
