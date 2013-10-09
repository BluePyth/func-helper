package fr.bluepyth.java.funchelper.trylike;

import static fr.bluepyth.java.funchelper.immutable.IList.list;
import static fr.bluepyth.java.funchelper.trylike.Try.success;
import static fr.bluepyth.java.funchelper.trylike.Try.trySeq;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import fr.bluepyth.java.funchelper.immutable.IList;

@RunWith(BlockJUnit4ClassRunner.class)
public class TryTest {
	
	private IList<Integer> list = list(1, 2, 3);
	@SuppressWarnings("unchecked")
	private IList<Try<Integer>> trylist = list((Try<Integer>) success(1), (Try<Integer>) success(2), (Try<Integer>) success(3)); 
	
	
	@Test
	public void trySeq_nominal() {
		Try<IList<Integer>> result = trySeq(trylist);
		
		Assert.assertTrue(result.isSuccess());
		Assert.assertEquals(list, result.getPayload());
	}
}
