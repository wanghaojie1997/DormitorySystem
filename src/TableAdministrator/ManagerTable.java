package TableAdministrator;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import ButtonFrame.ManagerAdd;
import ButtonFrame.ManagerSearch;
import Control.ManagerControl;
import Model.ManagersModel;
import Operation.ManagerOperation;
import Operation.RegisterOperation;

public class ManagerTable {
	public Object data[][] = null;
	Object columnNames[] = { "姓名", "员工号", "联系方式" };
	public static JPanel jp = new JPanel();
	public static JTable jt;
	private DefaultTableModel model;

	JButton Add_Button;
	JButton Update_Button;
	JButton Delete_Button;
	JButton Search_Button;

	public ManagerTable() {
		Add_Button = new JButton("添加");
		Update_Button = new JButton("刷新");
		Delete_Button = new JButton("删除");
		Search_Button = new JButton("查询");

		JScrollPane js = new JScrollPane();
		jt = new JTable(model);
		jt = new JTable(querymanager());
		jt.setVisible(true);
		jt.getTableHeader().setReorderingAllowed(false);
		jt.getTableHeader().setResizingAllowed(false);
		jt.setRowHeight(25);
		jt.setEnabled(true);
		js.setViewportView(jt);
		jp.add(js);
		Add_Button.setVisible(true);
		Add_Button.addActionListener(AddButton);
		jp.add(Add_Button);

		Update_Button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jt.setModel(querymanager());
				
			}
		});
		jp.add(Update_Button);

		Delete_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == Delete_Button) {
					String[] options = new String[] { "是", "否" };
					// 点击是的话
					int row = jt.getSelectedRow();
					if (row == -1) {
						JOptionPane.showMessageDialog(null, "请选择要删除的行！", "提示：", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					int n = JOptionPane.showOptionDialog(null, "确认删除？？？", "提示", JOptionPane.DEFAULT_OPTION,
							JOptionPane.WARNING_MESSAGE, null, options, options[0]);
					if (n == JOptionPane.YES_OPTION) {
						if (row != -1) {
							ManagerOperation manager = new ManagerOperation();
							RegisterOperation managerdelete = new RegisterOperation();

							try {
								manager.DeleteRemove(jt.getValueAt(jt.getSelectedRow(), 1));
								managerdelete.Deletemanager((int) jt.getValueAt(jt.getSelectedRow(), 1));
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}
						model.removeRow(row);
					}
				}
			}
		});
		jp.add(Delete_Button);

		Search_Button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == Search_Button) {
					new ManagerSearch();
				}

			}
		});
		jp.add(Search_Button);

		jp.setVisible(false);
		jp.setBounds(220, 50, 720, 465);
		jt.setPreferredScrollableViewportSize(new Dimension(700, 400));
	}

	public JPanel getpanel() {
		return jp;
	}

	public DefaultTableModel querymanager() {
		ManagerControl managercontrol = new ManagerControl();
		List<ManagersModel> result;
		try {
			result = managercontrol.query();
			data = new Object[result.size()][3];
			int j = 0;
			for (int i = 0; i < result.size(); i++) {
				data[i][j] = result.get(i).getManager_name();
				j++;
				data[i][j] = result.get(i).getManager_id();
				j++;
				data[i][j] = result.get(i).getContact();
				j = 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model = new DefaultTableModel(data, columnNames) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
	}

	ActionListener AddButton = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			new ManagerAdd();

		}
	};

}
