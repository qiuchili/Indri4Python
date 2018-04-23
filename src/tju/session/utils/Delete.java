package tju.session.utils;

import java.io.File;

public class Delete {
	/**
	 * delete on directory or file, all of the sub directory and files will be deleted meanwhile.
	 * @param path
	 * @return
	 */
	public static boolean delete(String path){
		File file = new File(path);
		if(file.exists()&&file.isFile()){
			return file.delete();
		}
		if(file.exists()&&file.isDirectory()&&file.listFiles().length==0){
			return file.delete();
		}
		if(file.exists()&&file.isDirectory()&&file.listFiles().length!=0){
			for(File subFile:file.listFiles()){
				boolean subFlag = delete(subFile.getAbsolutePath());
				if(!subFlag){
					return false;
				}
			}
			return file.delete();
		}
		return false;
	}
	public static void main(String[] args){
		String path = "D:\\privates\\LJF\\data\\session-experiments\\test\\evaluation\\map";
		boolean ok = delete(path);
		System.out.println(ok);
	}
}
