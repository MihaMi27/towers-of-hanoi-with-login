package game;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.*;

import userinfo.ResetPassword;

public class Menu extends JFrame {
	private static final long serialVersionUID = 1L;
	private static String username = "";
	private static String nickname = "";
	
	
	public Menu() {
		// Get info about current user
		File file_currentUser = new File("./userinfo/currentUser.txt");		
		try {
			Scanner sc = new Scanner(file_currentUser);
			String currentUser = sc.nextLine();
			//System.out.println(currentUser);
			username = currentUser.split(" ")[0];
			nickname = currentUser.split(" ")[1];
			sc.close();
		} catch (FileNotFoundException fnfe) {			
			fnfe.printStackTrace();
		}
		
		
		// Init
		Container cp = getContentPane();
		JLabel label_naslov = new JLabel("Towers of Hanoi");
		JLabel label_spin = new JLabel("Number of disks?");
		JButton btn_play = new JButton("Play");
		JButton btn_exit = new JButton("Exit");
		JButton btn_changePass = new JButton("Change password");		
		JButton btn_back = new JButton("Back");
		JButton btn_set = new JButton("Set");
		JTextPane textpane_desc = new JTextPane();
		SpinnerModel model_spin = new SpinnerNumberModel(3,3,10,1); // Init value 3, min value 3, max value 10, step by 1
		JSpinner spin_disks = new JSpinner(model_spin);
		JSpinner.NumberEditor spin_ne = (JSpinner.NumberEditor) spin_disks.getEditor();
		
		
		// Frame Properties
		setSize(new Dimension(440, 450));
		setTitle("Towers of Hanoi - Menu");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		
		// Element Properties
		cp.setLayout(null);
		cp.setBackground(new Color(37,37,38,230));		
		label_naslov.setHorizontalAlignment(SwingConstants.CENTER);
		label_naslov.setFont(new Font("Helvetica", 0, 30));	
		label_naslov.setForeground(new Color(240,240,240));	

		textpane_desc.setEditable(false);			
		textpane_desc.setContentType("text/html");
		textpane_desc.setText("<html><center>Welcome "+nickname+" ("+username+") in Towers of Hanoi! <br>To move disks around, you will need to use the <br>arrow keys on your keyboard.</center></html>");
		textpane_desc.setFont(new Font("Helvetica", 0, 14));
		textpane_desc.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true); // Honor setFont
		textpane_desc.setBackground(new Color(0,0,0,0));
		textpane_desc.setForeground(new Color(240,240,240));

		btn_play.setFont(new Font("Helvetica", 1, 15));
		btn_play.setBackground(new Color(0, 122, 204));
		btn_play.setForeground(new Color(240,240,240));

		btn_exit.setFont(new Font("Helvetica", 1, 15));	
		btn_exit.setBackground(new Color(0, 122, 204));
		btn_exit.setForeground(new Color(240,240,240));	
		
		btn_changePass.setFont(new Font("Helvetica", 1, 15));		
		btn_changePass.setBackground(new Color(0, 122, 204));
		btn_changePass.setForeground(new Color(240,240,240));

		btn_back.setFont(new Font("Helvetica", 0, 15));
		btn_back.setVisible(false);		
		btn_back.setBackground(new Color(0, 122, 204));
		btn_back.setForeground(new Color(240,240,240));

		btn_set.setFont(new Font("Helvetica", 0, 15));
		btn_set.setVisible(false);
		btn_set.setBackground(new Color(0, 122, 204));
		btn_set.setForeground(new Color(240,240,240));

		spin_disks.setFont(new Font("Helvetica", 0, 15));
		spin_disks.setBackground(new Color(0,0,0,0));
		spin_disks.setForeground(new Color(240,240,240));
		spin_disks.setVisible(false);

		spin_ne.getTextField().setEditable(false);
		spin_ne.setBackground(new Color(0,0,0,0));	

		label_spin.setFont(new Font("Helvetica", 0, 15));
		label_spin.setVisible(false);		
		label_spin.setForeground(new Color(240,240,240));
		label_spin.setBackground(new Color(0,0,0,0));
		

		// Element Positioning
		label_naslov.setBounds(60, 60, 300, 30);
		textpane_desc.setBounds(20, 100, 400, 100);		
		btn_play.setBounds(110, 230, 200, 50);
		btn_changePass.setBounds(110, 290, 200, 50);
		btn_exit.setBounds(110, 350, 200, 50);
		btn_back.setBounds(110, 350, 200, 50);
		btn_set.setBounds(110, 290, 200, 50);
		label_spin.setBounds(110, 205, 200, 25);
		spin_disks.setBounds(110, 230, 200, 50);
		
		

		// Action Listeners
		btn_play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btn_play.setVisible(false);				
				btn_changePass.setVisible(false);				
				btn_exit.setVisible(false);
				btn_back.setVisible(true);
				spin_disks.setVisible(true);
				btn_set.setVisible(true);
				label_spin.setVisible(true);			
				repaint();			
			}
		});

		btn_set.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int numOfDisks = (int) spin_disks.getValue();					
					new GameFrame(numOfDisks).setVisible(true);
					dispose();
										
				} catch (NumberFormatException nfe) {
					nfe.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error parsing the number of disks", "Error", JOptionPane.ERROR_MESSAGE);					
				}
			}
		});

		btn_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		btn_changePass.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new ResetPassword();				
			}
			
		});
		
		btn_back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btn_play.setVisible(true);				
				btn_changePass.setVisible(true);				
				btn_exit.setVisible(true);
				btn_back.setVisible(false);
				spin_disks.setVisible(false);
				btn_set.setVisible(false);
				label_spin.setVisible(false);
				repaint();			
			}
			
		});

		// Adding
		cp.add(label_naslov);
		cp.add(textpane_desc);
		cp.add(btn_play);
		cp.add(btn_changePass);
		cp.add(btn_exit);
		cp.add(btn_set);
		cp.add(btn_back);
		cp.add(spin_disks);
		cp.add(label_spin);
		
		

		// Post
		this.setVisible(true);

		
		
		
	}
}
