package custom_utils;

import java.util.*;

import custom_utils.LevenshteinDistance;
import custom_utils.PlaceholderMatch;

import java.lang.*;
import java.io.*;

class FileSystemUtility {
	public ArrayList<String> generateFileList(String path) {
		ArrayList<String> files = new ArrayList<String>();
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		if (listOfFiles != null) {
			for (File file : listOfFiles) {
				if (file.isFile()) {
					files.add(file.getName());
				}
			}
		}
		return files;
	}
}

class GrammarEncoder {
	HashMap<String, HashMap<String, Double>> grammarWeightList_tf;
	HashMap<String, HashMap<String, String>> grammarSymbolList_tf;
	HashMap<String, Double> grammarWeightList_idf;

	GrammarEncoder() {
		try {
			GrammarWeight gw = new GrammarWeight();
			this.grammarWeightList_tf = gw.getTF();
			this.grammarSymbolList_tf = gw.getSymbol();
			this.grammarWeightList_idf = gw.getIDF();
		} catch (Exception e) {
			// System.out.println(e);
		}
	}

	public String encode(String grammar, String[] tokenArray) {
		StringBuilder code = new StringBuilder();
		for (String token : tokenArray) {
			if (grammarWeightList_tf.get(grammar).get(token.toLowerCase()) != null) {
				Double tf = grammarWeightList_tf.get(grammar).get(token.toLowerCase());
				Double idf = grammarWeightList_idf.get(token.toLowerCase());
				String symbol = grammarSymbolList_tf.get(grammar).get(token.toLowerCase());
				// if(symbol.charAt(0) == 'F')
				// System.out.println(token);
				for (int i = 0; i < Math.ceil(tf * idf); i++) {
					code.append(symbol);
				}
			}
		}

		return code.toString();
	}
}

public class GrammarAnalyser {
	private String __RESOUCSE_PATH__ = "./resources/";
	// private FileSystemUtility fileSystemUtility = new FileSystemUtility();
	private ArrayList<String> fileList;
	HashMap<String, ArrayList<String>> encodedGrammarList;
	GrammarEncoder grammarEncoder;

	public String orig = "";

	public GrammarAnalyser() {
		// list all files in concept folder
		FileRead fr = new FileRead();

		this.fileList = fr.getFileList(new File(__RESOUCSE_PATH__ + "Grammar"));
		this.encodedGrammarList = new HashMap<>();

		// initializing the encoder object
		this.grammarEncoder = new GrammarEncoder();

		for (String file : this.fileList) {
			// grammar filename vs list of encoded grammar
			String grammar = file.split("\\.")[0];

			ArrayList<String> tempList = new ArrayList<>();
			try {

				for (String line : fr.readFileAsLine(new File(__RESOUCSE_PATH__ + "Grammar/" + file))) {
					String[] tokenArray = line.split("\\s+");
					tempList.add(this.grammarEncoder.encode(grammar, tokenArray));
				}
			} catch (Exception e) {
				System.out.println("Error reported" + e);
			}

			this.encodedGrammarList.put(grammar, tempList);
		}
	}

	public String findGrammer(ArrayList<String> sentenseList, ArrayList<String> sentenseList_1) {
		HashMap<String, Double> fileVsScore = new HashMap<>();
		HashMap<String, String> senVsGram = new HashMap<>();

		int i = 0;
		for (String sentense : sentenseList) {
			String sentense_1 = (sentenseList_1.get(i));
			String[] tokenArray = sentense.split("\\s+");

			// the code per grammar for the sentence provided by the user
			HashMap<String, String> sentenseEncodedGrammar = new HashMap<>();
			for (String file : this.fileList) {
				String grammar = file.split("\\.")[0];
				sentenseEncodedGrammar.put(grammar, this.grammarEncoder.encode(grammar, tokenArray));
			}

			//find optimum match
			double globalMax = -1000.0;
			String globalMaxFile = "";
			for (String grammar : this.encodedGrammarList.keySet()) {
				String token_code = sentenseEncodedGrammar.get(grammar);
				double localMax = -1000.0;
				for (String code : this.encodedGrammarList.get(grammar)) {
					double dist = PlaceholderMatch.jaro_winkler_dist(token_code, code, false);
					dist = dist * token_code.length();

					// System.out.println(token_code);
					// System.out.println(code);

					// System.out.println(grammar);
					// System.out.println(dist);

					if (localMax < dist) {
						localMax = dist;
					}
				}

				if (localMax > globalMax) {
					globalMax = localMax;
					globalMaxFile = grammar;


				}
				localMax = -1000.0;
			}
			fileVsScore.put(globalMaxFile, globalMax);
			senVsGram.put(globalMaxFile,sentense_1);
			i++;
		}

		Double max = Double.MIN_VALUE;
		String output = "";
		for (String file : fileVsScore.keySet()) {
			// System.out.println(file + " : " + fileVsScore.get(file));
			if (max < fileVsScore.get(file)) {
				max = fileVsScore.get(file);
				output = file;

			}
		}
		// System.out.println(senVsGram.get(output););
		orig = senVsGram.get(output);
		return output;
	}
}
