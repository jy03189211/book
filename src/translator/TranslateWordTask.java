package translator;

import android.os.AsyncTask;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

public class TranslateWordTask extends AsyncTask<Object, Object, Object> {
	
	private final String id = "jy03189211";
	private final String key = "metropoliasanapickup";
	
	public TranslateWordTask(){
		super();
	    Translate.setClientId(id);
	    Translate.setClientSecret(key);		
	}
	
	@Override
	protected Object doInBackground(Object... words) {
	    
		String word = (String) words[0];
				
		try {
			System.out.println("WORDTASK: .................................................."+word);
			String translatedText = Translate.execute(word, Language.ENGLISH, Language.FINNISH);
			System.out.println(translatedText);

			return translatedText;
			
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println(e.getCause());
			return null;
		}
		
	}
}
