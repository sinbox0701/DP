package hufs.ces.text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TermDictionary implements Dictionary {

	private final Map<String, Integer> indexedTerms;
	private final Map<Integer, String> inverseTerms;
    private int counter;

    public TermDictionary() {
        indexedTerms = new HashMap<>();
        inverseTerms = new HashMap<>();
        counter = 0;
    }
    
    public void addTerm(String term) {
        if(!indexedTerms.containsKey(term)) {
            indexedTerms.put(term, counter);
            inverseTerms.put(counter, term);
            counter++;
        }       
    }

    public void addTerms(List<String> terms) {
        for (String term : terms) {
            addTerm(term);
        }
    }
    
	public String getTerm(Integer index) {
    	return inverseTerms.get(index);
    }
	
    @Override
	public Integer getTermIndex(String term) {
    	return indexedTerms.get(term);	}

	@Override
	public int getNumTerms() {
		return indexedTerms.size();	}

}
