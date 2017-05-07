package src;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestCompetitor {

	@Test
	//Test Competitor.java start method
	public void testCompetitorStart() {
		//Test 1
		Competitor a = new Competitor(1);
		a.start(1000);
		assertTrue(a.getStarted());
		assertEquals(a.getStartTime(), 1000);
		//Test 2
		Competitor b = new Competitor(3);
		a.start(-1);
		assertEquals(false, b.getStarted());
		assertEquals(-1, b.getStartTime());
	}

	@Test
	//Test Competitor.java toString method
	public void testCompetitorToString() {
		//Test 1
		Competitor a = new Competitor(1);
		assertEquals(a.toString(), "Competitor: 1 --- Has Not Started");
		a.start(1000000000);
		assertEquals(a.toString(), "Competitor: 1 --- DNF");
		a.end(3000000000L);
		assertEquals(a.toString(), "Competitor: 1 --- 00:00:02.000");
		//Test 2
		Competitor b = new Competitor(123);
		assertEquals("Competitor: 123 --- Has Not Started", b.toString());
		b.start(3000000000L);
		b.end(60333000000L);
		assertEquals("Competitor: 123 --- 00:00:57.333", b.toString());
		//Test 3
		Competitor c = new Competitor(999);
		c.start(1000000999);
		c.end(6000000789L);
		assertEquals("Competitor: 999 --- 00:00:04.999", c.toString());
		c.setStarted(false);
		assertEquals("Competitor: 999 --- Has Not Started", c.toString());
		c.setStarted(true);
		assertEquals("Competitor: 999 --- 00:00:04.999", c.toString());
		c.setFinished(false);
		assertEquals("Competitor: 999 --- DNF", c.toString());
		//Test 4
		Competitor d = new Competitor(758);
		d.start(2000000000);
		d.end(1000000000);
		assertEquals("Competitor: 758 --- DNF", d.toString());
		d = new Competitor(758);
		d.start(-1);
		d.end(1000000000);
		assertEquals("Competitor: 758 --- Has Not Started", d.toString());
	}

	@Test
	//Test Competitor runTime method
	public void testCompetitorRunTime() {
		//Test 1
		Competitor a = new Competitor(1);
		a.start(1000);
		a.end(2000);
		assertEquals(a.runTime(), 1000);
		//Test 2
		Competitor b = new Competitor(2);
		b.start(1000);
		b.setStarted(false);
		b.end(2000);
		assertEquals(b.runTime(), -1);
		//Test 3
		Competitor c = new Competitor(3);
		c.start(-1);
		c.end(2000);
		assertEquals(c.runTime(), -1);
		//Test 4
		Competitor d = new Competitor(4);
		d.start(1000);
		d.end(-1);
		assertEquals(d.runTime(), -1);
		//Test 5
		Competitor e = new Competitor(5);
		e.start(-1);
		e.setStarted(false);
		e.end(2000);
		assertEquals(d.runTime(), -1);
		//Test 6
		Competitor f = new Competitor(6);
		f.start(1000);
		f.setStarted(false);
		f.end(-1);
		assertEquals(f.runTime(), -1);
		//Test 7
		Competitor g = new Competitor(7);
		g.start(2000);
		g.end(1000);
		assertEquals(g.runTime(), -1);
	}

	@Test
	//Test Competitor end method
	public void testCompetitorEnd() {
		//Test 1
		Competitor a = new Competitor(1);
		a.start(1000);
		a.end(2000);
		assertEquals(a.getEndTime(), 2000);
		assertEquals(a.getFinished(), true);
		//Test 2
		Competitor b = new Competitor(2);
		b.start(1000);
		b.end(-1);
		assertEquals(b.getEndTime(), -1);
		assertEquals(b.getFinished(), false);
		//Test 3
		Competitor c = new Competitor(3);
		c.start(-1);
		c.end(2000);
		assertEquals(b.getEndTime(), -1);
		assertEquals(b.getFinished(), false);
		//Test 4
		Competitor d = new Competitor(3);
		d.start(-1);
		d.end(2000);
		assertEquals(b.getEndTime(), -1);
		assertEquals(b.getFinished(), false);
	}
}
