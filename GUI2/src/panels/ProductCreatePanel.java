package panels;

import java.util.ArrayList;
import java.util.List;

import constant.Info;

public class ProductCreatePanel extends CreatePanel {

	public ProductCreatePanel(List<String> names) {
		super(names);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean checkIllegality() {
		// TODO Auto-generated method stub
		// if(Info.get)
		if (nameField.isEmpty()) {
			errorLabel.setText("新产品名称不能为空！");
			return false;
		}

		if (Info.getProductNames(false).contains(nameField.getText())) {
			errorLabel.setText("已存在该产品！");
			return false;
		}

		if (!states.contains(true)) {
			errorLabel.setText("请至少选择一个组件！");
			return false;
		}

		errorLabel.setText("");
		return true;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		createType = "产品";
		itemType = "组件";

		tips = "请输入新产品名称";
	}

	@Override
	public void setEnsureListener() {
		if (notifyListener != null && checkIllegality()) {
			notifyListener.notifyParent(0);
		}
	}

	public CreatedProduct getNewProduct() {
		List<String> jobs = new ArrayList<>();
		for (int i = 0; i < states.size(); i++) {
			if (states.get(i))
				jobs.add(Info.getJobNames().get(i));
		}

		return new CreatedProduct(nameField.getText(), jobs);
	}

	class CreatedProduct {
		String name;
		List<String> jobs;

		public CreatedProduct(String name, List<String> jobs) {
			// TODO Auto-generated constructor stub
			this.name = name;
			this.jobs = jobs;
		}
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			StringBuilder sb=new StringBuilder();
			for(int i=0;i<jobs.size();i++)
				sb.append(jobs.get(i)).append('\n');
			
			return sb.toString().trim();
		}
	}

}
