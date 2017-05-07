package src;
import static org.junit.Assert.*;
import org.junit.Test;

public class TestParaInd {
	
	@Test
	public void testTrigger(){
		ParaInd run = new ParaInd();
		run.addCompetitor(111);
		run.trigger(1, 1);
		run.trigger(2, 2);
		assertEquals(1, run.competitors.get(0).runTime());
		run.addCompetitor(222);
		run.addCompetitor(333);
		run.trigger(1, 3);
		run.trigger(2, 5);
		run.trigger(3, 6);
		run.trigger(4, 7);
		assertEquals(2, run.competitors.get(1).runTime());
		assertEquals(1, run.competitors.get(2).runTime());
		run.addCompetitor(444);
		run.addCompetitor(555);
		run.trigger(3, 10);
		run.trigger(1, 11);
		run.trigger(4, 12);
		run.trigger(2, 13);
		assertEquals(2, run.competitors.get(3).runTime());
		assertEquals(2, run.competitors.get(4).runTime());
		run.addCompetitor(666);
		run.addCompetitor(777);
		run.trigger(3, 15);
		run.trigger(1, 16);
		run.trigger(2, 17);
		run.trigger(4, 18);
		assertEquals(3, run.competitors.get(5).runTime());
		assertEquals(1, run.competitors.get(6).runTime());
		run.trigger(3, 1);
		run.trigger(1, 2);
		run.trigger(2, 3);
		run.trigger(4, 4);
		run.trigger(3, 5);
		run.trigger(3, 6);
		run.addCompetitor(888);
		run.trigger(3, 1);
		run.trigger(3, 2);
		run.trigger(4, 3);
		run.trigger(4, 4);
		assertEquals(2, run.competitors.get(7).runTime());
		run.addCompetitor(999);
		run.trigger(4, 1);
		run.trigger(3, 2);
		run.trigger(4, 3);
		run.trigger(2, 4);
		assertEquals(1, run.competitors.get(8).runTime());
		run.addCompetitor(1111);
		run.addCompetitor(2222);
		run.trigger(1, 1);
		run.trigger(1, 2);
		run.trigger(2, 3);
		run.trigger(2, 4);
		assertEquals(2, run.competitors.get(9).runTime());
		assertEquals(-1, run.competitors.get(10).runTime());
	}
	
	@Test
	public void testCompetitorList(){
		ParaInd r = new ParaInd();
		r.addCompetitor(111);
		assertEquals("Competitor: 111 --- Has Not Started", r.competitorList().get(0));
		r.addCompetitor(122);
		assertEquals("Competitor: 111 --- Has Not Started", r.competitorList().get(0));
		assertEquals("Competitor: 122 --- Has Not Started", r.competitorList().get(1));
		r.removeCompetitorByBib(111);
		assertEquals("Competitor: 122 --- Has Not Started", r.competitorList().get(0));
		r.addCompetitor(333);
		r.trigger(1, 1000000000);
		assertEquals("Competitor: 122 --- DNF", r.competitorList().get(0));
		assertEquals("Competitor: 333 --- Has Not Started", r.competitorList().get(1));
		r.trigger(2, 2000000000);
		assertEquals("Competitor: 122 --- 00:00:01.000", r.competitorList().get(0));
		assertEquals("Competitor: 333 --- Has Not Started", r.competitorList().get(1));
		r.trigger(3, 3000000000L);
		assertEquals("Competitor: 122 --- 00:00:01.000", r.competitorList().get(0));
		assertEquals("Competitor: 333 --- DNF", r.competitorList().get(1));
		r.trigger(4, 7000000000L);
		assertEquals("Competitor: 122 --- 00:00:01.000", r.competitorList().get(0));
		assertEquals("Competitor: 333 --- 00:00:04.000", r.competitorList().get(1));
		r.removeCompetitorByBib(122);
		assertEquals("Competitor: 333 --- 00:00:04.000", r.competitorList().get(0));
	}
	
	@Test
	public void testAddCompetitor(){
		ParaInd r = new ParaInd();
		assertEquals(true, r.addCompetitor(111));
		assertEquals(true, r.addCompetitor(222));
		assertEquals(false, r.addCompetitor(111));
		assertEquals(false, r.addCompetitor(222));
		assertEquals(true, r.addCompetitor(333));
		r.removeCompetitorByBib(111);
		assertEquals(true, r.addCompetitor(111));
	}
	
