package fr.sertelon.fp.funchelper.option;

import static fr.sertelon.fp.funchelper.option.Opt.toOpt;
import static fr.sertelon.fp.funchelper.trylike.Try.success;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import fr.sertelon.fp.funchelper.trylike.Try;

@RunWith(BlockJUnit4ClassRunner.class)
public class OptTest {
	@Test
	public void optToTry_nominal() {
		Opt<Try<Integer>> a = toOpt((Try<Integer>) success(1));
		Try<Opt<Integer>> result = a.toTry(Integer.class);
		
		assertTrue(result.isSuccess());
		assertTrue(result.getPayload().isDefined());
		assertEquals(Integer.valueOf(1), result.getPayload().get());
	}
}
