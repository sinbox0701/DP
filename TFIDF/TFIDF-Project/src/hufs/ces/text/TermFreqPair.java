/**
 * Created on 2014. 12. 4.
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */

package hufs.ces.text;

/**
 * @author cskim
 *
 */
public class TermFreqPair implements Comparable<TermFreqPair> {

	private String term = null;
	private Integer freq = null;
	
	public TermFreqPair(String word, Integer freq){
		this.term = word;
		this.freq = freq;
	}
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(TermFreqPair wfp) {
		return wfp.freq - freq;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public Integer getFreq() {
		return freq;
	}
	public void setFreq(Integer freq) {
		this.freq = freq;
	}

}
