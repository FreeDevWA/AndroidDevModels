package sig.youness.models.weather;

import java.util.List;

//GSON will convert JSON data to a Data object
class Data {
	// Base address for icon download
	private final static String ICON_ADDR = "http://openweathermap.org/img/w/";

	static class Weather {
		String description;
		String icon;
	}

	static class Main {
		float temp;
	}

	List<Weather> weather;

	Main main;

	String name;

	// A method that converts temperature from Kelvin degrees to Celsius
	String getTemperatureInCelsius() {
		float temp = main.temp - 273.15f;
		return String.format("%.2f", temp);
	}

	// getIconAddress concatenates the base address and the specific code for
	// the icon
	public String getIconAddress() {
		return ICON_ADDR + weather.get(0).icon + ".png";
	}

	public String getDescription() {
		if (weather != null && weather.size() > 0)
			return weather.get(0).description;
		return null;
	}
}
