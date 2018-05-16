package com.lwl.resource;

import com.lwl.task.Step;

/**
 * �豸�����ڼӹ�ĳ�������ʱ��Ƭ
 * @author Will
 *
 */
public class TimeChip implements Cloneable,Comparable<TimeChip>{

	//��ʼ�ӹ�ʱ��
	private double start;
	//�ӹ�����ʱ��
	private double end;
	//���ڼӹ��ĸ�����
	private Step step;
	
	public TimeChip(double s,double e,Step step){
		start=s;
		end=e;
		this.step=step;
	}
	
	public double getStart() {
		return start;
	}
	
	public double getEnd() {
		return end;
	}
	
	public double getPeriod(){
		return end-start;
	}
	
	public Step getStep() {
		return step;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String s=step==null?"(o0-0)":step.toString();
		
		
		return "["+start+","+end+"]"+s;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	public int compareTo(TimeChip chip) {
		// TODO Auto-generated method stub
		
		if(start>chip.start) return 1;
		else if (start<chip.start) return -1;
		else return 0;
			
		
	}
}
