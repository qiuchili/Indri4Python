package org.tju.ir.bases;

import java.io.Serializable;

public class StrIntPair implements Serializable{
	private static final long serialVersionUID = -2805284943658356093L;
	private String key;
	private Integer value;
	public StrIntPair(String key,Integer value){
		setKey(key);
		setValue(value);
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	
}
