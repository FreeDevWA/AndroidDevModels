package sig.youness.models.ormlite;


import java.sql.SQLException;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

public class ViewTeacherRecordActivity extends Activity implements OnItemClickListener, OnItemLongClickListener{

	// Reference of DatabaseHelper class to access its DAOs and other components
	private DatabaseHelper databaseHelper = null;
	
	// This ListView displays the list of Teacher Details data fetched from database.
	private ListView listview;
	
	// This holds the value of the row number, which user has selected for further action
	private int selectedRecordPosition = -1;
	
	// Declaration of DAO to interact with corresponding table
	private Dao<TeacherDetails, Integer> teacherDao;
	
	// It holds the list of TeacherDetails object fetched from Database
	private List<TeacherDetails> teacherList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activityviewrecorde);
		listview = (ListView) findViewById(R.id.listview);
		
		((TextView)findViewById(R.id.header_tv)).setText("View Teacher Records");
		
		try {
			// This is how, a reference of DAO object can be done
			teacherDao =  getHelper().getTeacherDao();
			
			// Query the database. We need all the records so, used queryForAll()
			teacherList = teacherDao.queryForAll();
			
			// Set the header of the ListView
			final LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final View rowView = inflater.inflate(R.layout.listviewitem, listview, false);
			((TextView)rowView.findViewById(R.id.teacher_tv)).setText("Address");
			((TextView)rowView.findViewById(R.id.student_name_tv)).setText("Teacher Name");
			listview.addHeaderView(rowView);
			
			//Now, link the  RecordArrayAdapter with the ListView
			listview.setAdapter(new RecordArrayAdapter(this, R.layout.listviewitem, teacherList, teacherDao));
			
			// Attach OnItemLongClickListener and OnItemClickListener to track user action and perform accordingly
			listview.setOnItemLongClickListener(this);
			listview.setOnItemClickListener(this);
			
			// If, no record found in the database, appropriate message needs to be displayed.
			populateNoRecordMsg();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void populateNoRecordMsg()
	{
		// If, no record found in the database, appropriate message needs to be displayed.
		if(teacherList.size() == 0)
		{
			final TextView tv = new TextView(this);
			tv.setPadding(5, 5, 5, 5);
			tv.setTextSize(15);
			tv.setText("No Record Found !!");
			listview.addFooterView(tv);
		}
	}
	
	// This is how, DatabaseHelper can be initialized for future use
	private DatabaseHelper getHelper() {
		if (databaseHelper == null) {
			databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
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
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// If the pressed row is not a header, update selectedRecordPosition and render ViewTeacherDetailsActivity screen
		if(position > 0)
		{
			selectedRecordPosition = position - 1;
			// Details screen showing code can put over here
			final Intent intent = new Intent(this, ViewTeacherDetailsActivity.class);
			intent.putExtra("details",teacherList.get(selectedRecordPosition));
			startActivity(intent);
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position,long id) {
		
		// If the long pressed row is not a header, update selectedRecordPosition and show the dialog
		if(position > 0)
		{
			selectedRecordPosition = position - 1;
			showDialog();
		}
		return true;
	}
	
	private void showDialog()
	{
		// Before deletion of the long pressed record, need to confirm with the user. So, build the AlartBox first
		final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		
		// Set the appropriate message into it.
		alertDialogBuilder.setMessage("Are you Really want to delete the selected record ?");
		
		// Add a positive button and it's action. In our case action would be deletion of the data
		alertDialogBuilder.setPositiveButton("Delete",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						try {
							// This is how, data from the database can be deleted
							teacherDao.delete(teacherList.get(selectedRecordPosition));
							
							// Removing the same from the List to remove from display as well
							teacherList.remove(selectedRecordPosition);
							listview.invalidateViews();
							
							// Reset the value of selectedRecordPosition
							selectedRecordPosition = -1;
							populateNoRecordMsg();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
		// Add a negative button and it's action. In our case, just hide the dialog box
		alertDialogBuilder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});

		// Now, create the Dialog and show it.
		final AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}
}
