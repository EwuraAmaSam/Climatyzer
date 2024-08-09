/**This class is an abstact class that keeps the attributes and methods of the climatyzer application
 * it has the getters of all the attributes needed
 * @author Daniel , Ewurama, Daniel, Dave 
 */
public class ClimateRecord {
    private String region;
    private int year;
    private int month;
    private double temperature;
    private double precipitation;

    public ClimateRecord(String region, int year, int month, double temperature, double precipitation) {
        this.region = region;
        this.year = year;
        this.month = month;
        this.temperature = temperature;
        this.precipitation = precipitation;
    }

    // Getters
    public String getRegion() { return region; }
    public int getYear() { return year; }
    public int getMonth() { return month; }
    public double getTemperature() { return temperature; }
    public double getPrecipitation() { return precipitation; }

    // ToString method for easier debugging
    @Override
    public String toString() {
        return String.format("%s,%d,%s,%.1f,%.1f", region, year, month, temperature, precipitation);
    }
}
