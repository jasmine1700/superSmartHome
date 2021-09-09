

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;


/**
 * @author vogella
 * @version 1.0
 * @author Aria
 * @version 2.0
 */
public class O_PieChart extends JFrame {

    private static final long serialVersionUID = 1L;

    public O_PieChart(String applicationTitle, String chartTitle, List<String> type, List<Double> number) {
        super(applicationTitle);
        // This will create the dataset
        PieDataset dataset = createDataset(type, number);
        // based on the dataset we create the chart
        JFreeChart chart = createChart(dataset, chartTitle);
        // we put the chart into a panel
        ChartPanel chartPanel = new ChartPanel(chart);
        // default size
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        // add it to our application
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(chartPanel);
        
        this.pack();
        this.setVisible(true);
    }

    /**
     * Creates a sample dataset
     */
    private  PieDataset createDataset(List<String> type, List<Double> number) {
        DefaultPieDataset result = new DefaultPieDataset();
        double total = 0;
        for(int i = 0; i < number.size(); i++) {
        	total += number.get(i);
        }
        for(int i = 0; i < type.size(); i++) {
        	double percentage = number.get(i).doubleValue()/total;
        	int percent = (int)(percentage*100);
        	result.setValue(type.get(i) + " : " + percent + "%", number.get(i));
        }
        
        return result;

    }

    /**
     * Creates a chart
     */
    private JFreeChart createChart(PieDataset dataset, String title) {

        JFreeChart chart = ChartFactory.createPieChart3D(
            title,                  // chart title
            dataset,                // data
            true,                   // include legend
            true,
            false
        );

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        return chart;

    }

}



