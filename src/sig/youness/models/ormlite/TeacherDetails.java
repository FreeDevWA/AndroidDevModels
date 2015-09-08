package sig.youness.models.ormlite;

import java.io.Serializable;
import com.j256.ormlite.field.DatabaseField;
 
public class TeacherDetails implements Serializable {
 
	/**
	 *  Model class for teacher_details database table
	 */
	private static final long serialVersionUID = -222864131214757024L;
	
	// Primary key defined as an auto generated integer 
	// If the database table column name differs than the Model class variable name, the way to map to use columnName
	@DatabaseField(generatedId = true, columnName = "teacher_id")
	public int teacherId;
 
	// Define a String type field to hold teacher's name
	@DatabaseField(columnName = "teacher_name")
	public String teacherName;
	
	// Define a String type field to hold student's address
	public String address;
	
	// Default constructor is needed for the SQLite, so make sure you also have it
	public TeacherDetails(){
		
	}
	
	//For our own purpose, so it's easier to create a TeacherDetails object
	public TeacherDetails(final String name, final String address){
		this.teacherName = name;
		this.address = address;
	}
}