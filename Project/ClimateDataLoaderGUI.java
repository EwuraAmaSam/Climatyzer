/**
 * A graphical user interface ,This class loads methods to buttons and includes functionality of 
 * the climate data . It also includes , text areas for information , and text field for typing in information to be serached
 * @author Christine, Daniel, Dave, Ewura Ama
 * @version 1.0
 */

import java.awt.*;
import java.io.File;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ClimateDataLoaderGUI extends JFrame {
    private ClimateRecord[] records;
    private JTable dataTable;
    private DefaultTableModel tableModel;
    private File file;

        /**
     * A consturctor of the class that initialises and
     * displays our user interface
     */
    public ClimateDataLoaderGUI() {
        setTitle("Climate Data Loader");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(240, 240, 240)); // Light gray background

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create table model and table
        tableModel = new DefaultTableModel(new String[]{"Region","Year","Month","Temperature", "Precipitation"}, 0);
        dataTable = new JTable(tableModel);
        dataTable.setFillsViewportHeight(true);
        dataTable.setBackground(Color.WHITE);
        dataTable.setGridColor(new Color(200, 200, 200));
        dataTable.setRowHeight(25);
        dataTable.getTableHeader().setBackground(new Color(100, 149, 237)); // Cornflower blue
        dataTable.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(dataTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(240, 240, 240));

        JButton loadButton = createStyledButton("Load Data");
        JButton sortButton = createStyledButton("Sort Data");
        JButton searchButton = createStyledButton("Search Data");
        JButton generateButton = createStyledButton("Generate Summary");

        buttonPanel.add(loadButton);
        buttonPanel.add(sortButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(generateButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

        loadButton.addActionListener(e -> loadData());
        sortButton.addActionListener(e -> sortData());
        searchButton.addActionListener(e -> searchData());
        generateButton.addActionListener(e -> generateSummary());
    }

       /**
     * A method to check if data is loaded or not
     * It returns a boolean indicating if data is loaded
     * @return {@code true} if data is loaded and {@code false} otherwise
     * 
     */
    private boolean checkDataLoaded() {
        if (records == null || records.length == 0) {
            JOptionPane.showMessageDialog(this, "Please load data first.", "Data Not Loaded", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

       /**
     * This method creates buttons and write text on it
     * @param text  action  that the button is doing
     * @return
     */

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(70, 130, 180)); // Steel blue
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        return button;
    }

    private void loadData() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
           
                FileLoader fileLoad = new FileLoader(file);
                records = fileLoad.loadData();
                displayData();
          
        }
    }

    private void displayData() {
        tableModel.setRowCount(0);
        for (ClimateRecord record : records) {
            tableModel.addRow(new Object[]{
                record.getRegion(),
                record.getYear(),
                record.getMonth(),
                record.getTemperature(),
                record.getPrecipitation()
                
                
                
            });
        }
    }
 /**
     * Sorted Data for  sorting the data accordingly
     * 
     */

    private void sortData() {
        String[] options = { "Region","Year","Month","Temperature", "Precipitation"};
        String key = (String) JOptionPane.showInputDialog(this,
            "Choose the key to sort by:",
            "Sort Data",
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);

        if (key != null) {
            DataSorting.mergeSort(records, key.toLowerCase());
            displayData();
        }
    }


     /**
     * This method organizes the data based on user desire
     */
    private void searchData() {
        JPanel searchPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField regionField = new JTextField();
        JTextField temperatureField = new JTextField();
        JTextField monthField = new JTextField();
        JTextField yearField = new JTextField();
        JComboBox<String> allowDuplicatesBox = new JComboBox<>(new String[]{"True", "False"});

        searchPanel.add(new JLabel("Region:"));
        searchPanel.add(regionField);
        searchPanel.add(new JLabel("Temperature:"));
        searchPanel.add(temperatureField);
        searchPanel.add(new JLabel("Month:"));
        searchPanel.add(monthField);
        searchPanel.add(new JLabel("Year:"));
        searchPanel.add(yearField);
        searchPanel.add(new JLabel("Allow Duplicates:"));
        searchPanel.add(allowDuplicatesBox);

        int result = JOptionPane.showConfirmDialog(this, searchPanel, "Search Data", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String region = regionField.getText().trim();
            String temperature = temperatureField.getText().trim();
            String month = monthField.getText().trim();
            String year = yearField.getText().trim();
            boolean allowDuplicates = allowDuplicatesBox.getSelectedItem().equals("True");

            searchAndDisplayData(region, temperature, month, year, allowDuplicates);
        }
    }
    /**
     * Displays all the parameters that have been displayed
     * @param region the place for data
     * @param temperature the temperature of the place
     * @param precipitation the precipitation of the temperature
     * @param month the month in numbers 
     * @param year the year of occurrence
     * @param allowDuplicates if user want to allow duplicates
     */

    private void searchAndDisplayData(String region, String temperature, String month, String year, boolean allowDuplicates) {
        tableModel.setRowCount(0);

        Double temp = temperature.isEmpty() ? null : Double.parseDouble(temperature);
        Double precip = null; // Assuming precipitation is not used in this method
        Integer mon = month.isEmpty() ? null : Integer.parseInt(month);
        Integer yr = year.isEmpty() ? null : Integer.parseInt(year);
        String reg = region.isEmpty() ? null : region;

        BinarySearch binarySearch = new BinarySearch();
        LinkedList result = binarySearch.searchEntry(records, temp, precip, mon, yr, reg, allowDuplicates);

        if (result == null || result.size == 0) {
            JOptionPane.showMessageDialog(this, "No records found matching the search criteria. Please try a different search.", "No Records Found", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        SearchNode h = result.head;
        while(h!=null) {
            tableModel.addRow(new Object[]{
                    h.data.getRegion(),
                    h.data.getYear(),
                    h.data.getMonth(),
                    h.data.getTemperature(),
                    h.data.getPrecipitation()
            });
            h=h.next;
        }
    }

/**
  * this method generates summary when the button is displayed
  */
    private void generateSummary() {
        DataSorting.mergeSort(records,"year");

        LinkedList averages = getYearlyAverages(records);

        ClimateDataActionGUI summaryWindow = new ClimateDataActionGUI(records, this,averages);
        summaryWindow.setVisible(true);
        this.setVisible(false);
    }

     /**
     * This method is for the creation of the graph for displaying the data and climate events
     * 
     * 
     */

    public static LinkedList getYearlyAverages(ClimateRecord[] data){
        LinkedList averages = new LinkedList();
        int currYear=data[0].getYear();
        double totalTemp=0;
        double totalPrecip=0;
        int numEntries=0;
        for(ClimateRecord rec: data){
            if(currYear==rec.getYear()){
                totalTemp+=rec.getTemperature();
                totalPrecip+=rec.getPrecipitation();
                numEntries++;
            }
            else{
                ClimateRecord entry = new ClimateRecord(null,currYear,0,totalTemp/numEntries,totalPrecip/numEntries);
                averages.addData(entry);
                currYear=rec.getYear();
                totalTemp=0; totalTemp+=rec.getTemperature();
                totalPrecip=0; totalPrecip+=rec.getPrecipitation();
                numEntries=0; numEntries++;
            }
        }
        ClimateRecord entry = new ClimateRecord(null,currYear,0,totalTemp/numEntries,totalPrecip/numEntries);
        averages.addData(entry);
        return averages;

    }
 /**
     * This is the main method that is being used
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ClimateDataLoaderGUI frame = new ClimateDataLoaderGUI();
            frame.setVisible(true);
        });
    }
}

