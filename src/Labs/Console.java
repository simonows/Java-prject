package Labs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class Console extends JFrame{
	public static PipedInputStream pipeIn;
	public static PipedOutputStream pipeOut;
	public static PipedInputStream pIn;
	public static PipedOutputStream pOut;
	
	public static BufferedInputStream in;
	public static BufferedOutputStream out;
	int offset=4;
	String command;
	
	public Console(){
		super("Консоль");
		pipeIn = new PipedInputStream();
		pIn = new PipedInputStream();
		try {
			pipeOut = new PipedOutputStream(pipeIn);
			pOut = new PipedOutputStream(pIn);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(800, 500);
        ImageIcon icon = new ImageIcon("icon.jpg");
        setIconImage(icon.getImage());
        JEditorPane text = new JEditorPane();
        text.setBackground(Color.BLACK);
        text.setForeground(Color.GREEN);
        text.setFont(new Font("Consolas",Font.PLAIN,14));
        text.setLocation(0,0);
        Document doc = text.getDocument();
        try{
        	doc.insertString(doc.getLength(),">>> ", null);
        } catch(Exception e){
        	e.printStackTrace();
        }
        
        add(text);
        setLocation(160,100);
        setVisible(true);
        text.addKeyListener(new KeyListener() {
    	    public void keyReleased(KeyEvent e) {
    	    	if(e.getKeyCode()==KeyEvent.VK_ENTER){
    	    		try {
    	    			command = doc.getText(offset,doc.getLength()-offset-1);
    	    			
						if(command.equals("рабочих")){
							pipeOut.write(0);
							doc.insertString(doc.getLength(),"   "+pIn.read()+'\n', null);
							
						} else
						if(command.equals("трутней")){
							pipeOut.write(1);
							doc.insertString(doc.getLength(),"   "+pIn.read()+'\n', null);
							
						}else
						if(command.equals("выход")){
							setVisible(false);
						}else{
							doc.insertString(doc.getLength(),"   команда "+'"'+command+'"'+" не является командой"+'\n', null);
						}
						doc.insertString(doc.getLength(),">>> ", null);
					} catch (BadLocationException | IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
    	    		offset = doc.getLength();
    	    	}
    	    	
    	    }
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
			}
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
			}
    	});

	}
	
}
