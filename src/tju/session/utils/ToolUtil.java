package tju.session.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;

import org.tju.ir.bases.ByStrDoubleValue;
import org.tju.ir.bases.StrDoublePair;

public class ToolUtil {
//	static StopList _stopList = new StopList();
	/**
	 * Return the soft intersection of two set, each element of the set is weighted with a double number.
	 * The weight has been normalized.
	 * @param set_1
	 * @param set_2
	 * @return
	 */
	public static HashMap<String,Double> getSoftIntersection(HashMap<String,Double> set_1,HashMap<String,Double> set_2){
		HashMap<String,Double> softIntersection = new HashMap<String,Double>();
		softIntersection.putAll(set_1);
		if(set_2 == null || set_2.size()==0)
			return softIntersection;
		for(Entry<String,Double> entry : set_2.entrySet()){
			String key_2 = entry.getKey();
			Double value_2 = entry.getValue();
			if(softIntersection.containsKey(key_2)){
				Double value_1 = softIntersection.get(key_2);
				softIntersection.put(key_2, value_1+value_2);
			}else{
				softIntersection.put(key_2, value_2);
			}
		}
		normalize(softIntersection);
		return softIntersection;
	}
	/**
	 * Get the true intersection, the returned weights of the elements are gained through multiplying the 
	 * corresponding original weight of each element.
	 * @param set_1
	 * @param set_2
	 * @return
	 */
	public static HashMap<String,Double> getTrueIntersection(HashMap<String,Double> set_1,HashMap<String,Double> set_2){
		HashMap<String,Double> trueIntersection = new HashMap<String,Double>();
		trueIntersection.putAll(set_1);
		if(trueIntersection.size()==0)
			return trueIntersection;
		for(Entry<String,Double> entry : set_1.entrySet()){
			String key_1 = entry.getKey();
			Double value_1 = entry.getValue();
			if(set_2.containsKey(key_1)){
				Double value_2 = set_2.get(key_1);
				trueIntersection.put(key_1, value_1*value_2);
			}else{
				trueIntersection.remove(key_1);
			}
		}
		normalize(trueIntersection);
		return trueIntersection;
	}
	
	/**
	 * get the intersection of two sets.
	 * @param set_1
	 * @param set_2
	 * @return
	 */
	public static HashSet<String> getSetIntersection(Set<String> set_1,Set<String> set_2){
		HashSet<String> trueIntersection = new HashSet<String>();
		trueIntersection.addAll(set_1);
		if(trueIntersection.size()==0)
			return trueIntersection;
		for(String str : set_1){
			if(!set_2.contains(str)){
//				System.out.println(str);
				trueIntersection.remove(str);
			}
		}
		return trueIntersection;
	}
	
