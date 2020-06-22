package hufs.ces.text;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.commons.math3.linear.RealMatrix;

public class BookTFIDFMain {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();

		Map<String, Integer> termFreqMap = new HashMap<String, Integer>();
		Map<String, Integer> termFreqMapT = new HashMap<String, Integer>();
		List<TermFreqPair> wfList = new ArrayList<TermFreqPair>();
		List<TermFreqPair> wfListT = new ArrayList<TermFreqPair>();
		
		/* create a dictionary of all terms */
		TermDictionary termDictionary = new TermDictionary();
		TermDictionary termDictionaryT = new TermDictionary();
		/* need a basic tokenizer to parse text */
		Tokenizer tokenizer = new StreamTokenizer();
		Tokenizer tokenizerT = new StreamTokenizer();
		//Tokenizer tokenizer = new SimpleTokenizer();

		File[] textFiles = new File[0];
		
		try {
			URL url = BookTFIDFMain.class.getResource("/coms");
			System.out.println(url);
			textFiles = new File(url.toURI()).listFiles();//file list
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		List<String> documents = new ArrayList<>();

		for (File f:textFiles) {
			String doc = readFilesToString(f); // doc is one book
			documents.add(doc); // list of doc(String)
//			System.out.println(f.getName()+" size="+doc.length());
		}
		documents.stream()
				.parallel()
				.map(doc -> tokenizerT.getTokens(doc))
				.forEach(tok -> {
					tok.stream().forEach(to -> {
							Integer freq = termFreqMapT.get(to);
							if (freq==null || freq.intValue()==0){
								termFreqMapT.put(to, 1);
							}
							else {
								freq++;
								termFreqMapT.put(to, freq);//tok에 대한 freq 증가
							}
						});
				});
//				System.out.println(termFreqMapT);
				Set<String> keysT = termFreqMapT.keySet();
				keysT.forEach(k ->wfListT.add(new TermFreqPair(k, termFreqMapT.get(k))));

				wfListT.stream()
				.filter(wf->wf.getFreq()>1)
				.sorted()
				.limit(1000)
				.forEach(wf->{
					termDictionaryT.addTerm(wf.getTerm());//termDict --> {key, value}
//					System.out.println(wf.getFreq()+"--"+wf.getTerm());
				});

//				System.out.println("size(termDictionary)="+termDictionaryT.getNumTerms());
				//* ... or create a matrix TFIDF  
				TFIDFVectorizer tfidfVectorizerT = new TFIDFVectorizer(termDictionaryT, tokenizerT);
				RealMatrix tfidfT = tfidfVectorizerT.getTFIDF(documents);
//				System.out.println(tfidfT.getRowDimension()+","+tfidfT.getColumnDimension());//문서 개수, 토큰
				
				for (int i=0; i<tfidfT.getRowDimension(); ++i) {
					System.out.println("-----------------------------------------------------------------");
					System.out.println(textFiles[i].getName());
					double[] rowVectorT = tfidfT.getRow(i);
					IntStream.range(0,rowVectorT.length)
					.mapToObj(iterm->new TermTFIDFPair(iterm, rowVectorT[iterm]))
					.sorted()
					.limit(100)
					.forEach(t->System.out.format("word: %-15s value: %3.2f\n",termDictionaryT.getTerm(t.getTerm()),t.getTFID()));
					System.out.println();
//					System.out.println();
				}
//				System.out.println("stream!!!");
				long end = System.currentTimeMillis();
				System.out.println( "실행 시간 : " + ( end - start )/1000.0 );
		
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
