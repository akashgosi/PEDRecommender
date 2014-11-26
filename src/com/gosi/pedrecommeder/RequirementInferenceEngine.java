package com.gosi.pedrecommeder;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasonerFactory;
import com.hp.hpl.jena.util.PrintUtil;
import com.hp.hpl.jena.vocabulary.ReasonerVocabulary;

public class RequirementInferenceEngine {
	
	OntModel model;
	String baseURI;
	
	public RequirementInferenceEngine(OntModel model, String baseURI) {
		// TODO Auto-generated constructor stub
		this.model = model;
		this.baseURI = baseURI;
		
	}
	
	public OntModel getInferredStatements(){
		
		PrintUtil.registerPrefix("baseURI", baseURI);

		// Create an (RDF) specification of a hybrid reasoner which
		// loads its data from an external file.
		Model m = ModelFactory.createDefaultModel();
		Resource configuration =  m.createResource();
		configuration.addProperty(ReasonerVocabulary.PROPruleMode, "hybrid");
		configuration.addProperty(ReasonerVocabulary.PROPruleSet,  "PEDRecommender.rules");


		// Create an instance of such a reasoner
		Reasoner reasoner = GenericRuleReasonerFactory.theInstance().create(configuration);


		// Load test data
		//Model data = FileManager.get().loadModel("PEDRecommender.owl");
		InfModel infmodel = ModelFactory.createInfModel(reasoner, model);
		
		OntModel newInfmodel = ModelFactory.createOntologyModel( OntModelSpec.RDFS_MEM, infmodel );
		//newInfmodel.write(System.out);
		return newInfmodel;
	}
	
	
}
