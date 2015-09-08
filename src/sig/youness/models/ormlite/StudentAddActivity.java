package sig.youness.models.ormlite;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import sig.youness.models.R;
 
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
 
public class StudentAddActivity extends Activity implements OnClickListener {
 
	// Reference of DatabaseHelper class to access its DAOs and other components
	private DatabaseHelper databaseHelper = null;
 
	// Declaration of screen components
	private EditText student_name_et, address_et;
	private Button reset_btn, submit_btn;
	private Spinner teacher_sp;
	
	// This object would hold the list of all teachers, so user can select a particular one to associate with a student
	private List<TeacherDetails> teacherList;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activityaddstudent);
 
		student_name_et = (EditText) findViewById(R.id.student_name_et);
		address_et = (EditText) findViewById(R.id.address_et);
		teacher_sp = (Spinner) findViewById(R.id.teacher_sp);
		reset_btn = (Button) findViewById(R.id.reset_btn);
		submit_btn = (Button) findViewById(R.id.submit_btn);
		
		try {
			// This is how, a reference of DAO object can be done
			// Need to find out list of TeacherDetails from database, so initialize DAO for TeacherDetails first
			final Dao<TeacherDetails, Integer> teachDao = getHelper().getTeacherDao();
			
			// Query the database. We need all the records so, used queryForAll()
			teacherList = teachDao.queryForAll();
			
			// Populate the spinner with Teachers data by using CustomAdapter
			teacher_sp.setAdapter(new CustomAdapter(this,android.R.layout.simple_spinner_item, android.R.layout.simple_spinner_dropdown_item, teacherList));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		reset_btn.setOnClickListener(this);
		submit_btn.setOnClickListener(this);
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
 
		if(v == submit_btn)
		{
			// Student Details record only be added, if atleast one Teacher Details data exists in the database
			if(teacherList.size() > 0)
			{
				// All input fields are mandatory, so made a check 
				if(student_name_et.getText().toString().trim().length() > 0 && 
										address_et.getText().toString().trim().length() > 0)
				{
					// Create the StudentDetails object and set the inputed data into it
					final StudentDetails stuDetails = new StudentDetails();
					stuDetails.studentName = student_name_et.getText().toString();
					stuDetails.address = address_et.getText().toString();
					stuDetails.addedDate = new Date();
					
					// StudentDetails has a reference to TeacherDetails, so set the reference as well
					stuDetails.teacher =  (TeacherDetails) teacher_sp.getSelectedItem();
					
					try {
						// Now, need to interact with StudentDetails table/object, so initialize DAO for that
						final Dao<StudentDetails, Integer> studentDao = getHelper().getStudentDao();
						
						//This is the way to insert data into a database table 
						studentDao.create(stuDetails);
						reset();
						showDialog();
						
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				// Show a dialog with appropriate message in case input fields are blank
				else
				{
					showMessageDialog("All fields are mandatory !!");
				}
			}
			// If no TeacherDetails data found in the database, show a dialog with appropriate message to user
			else
			{
				showMessageDialog("Please, add Teacher Details first !!");
			}
		}
		else if(v == reset_btn)
		{
			reset();
		}
	}
	
	private void showMessageDialog(final String message)
	{
		final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setMessage(message);
		final AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}
	
	// Clear the entered text
	private void reset()
	{
		student_name_et.setText("");
		address_et.setText("");
	}
	
	private void showDialog()
	{
		// After submission, Dialog opens up with "Success" message. So, build the AlartBox first
		final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		
		// Set the appropriate message into it.
		alertDialogBuilder.setMessage("Student record added successfully !!");
		
		// Add a positive button and it's action. In our case action would be, just hide the dialog box
		// so no need to write any code for that.
		alertDialogBuilder.setPositiveButton("Add More",
				new DialogInterface.OnClickListener() {
 
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
					}
				});
		
		// Add a negative button and it's action. In our case, just open up the ViewStudentRecordActivity screen 
		//to display all the records
		alertDialogBuilder.setNegativeButton("View Records",
				new DialogInterface.OnClickListener() {
 
					@Override
					public void onClick(DialogInterface dialog, int which) {
						final Intent negativeActivity = new Intent(getApplicationContext(),ViewStudentRecordActivity.class);
						startActivity(negativeActivity);
						finish();
					}
				});
 
		// Now, create the Dialog and show it.
		final AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}
	
	// Custom Adapter to feed data to the Teacher Spinner
	@SuppressWarnings("rawtypes")
	class CustomAdapter extends ArrayAdapter<String>
	{
		LayoutInflater inflater;
		
		// Holds data of Teacher Details
		List objects;
		
		@SuppressWarnings("unchecked")
		public CustomAdapter(Context context, int resource, int dropDownViewResource, List objects) {
			super(context, resource, objects);
			this.setDropDownViewResource(dropDownViewResource);
			inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			// set the Teacher Details objects to populate the Spinner 
			this.objects = objects;
		}
 
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return getCustomView(position, convertView, parent);
		}
		
		@Override
		public View getDropDownView(int position, View convertView,ViewGroup parent) {
			return getCustomView(position, convertView, parent);
		}
		
		// Inflate the Android default spinner layout view, set the label according to passed data and 
		// return the view to display as one row of the Teacher Spinner
		public View getCustomView(int position, View convertView, ViewGroup parent) {
			
			final View row = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
			
			final TextView label = (TextView) row.findViewById(android.R.id.text1);
			final TeacherDetails teacher = (TeacherDetails) this.objects.get(position);
    		label.setText(teacher.teacherName);
			return row;
		}
	}
}
