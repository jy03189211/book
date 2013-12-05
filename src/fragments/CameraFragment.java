package fragments;

import test.interfacUI.camera.activate.CameraActivateButton;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import book.Book;

import com.example.book.R;

import edu.sfsu.cs.orange.ocr.CaptureActivity;

public class CameraFragment extends Fragment implements OnClickListener{
	
	private PagerActivity pager;
	private View view;	
	private EditText textfield;
	private Book addTo;
	private Button button1;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        		
        this.pager = (PagerActivity) getActivity();
        this.view = (View)inflater.inflate(R.layout.camera, container, false);
        this.textfield = (EditText) this.view.findViewById(R.id.addtext);
        
        ImageButton btn = (ImageButton) this.view.findViewById(R.id.ok);
        btn.setOnClickListener(this);
        
       
                        
        return this.view;
    }

	public void setText(String text){
		this.textfield.setText(text);
	}
		
	
	public void addTo(Book book){		
		this.addTo = book;
	}
	
	public void clear(){
		this.setText("");
		this.addTo = null;		
	}
	
	@Override
	public void onClick(View arg0) {		
		if (this.textfield.getText().toString().length() > 0){
			
			String[] text = this.textfield.getText().toString().split(" ");
	
			this.pager.hideKeyboard(this.textfield);
			
			Book bk = new Book(text[0]);
			for (String word : text){
				bk.addWord(word);
			}

			//pager.modifyBook(bk);
			pager.EDITBOOK.setBook(bk);
			pager.show(pager.EDITBOOK,true);
		}
	}
}
