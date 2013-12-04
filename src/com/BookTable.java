package com;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import book.Book;

public class BookTable{

	private SQLiteDatabase db;
	
	private static final String TABLE_NAME = "book";
	
	public BookTable(SQLiteDatabase db) {
		this.db = db;
		db.execSQL("create table if not exists "+TABLE_NAME+" (id integer primary key autoincrement, name char(255))");
	}
	
	public boolean has(Book book){
		return this.has(book.getName());
	}
	
	public void clear(){
		db.execSQL("delete from "+TABLE_NAME +" if exists");
	}
		
	public boolean has(String name){
		Cursor cursor = db.query(TABLE_NAME, new String[] {"name"}, "name="+name, null, null,null, null);
		
		if (cursor.getCount() > 0){
			return true;
		} else {
			return false;	
		}
	}
	
	public void remove(Book book){
		db.execSQL("delete from "+TABLE_NAME+" where id="+book.getId());
	}
	

	public Book getBook(String id){
		Cursor cursor = db.rawQuery("select id,name from "+TABLE_NAME+" where id ='"+id+"'",null);
		cursor.moveToFirst();
		Book bk = new Book(cursor.getLong(0), cursor.getString(1));
		return bk;
	}

	public long save(Book book){		
		ContentValues val = new ContentValues();
		val.put("name",book.getName());
			
		if (book.getId() > 0){
			db.update("book", val, "id="+book.getId(), null);
			return book.getId();
		} else {
			long insertId = db.insert(TABLE_NAME,null, val);
			book.setId(insertId);
			return insertId;
		}		
	}	
	
	public List<Book> get(){
		List<Book> books = new ArrayList<Book>();
		Cursor cursor = db.rawQuery("select id,name from "+TABLE_NAME,null);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()){
			books.add( new Book(cursor.getLong(0), cursor.getString(1) ) );
			
			cursor.moveToNext();			
		}		
		return books;
	}	
}
