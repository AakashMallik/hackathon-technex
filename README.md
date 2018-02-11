# Technex Hackathon

### Round 1 Final Submission

# Rule Based NLU – Pattern Matching Algorithm

NOTE: All data files which were initially provided are kept in ./resources/ directory. The final output.txt is present at ./resources/Testing/ directory.

## Assumptions/Edits in initial resources

The given data had some errors. We modified the given data at some places:

1. The keyword 'alram' in the file ./resources/Grammar/CreateAlarmWithTime.txt was changed to 'alarm'.
2. A space character was inserted in between '{please_concept}{tell_concept}' so that we can have two different tokens. The files changed in this case were ./resources/Grammar/WeatherInCity.txt and /resources/Grammar/WeatherTimeCity.txt.
3. The BookTicket.txt was moved from /resources/Concept/ to /resources/Grammar/ directory.

4. The keyword 'thunderstrom' was changed to 'thunderstorm' in file ./resources/Concept/weather_activity.txt.

## Our Approach to the problem

The two major task involved in our solution:

1. Conversion of the input sentence into the Grammar compatible form.
2. Matching of above converted sentence to all grammars available.


## First task:

1. We target day, date, time, datetime placeholders in the text by using various regex patterns(see custom_utils/DateTimePlaceholder.java).

2.  We target contact names and places by first extracting every token from the given data, and then matching them with our input sentence. Here, we ensure that a fuzzy matching also takes place between tokens and input sentence. For computing such similarity scores, we use Jaro Winkler Distance algorithm(See custom_utils/PlaceholderMatch.java). This algorithm is good matching algo for smaller length strings(like names).

3.  We identify concepts in the input sentence by first building a general purpose matrix. This matrix is of shape NUM_CONCEPTS X VOCABULARY, and its element M[i][j] is a boolean value describing whether the jth word in the vocabulary will belong to concept i. We use this matrix to deduce concepts in the input sentence(See custom_utils/Concept_parser.java).

    After the above steps, we move to our next task. 

    Note: consider the transformed sentence as S from now on.

## Second task

1.  First of all, we preprocess the given grammar txt files. We observed that some tokens were exclusive to some specfic grammar rules(for e.g 'alarm') and some tokens were spread across all grammar rules(e.g. '<please_concept>'). We decided to find some weights for each token, which can help us in matching S with grammar rules. We adopted the TF-IDF method for finding such weights(See See custom_utils/GrammarWeight.java).

2. Now the next task was to actually match the transformed sentence S with grammar rules. Given a grammar file, we choose a rule from that file, and then match our S with the rule. S and the rule are converted to a new form first (based on TF-IDF weights) and then matched with a algorithm. We chose Levenshtein Distance algorithm first(see custom_utils/LevenshteinDistance.java), but we got better results with Jaro Distance algorithm.

3.  For this task, please see custom_utils/GrammarAnalyser.java.

    After this, we just chose our final answer as the grammar rule which has maximum similarity score among all others. And this completes the complete problem.

## Running the program
```
$ javac samsungtest.java
$ java samsungtest
```
## Contributors
1. Aakash Mallik [Github @AakashMallik](https://github.com/AakashMallik)
2. Utsav Krishnan [Github @ketankr9](https://github.com/ketankr9)
3. Divesh Pandey [Github @pandeydivesh15](https://github.com/pandeydivesh15)






