package com.lwl.aco;

import java.util.List;

import com.lwl.antcolony.Ant;
import com.lwl.antcolony.AntColony;
import com.lwl.antcolony.Way;
import com.lwl.resource.Resource;
import com.lwl.task.Step;
import com.lwl.task.TaskGraph;

/**
 * 蚁群算法的一轮迭代
 * @author Will
 *
 */
public class Round {
	
	//信息素增量
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
		//每只蚂蚁蚂蚁都要走完所有的工序
		for(int i=0;i<graph.getTotalStepCount()-1;i++){//因为不包括initStep,所以循环终止条件要-1
			for (int k = 0; k < antColony.getAntsCount(); k++) {
				Ant ant=antColony.getKthAnt(k);
				//从可选池中选择下一个工序
				Step nextStep=ant.chooseNextStep(aco.getPheromone());
				//从可选池中移除该工序并添加到禁忌池中
				ant.removeFromAccessiblePool(nextStep);
				//把该工序的后续工序添加到可选池
				ant.add2AccessiblePool(nextStep);
				ant.setCurStep(nextStep);
			}
		}
		
		//筛选本轮的最优解
		roundBestWay=antColony.getKthAnt(0).getWay();
//		int bestAntId=0;
		for(int i=1;i<antColony.getAntsCount();i++){
			if(antColony.getKthAnt(i).getWay().getFinishedTime()<roundBestWay.getFinishedTime()){
				roundBestWay=antColony.getKthAnt(i).getWay();
//				bestAntId=i;
			}
		}

		//截止至上一轮迭代的全局最优解
		Way temp=aco.getBestWay();
		//与全局最优解进行比较
		if(aco.getBestWay()==null||roundBestWay.getFinishedTime()<=aco.getBestWay().getFinishedTime())
			aco.setBestWay(roundBestWay);
		
		//先挥发信息素
		aco.decreasePhe();
		
		//只对本轮最佳蚂蚁的路径更新信息素
		//信息素增量为averagePhe=Q/(t*(e^(t-tbest)))
		//tbest为全局最佳路径的完工时间
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
