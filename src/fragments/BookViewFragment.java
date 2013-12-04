package fragments;

import java.util.HashMap;
import java.util.List;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import book.Book;
import book.ExpandableListAdapter;

import com.example.book.R;
/*
 * 
 * 			bookview
 * 
 * 				shows contents of one book in a listview
 */
public class BookViewFragment extends Fragment implements OnItemClickListener, OnClickListener{
    
	private PagerActivity pager;	
	private View view;
    private Book book;
	
	private ExpandableListAdapter wordAdapter;
	private ExpandableListView list;	
	private TextView name;
	
	private ImageButton delete;
		
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
		
        this.pager = (PagerActivity) getActivity();        
        this.view = (View)inflater.inflate(R.layout.bookview, container, false);
        
        this.name = (TextView) this.view.findViewById(R.id.bookName);        
        this.list = (ExpandableListView)this.view.findViewById(R.id.wordList);
		
        		
		//this.list.setOnItemClickListener(this);

		
		ImageButton btn = (ImageButton) this.view.findViewById(R.id.add);
		btn.setOnClickListener(this);
		
		delete = (ImageButton) this.view.findViewById(R.id.delete);
		delete.setOnClickListener(this);
		
		
		// if this fragment already had a book open		
		if (this.pager.openBook != null){
			this.showBook(this.pager.openBook);
		}
        
        return this.view;
    }
    
	public void setName(String name){
		this.name.setText(name);		
	}
	
	
	// to show a book...
    public void showBook(Book book){
        this.clear();
    	this.book = book;
        this.setName(book.getName());
   	/*
    	List<Word> words = book.getWords();
    	
    	for (Word word : words){
    		this.wordAdapter.addWord(word);
    	}
    */  
       
        if (book.getCount() > 0){
	        this.wordAdapter = new ExpandableListAdapter(this.pager, book.getWords() );

	        if (this.wordAdapter != null && this.list != null){
	        	this.list.setAdapter(this.wordAdapter);
	        }
        }
    }

    public void clear(){
    	this.book = null;
    	this.wordAdapter = new ExpandableListAdapter(this.pager, new HashMap<String, List<String>>() );
    	this.list.setAdapter(this.wordAdapter);
    	this.setName("");    	
    }
    
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
	}

	@Override
	public void onClick(View v) { 

		switch (v.getId()){
			case R.id.add:
				/*
				this.pager.show(this.pager.CAMERA, false);
				this.pager.CAMERA.addTo(this.book);
				*/
				this.pager.addToBook(this.book);
				this.clear();
			break;
			
			case R.id.delete:				
				// x-button is clicked and the book will be deleted
				if (this.book != null){
					this.pager.removeBook(this.book);
					this.pager.show(pager.BOOKLIST,true);
					this.clear();
				}
			break;		
		}
	}
    
    
}
