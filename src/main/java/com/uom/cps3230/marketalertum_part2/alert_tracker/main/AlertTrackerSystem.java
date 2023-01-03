package com.uom.cps3230.marketalertum_part2.alert_tracker.main;


//import java.io.BufferedReader;
//import java.io.InputStreamReader;
import java.util.ArrayList;

public class AlertTrackerSystem {
	// System under test properties/variables
	private boolean viewingAlerts = true,  viewingLatestAlerts = false;
	private int noOfAlerts = 0; // if running with main comment this out
//	private static int noOfAlerts = 0; // if running with main uncomment

	//Events
	public boolean createAlert(){
		addAlert();
		noOfAlerts ++;
		if(viewingAlerts || viewingLatestAlerts){
//			if(noOfAlerts > 5 && viewingAlerts) {
			if(noOfAlerts > 5 && viewingAlerts) {
				// Alert limit exceeded - going to viewing latest 5 alerts state
				viewingLatestAlerts = true;
				viewingAlerts = false;
			}
			return true;
		}else{
			throw new IllegalStateException();
		}
	}

	public boolean deleteAlerts(){
		removeAlerts();
		noOfAlerts = 0;
		if(viewingAlerts || viewingLatestAlerts){
//			if(viewingLatestAlerts && !viewingAlerts) {
//			if(viewingLatestAlerts) {
				// Alert limit exceeded - going back to initial viewing state
				viewingLatestAlerts = false;
				viewingAlerts = true;
//			}
			return true;
		}else{
			throw new IllegalStateException();
		}
	}




	//Helper Objects
	static ArrayList<Product> productAlerts = new ArrayList<Product>();
	RequestMaker requestMaker = new RequestMaker();
//	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	//Helper Methods
//
//	//read input
//	public static int read()
//	{
//		try{
//			return Integer.parseInt(br.readLine());
//		}
//		catch(Exception ex)
//			{ex.printStackTrace();}
//		return -1;
//	}
//
//	//reset method
//	public void reset(){
//		viewingAlerts = true;
//		viewingLatestAlerts = false;
//		noOfAlerts = 0;
//		resetSystem();
//	}
//
//	public void resetSystem(){
//		System.out.println("Resetting System");
//		productAlerts = new ArrayList<Product>();
//		deleteAlerts();
//		requestMaker = new RequestMaker();
////		try{
////			requestMaker.getRequestAllEvents();
////			System.out.println("Cleared EventLog System");
////		}catch(Exception e){
////			System.out.println(e.getMessage());
////		}
//
//	}
	
	public int getRandomNumber(int max, int min){
		return (int)(Math.random() * (max - min + 1) + min);
	}

	public void addAlert(){
		System.out.print("addAlert Method: ");
		requestMaker = new RequestMaker();
		//initialise random dummy object
		Product p = new Product(getRandomNumber(5,1),"Product "+productAlerts.size()+" is an Alert", "Description for Product", "https://www.google.com", "https://www.google.com", 23212);
				
		productAlerts.add(p);
		requestMaker.setJSONObject(p);
		try{
			requestMaker.postRequestAddAlert();
			System.out.println("Alert Added at "+ System.currentTimeMillis());
		}catch(Exception e){
    		System.out.println(e.getMessage());
    	}
	}


	public void removeAlerts(){
		System.out.print("deleteAlert Method: ");
		requestMaker = new RequestMaker();
    	try{
	    	requestMaker.deleteRequestDeleteAlerts();
			System.out.println("Alerts Deleted at "+ System.currentTimeMillis());
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
    	productAlerts = new ArrayList<Product>();
	}

//	public String show()
//	{
//		String s = "";
//		int i = 0;
//		for (Product p:productAlerts){
//			s += i + "("+p.heading+"), ";
//			i++;
//		}
//		return s;
//	}
//
//	public void menu()
//	{
//		reset();
//		boolean run = true;
//
//		final AlertTrackerSystem alertTracker = new AlertTrackerSystem();
//		while (run)
//		{
//			System.out.println("****MAIN MENU****");
//			System.out.println("Alerts: "+show());
//			System.out.println("0. reset automaton");
//			System.out.println("1. add alert");
//			System.out.println("2. delete alerts");
//			System.out.println("3. exit");
//			switch(read())
//			{
//				case 0:
//					reset(); break;
//				case 1:
//					if(alertTracker.AlertCreated()){
//						System.out.println("Alert Created");
//						if(noOfAlerts <= 5){
//							System.out.println("0-5 alerts present");
//						}else{
//							System.out.println("Over limit - 6 or more alerts present - showing latest 5 alerts");
//						}
//					}else{
//						System.out.println("Failed Alert Creation");
//					}
//					break;
//
//				case 2:
//					if(alertTracker.AlertsDeleted()){
//						System.out.println("Alerts Deleted Successfully");
//					}else{
//						System.out.println("Failed Alerts Deletion");
//					}
//					break;
//
//				case 3:
//					run=false;
//					break;
//			}
//		}
//	}

	public boolean isViewingAlerts() {
		return viewingAlerts;
	}

	public boolean isViewingLatestAlerts() {
		return viewingLatestAlerts;
	}

	public int getNoOfAlerts() {
		return noOfAlerts;
	}
//
//	public void generateProductAlerts(int wantedAmount){
//		for(int i = 0; i < wantedAmount; i++){
//			switch(getRandomNumber(1,0)){
//			case 0:addAlert(); break;
//			case 1:deleteAlerts(); break;
//			}
//		}
//	}

//	public static void main(String args[]){
//		new AlertTrackerSystem().menu();
//
//	}
}
