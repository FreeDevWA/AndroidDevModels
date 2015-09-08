package sig.youness.models;

import java.io.File;

import sig.youness.models.adapter.GridViewAdapter;
import sig.youness.models.grid.ViewImage;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class PhotoActivity extends Activity {

	// Declare variables
	private String[] FilePathStrings;
	private String[] FileNameStrings;
	private File[] listFile;
	GridView grid;
	GridViewAdapter adapter;
	File file;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gridview);

		// Check for SD Card
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			Toast.makeText(this, "Error! No SDCARD Found!", Toast.LENGTH_LONG)
					.show();
		} else {
			// Locate the image folder in your SD Card
			file = new File(Environment.getExternalStorageDirectory()
					+ File.separator + "SDImageTutorial");
			// Create a new folder if no folder named SDImageTutorial exist
			file.mkdirs();
		}

		if (file.isDirectory()) {
			listFile = file.listFiles();
			// Create a String array for FilePathStrings
			Log.e("Tag",""+ listFile.length);
			FilePathStrings = new String[listFile.length];
			// Create a String array for FileNameStrings
			FileNameStrings = new String[listFile.length];

			for (int i = 0; i < listFile.length; i++) {
				// Get the path of the image file
				FilePathStrings[i] = listFile[i].getAbsolutePath();
				// Get the name image file
				FileNameStrings[i] = listFile[i].getName();
			}
		}

		// Locate the GridView in gridview_main.xml
		grid = (GridView) findViewById(R.id.gridview);
		// Pass String arrays to LazyAdapter Class
		adapter = new GridViewAdapter(this, FilePathStrings, FileNameStrings);
		// Set the LazyAdapter to the GridView
		grid.setAdapter(adapter);

		// Capture gridview item click
		grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Intent i = new Intent(PhotoActivity.this, ViewImage.class);
				// Pass String arrays FilePathStrings
				i.putExtra("filepath", FilePathStrings);
				// Pass String arrays FileNameStrings
				i.putExtra("filename", FileNameStrings);
				// Pass click position
				i.putExtra("position", position);
				startActivity(i);
			}

		});
	}

}
