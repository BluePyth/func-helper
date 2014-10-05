package fr.sertelon.fp.funchelper.trylike;

import static fr.sertelon.fp.funchelper.immutable.IList.list;
import static fr.sertelon.fp.funchelper.trylike.Try.sequence;
import static fr.sertelon.fp.funchelper.trylike.Try.success;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import fr.sertelon.fp.funchelper.immutable.IList;

@RunWith(BlockJUnit4ClassRunner.class)
public class TryTest {
	
	private IList<Integer> list = list(1, 2, 3);
	@SuppressWarnings("unchecked")
	private IList<Try<Integer>> trylist = list((Try<Integer>) success(1), (Try<Integer>) success(2), (Try<Integer>) success(3)); 
	
	
	@Test
	public void trySeq_nominal() {
		Try<IList<Integer>> result = sequence(trylist);
		
		assertTrue(result.isSuccess());
		assertEquals(list, result.getPayload());
	}
	
}
