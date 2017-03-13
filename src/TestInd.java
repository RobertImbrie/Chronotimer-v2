package src;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class TestInd {
    
	
	@Test
	public void testCompetitorList(){
		Ind r = new Ind();
		r.addCompetitor(111);
		assertEquals("Competitor: 111 --- Has Not Started", r.competitorList().get(0));
		r.addCompetitor(122);
		assertEquals("Competitor: 111 --- Has Not Started", r.competitorList().get(0));
		assertEquals("Competitor: 122 --- Has Not Started", r.competitorList().get(1));
		r.removeCompetitorByBib(111);
		assertEquals("Competitor: 122 --- Has Not Started", r.competitorList().get(0));
		r.addCompetitor(333);
		r.start(1000000000);
		assertEquals("Competitor: 122 --- DNF", r.competitorList().get(0));
		assertEquals("Competitor: 333 --- Has Not Started", r.competitorList().get(1));
		r.end(2000000000);
		assertEquals("Competitor: 122 --- 1 Seconds", r.competitorList().get(0));
		assertEquals("Competitor: 333 --- Has Not Started", r.competitorList().get(1));
		r.start(3000000000L);
		assertEquals("Competitor: 122 --- 1 Seconds", r.competitorList().get(0));
		assertEquals("Competitor: 333 --- DNF", r.competitorList().get(1));
		r.end(7000000000L);
		assertEquals("Competitor: 122 --- 1 Seconds", r.competitorList().get(0));
		assertEquals("Competitor: 333 --- 4 Seconds", r.competitorList().get(1));
		r.removeCompetitorByBib(122);
		assertEquals("Competitor: 333 --- 4 Seconds", r.competitorList().get(0));
	}
	
	@Test
	public void testAddCompetitor(){
		Ind r = new Ind();
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
		Ind r = new Ind();
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
		r.start(1000000000);
		r.end(2000000000);
		r.start(3000000000L);
		r.end(4000000000L);
		r.start(5000000000L);
		assertEquals("Competitor: 111 --- 1 Seconds", r.clear()[0]);
		r.addCompetitor(111);
		r.addCompetitor(222);
		r.addCompetitor(333);
		r.start(1000000000);
		r.end(2000000000);
		r.start(3000000000L);
		r.end(4000000000L);
		r.start(5000000000L);
		assertEquals("Competitor: 222 --- 1 Seconds", r.clear()[1]);
		r.addCompetitor(111);
		r.addCompetitor(222);
		r.addCompetitor(333);
		r.start(1000000000);
		r.end(2000000000);
		r.start(3000000000L);
		r.end(4000000000L);
		r.start(5000000000L);
		assertEquals("Competitor: 333 --- DNF", r.clear()[2]);
	}
	
	@Test
	public void testRemoveCompetitorByPos(){
		Ind r = new Ind();
		r.addCompetitor(111);
		assertEquals("Competitor: 111 --- Has Not Started", r.removeCompetitorByPos(0));
		r.addCompetitor(111);
		r.addCompetitor(222);
		r.start(1000000000);
		r.end(3000000000L);
		assertEquals("Competitor: 222 --- Has Not Started", r.removeCompetitorByPos(1));
		assertEquals("Competitor: 111 --- 2 Seconds", r.removeCompetitorByPos(0));
		r.addCompetitor(111);
		r.addCompetitor(222);
		r.addCompetitor(333);
		r.start(1000000000);
		r.end(2000000000);
		r.start(3000000000L);
		r.end(5000000000L);
		assertEquals("Competitor: 222 --- 2 Seconds", r.removeCompetitorByPos(1));
		assertEquals("Competitor: 111 --- 1 Seconds", r.removeCompetitorByPos(0));
		r.start(6000000000L);
		r.end(8000000000L);
		assertEquals("Competitor: 333 --- 2 Seconds", r.removeCompetitorByPos(0));
	}
	
	@Test
	public void testDidNotFinish(){
		Ind r = new Ind();
		r.addCompetitor(111);
		r.addCompetitor(222);
		r.start(1000000000);
		r.didNotFinish();
		r.start(2000000000);
		r.end(3000000000L);
		assertEquals("Competitor: 111 --- DNF", r.getCompetitors().get(0).toString());
		assertEquals("Competitor: 222 --- 1 Seconds", r.getCompetitors().get(1).toString());
		r.clear();
		r.addCompetitor(111);
		r.addCompetitor(222);
		r.addCompetitor(333);
		r.didNotFinish();
		r.start(1000000000);
		r.didNotFinish();
		r.start(2000000000);
		r.end(4000000000L);
		assertEquals("Competitor: 111 --- DNF", r.getCompetitors().get(0).toString());
		assertEquals("Competitor: 222 --- DNF", r.getCompetitors().get(1).toString());
		assertEquals("Competitor: 333 --- 2 Seconds", r.getCompetitors().get(2).toString());
	}
	
	@Test
	public void testEnd2(){
		Ind r = new Ind();
		r.addCompetitor(111);
		r.addCompetitor(222);
		r.addCompetitor(333);
		r.addCompetitor(444);
		r.start(1000000000); 		//111 Start 1s
		r.end(2000000000);			
		r.start(3000000000L);   	//222 Start	3s
		r.end(4000000000L, 1);		//222 End 4s
		r.start(5000000000L);		//333 Start 5s
		r.end(6000000000L);			//333 End 6s
		r.start(7000000000L);		//444 Start 7s
		r.end(8000000000L, 3);		//444 End 8s
		r.end(3000000000L, 0);		//111 End 3s
		assertEquals("Competitor: 111 --- 2 Seconds", r.getCompetitors().get(0).toString());
		assertEquals("Competitor: 222 --- 1 Seconds", r.getCompetitors().get(1).toString());
		assertEquals("Competitor: 333 --- 1 Seconds", r.getCompetitors().get(2).toString());
		assertEquals("Competitor: 444 --- 1 Seconds", r.getCompetitors().get(3).toString());		
	}
	
	@Test
	public void testReset(){
		Ind r = new Ind();
		r.addCompetitor(111); 
		r.addCompetitor(222); 
		r.addCompetitor(333);
		r.start(1000000000);  		assertEquals(1, r.curStart); assertEquals(0, r.curFinish);
		r.reset();					assertEquals(0, r.curStart); assertEquals(0, r.curFinish);
		r.start(2000000000);		assertEquals(1, r.curStart); assertEquals(0, r.curFinish);
		r.end(3000000000L);			assertEquals(1, r.curStart); assertEquals(1, r.curFinish);
		r.start(4000000000L);		assertEquals(2, r.curStart); assertEquals(1, r.curFinish);
		r.end(5000000000L);			assertEquals(2, r.curStart); assertEquals(2, r.curFinish);
		r.reset();					assertEquals(1, r.curStart); assertEquals(1, r.curFinish);
		r.start(6000000000L);		assertEquals(2, r.curStart); assertEquals(1, r.curFinish);
		r.end(7000000000L);			assertEquals(2, r.curStart); assertEquals(2, r.curFinish);
		r.start(8000000000L);		assertEquals(3, r.curStart); assertEquals(2, r.curFinish);
		r.end(10000000000L);		assertEquals(3, r.curStart); assertEquals(3, r.curFinish);
		assertEquals("Competitor: 111 --- 1 Seconds", r.getCompetitors().get(0).toString());
		assertEquals("Competitor: 222 --- 1 Seconds", r.getCompetitors().get(1).toString());
		assertEquals("Competitor: 333 --- 2 Seconds", r.getCompetitors().get(2).toString());
		r.reset();
		assertEquals("Competitor: 333 --- Has Not Started", r.getCompetitors().get(2).toString());
	}
	
    @Test
    public void testRunEnd(){
    	Ind run = new Ind();
    	run.addCompetitor(1);
    	run.start(1000000000);
    	run.end(2000000000);
    	assertEquals(2000000000, run.getCompetitors().get(0).getEndTime());
    	assertEquals(true, run.getCompetitors().get(0).getFinished());
     
    	run = new Ind();
    	run.addCompetitor(1);
    	run.start(1000);
    	run.end(-1);
    	assertEquals(-1, run.getCompetitors().get(0).getEndTime());
    	assertEquals(false, run.getCompetitors().get(0).getFinished());
     
    	run = new Ind();
    	run.addCompetitor(1);
    	run.end(1000000000);
    	assertEquals(-1, run.getCompetitors().get(0).getEndTime());
    	assertEquals(false, run.getCompetitors().get(0).getFinished());
    	
    	run = new Ind();
    	run.addCompetitor(1);
    	run.start(-1);
    	run.end(1000000000);
    	assertEquals(-1, run.getCompetitors().get(0).getEndTime());
    	assertEquals(false, run.getCompetitors().get(0).getFinished());
     
    	run = new Ind();
    	run.addCompetitor(1);
    	run.start(2000000000);
    	run.end(1000000000);
    	assertEquals(-1, run.getCompetitors().get(0).getEndTime());
    	assertEquals(false, run.getCompetitors().get(0).getFinished());
    }
    
   
    @Test
    public void testStart(){
    	//start(Time t)
    	Ind r = new Ind();
    	r.addCompetitor(1);
    	r.start(1000000000);
    	assertFalse(r.getCompetitors().get(0).getStartTime() == -1);
    	assertTrue(r.getCompetitors().get(0).getStarted());
    }
    
    @Test
    public void testStart2(){
    	//start(Time t, int position)
    	Ind r = new Ind();
    	r.addCompetitor(1);
    	r.addCompetitor(2);
    	r.addCompetitor(3);
    	r.start(1000000000, 0);
    	r.start(2000000000, 1);
    	r.start(3000000000L, 2);
    	assertEquals(r.getCompetitors().get(0).getStartTime(), 1000000000);
    	assertEquals(r.getCompetitors().get(1).getStartTime(), 2000000000);
    	assertEquals(r.getCompetitors().get(2).getStartTime(), 3000000000L);
    	assertTrue(r.getCompetitors().get(0).getStarted());
    	assertTrue(r.getCompetitors().get(1).getStarted());
    	assertTrue(r.getCompetitors().get(2).getStarted());
    }
    
    @Test
    public void testRunTime(){
    	Ind r = new Ind();
    	
    	r.addCompetitor(1);
    	r.start(1000000000);
    	r.end(2000000000);
    	assertEquals(1000000000, r.runTime(0));
    	
    	r.addCompetitor(2);
    	r.start(-1);
    	r.end(1000000000);
    	assertEquals(-1, r.runTime(1));
    	
    	r.addCompetitor(3);
    	r.end(1000000000);
    	assertEquals(-1, r.runTime(2));
    	
    	r.addCompetitor(4);
    	r.start(1000000000);
    	r.end(-1);
    	assertEquals(-1, r.runTime(3));
    	
    	r.addCompetitor(5);
    	assertEquals(-1, r.runTime(4));
    	
    	r.addCompetitor(6);
    	r.start(2000000000);
    	r.end(1000000000);
    	assertEquals(-1, r.runTime(5)); 
    }
    
    @Test
    public void testRemoveCompetitorByBib(){
    	Ind r = new Ind();
    	
    	r.addCompetitor(111);
		assertEquals("Competitor: 111 --- Has Not Started", r.removeCompetitorByBib(111));
		r.addCompetitor(111);
		r.addCompetitor(222);
		r.start(1000000000);
		r.end(3000000000L);
		assertEquals("Competitor: 222 --- Has Not Started", r.removeCompetitorByBib(222));
		assertEquals("Competitor: 111 --- 2 Seconds", r.removeCompetitorByBib(111));
		r.addCompetitor(111);
		r.addCompetitor(222);
		r.addCompetitor(333);
		r.start(1000000000);
		r.end(2000000000);
		r.start(3000000000L);
		r.end(5000000000L);
		assertEquals("Competitor: 222 --- 2 Seconds", r.removeCompetitorByBib(222));
		assertEquals("Competitor: 111 --- 1 Seconds", r.removeCompetitorByBib(111));
		r.start(6000000000L);
		r.end(8000000000L);
		assertEquals("Competitor: 333 --- 2 Seconds", r.removeCompetitorByBib(333));
    }
 
    
    /* DON'T NEED FOR SPRINT 1
    @Test
    public void testRunSwapNext(){
    	Run run = new Run();
    	for(int i = 0; i < 100; i++){
    		run.addCompetitor(i);
    	}
    	run.swapNext();
    	assertEquals(run.getCompetitors().indexOf(new Competitor(0)), 99);
    	assertEquals(run.getCompetitors().indexOf(new Competitor(99)), 0);
     
    	run = new Run();
    	run.swapNext();
    	run.addCompetitor(0);
    	run.swapNext();
    	run.addCompetitor(1);
    	run.swapNext();
    	assertEquals(run.getCompetitors().indexOf(new Competitor(0)), 1);
    	assertEquals(run.getCompetitors().indexOf(new Competitor(1)), 0);
    }
    */
    
}

	

