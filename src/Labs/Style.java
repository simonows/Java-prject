package Labs;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;

public class Style{
	static int width = 1000;
	static int height = 680;
	static int info_height = 24;
	static int menu_width = 200;
	static int net_menu_width = 200;
	
	static int info_width = width;
	static int menu_height = height;
	static int space_width=width-menu_width-net_menu_width-15;
	static int space_height=height-info_height*3-8;
	static Color space_background=Color.WHITE;
	static Color info_background = Color.decode("#8401a5");
	static Color menu_background = Color.decode("#f3e9f7");
	static Color menubar_background = Color.decode("#e3cced");
	static Color text_color = Color.decode("#ffffff");
	static Font text_style1 = new Font("Verdana",Font.PLAIN,12);
	
	public static void setJLabelStyle(JLabel element){
		element.setFont(text_style1);
        element.setForeground(info_background);
	}
	
	public static JButton newButton(String name){
		JButton button = new JButton(name);
        button.setBackground(Style.info_background);
        button.setForeground(Color.white);
        return button;
	}
};
