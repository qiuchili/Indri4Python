package org.tju.ir.bases;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Comparator;

/**
 * The comparator sorted by double in decreasing.
 *  Note that if two times are equal, return 1 default, namely the order follows the original order.
 * @author NewStart
 *
 */
public class ByDoubleDec implements Comparator<Double>, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID=-2805284943658356093L;
	@Override
	public int compare(Double arg0, Double arg1) {
		int flag = arg1.compareTo(arg0);
		if(flag==0)
			flag = 1;
		return flag;
		// TODO Auto-generated method stub
	}
}
