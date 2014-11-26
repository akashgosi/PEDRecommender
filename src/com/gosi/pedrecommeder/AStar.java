package com.gosi.pedrecommeder;

import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.PriorityQueue;
import java.util.Stack;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;

public class AStar {
	
	Comparator<TreeNode> comparator;
	PriorityQueue<TreeNode> priorityQueue; 
	Stack<TreeNode> path;
	OntModel model;
	String baseURI;
	List<UserRequirements> requirements;
	private static final Float MAX_HEURISTIC = (float) 999;
	public AStar(List<UserRequirements> requirements,OntModel model, String baseURI) {
		
		comparator = new TreeNodeHeuristicComparator();
		priorityQueue = new PriorityQueue<TreeNode>(25, comparator); 
		path = new Stack<TreeNode>();
		this.requirements = requirements;
		this.model = model;
		this.baseURI = baseURI;
		
	}
	
	public String runAStar(){
		
		//??? Still have to handle nodes with same heuristic....
		//??? The child node is not always expanded first..is that ok?
		
		TreeNode root = new TreeNode(baseURI,"root", new SparqlQuery(baseURI));
		//expandAt(root);
		TreeNode currentNode = null;
		priorityQueue.add(root);
		while(!priorityQueue.isEmpty() && !requirements.isEmpty() &&!requirements.isEmpty()){
			System.out.println("runAStar: first loop");
			//Pop the highest priority node
			currentNode = priorityQueue.remove();
			
			//Get the requirements that were fulfilled by the node and remove it from requirements
			ListIterator<UserRequirements> requirementsItr = requirements.listIterator();
			ListIterator<UserRequirements> fulfilledRequirementsItr = currentNode.fulfilledRequirements.listIterator();
			while(fulfilledRequirementsItr.hasNext()){
				UserRequirements nodeRequirement = fulfilledRequirementsItr.next();
				while(requirementsItr.hasNext()){
					UserRequirements classRequirement = requirementsItr.next();
					if(nodeRequirement.isEqualTo(classRequirement)){
						System.out.println("In equals");
						requirements.remove(classRequirement);
					}
				}
			}
			
			expandAt(currentNode);
		}
		
		System.out.println("runAStar: Out of loop");
		try{
			
			return currentNode.nodeQuery.getQueryString();
		}
		
		//If there are no nodes that satisfy the requirements
		catch(NullPointerException e){
			return null;
		}
	}
	
	public void expandAt(TreeNode node){
		
		
		//For each user requirement query string, create a new child node
		
		ListIterator<UserRequirements> requirementsItr = requirements.listIterator();
		while(requirementsItr.hasNext()){
			UserRequirements requirement = requirementsItr.next();
			
			SparqlQuery aQuery = requirement.featureQuery;
			SparqlQuery temp = new SparqlQuery(baseURI);
			
			//Add the query in the current node
			temp.addToQuery(node.nodeQuery);
			
			//Add the one of the requirement queries
			temp.addToQuery(aQuery);
			
			//Create the node
			//????
			TreeNode newNode = new TreeNode(baseURI,node.identifier+"_"+requirement.feature,temp,node.fulfilledRequirements);
			
			//Add the requirement that was fulfilled
			newNode.addToFulfilledRequirements(requirement);
			
			//Get the heuristic for the new node
			Float newHeuristic = getHeuristic(newNode);
			//Add the node to the parent
			node.addChild(node.identifier+"_"+requirement.feature);
			if(newHeuristic!=MAX_HEURISTIC){
				priorityQueue.add(newNode);
			}
		
		}	
	}
	
	public Float getHeuristic(TreeNode node){
		Float heuristic = (float) 0;
		String queryString;
		
		queryString = node.nodeQuery.getQueryString();
		System.out.println("getHeuristic:\"queryStrng\"="+queryString);
		Query query = QueryFactory.create(queryString);
		
		try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
			
			ResultSet results = qexec.execSelect() ;
			//Output Result in table format
		   // ResultSetFormatter.out(System.out, results, query);
		    
			//Get the number of results
			while (results.hasNext()){
				results.next();
				heuristic++;
			}
			
			//If there are no results, make the heuristic max
			if(heuristic==0){
				heuristic= MAX_HEURISTIC;
			}
		  }
		
		node.setHeuristic(heuristic);
		System.out.println("getHeuristic:\"heuristic:\" "+heuristic.toString());
		return heuristic;
	}
	

}
