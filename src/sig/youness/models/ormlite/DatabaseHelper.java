package sig.youness.models.ormlite;

import java.sql.SQLException;

import sig.youness.models.R;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
 
/**
 * Database helper which creates and upgrades the database and provides the DAOs for the app.
 * 
 * 
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
 
	/************************************************
	 * Suggested Copy/Paste code. Everything from here to the done block.
	 ************************************************/
 
	private static final String DATABASE_NAME = "studentdir.db";
	private static final int DATABASE_VERSION = 1; 
 
	private Dao<StudentDetails, Integer> studentDao;
	private Dao<TeacherDetails, Integer> teacherDao;
 
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
	}
 
	/************************************************
	 * Suggested Copy/Paste Done
	 ************************************************/
 
	@Override
	public void onCreate(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {
		try {
			
			// Create tables. This onCreate() method will be invoked only once of the application life time i.e. the first time when the application starts.
			TableUtils.createTable(connectionSource, TeacherDetails.class);
			TableUtils.createTable(connectionSource, StudentDetails.class);
			
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Unable to create datbases", e);
		}
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource, int oldVer, int newVer) {
		try {
			
			// In case of change in database of next version of application, please increase the value of DATABASE_VERSION variable, then this method will be invoked 
			//automatically. Developer needs to handle the upgrade logic here, i.e. create a new table or a new column to an existing table, take the backups of the
			// existing database etc.
			
			TableUtils.dropTable(connectionSource, TeacherDetails.class, true);
			TableUtils.dropTable(connectionSource, StudentDetails.class, true);
			onCreate(sqliteDatabase, connectionSource);
			
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Unable to upgrade database from version " + oldVer + " to new "
					+ newVer, e);
		}
	}
	
	// Create the getDao methods of all database tables to access those from android code.
	// Insert, delete, read, update everything will be happened through DAOs
 
	public Dao<TeacherDetails, Integer> getTeacherDao() throws SQLException {
		if (teacherDao == null) {
			teacherDao = getDao(TeacherDetails.class);
		}
		return teacherDao;
	}
 
	public Dao<StudentDetails, Integer> getStudentDao() throws SQLException {
		if (studentDao == null) {
			studentDao = getDao(StudentDetails.class);
		}
		return studentDao;
	}
}