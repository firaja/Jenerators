package cc.firaja.generators;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author David Bertoldi
 */
public abstract class ReminiscentGenerator<T> extends AbstractGenerator<T> implements VarGenerator<T>
{
	private Map<String, Object> vars = new HashMap<>();


	public ReminiscentGenerator()
	{
		//
	}

	public ReminiscentGenerator(final int end)
	{
		super(end);
	}


	public Object get(final String varName)
	{
		return get(varName, Object.class);
	}

	@SuppressWarnings("unchecked")
	public <M> M get(final String varName, Class<? extends M> clazz)
	{
		Objects.requireNonNull(varName, "varName cannot be null");
		if (vars.containsKey(varName))
		{
			Object result = vars.get(varName);
			if (result == null || clazz.isInstance(result))
			{

				return (M) result;
			}
			else
			{
				throw new ClassCastException("Cannot cast \"" + varName + "\" of type " +
						result != null ? result.getClass().getName() : "null" +
						" value to " + clazz.getName());
			}
		}
		throw new NullPointerException("No definition for variable " + varName);
	}


	public long getLong(final String varName)
	{
		return (long) get(varName, Long.class);
	}


	public int getInt(final String varName)
	{
		return (int) get(varName, Integer.class);
	}


	public double getDouble(final String varName)
	{
		return (double) get(varName, Double.class);
	}


	public boolean getBool(final String varName)
	{
		return (boolean) get(varName, Boolean.class);
	}


	public String getString(final String varName)
	{
		return (String) get(varName, String.class);
	}


	public float getFloat(final String varName)
	{
		return (float) get(varName, Float.class);
	}


	public char getChar(final String varName)
	{
		return (char) get(varName, Character.class);
	}


	public void store(final String varName, final Object var)
	{
		Objects.requireNonNull(varName, "varName cannot be null");
		vars.put(varName, var);
	}
}
