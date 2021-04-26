package game;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import userinfo.ResetPassword;

public class Game extends JFrame {
	private static final long serialVersionUID = 1L;
	Game frame;
	static int width = 1280;
	static int height = 720;
	int numberOfDisks;	
	static int towerWidth;
	static int towerHeight;
	static int diskHeight;
	static int diskWidth;
	static int xTower1;
	static int xTower2;
	static int xTower3;
	static int yTower;

	static int moves = 0;
	static boolean isSelectedTower1 = true;
	static boolean isSelectedTower2 = false;
	static boolean isSelectedTower3 = false;
	static boolean gameFinished = false;	
	static Color brown = new Color(160,82,45);
	static Color light_blue = new Color(0, 122, 204);
	static Color candy_red = new Color(255,41,41);	
	static ArrayList<Disk> all_disks = new ArrayList<Disk>();	

	Tower tower1 = new Tower(xTower1);
	Tower tower2 = new Tower(xTower2);
	Tower tower3 = new Tower(xTower3);
	Tower prevTower = null;	
	Disk carriedDisk = null;

	
	public Game(int numDisks) {
		// Init
		frame = this;
		numberOfDisks = numDisks;
		moves = 0;
		varCalc();
			
		
		// Frame Properties
		setTitle("Towers of Hanoi - Game");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setSize(new Dimension(width, height));		
		setLocationRelativeTo(null);				
		setResizable(true);
		setFocusable(true);	

		
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
				varCalc();			
				
			}

