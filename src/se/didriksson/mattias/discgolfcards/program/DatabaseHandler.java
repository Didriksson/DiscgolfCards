package se.didriksson.mattias.discgolfcards.program;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	// http://www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/

	private static final String DATABASE_NAME = "DGChallengeDB.db";
	private static final int DATABASE_VERSION = 5;

	// Table names
	private final static String PLAYER_TABLE = "Players"; // name of table
	private final static String COURSE_TABLE = "Courses"; // name of table
	private final static String ROUNDS_TABLE = "Rounds"; // name of table

	private final static String COURSE_ID = "course_id"; // id value for course
	private final static String COURSE_NAME = "name"; // name of course

	private final static String ROUNDS_ID = "_id";
	private final static String ROUNDS_SCORE = "Score";

	// name of course

	private final static String PLAYER_ID = "player_id"; // id value for player
	private final static String PLAYER_NAME = "name"; // name of player

	private final String CREATE_PLAYER_TABLE = "CREATE TABLE " + PLAYER_TABLE
			+ "(" + PLAYER_ID + " INTEGER PRIMARY KEY," + PLAYER_NAME + " TEXT"
			+ ")";

	private final String CREATE_COURSE_TABLE = "CREATE TABLE " + COURSE_TABLE
			+ "(" + COURSE_ID + " INTEGER PRIMARY KEY," + COURSE_NAME + " TEXT"
			+ ")";
	
    private static final String CREATE_ROUNDS_TABLE = "CREATE TABLE "
            + ROUNDS_TABLE + "(" + ROUNDS_ID + " INTEGER PRIMARY KEY,"
            + PLAYER_ID + " INTEGER," + COURSE_ID + " INTEGER,"
            + ROUNDS_SCORE + " INTEGER" + ")";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_PLAYER_TABLE);
		db.execSQL(CREATE_COURSE_TABLE);
		db.execSQL(CREATE_ROUNDS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + PLAYER_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + COURSE_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + ROUNDS_TABLE);
		// Create tables again
		onCreate(db);
	}

	public long addPlayer(Player player) {
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(PLAYER_NAME, player.getName());

		long player_id = database.insert(PLAYER_TABLE, null, values);
		database.close();
		return player_id;

	}

	public long addCourse(Course course) {
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COURSE_NAME, course.getName());

		long course_id = database.insert(COURSE_TABLE, null, values);
		database.close();

		return course_id;

	}

	public long addRounds(long player_id, long course_id, int score) {
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put(PLAYER_ID, player_id);
		values.put(COURSE_ID, course_id);
		values.put(ROUNDS_SCORE, score);

		long rounds_id = database.insert(ROUNDS_TABLE, null, values);
		database.close();

		return rounds_id;

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

	public Course getCourse(int id) {
		SQLiteDatabase database = this.getReadableDatabase();

		Cursor cursor = database.query(COURSE_TABLE, new String[] { COURSE_ID,
				COURSE_NAME }, COURSE_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		return new Course(Integer.parseInt(cursor.getString(0)),
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

	public List<Course> getAllCourses() {

		SQLiteDatabase database = this.getReadableDatabase();
		List<Course> course = new ArrayList<Course>();

		String selectQuery = "SELECT * FROM " + COURSE_TABLE;
		Cursor cursor = database.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				int id = Integer.parseInt(cursor.getString(0));
				String name = cursor.getString(1);
				course.add(new Course(id, name));

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
				Player player = getPlayer(player_id);
				Course course = getCourse(course_id);
				rounds.add(new Round(rounds_id, course, player, round_score));

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

		values.put(PLAYER_NAME, player.getName());

		return database.update(PLAYER_TABLE, values, PLAYER_ID + " = ?",
				new String[] { String.valueOf(player.getID()) });

	}

	public int updateCourse(Course course) {
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put(COURSE_NAME, course.getName());

		return database.update(COURSE_TABLE, values, COURSE_ID + " = ?",
				new String[] { String.valueOf(course.getID()) });

	}

	public void deletePlayer(Player player) {
		SQLiteDatabase database = this.getWritableDatabase();
		database.delete(PLAYER_TABLE, PLAYER_ID + " = ?",
				new String[] { String.valueOf(player.getID()) });
		database.close();

	}

	public void deleteCourse(Course course) {
		SQLiteDatabase database = this.getWritableDatabase();
		database.delete(COURSE_TABLE, COURSE_ID + " = ?",
				new String[] { String.valueOf(course.getID()) });
		database.close();

	}

}