	@Test
	public void testClear(){
		ParaInd r = new ParaInd();
		r.addCompetitor(111);
		r.addCompetitor(222);
		r.addCompetitor(333);
		assertEquals("Competitor: 111 --- Has Not Started", r.clear()[0]);
		r.addCompetitor(111);
		r.addCompetitor(222);
		r.addCompetitor(333);
		assertEquals("Competitor: 222 --- Has Not Started", r.clear()[1]);
		r.addCompetitor(111);
		r.addCompetitor(222);
		r.addCompetitor(333);
		assertEquals("Competitor: 333 --- Has Not Started", r.clear()[2]);
		r.addCompetitor(111);
		r.addCompetitor(222);
		r.addCompetitor(333);
		r.trigger(1, 1000000000);
		r.trigger(2, 2000000000);
		r.trigger(1, 3000000000L);
		r.trigger(2, 4000000000L);
		r.trigger(1, 5000000000L);
		assertEquals("Competitor: 111 --- 00:00:01.000", r.clear()[0]);
		r.addCompetitor(111);
		r.addCompetitor(222);
		r.addCompetitor(333);
		r.trigger(3, 1000000000);
		r.trigger(4, 2000000000);
		r.trigger(1, 3000000000L);
		r.trigger(2, 4000000000L);
		r.trigger(3, 5000000000L);
		assertEquals("Competitor: 222 --- 00:00:01.000", r.clear()[1]);
		r.addCompetitor(111);
		r.addCompetitor(222);
		r.addCompetitor(333);
		r.trigger(1, 1000000000);
		r.trigger(2, 2000000000);
		r.trigger(3, 3000000000L);
		r.trigger(4, 4000000000L);
		r.trigger(3, 5000000000L);
		assertEquals("Competitor: 333 --- DNF", r.clear()[2]);
	}
	
	@Test
	public void testRemoveCompetitorByBib(){
		ParaInd r = new ParaInd();
    	r.addCompetitor(111);
		assertEquals("Competitor: 111 --- Has Not Started", r.removeCompetitorByBib(111));
		r.addCompetitor(111);
		r.addCompetitor(222);
		r.trigger(3, 1000000000);
		r.trigger(4, 3000000000L);
		assertEquals("Competitor: 222 --- Has Not Started", r.removeCompetitorByBib(222));
		assertEquals("Competitor: 111 --- 00:00:02.000", r.removeCompetitorByBib(111));
		r.addCompetitor(111);
		r.addCompetitor(222);
		r.addCompetitor(333);
		r.trigger(1, 1000000000);
		r.trigger(2, 2000000000);
		r.trigger(3, 3000000000L);
		r.trigger(4, 5000000000L);
		assertEquals("Competitor: 222 --- 00:00:02.000", r.removeCompetitorByBib(222));
		assertEquals("Competitor: 111 --- 00:00:01.000", r.removeCompetitorByBib(111));
		r.trigger(3, 6000000000L);
		r.trigger(4, 8000000000L);
		assertEquals("Competitor: 333 --- 00:00:02.000", r.removeCompetitorByBib(333));
	}
	
	@Test
	public void testRemoveCompetitorByPos(){
		ParaInd r = new ParaInd();
		r.addCompetitor(111);
		assertEquals("Competitor: 111 --- Has Not Started", r.removeCompetitorByPos(0));
		r.addCompetitor(111);
		r.addCompetitor(222);
		r.trigger(1, 1000000000);
		r.trigger(2, 3000000000L);
		assertEquals("Competitor: 222 --- Has Not Started", r.removeCompetitorByPos(1));
		assertEquals("Competitor: 111 --- 00:00:02.000", r.removeCompetitorByPos(0));
		r.addCompetitor(111);
		r.addCompetitor(222);
		r.addCompetitor(333);
		r.trigger(3, 1000000000L);
		r.trigger(4, 2000000000L);
		r.trigger(3, 3000000000L);
		r.trigger(4, 5000000000L);
		assertEquals("Competitor: 222 --- 00:00:02.000", r.removeCompetitorByPos(1));
		assertEquals("Competitor: 111 --- 00:00:01.000", r.removeCompetitorByPos(0));
		r.trigger(1, 6000000000L);
		r.trigger(2, 8000000000L);
		assertEquals("Competitor: 333 --- 00:00:02.000", r.removeCompetitorByPos(0));
	}
	
