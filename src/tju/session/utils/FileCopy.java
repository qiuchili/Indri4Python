package tju.session.utils;

import java.io.*;
import java.nio.channels.FileChannel;

/**
 * copy a directory from sourceDir to targetDir recursively
 * @author NewStart
 *
 */
public class FileCopy {
	public static void copyDirectory(String sourceDir, String targetDir){
		File sDir = new File(sourceDir);
		File tDir = new File(targetDir);
		if(!tDir.exists())
			tDir.mkdirs();
		if(!sDir.isDirectory())
			return;
		File[] files = sDir.listFiles();
		for(File file:files){
			String tmpName = file.getName();
			if(file.isDirectory()){
				file.setReadable(true);
				String tmpDirPath = file.getAbsolutePath();
				String tmpTargetPath = tDir+"\\"+tmpName;
				copyDirectory(tmpDirPath,tmpTargetPath);
			}else{
				String sourcePath = sourceDir+"\\"+tmpName;
				String targetPath = targetDir+"\\"+tmpName;
				copyFile(sourcePath,targetPath);
			}		
		}
	}
	/**
	 * copy one file from sourcePath to targetPath
	 * @param sourcePath
	 * @param targetPath
	 */
    public static void copyFile(String sourcePath,String targetPath) {
		File source = new File(sourcePath);
		File target = new File(targetPath);
		
        FileChannel in = null;
        FileChannel out = null;
        FileInputStream inStream = null;
        FileOutputStream outStream = null;
        try {
            inStream = new FileInputStream(source);
            outStream = new FileOutputStream(target);
            in = inStream.getChannel();
            out = outStream.getChannel();
            in.transferTo(0, in.size(), out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	try {
				inStream.close();
				in.close();
	        	outStream.close();
	        	out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}        	
        }
    }
    /**
     * main
     * @param args
     */
    public static void main(String[] args){
    	String logDir = "L:\\Jingfei_backup\\2012_07log\\sampled_users_log_1K_sorted";
    	String usersDir = "L:\\Jingfei_backup\\2012_07log\\sampled_users_107\\query_traces";
    	String toDir = "L:\\Jingfei_backup\\2012_07log\\sampled_users_107\\query-log";
    	File users = new File(usersDir);
    	File[] files = users.listFiles();
    	System.out.println(files.length);
    	for(File file:files){
    		String name = file.getName();
    		String fromPath = logDir +"\\"+name;
    		String targetPath = toDir +"\\"+name;
    		copyFile(fromPath, targetPath);
    	}
    }
}
