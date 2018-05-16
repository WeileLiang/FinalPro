package com.lwl.resource;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class Machine implements Cloneable {

	// 设备的加工序列
	private PriorityQueue<TimeChip> chips;
	// 设备的id，例如设备1的id就是1
	private int id;

	public Machine(int id, List<TimeChip> chips) {
		this.id = id;
		this.chips = new PriorityQueue<TimeChip>(chips);
	}

	public PriorityQueue<TimeChip> getChips() {
		return chips;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder("M" + id + ":");
		Iterator<TimeChip> iterator=chips.iterator();
		int i=0;
		while (iterator.hasNext()) {
			if(i==0){
				sb.append(iterator.next());
				i++;
			}else {
				sb.append("->").append(iterator.next());
			}
		}
		
		return sb.toString();
	}

	@Override
	protected Object clone() {
		// TODO Auto-generated method stub
		Machine machine = null;
		try {
			machine = (Machine) super.clone();
			machine.chips = new PriorityQueue<TimeChip>();
			Iterator<TimeChip> iterator=chips.iterator();
			while (iterator.hasNext()) {
				machine.chips.offer((TimeChip) iterator.next().clone());
			}
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return machine;
	}
}
