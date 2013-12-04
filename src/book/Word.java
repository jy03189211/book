package book;

import java.util.ArrayList;
import java.util.List;


/*
 * 
 * 	fokkin stupid word class
 * 	made it, because needed to store the translations somewhere....
 * 	(yo dawg; i heard you liked objects, so i put objects in your objects so you can object while you object)
 * 
 */
public class Word {
	
	private long id;
	private String word;
	private List<String> translations;
	
	
	public Word(String word){
		this.setWord(word);
		this.translations = new ArrayList<String>();
	}

	public Word(long id, String word){
		this.setId(id);
		this.setWord(word);
		this.translations = new ArrayList<String>();
	}

	
	public Word(String word, List<String>translations){
		this.setWord(word);
		this.addTranslations(translations);
	}
	
	public void addTranslations(List<String> translations){
		for (String word : translations){
			this.addTranslation(word);
		}
	}
	
	public void addTranslation(String translation){
		this.translations.add(translation);
	}
	
	public List<String> getTranslations(){
		return this.translations;
	}
	
	public String getTranslationsText(){
		if (this.translations.size() > 0){
			//return Arrays.asList( this.translations.toArray() ).toString().replaceAll("(^\\[|\\]$)", "").replace("\n ", "\n");
			String text = "";
			
			for (String t : this.translations){
				text += t + "\n";
			}
			
			return text;
		} else {
			return "no translations";
		}
				
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
