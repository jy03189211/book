package book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.example.book.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class SelectableWordListAdapter extends ArrayAdapter<String>{
	
	private List<String> words;
	private final Context context;
			
	public SelectableWordListAdapter(Context context) {
		super(context, R.layout.booklistitem);
		this.context = context;
		this.words = new ArrayList<String>();
	}
	
	public void set(HashMap<String, List<String>> book){
		for (String word : book.keySet()){
			words.add(word);
			this.add(word);
		}
	}
		
	  @Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    LayoutInflater inflater = (LayoutInflater) context
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.selectablewordlistitem, parent, false);
	    
	    CheckBox item = (CheckBox) rowView.findViewById(R.id.select);
	    	 
	    	
	    
	    item.setText(this.words.get(position));
	    //TextView word = (TextView) rowView.findViewById(R.id.word);	   
        //word.setText(this.words.get(position));	    
	    rowView.setTag(this.words.get(position));
	    
	    return rowView;
	}
}
