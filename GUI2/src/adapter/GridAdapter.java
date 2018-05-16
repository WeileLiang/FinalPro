package adapter;

import java.util.List;

import javax.swing.JComponent;

import views.ItemLabel;

public class GridAdapter<T> {
	
	private List<T> datas;
	
	public GridAdapter(List<T> datas) {
		this.datas=datas;
	}	
	
	public int getCount() {
		return datas.size();
	}
	
	public JComponent getView(int position) {
		return new ItemLabel(datas.get(position).toString());
	}

	
}
