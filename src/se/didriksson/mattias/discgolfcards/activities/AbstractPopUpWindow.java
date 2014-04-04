package se.didriksson.mattias.discgolfcards.activities;

import se.didriksson.mattias.discgolfcards.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public abstract class AbstractPopUpWindow extends Activity {

	TextView popupTextView;
	EditText popupInput;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pop_up_layout);
		popupTextView = (TextView)findViewById(R.id.popupEditName);
		popupInput = (EditText)findViewById(R.id.popupInput);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_course, menu);
		return true;
	}

	public abstract void saveAndExit(View view);
	

}
