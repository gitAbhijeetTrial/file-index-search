package com.vonage.api.interview;

import com.vonage.api.interview.scoring.FileScores;

import java.util.*;
import java.util.stream.Collectors;

import static com.vonage.api.interview.utils.Constants.WORD_DELIMITER_REGEX;
import static com.vonage.api.interview.utils.Utils.calculateScore;
import static com.vonage.api.interview.utils.Utils.sortByValue;

public class SearchHandler implements CommandHandlers.SearchHandler {

    // The indices map holds the indexed data. It's a map from index names to another map,
    // which maps file paths to sets of words in each file.
    private final Map<String, Map<String, Set<String>>> indices;

    // Constructor that takes the indices map. This map is shared across instances
    // of the IndexHandler and SearchHandler.
    public SearchHandler(Map<String, Map<String, Set<String>>> indices) {
        this.indices = indices;
    }

    @Override
    public FileScores search(String indexName, String searchString) {

        // If the search string is null or empty, return an empty FileScores object.
        if (searchString == null || searchString.trim().isEmpty()) {
            return new FileScores(Collections.emptyMap());
        }

        // Retrieve the specific index based on the indexName. This index contains the words of each file.
        Map<String, Set<String>> index = indices.get(indexName);
        if (index == null) {
            // If the index does not exist, throw an exception.
            throw new IllegalArgumentException("Index " + indexName + " does not exist.");
        }

        // Split the search string into words, convert them to lowercase, and collect them into a set.
        Set<String> searchWords = Arrays.stream(searchString.split(WORD_DELIMITER_REGEX))
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        // A map to store the scores for each file. The scores are calculated based on the presence of search words.
        Map<String, Integer> scores = new HashMap<>();
        index.forEach((filePath, wordsInFile) -> {

            // Calculate the score for each file and put it in the scores map.
            int score = calculateScore(wordsInFile, searchWords);
            scores.put(filePath, score);
        });

        // Return the scores sorted by value in descending order.
        return new FileScores(sortByValue(scores));
    }
}
