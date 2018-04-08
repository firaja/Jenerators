package cc.firaja.generators;

import java.util.NoSuchElementException;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author David Bertoldi
 */
public class MemoryListUnitTest
{

	private static final int MAX_ITERATIONS = 10;
	private static final String VAR = "var_string";

	MemoryListGenerator<Boolean> classUnderTest = new MemoryListGenerator<Boolean>()
	{
		@Override
		public Boolean generate()
		{
			if (c() == 0)
			{
				return false;
			}
			return !getResult(c() - 1);
		}
	};


	@Test
	public void testConstructorNoArgs()
	{
		// GIVEN

		// WHEN
		for (int i = 0; i < MAX_ITERATIONS; i++)
		{
			// THEN
			if (i % 2 == 0)
			{
				assertFalse(classUnderTest.next());
			}
			else
			{
				assertTrue(classUnderTest.next());
			}
		}
		assertTrue(classUnderTest.hasNext());
	}

	@Test
	public void testConstructorWithLimit()
	{
		// GIVEN
		classUnderTest.resize(MAX_ITERATIONS);

		// WHEN
		for (int i = 0; i < MAX_ITERATIONS; i++)
		{
			// THEN
			if (i % 2 == 0)
			{
				assertFalse(classUnderTest.next());
			}
			else
			{
				assertTrue(classUnderTest.next());
			}
		}
		assertFalse(classUnderTest.hasNext());
	}


	@Test(expected = NoSuchElementException.class)
	public void testGetResultExc()
	{
		// GIVEN
		classUnderTest.resize(MAX_ITERATIONS);

		// WHEN
		classUnderTest.getResult(50);
		
	}

}
