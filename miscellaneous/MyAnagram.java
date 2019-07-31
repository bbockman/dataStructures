package miscellaneous;

import java.util.ArrayList;
import java.util.HashSet;

public class MyAnagram {
	private int PHRASE_LENGTH;
	private ArrayList<Character> phrase;
	private char[] current;
	private int anaCount = 0;
	int[] wordStarts;
	PrefixDictionary myDic;
	
	MyAnagram(String _phrase) {
		phrase = new ArrayList<>();
		for (char c : _phrase.toCharArray()) {
			if (c != ' ') this.phrase.add(c);
		}
		PHRASE_LENGTH = phrase.size();
		current = new char[PHRASE_LENGTH];
		wordStarts = new int[PHRASE_LENGTH];
		for (int i = 0; i < PHRASE_LENGTH; i++) {
			wordStarts[i] = -1;
		}
		myDic = PrefixDictionary.getDictionary();
	}
	
	public void generateAnagram() {
		permute(0);
	}
	
	private void permute(int k) {
		if (isDone(k)) {
			processDone();
			return;
		}
		HashSet<Character> repeats = new HashSet<>();
		for (int i = 0; i < phrase.size(); i++) {
			current[k] = phrase.get(i);
			if (!isAllowed(k) || repeats.contains(phrase.get(i))) continue;
			repeats.add(phrase.get(i));
			phrase.remove(i);
			permute(k+1);
			phrase.add(i,current[k]);
		}
	}
	
	private boolean isDone(int k) {
		return (k == PHRASE_LENGTH);
	}
	
	private void processDone(){
		ArrayList<Integer> startList = new ArrayList<>();
		if (wordStarts[wordStarts.length-1] != -1) {
			int n = wordStarts.length-1;
			while (n != -1) {
				startList.add(n);
				n = wordStarts[n];
				startList.add(n);
				n--;
			}
			for (int i = startList.size()-1; i > 0; i-=2) {
				for (int j = startList.get(i); j <= startList.get(i-1); j++){
					System.out.print(current[j]);
				}
				System.out.print(" ");
			}
			System.out.println();
			anaCount++;
		}
	}
	
	private boolean isAllowed(int k) {
		boolean isWord = false;
		boolean isPrefix = false;
		int trieResult = -1;
		for (int i = 0; i < k; i++) {
			if (wordStarts[i] != -1) {
				trieResult = myDic.checkWord(current, i+1, k);
				if (trieResult == 0) {isPrefix = true;}
				if (trieResult == 1) {isWord = true; wordStarts[k] = i+1;}
			}
		}
		boolean flag = true;
		if (isWord) {flag = false;}
		trieResult = myDic.checkWord(current, 0, k);
		if (trieResult == 0) {isPrefix = true;}
		if (trieResult == 1) {isWord = true;}
		if (isWord && flag) {wordStarts[k] = 0;}
		if(!isWord) {wordStarts[k] = -1;}
		
		return (isPrefix || isWord);
	}
	
	public int getCount() {
		return anaCount;
	}
	
	public static void main(String[] args) {
		MyAnagram ana = new MyAnagram("tuesday november");
		ana.generateAnagram();
		if (ana.getCount() == 0) {System.out.println("No anagrams found that contain all characters.");}
		System.out.println("Anagrams found " + ana.getCount());
	}

}