package engine.utils;

import test.Main;

public class Logger {
	
	public static void log(Level level, Object... info) {
		String message = "";
		for(Object obj:info) {
			message += obj.toString();
		}
		
		if(level.getLevel() >= 2) {
			System.err.println(level.getName().toUpperCase() + ": " + message);
			
			if(level.getLevel() == 3) Main.window.stop();
			
			return;
		}
		
		System.out.println(level.getName().toUpperCase() + ": " + message);
	}
	
}
