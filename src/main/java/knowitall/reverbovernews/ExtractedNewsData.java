package knowitall.reverbovernews;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import edu.washington.cs.knowitall.nlp.extraction.ChunkedBinaryExtraction;

public class ExtractedNewsData extends NewsData{

	public Map<String, ChunkedBinaryExtraction> extractions;
	
	public ExtractedNewsData(String category, String subCategory, String title,
			String date) {
		super(category, subCategory, title, date);
		extractions = new HashMap<String, ChunkedBinaryExtraction>();
	}
	
	public String toJsonString(){
		StringBuilder sb = new StringBuilder();
		sb.append("\t\n{");
		sb.append("\t\t" + getFiledsJson() + ", \n");
		sb.append("\t\t" + getExtractionsJsonString() + "\n");
		sb.append("}\n");
		return sb.toString();
	}

	//"extractions":[{"sent:":"content-of-the-extracted-string","arg1":"...", "relation":"...", "arg2":"..."}, {...another extracted string..}, ..] 
	private String getExtractionsJsonString() {
		assert extractions != null;
		StringBuilder sb = new StringBuilder();
		sb.append("\"extractions\":[");
		Iterator<Entry<String, ChunkedBinaryExtraction>> it = extractions.entrySet().iterator();
		boolean empty = true;
		while(it.hasNext()){
			empty = false;
			Map.Entry<String, ChunkedBinaryExtraction> pairs = (Map.Entry<String, ChunkedBinaryExtraction>)it.next();
			sb.append("\n\t\t\t{\"sent\":\"" + pairs.getKey().replace("\"", "\\\"") + "\", \n");
			sb.append("\t\t\t\"arg1\":\"" + pairs.getValue().getArgument1().toString().replace("\"", "\\\"") + "\", \n");
			sb.append("\t\t\t\"relation\":\"" + pairs.getValue().getRelation().toString().replace("\"", "\\\"") + "\", \n");
			sb.append("\t\t\t\"arg2\":\"" + pairs.getValue().getArgument2().toString().replace("\"", "\\\"") + "\"},");
		}
		if(!empty) sb.deleteCharAt(sb.length() - 1);
		sb.append("]");
		return sb.toString();
	}

}
