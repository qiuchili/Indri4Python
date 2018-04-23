package tju.session.utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;

import org.tju.ir.bases.*;

import lemurproject.indri.*;

public class IndriAPI {
	QueryEnvironment _queryEnvironment;
	public IndriAPI(){

	}
	public IndriAPI(String indexDir) throws Exception{
		_queryEnvironment = new QueryEnvironment();
		_queryEnvironment.addIndex(indexDir);
	}
	/**
	 * get the doc ID. Before use this function, we must initialize the IndriAPI obj using
	 * the Constructor  IndriAPI(String indexDir). The performance is good. If there is no the document
	 * in the repository then return 0.
	 * @param docno
	 * @return
	 * @throws Exception
	 */
	public int getDocID(String docno) throws Exception{
		if(_queryEnvironment==null)
		{
			System.out.println("There is no repository be specified!");
			return 0;
		}
		if(docno==null||"".equals(docno))
		{
//			System.out.println("the docno is not valid!");
			return 0;
		}
		String[] values = {docno};
        int[] ids = _queryEnvironment.documentIDsFromMetadata("docno",values);
        if(ids.length!=0)
        	return ids[0];
        else
        	return 0;
	}
	public int getDocID4Field(String fieldName, String fieldValue) throws Exception{
		if(_queryEnvironment==null)
		{
			System.out.println("There is no repository be specified!");
			return 0;
		}
		String[] values = {fieldValue};
        int[] ids = _queryEnvironment.documentIDsFromMetadata(fieldName,values);
        if(ids.length!=0)
        	return ids[0];
        else
        	return 0;
	}
	public String getDocNo(int docID) throws Exception{
		int[] docIDs = {docID};
		String[] docNos =	_queryEnvironment.documentMetadata(docIDs, "docno");
		String docNo = "";
		if(docNos.length!=0){
			docNo = docNos[0];
		}
		return docNo;
	}
	public long getDocFrequency(String term) throws Exception{
		long docCount = _queryEnvironment.documentCount(term);
		return docCount;
	}
	public long getCollectionDocCount() throws Exception{
		return _queryEnvironment.documentCount();
	}
	public long getCollectionTermCount() throws Exception{
		return _queryEnvironment.termCount();
	}
	public long getTermFrequency(String term) throws Exception{
		return _queryEnvironment.termCount(term);
	}
	public double getCountIndriExpression(String expression) throws Exception{
		return _queryEnvironment.expressionCount(expression);
	}
	/**
	 * get the language model for a query, i.e. a string
	 * @param vocabulary
	 * @param query
	 * @param dirichletPrior
	 * @return
	 * @throws Exception
	 */
	public Map<Integer,Double> getLM4Query(Map<String,Integer> vocabulary,
										   String query,
										   int dirichletPrior) throws Exception{

		Map<Integer,Double> languageModel = new HashMap<Integer,Double>();
		Map<Integer,Double> collectionLanguageModels = new HashMap<Integer,Double>();
		long colTermCount = _queryEnvironment.termCount();
		for(Entry<String,Integer> entry:vocabulary.entrySet()){
			String term = entry.getKey();
			Integer termID = entry.getValue();
			long termCount = _queryEnvironment.termCount(term);
			if(termCount==0)
				termCount = 1;
			double colProbability = (double)termCount/colTermCount;
			collectionLanguageModels.put(termID, colProbability);
		}
		//get the non-smoothed query LM
		Map<Integer,Integer> termFrequency = new HashMap<Integer,Integer>();
		String[] queryTerms = ToolUtil.splitString(query);
		int docLength = queryTerms.length;
		Porter porter = new Porter();
		for(String term:queryTerms)
		{
			if(term==null||"".equals(term))
				continue;
			term = porter.stripAffixes(term);
			Integer termID = vocabulary.get(term);
			if(termID!=null){
				Integer tempFrequency = termFrequency.get(termID);
				int newFrequency = (tempFrequency==null)?1:(tempFrequency+1);
				termFrequency.put(termID, newFrequency);
			}
		}
		for(Entry<Integer,Double> entry:collectionLanguageModels.entrySet()){
			int termID = entry.getKey();
			double colProbability = entry.getValue();
			Integer tf = termFrequency.get(termID);
			if(tf==null)
				tf = 0;
			double smoothedProbability = (tf+dirichletPrior*colProbability)/(docLength+dirichletPrior);
			languageModel.put(termID, smoothedProbability);
		}
		ToolUtil.normalizeIntDouble(languageModel);
		return languageModel;
	}
	/**
	 * get the language model for a list of docs, docs are specified by docnos.
	 * the LM is smoothed by dirichlet prior, and is normalized to one.
	 * @param vocabulary
	 * @param docnos
	 * @param dirichletPrior
	 * @return
	 * @throws Exception
	 */
	public Map<Integer,Double> getLM4Docs(Map<String,Integer> vocabulary,
									      String[] docnos,
									      int dirichletPrior) throws Exception{
		if(docnos==null||docnos.length==0)
			return null;
		Map<Integer,Double> languageModel = new HashMap<Integer,Double>();
		Map<Integer,Double> collectionLanguageModels = new HashMap<Integer,Double>();
		long colTermCount = _queryEnvironment.termCount();
		for(Entry<String,Integer> entry:vocabulary.entrySet()){
			String term = entry.getKey();
			Integer termID = entry.getValue();
			long termCount = _queryEnvironment.termCount(term);
			if(termCount==0)
				termCount = 1;
			double colProbability = (double)termCount/colTermCount;
			collectionLanguageModels.put(termID, colProbability);
		}
		int[] ids = _queryEnvironment.documentIDsFromMetadata("docno",docnos);
		DocumentVector[] docVecs = _queryEnvironment.documentVectors(ids);
		if(docVecs == null || docVecs.length==0)
			return null;
		Map<Integer,Integer> termFrequency = new HashMap<Integer,Integer>();
		int docLength = 0;
		for(DocumentVector docVec:docVecs){
			int[] positions = docVec.positions;
			String[] terms = docVec.stems;
			docLength += positions.length;
			for(int position:positions)
			{
				String term = terms[position];
				Integer termID = vocabulary.get(term);
				if(termID!=null){
					Integer tempFrequency = termFrequency.get(termID);
					int newFrequency = (tempFrequency==null)?1:(tempFrequency+1);
					termFrequency.put(termID, newFrequency);
				}
			}
		}
		for(Entry<Integer,Double> entry:collectionLanguageModels.entrySet()){
			int termID = entry.getKey();
			double colProbability = entry.getValue();
			Integer tf = termFrequency.get(termID);
			if(tf==null)
				tf = 0;
			double smoothedProbability = (tf+dirichletPrior*colProbability)/(docLength+dirichletPrior);
			languageModel.put(termID, smoothedProbability);
		}
		ToolUtil.normalizeIntDouble(languageModel);
		return languageModel;
	}
	/**
	 * get the tf-idf representation for a list of documents, each document is specified by a docno
	 * @param docnos
	 * @return
	 * @throws Exception
	 */
	public Map<String,Double> getTFIDFRep4Docs(String[] docnos) throws Exception{
		int[] ids = _queryEnvironment.documentIDsFromMetadata("docno",docnos);
		DocumentVector[] docVecs = _queryEnvironment.documentVectors(ids);
		if(docVecs.length==0)
			return null;
		Map<String,Integer> termFrequency = new HashMap<String,Integer>();
		Map<String,Double> tfidfs = new HashMap<String,Double>();
		int maxTF = 0;
		long totalDocs = getCollectionDocCount();
		for(DocumentVector docVec:docVecs){
			int[] positions = docVec.positions;
			String[] terms = docVec.stems;
			for(int position:positions)
			{
				String term = terms[position];
				if(term.trim().equals("[OOV]")||term.trim().length()<=1)
					continue;
				if(termFrequency.containsKey(term)){
					int originalTF = termFrequency.get(term);
					int currentTF = originalTF+1;
					termFrequency.put(term, currentTF);
					if(currentTF>maxTF)
						maxTF = currentTF;
				}else{
					termFrequency.put(term, 1);
					if(maxTF<1)
						maxTF=1;
				}
			}
		}
		for(Entry<String,Integer> entry:termFrequency.entrySet()){
			String term  = entry.getKey();
			Integer frequency = entry.getValue();
			long docFrequency = this.getDocFrequency(term);
			double normalizedTF = 0.5+0.5*frequency/maxTF;
			double idf = Math.log10(totalDocs/(docFrequency+1));
			double tf_idf = normalizedTF*idf;
			tfidfs.put(term, tf_idf);
		}
		ToolUtil.normalize(tfidfs);
		return tfidfs;
	}
	/**
	 *
	 * @param docnos
	 * @param queryTerm
	 * @return
	 * @throws Exception
	 */
	public int getTermFrequencyInDocs(String docnos[],String queryTerm) throws Exception{
		int []numbers={1};
		int []totalCounts=getCoOccurCountInDocs(docnos,numbers,queryTerm);
		return totalCounts[0];
	}



