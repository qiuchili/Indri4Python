package tju.session.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;

public class StopList {
	static HashSet<String> _stopWords;
	static Porter _stemmer;
	public StopList(){
		String filePath = "stoplist/StopList.txt";
		String objPath = "stoplist/StopList.hashset";
		File stopFile = new File(objPath);
		if(stopFile.exists())
			_stopWords = (HashSet<String>)FileUtilities.getPersistedObject(objPath);
		else
		{
			persistStopWords(filePath, objPath);
			_stopWords = (HashSet<String>)FileUtilities.getPersistedObject(objPath);
		}
		_stemmer = new Porter();
	}
	public StopList(String path){
		_stopWords = new HashSet<String>();
		try {
			FileInputStream stream = new FileInputStream(path);
			BufferedReader br;
			br = new BufferedReader(new InputStreamReader(stream,"UTF-8"));
			String line;
			while((line=br.readLine())!=null){
				String word = line.trim();	
                _stopWords.add(word);
			}
			stream.close();
			br.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	/**
	 * judge if the word is a stop word
	 * @param word
	 * @return
	 */
	public boolean isStopWord(String word){
		return _stopWords.contains(word);
	}
	/**
	 * remove the stop words in a string and return a list of stemmed words in original rank.
	 * the return list can contain duplicate words.
	 * @return
	 */
	public ArrayList<String> removeStopWords(String string)
	{
		String[] terms = ToolUtil.splitString(string.toLowerCase());
		ArrayList<String> words = new ArrayList<String>();
		for(String term:terms){
			if(!isStopWord(term)){
				term = _stemmer.stripAffixes(term);
				words.add(term);
			}
		}
		return words;
	}
	/**
	 * Persist the stop word file to a object path
	 * @param path
	 * @param objPath
	 */
	public void persistStopWords(String path,String objPath)
	{
		HashSet<String> stopWords = new HashSet<String>();
		try {
			FileInputStream stream = new FileInputStream(path);
			BufferedReader br;
			br = new BufferedReader(new InputStreamReader(stream,"UTF-8"));
			String line;
			while((line=br.readLine())!=null){
				String word = line.trim();	
                stopWords.add(word);
			}
			stream.close();
			br.close();
			FileUtilities.persistObject(stopWords, objPath);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
