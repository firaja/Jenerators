package cc.firaja.generators;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author David Bertoldi
 */
public class SequentialNumericalAbstractGenerator
{

	private static final int MAX_ITERATIONS = 10;

	private AbstractGenerator<Integer> sequentialNumericalAbstractGenerator;

	@Before
	public void setup()
	{
		sequentialNumericalAbstractGenerator = new AbstractGenerator<Integer>(MAX_ITERATIONS)
		{
			@Override
			public Integer generate()
			{
				return c();
			}
		};
	}

	@Test
	public void testFirstIsZero()
	{
		// GIVEN

		// WHEN
		Object result = sequentialNumericalAbstractGenerator.next();

		// THEN
		assertEquals(0, result);
	}

	@Test
	public void testAscendant()
	{
		// GIVEN
		StringBuilder sb = new StringBuilder();

		// WHEN
		for (Integer i : sequentialNumericalAbstractGenerator)
		{
			sb.append(i);
		}

		// THEN
		assertEquals("0123456789", sb.toString());
	}

	/**
	 * Don't use a {@link AbstractGenerator} with this purpose.
	 */
	@Test
	public void testAcendantExternal()
	{
		// GIVEN
		class Ext
		{
			int number;

			Ext(int number)
			{
				this.number = number;
			}
		}
		Ext ext = new Ext(1);

		StringBuilder sb = new StringBuilder();
		sequentialNumericalAbstractGenerator = new AbstractGenerator<Integer>(MAX_ITERATIONS)
		{
			@Override
			public Integer generate()
			{
				return c() * ext.number;
			}
		};

		// WHEN
		for (Integer i : sequentialNumericalAbstractGenerator)
		{
			ext.number *= 2;
			sb.append(i);
		}

		// THEN
		// 0*1 + 1*2 + 2*4 + 3*8 + ...
		assertEquals("028246416038489620484608", sb.toString());
	}

	@Test
	public void testNumberOfIterations()
	{
		// GIVEN
		int i = 0;

		// WHEN
		while (sequentialNumericalAbstractGenerator.hasNext())
		{
			i++;
			sequentialNumericalAbstractGenerator.next();
		}

		// THEN
		assertEquals(MAX_ITERATIONS, i);
	}

	@Test(expected = NoSuchElementException.class)
	public void testExceed()
	{
		// GIVEN

		// WHEN
		while (sequentialNumericalAbstractGenerator.hasNext())
		{
			sequentialNumericalAbstractGenerator.next();
		}
		sequentialNumericalAbstractGenerator.next();

		// THEN
	}

	@Test
	public void testIteratorAndCursor()
	{
		// GIVEN
		final Iterator<Integer> iterator1 = sequentialNumericalAbstractGenerator.iterator();
		final Iterator<Integer> iterator2 = sequentialNumericalAbstractGenerator.iterator();

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
	public void testLast()
	{
		// GIVEN

		// WHEN
		sequentialNumericalAbstractGenerator.next(); // 0;
		sequentialNumericalAbstractGenerator.next(); // 1;
		int i = sequentialNumericalAbstractGenerator.last();
		sequentialNumericalAbstractGenerator.next(); // 2
		int j = sequentialNumericalAbstractGenerator.last();


		// THEN
		assertEquals(1, i);
		assertEquals(2, j);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeLimitInConstructor()
	{
		// GIVEN
		sequentialNumericalAbstractGenerator = new AbstractGenerator<Integer>(-1)
		{
			@Override
			public Integer generate()
			{
				return null;
			}
		};

		// WHEN

		// THEN
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeLimitInResize()
	{
		// GIVEN
		sequentialNumericalAbstractGenerator.resize(-1);

		// WHEN

		// THEN
	}

	@Test
	public void testConstructorCopy()
	{
		// GIVEN
		Generator<Integer> newGenerator;

		sequentialNumericalAbstractGenerator.next();
		sequentialNumericalAbstractGenerator.next();
		sequentialNumericalAbstractGenerator.next();
		int lastResult = sequentialNumericalAbstractGenerator.last();


		// WHEN
		newGenerator = new AbstractGenerator<Integer>(sequentialNumericalAbstractGenerator)
		{
			@Override
			public Integer generate()
			{
				return c();
			}
		};
		int nextResult = sequentialNumericalAbstractGenerator.next();

		// THEN
		assertEquals(lastResult, (int) newGenerator.last());
		assertEquals(nextResult, (int) newGenerator.next());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNullCopy()
	{
		// GIVEN


		// WHEN
		new AbstractGenerator<Object>(null)
		{
			@Override
			public Object generate()
			{
				return null;
			}
		};

		// THEN

	}

}
