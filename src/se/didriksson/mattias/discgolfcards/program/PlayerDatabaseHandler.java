package se.didriksson.mattias.discgolfcards.program;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PlayerDatabaseHandler extends SQLiteOpenHelper{

	public final static String PLAYER_TABLE = "Players"; // name of table

	public final static String PLAYER_ID = "_id"; // id value for player
	public final static String PLAYER_NAME = "name"; // name of player
	
	private static final String DATABASE_NAME = "DGChallengeDB.db";
	private static final int DATABASE_VERSION = 2;

	public PlayerDatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void addPlayer(Player player) {
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(PLAYER_NAME, player.getName());

		database.insert(PLAYER_TABLE, null, values);
		database.close();

	}

	public Player getPlayer(int id) {
		SQLiteDatabase database = this.getReadableDatabase();

		Cursor cursor = database.query(PLAYER_TABLE, new String[] { PLAYER_ID,
				PLAYER_NAME }, PLAYER_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		return new Player(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1));
	}

	public List<Player> getAllPlayers() {
		
		SQLiteDatabase database = this.getReadableDatabase();
		List<Player> players = new ArrayList<Player>();

		String selectQuery = "SELECT * FROM " + PLAYER_TABLE;
		Cursor cursor = database.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				int id = Integer.parseInt(cursor.getString(0));
				String name = cursor.getString(1);
				players.add(new Player(id, name));

			} while (cursor.moveToNext());
		}

		return players;

	}
	
	
	public int getPlayerCount() {
		SQLiteDatabase database = this.getReadableDatabase();

		String selectQuery = "SELECT * FROM " + PLAYER_TABLE;
		Cursor cursor = database.rawQuery(selectQuery, null);
		cursor.close();
		
		return cursor.getCount();

	}
	
	public int updateContact(Player player){
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		values.put(PLAYER_NAME, player.getName());
		
		return database.update(PLAYER_TABLE, values, PLAYER_ID + " = ?", new String[] {String.valueOf(player.getID())});
		
	}

	public void deletePlayer(Player player){
		SQLiteDatabase database = this.getWritableDatabase();
		database.delete(PLAYER_TABLE, PLAYER_ID + " = ?", new String[] {String.valueOf(player.getID())});
		database.close();
	
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	      String CREATE_CONTACTS_TABLE = "CREATE TABLE " + PLAYER_TABLE + "("
	                + PLAYER_ID + " INTEGER PRIMARY KEY," + PLAYER_NAME + " TEXT" + ")";
	        db.execSQL(CREATE_CONTACTS_TABLE);		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + PLAYER_TABLE);
 
        // Create tables again
        onCreate(db);		
	}

}