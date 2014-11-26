package com.gosi.pedrecommeder;



import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.util.FileManager;

public class Main {

	public Main() {
		// TODO Auto-generated constructor stub
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//RDF prefix
		String baseURI = "http://www.semanticweb.org/akash/ontologies/2014/10/PEDRecommender#";
		
		 // create an empty model to store the RDF model
		 OntModel model= ModelFactory.createOntologyModel(); 
		 String inputFileName = "PEDRecommender_RDF_XML.owl";
		 
		// use the FileManager to find the input file
		 InputStream in = FileManager.get().open( inputFileName  );
		if (in == null) {
		    throw new IllegalArgumentException("File: " + inputFileName + " not found");
		}
		
		//Read the file into the model
		model.read(in, null);
		
		//Read the user input
		System.out.println("Enter you requirements:\n");
		List<UserRequirements> requirements = new LinkedList<UserRequirements>();
		Scanner scannerInput = new Scanner (System.in);
		while(!scannerInput.hasNext(";")){
			
			
			String temp1=null;
			String temp2=null;
			temp1=scannerInput.next();
			temp2=scannerInput.next();
			requirements.add(new UserRequirements(temp2,temp1,baseURI));
			
		}
		scannerInput.close();

	
		//Set the query object for each requirement
		SparqlQuery.getQueryString(requirements);
		
		//Get new information from the inferences
		RequirementInferenceEngine inference = new RequirementInferenceEngine(model, baseURI);
		model = inference.getInferredStatements();
		//model.write(System.out);
		//System.out.println(model.toString());
		AStar search = new AStar(requirements, model, baseURI);
		String functionString = null;
		try{
			
			//Execute the query in SPARQL
			functionString = search.runAStar();
			
			System.out.println("FunctionQuery: "+functionString);
			if(functionString==null){
				throw new NoPhoneFoundException("No phones that satisfy all the requirements");
			}
			Query query = QueryFactory.create(functionString);
			  try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
			    ResultSet results = qexec.execSelect() ;
			    
			    //Output Result in table format
			    //ResultSetFormatter.out(System.out, results, query);
			   
			    System.out.println("\nThe phones that match the Criteria:");
			    for ( ; results.hasNext() ; )
			    {
			      
			      QuerySolution soln = results.nextSolution() ;
			      RDFNode l = soln.getResource("phone") ; 
			      System.out.println(l.toString().split("\\^\\^")[0] );
			   		      
			    }
			  }
		}
		catch (Exception e){
			System.out.println("PEDRecommender: "+e.getMessage());
			e.printStackTrace(System.out);
			
		}
		
		
	}

}
