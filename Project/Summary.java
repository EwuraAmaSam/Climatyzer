/**
 * This class gives the summary of key information users may need to know about climate information in any given region
 * It gives the mean , mode, of both Temperature and precipitation 
 * @author Dave , Daniel, Christine,Ewura Ama 
 */

public class Summary {
    private ClimateRecord[] dataPoints;
    
    
    public Summary(ClimateRecord[] records){
        dataPoints = records;
        System.out.println("=======================================================================");
        System.out.println("Summary of the uploaded data.");
    }

    /**
     * This method gets the mean of the temperature data that has been uploaded
     * @return the mean of the temperature values
     */
    public double meanData(){
        int length = dataPoints.length;
        double sum = 0;
        for (int i = 0; i < dataPoints.length; i++){
            sum = sum + dataPoints[i].getTemperature();
        }
        double mean = sum/length;
        // System.out.printf("The mean is %.2f\n", mean);
        String formattedMean = String.format("%.2f",mean);
        return Double.parseDouble(formattedMean);
    }

    /**
     * A method to return the mean precipitation
     * @return the mean precipitation
     */
    public double meanDataP(){
        int length = dataPoints.length;
        double sum = 0;
        for (int i = 0; i < dataPoints.length; i++){
            sum = sum + dataPoints[i].getPrecipitation();
        }
        double mean = sum/length;

        String formattedMean = String.format("%.2f\n",mean);
        return Double.parseDouble(formattedMean);
    }
    
    /**
     * A method to calculate the most occurring temperature
     */
    public void modeData(){
        DataSorting.mergeSort(dataPoints, "temperature");
        double mode = dataPoints[0].getTemperature();
        int maxCount = 0;
        int currentCount = 0;

        // Iterate through the sorted dataPoints and count occurrence of data
        for (int i = 1; i < dataPoints.length; i++){
            if (dataPoints[i].getTemperature() == dataPoints[i - 1].getTemperature()){
                currentCount ++;
            }
            else{
                if (currentCount > maxCount){
                    maxCount = currentCount;
                    mode = dataPoints[i - 1].getTemperature();
                }
                currentCount = 1;
            }
        }

        // Check for the final element
        if (currentCount > maxCount){
            mode = dataPoints[dataPoints.length - 1].getTemperature();
        }
        
        System.out.printf("The most occurring data point is %.2f\n", mode);
    }
    
    /**
     * A method to calculate the most occuring precipitation value
     */
    public void modeDataP(){
        DataSorting.mergeSort(dataPoints, "precipitation");
        double mode = dataPoints[0].getPrecipitation();
        int maxCount = 0;
        int currentCount = 0;

        // Iterate through the sorted dataPoints and count occurrence of data
        for (int i = 1; i < dataPoints.length; i++){
            if (dataPoints[i].getPrecipitation() == dataPoints[i - 1].getPrecipitation()){
                currentCount ++;
            }
            else{
                if (currentCount > maxCount){
                    maxCount = currentCount;
                    mode = dataPoints[i - 1].getPrecipitation();
                }
                currentCount = 1;
            }
        }

        // Check for the final element
        if (currentCount > maxCount){
            mode = dataPoints[dataPoints.length - 1].getPrecipitation();
        }
        
        System.out.printf("The most occurring data point is %.2f\n", mode);
    }
    
    // Most occurring month
    public void modeMonth(){
        DataSorting.mergeSort(dataPoints, "month");
        int mode = dataPoints[0].getMonth();
        int maxCount = 0;
        int currentCount = 0;
        
        // Iterate through the sorted dataPoints and count occurrence of data
        for (int i = 1; i < dataPoints.length; i++){
            if (dataPoints[i].getMonth() == dataPoints[i - 1].getMonth()){
                currentCount ++;
            }
            else{
                if (currentCount > maxCount){
                    maxCount = currentCount;
                    mode = dataPoints[i - 1].getMonth();
                }
                currentCount = 1;
            }
        }
        
        // Check for the final element
        if (currentCount > maxCount){
            mode = dataPoints[dataPoints.length - 1].getMonth();
        }
        
        System.out.println("The most occurring month is month " + mode + ".");
    }
    
