package sig.youness.models.ormlite;
import java.sql.SQLException;

import sig.youness.models.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
 
public class TeacherAddActivity extends Activity implements OnClickListener {
 
	// Reference of DatabaseHelper class to access its DAOs and other components
	private DatabaseHelper databaseHelper = null;
	
	private EditText teacher_name_et, address_et;
	private Button reset_btn, submit_btn;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activityaddteacher);
 
		teacher_name_et = (EditText) findViewById(R.id.teacher_name_et);
		address_et = (EditText) findViewById(R.id.address_et);
		reset_btn = (Button) findViewById(R.id.reset_btn);
		submit_btn = (Button) findViewById(R.id.submit_btn);
		
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
			// All input fields are mandatory, so made a check
			if(teacher_name_et.getText().toString().trim().length() > 0 && 
									address_et.getText().toString().trim().length() > 0)
			{
				// Once click on "Submit", it's first creates the TeacherDetails object
				final TeacherDetails techDetails = new TeacherDetails();
				
				// Then, set all the values from user input
				techDetails.teacherName = teacher_name_et.getText().toString();
				techDetails.address = address_et.getText().toString();
				
				try {
					// This is how, a reference of DAO object can be done
					final Dao<TeacherDetails, Integer> techerDao = getHelper().getTeacherDao();
					
					//This is the way to insert data into a database table 
					techerDao.create(techDetails);
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
		teacher_name_et.setText("");
		address_et.setText("");
	}
	
	private void showDialog()
	{
		// After submission, Dialog opens up with "Success" message. So, build the AlartBox first
		final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		
		// Set the appropriate message into it.
		alertDialogBuilder.setMessage("Teacher record added successfully !!");
		
		// Add a positive button and it's action. In our case action would be, just hide the dialog box , 
		// so no need to write any code for that.
		alertDialogBuilder.setPositiveButton("Add More",
				new DialogInterface.OnClickListener() {
 
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						//finish();
					}
				});
		
		// Add a negative button and it's action. In our case, just open up the ViewTeacherRecordActivity screen 
		// to display all the records
		alertDialogBuilder.setNegativeButton("View Records",
				new DialogInterface.OnClickListener() {
 
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent negativeActivity = new Intent(getApplicationContext(),ViewTeacherRecordActivity.class);
						startActivity(negativeActivity);
						finish();
					}
				});
 
		// Now, create the Dialog and show it.
		final AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}
}
