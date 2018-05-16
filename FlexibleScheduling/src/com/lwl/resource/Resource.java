package com.lwl.resource;

import java.util.ArrayList;
import java.util.List;

public class Resource implements Cloneable {

	private List<Machine> machines;

	public Resource(List<Machine> machines) {
		this.machines = machines;
	}

	public List<Machine> getMachines() {
		return machines;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder sb=new StringBuilder();
		for (int i = 1; i < machines.size(); i++) {
			sb.append(machines.get(i).toString()).append('\n');
		}
		return sb.toString();
	}
	
	@Override
	public Object clone() {
		// TODO Auto-generated method stub
		Resource resource = null;

		try {
			resource = (Resource) super.clone();
			resource.machines = new ArrayList<Machine>();
			for (int i = 0; i < machines.size(); i++)
				resource.machines.add((Machine) machines.get(i).clone());
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resource;
	}
}
