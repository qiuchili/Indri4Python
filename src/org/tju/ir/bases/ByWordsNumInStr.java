package org.tju.ir.bases;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Comparator;

public class ByWordsNumInStr implements Comparator<String>, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID=-2805284943658356093L;
	@Override
	public int compare(String arg0, String arg1) {
		String[] words0 = arg0.split(" ");
		String[] words1 = arg1.split(" ");
		Integer size0 = words0.length;
		Integer size1 = words1.length;
		int flag = size0.compareTo(size1);
		if(flag==0)
			flag = 1;
		return flag;
		// TODO Auto-generated method stub
	}
}
