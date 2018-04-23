package tju.session.utils;

import java.util.HashSet;

public class JaccardIndex {
	/**
	 * get the jaccard index
	 * @param set_1
	 * @param set_2
	 * @return
	 */
	public static double getJaccardIndex(HashSet<String> set_1,HashSet<String> set_2){
		if(set_1 == null || set_2 == null || set_1.size()==0 || set_2.size() == 0)
			return 0;
		HashSet<String> unionSet = new HashSet<String>();
		HashSet<String> intersection = new HashSet<String>();
		unionSet.addAll(set_1);
		unionSet.addAll(set_2);
		int unionSize = unionSet.size();
		intersection.addAll(set_1);
		intersection.retainAll(set_2);
		int interSize = intersection.size();
		if(unionSize == 0 || interSize == 0)
			return 0;
		return (double)interSize/(double)unionSize;
	}
}
