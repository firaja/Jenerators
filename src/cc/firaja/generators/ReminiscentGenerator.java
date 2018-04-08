package cc.firaja.generators;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author David Bertoldi
 */
public abstract class ReminiscentGenerator<T> extends AbstractGenerator<T> implements VarGenerator<T>
{
	private Map<String, Object> vars = new ConcurrentHashMap<>();


	public ReminiscentGenerator()
	{

	}

	public ReminiscentGenerator(final int end)
	{
		super(end);
	}


	protected Object get(final String varName)
	{
		Objects.requireNonNull(varName, "varName cannot be null");
		if (vars.containsKey(varName))
		{
			return vars.get(varName);
		}
		throw new NullPointerException("No definition for variable " + varName);
	}


	public long getLong(final String varName)
	{
		return (long) get(varName);
	}


	public int getInt(final String varName)
	{
		return (int) get(varName);
	}


	public double getDouble(final String varName)
	{
		return (double) get(varName);
	}


	public boolean getBool(final String varName)
	{
		return (boolean) get(varName);
	}


	public String getString(final String varName)
	{
		return (String) get(varName);
	}


	public void store(final String varName, final Object var)
	{
		Objects.requireNonNull(varName, "varName cannot be null");
		vars.put(varName, var);
	}
}
