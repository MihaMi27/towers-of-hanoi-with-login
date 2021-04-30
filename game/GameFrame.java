package game;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	static int width = 1280;
	static int height = 720;
	
	public GameFrame(int numDisks) {
		// Init
		GamePanel game = new GamePanel(numDisks);		

		// Frame Properties
		setTitle("Towers of Hanoi - Game");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setSize(new Dimension(width, height));		
		setLocationRelativeTo(null);				
		setResizable(true);
		setFocusable(false);	

		
		// Listeners
		addComponentListener(new ComponentListener() {
			@Override
			public void componentHidden(ComponentEvent e) {
				
			}

			@Override
			public void componentMoved(ComponentEvent e) {				
				
			}

			@Override
			public void componentResized(ComponentEvent e) {
				width = getWidth();
				height = getHeight();
				
				
			}
			@Override
			public void componentShown(ComponentEvent e) {
				
				
			}

			
			
		});		
		addWindowListener(new WindowListener() {
			@Override
			public void windowActivated(WindowEvent e) {
								
			}

			@Override
			public void windowClosed(WindowEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						new Menu();
					}
				});
				
			}

			@Override
			public void windowClosing(WindowEvent e) {
				int option = JOptionPane.showConfirmDialog(null, "Do you want to quit the game?", "Quit Game", JOptionPane.YES_NO_OPTION);
				if (option == JOptionPane.YES_OPTION) {									
					dispose();
				}
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
								
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
								
			}

			@Override
			public void windowIconified(WindowEvent e) {
								
			}

			@Override
			public void windowOpened(WindowEvent e) {
				
				
			}
			
		});

		// Adding
		getContentPane().add(game);

		// Set Visible
		this.setVisible(true);
		
	}

	

	

}
