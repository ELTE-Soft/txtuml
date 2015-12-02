package hu.elte.txtuml.export.papyrus.elementsarrangers.txtumllayout.tests;

import hu.elte.txtuml.export.papyrus.elementsarrangers.txtumllayout.LayoutTransformer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.junit.Assert;
import org.junit.Test;

public class LayoutTransformerTest {
	
	@Test
	public void testGapX(){
		LayoutTransformer layoutTransformer = new LayoutTransformer(1, 1, 2, 0, 0);

		Map<String, Rectangle> objects = new HashMap<String, Rectangle>();
		Map<String, List<Point>> connections = new HashMap<String, List<Point>>();
		
		objects.put("O", new Rectangle(0,0,1,1));
		objects.put("A", new Rectangle(1,1,1,1));
		objects.put("B", new Rectangle(2,2,1,1));
		connections.put("A_B", Arrays.asList(new Point(1,1), new Point(2,2)));
		
		layoutTransformer.doTranformations(objects, connections);
		
		Assert.assertEquals(1, objects.get("O").x());
		Assert.assertEquals(0, objects.get("O").y());
		Assert.assertEquals(4, objects.get("A").x());
		Assert.assertEquals(1, objects.get("A").y());
		Assert.assertEquals(7, objects.get("B").x());
		Assert.assertEquals(2, objects.get("B").y());
		
		Assert.assertEquals(3, connections.get("A_B").get(0).x());
		Assert.assertEquals(1, connections.get("A_B").get(0).y());
		Assert.assertEquals(6, connections.get("A_B").get(1).x());
		Assert.assertEquals(2, connections.get("A_B").get(1).y());
	}
	
	@Test
	public void testGapY(){
		LayoutTransformer layoutTransformer = new LayoutTransformer(1, 1, 0, 2,0);

		Map<String, Rectangle> objects = new HashMap<String, Rectangle>();
		Map<String, List<Point>> connections = new HashMap<String, List<Point>>();
		
		objects.put("O", new Rectangle(0,0,1,1));
		objects.put("A", new Rectangle(1,1,1,1));
		objects.put("B", new Rectangle(2,2,1,1));
		connections.put("A_B", Arrays.asList(new Point(1,1), new Point(2,2)));
		
		layoutTransformer.doTranformations(objects, connections);
		
		Assert.assertEquals(0, objects.get("O").x());
		Assert.assertEquals(1, objects.get("O").y());
		Assert.assertEquals(1, objects.get("A").x());
		Assert.assertEquals(4, objects.get("A").y());
		Assert.assertEquals(2, objects.get("B").x());
		Assert.assertEquals(7, objects.get("B").y());
		
		Assert.assertEquals(1, connections.get("A_B").get(0).x());
		Assert.assertEquals(3, connections.get("A_B").get(0).y());
		Assert.assertEquals(2, connections.get("A_B").get(1).x());
		Assert.assertEquals(6, connections.get("A_B").get(1).y());
	}

	@Test
	public void testFlipXAxis() {
		LayoutTransformer layoutTransformer = new LayoutTransformer(1, 1, 0, 0, 0);
		layoutTransformer.flipXAxis();
		
		Map<String, Rectangle> objects = new HashMap<String, Rectangle>();
		Map<String, List<Point>> connections = new HashMap<String, List<Point>>();
		
		objects.put("A", new Rectangle(1,1,1,1));
		objects.put("B", new Rectangle(2,2,1,1));
		connections.put("A_B", Arrays.asList(new Point(1,1), new Point(2,2)));
		
		layoutTransformer.doTranformations(objects, connections);
		
		Assert.assertEquals(-1, objects.get("A").x());
		Assert.assertEquals(1, objects.get("A").y());
		Assert.assertEquals(-2, objects.get("B").x());
		Assert.assertEquals(2, objects.get("B").y());
		
		Assert.assertEquals(-1, connections.get("A_B").get(0).x());
		Assert.assertEquals(1, connections.get("A_B").get(0).y());
		Assert.assertEquals(-2, connections.get("A_B").get(1).x());
		Assert.assertEquals(2, connections.get("A_B").get(1).y());
	}

