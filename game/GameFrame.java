package game;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.*;

import javax.swing.*;

public class GameFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	static int width;
	static int height;
	static int widthPanel;
    static int heightPanel;
    static int x_ingameText;
	static int y_ingameText;	
    static int width_ingameText;
    static int height_ingameText;
	static Font font_ingameText;	
	GamePanel game;
	
	JLabel label_moves = new JLabel("<html><center>Moves: 0</center></html>",SwingConstants.CENTER);
	JLabel label_instructions = new JLabel("<html><center>←↑→↓ = Movement<br>F5 = Restart</center></html>",SwingConstants.CENTER);	

	public GameFrame(int numDisks) {
		// Init
		width = 1280;
		height = 720;
		game = new GamePanel(numDisks,this);
		JLayeredPane layeredPane = getLayeredPane();        
        JPanel panel_ingameText = new JPanel();
		
		// Frame Properties		
		setTitle("Towers of Hanoi - Game");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setSize(new Dimension(width, height));		
		setLocationRelativeTo(null);				
		setResizable(true);
		setFocusable(false);
		setVisible(true);
		varCalc();
		

		// Element Properties
		panel_ingameText.setLayout(new BorderLayout());
        panel_ingameText.setOpaque(false);
        label_moves.setFont(font_ingameText);
        label_instructions.setFont(font_ingameText);
        label_moves.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        label_instructions.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		
		panel_ingameText.setBorder(BorderFactory.createLineBorder(Color.red));

		// Element Positioning
		game.setBounds(layeredPane.getBounds());
		layeredPane.setSize(getSize());
		getContentPane().setSize(getSize());
		panel_ingameText.setBounds(x_ingameText,y_ingameText,width_ingameText,height_ingameText);
		
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
				varCalc();
				game.setBounds(layeredPane.getBounds());
				layeredPane.setSize(getSize());
				getContentPane().setSize(getSize());
				revalidate();				
				panel_ingameText.setBounds(x_ingameText,y_ingameText,width_ingameText,height_ingameText);
				panel_ingameText.validate();
                repaint();
				
				
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
					@Override
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
		panel_ingameText.add(label_moves,BorderLayout.NORTH);
        panel_ingameText.add(label_instructions,BorderLayout.SOUTH);
        layeredPane.add(game,new Integer(1));
        layeredPane.add(panel_ingameText,new Integer(2));
		
	}

	public void varCalc() {
        width = this.getWidth();
        height = this.getHeight();        
        width_ingameText = getWidth();
        height_ingameText = getHeight()/6;
        x_ingameText = 0;
		y_ingameText = (int)(height*0.05);
		font_ingameText = new Font("Helvetica",Font.BOLD,20);
		repaint();
    }

	public void setMoves() {
		label_moves.setText("<html><center>Moves: "+game.moves+"</center></html>");
	}

	

	

}
