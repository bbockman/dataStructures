package miscellaneous;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PrefixDictionary {
	private static final PrefixDictionary DICTIONARY = new PrefixDictionary();
	private TrieNode head = new TrieNode();
	private int size = 0;
	
	private PrefixDictionary() {
		generateDictionary();
	}
	
	public static PrefixDictionary getDictionary() {
		return DICTIONARY;
	}
	
	private void generateDictionary() {
		try (Scanner myReader = new Scanner(new File(System.getProperty("user.dir")+
				"/resources/no_short_10k_words.txt"))){
			while (myReader.hasNextLine()) {
				addWord(myReader.nextLine().toUpperCase().toCharArray());
			}
		} catch (FileNotFoundException e) {e.printStackTrace();}
	}
	
	private void addWord(char[] word) {
		TrieNode current = head;
		for (char c : word) {
			if (current.links[c-'A'] == null) {
				current.links[c-'A'] = new TrieNode();
			}
			current = current.links[c-'A'];
		}
		current.value = word;
		size++;
	}
	
	private class TrieNode {
		private char[] value = null;
		private TrieNode[] links = new TrieNode[26];
	}
	
	public boolean prefixWord(char[] prefix, int start, int end) {
		int crawlResult = checkWord(prefix,start,end);
		return (crawlResult == 0 || crawlResult == 1);
	}
	
	public boolean fullWord(char[] word, int start, int end) {
		int crawlResult = checkWord(word,start,end);
		return (crawlResult == 1);
	}
	
	public int checkWord(char[] word, int start, int end) {
		if (start > end) {System.out.println("Warning trie search word start > end");}
		TrieNode current = head;
		int i = start;
		while (current != null && i <= end) {
			current = current.links[Character.toUpperCase(word[i++])-'A'];
		}
		if (current == null) return -1;
		if (current.value == null) return 0;
		else return 1;
	}
	
	public int wordCount() {
		return size;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		traverseTrie(head, builder);
		return builder.toString();
	}
	
	private void traverseTrie(TrieNode current, StringBuilder builder) {
		if (current == null) {
			return;
		}
		if (current.value != null) {
			builder.append(new String(current.value));
			builder.append("\n");
		}
		for (int i = 0; i < current.links.length; i++) {
			traverseTrie(current.links[i], builder);
		}
	}
}
