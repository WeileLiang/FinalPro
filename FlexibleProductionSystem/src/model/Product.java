package model;

import java.util.List;

public class Product {
	public String name;
	public List<String> parts;
	//对应工件的数量
	public List<Integer> counts;
	
	public Product(String name, List<String> parts,List<Integer> counts) {
		this.name = name;
		this.parts = parts;
		this.counts=counts;
	}

	/**
	 * 工件
	 * 
	 * @author Will
	 *
	 */
	public static class Part {
		public String name;
		public List<Procedure> procedures;

		public Part(String name, List<Procedure> procedures) {
			this.name = name;
			this.procedures = procedures;
		}
	}

	/**
	 * 工序
	 * 
	 * @author Will
	 *
	 */
	public static class Procedure {
		public String name;
		public List<String> times;

		public Procedure(String name, List<String> times) {
			this.name = name;
			this.times = times;
		}
	}
}
