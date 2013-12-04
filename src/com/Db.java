package com;

import java.util.List;

import fragments.PagerActivity;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import book.Book;

/*
 * 
 * 
 * 			craopy db system for the app
 * 
 * 
 * 	book:
 * 		id : int
 * 		name : string
 * 
 * 
 * this could be normalized more, so each word can be in multiple books and vice versa
 * 	word
 * 		id	: int
 * 		book_id : int 
 * 		word : string
 * 
 * 	translations
 * 		id : int
 * 		word : string
 * 		translation : string
 * 
 */

public class Db extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "learnfin.db";
	private static final int DATABASE_VERSION = 1;
	
	// it was said that it is good practice to create own class for each of the tables...
	// so at least one good practice here
	private BookTable books;
	private WordTable words;
	private PagerActivity pager;
	private TranslationsTable translations;
	
	private SQLiteDatabase db;
	
	public Db(Context context) {
	   super(context, DATABASE_NAME, null, DATABASE_VERSION);
	   
	   db = this.getWritableDatabase();	   
	   pager = (PagerActivity) context;
	   books = new BookTable(db);
	   words = new WordTable(db);
	   translations = new TranslationsTable(db);
	}
	
	public Book getBook(long id){
		Book bk = books.getBook( String.valueOf(id) );
		words.get(bk);
		translations.get(bk);
		
		return bk;
	}
	
	public boolean hasTranslations(String word){
		return this.translations.hasWord(word);
	}
	
	public List<String> getTranslations(String word){
		return this.translations.getTranslation(word);
	}
	
	/*
	 * 
	 * 
	 * 	get contents for one book
	 */
	
	public Book getContent(Book bk){
		words.get(bk);
		translations.get(bk);
		return bk;
	}

	
	/*
	 *  loading all the books at once because
	 * 	i dont give a shit
	 */
	public List<Book> load(){		
		List<Book> bks = this.books.get();
		
		if (bks.size() > 0 && bks != null){				
			
			for (Book book : bks){
				words.get(book);
				translations.get(book);
			}
			
		}		
		return bks;
	}
	
	public void remove(Book book){
		books.remove(book);
	}
	
	// save / update the books
	public void save(Book book){
		books.save(book);
		words.save(book);
		translations.save(book);
	}
	
	
	// kill all
	public void clear(){
		books.clear();
		words.clear();
		translations.clear();
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {		

	}	

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	  onCreate(db);
	}

}
