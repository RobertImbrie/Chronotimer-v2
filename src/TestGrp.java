package src;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class TestGrp {
	
	@Test
	public void testTrigger() throws InterruptedException{

		Grp g = new Grp();
		assertFalse(g.hasStarted);
		g.trigger(1, 0);
		assertTrue(g.hasStarted);
		g.trigger(2, 1000000000);
		g.trigger(2, 2000000000);
		g.trigger(3, 3000000000L);
		Competitor temp = g.finishes.dequeue();
		assertEquals(0, temp.getStartTime());
		assertEquals(1000000000, temp.getEndTime());
		temp = g.finishes.dequeue();
		assertEquals(0, temp.getStartTime());
		assertEquals(2000000000, temp.getEndTime());
		temp = g.finishes.dequeue();
		assertEquals(0, temp.getStartTime());
		assertEquals(3000000000L, temp.getEndTime());
		
		g = new Grp();
		g.trigger(2, 0);
		g.trigger(2, 1000000000);
		assertFalse(g.hasStarted);
		g.trigger(1, 0);
		g.trigger(1, 1000000000);
		g.trigger(1, 2000000000);
		assertTrue(g.hasStarted);
		g.trigger(2, 3000000000L);
		temp = g.finishes.dequeue();
		assertEquals(0, temp.getStartTime());
		assertEquals(3000000000L, temp.getEndTime());
		g.trigger(1, 4000000000L);
		g.trigger(2, 5000000000L);
		temp = g.finishes.dequeue();
		assertEquals(0, temp.getStartTime());
		assertEquals(5000000000L, temp.getEndTime());
	}
	
	@Test 
	public void testCompetitorList(){
		
		Grp g = new Grp();
		g.trigger(1, 0);
		assertEquals(null, g.competitorList());
		g.trigger(2, 1000000000);
		g.trigger(2, 2000000000);
		g.trigger(2, 3000000000L);
		g.addCompetitor(111);
		g.addCompetitor(222);
		g.addCompetitor(333);
		ArrayList<String> temp = new ArrayList<String>();
		temp.add("Competitor: 111 --- 1 Seconds");
		temp.add("Competitor: 222 --- 2 Seconds");
		temp.add("Competitor: 333 --- 3 Seconds");
		assertEquals(temp, g.competitorList());
	}
	
	@Test 
	public void testClear(){
		
	}
	
	@Test
	public void testRemoveCompetitorByBib(){
		
	}
	
	@Test
	public void testRemoveCompetitorByPos(){
		
	}
	
	@Test
	public void testDidNotFinish(){
		
	}
	
	@Test
	public void testEnd(){
		
	}
	
	@Test
	public void testStart(){
		
	}
	
	@Test
	public void testReset(){
		
	}
	
	@Test
	public void testRuntime(){
		
	}
	
	@Test
	public void testToString(){
		
	}
}
