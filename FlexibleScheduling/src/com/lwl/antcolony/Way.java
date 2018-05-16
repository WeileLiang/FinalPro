package com.lwl.antcolony;

import java.util.List;

import com.lwl.resource.Resource;
import com.lwl.task.Step;

/**
 * 蚂蚁的路径
 * @author Will
 *
 */
public class Way {
	private List<Step> path;
	//路径中各个Step在设备上的安排
	private Resource resource;
	//该路径的完工时间
	private double finishedTime;
	public Way(List<Step> path,Resource resource,double time){
		this.path=path;
		this.resource=resource;
		this.finishedTime=time;
	}
	
	public List<Step> getPath() {
		return path;
	}
	
	public Resource getResource() {
		return resource;
	}
	
	public double getFinishedTime() {
		return finishedTime;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "最优解时间："+finishedTime+'\n'+resource;
	}
}
