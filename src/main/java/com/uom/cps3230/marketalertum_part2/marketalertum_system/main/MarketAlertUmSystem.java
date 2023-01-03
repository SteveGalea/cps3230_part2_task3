package com.uom.cps3230.marketalertum_part2.marketalertum_system.main;

//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//
//import com.uom.cps3230.marketalertum_part2.alert_tracker.main.Product;
//import com.uom.cps3230.marketalertum_part2.alert_tracker.main.RequestMaker;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;


//import org.json.JSONArray;
//import org.json.JSONObject;

public class MarketAlertUmSystem {


	//system helper properties
	WebDriver driver;
	MarketAlertsPageObject marketAlertsPageObject;
	// system initialisations and helper methods
//	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private boolean loggedOut = true, loggedIn = false, tooManyLogOuts = false, tooManyLogIns = false, viewingAlerts = false, badViewingAlerts = false;

//
//	public static int read()
//	{
//		try{
//			return Integer.parseInt(br.readLine());
//		}
//		catch(Exception ex)
//			{ex.printStackTrace();}
//		return -1;
//	}

	public MarketAlertUmSystem(MarketAlertsPageObject marketAlertsPageObject, WebDriver driver){
		this.marketAlertsPageObject = marketAlertsPageObject;
		this.driver = driver;
	}
	
//	public int getRandomNumber(int max, int min){
//		return (int)(Math.random() * (max - min + 1) + min);
//	}
//
//	public void menu()
//	{
//		resetSystem();
//		boolean run = true;
//		while (run)
//		{
//			System.out.println("\n****MAIN MENU****");
//			System.out.println("1. Generate alerts");
//			System.out.println("2. Run-time Verification");
//			System.out.println("3. Reset");
//			System.out.println("4. Exit");
//			System.out.println("Enter your choice");
//			switch(read())
//			{
//				case 1:
//					System.out.println("How many Events would you like generated?");
//					generateEvents(read());
//					break;
//
//				case 2:
//					System.out.println("Verification:");
//					new Runner().runner();
//					break;
//
//				case 3:
//					System.out.println("Resetting:");
//					resetSystem();
//					break;
//
//				case 4:
//					System.out.println("Bye!");
//					run=false;
//					break;
//			}
//			driver.quit();
//		}
//	}
//
//	public void generateEvents(int wantedAmount){
//		System.out.println("Wanted Events: "+wantedAmount);
//		for(int i = 0; i < wantedAmount; i++){
//			switch(getRandomNumber(2,0)){
//			case 0:
//				System.out.println(i+": Logging in");
//				marketAlertsPageObject.logIn();
//				break;
//			case 1:
//				System.out.println(i+": Logging Out");
//				marketAlertsPageObject.logOut();
//				break;
//			case 2:
//				System.out.println(i+": Viewing Alerts");
//				marketAlertsPageObject.viewAlerts();
//				break;
//			}
//		}
//	}

//	public void menu(){
//		boolean run = true;
//		while (run)
//		{
//			System.out.println("\n****MAIN MENU-Event Control****");
//			System.out.println("1. Login");
//			System.out.println("2. Logout");
//			System.out.println("3. View my alerts");
//			System.out.println("4. Exit");
//			System.out.println("Enter your choice: ");
//			switch(read()){
//				case 1:
//					System.out.println("Logging in");
//					marketAlertsPageObject.logIn();
//					break;
//				case 2:
//					System.out.println("Logging Out");
//					marketAlertsPageObject.logOut();
//					break;
//				case 3:
//					System.out.println("Viewing Alerts");
//					marketAlertsPageObject.viewAlerts();
//					break;
//				case 4: run = false; driver.close(); break;
//			}
//		}
//	}
//
//	public static void main(String args[]){
//		System.setProperty("webdriver.chrome.driver", "C:\\Users\\steve\\Downloads\\ModelTestingTutorial\\ModelTesting\\modeltestingtutorial\\src\\main\\java\\com\\uom\\cps3230\\marketalertum_part2\\marketalertum_system\\webdriver\\chromedriver.exe");
//		WebDriver driver = new ChromeDriver();
//		MarketAlertsPageObject marketAlertsPageObject =  new MarketAlertsPageObject(driver);
//		new MarketAlertUmSystem(marketAlertsPageObject, driver).menu();
//	}

	public boolean isLoggedOut() {
		return loggedOut;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public boolean isTooManyLogOuts() {
		return tooManyLogOuts;
	}

	public boolean isTooManyLogIns() {
		return tooManyLogIns;
	}

	public boolean isViewingAlerts() {
		return viewingAlerts;
	}

	public boolean isBadViewingAlerts() {
		return badViewingAlerts;
	}

	public void login() {
		System.out.print("Logging In Method: ");
		marketAlertsPageObject.logIn();

		if(loggedIn) {
			// if already logged in because
			// state is either in tooManyLogIns, loggedIn, or in viewingAlerts states
			tooManyLogIns = true;
		}else{
			// if loggedIn is false because
			// state is either in too ManyLogOuts, loggedOut, or badViewingAlerts
			loggedIn = true;
			tooManyLogIns = false;
		}

		tooManyLogOuts = false;
		viewingAlerts = false;
		badViewingAlerts = false;
		loggedOut = false;
	}

	public void logout() {
		System.out.print("Logging Out Method: ");
		marketAlertsPageObject.logOut();

		if(loggedOut) {
			// if already logged out
			// state either in tooManyLogOuts, loggedOut, or badViewingAlerts
			tooManyLogOuts = true;
		}else{
			// if loggedIn is true because
			// state is either in tooManyLogIns, loggedIn, or in viewingAlerts states)
			loggedOut = true;
		}

		loggedIn = false;
		tooManyLogIns = false;
		viewingAlerts = false;
		badViewingAlerts = false;
	}

	public void view() {
		System.out.print("Viewing Alerts Method: ");
		marketAlertsPageObject.viewAlerts();

		if(loggedIn) {
			// if loggedIn, allow viewing alerts
			// state is either in tooManyLogIns, loggedIn, or in viewingAlerts states)
			badViewingAlerts = false;
			viewingAlerts = true;
			loggedOut = false;
		}else{
			// logged out- access to alerts prohibited as user is logged out
			// state either in tooManyLogOuts, loggedOut, or badViewingAlerts
			badViewingAlerts = true;
			viewingAlerts = false;
			loggedOut = true;
		}

		tooManyLogIns = false;
		tooManyLogOuts = false;
	}
}
