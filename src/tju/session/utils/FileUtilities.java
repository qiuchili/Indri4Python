/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tju.session.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 *
 * @author iaa
 */
public class FileUtilities {
	public static void write2FileInUTF8(String str, String outputPath){
		try {
			File file = new File(outputPath);
			Writer writer = new OutputStreamWriter(new FileOutputStream(file,true), "UTF-8");
			writer.write(str);
			writer.close();
		} catch (Exception e) {
			System.out.println("Writing to file error- " + e);
		}
	}
	/**
	 *  Appends a given textual contents to an existing text file.
	 *  Creates the file if it does not exist before adding textual contents
	 * @param str Given textual contents
	 * @param outputFilepath Full path of text file to add textual contents
	 */
	public static void write_append2file(String str, String outputFilepath) {
		try {
			File f = new File(outputFilepath);
			
			if (!f.exists()) {
				BufferedWriter bw = new BufferedWriter(new FileWriter(f));
				bw.write("");
				bw.close();
			}
			FileWriter writer = new FileWriter(f,true);
//			System.out.println(writer.getEncoding());;
			BufferedWriter bw = new BufferedWriter(writer);
			bw.append(str);
			bw.close();

		} catch (Exception e) {
			System.out.println("Writing to file error- " + e);
		}
	}
	
	/**
	 *  Persists a java Object.
	 *  <p>Any existing file with same name will be overwritten!
	 * @param obj Given Object
	 * @param outputFilepath Full path of file to add Object
	 */
	public static void persistObject(Object obj, String outputFilepath) {
		try {
			String tmp= outputFilepath;
			File f = new File(outputFilepath);

			if (f.exists()) {
				System.out.println("Current file exists, _ added to newly created file!!!");
				tmp= outputFilepath+ "_";				
			}
			OutputStream fos = new FileOutputStream(tmp);
			OutputStream buffer = new BufferedOutputStream( fos );
			ObjectOutput oos = new ObjectOutputStream(buffer);

			try{
				oos.writeObject(obj);
			}
			finally{
				oos.close();
			}
			oos.close();



		} catch (Exception e) {
			System.out.println("Writing Object to file error- " + e);
		}
	}

	/**
	 * 
	 * @param obj
	 * @param outputFilepath
	 */
	public static Object getPersistedObject(String outputFilepath) {
		Object obj= null;
		try {
			InputStream fis = new FileInputStream(outputFilepath);
			InputStream buffer = new BufferedInputStream(fis);
			ObjectInput ois = new ObjectInputStream(buffer);

			try{
				obj = ois.readObject();

			}
			finally{
				ois.close();
			}
		} catch (Exception e) {
			System.out.println("Reading Object from file error- " + e);
			return null;
		}

		return obj;
	}

	/**
	 * Deletes a persistent file
	 * @param filepath
	 */
	public static void delete(String filepath) {
		try {
			File f = new File(filepath);
			if (f.exists()) {
				if (!f.delete()) {
					System.out.println("File cannot be deleted");
				}
			}
		} catch (Exception e) {
			System.out.println("Deleting to file error- " + e);
		}
	}

	/**
	 * Merges and sorts all collectionTokens file in a given directory
	 * @param directory CollectionTokens directory
	 */
	public static void merge_RemoveDuplicates_SortCollectionTokens(String directoryPath) {
		ArrayList<String> combined = new ArrayList<String>();
		File directory = new File(directoryPath);
		if (!directory.exists()) {
			System.out.println("File/Directory does not exist");
			return;
		} else if (directory.isDirectory()) {
			if (directory.list().length != 0) {
				String[] filenames = directory.list();
				for (int i = 0; i < filenames.length; i++) {
					String input = directoryPath + "/" + filenames[i];
					System.out.println("Parsing " + (i + 1) + " out of " + filenames.length + " files");
					File testDir = new File(input);
					if (testDir.isDirectory()) {
						System.out.println(input + " is a directory");
						continue;
					} else if (filenames[i].contains("collectionTokens")) {
						ArrayList<String> tmp = readTextFileAsArrayList(input);
						for (String s : tmp) {
							if (!combined.contains(s)) {
								combined.add(s);
							}
						}
					} else {
						System.out.println(input + " is being ignored!");
						continue;
					}
					System.out.println("Combined  " + i + " out of " + filenames.length + " files");
				}
				Collections.sort(combined);
				String output = directoryPath + "/collectionTokens.txt";
				for (String s : combined) {
					write_append2file(s + "\n", output);
				}
			}
		}
	}

	public static String removeDuplicates_Sort(String contents, String demarcator) {
		String tmp="";
		ArrayList<String> sorted = new ArrayList<String>();
		String[] components= contents.split(demarcator);
		for (String s : components) {
			if (!sorted.contains(s)) {
				sorted.add(s);
			}
		}
		Collections.sort(sorted);
		for(String s: sorted){
			tmp+= s+ "\n";
		}
		return tmp;
	}

	/**
	 * Combines the contents of all text files in a given directory by concatenating them
	 * leaving three empty lines between each file contents.
	 * @param inputDirectoryPath directory containing text files
	 * @param outputFilename Name of the output file place in the same directory
	 */
	public static void combine(String inputDirectoryPath, String outputFilename) {
		File directory = new File(inputDirectoryPath);
		if (!directory.exists()) {
			System.out.println("File/Directory does not exist");
			return;
		} else if (directory.isDirectory()) {
			if (directory.list().length != 0) {
				String[] filenames = directory.list();
				for (int i = 0; i < filenames.length; i++) {
					String input = inputDirectoryPath + "/" + filenames[i];
					String output = inputDirectoryPath + "/" + outputFilename;
					String combined = readTextFile(input) + "\n\n\n\n";
					write_append2file(combined, output);
					System.out.println("Combined  " + i + " out of " + filenames.length + " files");
				}
			}
		}
	}

