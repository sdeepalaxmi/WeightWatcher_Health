package com.weightwatcher.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.Test;


public class DictionaryWordsTest {
  private static final String FILEPATH = "src/test/resources/file/Dictionary.txt";

  /**
   * verify the file exist or not
   * @param path
   * @return boolean
   * @throws FileNotFoundException
   */
  private boolean doesFileExist(String path) throws FileNotFoundException {
    if (path == null || path.length() == 0) {
      return false;
    }
    return (new File(path).exists());
  }

  /**
   * List the dictionary words with meaning using HashMap
   * @param path
   * @return HashMap
   * @throws NullPointerException
   * @throws IOException
   */
  private HashMap<String, List<String>> listDictionaryWords(String path) throws NullPointerException, IOException {
    if (path == null || path.length() == 0) {
      throw new NullPointerException("Path should have value");
    }

    BufferedReader reader = new BufferedReader(new FileReader(path));
    String line = null;
    HashMap<String, List<String>> dictionary = new LinkedHashMap<>();

    while ((line = reader.readLine()) != null) {
      String[] dictionaryWords = line.split("-");
      if (dictionaryWords.length != 2) {
        //Invalid data
        return null;
      }
      String dictionaryWord = dictionaryWords[0];
      String[] meaningWords = dictionaryWords[1].split(",");
      List<String> listOfMeanings = Arrays.asList(meaningWords);
      dictionary.put(dictionaryWord, listOfMeanings);
    }
    if (line == null && dictionary.isEmpty()) {
      throw new NullPointerException("Input file should have data");
    }

    return dictionary;
  }

  /*
   * Test the file is exist
   */
  @Test
  public void fileExistTest() throws FileNotFoundException {
    Assert.assertEquals(doesFileExist(FILEPATH), true, "File is not exist");
    Assert.assertEquals(doesFileExist(null), false, "File is not exist");
    Assert.assertEquals(doesFileExist("src/test/resources/file/Diction.txt"), false, "File is not exist");
  }

  /*
   * print the Dictionary word with meaning
   */
  @Test
  public void printDictionaryWordsTest() throws IOException, FileNotFoundException {
    //Check the File exist
    Assert.assertEquals(doesFileExist(FILEPATH), true, "File is not exist");

    //Print the Dictionary words with meaning
    HashMap<String, List<String>> dictionary = listDictionaryWords(FILEPATH);
    Assert.assertNotNull(dictionary, "Data shoule have values");
    for (Map.Entry<String, List<String>> entry : dictionary.entrySet()) {
      String key = entry.getKey();
      System.out.println(key);
      List<String> list = entry.getValue();
      for (String word : list) {
        System.out.println(word);
      }
    }
  }
}
