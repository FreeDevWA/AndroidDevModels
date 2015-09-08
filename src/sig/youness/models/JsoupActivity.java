package sig.youness.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import sig.youness.models.adapter.ListViewAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.ListView;
 
public class JsoupActivity extends Activity {
	ListView listview;
	ListViewAdapter adapter;
	ProgressDialog mProgressDialog;
	ArrayList<HashMap<String, String>> arraylist;
	public static String RANK = "rank";
	public static String COUNTRY = "country";
	public static String POPULATION = "population";
	public static String FLAG = "flag";
	// URL Address
	String url = "http://www.androidbegin.com/tutorial/jsouplistview.html";
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the view from listview_main.xml
		setContentView(R.layout.listviewjsoup);
		// Execute DownloadJSON AsyncTask
		new JsoupListView().execute();
 
	}
 
	// Title AsyncTask
	private class JsoupListView extends AsyncTask<Void, Void, Void> {
 
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Create a progressdialog
			mProgressDialog = new ProgressDialog(JsoupActivity.this);
			// Set progressdialog title
			mProgressDialog.setTitle("Android Jsoup ListView Tutorial");
			// Set progressdialog message
			mProgressDialog.setMessage("Loading...");
			mProgressDialog.setIndeterminate(false);
			// Show progressdialog
			mProgressDialog.show();
		}
 
		@Override
		protected Void doInBackground(Void... params) {
			// Create an array
			arraylist = new ArrayList<HashMap<String, String>>();
 
			try {
				// Connect to the Website URL
				Document doc = Jsoup.connect(url).get();
				// Identify Table Class "worldpopulation"
				for (Element table : doc.select("table[class=worldpopulation]")) {
					
					// Identify all the table row's(tr)
					for (Element row : table.select("tr:gt(0)")) {
						HashMap<String, String> map = new HashMap<String, String>();
						
						// Identify all the table cell's(td)
						Elements tds = row.select("td");
						
						// Identify all img src's
						Elements imgSrc = row.select("img[src]");
						// Get only src from img src
						String imgSrcStr = imgSrc.attr("src");
						
						// Retrive Jsoup Elements
						// Get the first td
						map.put("rank", tds.get(0).text());
						// Get the second td
						map.put("country", tds.get(1).text());
						// Get the third td
						map.put("population", tds.get(2).text());
						// Get the image src links
						map.put("flag", imgSrcStr);
						// Set all extracted Jsoup Elements into the array
						arraylist.add(map);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 
			return null;
		}
 
		@Override
		protected void onPostExecute(Void result) {
			// Locate the listview in listview_main.xml
			listview = (ListView) findViewById(R.id.listview);
			// Pass the results into ListViewAdapter.java
			adapter = new ListViewAdapter(JsoupActivity.this, arraylist);
			// Set the adapter to the ListView
			listview.setAdapter(adapter);
			// Close the progressdialog
			mProgressDialog.dismiss();
		}
	}
 
}
