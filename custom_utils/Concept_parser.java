package custom_utils;

import java.util.*;
import java.lang.*;
import java.io.*;
import custom_utils.*;

class ReverseMappingUtility {
	private HashMap<String, ArrayList<String>> wordVsConceptHash = new HashMap<>();
	FileRead filereader = new FileRead();

	ReverseMappingUtility(ArrayList<String> fileList, String __PATH__) {
		for (String file : fileList) {
			try {

				for (String line : filereader.readFileAsLine(new File(__PATH__ + "/" + file))) {
					String first_word = line.split("\\s+")[0];
					String concept = file.split("\\.")[0];

					if (wordVsConceptHash.get(first_word) == null) {
						wordVsConceptHash.put(first_word, new ArrayList<String>());
						wordVsConceptHash.get(first_word).add(concept);
					} else {
						if (!wordVsConceptHash.get(first_word).contains(concept)) {
							wordVsConceptHash.get(first_word).add(concept);
						}
					}
				}
			} catch (Exception e) {
				System.out.println("Error reported" + e);
			}
		}
	}

	public ArrayList<String> findConceptList(String token) {

		// get keyset
		Set<String> keyList = this.wordVsConceptHash.keySet();

		// find best match with token using distance algo
		double max_threshold = Double.MIN_VALUE;
		String max_threshold_token = "";
		for (String key : keyList) {
			double temp = PlaceholderMatch.jaro_winkler_dist(token, key);
			// System.out.println(key + " -- " + token + " -- " + temp);
			if (temp > max_threshold && temp >= 0.9) {
				max_threshold = temp;
				max_threshold_token = key;
			}
		}

		ArrayList<String> conceptList = this.wordVsConceptHash.get(max_threshold_token);
		if (conceptList != null) {
			return conceptList;
		} else {
			return null;
		}
	}
}

class ConceptTableUtility {
	private HashMap<String, HashMap<String, Boolean>> wordVsConceptTable = new HashMap<>();
	private FileRead filereader = new FileRead();

	ConceptTableUtility(ArrayList<String> fileList, String __PATH__) {
		for (String file : fileList) {
			HashMap<String, Boolean> inner_map = new HashMap<>();
			String concept = file.split("\\.")[0];
			wordVsConceptTable.put(concept, inner_map);

			try {
				for (String line : filereader.readFileAsLine(new File(__PATH__ + "/" + file))) {
					String[] wordList = line.split("\\s+");
					for (String word : wordList) {
						if (!inner_map.containsKey(word)) {
							inner_map.put(word, true);
						}
					}
				}
			} catch (Exception e) {
				System.out.println("Error reported");
			}
		}
	}

	public ArrayList<String> findConceptList(ArrayList<String> conceptList, String token) {
		ArrayList<String> newConceptList = new ArrayList<String>();
		for (String concept : conceptList) {
			HashMap<String, Boolean> tokenVsBooleanMap = this.wordVsConceptTable.get(concept);

			// get token set for specific concept
			Set<String> keyList = tokenVsBooleanMap.keySet();

			// find best match with token using distance algo
			double max_threshold = Double.MIN_VALUE;
			String max_threshold_token = "";
			for (String key : keyList) {
				double temp = PlaceholderMatch.jaro_winkler_dist(token, key);
				// System.out.println(key + " -- " + token + " -- " + temp);
				if (temp > max_threshold && temp >= 0.9) {
					max_threshold = temp;
					max_threshold_token = key;
				}
			}

			if (tokenVsBooleanMap != null && tokenVsBooleanMap.get(max_threshold_token) != null
					&& tokenVsBooleanMap.get(max_threshold_token) == true) {
				newConceptList.add(concept);
			}
		}

		if (newConceptList.size() != 0) {
			return newConceptList;
		} else {
			return null;
		}
	}
}

public class Concept_parser {
	private String __RESOUCSE_PATH__ = "./resources/";
	private ArrayList<String> fileList;
	private ReverseMappingUtility reverseMappingUtility;
	private ConceptTableUtility conceptTableUtility;
	public FileRead filereader = new FileRead();

	private ArrayList<String> combinator(ArrayList<ArrayList<String>> conceptList, ArrayList<String> sentense_frame,
			int list_index, int concept_index) {
		ArrayList<String> temp = new ArrayList<>();
		if (list_index == sentense_frame.size()) {
			temp.add("");
		} else {

			if (sentense_frame.get(list_index).equals("[c]")) {
				ArrayList<String> textList = combinator(conceptList, sentense_frame, list_index + 1, concept_index + 1);
				for (String text : textList) {
					for (String curText : conceptList.get(concept_index)) {
						temp.add(curText + " " + text);
					}
				}
			} else {
				ArrayList<String> textList = combinator(conceptList, sentense_frame, list_index + 1, concept_index);
				for (String text : textList) {
					temp.add(sentense_frame.get(list_index) + " " + text);
				}
			}
		}
		return temp;
	}

	private ArrayList<String> generate_combination(ArrayList<ArrayList<String>> conceptList,
			ArrayList<String> sentense_frame) {
		return combinator(conceptList, sentense_frame, 0, 0);
	}

	public Concept_parser() {
		// list all files in concept folder
		this.fileList = filereader.getFileList(new File(__RESOUCSE_PATH__ + "Concept"));

		// get utility object for first word tagging
		this.reverseMappingUtility = new ReverseMappingUtility(fileList, __RESOUCSE_PATH__ + "Concept");

		// get utility object for subsequent word tagging
		this.conceptTableUtility = new ConceptTableUtility(fileList, __RESOUCSE_PATH__ + "Concept");
	}

	public ArrayList<String> generate_concept(String sentense) {
		sentense = sentense.toLowerCase();
		String[] tokenList = sentense.split("\\s+");
		StringBuilder result = new StringBuilder();
		ArrayList<ArrayList<String>> conceptList = new ArrayList<>();
		ArrayList<String> sentense_frame = new ArrayList<>();
		ArrayList<String> sentense_combinations = new ArrayList<>();

		ArrayList<String> currentTokenConceptList = null;
		for (String token : tokenList) {
			// System.out.print(currentTokenConceptList);
			// System.out.print(" : ");
			// System.out.println(token);
			if (currentTokenConceptList == null) {
				currentTokenConceptList = this.reverseMappingUtility.findConceptList(token);
				if (currentTokenConceptList == null) {
					sentense_frame.add(token);
				}
			} else {
				ArrayList<String> prev_concept = currentTokenConceptList;
				currentTokenConceptList = this.conceptTableUtility.findConceptList(currentTokenConceptList, token);
				if (currentTokenConceptList == null) {
					// result.append("{" + prev_concept.get(0) + "} ");
					ArrayList<String> conceptTempList = new ArrayList<>();
					for (String concept : prev_concept) {
						conceptTempList.add("{" + concept + "}");
					}
					conceptList.add(conceptTempList);
					sentense_frame.add("[c]");

					currentTokenConceptList = this.reverseMappingUtility.findConceptList(token);
					if (currentTokenConceptList == null) {
						sentense_frame.add(token);
					}
				}
			}
		}

		// System.out.println(conceptList);
		// System.out.println(sentense_frame.toString());
		sentense_combinations = generate_combination(conceptList, sentense_frame);
		// System.out.println(sentense_combinations);
		return sentense_combinations;
	}
}
