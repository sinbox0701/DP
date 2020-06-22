package hufs.ces.text;

public class TermTFIDFPair implements Comparable<TermTFIDFPair>  {

	private Integer term = null;
	private Double tfidf = null;
	
	public TermTFIDFPair(Integer term, Double tfidf) {
		this.term = term;
		this.tfidf = tfidf;
	}

	@Override
	public int compareTo(TermTFIDFPair ttp) {
		if ((ttp.tfidf - tfidf)>0)
			return 1;
		else if ((ttp.tfidf - tfidf)<0)
			return -1;
		else
			return 0;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public Double getTFID() {
		return tfidf;
	}

	public void setTFID(Double tfidf) {
		this.tfidf = tfidf;
	}

}
