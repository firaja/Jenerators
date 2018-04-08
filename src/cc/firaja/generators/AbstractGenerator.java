package cc.firaja.generators;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Stateless abstract generator.
 *
 * @author David Bertoldi
 * @version 0.1
 * @see Generator
 * @since 0.1
 */
public abstract class AbstractGenerator<T> implements Generator<T>
{
	private int originalLimit;
	private int originalCursor;
	private boolean originalHasEnd;

	private boolean hasLimit = false;
	private int limit;
	private int cursor = 0;
	private T result;


	/**
	 * Basic constructor that creates a generator
	 * without limit.
	 */
	public AbstractGenerator()
	{
		storeOriginals();
	}

	/**
	 * Creates a generator with a limit. This number determines
	 * the number of steps that the generator can do and it can be
	 * set through {@link #resize(int)}
	 *
	 * @param limit the number of steps that the generator can do.
	 * @see #resize(int)
	 */
	public AbstractGenerator(final int limit)
	{
		if (limit < 0)
		{
			throw new IllegalArgumentException("End cannot be negative. Found: " + limit);
		}
		this.hasLimit = true;
		this.limit = limit;

		storeOriginals();
	}

	/**
	 * Copy constructor.
	 * Note that when calling {@link #reset()}, the constructor
	 * is re-initialized to this state.
	 *
	 * @param copy the generator to be copied.
	 * @throws IllegalArgumentException if parameter copy is null
	 */
	public AbstractGenerator(final AbstractGenerator<T> copy)
	{
		if (copy == null)
		{
			throw new IllegalArgumentException("Argument cannot be null");
		}
		this.hasLimit = copy.hasLimit;
		this.limit = copy.limit;
		this.cursor = copy.cursor;
		this.result = copy.result;
		storeOriginals();
	}


	@Override
	public void reset()
	{
		cursor = originalCursor;
		limit = originalLimit;
		hasLimit = originalHasEnd;
		result = null;
	}


	@Override
	public T last()
	{
		return result;
	}


	@Override
	public Iterator<T> iterator()
	{
		return this;
	}

	private void internalGenerate()
	{
		before();

		result = generate();

		after();
	}

	/**
	 * This method is always executed <b>before</b> the computation of
	 * {@link #generate()}. By default this method is empty, but
	 * can be overridden while instantiating the generator.
	 */
	protected void before()
	{
		//
	}

	/**
	 * This method is always executed <b>after</b> the computation of
	 * {@link #generate()}. By default this method is empty, but
	 * can be overridden while instantiating the generator.
	 */
	protected void after()
	{
		//
	}

	/**
	 * Returns the current position of the cursor.
	 *
	 * @return the current position of the cursor
	 */
	protected int c()
	{
		return this.cursor;
	}

	/**
	 * Sets the current cursor to a new position.
	 * This information is not persisted when calling {@link #reset()}.
	 *
	 * @param cursor the new position (starting from 0).
	 * @throws IllegalArgumentException if cursor is negative.
	 */
	protected void c(final int cursor)
	{
		if (cursor < 0)
		{
			throw new IllegalArgumentException("Cursor cannot be negative. Found: " + cursor);
		}
		this.cursor = cursor;
	}


	@Override
	public T next()
	{
		if (!hasNext())
		{
			throw new NoSuchElementException();
		}
		internalGenerate();
		cursor++;
		return result;
	}


	@Override
	public boolean hasNext()
	{
		return !hasLimit || cursor < limit;
	}


	private void storeOriginals()
	{
		this.originalCursor = cursor;
		this.originalLimit = limit;
		this.originalHasEnd = hasLimit;
	}

	/**
	 * Sets the limit of the generator. The new limit must be
	 * greater than the current one, otherwise a {@link IllegalArgumentException} is thrown.
	 * This information is not persisted when calling {@link #reset()}.
	 *
	 * @param limit new limit of the generator.
	 * @throws IllegalArgumentException if parameter limit is not greater than the current limit.
	 */
	@Override
	public void resize(int limit)
	{
		if (limit < this.limit)
		{
			throw new IllegalArgumentException("New limit cannot be less than the current one.");
		}
		this.hasLimit = true;
		this.limit = limit;
	}
}
