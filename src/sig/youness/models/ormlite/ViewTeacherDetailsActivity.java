package sig.youness.models.ormlite;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import sig.youness.models.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

public class ViewTeacherDetailsActivity extends Activity implements OnClickListener {

	// Reference of DatabaseHelper class to access its DAOs and other components
	private DatabaseHelper databaseHelper = null;

	// Declaration of screen components
	private TextView teacher_name_et, address_et, students_et;
	private Button close_btn;
	
	// Declaration of DAO to interact with corresponding table
	private Dao<StudentDetails, Integer> studentDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activtyviewteacher);

		teacher_name_et = (TextView) findViewById(R.id.teacher_name_et);
		address_et = (TextView) findViewById(R.id.address_et);
		students_et = (TextView) findViewById(R.id.students_et);
		close_btn = (Button) findViewById(R.id.close_btn);
		
		close_btn.setOnClickListener(this);
		
		//Receive the TeacherDetails object which has sent by the previous screen through Intent
		final TeacherDetails tDetails = (TeacherDetails) getIntent().getExtras().getSerializable("details");
		
		teacher_name_et.setText(tDetails.teacherName);
		address_et.setText(tDetails.address);
		
		// This String would hold the list of all associated student's name (comma separated) for the selected Teacher 
		final List<String> studentName = new ArrayList<String>();
		
		try {
			// This is how, a reference of DAO object can be done
			studentDao =  getHelper().getStudentDao();
			
			// Get our query builder from the DAO
			final QueryBuilder<StudentDetails, Integer> queryBuilder = studentDao.queryBuilder();
			
			// We need only Students who are associated with the selected Teacher, so build the query by "Where" clause
			queryBuilder.where().eq(StudentDetails.TEACHER_ID_FIELD, tDetails.teacherId);
			
			// Prepare our SQL statement
			final PreparedQuery<StudentDetails> preparedQuery = queryBuilder.prepare();
			
			// Fetch the list from Database by queryingit 
			final Iterator<StudentDetails>  studentsIt = studentDao.query(preparedQuery).iterator();
			
			// Iterate through the StudentDetails object iterator and populate the comma separated String
			while (studentsIt.hasNext()) {
				final StudentDetails sDetails = studentsIt.next();
				studentName.add(sDetails.studentName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		students_et.setText(studentName.toString());
	}

	// This is how, DatabaseHelper can be initialized for future use
	private DatabaseHelper getHelper() {
		if (databaseHelper == null) {
			databaseHelper = OpenHelperManager.getHelper(this,DatabaseHelper.class);
		}
		return databaseHelper;
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();

		/*
		 * You'll need this in your class to release the helper when done.
		 */
		if (databaseHelper != null) {
			OpenHelperManager.releaseHelper();
			databaseHelper = null;
		}
	}
	
	@Override
	public void onClick(View v) {

		if(v == close_btn){
			finish();
		}
		
	}
}
