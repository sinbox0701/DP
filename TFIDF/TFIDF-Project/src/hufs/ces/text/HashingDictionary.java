package hufs.ces.text;

public class HashingDictionary implements Dictionary {

	private int numTerms; // 2^n is optimal

    public HashingDictionary() {
        // 2^20 = 1048576
        this(((Double)Math.pow(2,20)).intValue());
    }

    public HashingDictionary(int numTerms) {
        this.numTerms = numTerms;
    }
    
    @Override
	public Integer getTermIndex(String term) {
    	return Math.floorMod(term.hashCode(), numTerms);	}

	@Override
	public int getNumTerms() {
		return numTerms;
	}

}
