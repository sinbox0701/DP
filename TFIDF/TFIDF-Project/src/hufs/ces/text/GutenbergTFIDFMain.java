package hufs.ces.text;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.math3.linear.RealMatrix;

public class GutenbergTFIDFMain {

	public static void main(String[] args) {

		Map<String, Integer> termFreqMap = new HashMap<String, Integer>();
		List<TermFreqPair> wfList = new ArrayList<TermFreqPair>();
		
		/* create a dictionary of all terms */
		TermDictionary termDictionary = new TermDictionary();

		/* need a basic tokenizer to parse text */
		Tokenizer tokenizer = new StreamTokenizer();
		//Tokenizer tokenizer = new SimpleTokenizer();

		File[] textFiles = new File[0];
		try {
			URL url = GutenbergTFIDFMain.class.getResource("/gutenberg");
			System.out.println(url);
			textFiles = new File(url.toURI()).listFiles();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}


		List<String> documents = new ArrayList<>();
		for (File f:textFiles) {
			String doc = readFilesToString(f);
			documents.add(doc);
			System.out.println(f.getName()+" size="+doc.length());
		}
		for (String doc : documents) {
			//System.out.println(doc);
			List<String> tokens = tokenizer.getTokens(doc);
			//System.out.println("size(tok)="+tokens.size());
			//termDictionary.addTerms(tokens);
			for (String tok : tokens) {
				Integer freq = termFreqMap.get(tok);
				if (freq==null || freq.intValue()==0){
					termFreqMap.put(tok, 1);
					//System.out.println(lword);
				}
				else {
					freq++;
					termFreqMap.put(tok, freq);
				}
			}
		}

		// sort keyword by frequency
		Set<String> keys = termFreqMap.keySet();
		for (String k:keys){
			wfList.add(new TermFreqPair(k, termFreqMap.get(k)));
		}
		wfList.stream()
		.filter(wf->wf.getFreq()>1)
		.sorted()
		.limit(1000)
		.forEach(wf->{
			termDictionary.addTerm(wf.getTerm());
			System.out.println(wf.getFreq()+"--"+wf.getTerm());
		});

		System.out.println("size(termDictionary)="+termDictionary.getNumTerms());
		
		/* create of matrix of word counts for each sentence 
		Vectorizer vectorizer = new Vectorizer(termDictionary, tokenizer, false);
		RealMatrix counts = vectorizer.getCountMatrix(documents);
		System.out.println(counts.getRowDimension()+","+counts.getColumnDimension());
		 */
		/* ... or create a binary counter 
		Vectorizer binaryVectorizer = new Vectorizer(termDictionary, tokenizer, true);
		RealMatrix binCounts = binaryVectorizer.getCountMatrix(documents);
		System.out.println(binCounts.getRowDimension()+","+binCounts.getColumnDimension());
		*/
		///* ... or create a matrix TFIDF  
		TFIDFVectorizer tfidfVectorizer = new TFIDFVectorizer(termDictionary, tokenizer);
		RealMatrix tfidf = tfidfVectorizer.getTFIDF(documents);
		System.out.println(tfidf.getRowDimension()+","+tfidf.getColumnDimension());
		
		for (int i=0; i<tfidf.getRowDimension(); ++i) {
			double[] rowVector = tfidf.getRow(i);
			IntStream.range(0,rowVector.length)
			.mapToObj(iterm->new TermTFIDFPair(iterm, rowVector[iterm]))
			.sorted()
			.limit(100)
			.forEach(t->System.out.format("%s,%.2f ",termDictionary.getTerm(t.getTerm()),t.getTFID()));
			System.out.println();
		}
		//*/
	}

	public static String readFilesToString(File file) {
		String text = null;
		try {
			text = Files.lines(Paths.get(file.toURI()),Charset.defaultCharset())
					.collect(Collectors.joining());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return text;
	}
}
