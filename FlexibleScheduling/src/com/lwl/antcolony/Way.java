package com.lwl.antcolony;

import java.util.List;

import com.lwl.resource.Resource;
import com.lwl.task.Step;

/**
 * ���ϵ�·��
 * @author Will
 *
 */
public class Way {
	private List<Step> path;
	//·���и���Step���豸�ϵİ���
	private Resource resource;
	//��·�����깤ʱ��
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
		return "���Ž�ʱ�䣺"+finishedTime+'\n'+resource;
	}
}
