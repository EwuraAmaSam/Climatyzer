import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.text.DecimalFormat;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
/**
 * 
 */

public class ClimateDataActionGUI extends JFrame {
    private ClimateRecord[] records;
    private JFrame previousFrame;

    public ClimateDataActionGUI(ClimateRecord[] records, JFrame previousFrame, LinkedList averages) {
        this.records = records;
        this.previousFrame = previousFrame;
        setTitle("Climatyzer");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(240, 240, 240));

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel summaryPanel = new JPanel(new GridBagLayout());
        summaryPanel.setBackground(Color.WHITE);
        summaryPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        JPanel tempPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int padding = 50;
                int labelPadding = 25;
                int width = getWidth() - 2 * padding;
                int height = getHeight() - 2 * padding;

                // Draw axis
                g2.drawLine(padding, getHeight() - padding, padding, padding);
                g2.drawLine(padding, getHeight() - padding, getWidth() - padding, getHeight() - padding);

                // Find min and max values
                int minYear = Integer.MAX_VALUE;
                int maxYear = Integer.MIN_VALUE;
                double minTemp = Double.MAX_VALUE;
                double maxTemp = Double.MIN_VALUE;

                SearchNode h = averages.head;
                while (h != null) {
                    minYear = Math.min(minYear, h.data.getYear());
                    maxYear = Math.max(maxYear, h.data.getYear());
                    minTemp = Math.min(minTemp, h.data.getTemperature());
                    maxTemp = Math.max(maxTemp, h.data.getTemperature());
                    h = h.next;
                }

                // Draw lines and points
                g2.setPaint(Color.RED);
                int xPrev = 0;
                int yPrev = 0;
                boolean firstPoint = true;

                h = averages.head;
                while (h != null) {
                    int x = padding + (int) (width * (h.data.getYear() - minYear) / (double) (maxYear - minYear));
                    int y = getHeight() - padding - (int) (height * (h.data.getTemperature() - minTemp) / (maxTemp - minTemp));

                    // Draw point
                    g2.fill(new Ellipse2D.Double(x - 2, y - 2, 4, 4));

                    // Draw line to connect points
                    if (!firstPoint) {
                        g2.drawLine(xPrev, yPrev, x, y);
                    } else {
                        firstPoint = false;
                    }

                    xPrev = x;
                    yPrev = y;
                    h = h.next;
                }

                // Draw unique labels for years
                g2.setPaint(Color.BLACK);
                int[] drawnYears = new int[maxYear - minYear + 1]; // Track drawn years
                h = averages.head;
                while (h != null) {
                    int year = h.data.getYear();
                    if (drawnYears[year - minYear] == 0) {
                        int x = padding + (int) (width * (year - minYear) / (double) (maxYear - minYear));
                        g2.drawString(Integer.toString(year), x - 10, getHeight() - padding + labelPadding);
                        drawnYears[year - minYear] = 1;
                    }
                    h = h.next;
                }

                // Draw temperature labels
                for (int i = 0; i <= 5; i++) {
                    double temp = minTemp + (maxTemp - minTemp) * i / 5;
                    int y = getHeight() - padding - height * i / 5;
                    g2.drawString(String.format("%.1f", temp), padding - labelPadding, y + 5);
                }

