package tju.session.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class KLDivergence {
	/**
	 * get the negative KL divergence between two language models, e.g.,lm1 is the query language model, 
	 * lm2 is the document language model
	 * @param lm1
	 * @param lm2
	 * @return -1 means a illegal value
	 */
	public static Double getNKLDivergence(Map<Integer,Double> lm1,Map<Integer, Double> lm2){
		//klScore = sum(queryLM.*log(queryLM./docLM));
		
		if(lm1==null || lm2==null || lm1.size()==0 || lm2.size()==0)
			return null;
		Double divergence = 0d;
		for(Entry<Integer,Double> entry:lm1.entrySet()){
			Integer termID = entry.getKey();
			Double prob1 = entry.getValue();
			Double prob2 = lm2.get(termID);
			if(prob1==null||prob2==null||prob2==0)
				continue;
			divergence += prob1*Math.log(prob1/prob2);
		}
		return -divergence;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map<Integer,Double> lm1 = new HashMap<Integer,Double>();
		Map<Integer,Double> lm2 = new HashMap<Integer,Double>();
		lm1.put(1, 0.2);
		lm1.put(2, 0.8);
		lm2.put(1, 0.15);
		lm2.put(2, 0.85);
		System.out.println(getNKLDivergence(lm1, lm2));
	}

}
