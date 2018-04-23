package tju.session.utils;

public class CleanString {
	/**
	 * clean the query string terms by lowering the case, trimming the blank space and removing quotes.
	 * @param str
	 * @return
	 */
	public static String Clean(String str){
		str = str.toLowerCase();
		str = WordsUtil.trim(str);			
		str = WordsUtil.removeQuotes(str);
		return str;
	}
}
