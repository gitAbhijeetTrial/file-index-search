# Abhijeet Notes

These things are added to understand the project.

# Directory Structure: 
It's well-organized, with separate packages for different responsibilities (io, scoring, etc.).

# Classes and Packages:

"com.vonage.api.interview" This is our main package.
"io" This package contain classes related to input/output operations, like loading directories (DirectoryLoader).
"scoring" This package contains FileScores, which you'll use to represent the scores of files during a search.
"Application" This is our main class with the main method.
"CommandHandlers" This class is used to handle different commands.
"OurWishIsYourCommands" This class should provide instances of CommandHandlers.
"Utils" This class contains common methods used.
"Constants" This class contains all the constants used in project.

# Here's a high-level approach to how you can implement the code:

1. Indexing (IndexHandler):

> In the OurWishIsYourCommands class, within the lambda for the IndexHandler, we will need to create logic that walks through the given directory, reads the files, and constructs an index.
> This index could be a simple HashMap where the key is the file path and the value is a list or set of words contained in that file.
> Use the DirectoryLoader class to load files from the directory.
> We might also want to preprocess the text (e.g., lowercasing, removing punctuation) to make the search more effective.


2. Searching (SearchHandler):

> In the OurWishIsYourCommands class, within the lambda for the SearchHandler, we will need to create logic that searches the previously created index for the search string.
> Split the search string into words and then search for these words in your index. The FileScores should reflect the relevance of each file based on the number of search words found.
> We might want to create a scoring system, for example, each word found could be worth a certain number of points, and a file's total score would be the sum of the points for all the words found within it.


3. File Scoring:

> The FileScores class will be used to return the search results. Make sure to sort the results based on the scores so that the highest scores are at the top.


4. Putting it all together:

> The Application class handles the command-line input and output. It will call your handlers when the index or search command is input by the user.


5. Validations:

> Need to make sure to test the application thoroughly, including scenarios where the directory does not exist, is empty, or contains non-text files, and scenarios where the search string is empty or contains special characters.

The directory does not exist: When the DirectoryLoader tries to load files from a non-existent directory, it should throw a DirectoryLoadException. Index method in MyIndexHandler should handle this exception appropriately.

The directory is empty: If the directory is empty, the index method should not throw an error but rather create an empty index.

The directory contains non-text files: Non-text files should not cause the indexing process to fail. We could either skip these files or try to read them as text files and handle any exceptions that may occur.

The search string is empty: If the search string is empty, the search method could return an empty FileScores object or treat it as a search that matches no words.

The search string contains special characters: Special characters should be handled consistently both during indexing and searching. For example, you might decide to strip out special characters or to treat them as word separators.

Case sensitivity: The application should handle text in a case-insensitive manner for both indexing and searching.


# So the OurWishIsYourCommands class includes

> A MyIndexHandler that uses DirectoryLoader to load file contents and create a searchable index.
> A MySearchHandler that uses the created index to search for words, calculate scores for each file, and return a sorted FileScores object.
> Error handling for the case where the directory cannot be loaded.
> Handling for empty search strings by returning an empty FileScores object.


# Here's a summary of how the code works:

Indexing: When index is called, it uses the DirectoryLoader to load all files in the specified directory and creates an index mapping each file path to a set of words contained in that file. This index is then stored in a static map indices with the index name as the key.

Searching: When search is called, it retrieves the relevant index by name and then calculates the relevance score of each file based on how many search terms are found in the file. The scores are then sorted in descending order, and a FileScores object containing this sorted map is returned.

Error Handling: The code catches DirectoryLoader.DirectoryLoaderException to handle any issues that arise from attempting to load the directory, such as the directory not existing or being unreadable. When this happens, it logs the error and stores an empty index, indicating no files to search within.

# To Run the Application

Use the below commands
> index myIndex /path/to/directory
> search myIndex exampleSearchTerm
> exit


# Commands

index index1 test_directory
search index1 Abhijeet Vishwas Deokar

# Expected Output
/path/to/test_directory/file1.txt: 100
/path/to/test_directory/file2.txt: 66
/path/to/test_directory/file3.txt: 33
/path/to/test_directory/file-empty.txt: 0
/path/to/test_directory/no-word.txt: 0