	public SortedSet<StrDoublePair> getTopTermsFormDocs(int[] docids) throws Exception{
		DocumentVector[] docVecs = _queryEnvironment.documentVectors(docids);
		if(docVecs.length==0)
			return null;
		Map<String,Integer> termFrequency = new HashMap<String,Integer>();
		Map<String,Double> tfidfs = new HashMap<String,Double>();
		int maxTF = 0;
		long totalDocs = getCollectionDocCount();
		for(DocumentVector docVec:docVecs){
			int[] positions = docVec.positions;
			String[] terms = docVec.stems;
			for(int position:positions)
			{
				String term = terms[position];
				if(term.trim().equals("[OOV]")||term.trim().length()<=1)
					continue;
				if(termFrequency.containsKey(term)){
					int originalTF = termFrequency.get(term);
					int currentTF = originalTF+1;
					termFrequency.put(term, currentTF);
					if(currentTF>maxTF)
						maxTF = currentTF;
				}else{
					termFrequency.put(term, 1);
					if(maxTF<1)
						maxTF=1;
				}
			}
		}
		for(Entry<String,Integer> entry:termFrequency.entrySet()){
			String term  = entry.getKey();
			Integer frequency = entry.getValue();
			long docFrequency = this.getDocFrequency(term);
			double normalizedTF = 0.5+0.5*frequency/maxTF;
			double idf = Math.log10(totalDocs/(docFrequency+1));
			double tf_idf = normalizedTF*idf;
			tfidfs.put(term, tf_idf);
		}
		ToolUtil.normalize(tfidfs);

		return null;
	}

