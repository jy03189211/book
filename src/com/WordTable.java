package com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import book.Book;

public class WordTable{

	private SQLiteDatabase db;
	
	private static final String TABLE_NAME = "word";
	
	public WordTable(SQLiteDatabase db) {
	   this.db = db;
	   db.execSQL("create table IF NOT EXISTS "+TABLE_NAME+" (id integer primary key autoincrement, book_id integer, word char(255))");
	}

	public void clear(){
		db.execSQL("delete from "+TABLE_NAME +" if exists");
	}

	public void save(Book book){			
		HashMap<String, List<String>> words = book.getWords(); 
		
		for (String word : words.keySet() ){
			
			if (!this.hasWord(book.getId(), word)){
				ContentValues val = new ContentValues();
				val.put("book_id",book.getId());
				val.put("word",word);
				db.insert(TABLE_NAME,null, val);
			}
		}		
	}	
	
	public boolean hasWord(long id, String word){
		Cursor cursor = db.query(TABLE_NAME, new String[] {"word"}, "book_id="+id+" and word=\"" + word +"\"", null, null,null, null);
		if (cursor.getCount() > 0){
			return true;
		}
		return false;
	}
	
	public Book get(Book book){		
		book.addWords( this.get(book.getId()));
		return book;
	}
	
	public List<String> get(long id){
		List<String> results = new ArrayList<String>();
		
		Cursor cursor = db.query(TABLE_NAME, new String[] {"word"}, "book_id="+id, null, null,null, null);		
		cursor.moveToFirst();
		
		while (!cursor.isAfterLast()){			
			results.add( cursor.getString(0) );
			cursor.moveToNext();
		}
		return results;
	}		

}

