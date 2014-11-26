package com.gosi.pedrecommeder;

import java.util.Comparator;

public class TreeNodeHeuristicComparator implements Comparator<TreeNode> {

	public TreeNodeHeuristicComparator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(TreeNode node1, TreeNode node2) {
		// TODO Auto-generated method stub
		
		if(node1.heuristic < node2.heuristic)
			return -1;
		
		if(node1.heuristic > node2.heuristic)
			return 1;
		
		return 0;
	}

}
