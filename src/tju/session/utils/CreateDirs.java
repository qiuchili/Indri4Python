package tju.session.utils;

import java.io.File;

public class CreateDirs {
	public static void create(String dirPath){
		File dir = new File(dirPath);
		if(!dir.exists())
			dir.mkdirs();
	}
}
