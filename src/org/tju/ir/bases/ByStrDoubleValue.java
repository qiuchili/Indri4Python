package org.tju.ir.bases;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Comparator;

/**
 * The comparator sorted by double decrease
 *  Note that if two values are equal, return 1 default, namely the order follows the original order.
 * @author NewStart
 *
 */
public class ByStrDoubleValue implements Comparator<StrDoublePair>, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID=-2805284943658356093L;
	@Override
	public int compare(StrDoublePair arg0, StrDoublePair arg1) {
		Double x = arg0.value;
		Double y = arg1.value;
		int flag = y.compareTo(x);
		if(flag==0)
			flag = 1;
		return flag;
		// TODO Auto-generated method stub
	}
}
