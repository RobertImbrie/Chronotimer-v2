package src;
import static org.junit.Assert.*;
import org.junit.Test;

public class TestParaInd {
	
	@Test
	public void testTrigger(){
		ParaInd run = new ParaInd();
		run.addCompetitor(111);
		run.trigger(1, 1);
		run.trigger(3, 2);
		assertEquals(1, run.competitors.get(0).runTime());
		run.addCompetitor(222);
		run.addCompetitor(333);
		run.trigger(1, 3);
		run.trigger(3, 5);
		run.trigger(2, 6);
		run.trigger(2, 7);
		assertEquals(2, run.competitors.get(1).runTime());
		assertEquals(1, run.competitors.get(2).runTime());
		run.addCompetitor(444);
		run.addCompetitor(555);
		run.trigger(2, 10);
		run.trigger(1, 11);
		run.trigger(4, 12);
		run.trigger(3, 13);
		assertEquals(2, run.competitors.get(3).runTime());
		assertEquals(2, run.competitors.get(4).runTime());
		run.addCompetitor(666);
		run.addCompetitor(777);
		run.trigger(2, 15);
		run.trigger(1, 16);
		run.trigger(3, 17);
		run.trigger(4, 18);
		assertEquals(3, run.competitors.get(5).runTime());
		assertEquals(1, run.competitors.get(6).runTime());
		run.trigger(2, 1);
		run.trigger(1, 2);
		run.trigger(3, 3);
		run.trigger(4, 4);
		run.trigger(2, 5);
		run.trigger(2, 6);
		run.addCompetitor(888);
		run.trigger(2, 1);
		run.trigger(2, 2);
		run.trigger(4, 3);
		run.trigger(4, 4);
		assertEquals(2, run.competitors.get(7).runTime());
		run.addCompetitor(999);
		run.trigger(4, 1);
		run.trigger(2, 2);
		run.trigger(4, 3);
		run.trigger(3, 4);
		assertEquals(1, run.competitors.get(8).runTime());
		run.addCompetitor(1111);
		run.addCompetitor(2222);
		run.trigger(1, 1);
		run.trigger(1, 2);
		run.trigger(3, 3);
		run.trigger(3, 4);
		assertEquals(1, run.competitors.get(5).runTime());
		assertEquals(-1, run.competitors.get(6).runTime());
	}
	
	@Test
	public void testCompetitorList(){
		
	}
	
	@Test
	public void testAddCompetitor(){
		
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
	public void testStart(){
		
	}
	
	@Test
	public void testEnd1(){
		
	}
	
	@Test
	public void testEnd2(){
		
	}
	
	@Test
	public void testReset(){
		
	}
	
	@Test 
	public void TestRuntime(){
		
	}

}
