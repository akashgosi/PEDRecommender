package com.gosi.pedrecommeder;

import java.util.LinkedList;
import java.util.List;

public class TreeNode {
	
	String identifier;
	LinkedList<String> children;
	SparqlQuery nodeQuery;
	Float heuristic;
	Integer statementCount;
	String baseURI;
	List<String> queryStrings;
	LinkedList<UserRequirements> fulfilledRequirements ;
	public TreeNode() {
		// TODO Auto-generated constructor stub
	}
	
	public TreeNode(String baseURI, String identifier){
		this.baseURI = baseURI;
		this.identifier=identifier;
		children = new LinkedList<String>();
		heuristic = (float) 999;
		statementCount = 0;
		this.nodeQuery = new SparqlQuery(baseURI);
		fulfilledRequirements = new LinkedList<UserRequirements>();
	}
	
	public TreeNode(String baseURI, String identifier, SparqlQuery nodeQuery){
		this.baseURI = baseURI;
		this.identifier=identifier;
		children = new LinkedList<String>();
		heuristic = (float) 999;
		statementCount = 0;
		this.nodeQuery = nodeQuery;
		fulfilledRequirements = new LinkedList<UserRequirements>();		
	}
	
	public TreeNode(String baseURI, String identifier, SparqlQuery nodeQuery,LinkedList<UserRequirements> requirements){
		this.baseURI = baseURI;
		this.identifier=identifier;
		children = new LinkedList<String>();
		heuristic = (float) 999;
		statementCount = 0;
		this.nodeQuery = nodeQuery;
		fulfilledRequirements = requirements;		
	}
	
	public void addToFulfilledRequirements(UserRequirements newRequirement){
		fulfilledRequirements.add(newRequirement);
	}
	
	public void setHeuristic(Float heuristic){
		this.heuristic = heuristic;
	}
	
	public void setNodeQuery(SparqlQuery nodeQuery){
		this.nodeQuery = nodeQuery;
	}
	
	public String getIdentifer(){
		return identifier;
	}
	
	public LinkedList<String> getChildren(){
		return children;
	}
	
	public void addChild(String identifier){
		children.add(identifier);
	}
	
	public int getChildrenCount(){
		return children.size();
		
	}
	
	public void setStatementCount(Integer count){
		statementCount = count;
		return;
	}
	
	public Integer getStatementCount(){
		return statementCount;
	}
		
}
