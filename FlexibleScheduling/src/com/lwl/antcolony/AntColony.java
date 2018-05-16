package com.lwl.antcolony;

import java.util.ArrayList;
import java.util.List;

import com.lwl.resource.Resource;
import com.lwl.task.TaskGraph;

/**
 * рох╨
 * 
 * @author Will
 * 
 */
public class AntColony {

	private List<Ant> ants;
	private TaskGraph graph;
	private Resource resource;
	private int antsCount;

	public AntColony(TaskGraph graph, Resource resource) {
		this.graph = graph;
		this.resource = resource;

		antsCount = graph.getTotalStepCount();
		ants = new ArrayList<Ant>();
		for (int i = 0; i < antsCount; i++) {
			ants.add(new Ant(graph, (Resource) resource.clone()));
		}
	}
	
	public int getAntsCount() {
		return antsCount;
	}
	
	public Ant getKthAnt(int k){
		return ants.get(k);
	}
}
