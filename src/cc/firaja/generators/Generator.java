package cc.firaja.generators;

import java.util.Iterator;

/**
 * <p>
 * A generator over a dynamic logic. The logic is described in
 * {@link #generate()} method.
 * Best way to implement a generic generator is through an
 * abstract class and then instantiated anonymously.
 * </p>
 * <p>
 * It extends {@link Iterator} and  {@link Iterable} interfaces but
 * {@link #remove()} should not be supported.
 * </p>
 *
 * @author David Bertoldi
 * @version 1.0
 * @see Iterator
 * @see Iterable
 * @since 1.0
 */
public interface Generator<T> extends Iterator<T>, Iterable<T>
{

	/**
	 * Generates the <i>nth</i> result over a stepped logic.
	 * All the results from each step are returned from
	 * {@link #last()} and {@link #next()}.
	 *
	 * @return the <i>nth</i> step of an iteration
	 * @see #last()
	 * @see #next()
	 */
	T generate();

	/**
	 * Returns the last generated result by this generator.
	 * The call does not change the status of the object
	 *
	 * @return the last generated result.
	 */
	T last();

	/**
	 * Resets the generator to the initial configuration set when
	 * the object has been created the first time.
	 */
	void reset();


	/**
	 * Sets the limit of the generator. The new limit must be
	 * greater than the current one.
	 * This information is not persisted when calling {@link #reset()}.
	 *
	 * @param limit new limit of the generator.
	 */
	void resize(final int limit);

}
