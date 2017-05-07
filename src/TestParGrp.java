package src;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class TestParGrp {
	
	@Test
	//Test ParGrp.java trigger() method
	public void testTrigger(){
		//Test 1
		ParGrp g = new ParGrp();
		assertFalse(g.hasStarted);
		g.trigger(1, 0);
		assertTrue(g.hasStarted);
		g.trigger(1, 1000000000);
		g.trigger(1, 2000000000);
		g.addCompetitor(111);		
		assertEquals(0, g.competitors.get(0).getStartTime());
		assertEquals(1000000000, g.competitors.get(0).getEndTime());
		//Test 2
		g = new ParGrp();
		assertFalse(g.hasStarted);
		g.trigger(1, 0);
		assertTrue(g.hasStarted);
		g.trigger(1, 1000000000);
		g.trigger(2, 2000000000);
		g.trigger(3, 3000000000L);
		g.trigger(4, 4000000000L);
		g.trigger(5, 5000000000L);
		g.trigger(6, 6000000000L);
		g.trigger(7, 7000000000L);
		g.trigger(8, 8000000000L);
		g.addCompetitor(111);
		g.addCompetitor(222);
		g.addCompetitor(333);
		g.addCompetitor(444);
		g.addCompetitor(555);
		g.addCompetitor(666);
		g.addCompetitor(777);
		g.addCompetitor(888);
		assertEquals(0, g.competitors.get(0).getStartTime());
		assertEquals(1000000000, g.competitors.get(0).getEndTime());
		assertEquals(0, g.competitors.get(1).getStartTime());
		assertEquals(2000000000, g.competitors.get(1).getEndTime());
		assertEquals(0, g.competitors.get(2).getStartTime());
		assertEquals(3000000000L, g.competitors.get(2).getEndTime());
		assertEquals(0, g.competitors.get(3).getStartTime());
		assertEquals(4000000000L, g.competitors.get(3).getEndTime());
		assertEquals(0, g.competitors.get(4).getStartTime());
		assertEquals(5000000000L, g.competitors.get(4).getEndTime());
		assertEquals(0, g.competitors.get(5).getStartTime());
		assertEquals(6000000000L, g.competitors.get(5).getEndTime());
		assertEquals(0, g.competitors.get(6).getStartTime());
		assertEquals(7000000000L, g.competitors.get(6).getEndTime());
		assertEquals(0, g.competitors.get(7).getStartTime());
		assertEquals(8000000000L, g.competitors.get(7).getEndTime());
		//Test 3
		g = new ParGrp();
		assertFalse(g.hasStarted);
		g.trigger(1, 0);
		assertTrue(g.hasStarted);
		g.trigger(1, 1000000000);
		g.trigger(1, 7000000000L);
		g.trigger(2, 2000000000);
		g.trigger(2, 1000000000);
		g.trigger(3, 3000000000L);
		g.trigger(3, 1000000000);
		g.trigger(4, 4000000000L);
		g.trigger(4, 1000000000);
		g.trigger(4, 2000000000);
		g.trigger(5, 5000000000L);
		g.trigger(5, 1000000000);
		g.trigger(6, 6000000000L);
		g.trigger(6, 1000000000);
		g.trigger(7, 7000000000L);
		g.trigger(7, 1000000000);
		g.trigger(7, 2000000000);
		g.trigger(8, 8000000000L);
		g.trigger(1, 1000000000);
		g.trigger(8, 1000000000);
		g.addCompetitor(111);
		g.addCompetitor(222);
		g.addCompetitor(333);
		g.addCompetitor(444);
		g.addCompetitor(555);
		g.addCompetitor(666);
		g.addCompetitor(777);
		g.addCompetitor(888);
		assertEquals(0, g.competitors.get(0).getStartTime());
		assertEquals(1000000000, g.competitors.get(0).getEndTime());
		assertEquals(0, g.competitors.get(1).getStartTime());
		assertEquals(2000000000, g.competitors.get(1).getEndTime());
		assertEquals(0, g.competitors.get(2).getStartTime());
		assertEquals(3000000000L, g.competitors.get(2).getEndTime());
		assertEquals(0, g.competitors.get(3).getStartTime());
		assertEquals(4000000000L, g.competitors.get(3).getEndTime());
		assertEquals(0, g.competitors.get(4).getStartTime());
		assertEquals(5000000000L, g.competitors.get(4).getEndTime());
		assertEquals(0, g.competitors.get(5).getStartTime());
		assertEquals(6000000000L, g.competitors.get(5).getEndTime());
		assertEquals(0, g.competitors.get(6).getStartTime());
		assertEquals(7000000000L, g.competitors.get(6).getEndTime());
		assertEquals(0, g.competitors.get(7).getStartTime());
		assertEquals(8000000000L, g.competitors.get(7).getEndTime());
	}
	
	@Test 
	//Test ParGrp.java competitorList() method
	public void testCompetitorList(){
		//Test 1
		ParGrp g = new ParGrp();
		g.trigger(1, 0);
		ArrayList<String> temp = new ArrayList<String>();
		assertEquals(temp, g.competitorList());
		g.trigger(1, 1000000000);
		g.trigger(2, 2000000000);
		g.trigger(3, 3000000000L);
		g.trigger(4, 4000000000L);
		g.trigger(5, 5000000000L);
		g.trigger(6, 6000000000L);
		g.trigger(7, 7000000000L);
		g.trigger(8, 8000000000L);
		g.addCompetitor(111);
		g.addCompetitor(222);
		g.addCompetitor(333);
		g.addCompetitor(444);
		g.addCompetitor(555);
		g.addCompetitor(666);
		g.addCompetitor(777);
		g.addCompetitor(888);
		temp.add("Competitor: 111 --- 00:00:01.000");
		temp.add("Competitor: 222 --- 00:00:02.000");
		temp.add("Competitor: 333 --- 00:00:03.000");
		temp.add("Competitor: 444 --- 00:00:04.000");
		temp.add("Competitor: 555 --- 00:00:05.000");
		temp.add("Competitor: 666 --- 00:00:06.000");
		temp.add("Competitor: 777 --- 00:00:07.000");
		temp.add("Competitor: 888 --- 00:00:08.000");
		assertEquals(temp, g.competitorList());
		//Test 2
		g = new ParGrp();
		temp = new ArrayList<String>();
		assertEquals(temp, g.competitorList());
		g.trigger(1, 1000000000);
		g.trigger(1, 7000000000L);
		g.trigger(2, 2000000000);
		g.trigger(2, 1000000000);
		g.trigger(3, 3000000000L);
		g.trigger(3, 1000000000);
		g.trigger(4, 4000000000L);
		g.trigger(4, 1000000000);
		g.trigger(4, 2000000000);
		g.trigger(5, 5000000000L);
		g.trigger(5, 1000000000);
		g.trigger(6, 6000000000L);
		g.trigger(6, 1000000000);
		g.trigger(7, 7000000000L);
		g.trigger(7, 1000000000);
		g.trigger(7, 2000000000);
		g.trigger(8, 8000000000L);
		g.trigger(1, 1000000000);
		g.trigger(8, 1000000000);
		g.addCompetitor(111);
		g.addCompetitor(222);
		g.addCompetitor(333);
		g.addCompetitor(444);
		g.addCompetitor(555);
		g.addCompetitor(666);
		g.addCompetitor(777);
		g.addCompetitor(888);
		temp.add("Competitor: 111 --- 00:00:01.000");
		temp.add("Competitor: 222 --- 00:00:02.000");
		temp.add("Competitor: 333 --- 00:00:03.000");
		temp.add("Competitor: 444 --- 00:00:04.000");
		temp.add("Competitor: 555 --- 00:00:05.000");
		temp.add("Competitor: 666 --- 00:00:06.000");
		temp.add("Competitor: 777 --- 00:00:07.000");
		temp.add("Competitor: 888 --- 00:00:08.000");
		assertEquals(temp, g.competitorList());
	}
	
	@Test 
	//Test ParGrp.java clear() method
	public void testClear(){
		//Test 1
		ParGrp g = new ParGrp();
		g.trigger(1, 0);
		g.trigger(1, 1000000000);
		g.addCompetitor(111);
		String[] temp = g.clear();
		assertEquals("Competitor: 111 --- 00:00:01.000", temp[0]);
		//Test 2
		g = new ParGrp();
		g.trigger(1, 0);
		g.trigger(1, 1000000000);
		g.trigger(2, 2000000000);
		g.trigger(3, 3000000000L);
		g.trigger(4, 4000000000L);
		g.trigger(5, 5000000000L);
		g.trigger(6, 6000000000L);
		g.trigger(7, 7000000000L);
		g.trigger(8, 8000000000L);
		g.addCompetitor(111);
		g.addCompetitor(222);
		g.addCompetitor(333);
		g.addCompetitor(444);
		g.addCompetitor(555);
		g.addCompetitor(666);
		g.addCompetitor(777);
		g.addCompetitor(888);
		temp = g.clear();
		assertEquals("Competitor: 111 --- 00:00:01.000", temp[0]);
		assertEquals("Competitor: 222 --- 00:00:02.000", temp[1]);
		assertEquals("Competitor: 333 --- 00:00:03.000", temp[2]);
		assertEquals("Competitor: 444 --- 00:00:04.000", temp[3]);
		assertEquals("Competitor: 555 --- 00:00:05.000", temp[4]);
		assertEquals("Competitor: 666 --- 00:00:06.000", temp[5]);
		assertEquals("Competitor: 777 --- 00:00:07.000", temp[6]);
		assertEquals("Competitor: 888 --- 00:00:08.000", temp[7]);
	}
	
	@Test
	//Test ParGrp.java trigger() method
	public void testAddCompetitor(){
		//Test 1
		ParGrp g = new ParGrp();
		assertFalse(g.addCompetitor(111));
		g.trigger(1, 0);
		assertFalse(g.addCompetitor(111));
		g.trigger(1, 1000000000);
		assertTrue(g.addCompetitor(111));
		assertFalse(g.addCompetitor(222));
		g.trigger(1, 1000000000);
		assertFalse(g.addCompetitor(222));
		g.trigger(2, 2000000000);
		assertFalse(g.addCompetitor(111));
		assertTrue(g.addCompetitor(222));
		g.trigger(3, 3000000000L);
		g.trigger(4, 4000000000L);
		g.trigger(5, 5000000000L);
		g.trigger(6, 6000000000L);
		g.trigger(7, 7000000000L);
		g.trigger(8, 8000000000L);
		assertTrue(g.addCompetitor(333));
		assertTrue(g.addCompetitor(444));
		assertTrue(g.addCompetitor(555));
		assertTrue(g.addCompetitor(666));
		assertTrue(g.addCompetitor(777));
		assertTrue(g.addCompetitor(888));
		assertFalse(g.addCompetitor(999));
	}
	
	@Test
	//Test ParGrp.java runtime() method
	public void testRuntime(){
		//Test 1
		ParGrp g = new ParGrp();
		g.trigger(1, 0);
		g.trigger(1, 1000000000);
		g.trigger(2, 2000000000);
		g.trigger(3, 3000000000L);
		g.trigger(4, 4000000000L);
		g.trigger(5, 5000000000L);
		g.trigger(6, 6000000000L);
		g.trigger(7, 7000000000L);
		g.trigger(8, 8000000000L);
		g.addCompetitor(111);
		g.addCompetitor(222);
		g.addCompetitor(333);
		g.addCompetitor(444);
		g.addCompetitor(555);
		g.addCompetitor(666);
		g.addCompetitor(777);
		g.addCompetitor(888);
		assertEquals(1000000000, g.runTime(0));
		assertEquals(2000000000, g.runTime(1));
		assertEquals(3000000000L, g.runTime(2));
		assertEquals(4000000000L, g.runTime(3));
		assertEquals(5000000000L, g.runTime(4));
		assertEquals(6000000000L, g.runTime(5));
		assertEquals(7000000000L, g.runTime(6));
		assertEquals(8000000000L, g.runTime(7));
	}
	
	@Test
	//Test ParGrp.java toString() method
	public void testToString(){
		//Test 1
		ParGrp g = new ParGrp();
		assertEquals("", g.toString());
		g.trigger(1, 0);
		assertEquals("", g.toString());
		g.trigger(1, 1000000000);
		g.addCompetitor(111);
		assertEquals("\tCompetitor: 111 --- 00:00:01.000\n", g.toString());
		g.trigger(2, 2000000000);
		g.addCompetitor(222);
		assertEquals("\tCompetitor: 111 --- 00:00:01.000\n\tCompetitor: 222 --- 00:00:02.000\n", g.toString());
		g.trigger(3, 3000000000L);
		g.trigger(4, 4000000000L);
		g.trigger(5, 5000000000L);
		g.trigger(6, 6000000000L);
		g.trigger(7, 7000000000L);
		g.trigger(8, 8000000000L);
		g.addCompetitor(333);
		g.addCompetitor(444);
		g.addCompetitor(555);
		g.addCompetitor(666);
		g.addCompetitor(777);
		g.addCompetitor(888);
		assertEquals("\tCompetitor: 111 --- 00:00:01.000\n\tCompetitor: 222 --- 00:00:02.000\n\tCompetitor: 333 --- 00:00:03.000\n\tCompetitor: 444 --- 00:00:04.000\n\tCompetitor: 555 --- 00:00:05.000\n\tCompetitor: 666 --- 00:00:06.000\n\tCompetitor: 777 --- 00:00:07.000\n\tCompetitor: 888 --- 00:00:08.000\n", g.toString());
	}
	
}
