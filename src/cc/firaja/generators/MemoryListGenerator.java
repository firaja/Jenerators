package cc.firaja.generators;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * @author David Bertoldi
 */
public abstract class MemoryListGenerator<T> extends AbstractGenerator<T> implements StatefulGenerator<T>
{

	private List<State> memory = new ArrayList<>();

	public MemoryListGenerator()
	{
		super();
	}

	public MemoryListGenerator(final int end)
	{
		super(end);
	}

	@Override
	protected void after()
	{
		saveState();
	}

	protected void saveState()
	{
		memory.add(new State(last()));
	}

	protected T getResult(final int c)
	{
		Objects.requireNonNull(c, "c cannot be null");
		if (c < memory.size())
		{
			return memory.get(c).result;
		}
		throw new NoSuchElementException(" Cannot find memory location with state id " + c);
	}



	private class State
	{
		public T result;

		public State(T r)
		{
			this.result = r;
		}
	}
}
