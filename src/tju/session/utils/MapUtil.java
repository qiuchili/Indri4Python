package tju.session.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class MapUtil {
	public static Double getVar(Map<String,Double> map){
		if(map==null||map.size()==0)
			return 0d;
		int size = map.size();
		Double expectation = getExpectation(map);
		Double sumSquare = 0d;
		for(Entry<String,Double> entry:map.entrySet()){
			Double value = entry.getValue();
			Double difference = value-expectation;
			Double square = Math.pow(difference, 2);
			sumSquare +=square;
		}
		double variance = sumSquare/size;
		return variance;
	}
	public static Double getExpectation(Map<String,Double> map){
		if(map==null||map.size()==0)
			return 0d;
		Double sumValue = 0d;
		int size = map.size();
		for(Entry<String,Double> entry:map.entrySet()){
			Double value = entry.getValue();
			sumValue +=value;
		}
		Double expectation = sumValue/size;
		return expectation;
	}
	/**
	 * extract a Map from a file, the key column and value column index are keyCols 
	 * and valueCols respectively.
	 * @param path
	 * @param KeyCols
	 * @param ValueCols
	 * @return
	 */
	public static Map<String,Double> extractMapStrDouble(String path,int keyCols,int valueCols){
		Map<String,Double> keyValues = new HashMap<String,Double>();
		try {
			BufferedReader thereader = new BufferedReader(new FileReader(path));
			String line = null;
			while ((line = thereader.readLine()) != null) {
				String[] items = line.split("\t");
				if(items.length<=keyCols||items.length<=valueCols)
					continue;
				String key = items[keyCols];
				Double value;
				try{
					value = Double.parseDouble(items[valueCols]);
				}catch(Exception e){
					System.out.println("parse to double error-"+items[valueCols]);
					continue;
				}			
				if(!keyValues.containsKey(key)){					
					keyValues.put(key, value);
				}
			}
			thereader.close();
		} catch (Exception e) {
			System.out.println("read file to Map ERROR- " + e);
			//            e.printStackTrace();
		}
		return keyValues;
	}
	/**
	 * extract a map from a file, the key and value are corresponding to the keyCol and valueCol
	 * @param path
	 * @param keyCols
	 * @param valueCols
	 * @return
	 */
	public static Map<String,String> extractMapStrStr(String path,int keyCol,int valueCol,String seperator){
		Map<String,String> keyValues = new HashMap<String,String>();
		try {
			BufferedReader thereader = new BufferedReader(new FileReader(path));
			String line = null;
			while ((line = thereader.readLine()) != null) {
				String[] items = line.split(seperator);
				if(items.length<=keyCol||items.length<=valueCol)
					continue;
				String key = items[keyCol];
				String value = items[valueCol];
				keyValues.put(key, value);
			}
			thereader.close();
		} catch (Exception e) {
			System.out.println("read file to Map ERROR- " + e);
			//            e.printStackTrace();
		}
		return keyValues;
	}
	/**
	 * extract a map from a file, the key and value are corresponding to the keyCol and valueCol
	 * @param path
	 * @param keyCols
	 * @param valueCols
	 * @return
	 */
	public static Map<Integer,Integer> extractMapIntInt(String path,int keyCol,int valueCol,String seperator){
		Map<Integer,Integer> keyValues = new HashMap<Integer,Integer>();
		try {
			BufferedReader thereader = new BufferedReader(new FileReader(path));
			String line = null;
			while ((line = thereader.readLine()) != null) {
				String[] items = line.split(seperator);
				if(items.length<=keyCol||items.length<=valueCol)
					continue;
				Integer key = Integer.parseInt(items[keyCol]);
				Integer  value = Integer.parseInt(items[valueCol]);
				keyValues.put(key, value);
			}
			thereader.close();
		} catch (Exception e) {
			System.out.println("read file to Map ERROR- " + e);
			//            e.printStackTrace();
		}
		return keyValues;
	}
	/**
	 * get the cosine similarity between two Map
	 * @param map1
	 * @param map2
	 * @return
	 */
	public static double getCosineSimilarity(Map<String,Double> map1,Map<String,Double> map2){
		if(map1==null||map2==null)
			return 0d;
		double numerator = 0.0;
        double denominator = 1.0;
        double sumMap1Square = 0.0;
        double sumMap2Square = 0.0;
        Set<String> termsIntersection = ToolUtil.getSetIntersection(map1.keySet(), map2.keySet());
        for (String term : termsIntersection)
        {
        	double value1 = map1.get(term);
            double value2 = map2.get(term);
            numerator += value1 * value2;
        }
        for (Entry<String, Double> entry:map1.entrySet())
        {
            sumMap1Square += Math.pow(entry.getValue(), 2);
        }
        for (Entry<String, Double> entry:map2.entrySet())
        {
            sumMap2Square += Math.pow(entry.getValue(), 2);
        }
        denominator = Math.sqrt(sumMap1Square) * Math.sqrt(sumMap2Square);
        if (denominator != 0)
        {
            double cosineValue = numerator / denominator;
            return cosineValue;
        }
        else
            return 0.0;
	}
	/**
	 * get the Pearson Correlation between two Map
	 * @param map1
	 * @param map2
	 * @return
	 */
	public static double getPearsonCorrelation(Map<String,Double> map1,Map<String,Double> map2) {
		if(map1==null||map2==null) {
			return 0d;
		}
		double innerProduct=0.0;
		double numerator = 0.0;
        double denominator = 1.0;
        double sumMap1Square = 0.0;
        double sumMap1=0.0;
        double sumMap2Square = 0.0;
        double sumMap2=0.0;
        int _size=0;
		Set<String> termsIntersection = ToolUtil.getSetIntersection(map1.keySet(), map2.keySet());
		for (String term : termsIntersection)
        {
        	double value1 = map1.get(term);
            double value2 = map2.get(term);
            innerProduct += value1 * value2;
        }
		for (Entry<String, Double> entry:map1.entrySet())
        {
            sumMap1Square += Math.pow(entry.getValue(), 2);
            sumMap1+=entry.getValue();
        }
        for (Entry<String, Double> entry:map2.entrySet())
        {
            sumMap2Square += Math.pow(entry.getValue(), 2);
            sumMap2+=entry.getValue();
        }
        _size=map1.size()+map2.size()-termsIntersection.size();
        denominator=Math.pow((_size*sumMap1Square -Math.pow(sumMap1, 2))*(_size*sumMap2Square -Math.pow(sumMap2, 2)), 0.5);
        numerator=_size*innerProduct-sumMap1*sumMap2;
        if (denominator != 0)
        {
            double pearsonCorr = Math.abs(numerator / denominator);
            return pearsonCorr;
        }
        else
            return 0.0;
	}
	
	public static String map2Str(Map<String,Double> map){
		if(map == null || map.size()==0)
			return "";
		String content = "";
		for(Entry<String,Double> entry:map.entrySet()){
			String key = entry.getKey();
			Double value = entry.getValue();
			String outLine = key+"\t"+value+"\n";
			content += outLine;
		}
		return content.trim();
	}
	public static String map2Str(Map<String,Double[]> map,int index){
		if(map == null || map.size()==0)
			return "";
		String content = "";
		for(Entry<String,Double[]> entry:map.entrySet()){
			String key = entry.getKey();
			Double[] value = entry.getValue();
			String outLine = key+"\t"+value[index]+"\n";
			content += outLine;
		}
		return content.trim();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map<String,Double> test = extractMapStrDouble("D:\\privates\\LJF\\data\\microsoft\\click-entropy\\"
				+ "query_entropy_clicknum_urlnum_usernum", 0, 1);
		System.out.println(test.size());
	}

}
