package hufs.ces.text;

import org.apache.commons.math3.linear.RealMatrixChangingVisitor;
import org.apache.commons.math3.linear.RealVector;

public class TFIDF implements RealMatrixChangingVisitor {

	private final int numDocuments;
    private final RealVector termDocumentFrequency;
    double logNumDocuments;

    public TFIDF(int numDocuments, RealVector termDocumentFrequency) {
        this.numDocuments = numDocuments;
        this.termDocumentFrequency = termDocumentFrequency;
        this.logNumDocuments = numDocuments > 0 ? Math.log(numDocuments) : 0;
    }
    
    @Override
	public double end() {
    	return 0.0;
    }

	@Override
	public void start(int rows, int columns, int startRow, int endRow,
            int startColumn, int endColumn) {
		// TODO Auto-generated method stub

	}

	@Override
	public double visit(int row, int column, double value) {
		double df = termDocumentFrequency.getEntry(column);
        double logDF = df > 0 ? Math.log(df) : 0.0;
        // TFIDF = TF_i * log(N/DF_i) = TF_i * ( log(N) - log(DF_i) )
        return value * (logNumDocuments - logDF);	}

}
