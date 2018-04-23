package org.tju.ir.bases;

import java.io.Serializable;

public class StrDoublePair implements Serializable{
	private static final long serialVersionUID = -2805284943658356093L;
	public String key;
	public Double value;
	public StrDoublePair(String key,Double value){
		this.key = key;
		this.value = value;
	}
}
