package Labs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Menu extends JPanel {
	public JTextField period_drone, period_worker, life_worker, life_drone;
	public JComboBox<String> probability, prt_drones, prt_workers;
	public JButton start_btn,stop_btn,show_bees,stop_workers,stop_drones, start_console;
	public static JRadioButton show_info,neshow_info;
	public ButtonGroup gr;
	public JCheckBox show_box;
	
	public Menu(){
		JPanel form = new JPanel();
		JPanel form2 = new JPanel();
		JPanel form3 = new JPanel();
		JPanel form4 = new JPanel();
		JPanel form21 = new JPanel();
		JPanel form22 = new JPanel();
		JPanel form31 = new JPanel();
		JPanel form32 = new JPanel();
		JPanel form41 = new JPanel();
		JPanel form42 = new JPanel();
		setPreferredSize(new Dimension(Style.menu_width, Style.menu_height));
		setBackground(Style.menu_background);
		show_info = new JRadioButton("показать время");
		show_info.setBackground(Style.menu_background);
        neshow_info = new JRadioButton("скрыть время");
        neshow_info.setBackground(Style.menu_background);
        gr = new ButtonGroup();
        show_box = new JCheckBox("показать информацию");
        show_box.setBackground(Style.menu_background);
        start_btn = Style.newButton("Старт");
        stop_btn = Style.newButton("Стоп");
        show_bees= Style.newButton("Показать");
        start_console = Style.newButton("Консоль");
        start_console.setPreferredSize(new Dimension(160,26));
        stop_workers = Style.newButton("Остановить рабочих");
        stop_workers.setPreferredSize(new Dimension(160,26));
        stop_drones= Style.newButton("Остановить трутней");
        stop_drones.setPreferredSize(new Dimension(160,26));
        stop_btn.setEnabled(false);
        show_box.setFont(Style.text_style1);
        show_info.setFont(Style.text_style1);
        neshow_info.setFont(Style.text_style1);
        setLayout(new FlowLayout(FlowLayout.CENTER));
        add(start_btn);
        add(stop_btn);
        add(form);
        form.add(show_box);
        form.add(show_info);
        form.add(neshow_info);
        form.setLayout(new FlowLayout(FlowLayout.LEFT));
        form.setPreferredSize(new Dimension(Style.menu_width, Style.menu_height/6));
        form.setBackground(Style.menu_background);
        form.setVisible(true);
        gr.add(show_info);
        gr.add(neshow_info);        
        period_drone = new JTextField(""+habitat.n1,6);
        period_worker = new JTextField(""+habitat.n2,6);
        life_drone = new JTextField(""+habitat.drone_life/1000,6);
        life_worker = new JTextField(""+habitat.worker_life/1000,6);
        String[] mas = {"10","20","30","40","50","60","70","80","90"};
        probability = new JComboBox(mas);
        probability.setSelectedIndex((int)(habitat.p*10)-1);
        addFragment("Период рождения: ", Style.menu_width,20,form2,null, null);
        addFragment("трутней:", Style.menu_width/2,20,form21,form22, period_drone);
        addFragment("рабочих:", Style.menu_width/2,19,form21,form22, period_worker);
        addFragment("вероятность:", Style.menu_width/2,25,form21,form22, probability);
        addFragment("Время жизни: ", Style.menu_width,20,form3,null, null);       
        addFragment("трутней:", Style.menu_width/2,20, form31,form32, life_drone);
        addFragment("рабочих:", Style.menu_width/2,19, form31, form32, life_worker);
        setMenuStructure(form2, form21, form22,110);
        setMenuStructure(form3, form31, form32,90);
        show_bees.setVisible(true);
        String prts[] = {"1","2","3","4","5","6","7","8"}; 
        prt_drones = new JComboBox(prts);
        prt_drones.setPreferredSize(new Dimension(50,21));
        prt_workers = new JComboBox(prts);
        prt_workers.setPreferredSize(new Dimension(50,21));
        addFragment("Приоритеты: ", Style.menu_width,20,form4,null, null);
        addFragment("трутней:", Style.menu_width/2,20,form41,form42, prt_drones);
        addFragment("рабочих:", Style.menu_width/2,19,form41,form42, prt_workers);
        setMenuStructure(form4, form41, form42,100);
        add(form2);
        add(form3);
        add(form4);
        add(show_bees);
        add(stop_workers);
        add(stop_drones);
        add(start_console);
        setVisible(true);
        stop_workers.setEnabled(false);
		stop_drones.setEnabled(false);

	}
	private void addFragment(String name, int width, int height, JPanel form1, JPanel form2, JComponent elem){
		JLabel text = new JLabel(name);
        text.setPreferredSize(new Dimension(width,height));
        form1.add(text);
        Style.setJLabelStyle(text);
        if(elem != null) form2.add(elem);
	}
	
	private void setMenuStructure(JPanel form, JPanel form1, JPanel form2, int height){
		form.setLayout(new FlowLayout(FlowLayout.LEFT));
        form.add(form1);
        form.add(form2);
        
        form.setBackground(Style.menu_background);
        form.setPreferredSize(new Dimension(Style.menu_width-15, height));
        form.setVisible(true);
        
        form1.setLayout(new FlowLayout(FlowLayout.LEFT));
        form2.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        form1.setPreferredSize(new Dimension(Style.menu_width/2-10, Style.menu_height/6));
        form2.setPreferredSize(new Dimension(Style.menu_width/2-20, Style.menu_height/6));
        form1.setBackground(Style.menu_background);
        form2.setBackground(Style.menu_background);
        form1.setVisible(true);
        form2.setVisible(true);
	}
}
