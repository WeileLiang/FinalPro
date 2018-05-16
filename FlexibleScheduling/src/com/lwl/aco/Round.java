package com.lwl.aco;

import java.util.List;

import com.lwl.antcolony.Ant;
import com.lwl.antcolony.AntColony;
import com.lwl.antcolony.Way;
import com.lwl.resource.Resource;
import com.lwl.task.Step;
import com.lwl.task.TaskGraph;

/**
 * ��Ⱥ�㷨��һ�ֵ���
 * @author Will
 *
 */
public class Round {
	
	//��Ϣ������
	private double[][] detaPhe;
	private ACO aco;
	private TaskGraph graph;
	private Resource resource;
	
	private AntColony antColony;
	private Way roundBestWay;
	
	public Round(ACO aco){
		this.aco=aco;
		this.resource=aco.getResource();
		this.graph=aco.getGraph();
		
		antColony=new AntColony(graph, resource);
		detaPhe=new double[graph.getTotalStepCount()][graph.getTotalStepCount()];
	}
	
	public void start(){
		//ÿֻ�������϶�Ҫ�������еĹ���
		for(int i=0;i<graph.getTotalStepCount()-1;i++){//��Ϊ������initStep,����ѭ����ֹ����Ҫ-1
			for (int k = 0; k < antColony.getAntsCount(); k++) {
				Ant ant=antColony.getKthAnt(k);
				//�ӿ�ѡ����ѡ����һ������
				Step nextStep=ant.chooseNextStep(aco.getPheromone());
				//�ӿ�ѡ�����Ƴ��ù�����ӵ����ɳ���
				ant.removeFromAccessiblePool(nextStep);
				//�Ѹù���ĺ���������ӵ���ѡ��
				ant.add2AccessiblePool(nextStep);
				ant.setCurStep(nextStep);
			}
		}
		
		//ɸѡ���ֵ����Ž�
		roundBestWay=antColony.getKthAnt(0).getWay();
//		int bestAntId=0;
		for(int i=1;i<antColony.getAntsCount();i++){
			if(antColony.getKthAnt(i).getWay().getFinishedTime()<roundBestWay.getFinishedTime()){
				roundBestWay=antColony.getKthAnt(i).getWay();
//				bestAntId=i;
			}
		}

		//��ֹ����һ�ֵ�����ȫ�����Ž�
		Way temp=aco.getBestWay();
		//��ȫ�����Ž���бȽ�
		if(aco.getBestWay()==null||roundBestWay.getFinishedTime()<=aco.getBestWay().getFinishedTime())
			aco.setBestWay(roundBestWay);
		
		//�Ȼӷ���Ϣ��
		aco.decreasePhe();
		
		//ֻ�Ա���������ϵ�·��������Ϣ��
		//��Ϣ������ΪaveragePhe=Q/(t*(e^(t-tbest)))
		//tbestΪȫ�����·�����깤ʱ��
		if(temp==null) temp=roundBestWay;
		double averagePhe=ACO.Q/(roundBestWay.getFinishedTime()*(Math.pow(Math.E, roundBestWay.getFinishedTime()-temp.getFinishedTime())));
		List<Step> path=aco.getBestWay().getPath();
		for(int i=0;i<path.size()-1;i++){
			aco.increasePhe(path.get(i).getGraphId(), path.get(i+1).getGraphId(), averagePhe);		
		}
		
	}
	
	public Way getRoundBestWay() {
		return roundBestWay;
	}
	
}
