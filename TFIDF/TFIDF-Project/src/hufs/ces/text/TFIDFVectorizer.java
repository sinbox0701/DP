package hufs.ces.text;

import java.util.List;

import org.apache.commons.math3.linear.OpenMapRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public class TFIDFVectorizer {

	private Vectorizer vectorizer;
    private Vectorizer binaryVectorizer;
    private int numTerms;
    
    public TFIDFVectorizer(Dictionary dictionary, Tokenizer tokenzier) {
    	vectorizer = new Vectorizer(dictionary, tokenzier, false);
        binaryVectorizer = new Vectorizer(dictionary, tokenzier, true);
        numTerms = dictionary.getNumTerms();
    }
    
    public TFIDFVectorizer() {
        this(new HashingDictionary(), new SimpleTokenizer());
     }
     
     public RealVector getTermDocumentCount(List<String> documents) {
         RealVector vector = new OpenMapRealVector(numTerms);
         for (String document : documents) {
             vector.add(binaryVectorizer.getCountVector(document));
         }
         return vector;
     }
     
     public RealMatrix getTFIDF(List<String> documents) {
         int numDocuments = documents.size();
         RealVector df = getTermDocumentCount(documents);
         RealMatrix tfidfMatrix = vectorizer.getCountMatrix(documents);
         tfidfMatrix.walkInOptimizedOrder(new TFIDF(numDocuments, df));
         return tfidfMatrix;
     }
}
