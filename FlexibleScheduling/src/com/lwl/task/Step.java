package com.lwl.task;

import java.util.List;

/**
 * ����
 * 
 * @author Will
 * 
 */
public class Step {

	// ���������Ĺ���
	private Job job;
	// �����ڹ����е�id,��0��ʼ
	private int id;
	// ����������ͼgraph�е�id
	private int graphId;

	// // ����ĺ������򣬼���ɸù������һ��Ӧ��ִ�еĹ���
	// private Step nextStep;

	private List<Integer> suitableMachines;
	private List<Double> processTimes;

	public Step(Job job, int id, int graphId, List<Integer> suitableMachines,
			List<Double> processTimes) {
		this.job = job;
		this.id = id;
		this.graphId = graphId;
		this.suitableMachines = suitableMachines;
		this.processTimes = processTimes;
	}

	public Job getJob() {
		return job;
	}

	public int getGraphId() {
		return graphId;
	}

	public int getId() {
		return id;
	}

	//��ȡ���ù���
	public Step getNextStep() {
		if (id < job.getSteps().size() - 1)
			return job.getSteps().get(id + 1);
		return null;
	}

	//��ȡǰ�ù���
	public Step getPreStep() {
		if (id > 0)
			return job.getSteps().get(id - 1);
		return null;
	}

	public void setSuitableMachines(List<Integer> suitableMachines) {
		this.suitableMachines = suitableMachines;
	}

	public void setProcessTimes(List<Double> processTimes) {
		this.processTimes = processTimes;
	}

	public List<Integer> getSuitableMachines() {
		return suitableMachines;
	}

	public List<Double> getProcessTimes() {
		return processTimes;
	}

	public String toString() {
		// TODO Auto-generated method stub
		return "(" + job.getNameId() + "-" + (id+1) + ")";
	}
}