	@Test
	public void testFlipYAxis() {
		LayoutTransformer layoutTransformer = new LayoutTransformer(1, 1,0, 0, 0);
		layoutTransformer.flipYAxis();
		
		Map<String, Rectangle> objects = new HashMap<String, Rectangle>();
		Map<String, List<Point>> connections = new HashMap<String, List<Point>>();
		
		objects.put("A", new Rectangle(1,1,1,1));
		objects.put("B", new Rectangle(2,2,1,1));
		connections.put("A_B", Arrays.asList(new Point(1,1), new Point(2,2)));
		
		layoutTransformer.doTranformations(objects, connections);
		
		Assert.assertEquals(1, objects.get("A").x());
		Assert.assertEquals(-1, objects.get("A").y());
		Assert.assertEquals(2, objects.get("B").x());
		Assert.assertEquals(-2, objects.get("B").y());
		
		Assert.assertEquals(1, connections.get("A_B").get(0).x());
		Assert.assertEquals(-1, connections.get("A_B").get(0).y());
		Assert.assertEquals(2, connections.get("A_B").get(1).x());
		Assert.assertEquals(-2, connections.get("A_B").get(1).y());
	}

	@Test
	public void testSetOrigoToUpperLeft() {
		LayoutTransformer layoutTransformer = new LayoutTransformer(1, 1, 0, 0, 0);
		layoutTransformer.setOrigo(LayoutTransformer.OrigoConstraint.UpperLeft);
		
		Map<String, Rectangle> objects = new HashMap<String, Rectangle>();
		Map<String, List<Point>> connections = new HashMap<String, List<Point>>();
		
		objects.put("A", new Rectangle(-1,1,1,1));
		objects.put("B", new Rectangle(1,1,1,1));
		objects.put("C", new Rectangle(1,-1,1,1));
		objects.put("D", new Rectangle(-1,-1,1,1));
		
		connections.put("A_B", Arrays.asList(new Point(-1,1), new Point(1,1)));
		connections.put("B_C", Arrays.asList(new Point(1,1), new Point(1,-1)));
		connections.put("C_D", Arrays.asList(new Point(1,-1), new Point(-1,-1)));
		connections.put("D_A", Arrays.asList(new Point(-1,-1), new Point(-1,1)));
		
		layoutTransformer.doTranformations(objects, connections);
		
		Assert.assertEquals(0, objects.get("A").x());
		Assert.assertEquals(0, objects.get("A").y());
		Assert.assertEquals(2, objects.get("B").x());
		Assert.assertEquals(0, objects.get("B").y());
		Assert.assertEquals(2, objects.get("C").x());
		Assert.assertEquals(-2, objects.get("C").y());
		Assert.assertEquals(0, objects.get("D").x());
		Assert.assertEquals(-2, objects.get("D").y());
		
		Assert.assertEquals(0, connections.get("A_B").get(0).x());
		Assert.assertEquals(0, connections.get("A_B").get(0).y());
		Assert.assertEquals(2, connections.get("A_B").get(1).x());
		Assert.assertEquals(0, connections.get("A_B").get(1).y());
		
		Assert.assertEquals(2, connections.get("B_C").get(0).x());
		Assert.assertEquals(0, connections.get("B_C").get(0).y());
		Assert.assertEquals(2, connections.get("B_C").get(1).x());
		Assert.assertEquals(-2, connections.get("B_C").get(1).y());
		
		Assert.assertEquals(2, connections.get("C_D").get(0).x());
		Assert.assertEquals(-2, connections.get("C_D").get(0).y());
		Assert.assertEquals(0, connections.get("C_D").get(1).x());
		Assert.assertEquals(-2, connections.get("C_D").get(1).y());
		
		Assert.assertEquals(0, connections.get("D_A").get(0).x());
		Assert.assertEquals(-2, connections.get("D_A").get(0).y());
		Assert.assertEquals(0, connections.get("D_A").get(1).x());
		Assert.assertEquals(0, connections.get("D_A").get(1).y());
	}
	
