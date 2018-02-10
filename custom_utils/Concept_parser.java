package custom_utils;

import java.util.*;
import java.lang.*;
import java.io.*;

class FileSystemUtility {
	public ArrayList<String> generateFileList(String path) {
		ArrayList<String> files = new ArrayList<String>();
		File folder = new File(path);
		System.out.println(folder);
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

// dummy
class ReverseMappingUtility {
	private HashMap<String, ArrayList<String>> wordVsConceptHash = new HashMap<>();

	ReverseMappingUtility() {
		// generate the map once during initialization
		String[] array_1 = { "book_concept", "create_concept" };
		String[] array_2 = { "talk_concept" };
		String[] array_3 = { "call_concept" };
		wordVsConceptHash.put("make", new ArrayList<String>(Arrays.asList(array_1)));
		wordVsConceptHash.put("message", new ArrayList<String>(Arrays.asList(array_2)));
		wordVsConceptHash.put("written", new ArrayList<String>(Arrays.asList(array_2)));
		wordVsConceptHash.put("place", new ArrayList<String>(Arrays.asList(array_3)));
		wordVsConceptHash.put("phone", new ArrayList<String>(Arrays.asList(array_3)));
		wordVsConceptHash.put("make", new ArrayList<String>(Arrays.asList(array_3)));
	}

	public ArrayList<String> findConceptList(String token) {
		ArrayList<String> conceptList = this.wordVsConceptHash.get(token);
		if (conceptList != null) {
			return conceptList;
		} else {
			return null;
		}
	}
}

// dummy
class ConceptTableUtility {
	private HashMap<String, HashMap<String, Boolean>> wordVsConceptTable = new HashMap<>();

	ConceptTableUtility() {
		HashMap<String, Boolean> temp_1 = new HashMap<>();
		temp_1.put("place", true);
		temp_1.put("phone", true);
		temp_1.put("call", true);
		temp_1.put("make", true);
		temp_1.put("a", true);
		this.wordVsConceptTable.put("call_concept", temp_1);

		temp_1 = new HashMap<>();
		temp_1.put("make", true);
		this.wordVsConceptTable.put("book_concept", temp_1);

		temp_1 = new HashMap<>();
		temp_1.put("make", true);
		temp_1.put("create", true);
		this.wordVsConceptTable.put("create_concept", temp_1);

		temp_1 = new HashMap<>();
		temp_1.put("message", true);
		temp_1.put("written", true);
		this.wordVsConceptTable.put("message_concept", temp_1);
	}

	public ArrayList<String> findConceptList(ArrayList<String> conceptList, String token) {
		ArrayList<String> newConceptList = new ArrayList<String>();
		for (String concept : conceptList) {
			HashMap<String, Boolean> tokenVsBooleanMap = this.wordVsConceptTable.get(concept);
			if (tokenVsBooleanMap != null && tokenVsBooleanMap.get(token) != null
					&& tokenVsBooleanMap.get(token) == true) {
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
	public String generate_concept(String sentense) {

		// not working code
		FileSystemUtility fileSystemUtility = new FileSystemUtility();
		ArrayList<String> list = fileSystemUtility.generateFileList("/resources/Concept");
		// 

		// get utility object for first word tagging
		ReverseMappingUtility reverseMappingUtility = new ReverseMappingUtility();

		// get utility object for subsequent word tagging
		ConceptTableUtility conceptTableUtility = new ConceptTableUtility();

		sentense = sentense.toLowerCase();
		String[] tokenList = sentense.split("\\s+");
		StringBuilder result = new StringBuilder();
		ArrayList<String> currentTokenConceptList = null;
		for (String token : tokenList) {
			if (currentTokenConceptList == null) {
				currentTokenConceptList = reverseMappingUtility.findConceptList(token);
				if (currentTokenConceptList == null) {
					result.append(token + " ");
				}
			} else {
				String prev_concept = currentTokenConceptList.get(0);
				currentTokenConceptList = conceptTableUtility.findConceptList(currentTokenConceptList, token);
				// System.out.print(prev_concept + " : ");
				// System.out.print(token + " : ");
				// System.out.println(currentTokenConceptList);

				if (currentTokenConceptList == null) {
					result.append("{" + prev_concept + "} ");
				}
			}
		}
		return result.toString();
	}
}