package se.bloomed.discgolfcards.program;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	// http://www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/

	Context myOwnContext;

	private static final String DATABASE_NAME = "DGChallengeDB.db";

	private static final int DATABASE_VERSION = 50;

	// Table names
	private final static String PLAYER_TABLE = "Players"; // name of table
	private final static String COURSE_TABLE = "Courses"; // name of table
	private final static String ROUNDS_TABLE = "Rounds"; // name of table
	private final static String CARD_TABLE = "Cards"; // name of table


	private final static String COURSE_ID = "CourseId"; // name of course
	private final static String COURSE_NAME = "CourseName"; // name of course
	private final static String COURSE_PARS = "CoursePars";

	private final static String CARD_NAME = "CardName"; // name of course
	private final static String CARD_DESC = "CardDescription";
	private final static String CARD_ID = "CardID";

	private final static String ROUNDS_ID = "_id";
	private final static String ROUNDS_SCORE = "Score";
	private final static String ROUNDS_RESULTS = "Results";
	private final static String ROUNDS_TIME = "Time";
	// name of course

	private final static String PLAYER_NAME = "PlayerName"; // name of player
	private final static String PLAYER_ID = "PlayerId"; // id of player

	private final String CREATE_PLAYER_TABLE = "CREATE TABLE " + PLAYER_TABLE
			+ "(" + PLAYER_ID + " INTEGER PRIMARY KEY," + PLAYER_NAME + " TEXT"
			+ ")";

	private final String CREATE_CARD_TABLE = "CREATE TABLE " + CARD_TABLE + "("
			+ CARD_ID + " INTEGER PRIMARY KEY," + CARD_NAME + " TEXT,"
			+ CARD_DESC + " TEXT" + ")";

	private final String CREATE_COURSE_TABLE = "CREATE TABLE " + COURSE_TABLE
			+ "(" + COURSE_ID + " INTEGER PRIMARY KEY,"+ COURSE_NAME + " TEXT," + COURSE_PARS + " TEXT"
			+ ")";

	private static final String CREATE_ROUNDS_TABLE = "CREATE TABLE "
			+ ROUNDS_TABLE + "(" + ROUNDS_ID + " INTEGER PRIMARY KEY,"
			+ PLAYER_ID + " INTEGER," + COURSE_ID+ " INTEGER," + ROUNDS_SCORE
			+ " INTEGER," + ROUNDS_RESULTS + " TEXT," + ROUNDS_TIME + " TEXT"
			+ ")";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		myOwnContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_PLAYER_TABLE);
		db.execSQL(CREATE_COURSE_TABLE);
		db.execSQL(CREATE_ROUNDS_TABLE);
		db.execSQL(CREATE_CARD_TABLE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + PLAYER_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + COURSE_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + ROUNDS_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + CARD_TABLE);
		// Create tables again
		onCreate(db);
	}

	public long addPlayer(Player player) {
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put(PLAYER_NAME, player.getName());
		Log.d("Finns namnet: ", ""+checkIfNameExist(player));
		if (checkIfNameExist(player))
			return -1;

		long player_id = database.insert(PLAYER_TABLE, null, values);
		database.close();
		return player_id;

	}

	public long addCourse(Course course) {
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		int[] results = course.getPars();

		values.put(COURSE_NAME, course.getName());
		values.put(COURSE_PARS, getStringFromResults(results));

		long course_id = database.insertOrThrow(COURSE_TABLE, null, values);
		database.close();

		return course_id;

	}

	public long addRounds(Player player, Course course, int score, String time) {
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		int[] results = player.getResults();

		values.put(PLAYER_ID, player.getId());
		values.put(COURSE_ID, course.getID());
		values.put(ROUNDS_SCORE, score);
		values.put(ROUNDS_RESULTS, getStringFromResults(results));
		values.put(ROUNDS_TIME, time);

		long rounds_id = database.insert(ROUNDS_TABLE, null, values);
		database.close();

		return rounds_id;

	}

	public long addCard(Card card) {
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(CARD_NAME, card.getName());
		values.put(CARD_DESC, card.getDescription());

		long card_id = database.insert(CARD_TABLE, null, values);
		database.close();
		return card_id;

	}

	public Card getCard(int id) {
		SQLiteDatabase database = this.getReadableDatabase();

		Cursor cursor = database.query(CARD_TABLE, new String[] { CARD_ID,
				CARD_NAME, CARD_DESC }, CARD_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		int cardID = Integer.parseInt(cursor.getString(0));
		String cardName = cursor.getString(1);
		String cardDesc = cursor.getString(2);
		return new Card(cardID, cardName, cardDesc);
	}

	private String getStringFromResults(int[] results) {
		String str = "";
		for (int i = 0; i < results.length; i++) {
			str = str + results[i] + ";";
		}
		return str;
	}

	public List<Card> getAllCards() {

		SQLiteDatabase database = this.getReadableDatabase();
		List<Card> card = new ArrayList<Card>();

		String selectQuery = "SELECT * FROM " + CARD_TABLE;
		Cursor cursor = database.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				int cardId = Integer.parseInt(cursor.getString(0));
				String name = cursor.getString(1);
				String desc = cursor.getString(2);
				card.add(new Card(cardId, name, desc));

			} while (cursor.moveToNext());
		}

		cursor.close();
		database.close();
		return card;

	}

	private int[] getResultsFromString(String result) {
		String[] results = result.split(";");
		int[] intResults = new int[results.length];
		for (int i = 0; i < results.length; i++) {
			intResults[i] = Integer.parseInt(results[i]);
		}

		return intResults;
	}

	public Player getPlayer(String name) {
		SQLiteDatabase database = this.getReadableDatabase();

		Cursor cursor = database.query(PLAYER_TABLE, new String[] { PLAYER_ID,
				PLAYER_NAME }, PLAYER_NAME + "=?", new String[] { name }, null,
				null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		return new Player(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1));
	}

	public Player getPlayer(int id) {
		SQLiteDatabase database = this.getReadableDatabase();

		Cursor cursor = database.query(PLAYER_TABLE, new String[] { PLAYER_ID,
				PLAYER_NAME }, PLAYER_ID + "=?", new String[] { String.valueOf(id) }, null,
				null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		return new Player(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1));
	}
	
