# Technex Hackathon Repo.

* DateTimePlaceholder.java - pattern matching for placeholders DateTime, Day, Date, Number
```
DateTimePlaceholderDat mm = new DateTimePlaceholder();
String st = ma.find_dateTime("9th February");
```
* GrammarWeight.java - Provides you with **term frequency**, **inverse document frequency** and **Tf-IDF**.
```
try{
    GrammarWeight gw = new GrammarWeight(); 
	HashMap<String, Double> idf = gw.getIDF(); // generates IDF of everyword.
	for(String ss: idf.keySet())
	    System.out.println(ss+" "+String.valueOf(idf.get(ss)));
	}catch(Exception e){
			System.out.println(e);
	}
```
```
try{
	GrammarWeight gw = new GrammarWeight(); 
	HashMap<String, HashMap<String, Double>> tf = gw.getTF(); // generate TF of every word.
	HashMap<String, HashMap<String, String>> charMap = gw.getSymbol();
	for(String ss: tf.keySet()){
		System.out.println(ss);
		for(String word : tf.get(ss).keySet()){
			System.out.println(word+" "+charMap.get(ss).get(word)+" "+String.valueOf(tf.get(ss).get(word)));
			}
		System.out.println();
		}
	}catch(Exception e){
		System.out.println(e);
	}
```
