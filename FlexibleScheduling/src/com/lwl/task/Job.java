package com.lwl.task;

import java.util.List;

public class Job {
	// 工件的id，例如参与调度的有工件1和工件5，两者id分别为0和1
	private int id;
	// 工件的id名称，例如参与调度的有工件1和工件5，两者的nameId分别为1和5
	private int nameId;
	// 工件的工序集合
	private List<Step> steps;

	public Job(int id, int nameId) {
		this.id = id;
		this.nameId = nameId;
	}

	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}

	public int getId() {
		return id;
	}

	public List<Step> getSteps() {
		return steps;
	}

	public int getNameId() {
		return nameId;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		
		StringBuilder sb=new StringBuilder("JOB");
		sb.append(nameId).append('\n');
//		sb.append(steps==null);
		for (int i = 0; i < steps.size(); i++) {
			sb.append("step").append(i+1).append('\n');
			Step step=steps.get(i);
			for(int j=0;j<step.getSuitableMachines().size();j++)
				sb.append(step.getSuitableMachines().get(j)).append(' ');
			
			sb.append("\n");
			
			for(int j=0;j<step.getProcessTimes().size();j++)
				sb.append(step.getProcessTimes().get(j)).append(' ');
			
			sb.append('\n');
		}
		
		sb.append("\n");
		return sb.toString();
	}
}
