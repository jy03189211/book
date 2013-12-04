package fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import book.Book;
import book.SelectableWordListAdapter;

import com.example.book.R;

public class EditBookFragment extends Fragment implements OnClickListener{
    
	private Book book;	
	private PagerActivity pager;	
	private View view;
    
	private SelectableWordListAdapter wordAdapter;
	private ListView list;
	private EditText textfield;
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
		
        this.pager = (PagerActivity) getActivity();
        this.view = (View)inflater.inflate(R.layout.editbook, container, false);
        
        this.textfield = (EditText) this.view.findViewById(R.id.bookName);        
        this.list = (ListView)this.view.findViewById(R.id.wordList);
        //this.list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE); // multiselect listview        		        

        
        if (pager.editBook != null){
        	this.setBook(pager.editBook);
        	
        }
        
		// shitbutton for shit
        LinearLayout toolbar = (LinearLayout) this.view.findViewById(R.id.toolbar);        
        
        for (int i = 0; i < toolbar.getChildCount(); i++){
        	toolbar.getChildAt(i).setOnClickListener(this);
        } 		
		
		
        return this.view;
    }
    
	
	// does not fucking work
	public void setName(String name){
		if (name != null){
			this.textfield.setText(name);	
		}				
	}
	
	public String getName(){
		return this.textfield.getText().toString();
	}
	
	// set the book...
	public void setBook(Book book){		
		this.clear();
		this.book = book;
		this.setName( book.getName() );
		
		this.wordAdapter.set(this.book.getWords());				
		this.selectAll(true);
	}
	
	
	// selects all list items
	public void selectAll(Boolean check){
		for (int index  = 0; index < this.list.getChildCount(); index++){
			View listItem = this.list.getChildAt(index);
			CheckBox item = (CheckBox) listItem.findViewById(R.id.select);
			item.setChecked(check);
		}
	}
	
	// select none
	public void clearSelected(){
		this.selectAll(false);
	}
	
	public void clear(){
		this.book = null;
		this.setName("");
		
		this.wordAdapter = new SelectableWordListAdapter(this.pager);
		this.list.setAdapter(this.wordAdapter);		
	}
	
	// get selected items from list
	public List<String> getSelected(){
	    List<String> words = new ArrayList<String>();
	    
		for (int index  = 0; index < this.list.getChildCount(); index++){
			View listItem = this.list.getChildAt(index);
			CheckBox item = (CheckBox) listItem.findViewById(R.id.select);
			
			if (item.isChecked() == true){
				words.add( (String) listItem.getTag() );
			}
		}
	    return words;
	    
	}
	
	// return the data
	@Override
	public void onClick(View v) {
		switch ( v.getId() ){
			case R.id.ok:
				Book bk =  new Book();
				bk.setName(this.getName());
				bk.addWords(this.getSelected());
				
				System.out.println(bk.getCount() );
				
				pager.addBook(bk);
				

				this.pager.hideKeyboard(this.textfield);
				this.clear();
				pager.show(pager.BOOKLIST,true);
				
				
			break;
			case R.id.deselect:
				this.clearSelected();
			break;
			case R.id.select:
				this.selectAll(true);
			break;
		}
	}



    
    
}
