package com.gosi.pedrecommeder;

import java.util.HashMap;

public class Tree {
	
	private String baseURI;
	//private final static int ROOT = 0;
	private static Integer nodeCount;
	private HashMap<String, TreeNode> nodes;
	public Tree(String baseURI) {
		// TODO Auto-generated constructor stub
		this.baseURI = baseURI;
		this.nodes = new HashMap<String,TreeNode>();
		nodeCount=0;
	}
	
	public HashMap<String, TreeNode> getNodes() {
		return nodes;
	}
	
	public TreeNode addNode(String identifier){
		return this.addNode(identifier, null);
	}
	
	public TreeNode addNode(String identifier, String parent){
		TreeNode node = new TreeNode(baseURI,identifier);
		nodes.put(identifier, node);
		
		if(parent !=null){
			nodes.get(parent).addChild(identifier);
		}
		
		nodeCount++;
		
		return node;
	}
	
	
}