                // Draw title
                g2.drawString("Temperature over Years", getWidth() / 2 - 50, 20);
            }
        };
        tempPanel.setPreferredSize(new Dimension(800, 600));

        JPanel precipPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int padding = 50;
                int labelPadding = 25;
                int width = getWidth() - 2 * padding;
                int height = getHeight() - 2 * padding;

                // Draw axis
                g2.drawLine(padding, getHeight() - padding, padding, padding);
                g2.drawLine(padding, getHeight() - padding, getWidth() - padding, getHeight() - padding);

                // Find min and max values
                int minYear = Integer.MAX_VALUE;
                int maxYear = Integer.MIN_VALUE;
                double minPrecip = Double.MAX_VALUE;
                double maxPrecip = Double.MIN_VALUE;

                SearchNode h = averages.head;
                while (h != null) {
                    minYear = Math.min(minYear, h.data.getYear());
                    maxYear = Math.max(maxYear, h.data.getYear());
                    minPrecip = Math.min(minPrecip, h.data.getPrecipitation());
                    maxPrecip = Math.max(maxPrecip, h.data.getPrecipitation());
                    h = h.next;
                }

                // Draw lines and points
                g2.setPaint(Color.BLUE);
                int xPrev = 0;
                int yPrev = 0;
                boolean firstPoint = true;

                h = averages.head;
                while (h != null) {
                    int x = padding + (int) (width * (h.data.getYear() - minYear) / (double) (maxYear - minYear));
                    int y = getHeight() - padding - (int) (height * (h.data.getPrecipitation() - minPrecip) / (maxPrecip - minPrecip));

                    // Draw point
                    g2.fill(new Ellipse2D.Double(x - 2, y - 2, 4, 4));

                    // Draw line to connect points
                    if (!firstPoint) {
                        g2.drawLine(xPrev, yPrev, x, y);
                    } else {
                        firstPoint = false;
                    }

                    xPrev = x;
                    yPrev = y;
                    h = h.next;
                }

                // Draw unique labels for years
                g2.setPaint(Color.BLACK);
                int[] drawnYears = new int[maxYear - minYear + 1]; // Track drawn years
                h = averages.head;
                while (h != null) {
                    int year = h.data.getYear();
                    if (drawnYears[year - minYear] == 0) {
                        int x = padding + (int) (width * (year - minYear) / (double) (maxYear - minYear));
                        g2.drawString(Integer.toString(year), x - 10, getHeight() - padding + labelPadding);
                        drawnYears[year - minYear] = 1;
                    }
                    h = h.next;
                }

                // Draw precipitation labels
                for (int i = 0; i <= 5; i++) {
                    double precip = minPrecip + (maxPrecip - minPrecip) * i / 5;
                    int y = getHeight() - padding - height * i / 5;
                    g2.drawString(String.format("%.1f", precip), padding - labelPadding, y + 5);
                }

                // Draw title
                g2.drawString("Precipitation over Years", getWidth() / 2 - 50, 20);
            }
        };
        precipPanel.setPreferredSize(new Dimension(800, 600));

        container.add(summaryPanel);
        container.add(tempPanel);
        container.add(precipPanel);

        JScrollPane scrollPane = new JScrollPane(container);
        scrollPane.setBorder(null);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = createStyledButton("Back to Main Menu");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(240, 240, 240));
        buttonPanel.add(backButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        backButton.addActionListener(e -> {
            previousFrame.setVisible(true);
            dispose();
        });

        generateSummary(summaryPanel);
    }
    
        /**
         * Creates Buttons 
         * @param text the texts  on the button 
         * @return
         */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        return button;
    }
    /**
     * This method is for generating summarries
     * @param panel this is the window that displays the summary generated
     */

    public void generateSummary(JPanel panel) {
        Summary summary = new Summary(records);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 20, 0);

        JLabel titleLabel = new JLabel("Summary of Climate Data");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 0, 5, 0);

        addSummaryRow(panel, gbc, "Mean Temperature", summary.meanData());
        addSummaryRow(panel, gbc, "Mean Precipitation", summary.meanDataP());
        addSummaryRow(panel, gbc, "Mode Temperature", captureConsoleOutput(summary::modeData));
        addSummaryRow(panel, gbc, "Mode Precipitation", captureConsoleOutput(summary::modeDataP));
        addSummaryRow(panel, gbc, "Mode Month", captureConsoleOutput(summary::modeMonth));
        addSummaryRow(panel, gbc, "Mode Year", captureConsoleOutput(summary::modeYear));
        addSummaryRow(panel, gbc, "Mode Region", captureConsoleOutput(summary::modeRegion));
        addSummaryRow(panel, gbc, "Least Temperature", captureConsoleOutput(summary::leastData));
        addSummaryRow(panel, gbc, "Highest Temperature", captureConsoleOutput(summary::highestData));
        addSummaryRow(panel, gbc, "Median Temperature", captureConsoleOutput(summary::median));
        addSummaryRow(panel, gbc, "Standard Deviation", captureConsoleOutput(summary::stdDeviation));
    }
    /**
     * This is the space for the summary row ,formated the fonts and the label of the key
     * @param panel this is the window
     * @param gbc  this gives the contstaint 
     * @param label  the label we want 
     * @param value the value the key is taking
     */

    private void addSummaryRow(JPanel panel, GridBagConstraints gbc, String label, Object value) {
        JLabel keyLabel = new JLabel(label + ":");
        keyLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(keyLabel, gbc);

        JLabel valueLabel = new JLabel(formatValue(value));
        valueLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(valueLabel, gbc);
    }
    /**
     * 
     * This is o formate the value of the data being added to the GUI
     * @param value
     * @return
     */

    private String formatValue(Object value) {
        if (value instanceof Double) {
            DecimalFormat df = new DecimalFormat("#.##");
            return df.format(value);
        } else if (value instanceof String) {
            try {
                double doubleValue = Double.parseDouble((String) value);
                DecimalFormat df = new DecimalFormat("#.##");
                return df.format(doubleValue);
            } catch (NumberFormatException e) {
                return (String) value;
            }
        }
        return String.valueOf(value);
    }

    /**
     * This is for displaying information in the console
     * @param method
     * @return
     */

    private String captureConsoleOutput(Runnable method) {
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        java.io.PrintStream old = System.out;
        System.setOut(new java.io.PrintStream(baos));
        method.run();
        System.out.flush();
        System.setOut(old);
        return baos.toString().trim();
    }
}