	@Test
	public void testSetOrigoToUpperRight() {
		LayoutTransformer layoutTransformer = new LayoutTransformer(1, 1, 0, 0, 0);
		layoutTransformer.setOrigo(LayoutTransformer.OrigoConstraint.UpperRight);
		
		Map<String, Rectangle> objects = new HashMap<String, Rectangle>();
		Map<String, List<Point>> connections = new HashMap<String, List<Point>>();
		
		objects.put("A", new Rectangle(-1,1,1,1));
		objects.put("B", new Rectangle(1,1,1,1));
		objects.put("C", new Rectangle(1,-1,1,1));
		objects.put("D", new Rectangle(-1,-1,1,1));
		
		connections.put("A_B", Arrays.asList(new Point(-1,1), new Point(1,1)));
		connections.put("B_C", Arrays.asList(new Point(1,1), new Point(1,-1)));
		connections.put("C_D", Arrays.asList(new Point(1,-1), new Point(-1,-1)));
		connections.put("D_A", Arrays.asList(new Point(-1,-1), new Point(-1,1)));
		
		layoutTransformer.doTranformations(objects, connections);
		
		Assert.assertEquals(-2, objects.get("A").x());
		Assert.assertEquals(0, objects.get("A").y());
		Assert.assertEquals(0, objects.get("B").x());
		Assert.assertEquals(0, objects.get("B").y());
		Assert.assertEquals(0, objects.get("C").x());
		Assert.assertEquals(-2, objects.get("C").y());
		Assert.assertEquals(-2, objects.get("D").x());
		Assert.assertEquals(-2, objects.get("D").y());
		
		Assert.assertEquals(-2, connections.get("A_B").get(0).x());
		Assert.assertEquals(0, connections.get("A_B").get(0).y());
		Assert.assertEquals(0, connections.get("A_B").get(1).x());
		Assert.assertEquals(0, connections.get("A_B").get(1).y());
		
		Assert.assertEquals(0, connections.get("B_C").get(0).x());
		Assert.assertEquals(0, connections.get("B_C").get(0).y());
		Assert.assertEquals(0, connections.get("B_C").get(1).x());
		Assert.assertEquals(-2, connections.get("B_C").get(1).y());
		
		Assert.assertEquals(0, connections.get("C_D").get(0).x());
		Assert.assertEquals(-2, connections.get("C_D").get(0).y());
		Assert.assertEquals(-2, connections.get("C_D").get(1).x());
		Assert.assertEquals(-2, connections.get("C_D").get(1).y());
		
		Assert.assertEquals(-2, connections.get("D_A").get(0).x());
		Assert.assertEquals(-2, connections.get("D_A").get(0).y());
		Assert.assertEquals(-2, connections.get("D_A").get(1).x());
		Assert.assertEquals(0, connections.get("D_A").get(1).y());
		
	}
	
