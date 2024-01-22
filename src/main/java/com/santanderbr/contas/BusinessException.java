package com.santanderbr.contas;

public class BusinessException extends Exception {
    // default constructor 
	BusinessException() {    } 
  
    // parameterized constructor 
	public BusinessException(String str) { super(str); } 
  
}