//	public Course getCourse(String name) {
//		SQLiteDatabase database = this.getReadableDatabase();
//
//		Cursor cursor = database.query(COURSE_TABLE, new String[] {
//				COURSE_ID, COURSE_NAME, COURSE_PARS }, COURSE_NAME + "=?",
//				new String[] { name }, null, null, null, null);
//		if (cursor != null)
//			cursor.moveToFirst();
//		return new Course(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
//				getResultsFromString(cursor.getString(2)));
//	}
	
	public Course getCourse(int id) {
		SQLiteDatabase database = this.getReadableDatabase();

		Cursor cursor = database.query(COURSE_TABLE, new String[] {
				COURSE_ID, COURSE_NAME, COURSE_PARS }, COURSE_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		return new Course(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
				getResultsFromString(cursor.getString(2)));
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

		cursor.close();
		database.close();
		return players;

	}

	public List<Course> getAllCourses() {

		SQLiteDatabase database = this.getReadableDatabase();
		List<Course> course = new ArrayList<Course>();

		String selectQuery = "SELECT * FROM " + COURSE_TABLE;
		Cursor cursor = database.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				int id = Integer.parseInt(cursor.getString(0));
				String name = cursor.getString(1);
				course.add(new Course(id, name, getResultsFromString(cursor
						.getString(2))));

			} while (cursor.moveToNext());
		}

		return course;

	}

	public List<Round> getAllRounds() {

		SQLiteDatabase database = this.getReadableDatabase();
		List<Round> rounds = new ArrayList<Round>();

		String selectQuery = "SELECT * FROM " + ROUNDS_TABLE;
		Cursor cursor = database.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				int rounds_id = Integer.parseInt(cursor.getString(0));
				int player_id = Integer.parseInt(cursor.getString(1));
				int course_id = Integer.parseInt(cursor.getString(2));
				int round_score = Integer.parseInt(cursor.getString(3));
				String time = cursor.getString(4);
				Player player = getPlayer(player_id);
				Course course = getCourse(course_id);
				int[] results = getResultsFromString(cursor.getString(4));
				rounds.add(new Round(rounds_id, course, player, round_score,
						results, time));

			} while (cursor.moveToNext());
		}

		return rounds;

	}

	public Round getRound(int id) {
		SQLiteDatabase database = this.getReadableDatabase();

		Cursor cursor = database.query(ROUNDS_TABLE, new String[] { ROUNDS_ID,
				PLAYER_ID, COURSE_ID, ROUNDS_SCORE, ROUNDS_RESULTS,
				ROUNDS_TIME }, ROUNDS_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		int rounds_id = Integer.parseInt(cursor.getString(0));
		int player_id = Integer.parseInt(cursor.getString(1));
		int course_id = Integer.parseInt(cursor.getString(2));
		int round_score = Integer.parseInt(cursor.getString(3));

		Player player = getPlayer(player_id);
		Course course = getCourse(course_id);
		int[] results = getResultsFromString(cursor.getString(4));
		String time = cursor.getString(5);
		return new Round(rounds_id, course, player, round_score, results, time);

	}

	public List<Round> getAllRoundsSpecificPlayer(Player playerSent) {

		SQLiteDatabase database = this.getReadableDatabase();
		List<Round> rounds = new ArrayList<Round>();

		String selectQuery = "SELECT * FROM " + ROUNDS_TABLE;
		Cursor cursor = database.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				int rounds_id = Integer.parseInt(cursor.getString(0));
				int player_id = Integer.parseInt(cursor.getString(1));
				Player player = getPlayer(player_id);

				
				if (player.getName().equals(playerSent.getName())) {
					int course_id = Integer.parseInt(cursor.getString(2));
					int round_score = Integer.parseInt(cursor.getString(3));
					Course course = getCourse(course_id);
					int[] results = getResultsFromString(cursor.getString(4));
					String time = cursor.getString(5);
					rounds.add(new Round(rounds_id, course, player,
							round_score, results, time));
				}
			} while (cursor.moveToNext());
		}

		return rounds;

	}

	public int getPlayerCount() {
		SQLiteDatabase database = this.getReadableDatabase();

		String selectQuery = "SELECT * FROM " + PLAYER_TABLE;
		Cursor cursor = database.rawQuery(selectQuery, null);
		cursor.close();

		return cursor.getCount();

	}

	public int getCourseCount() {
		SQLiteDatabase database = this.getReadableDatabase();

		String selectQuery = "SELECT * FROM " + COURSE_TABLE;
		Cursor cursor = database.rawQuery(selectQuery, null);
		cursor.close();

		return cursor.getCount();

	}

	public int updatePlayer(Player player) {
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put(PLAYER_ID, player.getId());
		values.put(PLAYER_NAME, player.getName());

		Log.d("Finns namnet: ", ""+checkIfNameExist(player));

		if (checkIfNameExist(player)) {
			throw new SQLiteConstraintException();
		}

		return database.update(PLAYER_TABLE, values, PLAYER_ID + " = ?",
				new String[] { String.valueOf(player.getId()) });

	}

	private boolean checkIfNameExist(Player player) {
		Player playerFromDB;
		boolean nameExists = false;
		try {
			playerFromDB = getPlayer(player.name);
			if (playerFromDB != null) {
					nameExists = true;
				}
			}

		 catch (CursorIndexOutOfBoundsException c) {
			nameExists = false;
		}

		return nameExists;
	}

	public int updateCourse(Course course) {
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		int[] results = course.getPars();

		values.put(COURSE_ID, course.getID());
		values.put(COURSE_NAME, course.getName());
		values.put(COURSE_PARS, getStringFromResults(results));
		return database.update(COURSE_TABLE, values, COURSE_ID + " = ?",
				new String[] { String.valueOf(course.id) });

	}

	public int updateCard(Card card) {
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put(CARD_ID, card.getID());
		values.put(CARD_NAME, card.getName());
		values.put(CARD_DESC, card.getDescription());
		return database.update(CARD_TABLE, values, CARD_ID + " = ?",
				new String[] { String.valueOf(card.getID()) });

	}

	public void deletePlayer(Player player) {
		SQLiteDatabase database = this.getWritableDatabase();
		database.delete(PLAYER_TABLE, PLAYER_ID + " = ?",
				new String[] { String.valueOf(player.getId()) });
		database.close();

	}

	public void deleteCourse(Course course) {
		SQLiteDatabase database = this.getWritableDatabase();
		database.delete(COURSE_TABLE, COURSE_ID + " = ?",
				new String[] { String.valueOf(course.id )});
		database.close();

	}

	public void deleteCard(Card card) {
		SQLiteDatabase database = this.getWritableDatabase();
		database.delete(CARD_TABLE, CARD_ID + " = ?",
				new String[] { String.valueOf(card.getID()) });
		database.close();
	}

}