package book;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.book.R;


/*
 * 
 *		adapter for listing the books 
 * 
 */

public class BookListAdapter extends ArrayAdapter<Book>{
	private List<Book> books;
	private Context context;
	
			
	public BookListAdapter(Context context, List<Book> books) {
		super(context, R.layout.booklistitem);
		this.context = context;
		this.books = books;
		
		if (books.size() > 0){
			for (Book bk : books){
				this.add(bk);
			}
		}
	}

	  @Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    LayoutInflater inflater = (LayoutInflater) context
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.booklistitem, parent, false);
	    
	    TextView bookname = (TextView) rowView.findViewById(R.id.bookname);	   
		TextView wordcount = (TextView) rowView.findViewById(R.id.wordcount);
	    
	    rowView.setTag(this.books.get(position));	    
	    bookname.setText(this.books.get(position).getName());
	    wordcount.setText(new Integer( this.books.get(position).getCount() ).toString());
	    
	    return rowView;
	}
}
