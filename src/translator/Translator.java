package translator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.os.AsyncTask;
import book.Book;

import com.Db;


public class Translator{
	
	private Db db;

	public Translator(Db db){	
		this.db = db;
	}
	
	public Book translate(Book book){
		System.out.println("TRANSLATE BOOK: .................................................."+book.getName());
		HashMap<String , List<String>> words = book.getWords();
		
		for (String word : words.keySet()){
			book.addTranslations(word, this.translate(word));	
		}
		return book;
	}
	
	public List<String> translate(String word){
		System.out.println("TRANSLATE: .................................................."+word);
		if (this.db.hasTranslations(word)){
			System.out.println("db fetch: "+word);
			return this.db.getTranslations(word);
		} else {
			
			/*
			AsyncTask translation = new TranslateWordTask().execute(word);
			try {
				String translated = (String) translation.get();
				List<String> result = new ArrayList<String>();
				result.add( translated );
				return result;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			
			List<String> fgds = new ArrayList<String>();
			fgds.add("kala");
			fgds.add("siika");
			fgds.add("2kg");
			return fgds; 
		}				
	}
}
