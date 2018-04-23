package org.tju.ir.bases;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Comparator;

/**
 * sorted by alpha and beta
 * @author NewStart
 *
 */
public class ByTripleDDD implements Comparator<TripleDDD>, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID=-2805284943658356093L;
	
	public int compare(TripleDDD arg0, TripleDDD arg1) {
		Double alpha1 = arg0.alpha;
		Double alpha2 = arg1.alpha;
		Double beta1 = arg0.beta;
		Double beta2 = arg1.beta;
		int flag = alpha1.compareTo(alpha2);
		
		if(flag==0)
			flag = beta1.compareTo(beta2);
		if(flag==0)
			flag=-1;
		return flag;
		// TODO Auto-generated method stub
	}
}
