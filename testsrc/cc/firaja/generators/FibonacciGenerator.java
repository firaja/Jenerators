package cc.firaja.generators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author David Bertoldi
 */
public class FibonacciGenerator
{
	private static final int MAX_ITERATIONS = 10;
	private static final List<Long> FIBONACCI_TEST = Arrays.asList(1L, 1L, 2L, 3L, 5L, 8L, 13L, 21L, 34L, 55L);

	@Before
	public void setup()
	{
		//
	}

	@Test
	public void testFibonacciWithExternal()
	{
		//GIVEN
		final List<Long> results = new ArrayList<>();
		VarGenerator<Long> externalVariableGenerator = new ReminiscentGenerator<Long>(MAX_ITERATIONS)
		{
			@Override
			public Long generate()
			{
				if (c() == 0)
				{
					return getLong("0");
				}
				else if (c() == 1)
				{
					return getLong("1");
				}
				else
				{
					long r = getLong(String.valueOf(c() - 1)) + getLong(String.valueOf(c() - 2));
					store(String.valueOf(c()), r);
					return r;
				}
			}
		};
		externalVariableGenerator.store("0", 1L);
		externalVariableGenerator.store("1", 1L);

		// WHEN
		externalVariableGenerator.forEachRemaining(results::add);


		// THEN
		assertThat(results, is(FIBONACCI_TEST));

	}


	@Test
	public void testFibonacciWithInternal()
	{
		//GIVEN
		final List<Long> results = new ArrayList<>();
		StatefulGenerator<Long> externalVariableGenerator = new MemoryListGenerator<Long>(MAX_ITERATIONS)
		{
			@Override
			public Long generate()
			{
				if (c() == 0)
				{
					return 1L;
				}
				else if (c() == 1)
				{
					return 1L;
				}
				else
				{
					return getResult(c() - 1) + getResult(c() - 2);
				}
			}
		};


		// WHEN
		externalVariableGenerator.forEachRemaining(results::add);


		// THEN
		assertThat(results, is(FIBONACCI_TEST));

	}

}
