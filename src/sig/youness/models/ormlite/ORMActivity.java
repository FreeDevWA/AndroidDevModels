package sig.youness.models.ormlite;


import sig.youness.models.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class ORMActivity extends Activity implements OnClickListener {

	// Declaration all four buttons of the Main screen
	private Button add_student_btn, add_teacher_btn, view_btn, view_teacher_btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ormactivity);
		
		// Initialization of all four buttons of the Main screen
		add_student_btn = (Button) findViewById(R.id.add_student_btn);
		add_teacher_btn = (Button) findViewById(R.id.add_teacher_btn);
		view_btn = (Button) findViewById(R.id.view_btn);
		view_teacher_btn = (Button) findViewById(R.id.view_teacher_btn);
		
		// Attachment of onClickListner for them
		add_student_btn.setOnClickListener(this);
		add_teacher_btn.setOnClickListener(this);
		view_btn.setOnClickListener(this);
		view_teacher_btn.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {

		// Render StudentAddActivity screen once click on "Add Student" Button
		Log.v("abc", "" + v);
		if(v == add_student_btn) {
			startActivity(new Intent(this, StudentAddActivity.class));
		}
		// Render TeacherAddActivity screen once click on "Add Teacher" Button
		else if(v == add_teacher_btn) {
			startActivity(new Intent(this, TeacherAddActivity.class));
		}
		// Render ViewStudentRecordActivity screen once click on "View Student Records" Button
		else if(v == view_btn) {
			startActivity(new Intent(this, ViewStudentRecordActivity.class));
		}
		// Render ViewTeacherRecordActivity screen once click on "View Teacher Records" Button
		else if(v == view_teacher_btn) {
			startActivity(new Intent(this, ViewTeacherRecordActivity.class));
		}
	}
}
