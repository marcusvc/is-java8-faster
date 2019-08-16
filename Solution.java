package faire.test.marcus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

/*
 * To execute Java, please define "static void main" on a class
 * named Solution.
 *
 * If you need more classes, simply define them inline.
 */

 class Solution {

   public static void main(String[] args) {
     String text = "But to see man outpace machine, look no further than Twitter, where a style marked by little punctuation and no capitalization is almost a native language. We have mostly ourselves to thank for this grammatical twist, says Gretchen McCulloch. A linguist who studies online behavior, McCulloch occasionally contributes to The Toast, the wry, writerly blog whose co-founder Mallory Ortberg is something of a pioneer of the low-punctuation, no-caps style.";
     
     String[] wordArray = text.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
     List<String> words = new ArrayList<>(Arrays.asList(wordArray));
     
     long[][] times = new long[5][1000];
     for (int i=0; i < times.length; i++) {
    	 for (int j=0; j < times[i].length; j++) {
    		 measureTime(words, times, i, j);
    	 }
     }
     
     System.out.println("times: " + Arrays.deepToString(times));
     
     long[] average = new long[5];
     long sum = 0;
     for (int i=0; i < times.length; i++) {
    	 for (int j=0; j < times[i].length; j++) {
    		 sum += times[i][j];
    	 }
    	 average[i] = sum / (long) times[i].length;
    	 sum = 0;
     }
     
     System.out.println("average: " + Arrays.toString(average));

     System.out.println(mostCommonWord0(words));
     System.out.println(mostCommonWord1(words));
     System.out.println(mostCommonWord2(words));
     System.out.println(mostCommonWord3(words));
     System.out.println(mostCommonWord4(words));
     
   }

   private static void measureTime(List<String> words, long[][] times, int i, int j) {
	   long start;
	   long finish;
	   switch (i) {
	   case 0:
		   start = System.nanoTime();
		   mostCommonWord0(words);
		   finish = System.nanoTime();
		   times[i][j] = finish - start;
		   break;
	   case 1:
		   start = System.nanoTime();
		   mostCommonWord1(words);
		   finish = System.nanoTime();
		   times[i][j] = finish - start;
		   break;
	   case 2:
		   start = System.nanoTime();
		   mostCommonWord2(words);
		   finish = System.nanoTime();
		   times[i][j] = finish - start;
		   break;
	   case 3:
		   start = System.nanoTime();
		   mostCommonWord3(words);
		   finish = System.nanoTime();
		   times[i][j] = finish - start;
		   break;
	   case 4:
		   start = System.nanoTime();
		   mostCommonWord4(words);
		   finish = System.nanoTime();
		   times[i][j] = finish - start;
		   break;

	   default:
		   throw new IllegalArgumentException("Unexpected value: " + i);
	   }
   }
   
   //Non Java 8 solution / 2 loops
   public static String mostCommonWord0(List<String> words) {
	   Map<String, Integer> stringsCounter = new HashMap<>();
	   for (String word : words) {
		   if (!stringsCounter.containsKey(word) ) {
			   stringsCounter.put(word, 1);
		   } else {
			   stringsCounter.put(word, stringsCounter.get(word)+1);
		   }
	   }
	   int maxValue = 0;
	   String result = null;
	   for (Map.Entry<String, Integer> entry : stringsCounter.entrySet()) {
		   if (entry.getValue() > maxValue) {
			   maxValue = entry.getValue();
			   result = entry.getKey();
		   }
	   }
	   return result;
	}
   
   //Non Java 8 solution / 1 loop
   public static String mostCommonWord1(List<String> words) {
	   String result = null;
	   int maxValue = 0;
	   Map<String, Integer> stringsCounter = new HashMap<>();
	   for (String word : words) {
		   if (!stringsCounter.containsKey(word) ) {
			   stringsCounter.put(word, 1);
		   } else {
			   stringsCounter.put(word, stringsCounter.get(word)+1);
		   }
		   if (stringsCounter.get(word) > maxValue) {
			   maxValue = stringsCounter.get(word);
			   result = word;
		   }
	   }
	   return result;
	}
   
   	//Entry level Java 8 solution
   public static String mostCommonWord2(List<String> words) {
	   Map<String, Integer> stringsCounter = new HashMap<>();
	   words.stream().forEach(word -> stringsCounter.merge(word, 1, Integer::sum));
	   int maxValue = 0;
	   String result = null;
	   for (Map.Entry<String, Integer> entry : stringsCounter.entrySet()) {
		   if (entry.getValue() > maxValue) {
			   maxValue = entry.getValue();
			   result = entry.getKey();
		   }
	   }
	   return result;
   }

   //Intermediate level Java 8 solution
   public static String mostCommonWord3(List<String> words) {
	   Map<String, Integer> stringsCounter = new HashMap<>();
	   words.stream().forEach(word -> stringsCounter.merge(word, 1, Integer::sum));
	   Optional<Entry<String, Integer>> maxEntry = stringsCounter.entrySet().stream().max(Entry.comparingByValue());
	   return maxEntry.get().getKey();
   }
   
   //Advanced level Java 8 solution
   public static String mostCommonWord4(List<String> words) {
	   return words.stream()
			   .collect(Collectors.toMap(word -> word, word -> 1, Integer::sum))
			   .entrySet().stream()
			   .max(Entry.comparingByValue())
			   .get().getKey();
   }
  
 }
