package src;
import static org.junit.Assert.*;
import org.junit.Test;

public class TestTime {
	
	@Test
	//Test Time.java parseTime method
	public void testParseTime(){
		
		Ind r = new Ind();
		r.addCompetitor(111);
		assertEquals("Competitor: 111 --- Has Not Started", r.clear()[0]);
		r.addCompetitor(111);
		r.start(1000000000);
		r.end(2000000000L);
		assertEquals("Competitor: 111 --- 00:00:01.000", r.clear()[0]);
	}

}
