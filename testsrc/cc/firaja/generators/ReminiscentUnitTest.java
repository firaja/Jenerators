package cc.firaja.generators;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author David Bertoldi
 */
public class ReminiscentUnitTest
{
	private static final int MAX_ITERATIONS = 10;
	private static final String TEST_STRING = "test_string";
	private static final String VAR = "var_string";
	private static final int TEST_INT = 9;
	private static final long TEST_LONG = 99L;
	private static final double TEST_DOUBLE = 1.123456d;
	private static final float TEST_FLOAT = 1.23f;
	private static final char TEST_CHAR = 'c';

	private ReminiscentGenerator<Boolean> classUnderTest = new ReminiscentGenerator<Boolean>()
	{
		@Override
		public Boolean generate()
		{
			return getBool(VAR);
		}
	};

	@Before
	public void setup()
	{
		//
	}

	@Test
	public void testConstructorNoArgs()
	{
		// GIVEN
		classUnderTest.store(VAR, false);

		// WHEN
		for (int i = 0; i < MAX_ITERATIONS; i++)
		{
			// THEN
			assertFalse(classUnderTest.next());
		}
		assertTrue(classUnderTest.hasNext());
	}

	@Test
	public void testConstructorWithLimit()
	{
		// GIVEN
		classUnderTest.resize(MAX_ITERATIONS);
		classUnderTest.store(VAR, false);

		// WHEN
		for (int i = 0; i < MAX_ITERATIONS; i++)
		{
			// THEN
			assertFalse(classUnderTest.next());
		}
		assertFalse(classUnderTest.hasNext());
	}

	@Test
	public void testStoreAndLoadString()
	{
		// GIVEN
		classUnderTest.store(VAR, TEST_STRING);

		// WHEN
		String result = classUnderTest.getString(VAR);

		// THEN
		assertEquals(TEST_STRING, result);
	}

	@Test
	public void testStoreAndLoadSubClass()
	{
		// GIVEN
		B data = new B(TEST_LONG);

		// WHEN
		classUnderTest.store(VAR, data);

		// THEN
		assertEquals(TEST_LONG * TEST_LONG, classUnderTest.get(VAR, A.class).x());
		assertEquals(TEST_LONG * TEST_LONG, classUnderTest.get(VAR, B.class).x());
	}

	@Test(expected = ClassCastException.class)
	public void testStoreAndLoadWrongClass()
	{
		// GIVEN

		// WHEN
		classUnderTest.store(VAR, TEST_INT);
		classUnderTest.getString(VAR);

		// THEN

	}

	@Test
	public void testStoreAndLoadInteger()
	{
		// GIVEN
		classUnderTest.store(VAR, TEST_INT);

		// WHEN
		int result = classUnderTest.getInt(VAR);

		// THEN
		assertEquals(TEST_INT, result);
	}

	@Test
	public void testStoreAndLoadDouble()
	{
		// GIVEN
		classUnderTest.store(VAR, TEST_DOUBLE);

		// WHEN
		double result = classUnderTest.getDouble(VAR);

		// THEN
		assertEquals(TEST_DOUBLE, result, 0.001);
	}

	@Test
	public void testStoreAndLoadFloat()
	{
		// GIVEN
		classUnderTest.store(VAR, TEST_FLOAT);

		// WHEN
		float result = classUnderTest.getFloat(VAR);

		// THEN
		assertEquals(TEST_FLOAT, result, 0.001);
	}

	@Test
	public void testStoreAndLoadChar()
	{
		// GIVEN
		classUnderTest.store(VAR, TEST_CHAR);

		// WHEN
		char result = classUnderTest.getChar(VAR);

		// THEN
		assertEquals(TEST_CHAR, result);
	}

	@Test(expected = NullPointerException.class)
	public void testStoreAndLoadNullExc()
	{
		// GIVEN
		classUnderTest.store(VAR, null);

		// WHEN
		classUnderTest.getChar(VAR);

		// THEN
	}

	@Test
	public void testStoreAndLoadNull()
	{
		// GIVEN
		classUnderTest.store(VAR, null);

		// WHEN
		Object result = classUnderTest.get(VAR);

		// THEN
		assertEquals(null, result);
	}

	@Test(expected = NullPointerException.class)
	public void testStoreAndLoadElse()
	{
		// GIVEN
		classUnderTest.store(VAR, null);

		// WHEN
		classUnderTest.get(VAR + "2");

		// THEN
	}

	private class A
	{
		private long x;

		protected A(long x)
		{
			this.x = x;
		}

		public long x()
		{
			return x;
		}
	}

	private class B extends A
	{

		B(long x)
		{
			super(x);
		}

		@Override
		public long x()
		{
			return super.x() * super.x();
		}
	}


}