    // Most occurring year
    public void modeYear(){
        DataSorting.mergeSort(dataPoints, "year");
        int mode = dataPoints[0].getYear();
        int maxCount = 0;
        int currentCount = 0;
        
        // Iterate through the sorted dataPoints and count occurrence of data
        for (int i = 1; i < dataPoints.length; i++){
            if (dataPoints[i].getYear() == dataPoints[i - 1].getYear()){
                currentCount ++;
            }
            else{
                if (currentCount > maxCount){
                    maxCount = currentCount;
                    mode = dataPoints[i - 1].getYear();
                }
                currentCount = 1;
            }
        }

        // Check for the final element
        if (currentCount > maxCount){
            mode = dataPoints[dataPoints.length - 1].getYear();
        }

        System.out.println("The most occurring year is " + mode + ".");
    }
    
    // Most occurring region
    public void modeRegion(){
        DataSorting.mergeSort(dataPoints, "region");
        String mode = dataPoints[0].getRegion();
        int maxCount = 0;
        int currentCount = 0;
        
        // Iterate through the sorted dataPoints and count occurrence of data
        for (int i = 1; i < dataPoints.length; i++){
            if (dataPoints[i].getRegion().equals(dataPoints[i - 1].getRegion())){
                currentCount ++;
            }
            else{
                if (currentCount > maxCount){
                    maxCount = currentCount;
                    mode = dataPoints[i - 1].getRegion();
                }
                currentCount = 1;
            }
        }
        
        // Check for the final element
        if (currentCount > maxCount){
            mode = dataPoints[dataPoints.length - 1].getRegion();
        }
        
        System.out.println("The most occurring region is " + mode + ".");
    }
    
    // Least Data
    public void leastData(){
        DataSorting.mergeSort(dataPoints, "temperature");
        System.out.println(dataPoints[0].getRegion() + " has the least temperature of " + 
        dataPoints[0].getTemperature() + " in month " + dataPoints[0].getMonth() + 
        " in the year " + dataPoints[0].getYear() + ".");
    }
    // Highest Data
    public void highestData(){
        DataSorting.mergeSort(dataPoints, "temperature");
        System.out.println(dataPoints[dataPoints.length - 1].getRegion() + " has the highest temperature of " + 
        dataPoints[dataPoints.length - 1].getTemperature() + " in month " + dataPoints[dataPoints.length - 1].getMonth() + 
        " in the year " + dataPoints[dataPoints.length - 1].getYear() + ".");
    }

    // Median temperature
    public void median(){
        double median;
        int length = dataPoints.length;
        // For even number of datapoints
        if (length % 2 == 0){
            median = (dataPoints[length / 2 - 1].getTemperature() + 
            dataPoints[length / 2].getTemperature()) / 2.0;
        }
        else{
            median = dataPoints[length / 2].getTemperature();
        }
        System.out.printf("The median data is %.2f\n", median);
    }

    // Standard deviation
    public void stdDeviation(){
        double mean = meanData();
        double sumSquares = 0;

        // calculating the sum of squares
        for (ClimateRecord datapoint : dataPoints){
            double deviation = datapoint.getTemperature() - mean;
            sumSquares += deviation * deviation;
        }

        double variance = sumSquares / dataPoints.length;
        double stdDeviation = Math.sqrt(variance);
        System.out.printf("The standard deviation is %.2f\n", stdDeviation);

    }
    
    
    // public void generateSummary(){
        
    //     System.out.println("=======================================================================");
    //     meanData();
    //     modeData();
    //     modeMonth();
    //     modeYear();
    //     modeRegion();
    //     leastData();
    //     highestData();
    //     median();
    //     stdDeviation();
    //     System.out.println("=======================================================================");
    // }

    
        
}
