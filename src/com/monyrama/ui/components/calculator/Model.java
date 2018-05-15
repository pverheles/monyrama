package com.monyrama.ui.components.calculator;

class Model {
	private String numOne;
	private String numTwo;
	private char operation;

	public Model() {
		reset();	
	}

	public String getNumOne() {
		return numOne;
	}

	public void setNumOne(String numOne) {
		this.numOne = theValue(numOne);		
	}

	public String getNumTwo() {
		return numTwo;
	}

	public void setNumTwo(String numTwo) {
		this.numTwo = theValue(numTwo);
	}

	public char getOperation() {
		return operation;
	}

	public void setOperation(char operation) {
		this.operation = operation;
	}
	
	public void appendNumOne(char key) {
		numOne = appendNumber(numOne, key);
	}
	
	public void appendNumTwo(char key) {
		numTwo = appendNumber(numTwo, key);
	}	
	
	public void removeLastSymbolInNumOne() {
		numOne = removeLastSymbol(numOne);
	}
	
	public void removeLastSymbolInNumTwo() {
		numTwo = removeLastSymbol(numTwo);
	}		
		
	private String appendNumber(String number, char key) {
		String value;
		if(number.equals("0")) {
			value = String.valueOf(key);
		} else if(number.contains(".")) {
			if(key != '.') {
				value = number + key;
			} else {
				value = number;
			}
		} else {
			value = number + key;
		}
		
		return value;
	}
	
	private String removeLastSymbol(String number) {
		String value = "0";
		if(number.length() > 1) {
			value = number.substring(0, number.length() - 1);
		}
		return value;
	}
	
	private String theValue(String value) {
		String theValue;
		if(value.equals(".")) {
			theValue = "0" + ".";
		} else {
			theValue = value;	
		}	
		
		return theValue;
	}
	
	public void reset() {
		numOne = "0";
		numTwo = "0";
	}	
	
}
