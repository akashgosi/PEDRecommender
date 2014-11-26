package com.gosi.pedrecommeder;

import java.util.LinkedList;
import java.util.List;

public class SparqlQuery {
	
	String baseURI;
	List<String> variables;
	List<String> subjects;
	List<String> predicates;
	List<String> objects;
	List<String> filters;
	
	public SparqlQuery(String baseURI){
		this.baseURI  =baseURI;
		variables = new LinkedList<String>();
		subjects = new LinkedList<String>();
		predicates = new LinkedList<String>();
		objects = new LinkedList<String>();
		filters = new LinkedList<String>();
	}
	
	
	public SparqlQuery(String baseURI2, String variable, String subject, String predicate, String object, String filter) {
		// TODO Auto-generated constructor stub
		variables = new LinkedList<String>();
		subjects = new LinkedList<String>();
		predicates = new LinkedList<String>();
		objects = new LinkedList<String>();
		filters = new LinkedList<String>();
		baseURI=baseURI2;
		if(!variable.isEmpty()){
			variables.add(variable);
		}
		subjects.add(subject);
		predicates.add(predicate);
		objects.add(object);
		filters.add(filter);
	}
	
	
	
	public int addToQuery(String subject, String predicate, String object, String filter){

		subjects.add(subject);
		predicates.add(predicate);
		objects.add(object);
		filters.add(filter);
		
		return 0;
		
	}
	
	public int addToQuery(SparqlQuery newQuery){
		if(newQuery.variables.isEmpty()||newQuery.subjects.isEmpty()||newQuery.predicates.isEmpty()||newQuery.objects.isEmpty()){
			return 0;
		}
		if(newQuery.baseURI.equals(this.baseURI)){
			for(String variable:newQuery.variables){
				if(!variable.isEmpty())
					this.variables.add(variable);
			}
			for(String subject:newQuery.subjects){
				if(!subject.isEmpty())	
					this.subjects.add(subject);
			}
			for(String predicate:newQuery.predicates){
				if(!predicate.isEmpty())
					this.predicates.add(predicate);
			}
			for(String object:newQuery.objects){
				if(!object.isEmpty())
					this.objects.add(object);
			}
			for(String filter:newQuery.filters){
				if(!filter.isEmpty())
					this.filters.add(filter);
			}
			
			return 0;
		}
		
		return -1;
	}
	
	public int addToQueryVariable(String variable){
		if(!variable.isEmpty()){
			variables.add(variable);
		}
		
		return 0;
		
	}
	
	public String getQueryString(){
	
	
	String queryString = "SELECT";
	
	//Set the variables right after SELECT
	for(String variable: variables){
		queryString=queryString+" ?"+variable;
	}
	
	//Add the Where Clause
	queryString+=" WHERE { ";
	
	//Add the subject-predicate-object (triples), and filters
	
	for(int i=0;i<predicates.size();i++){
		queryString+=" ?"+subjects.get(i)+" ";
		queryString+=" <"+baseURI+predicates.get(i)+"> ";
		queryString+=" "+objects.get(i);
		
		try{
			if(filters.get(i).isEmpty()){
				queryString+=" . ";
			}
			
			else{
			queryString+=" . filter( "+filters.get(i)+" ) . ";
			}
		}
		catch (IndexOutOfBoundsException e){
			queryString+=" . ";
		}
	}
	
	queryString+=" } ";
	
	return queryString;
		
}
	
	//Convert the user requirements to a query string
	public static void getQueryString(List<UserRequirements> requirements){
		for(UserRequirements temp:requirements){
			
			String value=temp.getValue();
			String feature=temp.getFeature();
	
			try{
				Float.parseFloat(value);
				temp.featureQuery.addToQueryVariable("phone");
				temp.featureQuery.addToQueryVariable("phoneName");
				temp.featureQuery.addToQuery("phone",feature,"?"+feature,"?"+feature+" = "+value);
									
			}
			catch (NumberFormatException e){
				temp.featureQuery.addToQueryVariable("phone");
				
				System.out.println("Catch:"+value.charAt(0));
				switch(value.charAt(0)){
					
				case '<':
				case '>':
						 temp.featureQuery.addToQuery("phone",feature,"?"+feature,"?"+feature+value);
						 break; 
					
				default: temp.featureQuery.addToQuery("phone",feature,"\""+value+"\"","");
				
				}
				
			}
			
			
		}
	}

	public static String toTitleCase(String input) {
	    StringBuilder titleCase = new StringBuilder();
	    boolean nextTitleCase = true;

	    for (char c : input.toCharArray()) {
	        if (Character.isSpaceChar(c)) {
	            nextTitleCase = true;
	        } else if (nextTitleCase) {
	            c = Character.toTitleCase(c);
	            nextTitleCase = false;
	        }

	        titleCase.append(c);
	    }

	    return titleCase.toString();
	}
	

}
