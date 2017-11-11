package com.weightwatcher.test;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class ReverseWordsTest {
  /**
   * Reverse all the letters
   * @param sentence
   * @return String
   */
  private String reverseEverything(String sentence) {
    //validate the sentence
    if (sentence == null || sentence.length() == 0 || sentence.trim().length() == 0) {
      return null;
    }
    StringBuffer reverseSentence = new StringBuffer();
    //Reverse the sentence
    for (int idx = sentence.length() - 1; idx >= 0; idx--) {
      reverseSentence.append(sentence.charAt(idx));
    }
    return reverseSentence.toString();
  }

  //Test Data
  @DataProvider(name = "ReverseTestData")
  public Object[][] createReverseTestData() {
    return new String[][]{{"hope you are doing well", "llew gniod era uoy epoh"},
        {"hopeyouaredoingwell", "llewgnioderauoyepoh"},
        {"", null}, {null, null},
        {"hope &you are_ doing $%^ well^^", "^^llew ^%$ gniod _era uoy& epoh"},
        {"\u089A Hope you are doing well \u089A", "\u089A llew gniod era uoy epoH \u089A"},
        {"hope 1234 are 1234 567", "765 4321 era 4321 epoh"}};
  }
  /*
   * test reverse a string
   */
  @Test(dataProvider = "ReverseTestData")
  public void reveseStringTest(String inputData, String expectedData) {
    Assert.assertEquals(reverseEverything(inputData), expectedData,"Invalid data");
  }

  /**
   * Another way to do reverse a sentence
   * Reverse a sentence with reversed words
   * @param sentence
   * @return String
   */
  private String reverseWords(String sentence) {
    //validate the sentence
    if (sentence == null || sentence.length() == 0 || sentence.trim().length() == 0) {
      return null;
    }
    // split the words with space
    String[] arrWords = sentence.split("\\s");
    StringBuilder result = new StringBuilder();
    for (int idx = arrWords.length - 1; idx >= 0; idx--) {
      //reverse word
      result.append(reverseWord(arrWords[idx]));
    }
    return result.toString().substring(0, result.length() - 1);
  }

  /**
   * Reverse a word using XOR
   * @param word
   * @return String
   */
  private String reverseWord(String word) {
    char[] arrCH = word.toCharArray();
    int left = 0;
    int right = arrCH.length - 1;
    while (left < right) {
      arrCH[left] = (char) (arrCH[left] ^ arrCH[right]);
      arrCH[right] = (char) (arrCH[left] ^ arrCH[right]);
      arrCH[left] = (char) (arrCH[left] ^ arrCH[right]);
      left++;
      right--;
    }
    String reverseStr = String.valueOf(arrCH) + " ";
    return reverseStr;
  }

}
