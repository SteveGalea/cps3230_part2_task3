package com.uom.cps3230.marketalertum_part2.marketalertum_system;

import com.uom.cps3230.marketalertum_part2.marketalertum_system.enums.MarketAlertUmSystemStates;
import com.uom.cps3230.marketalertum_part2.marketalertum_system.main.MarketAlertUmSystem;
import com.uom.cps3230.marketalertum_part2.marketalertum_system.main.MarketAlertsPageObject;
import junit.framework.Assert;
import nz.ac.waikato.modeljunit.*;
import nz.ac.waikato.modeljunit.coverage.ActionCoverage;
import nz.ac.waikato.modeljunit.coverage.StateCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionPairCoverage;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Random;

public class MarketAlertUmSystemModelTest implements FsmModel {
	private MarketAlertUmSystemStates modelState;
	private boolean loggedIn = false, viewingAlerts = false, tooManyLogIns=false, tooManyLogOuts = false, badViewingAlerts = false, loggedOut = true;
	private MarketAlertUmSystem systemUnderTest;
	WebDriver driver;
	MarketAlertsPageObject marketAlertsPageObject;

	public MarketAlertUmSystemStates getState() {
		return modelState;
	}

	public void reset(final boolean b) {
		modelState = MarketAlertUmSystemStates.LOGGED_OUT;
		loggedIn = false;
		viewingAlerts = false;
		tooManyLogOuts = false;
		badViewingAlerts = false;
		loggedOut = true;
		if (b) {
			if(driver == null && marketAlertsPageObject == null) {
				driver = new ChromeDriver();
				marketAlertsPageObject =  new MarketAlertsPageObject(driver);
			}else{
				assert driver != null;
				driver.quit();
				driver = new ChromeDriver();
				marketAlertsPageObject =  new MarketAlertsPageObject(driver);
			}
			systemUnderTest = new MarketAlertUmSystem(marketAlertsPageObject, driver);
		}
	}

	public @Action void logInWithValidUser() {
		//update system
		systemUnderTest.login();

		//update model
		loggedIn = true;
		switch (getState()){
			case LOGGED_OUT:
			case TOO_MANY_LOG_OUTS:
				tooManyLogOuts = false;
				loggedOut = false;
				tooManyLogIns = false;
				viewingAlerts = false;
				badViewingAlerts = false;
				modelState = MarketAlertUmSystemStates.LOGGED_IN;
				break;

			case LOGGED_IN:
			case TOO_MANY_LOG_INS:
				tooManyLogIns = true;
				loggedOut = false;
				tooManyLogOuts = false;
				viewingAlerts = false;
				badViewingAlerts = false;
				modelState = MarketAlertUmSystemStates.TOO_MANY_LOG_INS;
				break;

			case VIEWING_ALERTS:
				viewingAlerts = false;
				tooManyLogIns = true;
				badViewingAlerts = false;
				loggedOut = false;
				tooManyLogOuts = false;
				modelState = MarketAlertUmSystemStates.TOO_MANY_LOG_INS;
				break;

			case BAD_VIEWING_ALERTS:
				viewingAlerts = false;
				tooManyLogIns = false;
				badViewingAlerts = false;
				loggedOut = false;
				tooManyLogOuts = false;
				modelState = MarketAlertUmSystemStates.LOGGED_IN;
				break;
		}

		Assert.assertEquals("The model's Logged Out State tone state doesn't match the SUT's state.", loggedOut, systemUnderTest.isLoggedOut());
		Assert.assertEquals("The model's Logged In State doesn't match the SUT's state.", loggedIn, systemUnderTest.isLoggedIn());
		Assert.assertEquals("The model's Too Many Log Outs State doesn't match the SUT's state.", tooManyLogOuts, systemUnderTest.isTooManyLogOuts());
		Assert.assertEquals("The model's Too Many Log Ins State doesn't match the SUT's state.", tooManyLogIns, systemUnderTest.isTooManyLogIns());
		Assert.assertEquals("The model's Viewing Alerts State doesn't match the SUT's state.", viewingAlerts, systemUnderTest.isViewingAlerts());
		Assert.assertEquals("The model's Bad Viewing Alerts State doesn't match the SUT's state.", badViewingAlerts, systemUnderTest.isBadViewingAlerts());
	}