	public static HashSet<String> getSetCombination(Set<String> set_1,Set<String> set_2){
		HashSet<String> setCombination = new HashSet<String>();
		setCombination.addAll(set_1);
		setCombination.removeAll(set_2);
		setCombination.addAll(set_2);
		return setCombination;
	}
	/**
	 * Normalize the map with the max value of the elements.
	 * @param map
	 */
	public static void normalize(Map<String,Double> map){
		Double max = 0.0;
		for(Entry<String,Double> entry : map.entrySet()){
			if(max<entry.getValue())
				max = entry.getValue();
		}
		if(max>0.0)
		{
			for(Entry<String,Double> entry : map.entrySet()){
				String key = entry.getKey();
				Double value = entry.getValue();
				Double newValue = value/max;
				map.put(key, newValue);
			}
		}
	}
	/**
	 * normalize the map with the sum value of the elements
	 * @param map
	 */
	public static void normalize2One(Map<String,Double> map){
		Double sum = 0.0;
		for(Entry<String,Double> entry : map.entrySet()){
			sum += entry.getValue();
		}
		if(sum>0.0)
		{
			for(Entry<String,Double> entry : map.entrySet()){
				String key = entry.getKey();
				Double value = entry.getValue();
				Double newValue = value/sum;
				map.put(key, newValue);
			}
		}
	}
	public static void normalize2One(SortedSet<StrDoublePair> map){
		SortedSet<StrDoublePair> normalizedMap=new TreeSet<StrDoublePair>(new ByStrDoubleValue());
		Double sum = 0.0;
		for(StrDoublePair entry : map){
			sum += entry.value;
		}
		if(sum>0.0)
		{
			for(StrDoublePair entry : map){
				String _key= entry.key;
				Double _value = entry.value;
				Double newValue = _value/sum;
				normalizedMap.add(new StrDoublePair(_key,newValue));
			}
		}
		map.clear();
		map.addAll(normalizedMap);
	}
	/**
	 * normalize the map with the sum value of the elements
	 * @param map
	 */
	public static void normalizeIntDouble(Map<Integer,Double> map){
		Double sum = 0.0;
		for(Entry<Integer,Double> entry : map.entrySet()){
			sum += entry.getValue();
		}
		if(sum>0.0)
		{
			for(Entry<Integer,Double> entry : map.entrySet()){
				Integer key = entry.getKey();
				Double value = entry.getValue();
				Double newValue = value/sum;
				map.put(key, newValue);
			}
		}
	}
	/**
	 * Split a string use several separators including \t |\r\n\",;\'&:-
	 * @param str
	 * @return
	 */
	public static String[] splitString(String str){
		str = str.replace("-", " ").trim();
		str = str.replace("(", " ").trim();
		str = str.replace(")", " ").trim();
		str = str.replace("[", " ").trim();
		str = str.replace("]", " ").trim();
		str = str.replace("'", " ").trim();
		str = str.replaceAll("\\b\\s{2,}\\b", " ");
		Pattern pattern = Pattern.compile("[\t |\r\n\",;\'&:?./#$&!]+");
		String[] array = pattern.split(str);
		return array;
	}
	/**
	 * Split a string to HashSet<String>
	 * @param str
	 * @return
	 */
	public static HashSet<String> split2Set(String str){
		HashSet<String> set = new HashSet<String>();
		
		if(str==null || str.length()==0)
			return set;
		
		str = str.replace("-", " ").trim();
		str = str.replace("(", " ").trim();
		str = str.replace(")", " ").trim();
		str = str.replace("[", " ").trim();
		str = str.replace("]", " ").trim();
		
		Pattern pattern = Pattern.compile("[\t |\r\n\",;\'&:-?./#$&!]+");
		String[] array = pattern.split(str);
		for(String term : array){
			set.add(term);
		}
		return set;
	}
	/**
	 * replace separators with blanks
	 * @param str
	 * @return
	 */
	public static String replaceSeparatorsWithBlank(String str){
		if(str==null)
			return null;
		String[] array = splitString(str);
		String result = "";
		for(String element:array){
			if(element==null||"".equals(element.trim()))
				continue;
			result +=element+" ";
		}
		result = result.trim();
		return result;
	}
	/**
	 * Put a key value pair to a map, with the duplicate elements are combined together, such as (ele,1.0) and (ele,2.0)
	 * are combined into (ele,3.0)
	 * @param map
	 * @param key
	 * @param value
	 */
	public static void PutHashMap(HashMap<String,Double> map,String key,Double value){
		if(!map.containsKey(key)){
			map.put(key, value);
		}else{
			Double originalValue = map.get(key);
			map.put(key, value+originalValue);
		}
	}
	/**
	 * Convert the map to string format,like key1:value1,key2:value2;
	 * @param <T>
	 * @param map
	 * @return
	 */
	public static String MapToString(HashMap<String,Double> map){
		String keyValues = "";
		for(Map.Entry<String, Double> entry:map.entrySet()){
			String key = entry.getKey().toString();
			String value = entry.getValue().toString();
			keyValues += key+":"+value+",";
		}
		if(keyValues.length()>0)
			keyValues = keyValues.substring(0,keyValues.length()-1);
		return keyValues;
	}
	/**
	 * Get the n-grams from a string through scanning. All stop words are removed.
	 * @param string
	 * @param N
	 * @return
	 */
	public static ArrayList<String> GetNGram(String string,int N){
		String pattern = "^[A-Za-z0-9]*$";
		String[] specialString = { "nbsp", "http", "com", "https" };
		ArrayList<String> sString = new ArrayList<String>();
		for(String s:specialString)
		{
			sString.add(s);
		}
		Porter stemmer = new Porter();
		String[] unigrams = ToolUtil.splitString(string.toLowerCase());
		ArrayList<String> unigramsList = new ArrayList<String>();
		for(String term:unigrams){
			//System.out.println(term);
			if(term!=null && term.trim().length()>1&&Pattern.matches(pattern, term)&&!sString.contains(term)){
				term = stemmer.stripAffixes(term);
				unigramsList.add(term);
			}
		}
		ArrayList<String> ngrams = new ArrayList<String>();
		for(int i = 0;i+N<=unigramsList.size();i++){
			String subStr = getSubString(unigramsList,i,i+N-1);
			if(subStr!=null){
				ngrams.add(subStr);
			}
		}
		return ngrams;
	}
	/**
	 * Re-Order the n_gram so that each word in the n_gram is ordered by alphabet sequence.
	 * @param n_gram
	 * @return
	 */
	public static String GetOrderedNGram(String n_gram){
		String new_ngram = "";
        String[] terms = n_gram.toLowerCase().split(" ");
        SortedSet<String> orderedNGram = new TreeSet<String>();
        for(String term:terms)
        {
            orderedNGram.add(term);
        }
        for(String term:orderedNGram)
        {
            new_ngram += term + " "; 
        }
        new_ngram = new_ngram.trim();
        return new_ngram;
	}
	/**
	 * Get the sub string from the string array. If the indexes are out of the array range, return null. 
	 * @param array
	 * @param startIndex
	 * @param endIndex
	 * @return
	 */
	public static String getSubString(ArrayList<String> array,int startIndex,int endIndex){
		if(startIndex<0||endIndex>=array.size())
			return null;
		String ngram = "";
		for(int i=startIndex;i<=endIndex;i++)
		{
			ngram +=array.get(i)+" ";
		}
		return ngram;
	}
	/**
	 * load a df table or a co_df table to memory and return a HashMap object
	 * @param dir
	 * @param objPath
	 */
	public static HashMap<String,Integer> LoadDForCo_DFTable(String path){
		HashMap<String,Integer> table = new HashMap<String,Integer>();
		try {
			FileInputStream stream = new FileInputStream(path);
			BufferedReader br;
			br = new BufferedReader(new InputStreamReader(stream,"UTF-8"));
			String line;
			while((line=br.readLine())!=null){
				//0:user_id,1:query_time,2:query,3:page_num,4:rank_in_page,5:url,6:click_count_in_url
                String[] items = line.split("\t");
                String term = items[0];
                int df = Integer.parseInt(items[1]);
                table.put(term, df);
                //System.out.println("PUT "+term);
			}
			stream.close();
			br.close();
			return table;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println(e1.toString());
			return null;
		}
	}
	public static void main(String[] args){
		Map<Integer, Double> test = new HashMap<Integer,Double>();
		test.put(1, 3.0);
		test.put(2, 5.0);
		test.put(3, 2.0);
		test.put(4, 6.0);
		normalizeIntDouble(test);
		for(Entry<Integer,Double> entry:test.entrySet()){
			System.out.println(entry.getKey()+"\t"+entry.getValue());
		}
	}
}
