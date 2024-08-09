/**
 * A class that handles the functionality of loading the file and putting it
 * And creating ClimateRecord Objects from it and putting them into an array
 * It makes use of the ClimateRecord class
 * @author Daniel, Christine, Dave, Ewura Ama
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileLoader {
    //Instance variables for the file path and that 
    //of the number of lines in the path
    private File filePath;
    private int lineCount;
    
    /**
     * A constructor to initialize the FileLoader Class
     * The contrutor takes the path of the file to load from and uses
     * That for loading
     * @param filePath the path of the file to be loaded
     */
    public FileLoader(File filePath) {
        this.filePath = filePath;
    }

    /**
     * A method to load data into an array
     * First we loop through the the file and count the number 
     * of lines and then use that as the size of the array
     * Afterwards, then we loop again creating objects and putting them into the array
     * We chose to do this instead of starting the array with an initial size because 
     * Due to the resizing and creating of new arrays, the worst case time complexity
     * would be O(n^2)
     * But our approach uses O(n) time where n is the number of lines in the file
     * @return the array containing the data loaded
     */
    public ClimateRecord[] loadData() {

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            lineCount = 0;//Starting the count from 0
            while (reader.readLine() != null) {
                lineCount++; //Increamenting count after every line read
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Creating an array of the same size as the data points
        ClimateRecord[] recordsArray = new ClimateRecord[lineCount - 1]; 
        int count = 0;
        String line;
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Do not read the first line because it is a header
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                //Picking the values and creating objects for them
                String region = data[0];
                int year = Integer.parseInt(data[1]);
                int month = Integer.parseInt(data[2]);
                double temperature = Double.parseDouble(data[3]);
                double precipitation = Double.parseDouble(data[4]);

                ClimateRecord record = new ClimateRecord(region, year, month, temperature, precipitation);

                recordsArray[count] = record;
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return recordsArray;
    }

}
