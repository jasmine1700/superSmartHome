

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;


/**
 * ª≠’€œﬂÕº O_LineChart(String applicationTitle, String title, String xTitle, String yTitle, String lineName, List<Object> x, List<Object> y)
 * 
 * @author imssbora
 * @version 1.0
 * @author Xinyao Yu
 * @version 2.4
 */
public class O_LineChart extends JFrame {

  private static final long serialVersionUID = 1L;

  public O_LineChart(String applicationTitle, String title, String xTitle, String yTitle, List<String> lineName, List<List<String>> x, List<List<Double>> y) {
    super(applicationTitle);
    // Create dataset
    DefaultCategoryDataset dataset = createDataset(lineName, x, y);
    // Create chart
    JFreeChart chart = ChartFactory.createLineChart(
    		title, // Chart title
    		xTitle, // X-Axis Label
    		yTitle, // Y-Axis Label
    		dataset,
    		PlotOrientation.VERTICAL,
    		true,//legend
    		true,//tooltip, must be true!!!!!!!!
    		false//URL
        );

    ChartPanel panel = new ChartPanel(chart);
    setContentPane(panel);
    this.addValue(chart);
    
    this.setAlwaysOnTop(true);
    this.pack();
    this.setSize(600, 400);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.setVisible(true);
   
  }

  private DefaultCategoryDataset createDataset(List<String> lineName, List<List<String>> x, List<List<Double>> y) {
	  DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	  for(int i = 0; i < lineName.size(); i++) {
		  for(int j = 0; j < x.get(i).size(); j++)
		  dataset.addValue(y.get(i).get(j), lineName.get(i), x.get(i).get(j));
	  }
	return dataset;
  }

  @SuppressWarnings("deprecation")
private void addValue(JFreeChart chart) {
	//Create the chart object for the type Line Chart.
	  CategoryPlot plot = chart.getCategoryPlot();

	//Create of the renderer object to the draw point and the value
	  LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
	  renderer.setShapesVisible(true);

	//Define the format to the value to the draw
	  //DecimalFormat decimalformat1 = new DecimalFormat("##");//"{2}", decimalformat1
	  renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());

	  renderer.setItemLabelsVisible(true);
	  renderer.setSeriesVisible(true);
  }

}
