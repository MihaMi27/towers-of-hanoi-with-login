package game;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.*;

import userinfo.ResetPassword;
import userinfo.Scoreboard;

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
		JButton btn_changePass = new JButton("Change password");		
		JButton btn_scores = new JButton("Scoreboard");
		JButton btn_exit = new JButton("Exit");
		JButton btn_back = new JButton("Back");
		JButton btn_set = new JButton("Set");
		JLabel label_desc = new JLabel();
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
		label_naslov.setHorizontalAlignment(SwingConstants.CENTER);
		label_desc.setHorizontalAlignment(SwingConstants.CENTER);
		label_desc.setText(""+
			"<html><center>"+
			"Welcome "+nickname+" ("+username+") in Towers of Hanoi!<br>"+
			"RULES:<br>"+
			"Move all disks from the first tower to the third tower.<br>"+
			"However, you can only move one disk at the time and<br>"+
			"you can't place bigger disks on top of the smaller disks."+
			"</center></html>"
		);	
		spin_ne.getTextField().setEditable(false);
		label_desc.setFont(new Font("Helvetica", Font.PLAIN, 14));		
		label_naslov.setFont(new Font("Helvetica", Font.PLAIN, 30));	
		btn_play.setFont(new Font("Helvetica", Font.BOLD, 15));
		btn_changePass.setFont(new Font("Helvetica", Font.BOLD, 15));
		btn_scores.setFont(new Font("Helvetica", Font.BOLD, 15));
		btn_exit.setFont(new Font("Helvetica", Font.BOLD, 15));	
		btn_set.setFont(new Font("Helvetica", Font.BOLD, 15));
		btn_back.setFont(new Font("Helvetica", Font.BOLD, 15));
		spin_disks.setFont(new Font("Helvetica", Font.PLAIN, 15));
		label_spin.setFont(new Font("Helvetica", Font.PLAIN, 15));
		label_desc.setOpaque(false);
		label_spin.setOpaque(false);
		spin_disks.setOpaque(false);
		spin_ne.setOpaque(false);
		
		// Element Coloring
		cp.setBackground(new Color(37,37,38));	
		label_naslov.setForeground(new Color(240,240,240));	
		label_desc.setForeground(new Color(240,240,240));
		label_spin.setForeground(new Color(240,240,240));		
		btn_play.setBackground(new Color(0,122,204));
		btn_play.setForeground(new Color(240,240,240));
		btn_changePass.setBackground(new Color(0,122,204));
		btn_changePass.setForeground(new Color(240,240,240));
		btn_scores.setBackground(new Color(0,122,204));
		btn_scores.setForeground(new Color(240,240,240));
		btn_exit.setBackground(new Color(0,122,204));
		btn_exit.setForeground(new Color(240,240,240));	
		btn_back.setBackground(new Color(0,122,204));
		btn_back.setForeground(new Color(240,240,240));
		btn_set.setBackground(new Color(0,122,204));
		btn_set.setForeground(new Color(240,240,240));		
		spin_disks.setForeground(new Color(240,240,240));				
		
				

		// Element Positioning
		label_naslov.setBounds(60, 60, 300, 30);
		label_desc.setBounds(20, 100, 400, 100);		
		btn_play.setBounds(110, 230, 200, 30);
		btn_scores.setBounds(110,270,200,30);
		btn_changePass.setBounds(110, 320, 200, 30);
		btn_exit.setBounds(110, 360, 200, 30);
		btn_set.setBounds(110, 290, 200, 30);
		btn_back.setBounds(110, 330, 200, 30);
		label_spin.setBounds(110, 205, 200, 25);
		spin_disks.setBounds(110, 230, 200, 50);
		
		

		// Action Listeners
		btn_play.addActionListener(e -> {
			btn_play.setVisible(false);				
			btn_changePass.setVisible(false);	
			btn_scores.setVisible(false);			
			btn_exit.setVisible(false);
			btn_back.setVisible(true);
			spin_disks.setVisible(true);
			btn_set.setVisible(true);
			label_spin.setVisible(true);			
			label_desc.setText(""+
				"<html><center>"+
				"To move disks around, you will need to<br>"+
				"use the arrow keys on your keyboard.<br>"+
				"You can restart on any point by pressing F5."+
				"</center></html>"
			);
			repaint();			
		});

		btn_set.addActionListener(e -> {
			try {
				int numOfDisks = (int) spin_disks.getValue();
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						new GameFrame(numOfDisks);
					}
				});
				dispose();
									
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error parsing the number of disks", "Error", JOptionPane.ERROR_MESSAGE);					
			}
		});

		
		btn_changePass.addActionListener(e -> {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					new ResetPassword();
				}
			});
			dispose();
		});

		btn_scores.addActionListener(e -> {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					new Scoreboard();										
				}				
			});
			dispose();
		});

		btn_exit.addActionListener(e -> {
			dispose();			
		});
		
		btn_back.addActionListener(e -> {
			btn_play.setVisible(true);				
			btn_changePass.setVisible(true);				
			btn_scores.setVisible(true);
			btn_exit.setVisible(true);
			btn_back.setVisible(false);
			spin_disks.setVisible(false);
			btn_set.setVisible(false);
			label_spin.setVisible(false);
			label_desc.setText(""+
				"<html><center>"+
				"Welcome "+nickname+" ("+username+") in Towers of Hanoi!<br>"+
				"RULES:<br>"+
				"Move all disks from the first tower to the third tower.<br>"+
				"However, you can only move one disk at the time and<br>"+
				"you can't place bigger disks on top of the smaller disks."+
				"</center></html>"
			);
			repaint();
			
		});

		// Adding
		cp.add(label_naslov);
		cp.add(label_desc);
		cp.add(btn_play);
		cp.add(btn_changePass);
		cp.add(btn_scores);
		cp.add(btn_exit);
		cp.add(btn_set);
		cp.add(btn_back);
		cp.add(spin_disks);
		cp.add(label_spin);
		
		

		// Post
		this.setVisible(true);
		btn_set.setVisible(false);
		spin_disks.setVisible(false);
		label_spin.setVisible(false);		
		btn_back.setVisible(false);
		repaint();
		
	}
}
