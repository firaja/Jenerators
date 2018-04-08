package cc.firaja.generators;

/**
 * @author David Bertoldi
 */
public interface VarGenerator<T> extends Generator<T>
{

	void store(final String varName, final Object var);
}