			@Override
			public void componentShown(ComponentEvent e) {				
				
			}
			
		});
		addKeyListener(new KeyAdapter() {
			@Override
    		public void keyPressed (KeyEvent e) {
				int key = e.getKeyCode();
				
				if (key == KeyEvent.VK_RIGHT) {
					if (Game.isSelectedTower1) {
						Game.isSelectedTower2=true;
						Game.isSelectedTower1=false;
					} else if (Game.isSelectedTower2) {
						Game.isSelectedTower3=true;
						Game.isSelectedTower2=false;
					} else {
						Game.isSelectedTower1=true;
						Game.isSelectedTower3=false;
					}
				}
				if (key == KeyEvent.VK_LEFT) {
					if (Game.isSelectedTower1) {
						Game.isSelectedTower3=true;
						Game.isSelectedTower1=false;
					} else if (Game.isSelectedTower3) {
						Game.isSelectedTower2=true;
						Game.isSelectedTower3=false;
					} else {
						Game.isSelectedTower1=true;
						Game.isSelectedTower2=false;
					}
				} 
				
				if (key == KeyEvent.VK_UP) {
					if (carriedDisk == null) {
						if (Game.isSelectedTower1) {
							carriedDisk = tower1.pop();
							prevTower = tower1;
						} else if (Game.isSelectedTower2) {
							carriedDisk = tower2.pop();
							prevTower = tower2;
						} else {
							carriedDisk = tower3.pop();
							prevTower = tower3;
						}
					}
				}
				if (key == KeyEvent.VK_DOWN) {
					if (carriedDisk != null) {
						if (Game.isSelectedTower1) {
							if (tower1.peek() == null || tower1.peek().getLength()>carriedDisk.getLength()) {
								tower1.push(carriedDisk);
								carriedDisk = null;
								if (!prevTower.equals(tower1)) {
									moves++;									
								}
								
							}
						} else if (Game.isSelectedTower2) {
							if (tower2.peek() == null || tower2.peek().getLength()>carriedDisk.getLength()) {
								tower2.push(carriedDisk);
								carriedDisk = null;
								if (!prevTower.equals(tower2)) {
									moves++;
								}
							}
						} else {
							if (tower3.peek() == null || tower3.peek().getLength()>carriedDisk.getLength()) {
								tower3.push(carriedDisk);
								carriedDisk = null;
								if (!prevTower.equals(tower3)) {
									moves++;
								}
							}
						}						
					}
					
					checkMove();
				}
				repaint();
			}
		});
		addWindowListener(new WindowListener() {
			@Override
			public void windowActivated(WindowEvent e) {
								
			}

			@Override
			public void windowClosed(WindowEvent e) {
				new Menu();
				
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
				varCalc();
				
			}
			
		});

		
		
		// Game things
		for (int i = numberOfDisks; i > 0;i--) {						
			tower1.push(new Disk(i));
			tower1.peek().setWidth(diskWidth*i);
			tower1.peek().setHeight(diskHeight);			
			all_disks.add(tower1.peek());
		}
		
	}

	public void varCalc() {
		towerWidth = (int)((width*0.03)*(1+numberOfDisks/150)); // 1280 (10d) ... 40
		towerHeight = (int)((height*0.40)*(1+numberOfDisks/100)); // 720 (10d) ... 316
		diskHeight = (int)(height*0.027777);
		diskWidth = (int)((width*0.0320));		
		xTower1 = (int)(width*0.150871); // 1280 (10d) ... 193
		xTower2 = (int)((width/2)-(towerWidth/2));
		xTower3 = (int)(width-(int)(width*0.150871));
		yTower = height-towerHeight;
		tower1.setTowerX(xTower1);
		tower2.setTowerX(xTower2);
		tower3.setTowerX(xTower3);
		for (Disk d : all_disks) {
			d.setWidth(diskWidth);
			d.setHeight(diskHeight);
		}
		validate();
		repaint();
		
		
	}

	private void checkMove() {		
		repaint();
		if (tower3.count() == numberOfDisks) {			
			JOptionPane.showMessageDialog(null, "Congratulations! You did it in "+moves+" moves. Optimal number of moves is " + (int)(Math.pow(2, numberOfDisks)-1)+".", "Finished", JOptionPane.INFORMATION_MESSAGE);
			File score = new File("score.csv");			
			try {
				PrintWriter pw = new PrintWriter(score);					
				String name = ResetPassword.getCurrentNickname()+",("+ResetPassword.getCurrentUsername()+")";
				pw.println(name+","+numberOfDisks+","+moves);
				pw.append("\n");
				pw.close();
			} catch (IOException ioe) {				
				ioe.printStackTrace();				
			}			
			dispose();
		}
	}

	// Risanje
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		// Background
		Graphics2D g2 = (Graphics2D) g;
        GradientPaint gp = new GradientPaint(0, 0, Color.WHITE, 0, height, light_blue);
        g2.setPaint(gp);
        g2.fillRect(0, 0, width, height);

		// Moves
		g.setColor(Color.black);
		g.setFont(new Font("Helvetica",1,24));
		g.drawString("Moves: "+moves, 50, 75);
		
		
		// Tower 1
		g.setColor(brown);
		g.fillRect(xTower1, yTower, towerWidth, towerHeight);
		g.setColor(Color.black);
		g.drawRect(xTower1, yTower, towerWidth, towerHeight);
		
		// Tower 2
		g.setColor(brown);
		g.fillRect(xTower2, yTower, towerWidth, towerHeight);
		g.setColor(Color.black);
		g.drawRect(xTower2, yTower, towerWidth, towerHeight);
		
		// Tower 3
		g.setColor(brown);
		g.fillRect(xTower3, yTower, towerWidth, towerHeight);
		g.setColor(Color.black);
		g.drawRect(xTower3, yTower, towerWidth, towerHeight);
		
		// Disks
		tower1.drawDisks(g);
		tower2.drawDisks(g);
		tower3.drawDisks(g);
		
		// Selected Disk
		if (carriedDisk != null) {
			int xPos = 0;
			if (isSelectedTower1) {
				xPos = xTower1 + (towerWidth-carriedDisk.getWidth())/2;
			} else if (isSelectedTower2) {
				xPos = xTower2 + (towerWidth-carriedDisk.getWidth())/2;
			} else {
				xPos = xTower3 + (towerWidth-carriedDisk.getWidth())/2;
			}
			g.setColor(candy_red);
			g.fillRect(xPos, yTower-20, carriedDisk.getWidth(), carriedDisk.getHeight());
			g.setColor(Color.black);
			g.drawRect(xPos, yTower-20, carriedDisk.getWidth(), carriedDisk.getHeight());
		}
		
		// Outline
		g.setColor(Color.RED);		
		if (isSelectedTower1) {
			int lastDiskWidth = diskWidth*numberOfDisks;
			int x_obroba = (xTower1+towerWidth/2)-(int)(width*0.007812)-lastDiskWidth/2;
			int y_obroba = yTower-(int)(height*0.041666);
			int width_obroba = (int)(width*0.015625)+lastDiskWidth;
			int height_obroba = towerHeight+(int)(height*0.041666);

			g.drawLine(x_obroba,y_obroba,x_obroba,y_obroba+height_obroba); // LEFT LINE
			g.drawLine(x_obroba, y_obroba, x_obroba+width_obroba, y_obroba); // UPPER LINE
			g.drawLine(x_obroba+width_obroba, y_obroba, x_obroba+width_obroba, y_obroba+height_obroba); // RIGHT LINE			
		} else if (isSelectedTower2) {
			int lastDiskWidth = diskWidth*numberOfDisks;
			int x_obroba = (xTower2+towerWidth/2)-(int)(width*0.007812)-lastDiskWidth/2;
			int y_obroba = yTower-(int)(height*0.041666);
			int width_obroba = (int)(width*0.015625)+lastDiskWidth;
			int height_obroba = towerHeight+(int)(height*0.041666);

			g.drawLine(x_obroba,y_obroba,x_obroba,y_obroba+height_obroba); // LEFT LINE
			g.drawLine(x_obroba, y_obroba, x_obroba+width_obroba, y_obroba); // UPPER LINE
			g.drawLine(x_obroba+width_obroba, y_obroba, x_obroba+width_obroba, y_obroba+height_obroba); // RIGHT LINE	
		} else {
			int lastDiskWidth = diskWidth*numberOfDisks;
			int x_obroba = (xTower3+towerWidth/2)-(int)(width*0.007812)-lastDiskWidth/2;
			int y_obroba = yTower-(int)(height*0.041666);
			int width_obroba = (int)(width*0.015625)+lastDiskWidth;
			int height_obroba = towerHeight+(int)(height*0.041666);

			g.drawLine(x_obroba,y_obroba,x_obroba,y_obroba+height_obroba); // LEFT LINE
			g.drawLine(x_obroba, y_obroba, x_obroba+width_obroba, y_obroba); // UPPER LINE
			g.drawLine(x_obroba+width_obroba, y_obroba, x_obroba+width_obroba, y_obroba+height_obroba); // RIGHT LINE	
		}
		
	}

}