	public boolean logOutUserGuard() {
		return getState().equals(MarketAlertUmSystemStates.LOGGED_IN)
				|| getState().equals(MarketAlertUmSystemStates.LOGGED_OUT)
				|| getState().equals(MarketAlertUmSystemStates.TOO_MANY_LOG_INS)
				|| getState().equals(MarketAlertUmSystemStates.TOO_MANY_LOG_OUTS)
				|| getState().equals(MarketAlertUmSystemStates.BAD_VIEWING_ALERTS)
				|| getState().equals(MarketAlertUmSystemStates.VIEWING_ALERTS);
	}
	public @Action void logOutUser() {
		//update system
		systemUnderTest.logout();

		//update model
		loggedOut = true;
		switch (getState()){
			case LOGGED_OUT:
				case TOO_MANY_LOG_OUTS:
					case BAD_VIEWING_ALERTS:
						loggedIn = false;
						tooManyLogIns = false;
						viewingAlerts = false;
						badViewingAlerts = false;
						tooManyLogOuts = true;
						modelState = MarketAlertUmSystemStates.TOO_MANY_LOG_OUTS;
						break;

			case LOGGED_IN:
				case TOO_MANY_LOG_INS:
					loggedIn = false;
					tooManyLogIns = false;
					viewingAlerts = false;
					badViewingAlerts = false;
					tooManyLogOuts = false;
					modelState = MarketAlertUmSystemStates.LOGGED_OUT;
					break;

			case VIEWING_ALERTS:
				viewingAlerts = false;
				loggedIn = false;
				tooManyLogOuts = false;
				tooManyLogIns = false;
				badViewingAlerts = false;
				modelState = MarketAlertUmSystemStates.LOGGED_OUT;
				break;

		}

		//assertions model vs system
		Assert.assertEquals("The model's Logged Out State tone state doesn't match the SUT's state.", loggedOut, systemUnderTest.isLoggedOut());
		Assert.assertEquals("The model's Logged In State doesn't match the SUT's state.", loggedIn, systemUnderTest.isLoggedIn());
		Assert.assertEquals("The model's Too Many Log Outs State doesn't match the SUT's state.", tooManyLogOuts, systemUnderTest.isTooManyLogOuts());
		Assert.assertEquals("The model's Too Many Log Ins State doesn't match the SUT's state.", tooManyLogIns, systemUnderTest.isTooManyLogIns());
		Assert.assertEquals("The model's Viewing Alerts State doesn't match the SUT's state.", viewingAlerts, systemUnderTest.isViewingAlerts());
		Assert.assertEquals("The model's Bad Viewing Alerts State doesn't match the SUT's state.", badViewingAlerts, systemUnderTest.isBadViewingAlerts());
	}

	public boolean viewUserAlertsGuard() {
		return getState().equals(MarketAlertUmSystemStates.LOGGED_IN)
				|| getState().equals(MarketAlertUmSystemStates.LOGGED_OUT)
				|| getState().equals(MarketAlertUmSystemStates.TOO_MANY_LOG_INS)
				|| getState().equals(MarketAlertUmSystemStates.TOO_MANY_LOG_OUTS)
				|| getState().equals(MarketAlertUmSystemStates.VIEWING_ALERTS)
				|| getState().equals(MarketAlertUmSystemStates.BAD_VIEWING_ALERTS);
	}
	public @Action void viewUserAlerts() {
		//update system
		systemUnderTest.view();

		//update model
		switch (getState()){
			case LOGGED_OUT:
				case TOO_MANY_LOG_OUTS:
					badViewingAlerts = true;
					viewingAlerts = false;
					loggedIn = false;
					loggedOut = true;
					tooManyLogOuts = false;
					tooManyLogIns = false;
					modelState = MarketAlertUmSystemStates.BAD_VIEWING_ALERTS;
					break;

			case LOGGED_IN:
				case TOO_MANY_LOG_INS:
					badViewingAlerts = false;
					loggedOut = false;
					loggedIn = true;
					tooManyLogOuts = false;
					tooManyLogIns = false;
					viewingAlerts = true;
					modelState = MarketAlertUmSystemStates.VIEWING_ALERTS;
					break;

			case VIEWING_ALERTS:
				viewingAlerts = true;
				badViewingAlerts = false;
				loggedOut = false;
				loggedIn = true;
				tooManyLogOuts = false;
				tooManyLogIns = false;
				break;

			case BAD_VIEWING_ALERTS:
				badViewingAlerts = true;
				viewingAlerts = false;
				loggedOut = true;
				loggedIn = false;
				tooManyLogOuts = false;
				tooManyLogIns = false;
				break;
		}

		//assertions model vs system
		Assert.assertEquals("The model's Logged Out State doesn't match the SUT's state.", loggedOut, systemUnderTest.isLoggedOut());
		Assert.assertEquals("The model's Logged In State doesn't match the SUT's state.", loggedIn, systemUnderTest.isLoggedIn());
		Assert.assertEquals("The model's Too Many Log Outs State doesn't match the SUT's state.", tooManyLogOuts, systemUnderTest.isTooManyLogOuts());
		Assert.assertEquals("The model's Too Many Log Ins State doesn't match the SUT's state.", tooManyLogIns, systemUnderTest.isTooManyLogIns());
		Assert.assertEquals("The model's Viewing Alerts State doesn't match the SUT's state.", viewingAlerts, systemUnderTest.isViewingAlerts());
		Assert.assertEquals("The model's Bad Viewing Alerts State doesn't match the SUT's state.", badViewingAlerts, systemUnderTest.isBadViewingAlerts());
	}

	@Test
	public void MarketAlertUmSystemModelRunner(){
		System.setProperty("webdriver.chrome.driver", "C:\\cps3230_part2_task3\\modeltestingtutorial\\src\\main\\java\\com\\uom\\cps3230\\marketalertum_part2\\marketalertum_system\\webdriver\\chromedriver.exe");
		final Tester tester = new GreedyTester(new MarketAlertUmSystemModelTest());
		tester.setRandom(new Random());
		tester.addListener(new StopOnFailureListener());
		tester.addListener("verbose");
		tester.addCoverageMetric(new TransitionPairCoverage());
		tester.addCoverageMetric(new StateCoverage());
		tester.addCoverageMetric(new ActionCoverage());
		tester.generate(250);
		tester.printCoverage();
	}
}
