package se.didriksson.mattias.discgolfcards.activities;

import java.util.List;

import se.didriksson.mattias.discgolfcards.R;
import se.didriksson.mattias.discgolfcards.program.Course;
import se.didriksson.mattias.discgolfcards.program.Player;
import se.didriksson.mattias.discgolfcards.program.DatabaseHandler;
import se.didriksson.mattias.discgolfcards.program.Round;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainMenuActivity extends Activity {
	// Sets the number of players
	int numberOfPlayers = 8;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		DatabaseHandler db = new DatabaseHandler(this);
		Log.d("Insert: ", "Inserting players...");
		try{
			db.addCourse(new Course("Stora Vall"));	
			db.addCourse(new Course("Järva"));
			db.addCourse(new Course("Falun"));
			db.addCourse(new Course("Hissingen"));
		
			db.addPlayer(new Player("Mattias"));
			db.addPlayer(new Player("Anders"));
			db.addPlayer(new Player("Sven"));
			db.addPlayer(new Player("Ingvar"));
			db.addPlayer(new Player("Tomten"));

		}
		catch(SQLiteConstraintException sq){
			Log.d("Error inserting into table.", "");
		}
		
		Log.d("Reading: ", "Reading all players");
        
        List<Player> players = db.getAllPlayers();       
        
        for (Player cn : players) {
            String log = "Name: " + cn.getName();
                // Writing Contacts to log
      
            Log.d("Name: ", log);
		
	}
        
        
	Log.d("Reading: ", "Reading all Courses");
        
        List<Course> courses = db.getAllCourses();       
        
        for (Course cn : courses) {
            String log = "Name: " + cn.getName();
                // Writing Contacts to log
      
            Log.d("Course name: ", log);
		
	}
        
        
	Log.d("Reading: ", "Reading all Rounds");
        
        List<Round> rounds = db.getAllRounds();       
        
        for (Round cn : rounds) {
            String log = "Id: "+cn.getID()+" ,Bana: " + cn.getCourse().getName() + " ,Spelare: " + cn.getPlayer().getName() + " ,Resultat: " + cn.getScore();
                // Writing Contacts to log
      
            Log.d("Round name: ", log);
		
	}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	// Called when user clicks the scorecard button
	public void startScorecard(View view) {
		Intent intent = new Intent(this, ScorecardSubmenu.class);
		Bundle b = new Bundle();
		b.putBoolean("revengeGame", false);
		intent.putExtras(b);
		startActivity(intent);
		finish();
	}

	public void quitApplication(View view) {
		finish();

	}

	public void startRevengeGame(View view) {
		Intent intent = new Intent(this, ScorecardSubmenu.class);
		Bundle b = new Bundle();
		b.putBoolean("revengeGame", true);
		intent.putExtras(b);
		startActivity(intent);
		finish();
	}
}
