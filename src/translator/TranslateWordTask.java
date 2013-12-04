package translator;

import android.os.AsyncTask;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

public class TranslateWordTask extends AsyncTask<Object, Object, Object> {
	
	private final String id = "7289e55c-9818-4165-b7ec-9acd27beedf0";
	private final String key = "7lg8F/NaSZ1vLFDqmGiBI/AnVX8MwEt+X666lqNM3i0=";
	
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
