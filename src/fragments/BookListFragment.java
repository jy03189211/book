package fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import book.Book;
import book.BookListAdapter;

import com.example.book.R;

public class BookListFragment extends Fragment implements OnItemClickListener, OnClickListener {
	
	private PagerActivity pager;
	private ListView bookList;
	private View view;
	private BookListAdapter bookAdapter;
	
                
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		if (container == null) {
            return null;
        }
		
		this.pager = (PagerActivity) getActivity();		
        this.view = (View)inflater.inflate(R.layout.booklist, container, false);
        
        
		this.bookList = (ListView)this.view.findViewById(R.id.bookList);		
		this.bookList.setOnItemClickListener(this);
		
		ImageButton btn = (ImageButton) this.view.findViewById(R.id.add);
		btn.setOnClickListener(this);
		this.refresh();
		
        return this.view;
    }
	
	
	public void refresh(){		
		if (this.pager != null){
			this.bookAdapter = new BookListAdapter(this.pager, this.pager.BOOKS);
			this.bookList.setAdapter(this.bookAdapter);
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View item, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Book book = (Book) item.getTag();
		this.pager.showBook( book );		
	}

	@Override
	public void onClick(View arg0) {
		
		//this.pager.show(this.pager.CAMERA, true);		
	}
}
