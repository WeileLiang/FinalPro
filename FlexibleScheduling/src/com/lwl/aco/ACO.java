package com.lwl.aco;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lwl.antcolony.Way;
import com.lwl.resource.Resource;
import com.lwl.task.Job;
import com.lwl.task.TaskGraph;

/**
 * ��Ⱥ�㷨
 * ACO����ִ��һ����������Ⱥ�㷨
 * @author Will
 *
 */
public class ACO {
	//������������
	public static final int MAX_N=100;
	//��Ϣ�س�ʼŨ��
	public static final double INIT_PHE=1.0;
	//��Ϣ�ػӷ�����
	public static final double p=0.2;
	//��Ϣ��Ũ�ȵ����ֵ����Сֵ
	public static final double MAX_PHE=8;
	public static final double MIN_PHE=0.1;
	
	public static double ALPHA=2;
	public static double BETA=2;
	//ÿ�ֵ����ͷŵ���Ϣ������
	public static double Q=10;
	
	
	//������ȵĹ������񼯺�
	private TaskGraph graph;
	//������ȵ��豸��Դ
	private Resource resource;
	private double[][] pheromone;

	//��¼ÿ�ε����Ĺ���
	private List<Round> rounds=new ArrayList<Round>();
	
	//ȫ�����Ž�
	private Way bestWay;
	
	public ACO(TaskGraph graph,Resource resource){
		this.graph=graph;
		this.resource=resource;
		
		//��ʼ����Ϣ�ؾ���
		pheromone=new double[graph.getTotalStepCount()][graph.getTotalStepCount()];
		for (int i = 0; i < pheromone.length; i++) {
			Arrays.fill(pheromone[i], INIT_PHE);
		}
	}
	
	//ִ����Ⱥ�㷨
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
	 * ÿ�ֵ�����ָ���Ϣ��
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
	 * ÿ�ֵ�����ֻ�Ա������·��������Ϣ��
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
