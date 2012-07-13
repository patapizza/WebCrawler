package ro.iasi.vsm;

import java.util.ArrayList;
import java.util.List;

import no.uib.cipr.matrix.Vector.Norm;
import no.uib.cipr.matrix.sparse.SparseVector;

public class Ranking {

	private List<Integer> keywordsId;
	private List<DocMatch> docMatches;
	private int vocSize;

	public Ranking(List<Integer> keywordsId, List<DocMatch> docMatches, int vocSize) {
		this.keywordsId = keywordsId;
		this.docMatches = docMatches;
		this.vocSize = vocSize;
	}

	public List<Result> getResults() throws Exception {
		List<Result> results = new ArrayList<Result>();
		SparseVector docv = new SparseVector(vocSize);
		SparseVector queryv = new SparseVector(vocSize);
		// initialize everything in query with a zero value ?
		for (int keywordId : keywordsId)
			queryv.set(keywordId, 1);
		// initialize everything in docv with a zero value ?
		for (DocMatch docMatch : docMatches) {
			Result result = new Result();
			result.docId = docMatch.docId;
			for (Match match : docMatch.matches)
				docv.set(match.wordId, match.tf);
			result.sim = cosine(queryv, docv);
			results.add(result);
		}
		return results;
	}

	private double cosine(SparseVector v1, SparseVector v2) throws Exception {
		if (v1.size() != v2.size())
			throw new Exception("Vectors should be of the same dimension, bitch.");
		return v1.dot(v2) / (double) (v1.norm(Norm.Two) * v2.norm(Norm.Two));
	}

}
