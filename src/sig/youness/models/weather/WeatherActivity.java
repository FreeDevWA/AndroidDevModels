package sig.youness.models.weather;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
 
import com.google.gson.Gson;
 
import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
 
public class WeatherActivity extends ListActivity {
	// Base address for informations download
	private final static String BASE_ADDR = "http://api.openweathermap.org/data/2.5/weather";
 
	// The following array contains the names of the cities we are interested in
	private String[] cities = new String[] { "Tokyo", "London", "Moscow",
			"Ottawa", "Madrid", "Lisboa", "Zurich" };
 
	// Reference to the Adapter object. WeatherAdapter is a custom class,
	// defined in a separate file
 
	private WeatherAdapter adapter;
 
	/*
	 * Mixing the String object containing the name of the city with the base
	 * address, we can generate the complete HTTP address to contact. Its format
	 * should be like this:
	 * http://api.openweathermap.org/data/2.5/weather?q=London This is the
	 * purpose of the getDataAddress method.
	 */
	private String getDataAddress(String city) {
		return BASE_ADDR + "?q=" + city;
	}
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
 
		// The adapter object is instantiated
		adapter = new WeatherAdapter(this);
 
		// The adapter is linked to the ListView
		setListAdapter(adapter);
 
		// We request informations separately via HTTP for every city in the
		// array
		for (String c : cities)
			loadJson(c);
	}
 
	// This method performs the remote request and uses GSON to parse JSON data
	protected void loadJson(String selected) {
 
		// We extend the AsyncTask class and instance an object directly.
		new AsyncTask<String, Void, Data>() {
 
			// The doInBackground method contains the slower operations we want
			// to perform on a separate thread
			@Override
			protected Data doInBackground(String... params) {
				// Params array contains only one object and its value is the
				// string representing the name of the city
				String selectedCity = params[0];
 
				// URL object we'll use to connect to remote host
				URL url;
				try {
					// URL object is created. The input parameter we pass is the
					// URL to contact
					url = new URL(getDataAddress(selectedCity));
 
					// After connection, url provides the stream to the remote
					// data. Reader object can be used to read them
					Reader dataInput = new InputStreamReader(url.openStream());
 
					// GSON needs a stream to read data. We pass two parameters
					// to fromJson method: the stream to read and the Data class
					// structure for automatic parsing
					Data data = new Gson().fromJson(dataInput, Data.class);
					return data;
				} catch (MalformedURLException e1) {
					return null;
				} catch (IOException e1) {
					return null;
				}
			}
 
			// The onPostExecute method receives the return value of
			// doInBackground. Remember that onPostExecute works on the main
			// thread of the application
			protected void onPostExecute(Data result) {
				if (result != null) {
					// If not null, Data object is passed to the Adapter
					adapter.add(result);
				}
			};
 
			// The execute method is invoked to activate the background
			// operation
		}.execute(selected);
 
	}
 
}
