package ro.iasi.vsm;

public interface IRanking {

	public List<String> keywords;
	public List<Document> docs;
	public List<Document> matches;
	public List<String> voc;
	public List<Similarity> results;

	public List<Similarity> getSimilarities();

}
