package examples;

import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.testng.ScenarioTest;

class GivenSomeState extends Stage<GivenSomeState> {
	public GivenSomeState a_state() {
		return self();
	}
}

class WhenSomeAction extends Stage<WhenSomeAction> {
	public WhenSomeAction some_action() {
		return self();
	}
}

class ThenSomeOutcome extends Stage<ThenSomeOutcome> {
	public ThenSomeOutcome some_outcome() {
		return self();
	}
}

public class JGivenExampleTest extends ScenarioTest<GivenSomeState, WhenSomeAction, ThenSomeOutcome> {
	@Test
	public void test1()
	{
		this.given().a_state();
		this.when().some_action();
		this.then().some_outcome();
		assertTrue(false);
	}
}
