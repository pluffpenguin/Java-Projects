import java.util.*;

/**
 * Program to write the PhraseAnalyzer
 * @author Jason Lin
 */
public class PhraseAnalyzer
{
    /**
     * Total letters in the alphabet
     */
    public static final int ALPHABET_LETTERS = 26;

    /**
     * The whole program written out in main
     * 
     * @param args main string
     */
    public static void main(String[] args)
    {
        if(isValidPhrase(args))
        {
            String targetPunctuationWord = "This;;IsanExample!Of";
            String[] samplePhrase = {"This", "Is", "An", "Example", "Phrase", "Is", "Of", "an","Duplicates"};
            System.out.println("Removing punctuations for" + targetPunctuationWord + ": " + removePunctuationForWord(targetPunctuationWord));
            System.out.println("Testing smallestWords: " + getSmallestWords(samplePhrase));
            
            System.out.print("Getting words: \n");
            String[] wordsList = getWords(args);
            for (int i = 0; i < wordsList.length; i++){
                System.out.print(wordsList[i] + " ");
            }
            System.out.println("");
            
            System.out.println("Smallest word(s): " + getSmallestWords(args));
            System.out.println("Largest word(s): " + getLargestWords(args));
            System.out.print("Average word length: ");
            System.out.printf("%.2f", getAverageWordLength(args));
            System.out.println();

            // System.out.println("Get Letter Tally:");
            // int[] letterTally = getLetterTally(getWords(args));
            // for (int i = 0; i < letterTally.length; i++)
            // {
            //     System.out.println((char)(i+65) + ", " + letterTally[i]);
            // }

            System.out.println("Least frequently used letter(s): " +
            getLeastFrequentLetters(args));
            System.out.println("Most frequently used letter(s): " +
            getMostFrequentLetters(args));
        }
        else
        {
            System.out.println("Invalid phrase");
        }
    }


