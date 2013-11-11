package main.sys;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

import javax.imageio.ImageIO;

import main.Defines;


public final class Info {
	
	private static final String OS_NAME_PROPERTY_KEY = "os.name";
	
	public static int getCPUCores(){
		return Runtime.getRuntime().availableProcessors();
	}
	
	public static String getOSName(){
		return System.getProperty(OS_NAME_PROPERTY_KEY).toLowerCase();
	}
	
	public static int getScreenWidth(){
		return Toolkit.getDefaultToolkit().getScreenSize().width;
	}
	
	public static int getScreenHeight(){
		return Toolkit.getDefaultToolkit().getScreenSize().height;
	}
	
	public static String getDefaultLanguage(){
		return Locale.getDefault().getLanguage().toString().toLowerCase();
	}
	
	public static String getFileSeparator(){
		return System.getProperty("file.separator");
	}
	
	public static long getTime(){
		return System.currentTimeMillis();
	}
	
	public static long getPreciseTime(){
		return System.nanoTime();
	}
	
	public static String getLineSeparator(){
		return System.getProperty("line.separator");
	}
	
	public static String getScreenImageType(){
		GraphicsConfiguration gfxConfig = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		return gfxConfig.getColorModel().toString();
	}
	
	public static String getClassPath(){
		return System.getProperty("java.class.path");
	}
	
	public static BufferedImage getBufferedImageFromResource(String name){
		File f_image = new File(Defines.modelsPath + name);
		BufferedImage tempData = null;
		try {
			tempData = ImageIO.read(f_image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		GraphicsConfiguration gfx_config = GraphicsEnvironment.
				getLocalGraphicsEnvironment().getDefaultScreenDevice().
				getDefaultConfiguration();
		
		BufferedImage newImage = gfx_config.createCompatibleImage(tempData.getWidth(null), tempData.getHeight(null), Transparency.BITMASK);
	
		newImage.createGraphics().drawImage(tempData, 0, 0, null);
		
		return newImage;
	}

}
