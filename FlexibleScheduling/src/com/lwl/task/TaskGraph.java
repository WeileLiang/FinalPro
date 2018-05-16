package com.lwl.task;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.lwl.aco.ACO;
import com.lwl.antcolony.Way;
import com.lwl.resource.Machine;
import com.lwl.resource.Resource;
import com.lwl.resource.TimeChip;

/**
 * 多个工件组成的任务集合
 * 
 * @author Will
 * 
 */
public class TaskGraph{

	public static final String JOBS_FILE_PATH = "jobs";
	public static final String GRAPH_PATH = JOBS_FILE_PATH + "\\graph.txt";
	public static final String RESOURCE_PATH = "resource.txt";

	private List<Job> jobs;
	
	//初始节点
	private Step initStep=new Step(null, 0, 0, null, null);
	private int totalStepCount=1;
	//各工件的起始工序集合
	private List<Step> startSteps;

	public TaskGraph(List<Job> jobs) {
		this.jobs = jobs;
		for (int i = 0; i < jobs.size(); i++)
			totalStepCount += jobs.get(i).getSteps().size();

	}

	public Step getInitStep() {
		return initStep;
	}
	
	public List<Job> getJobs() {
		return jobs;
	}
	
	/**
	 * 返回各个工件的起始工序集合
	 * @return
	 */
	public List<Step> getStartSteps(){
		if(startSteps==null){
			startSteps=new ArrayList<Step>();
			for (int i = 0; i < jobs.size(); i++) {
				startSteps.add(jobs.get(i).getSteps().get(0)); 
			}
		}
		
		return startSteps;
	}
	
	public int getTotalStepCount() {
		return totalStepCount;
	}
	
	public static void main(String[] args) {
		List<Job> jobs = readJobs();
		Resource resource = readResource();
		
//		TaskGraph graph=new TaskGraph(jobs);
//		System.out.println(graph.getTotalStepCount());
		
		ACO aco=new ACO(new TaskGraph(jobs), resource);
		aco.start();
		System.out.println(aco.getBestWay());
	}

	public static Resource readResource() {
		BufferedReader reader = null;
		Resource resource = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(RESOURCE_PATH)));
			// 设备数量
			int machineCount = Integer.parseInt(reader.readLine());
			List<Machine> machines = new ArrayList<Machine>();
			for (int i = 0; i <= machineCount; i++) {
				machines.add(new Machine(i, new LinkedList<TimeChip>()));
			}

			// 需要初始化的设备数目
			int initMachineCount = Integer.parseInt(reader.readLine());
			for (int i = 0; i < initMachineCount; i++) {
				// 空行
				reader.readLine();
				String[] idAndCount = reader.readLine().split(" ");
				// 要初始化的设备ID
				int id = Integer.parseInt(idAndCount[0]);
				// 需要为设备加工序列初始化的时间片数目
				int chipsCount = Integer.parseInt(idAndCount[1]);

				for (int j = 0; j < chipsCount; j++) {
					String[] chip = reader.readLine().trim().split(" ");
					machines.get(id)
							.getChips()
							.add(new TimeChip(Double.parseDouble(chip[0]),
									Double.parseDouble(chip[1]), null));
				}

			}

			resource = new Resource(machines);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return resource;
	}

	/**
	 * 读取graph.txt中工件信息
	 * 
	 * @return
	 */
	public static List<Job> readJobs() {
		List<Job> jobs = new ArrayList<Job>();
		// 标记Step在全局工件集合中的id,由于需要为工件集合添加一个起始节点，因此从1而不是0开始算起
		int[] globalStepId = { 1 };
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(GRAPH_PATH)));

			String nameId = null;
			int jobId = 0;
			while ((nameId = reader.readLine()) != null) {
				Job job = readJob(jobId++, Integer.parseInt(nameId.trim()),
						globalStepId);
				jobs.add(job);

			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		System.out.println(globalStepId[0]);
		return jobs;
	}

	/**
	 * 读取job.txt中的工序信息，封装为对应的Job对象
	 * 
	 * @param jobId
	 * @param nameId
	 * @return
	 */
	public static Job readJob(int jobId, int nameId, int[] globalStepId) {
		Job job = new Job(jobId, nameId);
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(JOBS_FILE_PATH + "\\JOB" + nameId
							+ ".txt")));

			List<Step> steps = new ArrayList<Step>();

			int count = Integer.parseInt(reader.readLine().trim());
			for (int i = 0; i < count; i++) {
				//这行没用的，只是方便人来阅读txt
				int id = Integer.parseInt(reader.readLine().trim());
				steps.add(readStep(job, i, globalStepId[0]++,
						reader.readLine(), reader.readLine()));
			}

			job.setSteps(steps);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return job;
	}

	public static Step readStep(Job job, int id, int graphId,
			String machineLine, String timeLine) {
		String[] machines = machineLine.trim().split(" ");
		String[] times = timeLine.trim().split(" ");

		List<Integer> suitableMachines = new ArrayList<Integer>();
		List<Double> processTimes = new ArrayList<Double>();

		for (int i = 0; i < machines.length; i++) {
			suitableMachines.add(Integer.parseInt(machines[i]));
			processTimes.add(Double.parseDouble(times[i]));
		}

		return new Step(job, id, graphId, suitableMachines, processTimes);
	}
}