	@Test
	public void testSetOrigoToBottomLeft() {
		LayoutTransformer layoutTransformer = new LayoutTransformer(1, 1, 0, 0, 0);
		layoutTransformer.setOrigo(LayoutTransformer.OrigoConstraint.BottomLeft);
		
		Map<String, Rectangle> objects = new HashMap<String, Rectangle>();
		Map<String, List<Point>> connections = new HashMap<String, List<Point>>();
		
		objects.put("A", new Rectangle(-1,1,1,1));
		objects.put("B", new Rectangle(1,1,1,1));
		objects.put("C", new Rectangle(1,-1,1,1));
		objects.put("D", new Rectangle(-1,-1,1,1));
		
		connections.put("A_B", Arrays.asList(new Point(-1,1), new Point(1,1)));
		connections.put("B_C", Arrays.asList(new Point(1,1), new Point(1,-1)));
		connections.put("C_D", Arrays.asList(new Point(1,-1), new Point(-1,-1)));
		connections.put("D_A", Arrays.asList(new Point(-1,-1), new Point(-1,1)));
		
		layoutTransformer.doTranformations(objects, connections);
		
		Assert.assertEquals(0, objects.get("A").x());
		Assert.assertEquals(2, objects.get("A").y());
		Assert.assertEquals(2, objects.get("B").x());
		Assert.assertEquals(2, objects.get("B").y());
		Assert.assertEquals(2, objects.get("C").x());
		Assert.assertEquals(0, objects.get("C").y());
		Assert.assertEquals(0, objects.get("D").x());
		Assert.assertEquals(0, objects.get("D").y());
		
		Assert.assertEquals(0, connections.get("A_B").get(0).x());
		Assert.assertEquals(2, connections.get("A_B").get(0).y());
		Assert.assertEquals(2, connections.get("A_B").get(1).x());
		Assert.assertEquals(2, connections.get("A_B").get(1).y());
		
		Assert.assertEquals(2, connections.get("B_C").get(0).x());
		Assert.assertEquals(2, connections.get("B_C").get(0).y());
		Assert.assertEquals(2, connections.get("B_C").get(1).x());
		Assert.assertEquals(0, connections.get("B_C").get(1).y());
		
		Assert.assertEquals(2, connections.get("C_D").get(0).x());
		Assert.assertEquals(0, connections.get("C_D").get(0).y());
		Assert.assertEquals(0, connections.get("C_D").get(1).x());
		Assert.assertEquals(0, connections.get("C_D").get(1).y());
		
		Assert.assertEquals(0, connections.get("D_A").get(0).x());
		Assert.assertEquals(0, connections.get("D_A").get(0).y());
		Assert.assertEquals(0, connections.get("D_A").get(1).x());
		Assert.assertEquals(2, connections.get("D_A").get(1).y());
	}
	
	@Test
	public void testSetOrigoToBottomRight() {
		LayoutTransformer layoutTransformer = new LayoutTransformer(1, 1, 0 , 0, 0);
		layoutTransformer.setOrigo(LayoutTransformer.OrigoConstraint.BottomRight);
		
		Map<String, Rectangle> objects = new HashMap<String, Rectangle>();
		Map<String, List<Point>> connections = new HashMap<String, List<Point>>();
		
		objects.put("A", new Rectangle(-1,1,1,1));
		objects.put("B", new Rectangle(1,1,1,1));
		objects.put("C", new Rectangle(1,-1,1,1));
		objects.put("D", new Rectangle(-1,-1,1,1));
		
		connections.put("A_B", Arrays.asList(new Point(-1,1), new Point(1,1)));
		connections.put("B_C", Arrays.asList(new Point(1,1), new Point(1,-1)));
		connections.put("C_D", Arrays.asList(new Point(1,-1), new Point(-1,-1)));
		connections.put("D_A", Arrays.asList(new Point(-1,-1), new Point(-1,1)));
		
		layoutTransformer.doTranformations(objects, connections);
		
		Assert.assertEquals(-2, objects.get("A").x());
		Assert.assertEquals(2, objects.get("A").y());
		Assert.assertEquals(0, objects.get("B").x());
		Assert.assertEquals(2, objects.get("B").y());
		Assert.assertEquals(0, objects.get("C").x());
		Assert.assertEquals(0, objects.get("C").y());
		Assert.assertEquals(-2, objects.get("D").x());
		Assert.assertEquals(0, objects.get("D").y());
		
		Assert.assertEquals(-2, connections.get("A_B").get(0).x());
		Assert.assertEquals(2, connections.get("A_B").get(0).y());
		Assert.assertEquals(0, connections.get("A_B").get(1).x());
		Assert.assertEquals(2, connections.get("A_B").get(1).y());
		
		Assert.assertEquals(0, connections.get("B_C").get(0).x());
		Assert.assertEquals(2, connections.get("B_C").get(0).y());
		Assert.assertEquals(0, connections.get("B_C").get(1).x());
		Assert.assertEquals(0, connections.get("B_C").get(1).y());
		
		Assert.assertEquals(0, connections.get("C_D").get(0).x());
		Assert.assertEquals(0, connections.get("C_D").get(0).y());
		Assert.assertEquals(-2, connections.get("C_D").get(1).x());
		Assert.assertEquals(0, connections.get("C_D").get(1).y());
		
		Assert.assertEquals(-2, connections.get("D_A").get(0).x());
		Assert.assertEquals(0, connections.get("D_A").get(0).y());
		Assert.assertEquals(-2, connections.get("D_A").get(1).x());
		Assert.assertEquals(2, connections.get("D_A").get(1).y());
	}
	
