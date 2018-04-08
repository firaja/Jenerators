package cc.firaja.generators;

import java.util.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author David Bertoldi
 */
public class NullAbstractGenerator
{
	private static final int MAX_ITERATIONS = 10;

	private AbstractGenerator<Object> nullGenerator;

	@Before
	public void setup()
	{
		nullGenerator = new AbstractGenerator<Object>(MAX_ITERATIONS)
		{
			@Override
			public Object generate()
			{
				return null;
			}
		};
	}

	@Test
	public void testFirstIsNull()
	{
		// GIVEN

		// WHEN
		Object result = nullGenerator.next();

		// THEN
		assertNull(result);
	}

	@Test
	public void testAllNull()
	{
		// GIVEN
		List<Object> results = new ArrayList<>();

		// WHEN
		nullGenerator.forEachRemaining(results::add);

		// THEN
		assertTrue(results.stream().allMatch(Objects::isNull));
	}

	@Test
	public void testNumberOfIterations()
	{
		// GIVEN
		int i = 0;

		// WHEN
		while (nullGenerator.hasNext())
		{
			i++;
			nullGenerator.next();
		}

		// THEN
		assertEquals(MAX_ITERATIONS, i);
	}

	@Test(expected = NoSuchElementException.class)
	public void testExceed()
	{
		// GIVEN

		// WHEN
		while (nullGenerator.hasNext())
		{
			nullGenerator.next();
		}
		nullGenerator.next();

		// THEN
	}

	@Test
	public void testIterator()
	{
		// GIVEN
		final Iterator<Object> iterator1 = nullGenerator.iterator();
		final Iterator<Object> iterator2 = nullGenerator.iterator();

		// WHEN
		int middleCursor = 0;
		while (iterator1.hasNext())
		{
			middleCursor++;
			iterator1.next();
		}


		int finalCursor = 0;
		while (iterator2.hasNext())
		{
			finalCursor++;
			iterator2.next();
		}

		// THEN
		assertEquals(MAX_ITERATIONS, middleCursor);
		assertEquals(0, finalCursor);
	}

	@Test
	public void testIteratorAndCursor()
	{
		// GIVEN
		final Iterator<Object> iterator1 = nullGenerator.iterator();

		// WHEN
		int middleCursor = 0;
		while (iterator1.hasNext())
		{
			middleCursor++;
			iterator1.next();
		}


		int finalCursor = 0;
		while (nullGenerator.hasNext())
		{
			finalCursor++;
			nullGenerator.next();
		}

		// THEN
		assertEquals(MAX_ITERATIONS, middleCursor);
		assertEquals(0, finalCursor);
	}


	@Test
	public void testReset()
	{
		// GIVEN

		// WHEN
		int middleCursor = 0;
		while (nullGenerator.hasNext())
		{
			middleCursor++;
			nullGenerator.next();
		}

		nullGenerator.reset();

		int finalCursor = 0;
		while (nullGenerator.hasNext())
		{
			finalCursor++;
			nullGenerator.next();
		}

		// THEN
		assertEquals(MAX_ITERATIONS, middleCursor);
		assertEquals(MAX_ITERATIONS, finalCursor);
	}


}