	/**
	 * Reads a specified line of a give text file containing the relevance matrix.
	 * The line is split and the contents returned as an ArrayList of numbers
	 * @param filename Full path of the text file to be read
	 * @param lineNumber Line number to be read
	 * @return ArrayList with each integer in the line as an item
	 */
	public static ArrayList<Integer> readTextFileAsArrayList(String filename, int lineNumber) {
		ArrayList<Integer> contents = new ArrayList<Integer>();
		try {
			BufferedReader thereader = new BufferedReader(new FileReader(filename));
			String line = null;
			int num = 1;
			while ((line = thereader.readLine()) != null) {
				if (num == lineNumber) {
					if(line.trim().equals("")){
						break;
					}
					String[] str = line.split(" ");
					for (int i = 0; i < str.length; i++) {
						contents.add(Integer.parseInt(str[i].trim()));
					}
					break;
				}
				num++;
			}
			thereader.close();
		} catch (Exception e) {
			System.out.println("read file to ArrayList ERROR- " + e);
		}
		return contents;
	}

	/**
	 * Reads the contents of a text file with each line as an item in an ArrayList.
	 * Only those lines that starts with the given string are returned.
	 * @param filename Full path of the text file to be read
	 * @param startsWith Text at the beginning of line
	 * @return ArrayList with each line as an item
	 */
	public static ArrayList<String> readTextFileAsArrayList(String filename, String startsWith) {
		ArrayList<String> contents = new ArrayList<String>();
		try {
			BufferedReader thereader = new BufferedReader(new FileReader(filename));
			String line = null;
			while ((line = thereader.readLine()) != null) {
				if (line.startsWith(startsWith)) {
					contents.add(line);
				}
			}
			thereader.close();
		} catch (Exception e) {
			System.out.println("read file to ArrayList ERROR- " + e);
			//            e.printStackTrace();
		}
		return contents;
	}

	/**
	 * Reads the contents of a text file
	 * @param filename Full path of the text file to be read
	 * @return ArrayList with each line as an item
	 */
	public static ArrayList<String> readTextFileAsArrayList(String filename, short lastline) {
		ArrayList<String> contents = new ArrayList<String>();
		try {
			BufferedReader thereader = new BufferedReader(new FileReader(filename));
			String line = null;
			int num = 1;
			while ((line = thereader.readLine()) != null) {
				if(num<lastline){
					contents.add(line);
					num++;
				}else{
					break;
				}
			}
			thereader.close();
		} catch (Exception e) {
			System.out.println("read file to ArrayList ERROR- " + e);
			//            e.printStackTrace();
		}
		return contents;
	}

	/**
	 * Reads the contents of a text file
	 * @param filename Full path of the text file to be read
	 * @return ArrayList with each line as an item
	 */
	public static ArrayList<String> readTextFileAsArrayList(String filename) {
		ArrayList<String> contents = new ArrayList<String>();
		try {
			BufferedReader thereader = new BufferedReader(new FileReader(filename));
			String line = null;
			while ((line = thereader.readLine()) != null) {
				contents.add(line);
			}
			thereader.close();
		} catch (Exception e) {
			System.out.println("read file to ArrayList ERROR- " + e);
			//            e.printStackTrace();
		}
		return contents;
	}

	/**
	 * Reads the contents of a text file and remove duplicates
	 * @param filename Full path of the text file to be read
	 * @return ArrayList with each line as an item
	 */
	public static ArrayList<String> readTextFileAsArrayList_RemoveDuplicates(String filename) {
		ArrayList<String> contents = new ArrayList<String>();
		try {
			BufferedReader thereader = new BufferedReader(new FileReader(filename));
			String line = null;
			while ((line = thereader.readLine()) != null) {
				if(!contents.contains(line))
					contents.add(line);
			}
			thereader.close();
		} catch (Exception e) {
			System.out.println("read file to ArrayList ERROR- " + e);
			//            e.printStackTrace();
		}
		return contents;
	}
	
	public static HashSet<String> readTextFileAsSet(String filename) {
		HashSet<String> contents = new HashSet<String>();
		try {
			BufferedReader thereader = new BufferedReader(new FileReader(filename));
			String line = null;
			while ((line = thereader.readLine()) != null) {
					contents.add(line);
			}
			thereader.close();
		} catch (Exception e) {
			System.out.println("read file to ArrayList ERROR- " + e);
			//            e.printStackTrace();
		}
		return contents;
	}
	/**
	 * Reads the contents of a text file
	 * @param filename Full path of the text file to be read
	 * @return File contents as a string
	 */
	public static String readTextFile(String filename) {
		String contents = "";
		try {
			BufferedReader thereader = new BufferedReader(new FileReader(filename));
			String line = null;
			while ((line = thereader.readLine()) != null) {
				contents += line + "\n";
			}
			thereader.close();
		} catch (Exception e) {
			System.out.println("read file ERROR");
			//            e.printStackTrace();
		}

		return contents;
	}

}
