package com.vonage.api.interview.utils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Utils {
    // This method calculates the score for a file based on the number of search words present in it.
    public static int calculateScore(Set<String> wordsInFile, Set<String> searchWords) {
        int totalWords = searchWords.size();
        int matches = 0;

        // Count how many search words are present in the file.
        for (String word : searchWords) {
            if (wordsInFile.contains(word)) {
                matches++;
            }
        }

        // If no search words are found, return a score of 0.
        if (matches == 0) {
            return 0;
        }

        // Calculate the score as a percentage of the search words found in the file.
        return (int) (((double) matches / totalWords) * 100);
    }

    // This method sorts a map by its values in descending order.
    public static Map<String, Integer> sortByValue(Map<String, Integer> unsortedMap) {
        return unsortedMap.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }
}