	@Test
	public void testScaleX(){
		LayoutTransformer layoutTransformer = new LayoutTransformer(100, 1, 0, 0, 0);

		Map<String, Rectangle> objects = new HashMap<String, Rectangle>();
		Map<String, List<Point>> connections = new HashMap<String, List<Point>>();
		
		objects.put("O", new Rectangle(0,0,1,1));
		objects.put("A", new Rectangle(1,1,1,1));
		objects.put("B", new Rectangle(2,2,1,1));
		connections.put("A_B", Arrays.asList(new Point(1,1), new Point(2,2)));
		
		layoutTransformer.doTranformations(objects, connections);
		
		Assert.assertEquals((int) 49.5, objects.get("O").x());
		Assert.assertEquals(0, objects.get("O").y());
		Assert.assertEquals(1, objects.get("O").width());
		Assert.assertEquals(1, objects.get("O").height());
		
		Assert.assertEquals((int) 149.5, objects.get("A").x());
		Assert.assertEquals(1, objects.get("A").y());
		Assert.assertEquals(1, objects.get("A").width());
		Assert.assertEquals(1, objects.get("A").height());
		
		Assert.assertEquals((int) 249.5, objects.get("B").x());
		Assert.assertEquals(2, objects.get("B").y());
		Assert.assertEquals(1, objects.get("B").width());
		Assert.assertEquals(1, objects.get("B").height());
		
		Assert.assertEquals(100,  connections.get("A_B").get(0).x());
		Assert.assertEquals(1, connections.get("A_B").get(0).y());
		Assert.assertEquals(200, connections.get("A_B").get(1).x());
		Assert.assertEquals(2, connections.get("A_B").get(1).y());
	}
	
	@Test
	public void testScaleY(){
		LayoutTransformer layoutTransformer = new LayoutTransformer(1, 100, 0, 0, 0);

		Map<String, Rectangle> objects = new HashMap<String, Rectangle>();
		Map<String, List<Point>> connections = new HashMap<String, List<Point>>();
		
		objects.put("O", new Rectangle(0,0,1,1));
		objects.put("A", new Rectangle(1,1,1,1));
		objects.put("B", new Rectangle(2,2,1,1));
		connections.put("A_B", Arrays.asList(new Point(1,1), new Point(2,2)));
		
		layoutTransformer.doTranformations(objects, connections);
		
		Assert.assertEquals(0, objects.get("O").x());
		Assert.assertEquals((int) 49.5, objects.get("O").y());
		Assert.assertEquals(1, objects.get("O").width());
		Assert.assertEquals(1, objects.get("O").height());
		
		Assert.assertEquals(1, objects.get("A").x());
		Assert.assertEquals((int) 149.5, objects.get("A").y());
		Assert.assertEquals(1, objects.get("A").width());
		Assert.assertEquals(1, objects.get("A").height());
		
		Assert.assertEquals(2, objects.get("B").x());
		Assert.assertEquals((int) 249.5, objects.get("B").y());
		Assert.assertEquals(1, objects.get("B").width());
		Assert.assertEquals(1, objects.get("B").height());
		
		Assert.assertEquals(1,  connections.get("A_B").get(0).x());
		Assert.assertEquals(100, connections.get("A_B").get(0).y());
		Assert.assertEquals(2, connections.get("A_B").get(1).x());
		Assert.assertEquals(200, connections.get("A_B").get(1).y());
	}
	
