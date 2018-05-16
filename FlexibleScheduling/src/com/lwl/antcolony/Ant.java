package com.lwl.antcolony;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;

import javax.crypto.NullCipher;

import com.lwl.aco.ACO;
import com.lwl.aco.Round;
import com.lwl.resource.Machine;
import com.lwl.resource.Resource;
import com.lwl.resource.TimeChip;
import com.lwl.task.Step;
import com.lwl.task.TaskGraph;

public class Ant {
	private TaskGraph graph;
	private Resource resource;

	// ���ɳ�
	private Set<Step> taboo = new LinkedHashSet<Step>();
	// ��ѡ��
	private Set<Step> accessiblePool = new HashSet<Step>();
	// ���ϵ�ǰ���ڵĽڵ�
	private Step curStep;
	// ���ϵĵ�ǰ·��
	private Way way;
	// ��ǰ·����ʱ��
	private double time;

	// ��ѡ���и��������������ɼӹ�ʱ��
	List<Double> startTimes = new ArrayList<Double>();

	public Ant(TaskGraph graph, Resource resource) {
		this.graph = graph;
		this.resource = resource;

		// �Ѹ������ĳ�ʼ������뵽��ѡ����
		accessiblePool.addAll(graph.getStartSteps());

		// ��ʼ��ѡ���и��������ʱ��0���мӹ�
		for (int i = 0; i < graph.getJobs().size(); i++)
			startTimes.add(.0);

		curStep = graph.getInitStep();
		taboo.add(curStep);

	}

	public void setTaboo(Set<Step> taboo) {
		this.taboo = taboo;
	}

	public void removeFromAccessiblePool(Step step) {
		accessiblePool.remove(step);
		taboo.add(step);
	}

	public void add2AccessiblePool(Step step) {
		// ��Step�ĺ���������ӵ���ѡ��
		if (step.getId() < step.getJob().getSteps().size() - 1) {
			accessiblePool.add(step.getJob().getSteps().get(step.getId() + 1));
		}
	}

	public double getTime() {
		return time;
	}

	/**
	 * �ӿ�ѡ����ѡ��һ������
	 * 
	 * @return
	 */
	public Step chooseNextStep(double[][] pheromone) {
		// ��������ѡ���깤ʱ��ļ���
		List<MCDetial> mcDetials = new ArrayList<Ant.MCDetial>();

		// �����ѡ����ÿ�������Ӧ�Ļ���ѡ����깤ʱ��
		for (Step step : accessiblePool) {
			for (int i = 0; i < step.getSuitableMachines().size(); i++) {
				int machineId = step.getSuitableMachines().get(i);
				double processTime = step.getProcessTimes().get(i);
				double startTime = startTimes.get(step.getJob().getId());
				double c = getFinishedTime(machineId, processTime, startTime);
				mcDetials.add(new MCDetial(machineId, processTime, step, c));
			}
		}

		// �����������ѡ����ʵķ��Ӽ���
		List<Double> numerators = new ArrayList<Double>();
		// �����������ѡ����ʵķ�ĸ,Ϊ����֮��
		double denominator = 0;
		// ���л���ѡ���깤ʱ����ܺ�
		for (MCDetial mcDetial : mcDetials) {

			numerators.add(Math.pow(pheromone[curStep.getGraphId()][mcDetial.step.getGraphId()], ACO.ALPHA)
					* Math.pow(1 / mcDetial.finishedTime, ACO.BETA));
			denominator += numerators.get(numerators.size() - 1);
		}

		// ������ѡ��ĸ��ʣ�pi=reciprocals(i)/sum
		List<Double> probabilities = new ArrayList<Double>();
		for (Double numerator : numerators) {
			probabilities.add(numerator / denominator);
		}

		double randomVal = Math.random();
		double curSum = 0;

		for (int i = 0; i < probabilities.size(); i++) {
			curSum += probabilities.get(i);
			if (randomVal < curSum) {
				MCDetial mcDetial = mcDetials.get(i);
				// �ڶ�Ӧ�����ϲ���û���ѡ���ʱ��Ƭ
				TimeChip insertedChip = new TimeChip(mcDetial.finishedTime - mcDetial.processTime,
						mcDetial.finishedTime, mcDetial.step);

				// ѡ��������ڶ�Ӧ�Ļ����ϲ���ʱ��Ƭ
				resource.getMachines().get(mcDetial.machineId).getChips().offer(insertedChip);
				// ���¸����ϵĵ�ǰ·�������ʱ��
				setTime(mcDetial.finishedTime);
				// ���ù������������ĺ�����������翪ʼ�ӹ�ʱ��
				startTimes.set(mcDetial.step.getJob().getId(), mcDetial.finishedTime);
				return mcDetial.step;
			}
		}

		return null;
	}

	/**
	 * ����ĳ�ֻ���ѡ���Ԥ���깤ʱ��
	 * 
	 * @param machineId
	 * @param processTime
	 * @param startTime
	 *            �����ڸ��豸�ϵ����翪ʼ�ӹ�ʱ��
	 * @return
	 */
	public double getFinishedTime(int machineId, double processTime, double startTime) {
		PriorityQueue<TimeChip> chips = resource.getMachines().get(machineId).getChips();

		if (chips.isEmpty())
			return startTime + processTime;

		double previous = 0;
		for (TimeChip chip : chips) {
			if (startTime < previous && chip.getStart() - previous >= processTime)
				return previous + processTime;
			if (startTime >= previous && startTime <= chip.getStart() && chip.getStart() - startTime >= processTime)
				return startTime + processTime;

			previous = chip.getEnd();
		}

		if (previous > startTime)
			return previous + processTime;
		else
			return startTime + processTime;
	}

	public Way getWay() {
		if (way == null)
			way = new Way(new ArrayList<Step>(taboo), resource, time);
		return way;
	}

	public void setCurStep(Step curStep) {
		this.curStep = curStep;
	}

	public void setTime(double time) {
		if (time > this.time)
			this.time = time;
	}

	/**
	 * ��¼ÿ�ֻ���ѡ���Ӧ���깤ʱ��
	 * 
	 * @author Will
	 * 
	 */
	class MCDetial {
		int machineId;
		double processTime;
		Step step;
		double finishedTime;

		public MCDetial(int mId, double pTime, Step s, double fTime) {
			machineId = mId;
			processTime = pTime;
			step = s;
			finishedTime = fTime;
		}
	}

}
