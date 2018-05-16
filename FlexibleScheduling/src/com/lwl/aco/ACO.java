package com.lwl.aco;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lwl.antcolony.Way;
import com.lwl.resource.Resource;
import com.lwl.task.Job;
import com.lwl.task.TaskGraph;

/**
 * 蚁群算法
 * ACO代表执行一次完整的蚁群算法
 * @author Will
 *
 */
public class ACO {
	//迭代的最大次数
	public static final int MAX_N=100;
	//信息素初始浓度
	public static final double INIT_PHE=1.0;
	//信息素挥发因子
	public static final double p=0.2;
	//信息素浓度的最大值和最小值
	public static final double MAX_PHE=8;
	public static final double MIN_PHE=0.1;
	
	public static double ALPHA=2;
	public static double BETA=2;
	//每轮迭代释放的信息素总量
	public static double Q=10;
	
	
	//参与调度的工件任务集合
	private TaskGraph graph;
	//参与调度的设备资源
	private Resource resource;
	private double[][] pheromone;

	//记录每次迭代的过程
	private List<Round> rounds=new ArrayList<Round>();
	
	//全局最优解
	private Way bestWay;
	
	public ACO(TaskGraph graph,Resource resource){
		this.graph=graph;
		this.resource=resource;
		
		//初始化信息素矩阵
		pheromone=new double[graph.getTotalStepCount()][graph.getTotalStepCount()];
		for (int i = 0; i < pheromone.length; i++) {
			Arrays.fill(pheromone[i], INIT_PHE);
		}
	}
	
	//执行蚁群算法
	public Way start(){
		for (int i = 0; i < MAX_N; i++) {
			Round round=new Round(this);
			rounds.add(round);
			round.start();
			System.out.println(round.getRoundBestWay().getFinishedTime());
		}
		
		return bestWay;
	}
	
	/**
	 * 每轮迭代后恢复信息素
	 * @param i
	 * @param j
	 * @param increasedPhe
	 */
	public void decreasePhe(){
		for (int i = 0; i < pheromone.length; i++) {
			for (int j = 0; j < pheromone[0].length; j++) {
				pheromone[i][j]*=(1-p);
				pheromone[i][j]=pheromone[i][j]<MIN_PHE?MIN_PHE:pheromone[i][j];
			}
		}
	}
	
	/**
	 * 每轮迭代后只对本轮最佳路径增加信息素
	 * @param i
	 * @param j
	 * @param increasedPhe
	 */
	public void increasePhe(int i,int j, double increasedPhe) {
		pheromone[i][j]+=increasedPhe;
		pheromone[i][j]=pheromone[i][j]>MAX_PHE?MAX_PHE:pheromone[i][j];
	}
	
	public Resource getResource() {
		return resource;
	}
	
	public TaskGraph getGraph() {
		return graph;
	}
	
	public Way getBestWay() {
		return bestWay;
	}
	
	public void setBestWay(Way bestWay) {
		this.bestWay = bestWay;
	}
	
	public double[][] getPheromone() {
		return pheromone;
	}
}
