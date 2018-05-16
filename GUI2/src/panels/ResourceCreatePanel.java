package panels;

import java.util.ArrayList;
import java.util.List;

import constant.Info;

public class ResourceCreatePanel extends CreatePanel {

	public ResourceCreatePanel(List<String> names) {
		super(names);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean checkIllegality() {
		// TODO Auto-generated method stub
		// if(Info.get)
		if (nameField.isEmpty()) {
			errorLabel.setText("新车间名称不能为空！");
			return false;
		}

		if (Info.getJobshopNames(false).contains(nameField.getText())) {
			errorLabel.setText("已存在该车间！");
			return false;
		}

		if (!states.contains(true)) {
			errorLabel.setText("请至少选择一个设备！");
			return false;
		}

		errorLabel.setText("");
		return true;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		createType = "车间";
		itemType = "设备";

		tips = "请输入新车间名称";
	}

	@Override
	public void setEnsureListener() {
		if (notifyListener != null && checkIllegality()) {
			notifyListener.notifyParent(0);
		}
	}

	public CreatedJobshop getNewJobshop() {
		List<String> machines = new ArrayList<>();
		for (int i = 0; i < states.size(); i++) {
			if (states.get(i))
				machines.add(Info.getMachineNames().get(i));
		}

		return new CreatedJobshop(nameField.getText(), machines);
	}

	class CreatedJobshop {
		String name;
		List<String> machines;

		public CreatedJobshop(String name, List<String> machines) {
			// TODO Auto-generated constructor stub
			this.name = name;
			this.machines = machines;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < machines.size(); i++)
				sb.append(machines.get(i)).append('\n');

			return sb.toString().trim();
		}
	}

}
