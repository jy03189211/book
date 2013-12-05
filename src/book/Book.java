package book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.Db;

public class Book {
	private long id; // database id of the book
	private String name;
	private HashMap<String, List<String>> words;
	private Db db;
	
	public Book(Db db){
		this.db = db;
		this.words = new HashMap<String, List<String>>();	
	}
	
	public void save(){
		this.db.save(this);
	}
	
	public void load(){
	}
	
	public void remove(){
		this.db.remove(this);
	}
	
	
	
	public void setString(String text){
		this.words = new HashMap<String, List<String>>();
		String[] txt = text.split(" ");
		
		for (String t : txt){
			this.addWord(t);
		}
	}
	
	public void Clear(){
		this.words = new HashMap<String, List<String>>();
	}
	
	public Book(String name){
		this.setName(name);
		this.words = new HashMap<String, List<String>>();
	}
	
	public Book(long id, String name){
		this.setId(id);
		this.setName(name);
		this.words = new HashMap<String, List<String>>();
	}

	// overloading addword method
	public void addWord(String word, List<String> translations){				
		if (!this.hasWord(word)){
			words.put(word, translations);
		}
	}
	
	// check if this already has the word.. regardless of translations..
	public boolean hasWord(String word){
		return this.words.containsKey(word);
	}

	public List<String> getWordsAsList(){
		List<String> wlist = new ArrayList<String>();
		
		for (String word : words.keySet()){
			wlist.add(word);
		}
		
		return wlist;		
	}
	
	public void addWord(String word){		
		if (!this.words.containsKey(word)){
			this.words.put(word, new ArrayList<String>());
		}		
	}
	
	public void merge(Book book){
		HashMap<String ,List<String>> words = book.getWords();
		for (String word : words.keySet()){
			this.addWord(word, words.get(word));
		}
	}
	
	public void addWords(List<String> words){
		for (String word : words ){
			this.addWord(word);
		}		
	}
	
	public void removeWord(String word){
		if (this.hasWord(word)){
			words.remove(word);
		}		
	}

	public void addTranslations(String word, List<String> translations){
		for (String translation : translations){
			this.addTranslation(word, translation);
		}
	}
	
	public void addTranslation(String word, String translation){
		this.addWord(word);
		this.words.get(word).add(translation);
	}
	
	public void setWords(HashMap<String, List<String>> words){
		this.words = words;
	}
	
	public HashMap<String, List<String>> getWords(){
		return words;
	}
	
	// count em
	public int getCount(){		
		return this.words.size();
	}
	

	// captain obvious
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
