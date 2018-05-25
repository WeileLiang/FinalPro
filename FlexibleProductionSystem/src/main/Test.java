package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import constant.AnimationUtil;
import module.InfoManagePanel;
import panels.TablePanel;
import panels.TimeLinePanel;
import views.HorizontalScrollBarUI;
import views.LineSeparator;
import views.TableRow;
import views.TimeLineItem;
import views.VerticalScrollBarUI;

public class Test extends JFrame {

	public Test() {
		setLayout(null);
		getContentPane().setBackground(Color.GRAY);
		
		JPanel line=new JPanel();
		line.setBackground(Color.WHITE);
		line.setBounds(0, MyFrame.HEIGHT/2, 0, 3);
		add(line);
		AnimationUtil.doProgressAnim(line, 0, MyFrame.WIDTH, 1500, null);
	}

	public static void main(String[] args) {
		Test jf = new Test();
		jf.setSize(MyFrame.WIDTH, MyFrame.HEIGHT);
		jf.setLocationRelativeTo(null);
		jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		jf.setVisible(true);

	}
	
	//读取运行结果
	static List<String> names;
	static List<List<double[]>> chipLists;
	static List<List<String>> procedureLists;
		public static void readResult() {
			BufferedReader reader=null;
			names=new ArrayList<>();
			chipLists=new ArrayList<>();
			procedureLists=new ArrayList<>();
			try {
				reader=new BufferedReader(new InputStreamReader(new FileInputStream("results\\result1.txt")));
				int machineCount=Integer.parseInt(reader.readLine());
				
				while (machineCount-->0) {
					reader.readLine();
					String[] machineNameAndChipCount=reader.readLine().split(" ");
					names.add(machineNameAndChipCount[0]);
					
					int chipCount=Integer.parseInt(machineNameAndChipCount[1]);
					List<double[]> chips=new ArrayList<>();
					List<String> procedures=new ArrayList<>();
					while (chipCount-->0) {
						String[] chipAndProcedure=reader.readLine().split(" ");
						chips.add(new double[] {Double.parseDouble(chipAndProcedure[0]),Double.parseDouble(chipAndProcedure[1])});
						procedures.add(chipAndProcedure[2]);
					}
					chipLists.add(chips);
					procedureLists.add(procedures);
				}
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
		}

}
