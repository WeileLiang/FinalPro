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

	// 禁忌池
	private Set<Step> taboo = new LinkedHashSet<Step>();
	// 可选池
	private Set<Step> accessiblePool = new HashSet<Step>();
	// 蚂蚁当前所在的节点
	private Step curStep;
	// 蚂蚁的当前路径
	private Way way;
	// 当前路径的时间
	private double time;

	// 可选池中各工件工序的最早可加工时间
	List<Double> startTimes = new ArrayList<Double>();

	public Ant(TaskGraph graph, Resource resource) {
		this.graph = graph;
		this.resource = resource;

		// 把各工件的初始工序加入到可选池中
		accessiblePool.addAll(graph.getStartSteps());

		// 初始可选池中各工序可在时间0进行加工
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
		// 把Step的后续工序添加到可选池
		if (step.getId() < step.getJob().getSteps().size() - 1) {
			accessiblePool.add(step.getJob().getSteps().get(step.getId() + 1));
		}
	}

	public double getTime() {
		return time;
	}

	/**
	 * 从可选池中选择一个工序
	 * 
	 * @return
	 */
	public Step chooseNextStep(double[][] pheromone) {
		// 各个机器选择完工时间的集合
		List<MCDetial> mcDetials = new ArrayList<Ant.MCDetial>();

		// 计算可选池中每个工序对应的机器选择的完工时间
		for (Step step : accessiblePool) {
			for (int i = 0; i < step.getSuitableMachines().size(); i++) {
				int machineId = step.getSuitableMachines().get(i);
				double processTime = step.getProcessTimes().get(i);
				double startTime = startTimes.get(step.getJob().getId());
				double c = getFinishedTime(machineId, processTime, startTime);
				mcDetials.add(new MCDetial(machineId, processTime, step, c));
			}
		}

		// 计算各个机器选择概率的分子集合
		List<Double> numerators = new ArrayList<Double>();
		// 计算各个机器选择概率的分母,为分子之和
		double denominator = 0;
		// 所有机器选择完工时间的总和
		for (MCDetial mcDetial : mcDetials) {

			numerators.add(Math.pow(pheromone[curStep.getGraphId()][mcDetial.step.getGraphId()], ACO.ALPHA)
					* Math.pow(1 / mcDetial.finishedTime, ACO.BETA));
			denominator += numerators.get(numerators.size() - 1);
		}

		// 各机器选择的概率：pi=reciprocals(i)/sum
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
				// 在对应机器上插入该机器选择的时间片
				TimeChip insertedChip = new TimeChip(mcDetial.finishedTime - mcDetial.processTime,
						mcDetial.finishedTime, mcDetial.step);

				// 选定工序后，在对应的机器上插入时间片
				resource.getMachines().get(mcDetial.machineId).getChips().offer(insertedChip);
				// 更新该蚂蚁的当前路径的最大时间
				setTime(mcDetial.finishedTime);
				// 设置工序所属工件的后续工序的最早开始加工时间
				startTimes.set(mcDetial.step.getJob().getId(), mcDetial.finishedTime);
				return mcDetial.step;
			}
		}

		return null;
	}

	/**
	 * 计算某种机器选择的预计完工时间
	 * 
	 * @param machineId
	 * @param processTime
	 * @param startTime
	 *            工序在该设备上的最早开始加工时间
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
	 * 记录每种机器选择对应的完工时间
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