	@Test
	public void testScaleWithDensity1(){
		LayoutTransformer layoutTransformer = new LayoutTransformer(100, 50, 0, 0, 1);

		Map<String, Rectangle> objects = new HashMap<String, Rectangle>();
		Map<String, List<Point>> connections = new HashMap<String, List<Point>>();
		
		objects.put("O", new Rectangle(0,0,1,1));
		objects.put("A", new Rectangle(1,1,1,1));
		objects.put("B", new Rectangle(2,2,1,1));
		connections.put("A_B", Arrays.asList(new Point(1,1), new Point(2,2)));
		
		layoutTransformer.doTranformations(objects, connections);
		
		Assert.assertEquals((int) 49.5, objects.get("O").x());
		Assert.assertEquals((int) 24.5, objects.get("O").y());
		Assert.assertEquals(1, objects.get("O").width());
		Assert.assertEquals(1, objects.get("O").height());
		
		Assert.assertEquals((int) 149.5, objects.get("A").x());
		Assert.assertEquals((int) 74.5, objects.get("A").y());
		Assert.assertEquals(1, objects.get("A").width());
		Assert.assertEquals(1, objects.get("A").height());
		
		Assert.assertEquals((int) 249.5, objects.get("B").x());
		Assert.assertEquals((int) 124.5, objects.get("B").y());
		Assert.assertEquals(1, objects.get("B").width());
		Assert.assertEquals(1, objects.get("B").height());
		
		Assert.assertEquals(100,  connections.get("A_B").get(0).x());
		Assert.assertEquals(50, connections.get("A_B").get(0).y());
		Assert.assertEquals(200, connections.get("A_B").get(1).x());
		Assert.assertEquals(100, connections.get("A_B").get(1).y());
	}
	
	@Test
	public void testScaleWithDensity5(){
		LayoutTransformer layoutTransformer = new LayoutTransformer(100, 50, 0, 0, 5);

		Map<String, Rectangle> objects = new HashMap<String, Rectangle>();
		Map<String, List<Point>> connections = new HashMap<String, List<Point>>();
		
		objects.put("O", new Rectangle(0,0,5,5));
		objects.put("A", new Rectangle(5,5,5,5));
		objects.put("B", new Rectangle(10,10,5,5));
		connections.put("A_B", Arrays.asList(new Point(1,1), new Point(2,2)));
		
		layoutTransformer.doTranformations(objects, connections);
		
		Assert.assertEquals((int) 47.5, objects.get("O").x());
		Assert.assertEquals((int) 22.5, objects.get("O").y());
		Assert.assertEquals(5, objects.get("O").width());
		Assert.assertEquals(5, objects.get("O").height());
		
		Assert.assertEquals((int) 147.5, objects.get("A").x());
		Assert.assertEquals((int) 72.5, objects.get("A").y());
		Assert.assertEquals(5, objects.get("A").width());
		Assert.assertEquals(5, objects.get("A").height());
		
		Assert.assertEquals((int) 247.5, objects.get("B").x());
		Assert.assertEquals((int) 122.5, objects.get("B").y());
		Assert.assertEquals(5, objects.get("B").width());
		Assert.assertEquals(5, objects.get("B").height());
		
		Assert.assertEquals(20,  connections.get("A_B").get(0).x());
		Assert.assertEquals(10, connections.get("A_B").get(0).y());
		Assert.assertEquals(40, connections.get("A_B").get(1).x());
		Assert.assertEquals(20, connections.get("A_B").get(1).y());
	}
}
