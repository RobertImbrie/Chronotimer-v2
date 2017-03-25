package src;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestCompetitor {

	@Test
	public void testCompetitorStart() {
		Competitor a = new Competitor(1);
		a.start(1000);
		assertTrue(a.getStarted());
		assertEquals(a.getStartTime(), 1000);
		
		Competitor b = new Competitor(3);
		a.start(-1);
		assertEquals(false, b.getStarted());
		assertEquals(-1, b.getStartTime());
	}

	@Test
	public void testCompetitorToString() {
		Competitor a = new Competitor(1);
		assertEquals(a.toString(), "Competitor: 1 --- Has Not Started");

		a.start(1000000000);
		assertEquals(a.toString(), "Competitor: 1 --- DNF");

		a.end(3000000000L);
		assertEquals(a.toString(), "Competitor: 1 --- 2 Seconds");
		
		Competitor b = new Competitor(123);
		assertEquals("Competitor: 123 --- Has Not Started", b.toString());
		
		b.start(3000000000L);
		b.end(60333000000L);
		assertEquals("Competitor: 123 --- 57.333 Seconds", b.toString());
		
		Competitor c = new Competitor(999);
		c.start(1000000999);
		c.end(6000000789L);
		assertEquals("Competitor: 999 --- 5 Seconds", c.toString());
		
		c.setStarted(false);
		assertEquals("Competitor: 999 --- Has Not Started", c.toString());
		
		c.setStarted(true);
		assertEquals("Competitor: 999 --- 5 Seconds", c.toString());
		
		c.setFinished(false);
		assertEquals("Competitor: 999 --- DNF", c.toString());
		
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
	public void testCompetitorRunTime() {
		Competitor a = new Competitor(1);
		a.start(1000);
		a.end(2000);
		assertEquals(a.runTime(), 1000);

		Competitor b = new Competitor(2);
		b.start(1000);
		b.setStarted(false);
		b.end(2000);
		assertEquals(b.runTime(), -1);

		Competitor c = new Competitor(3);
		c.start(-1);
		c.end(2000);
		assertEquals(c.runTime(), -1);

		Competitor d = new Competitor(4);
		d.start(1000);
		d.end(-1);
		assertEquals(d.runTime(), -1);

		Competitor e = new Competitor(5);
		e.start(-1);
		e.setStarted(false);
		e.end(2000);
		assertEquals(d.runTime(), -1);

		Competitor f = new Competitor(6);
		f.start(1000);
		f.setStarted(false);
		f.end(-1);
		assertEquals(f.runTime(), -1);

		Competitor g = new Competitor(7);
		g.start(2000);
		g.end(1000);
		assertEquals(g.runTime(), -1);
	}

	@Test
	public void testCompetitorEnd() {
		Competitor a = new Competitor(1);
		a.start(1000);
		a.end(2000);
		assertEquals(a.getEndTime(), 2000);
		assertEquals(a.getFinished(), true);

		Competitor b = new Competitor(2);
		b.start(1000);
		b.end(-1);
		assertEquals(b.getEndTime(), -1);
		assertEquals(b.getFinished(), false);

		Competitor c = new Competitor(3);
		c.start(-1);
		c.end(2000);
		assertEquals(b.getEndTime(), -1);
		assertEquals(b.getFinished(), false);

		Competitor d = new Competitor(3);
		d.start(-1);
		d.end(2000);
		assertEquals(b.getEndTime(), -1);
		assertEquals(b.getFinished(), false);
	}
}
