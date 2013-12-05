package fragments;

import java.util.ArrayList;
import java.util.List;

import translator.Translator;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import book.Book;

import com.Db;
import com.example.book.R;


/*
 * 
 * 			main activity
 * 
 * 
 */


public class PagerActivity extends FragmentActivity implements OnClickListener{
   
    private PagerAdapter pageAdapter;	// shit
    private List<Fragment> fragments;	// guess
    private ViewPager pager;	// handles the fragments
       
    public List<Book> BOOKS;	// books stored in this application
    public Db DB;
    
    public Book openBook; // current book, which is opened in bookFragment
    public Book editBook; // current book at the edit window
    
    public Translator translator;
    
   // fragments
    public CameraFragment CAMERA;
    public EditBookFragment EDITBOOK;
    public BookListFragment BOOKLIST;
    public BookViewFragment BOOKVIEW;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.viewpager);
        BOOKS = new ArrayList<Book>();
        this.initialisePaging();
    }
    
    // ridiculously handy keyboard goes to hell
    public void hideKeyboard(EditText field){
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(field.getWindowToken(), 0);    	
    }
    
    /*
     * 
     *  show a book in the bookFragment
     */
    public void showBook(Book book){    	    	
    	openBook = book;     	
    	BOOKVIEW.showBook(book);    	
    	this.show(this.BOOKVIEW,true);
    }

    
    public void addToBook(Book book){
    	editBook = book;
    	this.show(this.CAMERA, true);    	
    }
/*
    // edit books contents
    public void modifyBook(Book book){    	
    	editBook = book;
    	EDITBOOK.setBook(book);
    	this.show(this.EDITBOOK,true);
    }
  */     
    public Book getBook(String name){
    	for (Book bk : this.BOOKS){
    		if (bk.getName().equals(name)){
    			return bk;
    		}
    	}
    	return null;
    }
    
    // check if book already exists
    public boolean hasBook(Book book){    	
		for (Book bk : BOOKS){
			if (bk.getName().equals(book.getName())){
				return true;
			}
		}
    	return BOOKS.contains(book);
    }
    
    public Book createBook(){
    	Book bk = new Book(this.DB);
    	return bk;
    }
    
    // method to add book into array
    public void addBook(Book book){
    	if (this.editBook != null){
    		this.editBook.merge(book);
    		if (!BOOKS.contains(this.editBook)){
    			BOOKS.add(this.editBook);
    		}
    	} else if (!this.hasBook(book)){
    		BOOKS.add(book);    		   	
    	}
    
		this.editBook = null;
    	translator.translate(book);    	
    	BOOKLIST.refresh();
    }
    
    public void removeBook(Book book){
    	book.remove();
    	BOOKS.remove(book);
    	BOOKLIST.refresh();
    	openBook = null;
    }
   
    public void saveBook(Book book){
    	book.save();
    	BOOKLIST.refresh();
    }
    
      // show some fragments
    public void show(Fragment frag, Boolean animate){
        pager.setCurrentItem(this.fragments.indexOf(frag), animate);    	
    }
    
    // main init
    private void initialisePaging() {
    	fragments = new ArrayList<Fragment>();
    	
    	DB = new Db(this);
    	BOOKS = DB.load();
    	
    	translator = new Translator(DB);
    	
    	//CAMERA = new CameraFragment();
    	//EDITBOOK = new EditBookFragment();
    	BOOKLIST = new BookListFragment();
    	BOOKVIEW = new BookViewFragment();
    	
    	
    	
    	//fragments.add(CAMERA);
    	//fragments.add(EDITBOOK);
    	fragments.add(BOOKLIST);
    	fragments.add(BOOKVIEW);
    	    	
    	// setup view pager
    	pageAdapter  = new PagerAdapter(super.getSupportFragmentManager(), this.fragments);
        pager = (ViewPager)super.findViewById(R.id.viewpager);         
        pager.setAdapter(pageAdapter);
               
        
        // first pane to be shown at startup
        show(this.BOOKLIST,false);
        
    }


    
    // button onclick
	@Override
	public void onClick(View button) {
		
		
	}
}

