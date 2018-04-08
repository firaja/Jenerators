package cc.firaja.generators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author David Bertoldi
 */
public class ReminescentFuncTest
{

	private static final List<Integer> OFFSET_LIST = Arrays.asList(100, 96, 116, 102, 96);


	@Test
	public void testCharGen()
	{
		VarGenerator<Character> myNameGenerator = new ReminiscentGenerator<Character>(5)
		{
			@Override
			public Character generate()
			{
				int offset = (int) get("data", List.class).get(c());
				return (char) (offset + c());
			}
		};
		myNameGenerator.store("data", OFFSET_LIST);


		StringBuilder name = new StringBuilder();
		for (char c : myNameGenerator)
		{
			name.append(c);
		}

		assertEquals("david", name.toString());

	}


}
