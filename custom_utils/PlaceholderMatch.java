package custom_utils;

import java.io.*;
import java.util.*;
import java.lang.*;

public class PlaceholderMatch {
	String names_path = "./resources/PlaceHolder/contact_name.txt";
	String places_path = "./resources/PlaceHolder/places.txt";

	ArrayList<String> all_names = new ArrayList<String>();
	ArrayList<String> all_names_individual = new ArrayList<String>();
	ArrayList<String> all_places = new ArrayList<String>();

	public HashMap<String, ArrayList<String>> all_replacements = new HashMap<String, ArrayList<String>>();

	int max_len_name = 0;
	int max_len_place = 0;

	public PlaceholderMatch() {
		// System.out.println("Constructor called");
		read_contents();

		ArrayList<String> arraylist = new ArrayList<String>();
		ArrayList<String> arraylist2 = new ArrayList<String>();

		all_replacements.put("<place>", arraylist);
		all_replacements.put("<contact_name>", arraylist2);
	}

	public void read_contents() {
		// Read all names
		BufferedReader reader;
		String[] arr_of_str;
		try {
			reader = new BufferedReader(new FileReader(names_path));
			String line = reader.readLine();
			while (line != null) {
				// System.out.println(line);

				line = line.toLowerCase();
				all_names.add(line.trim());
				arr_of_str = line.split(" ", 0);
				for (String str : arr_of_str) {
					all_names_individual.add(str);
				}

				max_len_name = Math.max(arr_of_str.length, max_len_name);

				// read next line
				line = reader.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println(all_names);

		// Now get placess names
		try {
			reader = new BufferedReader(new FileReader(places_path));
			String line = reader.readLine();
			while (line != null) {
				// System.out.println(line);

				line = line.toLowerCase();
				all_places.add(line.trim());
				arr_of_str = line.split(" ", 0);
				// for (String str : arr_of_str) {
				// 	all_places.add(str);
				// }
				max_len_place = Math.max(arr_of_str.length, max_len_place);

				// read next line
				line = reader.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println(all_places);

		return;
	}

	public static double jaro_winkler_dist(String a, String b){
		return jaro_winkler_dist(a, b, true);
	}

	public static double jaro_winkler_dist(String a, String b, boolean winkler) {
		if (a.equals(b)) {
			return 1.0;
		}
		if (a.isEmpty() || b.isEmpty()) {
			return 0;
		}

		int matches = 0;
		int prefix_match = 0; // at most 4 characters
		int transpositions = 0; // characters that match but are not aligned, only close to each other

		int max_length = Math.max(a.length(), b.length());
		int max_match_distance = Math.max((int) Math.floor(max_length / 2.0) - 1, 0);

		if(!winkler)
			max_match_distance = max_length;

		String shorter = a.length() < b.length() ? a : b;
		String longer = a.length() >= b.length() ? a : b;

		for (int i = 0; i < shorter.length(); i++) {

			// Find a match
			boolean match_bool = shorter.charAt(i) == longer.charAt(i);

			if (match_bool && i < 4) {
				prefix_match++;
			}
			if (match_bool) {
				matches++;
				continue; // Go to next character
			}

			int j = Math.max(i - max_match_distance, 0);
			for (; j < Math.min(i + max_match_distance, longer.length()); j++) {
				if (i != j) {
					match_bool = shorter.charAt(i) == longer.charAt(j);
					if (match_bool) {
						transpositions++;
						break;
					}
				}
			}
		}

		if (matches == 0)
			return 0; // No need to progress ahead
		transpositions = (int) (transpositions / 2.0);
		
		double jaro_score = (1.0 / 3) * (matches / (double) longer.length() + matches / (double) shorter.length()
				+ (matches - transpositions) / (double) matches);
		if(!winkler)
			return jaro_score;

		double score = jaro_score + prefix_match * 0.1 * (1.0 - jaro_score);
		// standard p value = 0.1 (consult wikipedia)
		return score; // higher score == more simialarity
	}

	public String find_placeholder(String s) {
		// System.out.println(jaro_winkler_dist("Delhi", "New Delhi"));
		// return "Hello";
		boolean flag = false;

		s = s.toLowerCase();

		ArrayList<String> str_list = new ArrayList<String>();

		String[] arr_of_str = s.trim().split(" ", 0);
		for (String str : arr_of_str) {
			str_list.add(str);
		}

		// First we check for names
		for (int l = max_len_name; l > 0; l--) {
			for (int i = 0; i < str_list.size(); i++) {
				int j = Math.min(i + l - 1, str_list.size() - 1);
				String temp = "";
				for (int k = i; k <= j; k++) {
					temp += str_list.get(k);
					temp += " ";
				}
				temp = temp.trim();
				flag = false;
				for (String name : all_names) {
					double similarity_score = jaro_winkler_dist(temp, name);
					if (similarity_score > 0.85) {
						// Match
						String temp_str = str_list.get(i);
						
						str_list.set(i, "<contact_name>");
						int c = i + 1;

						while (c <= j) {
							temp_str = temp_str + ' ' + str_list.get(i + 1);
							str_list.remove(i + 1);
							c++;
						}
						ArrayList<String> temp_list = all_replacements.get("<contact_name>");
						temp_list.add(temp_str);
						all_replacements.put("<contact_name>", temp_list);
						flag = true;
						break;
					}
				}
				if (l == 1 && flag == false) {
					for (String name : all_names_individual) {
						double similarity_score = jaro_winkler_dist(temp, name);
						if (similarity_score > 0.85) {
							// Match
							str_list.set(i, "<contact_name>");
							flag = true;
							break;
						}
					}
				}
				// if(flag){
				// 	break;
				// }
			}
			// if(flag){
			// 	break;
			// }
		}
		// System.out.println(str_list);
		// Now for places
		flag = false;
		for (int l = max_len_place; l > 0; l--) {
			for (int i = 0; i < str_list.size(); i++) {
				int j = Math.min(i + l - 1, str_list.size() - 1);
				String temp = "";
				for (int k = i; k <= j; k++) {
					temp += str_list.get(k);
					temp += " ";
				}
				temp = temp.trim();

				flag = false;
				for (String name : all_places) {
					// if (name == "varanasi")
					// 	System.out.println(str_list.get(i));
					double similarity_score = jaro_winkler_dist(temp, name);

					if(temp.split(" ", 0).length > 1)
						similarity_score += 0.05;
					
					if (similarity_score > 0.82) {

						if (temp.split(" ", 0).length != name.split(" ", 0).length) {
							continue;
						}
						// System.out.println(similarity_score);
						String temp_str = str_list.get(i);
						// Match
						str_list.set(i, "<place>");
						int c = i + 1;
						while (c <= j) {
							temp_str = temp_str + ' ' + str_list.get(i + 1);
							str_list.remove(i + 1);
							c++;
						}

						ArrayList<String> temp_list = all_replacements.get("<place>");
						temp_list.add(temp_str);
						all_replacements.put("<place>", temp_list);

						flag = true;
						break;
					}
				}
				// if(l == 1){
				// 	for (String name : all_names_individual) {
				// 		double similarity_score = jaro_winkler_dist(temp, name);
				// 		if(similarity_score > 0.85){
				// 			// Match
				// 			str_list.set(i, "<contact_name>");
				// 			flag = true;
				// 			break;
				// 		}
				// 	}
				// }
				// if(flag){
				// 	break;
				// }
			}
			// if(flag){
			// 	break;
			// }
		}

		String final_answer = "";
		for (String str : str_list) {
			final_answer += str;
			final_answer += " ";
		}

		return final_answer.trim();
	}

	// public  void main(String[] args) {
	// 	// Read all contents first
	//
	// }

}
