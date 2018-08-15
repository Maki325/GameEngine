package engine.utils;

public class Level {

	public static Level INFO = new Level(0, "INFO");
	public static Level WARNING = new Level(1, "WARNING");
	public static Level ERROR = new Level(2, "ERROR");
	public static Level SEVERE = new Level(3, "SEVERE");
	
	private int level;
	private String name;
	
	private Level(int level, String name) {
		this.level = level;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public int getLevel() {
		return level;
	}
	
}
