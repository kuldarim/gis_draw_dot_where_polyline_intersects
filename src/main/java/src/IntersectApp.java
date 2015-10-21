/* Copyright 2014 Esri

All rights reserved under the copyright laws of the United States
and applicable international laws, treaties, and conventions.

You may freely redistribute and use this sample code, with or
without modification, provided you include the original copyright
notice and use restrictions.

See the use restrictions.*/
package main.java.src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;

import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Polyline;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.map.ArcGISTiledMapServiceLayer;
import com.esri.map.GraphicsLayer;
import com.esri.map.JMap;
import com.esri.map.LayerList;
import com.esri.map.MapEvent;
import com.esri.map.MapEventListenerAdapter;

/**
 * This sample shows how to add different kinds of graphics to a graphics layer.
 */
public class IntersectApp {

  private JMap map;

  // Constructor
  public IntersectApp() { }

  // ------------------------------------------------------------------------
  // Core functionality
  // ------------------------------------------------------------------------
  /**
   * Creates the map, adds a tiled basemap and a graphics layer and adds 
   * graphics to this graphics layer when the 'map ready' event fires, that is, 
   * when the map's spatial reference has been set by the basemap.
   * @return
   */
  private JMap createMap() {

    JMap jMap = new JMap();
    // set to default extent
    jMap.setExtent(new Envelope(-15.8, -37.8, 156.8, 77.3));

    // add base layer
    ArcGISTiledMapServiceLayer tiledLayer = new ArcGISTiledMapServiceLayer(
        "http://services.arcgisonline.com/ArcGIS/rest/services/NGS_Topo_US_2D/MapServer");
    LayerList layers = jMap.getLayers();
    layers.add(tiledLayer);

    // create the graphics layer
    final GraphicsLayer graphicsLayer = new GraphicsLayer();
    jMap.addMapEventListener(new MapEventListenerAdapter() {
      @Override
      public void mapReady(final MapEvent arg0) {
        SwingUtilities.invokeLater(new Runnable() {
          @Override
          public void run() {
            addSimpleLineGraphics(graphicsLayer);
          }
        });
      }
    });

    layers.add(graphicsLayer);

    return jMap;
  }

  /**
   * Adds graphics symbolized with SimpleLineSymbols.
   * @param graphicsLayer
   */
  private void addSimpleLineGraphics(GraphicsLayer graphicsLayer) {
    Polyline polyline = new Polyline();
    polyline.startPath(118.169, 34.016);
    polyline.lineTo(104.941, 39.7072);
    polyline.lineTo(96.724, 32.732);

    SimpleLineSymbol symbol = new SimpleLineSymbol(Color.MAGENTA, 4);
    symbol.setStyle(SimpleLineSymbol.Style.SOLID);

    Graphic graphic = new Graphic(polyline, symbol);
    graphicsLayer.addGraphic(graphic);
    
    Polyline polyline2 = new Polyline();
    polyline2.startPath(150.169, 34.016);
    polyline2.lineTo(90.941, 39.7072);

    SimpleLineSymbol symbol2 = new SimpleLineSymbol(Color.BLACK, 4);
    symbol2.setStyle(SimpleLineSymbol.Style.SOLID);

    Graphic graphic2 = new Graphic(polyline2, symbol2);
    graphicsLayer.addGraphic(graphic2);
  }

  
  // ------------------------------------------------------------------------
  // Static methods
  // ------------------------------------------------------------------------
  /**
   * Starting point of this application.
   * 
   * @param args
   *            arguments to this application.
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        try {
          // instance of this application
        	IntersectApp addGraphicsApp = new IntersectApp();

          // create the UI, including the map, for the application.
          JFrame appWindow = addGraphicsApp.createWindow();
          appWindow.add(addGraphicsApp.createUI());
          appWindow.setVisible(true);
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    });
  }

  // ------------------------------------------------------------------------
  // Public methods
  // ------------------------------------------------------------------------
  /**
   * Creates and displays the UI, including the map, for this application.
   * 
   * @return the UI component.
   */
  public JComponent createUI() {
    // application content
    JComponent contentPane = createContentPane();

    // map
    map = createMap();

    contentPane.add(map);

    return contentPane;
  }

  // ------------------------------------------------------------------------
  // Private methods
  // ------------------------------------------------------------------------
  /**
   * Creates a content pane.
   * 
   * @return a content pane.
   */
  private static JLayeredPane createContentPane() {
    JLayeredPane contentPane = new JLayeredPane();
    contentPane.setBounds(100, 100, 1000, 700);
    contentPane.setLayout(new BorderLayout(0, 0));
    contentPane.setVisible(true);
    return contentPane;
  }

  /**
   * Creates a window.
   * 
   * @return a window.
   */
  private JFrame createWindow() {
    JFrame window = new JFrame("Add Graphics Application");
    window.setBounds(100, 100, 1000, 700);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.getContentPane().setLayout(new BorderLayout(0, 0));
    window.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent windowEvent) {
        super.windowClosing(windowEvent);
        map.dispose();
      }
    });
    return window;
  }
}