	@Test
	public void testDidNotFinish(){
		ParaInd r = new ParaInd();
		r.addCompetitor(111);
		r.addCompetitor(222);
		r.trigger(1, 1000000000);
		r.didNotFinish(1);
		r.trigger(3, 2000000000);
		r.trigger(4, 3000000000L);
		r.didNotFinish(2);
		assertEquals("Competitor: 111 --- DNF", r.getCompetitors().get(0).toString());
		assertEquals("Competitor: 222 --- 00:00:01.000", r.getCompetitors().get(1).toString());
		r.clear();
		r.addCompetitor(111);
		r.addCompetitor(222);
		r.addCompetitor(333);
		r.didNotFinish(1);
		r.trigger(3, 1000000000);
		r.didNotFinish(2);
		r.trigger(1, 2000000000);
		r.trigger(2, 4000000000L);
		r.trigger(1, 2000000000);
		r.trigger(2, 4000000000L);
		assertEquals("Competitor: 111 --- DNF", r.getCompetitors().get(0).toString());
		assertEquals("Competitor: 222 --- 00:00:02.000", r.getCompetitors().get(1).toString());
		assertEquals("Competitor: 333 --- 00:00:02.000", r.getCompetitors().get(2).toString());
	}
	
	@Test
	public void testStart(){
		//start(int channel, long l)
		ParaInd r = new ParaInd();
    	r.addCompetitor(1);
    	r.addCompetitor(2);
    	r.trigger(1, 1000000000);
    	r.trigger(3, 2000000000);
    	assertFalse(r.getCompetitors().get(0).getStartTime() == -1);
    	assertTrue(r.getCompetitors().get(0).getStarted());
	}
	
	@Test
	public void testEnd(){
		ParaInd r = new ParaInd();
		r.addCompetitor(111);
		r.addCompetitor(222);
		r.addCompetitor(333);
		r.addCompetitor(444);
		r.trigger(1, 1000000000); 	
		r.trigger(2, 2000000000);			
		r.trigger(1, 3000000000L);   	
		r.trigger(2, 4000000000L);		
		r.trigger(3, 5000000000L);		
		r.trigger(4, 6000000000L);			
		r.trigger(1, 7000000000L);	
		r.trigger(2, 8000000000L);		
		r.trigger(4, 3000000000L);	
		assertEquals("Competitor: 111 --- 00:00:01.000", r.getCompetitors().get(0).toString());
		assertEquals("Competitor: 222 --- 00:00:01.000", r.getCompetitors().get(1).toString());
		assertEquals("Competitor: 333 --- 00:00:01.000", r.getCompetitors().get(2).toString());
		assertEquals("Competitor: 444 --- 00:00:01.000", r.getCompetitors().get(3).toString());
	}
	
	
	@Test
	public void testReset(){
		ParaInd r = new ParaInd();
		r.addCompetitor(111); 
		r.addCompetitor(222); 
		r.addCompetitor(333);
		r.trigger(1, 1000000000);  		
		r.reset();					
		r.trigger(1, 2000000000);		
		r.trigger(2, 3000000000L);			
		r.trigger(3, 4000000000L);	
		r.trigger(4, 5000000000L);			
		r.reset();				
		r.trigger(3, 6000000000L);		
		r.trigger(4, 7000000000L);		
		r.trigger(1, 8000000000L);		
		r.trigger(2, 10000000000L);		
		assertEquals("Competitor: 111 --- 00:00:01.000", r.getCompetitors().get(0).toString());
		assertEquals("Competitor: 222 --- 00:00:02.000", r.getCompetitors().get(1).toString());
		assertEquals("Competitor: 333 --- Has Not Started", r.getCompetitors().get(2).toString());
		r.reset();
		assertEquals("Competitor: 333 --- Has Not Started", r.getCompetitors().get(2).toString());
	}
	
	@Test 
	public void TestRuntime(){
		ParaInd r = new ParaInd();
    	r.addCompetitor(1);
    	r.trigger(3, 1000000000);
    	r.trigger(4, 2000000000);
    	assertEquals(1000000000, r.runTime(0));
    	r.addCompetitor(2);
    	r.trigger(3, -1);
    	r.trigger(4, 1000000000);
    	assertEquals(-1, r.runTime(1));
    	r.addCompetitor(3);
    	r.trigger(4, 1000000000);
    	assertEquals(-1, r.runTime(2));
    	r.addCompetitor(4);
    	r.trigger(1, 1000000000);
    	r.trigger(2, -1);
    	assertEquals(-1, r.runTime(3));
    	r.addCompetitor(5);
    	assertEquals(-1, r.runTime(4));
    	r.addCompetitor(6);
    	r.trigger(3, 2000000000);
    	r.trigger(4, 1000000000);
    	assertEquals(-1, r.runTime(5)); 
	}
}
