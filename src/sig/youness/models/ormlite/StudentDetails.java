package sig.youness.models.ormlite;

import java.io.Serializable;
import java.util.Date;
import com.j256.ormlite.field.DatabaseField;
 
public class StudentDetails implements Serializable {
 
	/**
	 * Model class for student_details database table
	 */
	private static final long serialVersionUID = -222864131214757024L;
	
	public static final String ID_FIELD = "student_id";
	public static final String TEACHER_ID_FIELD = "teacher_id";
	
	// Primary key defined as an auto generated integer 
	// If the database table column name differs than the Model class variable name, the way to map to use columnName
	@DatabaseField(generatedId = true, columnName = ID_FIELD)
	public int studentId;
 
	// Define a String type field to hold student's name
	@DatabaseField(columnName = "student_name")
	public String studentName;
	
	// Define a String type field to hold student's address
	public String address;
	
	// Foreign key defined to hold associations
	@DatabaseField(canBeNull = false, foreign = true, foreignAutoRefresh = true)
	public TeacherDetails teacher;
	
	// Define a String type field to hold student's date of insertion
	@DatabaseField(columnName = "added_date")
	public Date addedDate;
	
	// Default constructor is needed for the SQLite, so make sure you also have it
	public StudentDetails(){
		
	}
	
	//For our own purpose, so it's easier to create a StudentDetails object
	public StudentDetails(final String name, final String address, TeacherDetails teacher){
		this.studentName = name;
		this.address = address;
		this.teacher = teacher;
	}
 
}
