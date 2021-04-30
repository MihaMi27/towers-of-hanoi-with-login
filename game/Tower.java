package game;
import java.awt.Color;
import java.awt.Graphics;

public class Tower {	
	private Disk topDisk;
	private int tower_xPos;	
	private GamePanel panel;
	
	public Tower(int x) {
		tower_xPos = x;				
	}

	public void setPanel(GamePanel p) {
		panel = p;
	}

	public void setTowerX(int x) {
		tower_xPos = x;
	}
	
	
	public Disk getTopDisk() {
		return topDisk;
	}
	
	
	public void setTopDisk(Disk topDisk) {
		this.topDisk = topDisk;
	}
	
	// Doda disk na stack
	public void push (Disk disk) {
		if (topDisk == null) {
			topDisk = disk;
		} else {
			Disk currentDisk = topDisk;
			while (currentDisk.getNext() != null) {
				currentDisk = currentDisk.getNext();
			}
			currentDisk.setNext(disk);
		}
	}
	
	// Odstrani vrhnji disk iz stacka in ga vrne
	public Disk pop() {
		Disk popDisk = null;
		if (count() == 1) {
			popDisk = topDisk;
			topDisk = null;
		} else if (count() > 1) {
			Disk currentDisk = topDisk;
			for (int i = 1; i < count()-1; i++) {
				currentDisk = currentDisk.getNext();
			}
			popDisk = currentDisk.getNext();
			currentDisk.setNext(null);
		}
		return popDisk;
	}
	// Pogleda top disk
	public Disk peek() {
		if (count() > 0) {
			Disk currentDisk = topDisk;
			while (currentDisk.getNext() != null) {
				currentDisk = currentDisk.getNext();
			}
			return currentDisk;
		} else {
			return null;
		}
	}
	
	// Presteje diske v stacku
	public int count() {
		int count = 0;
		Disk currentDisk = topDisk;
		while (currentDisk != null) {
			count++;
			currentDisk = currentDisk.getNext();
		}
		return count;
	}
	
	// Narise diske
	public void drawDisks(Graphics g) {
		Disk currentDisk = topDisk;		
		for (int i = 0; i < count(); i++) {
			int xPos = tower_xPos+(GamePanel.towerWidth/2)-(currentDisk.getWidth()/2);			
			int yPos = panel.getHeight()-(currentDisk.getHeight()*(i+1));
			g.setColor(GamePanel.COLOR_DISK);
			g.fillRect(xPos, yPos, currentDisk.getWidth(), currentDisk.getHeight());
			g.setColor(Color.black);
			g.drawRect(xPos,yPos, currentDisk.getWidth(), currentDisk.getHeight());
			currentDisk = currentDisk.getNext();
		}
	}
	
}
