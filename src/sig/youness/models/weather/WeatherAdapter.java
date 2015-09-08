package sig.youness.models.weather;

import java.util.ArrayList;

import sig.youness.models.R;

import com.squareup.picasso.Picasso;
 
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class WeatherAdapter extends BaseAdapter {
 
	// Source data: each of Data objects contains the informations about a city
	private ArrayList<Data> cities = new ArrayList<Data>();
	private Context context;
 
	// A ViewHolder object stores each of the widgets reference in the layout.
	// It avoids a frequent use of findViewById
	private static class ViewHolder {
		ImageView imgv;
		TextView city;
		TextView description;
		TextView temperature;
	}
 
	public WeatherAdapter(Context context) {
		this.context = context;
	}
 
	// A Data object is added to List<Data>.
	// The invocation of notifyDataSetChanged implies the ListView refresh
	void add(Data c) {
		cities.add(c);
		notifyDataSetChanged();
	}
 
	// getCount returns the number of elements in the data structure
	@Override
	public int getCount() {
		return cities.size();
	}
 
	// getItem returns the element stored in a specific position of the data
	// structure
	@Override
	public Object getItem(int pos) {
 
		return cities.get(pos);
	}
 
	/*
	 * getItemId performs the same work of getItem but returns the object id
	 * instead of the reference to the object
	 */
	@Override
	public long getItemId(int pos) {
 
		return pos;
	}
 
	/*
	 * getView creates a View object that displays informations stored in a Data
	 * object at a specific position in the ArrayList
	 */
	@Override
	public View getView(int pos, View v, ViewGroup vg) {
 
		ViewHolder vh = null;
 
		// If v is null, a new View is instantiated
		if (v == null) {
			// The LayoutInflater creates the View using a XML layout as a model
			v = LayoutInflater.from(context).inflate(R.layout.rowweather, null);
 
			// References to internal widgets are stored in a ViewHolder
			vh = new ViewHolder();
			vh.imgv = (ImageView) v.findViewById(R.id.img);
			vh.description = (TextView) v.findViewById(R.id.description);
			vh.city = (TextView) v.findViewById(R.id.city);
			vh.temperature = (TextView) v.findViewById(R.id.temperature);
			v.setTag(vh);
		} else
			// v is not null so we don't need to invoke the LayoutInflater
			vh = (ViewHolder) v.getTag();
 
		// We retrieve the object in a specific position
		Data res = (Data) getItem(pos);
 
		// Picasso library downloads the icon and set it as ImageView source
		Picasso.with(context).load(res.getIconAddress()).into(vh.imgv);
 
		// Other TextViews are filled with Data object informations
		vh.description.setText(res.getDescription());
		vh.city.setText(res.name);
		vh.temperature.setText(res.getTemperatureInCelsius() + "°C");
 
		return v;
	}
 
}
