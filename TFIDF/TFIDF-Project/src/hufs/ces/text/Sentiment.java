package hufs.ces.text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.linear.RealMatrix;

public class Sentiment {

	private final List<String> documents = new ArrayList<>();
	private final List<Integer> sentiments = new ArrayList<>();

	private static final String IMDB_RESOURCE = 
			"/sentiment/imdb_labelled.txt";
	private static final String YELP_RESOURCE = 
			"/sentiment/yelp_labelled.txt";
	private static final String AMZN_RESOURCE = 
			"/sentiment/amazon_cells_labelled.txt";
	public enum DataSource {IMDB, YELP, AMZN};

	public Sentiment() throws IOException {
		parseResource(IMDB_RESOURCE); // 1000 sentences
		parseResource(YELP_RESOURCE); // 1000 sentences
		parseResource(AMZN_RESOURCE); // 1000 sentences
	}

	public List<Integer> getSentiments(DataSource dataSource) {
		int fromIndex = 0; // inclusive
		int toIndex = 3000; // exclusive
		switch(dataSource) {
		case IMDB:
			fromIndex = 0;
			toIndex = 1000;
			break;
		case YELP:
			fromIndex = 1000;
			toIndex = 2000;
			break;
		case AMZN:
			fromIndex = 2000;
			toIndex = 3000;
			break;
		}
		return sentiments.subList(fromIndex, toIndex);
	}

	public List<String> getDocuments(DataSource dataSource) {
		int fromIndex = 0; // inclusive
		int toIndex = 3000; // exclusive
		switch(dataSource) {
		case IMDB:
			fromIndex = 0;
			toIndex = 1000;
			break;
		case YELP:
			fromIndex = 1000;
			toIndex = 2000;
			break;
		case AMZN:
			fromIndex = 2000;
			toIndex = 3000;
			break;
		}
		return documents.subList(fromIndex, toIndex);
	}

	public List<Integer> getSentiments() {
		return sentiments;
	}

	public List<String> getDocuments() {
		return documents;
	}

	private void parseResource(String resource) throws IOException {
		try(InputStream inputStream = getClass().getResourceAsStream(resource)) {
			BufferedReader br = 
					new BufferedReader(new InputStreamReader(inputStream));
			String line;
			while ((line = br.readLine()) != null) {
				String[] splitLine = line.split("\t");
				// both yelp and amzn have many sentences with no label
				if (splitLine.length > 1) {
					documents.add(splitLine[0]);
					sentiments.add(Integer.parseInt(splitLine[1]));
				}
			}
		}
	}

	public static void main(String[] args) {
		
		/* create a dictionary of all terms */
		TermDictionary termDictionary = new TermDictionary();

		/* need a basic tokenizer to parse text */
		SimpleTokenizer tokenizer = new SimpleTokenizer();
		Sentiment sentiment = null;
		try {
			sentiment = new Sentiment();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("size(doc)="+sentiment.getDocuments().size());
		/* add all terms in sentiment dataset to dictionary */
		for (String document : sentiment.getDocuments()) {
			//System.out.println(document);
			List<String> tokens = tokenizer.getTokens(document);
			System.out.println("size(tok)="+tokens.size());
			termDictionary.addTerms(tokens);
		}
		/* create of matrix of word counts for each sentence */
		
		//Vectorizer vectorizer = new Vectorizer(termDictionary, tokenizer, false);
		//RealMatrix counts = vectorizer.getCountMatrix(sentiment.getDocuments());
		//System.out.println(counts);

		/* ... or create a binary counter */
		//Vectorizer binaryVectorizer = new Vectorizer(termDictionary, tokenizer, true);
		//RealMatrix binCounts = binaryVectorizer.getCountMatrix(sentiment.getDocuments());
		//System.out.println(binCounts);

		/* ... or create a matrix TFIDF  */
		//TFIDFVectorizer tfidfVectorizer = new TFIDFVectorizer(termDictionary, tokenizer);
		//RealMatrix tfidf = tfidfVectorizer.getTFIDF(sentiment.getDocuments());
		//System.out.println(tfidf);
		
		
	}
}
