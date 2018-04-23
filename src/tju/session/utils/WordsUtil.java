package tju.session.utils;

import java.util.regex.Pattern;

import com.sun.xml.internal.ws.util.StringUtils;
/**
 * Provides some utility functions to process strings
 * 
 * @author dyaa.albkour
 *
 */
public class WordsUtil {

	
	/**
	 * remove all quotes in the string
	 * 
	 * @param source
	 * @return
	 */
	public static String removeQuotes(String source){
    	return source.replaceAll("\"", "");
	} 

	
	/**
	 * Remove leading white space
	 * 
	 * @param source
	 * @return
	 */
	public static String ltrim(String source){
    	return source.replaceAll("^\\s+", "");
	}

	
	/**
	 * remove trailing whitespace
	 * @param source
	 * @return
	 */
	public static String rtrim(String source){
    	return source.replaceAll("\\s+$", "");
	}

	
	/**
	 * replace multiple whitespaces between words with single blank 
	 * @param source
	 * @return
	 */
	public static String itrim(String source){
		return source.replaceAll("\\b\\s{2,}\\b", " ");
//		return source.replaceAll("\\s{2,}", " ");
	}

	
	/**
	 * remove all superfluous whitespaces in source string 
	 * @param source
	 * @return
	 */
	public static String trim(String source ){
    	return itrim( ltrim( rtrim(source )));
	}
	
	/**
	 * 
	 * 
	 * @param str
	 * @return
	 */
	public static String cleanNonAscii(String str){		
		return Pattern.compile("[^\\p{Graph}^\\p{Space}]").matcher(str).
			replaceAll(StringUtils.capitalize(""));		
	}
	
	public static String replacePunctWithSpaces(String str){
		return Pattern.compile("\\p{Punct}").matcher(str).
			replaceAll(" ");
	}
	
	
	public static String reduceToSingleSpace(String str){
		
		return WordsUtil.itrim(str);
		
	}
	
	
	/**
	 * 	This method stems from the anchor log cleanning.
	 * Some anchor text contain HTML encoding for special characters
	 * like nbsp,lt,amp
	 * 
	 * @param str
	 * @return
	 */
	public static String cleanHTMLSpecialCharacters(String str){
		return str.replaceAll("\\b(nbsp|lt|amp|gt|http|https|com|www|)\\b","");
	}
	
	
	public static  boolean isAsciiAlnum(String suggestion){
		Pattern pattern = Pattern.compile("^[\\p{Alnum}\\p{Space}]*$");
		return pattern.matcher(suggestion).matches();
	}
	
	public static void main(String[] args){
		String test = "lt amp driving } lt com http https  license lt amp driving  lt   license lt amp driving  lt   license lt amp driving  lt   license lt amp driving  lt   license lt amp driving  lt   license lt amp driving  lt   license lt amp driving  lt   license lt amp driving  lt   license lt amp driving  lt   license lt amp driving  lt   license lt amp driving  lt   license lt amp driving  lt   license lt amp driving  lt   license ";
		String test_s = WordsUtil.cleanHTMLSpecialCharacters(test);
		System.out.println(itrim(test_s));	
		
//		System.out.println(WordsUtil.isAsciiAlnum("jf23trf32$(%(*%£%2 r32 "));
//		System.out.println(WordsUtil.isAsciiAlnum("university of  essex2 "));
//		System.out.println(test_s);
	}
	
	


}


