package org.tju.ir.bases;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Comparator;

/**
 * The comparator sorted by rank
 *  Note that if two values are equal, return 1 default, namely the order follows the original order.
 * @author NewStart
 *
 */
public class ByStrIntValue implements Comparator<StrIntPair>, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID=-2805284943658356093L;
	@Override
	public int compare(StrIntPair arg0, StrIntPair arg1) {
		Integer x = arg0.getValue();
		Integer y = arg1.getValue();
		int flag = x.compareTo(y);
		if(flag==0)
			flag = 1;
		return flag;
		// TODO Auto-generated method stub
	}
}
