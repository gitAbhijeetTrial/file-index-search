package com.vonage.api.interview;

import com.vonage.api.interview.io.DirectoryLoader;
import com.vonage.api.interview.io.DirectoryLoader.FileContent;

import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static com.vonage.api.interview.utils.Constants.WORD_DELIMITER_REGEX;


public class IndexHandler implements CommandHandlers.IndexHandler {

    // indices is a shared map between IndexHandler and SearchHandler.
    // It maps index names to another map, which in turn maps file paths to sets of words in each file.
    private final Map<String, Map<String, Set<String>>> indices;

    // DirectoryLoader is used to load the content of files in a directory.
    private final DirectoryLoader loader = new DirectoryLoader();

    // Constructor takes the shared indices map.
    public IndexHandler(Map<String, Map<String, Set<String>>> indices) {
        this.indices = indices;
    }

    @Override
    public void index(String indexName, String directory) {
        try {
            // Load the files and their contents from the specified directory.
            Collection<FileContent> fileContents = loader.load(Paths.get(directory));

            // Process each file and create a local index: a map of file paths to sets of words in each file.
            Map<String, Set<String>> localIndex = fileContents.stream()
                    .collect(Collectors.toMap(
                            FileContent::path,
                            // Split each file's content into words, convert them to lowercase, and collect as a set.
                            content -> Arrays.stream(content.contents().split(WORD_DELIMITER_REGEX))
                                    .map(String::toLowerCase)
                                    .collect(Collectors.toSet())
                    ));

            // Store the local index in the global indices map with the given index name.
            indices.put(indexName, localIndex);

        } catch (DirectoryLoader.DirectoryLoaderException e) {

            // Handle any errors thrown by DirectoryLoader and log them.
            System.err.println("Error loading directory: " + e.getMessage());

            // Use an empty map as the index in case of an error or if the directory is empty.
            indices.put(indexName, Collections.emptyMap());
        }
    }
}
