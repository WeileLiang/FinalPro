package model;

import java.util.ArrayList;
import java.util.List;

/**
 * 代表一间工厂的资源
 * 
 * @author Will
 *
 */
public class Factory {

	public List<Jobshop> jobshops;
	public String name;

	public Factory(String name, List<Jobshop> jobshops) {
		this.name = name;
		this.jobshops = jobshops;
	}

	/**
	 * 获取第k间工厂的车间名字列表
	 * 
	 * @return
	 */
	public List<String> getJobshopNames() {
		List<String> names = new ArrayList<>();
		for (int i = 0; i < jobshops.size(); i++)
			names.add("车间" + (i + 1));

		return names;
	}

	public static class Jobshop {
		public List<Machine> machines;

		public Jobshop(List<Machine> machines) {
			// TODO Auto-generated constructor stub
			this.machines = machines;
		}

		public Object[][] getMchinedatas() {
			Object[][] datas = new Object[machines.size()+1][];
			datas[0]= new Object[]{"设备名称","设备类型","数量","状态"};
			for (int i = 0; i < machines.size(); i++) {
				datas[i+1]=machines.get(i).getData();
			}
			return datas;
		}
	}

	public static class Machine {
		public String name;
		public String type;
		public String count;
		public String state;

		public Machine(String name, String type, String count, String state) {
			this.name = name;
			this.type = type;
			this.count = count;
			this.state = state;
		}

		public Object[] getData() {
			return new Object[] { name, type, count, state };
		}
	}
}