	/**
	 * get the tf-idf representation of the document given a document id
	 * @param docID
	 * @return
	 * @throws Exception
	 */
	public Map<String,Double> getTFIDFRep4Doc(int docID) throws Exception{
		int[] ids = {docID};
		DocumentVector[] dv = _queryEnvironment.documentVectors(ids);
		if(dv.length==0)
			return null;
		Map<String,Integer> termFrequency = new HashMap<String,Integer>();
		Map<String,Double> tfidfs = new HashMap<String,Double>();
		int maxTF = 0;
		long totalDocs = getCollectionDocCount();
		for(int position:dv[0].positions)
		{
			String term = dv[0].stems[position];
			if(term.trim().equals("[OOV]")||term.trim().length()<=1)
				continue;
			if(termFrequency.containsKey(term)){
				int originalTF = termFrequency.get(term);
				int currentTF = originalTF+1;
				termFrequency.put(term, currentTF);
				if(currentTF>maxTF)
					maxTF = currentTF;
			}else{
				termFrequency.put(term, 1);
				if(maxTF<1)
					maxTF=1;
			}
		}

		for(Entry<String,Integer> entry:termFrequency.entrySet()){
			String term  = entry.getKey();
			Integer frequency = entry.getValue();
			long docFrequency = this.getDocFrequency(term);
			double normalizedTF = 0.5+0.5*frequency/maxTF;
			double idf = Math.log10(totalDocs/(docFrequency+1));
			double tf_idf = normalizedTF*idf;
			tfidfs.put(term, tf_idf);
		}
		ToolUtil.normalize(tfidfs);
		return tfidfs;
	}
	/**
	 * get the doc id from a index given the docno. This is a static function, the running performance is relative low.
	 * @param indexDir
	 * @param docno
	 * @return
	 * @throws Exception
	 */
	public static int getDocID(String indexDir,String docno) throws Exception
	{
		QueryEnvironment queryEnvironment = new QueryEnvironment();
        queryEnvironment.addIndex(indexDir);
        String[] values = {docno};

        int[] ids = queryEnvironment.documentIDsFromMetadata("docno",values);
        queryEnvironment.close();

        if(ids.length!=0)
        	return ids[0];
        else
        	return 0;
	}
	/**
	 * get the total co-occur count of a dependency term for different window length.
	 * if all of the counts equal to 0, return null
	 * @param indri
	 * @param dependency
	 * @param windowSizePool
	 * @return a string, counts are separated by blanks
	 * @throws Exception
	 */
	public static String getCoOccurCountInCollection(IndriAPI indri,
												  String dependency,
												  int[] windowSizePool) throws Exception{
		String counts = "";
		boolean nullFlag = true;
		for(int l:windowSizePool){
			String expression = "#uw"+l+"("+dependency+")";
			double count = indri.getCountIndriExpression(expression);
			//if any window length has a count doesn't equal to 0, return a non-null results
			if(count!=0)
				nullFlag = false;
			counts = counts+count+" ";
		}
		//if all of the counts equal to 0, return null
		if(nullFlag)
			return null;
		return counts.trim();
	}
	/**
	 * get the co-occur count of the dependency in a list of docs.
	 * documents are specified with docno.
	 * @param docnos
	 * @param windowSizePool
	 * @param dependency
	 * @return String counts are separated by a blank
	 * @throws Exception
	 */
	public int[] getCoOccurCountInDocs(String[] docnos,
									 int[] windowSizePool,
									 String dependency) throws Exception{
		int[] coOccurCounts = new int[windowSizePool.length];
		int[] ids = _queryEnvironment.documentIDsFromMetadata("docno",docnos);
		DocumentVector[] dv = _queryEnvironment.documentVectors(ids);
		for(DocumentVector doc:dv){
			int[] positions = doc.positions;
			String[] stems = doc.stems;
			ArrayList<String> termArrayList = new ArrayList<String>();
			for(int i=0;i<positions.length;i++){
				termArrayList.add(stems[positions[i]]);
			}
			for(int j = 0;j<windowSizePool.length;j++){
				int wSize = windowSizePool[j];
				coOccurCounts[j] += getCoOccurCountInDoc(termArrayList, wSize, dependency);
			}
		}
		return coOccurCounts;
	}
	/**
	 * get the co-occur document frequency for given dependency in the collection
	 * @param dependency
	 * @param windowSizePool
	 * @return
	 * @throws Exception
	 */
	public long[] getCoOccurDocCounts(String dependency,int[] windowSizePool) throws Exception{
		long[] coOccurDFs = new long[windowSizePool.length];
		for(int j = 0;j<windowSizePool.length;j++){
			int wSize = windowSizePool[j];
			String expression = "#uw"+wSize+"("+dependency+")";
			ScoredExtentResult[] results = _queryEnvironment.expressionList(expression);
			coOccurDFs[j] = results.length;
		}
		return coOccurDFs;
	}
	/**
	 * get the co-occur count of a dependency from a document
	 * @param docTermsArray
	 * @param windowSize
	 * @param dependency
	 * @return
	 */
	public int getCoOccurCountInDoc(ArrayList<String> docTermsArray,int windowSize,String dependency){
		String[] items = dependency.split(" ");
		ArrayList<String> components = new ArrayList<String>();
		for(String item:items){
			components.add(item);
		}
		int coOccurCount = 0;
		int docLength = docTermsArray.size();
		for(int i = 0;i<docLength;i +=windowSize){
			//subList return the fromIndex inclusive, toIndex exclusive, so the
			//subEndIndex can reach the docLength
			int subEndIndex = (i+windowSize>docLength)?docLength:(i+windowSize);
			if(docTermsArray.subList(i,subEndIndex).containsAll(components))
				coOccurCount++;
		}
		return coOccurCount;
	}
	/**
	 * get the top ranked docnos and their score
	 * @param query
	 * @param topK
	 * @return
	 * @throws Exception
	 */
	public StrDoublePair[] getTopKResults(String query,int topK) throws Exception{
		ScoredExtentResult[] results  = _queryEnvironment.runQuery(query, topK);
		StrDoublePair[] docNos = new StrDoublePair[results.length];
		for(int i=0;i<results.length;i++){
			int docID = results[i].document;
			String docNo = getDocNo(docID);
			double score = results[i].score;
			docNos[i] = new StrDoublePair(docNo,score);
		}
		return docNos;
	}

	public void testQuerySearch() throws Exception{
		String query = "#combine(china 1 beijing 0.5)";
		ScoredExtentResult[] results = _queryEnvironment.runQuery(query,20);
		for(ScoredExtentResult result:results){
			int[] docID = {result.document};
		    String[] docNos =	_queryEnvironment.documentMetadata(docID, "docno");
		    System.out.println(docNos[0]+" "+result.score);
		}
	}

	public static void main(String[] args) throws Exception{
		String indexDir = "E:/qiuchi/clueweb12/text_datasets/index";
		IndriAPI indri = new IndriAPI(indexDir);
		System.out.println(indri.getCollectionDocCount());
	}

}
