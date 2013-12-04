package com;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import book.Book;

public class TranslationsTable{

	private SQLiteDatabase db;
	
	private static final String TABLE_NAME = "translation";
	
	public TranslationsTable(SQLiteDatabase db) {
		this.db = db;
		db.execSQL("create table IF NOT EXISTS "+TABLE_NAME+" (id integer primary key autoincrement,word char(255), translation char(255))");
	}

	public void clear(){
		db.execSQL("delete from "+TABLE_NAME +" if exists");
	}
	
	public Book get(Book book){
		book.setWords( this.get( book.getWordsAsList() ) );
		return book;
	}
	
	public HashMap<String, List<String>> get(List<String> words){
		HashMap<String, List<String>> result = new HashMap<String, List<String>>(); 

		for (String word : words){
			
			result.put(word, this.getTranslation(word));
		}
		
		return result;
	}

	public List<String> getTranslation(String word){		
		List<String> translations = new ArrayList<String>();
		
		Cursor cursor = db.query(TABLE_NAME, new String[] {"translation"}, "word=\""+word+"\"", null, null,null, null);		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()){			
			translations.add(cursor.getString(0));		
			cursor.moveToNext();
		}
		return translations;				
	}

	public boolean hasWord(String word){
		//Cursor cursor = db.query(TABLE_NAME, new String[] {"word"}, "word=\""+word+"\"", null, null,null, null);
		Cursor cursor = db.rawQuery("select word from "+TABLE_NAME+" where word=\""+word+"\"", null);
		
		if (cursor.getCount() > 0){
			System.out.println("juu on");
			return true;
		} else {
			return false;	
		}
	}
	

	public boolean hasTranslation(String word, String translation){
		Cursor cursor = db.rawQuery("select * from "+TABLE_NAME+" where word=\""+word+"\" and translation=\""+translation+"\" ", null);		
		
		if (cursor.getCount() > 0){
			return true;
		} else {
			return false;	
		}		
	}

	public void save(Book book){
		HashMap<String, List<String>> words = book.getWords();
		
		for (String word : words.keySet()){
			this.save(word, words.get(word));
		}
	}
	
	public void save(String word, List<String> translations){	
		System.out.println("SAVE ..............."+word);
		
		if (translations.size() > 0){				
			for (String translation : translations){				
				System.out.println("SAVE ...............TRANSLATION: "+translation);
				if (!this.hasTranslation(word, translation)){
								
					ContentValues val = new ContentValues();
					val.put("word",word);
					val.put("translation",translation);
					db.insert(TABLE_NAME,null, val);
				}					
			}
		}
	}
		
}
