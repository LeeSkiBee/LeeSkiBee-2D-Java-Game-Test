package main;

import java.util.HashMap;
import java.util.Map;

public abstract class Settings {
	
	public final static String V_FALSE = "0";
	public final static String V_TRUE = "1";
	
	public final static String S_ENABLE_OPEN_GL_PIPELINING = "open_gl_pipeline";
	public final static String S_SELECTIVE_HP_BAR_DISPLAY = "selective_hp_bars";

	private static Map<String, String> settings = new HashMap<>();
	
	public static void initSettings(){
		initDefaultSettings();
	}
	
	private static void initDefaultSettings(){
		settings.put(S_ENABLE_OPEN_GL_PIPELINING, V_TRUE);
		settings.put(S_SELECTIVE_HP_BAR_DISPLAY, V_TRUE);
	}
	
	public static String getSetting(String key){
		if( settings.containsKey(key) ){
			return settings.get(key);
		} else {
			return "";
		}
	}
}
