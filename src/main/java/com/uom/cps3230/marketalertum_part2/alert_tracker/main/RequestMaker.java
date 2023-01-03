package com.uom.cps3230.marketalertum_part2.alert_tracker.main;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONObject;

import java.util.UUID;


public class RequestMaker {
    private JSONObject jsonObject;
    private String endpoint;
    private UUID myUUID;

    public RequestMaker(){
        jsonObject = new JSONObject();
        endpoint = "https://api.marketalertum.com/Alert";
        myUUID = UUID.fromString("baf95487-17f6-40df-b758-3c938a0ec72a");
    }

    // getters
    private JSONObject getJSONObject(){
        return jsonObject;
    }

    // setters
    public void setAlertsEndpoint(){
    	this.endpoint = "https://api.marketalertum.com/Alert";
    }
    
//    public void setEventsLogEndpoint(){
//    	this.endpoint = "https://api.marketalertum.com/EventsLog/"+myUUID;
//    }
//
//    public void setLoginStatusEndpoint(){
//    	this.endpoint = "https://api.marketalertum.com/EventsLog/"+myUUID+"/login-status";
//    }
    
    public void setJSONObject(Product product){
        jsonObject = new JSONObject();
        jsonObject.put("alertType", product.getAlertType()); // dont escape integers, otherwise :(
        jsonObject.put("heading", product.getHeading());
        jsonObject.put("description", product.getDescription());
        jsonObject.put("url", product.getUrl()); // no https = no search
        jsonObject.put("imageUrl", product.getImageUrl());
        jsonObject.put("postedBy", myUUID);
        jsonObject.put("priceInCents", product.getPriceInCents());
//        System.out.println(jsonObject);
    }

    //methods
    
    // delete request using UnirestApi
    public int deleteRequestDeleteAlerts() throws UnirestException {
    	setAlertsEndpoint();
        HttpResponse<JsonNode> response = Unirest.delete(endpoint + "?userId=" + myUUID).asJson();
        return response.getStatus();
    }

    // post request using UnirestApi
//    public String getRequestAllEvents() throws UnirestException {
//    	setEventsLogEndpoint();
//        HttpResponse<JsonNode> response = Unirest.get(endpoint)
//                .asJson();
//        return response.getBody().toString();
//    }
    
    
    // post request using UnirestApi
//    public String getRequestCurrentLoginStatus() throws UnirestException {
//    	setEventsLogEndpoint();
//        HttpResponse<JsonNode> response = Unirest.get(endpoint)
//                .asJson();
//        return response.getBody().toString();
//    }
    
    public int postRequestAddAlert() throws UnirestException {
    	setAlertsEndpoint();
        HttpResponse<JsonNode> response = Unirest.post(endpoint)
                .header("Content-Type", "application/json")
                .body(getJSONObject().toString())
                .asJson();
        return response.getStatus();
    }
    
//    public static void main(String args[]){
//    	Product p = new Product(1,"Test", "Desc", "https://www.google.com", "https://www.google.com", 23212);
//    	new RequestMaker();
//    	setJSONObject(p);
//    	System.out.println(getJSONObject());
//    	try{
//    		System.out.println(makePostRequest());
//    	}catch(Exception e){
//    		System.out.println(e.getMessage());
//    	}
//    	try{
//	    	System.out.println(makeDeleteRequest());
//		}catch(Exception e){
//			System.out.println(e.getMessage());
//		}
//    }
}
