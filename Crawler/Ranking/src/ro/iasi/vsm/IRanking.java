package ro.iasi.vsm;

public interface IRanking {

	private List<int> keywordsId;
	private List<DocMatch> docMatches;
	private int vocSize;

	public List<Result> getResults();

}
