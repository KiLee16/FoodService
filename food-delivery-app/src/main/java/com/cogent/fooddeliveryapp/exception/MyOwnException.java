package com.cogent.fooddeliveryapp.exception;

/**
 * @author : Ki Beom Lee
 * @time : 2022. 2. 21.-���� 11:42:16
 */
public class MyOwnException extends RuntimeException {
	public MyOwnException(String e) {
		// TODO Auto-generated constructor stub
		super(e);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.getMessage();
	}

}
