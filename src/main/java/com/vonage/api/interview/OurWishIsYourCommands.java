package com.vonage.api.interview;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class OurWishIsYourCommands {

    // This map will hold the indexed data. Each index is a map from file paths to sets of words in each file.
    private static final Map<String, Map<String, Set<String>>> indices = new HashMap<>();

    public static CommandHandlers createCommandHandlers() {
        // Note that you must not change the signature of this method, or the name of this class.
        // TODO: Instantiate your dependencies here, DO NOT use any kind of dependency injection frameworks

        // Here we are creating and returning a new instance of CommandHandlers.
        // The CommandHandlers constructor takes two functional interfaces as parameters.
        // We implement these interfaces using lambda expressions.
        return new CommandHandlers(

                // The first lambda expression implements the IndexHandler.
                // It creates a new instance of IndexHandler and calls its index method.
                // The IndexHandler will use the shared 'indices' map to store indexed data.
                (indexName, directory) -> new IndexHandler(indices).index(indexName, directory),

                // The second lambda expression implements the SearchHandler.
                // It creates a new instance of SearchHandler and calls its search method.
                // The SearchHandler will use the shared 'indices' map to search through indexed data.
                (indexName, searchString) -> new SearchHandler(indices).search(indexName, searchString)
        );
    }

}
