package com.gosi.pedrecommeder;

public class UserRequirements {

	String feature;
	String value;
	SparqlQuery featureQuery;
	boolean seenFlag;
	String baseURI;
	public UserRequirements() {
		// TODO Auto-generated constructor stub
		feature = null;
		value = null;
		featureQuery = null;
		seenFlag=false;
	}
	
	public UserRequirements(String inputFeature, String inputValue, String baseURI){
		
		feature = inputFeature;
		value = inputValue;
		this.baseURI = baseURI;
		featureQuery = new SparqlQuery(baseURI);
		
	}
	
	
	public boolean isEqualTo(UserRequirements otherUR){
		if(otherUR.feature.equals(this.feature))
			if(otherUR.value.equals(this.value))
				return true;
		
		return true;
	}
	
	public String getValue(){
		return value;
		
	}
	
	public String getFeature(){
		return feature;
		
	}
}
