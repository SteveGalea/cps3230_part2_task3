package com.uom.cps3230.marketalertum_part2.alert_tracker;

import com.uom.cps3230.marketalertum_part2.alert_tracker.enums.AlertTrackerSystemStates;
import com.uom.cps3230.marketalertum_part2.alert_tracker.main.AlertTrackerSystem;
import nz.ac.waikato.modeljunit.*;
import nz.ac.waikato.modeljunit.coverage.ActionCoverage;
import nz.ac.waikato.modeljunit.coverage.StateCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionPairCoverage;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public class AlertTrackerSystemModelTest implements FsmModel {
	//Linking the SUT
	private AlertTrackerSystem systemUnderTest = new AlertTrackerSystem();

	//State Variables
	private AlertTrackerSystemStates modelSystem = AlertTrackerSystemStates.VIEWING_ALERTS;

	private boolean viewingAlerts = true,  viewingLatestAlerts = false;
	private int noOfAlerts = 0;

	//Method implementations
	public AlertTrackerSystemStates getState() {
		return modelSystem;
	}

	public void reset(final boolean var1) {
		if (var1) {
			systemUnderTest = new AlertTrackerSystem();
		}
		modelSystem = AlertTrackerSystemStates.VIEWING_ALERTS;
		viewingAlerts = true;
		viewingLatestAlerts = false;
		noOfAlerts = 0;
	}

	//Transitions incl. guards
	public boolean alertCreatedGuard() {
		return getState().equals(AlertTrackerSystemStates.VIEWING_ALERTS) || getState().equals(AlertTrackerSystemStates.VIEWING_LATEST_ALERTS);
	}
	public @Action void alertCreated() {
		//Updating SUT
		systemUnderTest.AlertCreated();

		//Updating model
		noOfAlerts ++;
		if (getState().equals(AlertTrackerSystemStates.VIEWING_ALERTS)) {
			if(noOfAlerts > 5) {
				modelSystem = AlertTrackerSystemStates.VIEWING_LATEST_ALERTS;
				viewingLatestAlerts = true;
				viewingAlerts = false;
			}
		}

		//Checking correspondence between model and SUT.
		assertEquals("!! ViewingAlerts Variable => This SUT's alert state does not match the expected model's alert tracker after creating an alert", viewingAlerts, systemUnderTest.isViewingAlerts());
		assertEquals("!! ViewingLatestAlerts Variable => This SUT's alert state does not match the expected model's alert tracker after creating an alert", viewingLatestAlerts, systemUnderTest.isViewingLatestAlerts());
		assertEquals("!! NoOfAlerts Variable => The SUT's number of Alerts of the SUT does not match as the expected model", noOfAlerts, systemUnderTest.getNoOfAlerts());
		// I don't necessarily have to test this since I never specified that I only need to be in one brightness state at once, but I have opted to do it anyway for completeness.
		// Feel free to NOT test variables which have not been altered in this transition (except if you are explicitly told to do so)
	}
	public boolean alertsDeletedGuard() {
		return getState().equals(AlertTrackerSystemStates.VIEWING_ALERTS) || getState().equals(AlertTrackerSystemStates.VIEWING_LATEST_ALERTS);
	}
	public @Action void alertsDeleted() {
		//Updating SUT
		systemUnderTest.AlertsDeleted();

		//Updating model
		noOfAlerts = 0;
		if (getState().equals(AlertTrackerSystemStates.VIEWING_LATEST_ALERTS)) {
			modelSystem = AlertTrackerSystemStates.VIEWING_ALERTS;
			viewingLatestAlerts = false;
			viewingAlerts = true;
		}

		//Checking correspondence between model and SUT.
		assertEquals("!! ViewingAlerts Variable => This SUT's alert state does not match the expected model's alert tracker after creating an alert", viewingAlerts, systemUnderTest.isViewingAlerts());
		assertEquals("!! ViewingLatestAlerts Variable => This SUT's alert state does not match the expected model's alert tracker after creating an alert", viewingLatestAlerts, systemUnderTest.isViewingLatestAlerts());
		assertEquals("!! NoOfAlerts Variable => The SUT's number of Alerts of the SUT does not match as the expected model", noOfAlerts, systemUnderTest.getNoOfAlerts());
		// I don't necessarily have to test this since I never specified that I only need to be in one brightness state at once, but I have opted to do it anyway for completeness.
		// Feel free to NOT test variables which have not been altered in this transition (except if you are explicitly told to do so)
	}

	//Test runner
	@Test
	public void AlertTrackerModelRunner() {
//		final RandomTester tester = new RandomTester(new AlertTrackerSystemModelTest()); //Creates a test generator that can generate random walks. A greedy random walk gives preference to transitions that have never been taken before. Once all transitions out of a state have been taken, it behaves the same as a random walk.
		final GreedyTester tester = new GreedyTester(new AlertTrackerSystemModelTest()); //Creates a test generator that can generate random walks. A greedy random walk gives preference to transitions that have never been taken before. Once all transitions out of a state have been taken, it behaves the same as a random walk.
//		final LookaheadTester tester = new LookaheadTester(new AlertTrackerSystemModelTest()); //Creates a test generator that can generate random walks. A greedy random walk gives preference to transitions that have never been taken before. Once all transitions out of a state have been taken, it behaves the same as a random walk.
		tester.setRandom(new Random()); //Allows for a random path each time the model is run.
		tester.buildGraph(); //Builds a model of our FSM to ensure that the coverage metrics are correct.
		tester.addListener(new StopOnFailureListener()); //This listener forces the test class to stop running as soon as a failure is encountered in the model.
		tester.addListener("verbose"); //This gives you printed statements of the transitions being performed along with the source and destination states.
		tester.addCoverageMetric(new TransitionPairCoverage()); //Records the transition pair coverage i.e. the number of paired transitions traversed during the execution of the test.
		tester.addCoverageMetric(new StateCoverage()); //Records the state coverage i.e. the number of states which have been visited during the execution of the test.
		tester.addCoverageMetric(new ActionCoverage()); //Records the number of @Action methods which have ben executed during the execution of the test.
		tester.generate(1000); //Generates 500 transitions
		tester.printCoverage(); //Prints the coverage metrics specified above.
	}
}
