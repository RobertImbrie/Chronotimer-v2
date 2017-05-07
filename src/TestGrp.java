package src;
import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;

public class TestGrp {
	
	@Test
	//Test Grp.java trigger() method
	public void testTrigger() {
		//Test 1
		Grp g = new Grp();
		assertFalse(g.hasStarted);
		g.trigger(1, 0);
		assertTrue(g.hasStarted);
		g.trigger(2, 1000000000);
		g.trigger(2, 2000000000);
		g.trigger(2, 3000000000L);
		g.addCompetitor(111);		
		assertEquals(0, g.competitors.get(0).getStartTime());
		assertEquals(1000000000, g.competitors.get(0).getEndTime());
		g.addCompetitor(222);
		assertEquals(0, g.competitors.get(1).getStartTime());
		assertEquals(2000000000, g.competitors.get(1).getEndTime());
		g.addCompetitor(333);
		assertEquals(0, g.competitors.get(2).getStartTime());
		assertEquals(3000000000L, g.competitors.get(2).getEndTime());
		//Test 2
		g = new Grp();
		g.trigger(2, 0);
		g.trigger(2, 1000000000);
		assertFalse(g.hasStarted);
		g.trigger(1, 0);
		g.trigger(1, 1000000000);
		g.trigger(1, 2000000000);
		assertTrue(g.hasStarted);
		g.trigger(2, 3000000000L);
		g.addCompetitor(111);
		assertEquals(0, g.competitors.get(0).getStartTime());
		assertEquals(3000000000L, g.competitors.get(0).getEndTime());
		g.trigger(1, 4000000000L);
		g.trigger(2, 5000000000L);
		g.addCompetitor(222);
		assertEquals(0, g.competitors.get(1).getStartTime());
		assertEquals(5000000000L, g.competitors.get(1).getEndTime());
	}
	
	@Test 
	//Test Grp.java competitorList() method
	public void testCompetitorList(){
		//Test 1
		Grp g = new Grp();
		g.trigger(1, 0);
		ArrayList<String> temp = new ArrayList<String>();
		assertEquals(temp, g.competitorList());
		g.trigger(2, 1000000000);
		g.trigger(2, 2000000000);
		g.trigger(2, 3000000000L);
		g.addCompetitor(111);
		g.addCompetitor(222);
		g.addCompetitor(333);
		temp.add("Competitor: 111 --- 00:00:01.000");
		temp.add("Competitor: 222 --- 00:00:02.000");
		temp.add("Competitor: 333 --- 00:00:03.000");
		assertEquals(temp, g.competitorList());
	}
	
	@Test 
	//Test Grp.java clear() method
	public void testClear(){
		//Test 1
		Grp g = new Grp();
		g.trigger(1, 0);
		g.trigger(2, 1000000000);
		g.addCompetitor(111);
		String[] temp = g.clear();
		assertEquals("Competitor: 111 --- 00:00:01.000", temp[0]);
		g.trigger(2, 2000000000);
		g.trigger(2, 3000000000L);
		g.trigger(2, 4000000000L);
		g.trigger(2, 5000000000L);
		g.trigger(2, 6000000000L);
		g.addCompetitor(111);
		g.addCompetitor(222);
		g.addCompetitor(333);
		g.addCompetitor(444);
		g.addCompetitor(555);
		temp = g.clear();
		assertEquals("Competitor: 111 --- 00:00:02.000", temp[0]);
		assertEquals("Competitor: 222 --- 00:00:03.000", temp[1]);
		assertEquals("Competitor: 333 --- 00:00:04.000", temp[2]);
		assertEquals("Competitor: 444 --- 00:00:05.000", temp[3]);
		assertEquals("Competitor: 555 --- 00:00:06.000", temp[4]);
	}
	
	@Test
	//Test Grp.java addCompetitor() method
	public void testAddCompetitor(){
		//Test 1
		Grp g = new Grp();
		assertFalse(g.addCompetitor(111));
		g.trigger(1, 0);
		assertFalse(g.addCompetitor(111));
		g.trigger(1, 1000000000);
		assertFalse(g.addCompetitor(111));
		g.trigger(2, 2000000000);
		assertTrue(g.addCompetitor(111));
		assertFalse(g.addCompetitor(222));
		g.trigger(2, 3000000000L);
		assertFalse(g.addCompetitor(111));
		assertTrue(g.addCompetitor(222));
		g.trigger(2, 4000000000L);
		g.trigger(2, 5000000000L);
		assertTrue(g.addCompetitor(333));
		assertTrue(g.addCompetitor(444));
		g.clear();
		assertFalse(g.addCompetitor(555));
		g.trigger(2, 8000000000L);
		assertTrue(g.addCompetitor(666));
	}
	
	@Test
	//Test Grp.java runtime() method
	public void testRuntime(){
		//Test 1
		Grp g = new Grp();
		g.trigger(1, 0);
		g.trigger(2, 1000000000);
		g.trigger(2, 2000000000);
		g.trigger(2, 3000000000L);
		g.addCompetitor(111);
		g.addCompetitor(222);
		g.addCompetitor(333);
		assertEquals(1000000000, g.runTime(0));
		assertEquals(2000000000, g.runTime(1));
		assertEquals(3000000000L, g.runTime(2));
	}
	
	@Test
	//Test Grp.java runtime() method
	public void testToString(){
		//Test 1
		Grp g = new Grp();
		assertEquals("", g.toString());
		g.trigger(1, 0);
		assertEquals("", g.toString());
		g.trigger(2, 1000000000);
		g.addCompetitor(111);
		assertEquals("\tCompetitor: 111 --- 00:00:01.000\n", g.toString());
		g.trigger(2, 2000000000);
		g.addCompetitor(222);
		assertEquals("\tCompetitor: 111 --- 00:00:01.000\n\tCompetitor: 222 --- 00:00:02.000\n", g.toString());
	}
}	
	
	