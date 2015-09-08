package sig.youness.models;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import sig.youness.models.menuview.CircleImageView;
import sig.youness.models.menuview.CircleLayout;
import sig.youness.models.menuview.CircleLayout.OnItemClickListener;
import sig.youness.models.menuview.CircleLayout.OnItemSelectedListener;
import sig.youness.models.ormlite.ORMActivity;
import sig.youness.models.weather.WeatherActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends Activity implements OnItemSelectedListener, OnItemClickListener{
	TextView selectedTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ImageButton retrofitactivity = (ImageButton)findViewById(R.id.retofitButton);
		ImageButton sideButtonactivity = (ImageButton)findViewById(R.id.sideButton);
		ImageButton calenderButton = (ImageButton)findViewById(R.id.calenderButton);
		ImageButton countryButton = (ImageButton) findViewById(R.id.imagecountry);
		ImageButton weatherButton = (ImageButton) findViewById(R.id.imagesnow);
		ImageButton ormButton = (ImageButton) findViewById(R.id.imageorm);
		CircleLayout circleMenu = (CircleLayout)findViewById(R.id.main_circle);
		circleMenu.setOnItemSelectedListener(this);
		circleMenu.setOnItemClickListener(this);
		AdView mAdView = (AdView) findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
		selectedTextView = (TextView)findViewById(R.id.selected_textView);
		selectedTextView.setText(((CircleImageView)circleMenu.getSelectedItem()).getName());
		retrofitactivity.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
					startActivity(new Intent(getApplicationContext(), ListActivity.class));

			}

		});
		sideButtonactivity.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), PhotoActivity.class));
				
			}
		});
		calenderButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), CalenderActivity.class));
				
			}
		});
		countryButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), JsoupActivity.class));
				
			}
		});
		weatherButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), WeatherActivity.class));
				
			}
		});
		ormButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), ORMActivity.class));
				
			}
		});
	}

	@Override
	public void onItemSelected(View view, int position, long id, String name) {		
		selectedTextView.setText(name);
	}

	@Override
	public void onItemClick(View view, int position, long id, String name) {
		Toast.makeText(getApplicationContext(), getResources().getString(R.string.start_app) + " " + name, Toast.LENGTH_SHORT).show();
	}

}
