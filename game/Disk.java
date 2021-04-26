package game;
public class Disk {
	private int length;
	private int height;
	private int width;
	private Disk next;
	
	public Disk (int length) {
		super();
		this.length = length;		
		this.next = null;
	}
	
	public int getWidth() {
		return width;
	}

	public int getLength() {
		return length;
	}

	public int getHeight() {
		return height;
	}
	
	public void setWidth(int width) {
		this.width = width*length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Disk getNext() {
		return next;
	}
	
	public void setNext(Disk next) {
		this.next = next;
	}
}
