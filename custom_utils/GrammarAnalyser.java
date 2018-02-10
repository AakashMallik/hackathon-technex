package custom_utils;

import java.util.*;
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
	HashMap<String, HashMap<String, Char>> grammarSymbolList_tf;
	HashMap<String, Double> grammarWeightList_idf;

	GrammarEncoder() {
		// use utsav's code to get weightList
	}

	public String encode(String grammar, String[] tokenArray) {
		StringBuilder code = new StringBuilder();
		for (String token : tokenArray) {
			if (grammarWeightList_tf.get(grammar).get(token) != null) {
				Double wight = grammarWeightList_tf.get(grammar).get(token);
				Double idf = grammarWeightList_idf.get(token);
				Char symbol = grammarSymbolList_tf.get(grammar).get(token);

				for (int i = 0; i < Math.ceil(wight * idf); i++) {
					code.append(symbol);
				}
			}
		}

		return code;
	}
}

public class GrammarAnalyser {
	private String __RESOUCSE_PATH__ = "./resources/";
	private FileSystemUtility fileSystemUtility = new FileSystemUtility();
	private ArrayList<String> fileList;
	HashMap<String, ArrayList<String>> encodedGrammarList;
	GrammarEncoder grammarEncoder;

	public GrammarAnalyser() {
		// list all files in concept folder
		this.fileList = fileSystemUtility.generateFileList(__RESOUCSE_PATH__ + "Grammar");
		this.EncodedGrammarList = new HashMap<>();

		// initializing the encoder object
		this.grammarEncoder = new GrammarEncoder();

		for (String file : this.fileList) {
			// grammar filename vs list of encoded grammar
			String grammar = file.split("\\.")[0];

			ArrayList<String> tempList = new ArrayList<>();

			File fileOject = new File(__PATH__ + "/" + file);
			try {
				BufferedReader reader = new BufferedReader(new FileReader(fileOject));
				String line = reader.readLine();
				while (line != null) {
					String[] tokenArray = line.split("\\s+");
					tempList.add(this.grammarEncoder.encode(grammar, tokenArray));
					line = reader.readLine();
				}
				reader.close();
			} catch (Exception e) {
				System.out.println("Error reported" + e);
			}

			this.encodedGrammarList.put(grammar, tempList);
		}
	}

	public String findGrammer(String sentense) {
		String[] tokenArray = sentense.split("\\s+");

		// the code per grammar for the sentence provided by the user
		HashMap<String, String> sentenseEncodedGrammar = new HashMap<>();
		for (String file : this.fileList) {
			String grammar = file.split("\\.")[0];
			sentenseEncodedGrammar.put(grammar, this.grammarEncoder.encode(grammar, tokenArray));
		}
	}
}