    public static String removePunctuationForWord(String word)
    {
        char[] punctuations = {',', '.', '!', ';'};

        for (int i = 0; i < word.length(); i++)
        {
            // For every individual letter in the word
            // loop through punctuations character array to check
            for (int j = 0; j < punctuations.length; j++)
            {
                if (word.charAt(i) == punctuations[j])
                {
                    word = word.substring(0, i) + word.substring(i+1);
                    i--;
                    break;
                }
            }
        }
        return word;
    }
    /**
     * This determine if the input phrase is valid
     * true if valid, false if not
     *
     * @param phrase The String array being checked if valid
     * @return true if valid, false if not
     */
    public static boolean isValidPhrase(String[] phrase)
    {
        String[] punctuations = {",", ".", "!", ";"};
        String[] numbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
        for(int i = 0; i < phrase.length; i++)
        {
            // Check if letter at index 'i' is a number, return false if is
            for(int j = 0; j < numbers.length; j++)
            {
                if(phrase[i].equals(numbers[j]))
                {
                    return false;
                }
            }

            // Check if letter at index 'i' is a punctuation
            for(int j = 0; j < punctuations.length; j++)
            {
                if(i != phrase.length - 1)
                {
                    if(phrase[i].equals(punctuations[j]))
                    {
                        // Check the letter phrase at 'i + 1' if it's a punctuation
                        for(int n = 0; n < punctuations.length; n++)
                        {
                            if(phrase[i + 1].equals(punctuations[n]))
                            {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        
        return true;
    }
    


    /**
     * This method returns a String array of "words" inputted
     * without any punctuation in the order entered
     * 
     * @param phrase The String array the user entered to be checked
     * @return an array of the words without punctuation
     * @throw IllegalArgumentException if the phrase is invalid
     */
    public static String[] getWords(String[] phrase)
    {  
        for (int i = 0; i < phrase.length; i++){
            phrase[i] = removePunctuationForWord(phrase[i]);
        }
        return phrase;
    }

    /**
     * Returns a String of the smallest unique word(s)
     * in the order they appear with words being
     * separated with a comma and a space unless
     * it's the last word
     * 
     * @param words The String array the user inputted
     * @return a String with the smallest words
     * @throw IllegalArgumentException if the words array is null
     * @throw IllegalArgumentException if the length of words array is 0
     * @throw IllegalArgumentException if a word is null, length is 0, or character isn't a letter
     */
    public static boolean isWordAlreadyAdded(String[] alreadyAddedWords, String word)
    {
        for (int i = 0; i < alreadyAddedWords.length; i++)
        {
            if ( alreadyAddedWords[i].equals(word) )
            {
                return true;
            }
        }
        return false;
    }
    
    public static String getSmallestWords(String[] words)
    {
        if(words.equals(null))
        {
            throw new IllegalArgumentException("Null words");
        }

        if(words.length == 0)
        {
            throw new IllegalArgumentException("Zero length");
        }
        
        int smallestWordLength = words[0].length();
        String smallestWords = "";
        String[] alreadyAddedWords = {};

        // Get the length of the smallest word
        for (int i = 0; i < words.length; i++)
        {
            if(words[i] == null || words[i].length() == 0 || !isValidWord(words[i]))
            {
                throw new IllegalArgumentException("Invalid words");
            }

            if (words[i].length() < smallestWordLength)
            {
                smallestWordLength = words[i].length();
            }
        }
        
        // Get all words with the same length, add them to smallestWords string
        for (int i = 0; i < words.length; i++)
        {
            if (words[i].length() == smallestWordLength && !isWordAlreadyAdded(alreadyAddedWords, words[i]))
            {
                // If there is a word in the array & not at the end of the phrase
                if (smallestWords.length() > 0)
                {
                    smallestWords = smallestWords.concat(", ");
                }
                smallestWords = smallestWords.concat(words[i]);
                // Append to the array  of already added words
                alreadyAddedWords = Arrays.copyOf(alreadyAddedWords, alreadyAddedWords.length + 1);
                alreadyAddedWords[alreadyAddedWords.length - 1] = words[i];
            }
        }
        return smallestWords;
    }
    

    /**
     * Returns a String of the largest unique word(s)
     * in the order they appear with words being
     * separated with a comma and space unless
     * it's the last word
     * 
     * @param words The String array the user inputted
     * @return a String with the largest words
     * @throw IllegalArgumentException if the words array is null
     * @throw IllegalArgumentException if the length of the words array is 0
     * @throw IllegalArgumentException if a word is null, length is 0, or character isn't a letter
     */
    public static String getLargestWords(String[] words)
    {
        if(words.equals(null))
        {
            throw new IllegalArgumentException("Null words");
        }

        if(words.length == 0)
        {
            throw new IllegalArgumentException("Zero length");
        }

        int largestWordLength = words[0].length();
        String largestWords = "";
        String[] alreadyAddedWords = {};
    
        // Get the length of the largest word
        for (int i = 0; i < words.length; i++)
        {
            if(words[i] == null || words[i].length() == 0 || !isValidWord(words[i]))
            {
                throw new IllegalArgumentException("Invalid words");
            }

            if (words[i].length() > largestWordLength)
            {
                largestWordLength = words[i].length();
            }
        }
        
        // Get all words with the same length, add them to largestWords string
        for (int i = 0; i < words.length; i++)
        {
            if (words[i].length() == largestWordLength && !isWordAlreadyAdded(alreadyAddedWords, words[i]))
            {
                // If there is a word in the array & not at the end of the phrase
                if (largestWords.length() > 0)
                {
                    largestWords = largestWords.concat(", ");
                }
                largestWords = largestWords.concat(words[i]);
                // Append to the array  of already added words
                alreadyAddedWords = Arrays.copyOf(alreadyAddedWords, alreadyAddedWords.length + 1);
                alreadyAddedWords[alreadyAddedWords.length - 1] = words[i];
            }
        }


        return largestWords;
    }

    /**
     * Returns the average length of the words in a given array
     * 
     * @param words The String array the user inputted
     * @return the average number of length of the words
     * @throw IllegalArgumentException if the words array is null
     * @throw IllegalArgumentException if the length of the words array is 0
     * @throw IllegalArgumentException if a word is null, length is 0, or character isn't a letter
     */
    public static double getAverageWordLength(String[] words)
    {
        if(words.equals(null))
        {
            throw new IllegalArgumentException("Null words");
        }

        if(words.length == 0)
        {
            throw new IllegalArgumentException("Zero length");
        }

        double wordCounter = 0.0;
        double charCounter = 0.0;

        // Counts the number of words in the array
        for(int i = 0; i < words.length; i++)
        {
            wordCounter++;
            for(int j = 0; j < words[i].length(); j++)
            {
                // Add 1 to charCounter if there's no punctuations
                if(words[i].charAt(j) != '.' && words[i].charAt(j) != ','
                && words[i].charAt(j) != ';' && words[i].charAt(j) != '!')
                {
                    charCounter++;
                }
            }
        }

        double avgLength = 0.0;
        avgLength = charCounter / wordCounter;
        return avgLength;
    }

    /**
     * Gets the total number of tallys for each letter used
     * for example, if the word is "Area" then two tallys for
     * A/a, 1 tally for R/r, and 1 tally for E/e is returned
     * in an array
     *
     * @param words A string array entered that gets tallied
     * @return an array with total number of tallies for each letter
     * @throw IllegalArgumentException if the words array is null
     * @throw IllegalArgumentException if the length of words array is 0
     * @throw IllegalArgumentException if a word is null, length is 0, or character isn't a letter
     */
    public static int[] getLetterTally(String[] words)
    {
        int[] letterTally = new int[26];
        for (int i = 0; i < words.length; i++){
            // Loop through the individual word
            for (int j = 0; j < words[i].length(); j++){
                // Convert capital / lower case letter into ASCII
                int tallyIndex = words[i].charAt(j);
                // Check to see if it's a lower case ASCII
                if (tallyIndex >= 97)
                {
                    tallyIndex -= 97;
                }
                else
                {
                    tallyIndex -= 65;
                }
                letterTally[tallyIndex] += 1;
            }
        }
        return letterTally;
    }

    /**
     * This determines if a given word is valid
     * true if valid, false if not
     * 
     * @param word The word in the array being checked if valid
     * @return true if valid, false if not
     */
    public static boolean isValidWord(String word)
    {
        char[] punctuations = {',', '.', '!', ';'};
        char[] numbers = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
        for(int i = 0; i < word.length(); i++)
        {
            // Check if letter at index 'i' is a number, return false if is
            for(int j = 0; j < numbers.length; j++)
            {
                if(word.charAt(i) == numbers[j])
                {
                    return false;
                }
            }

            // Check if letter at index 'i' is a punctuation
            for(int j = 0; j < punctuations.length; j++)
            {
                if(i != word.length() - 1)
                {
                    if(word.charAt(i) == punctuations[j])
                    {
                        // Check the letter phrase at 'i + 1' if it's a punctuation
                        for(int n = 0; n < punctuations.length; n++)
                        {
                            if(word.charAt(i + 1) == punctuations[n])
                            {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        
        return true;
    }

    /**
     * Runs getLetterTally and gets the least
     * frequently used letter(s)
     * 
     * @param words A string array entered that gets tallied
     * @return the least frequently used letter(s)
     */
    public static String getLeastFrequentLetters(String[] words)
    {
        words = getWords(words);
        int[] letterTally = getLetterTally(words);
        int leastTallyCount = letterTally[0];
        String allLetters = "";

        // Get the greatest value for leastTallyCount 
        for (int i = 0; i < letterTally.length; i++){
            if (letterTally[i] != 0 && letterTally[i] > leastTallyCount){
                leastTallyCount = letterTally[i];
            }
        }

        for (int i = 0; i < letterTally.length; i++){
            // int result = (letterTally[i] == leastTallyCount) ? 1 : 0;
            // System.out.println("in for:" + i + ", " + letterTally[i] + ", " + leastTallyCount + ": " + result);
            if (letterTally[i] < leastTallyCount && letterTally[i] != 0){
                allLetters = "";
                leastTallyCount = letterTally[i];
                allLetters += (char)(i + 97);
            }
            else if (letterTally[i] == leastTallyCount){
                // System.out.println("All letters check: " + allLetters);
                if (allLetters.length() > 0){
                    allLetters += ", ";
                }
                allLetters += (char)(i + 97);
                // System.out.println("All letters check: " + allLetters);
            }
        }
        return allLetters;
    }

    /**
     * Runs getLetterTally and gets the most
     * frequently used letter(s)
     * 
     * @param words A string array entered that gets tallied
     * @return the most frequently used letter(s)
     */
    public static String getMostFrequentLetters(String[] words)
    {
        words = getWords(words);
        int[] letterTally = getLetterTally(words);
        int mostTallyCount = 0;
        String allLetters = "";

        for (int i = 0; i < letterTally.length; i++){
            // int result = (letterTally[i] == mostTallyCount) ? 1 : 0;
            // System.out.println("in for:" + i + ", " + letterTally[i] + ", " + mostTallyCount + ": " + result);
            if (letterTally[i] > mostTallyCount){
                allLetters = "";
                mostTallyCount = letterTally[i];
                allLetters += (char)(i + 97);
            }
            else if (letterTally[i] == mostTallyCount){
                // System.out.println("All letters check: " + allLetters);
                if (allLetters.length() > 0){
                    allLetters += ", ";
                }
                allLetters += (char)(i + 97);
                // System.out.println("All letters check: " + allLetters);
            }
        }
        return allLetters;
    }

}
