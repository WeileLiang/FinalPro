package com.lwl.task;

import java.util.List;

/**
 * 工序
 * 
 * @author Will
 * 
 */
public class Step {

	// 工序隶属的工件
	private Job job;
	// 工序在工件中的id,从0开始
	private int id;
	// 工序在整个图graph中的id
	private int graphId;

	// // 工序的后续工序，即完成该工序后下一个应该执行的工序
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

	//获取后置工序
	public Step getNextStep() {
		if (id < job.getSteps().size() - 1)
			return job.getSteps().get(id + 1);
		return null;
	}

	//获取前置工序
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
