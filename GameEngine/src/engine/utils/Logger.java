package engine.utils;

import engine.Display;

public class Logger {
	
	public static void log(Level level, Object... info) {
		String message = "";
		for(Object obj:info) {
			message += obj.toString();
		}
		
		if(level.getLevel() >= 2) {
			System.err.println(message);
			
			if(level.getLevel() == 3) {
				Display.isRunning = false;
			}
			
			return;
		}
		
		System.out.println(message);
	}
	
}
