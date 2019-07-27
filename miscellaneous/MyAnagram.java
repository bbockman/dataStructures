package miscellaneous;

import java.util.ArrayList;

public class MyAnagram {
	private int PHRASE_LENGTH;
	private ArrayList<Character> phrase;
	private char[] current;
	private int anaCount = 0;
	//private ArrayList<Integer> wordStops = new ArrayList<>();
	int[] wordStops;
	int[] wordStarts;
	PrefixDictionary myDic;
	
	MyAnagram(String _phrase) {
		phrase = new ArrayList<>();
		for (char c : _phrase.toCharArray()) {
			if (c != ' ') this.phrase.add(c);
		}
		PHRASE_LENGTH = phrase.size();
		current = new char[PHRASE_LENGTH];
		wordStops = new int[PHRASE_LENGTH];
		wordStarts = new int[PHRASE_LENGTH];
		for (int i = 0; i < PHRASE_LENGTH; i++) {
			wordStops[i] = -1;
			wordStarts[i] = -1;
		}
		myDic = PrefixDictionary.getDictionary();
		//System.out.println(myDic.toString());
	}
	
	public void generateAnagram() {
		permute(0);
	}
	
	private void permute(int k) {
		//System.out.println("k "+k);
		if (isDone(k)) {
			processDone();
			return;
		}
		for (int i = 0; i < phrase.size(); i++) {//char c : phrase) {
			current[k] = phrase.get(i);
			if (!isAllowed(k)) continue;
			phrase.remove(i);
			permute(k+1);
			phrase.add(i,current[k]);
		}
	}
	
	private boolean isDone(int k) {
		return (k == PHRASE_LENGTH);
	}
	
	private void processDone(){
		if (wordStops[wordStops.length-1] != -1) {
			for(char c : current) {
				//System.out.print(c);
			}
			//System.out.println();
			anaCount++;
		}
	}
	
	private boolean isAllowed(int k) {
		boolean isWord = false;
		boolean isPrefix = false;
		int trieResult = -1;
		for (int i = 0; i < k; i++) {
			if (wordStops[i] != -1) {
				trieResult = myDic.checkWord(current, i+1, k);
			}
			if (trieResult == 0) {isPrefix = true;}
			if (trieResult == 1) {isWord = true; wordStarts[k] = i;}
		}
		//System.out.println("current[0] "+current[0]);
		trieResult = myDic.checkWord(current, 0, k);
		if (trieResult == 0) {isPrefix = true;}
		if (trieResult == 1) {isWord = true;}
		if (isWord) {wordStops[k] = k;}
		else {wordStops[k] = -1;}
		
		return (isPrefix || isWord);
	}
	
	public int getCount() {
		return anaCount;
	}
	
	public static void main(String[] args) {
		MyAnagram ana = new MyAnagram("tuesday november third");
		ana.generateAnagram();
		if (ana.getCount() == 0) {System.out.println("No anagrams found that contain all characters.");}
		System.out.println("Anagrams found " + ana.getCount());
	}

}