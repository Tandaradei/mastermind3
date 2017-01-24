/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai;

import java.util.ArrayList;
import java.util.Random;
import network.server.Server;

/**
 *
 * @author laurin.agostini
 */
public class MastermindAI {
    
    private int codeLength;
    private String colors;
    private String bestCode = null;
    private String prevCode = null;
    //private String bestCode = null;
    private String lastResponse = "";
    private String bestResponse = "";
   
    private int currentColorIndex = 0;
    private char currentColor = '0';
    private int lastCheckingIndex = 0;
    private int checkingIndex = 0;
    private String saved = "";
    
    
    public MastermindAI(int codeLength, String colors){
        this.codeLength = codeLength;
        this.colors = colors;
        for(int i = 0; i < codeLength; ++i) {
            saved += "o";
        }
    }
    
    private int charToPoints(char character){
        switch(character){
            case 'B':
                return codeLength;
            case 'W':
                return 1;
            default:
                return 0;
        }
    }
    
    private int getPointDifference(String response1, String response2){
        int points1 = 0;
        int points2 = 0;
        for(int i = 0; i < response1.length(); ++i) {
            points1 += charToPoints(response1.charAt(i));
            
        }
        for(int i = 0; i < response2.length(); ++i) {
            points2 += charToPoints(response2.charAt(i));
        }
        System.out.println("Points: " + points1 + " <> " + points2);
        return points1 - points2;
    }
    
   
    public String next(String response){
        String code = "";
        // First code
        if(prevCode == null) {
            currentColor = colors.charAt(currentColorIndex);
            for(int i = 0; i < codeLength; ++i){
                code += currentColor;
            }
            currentColorIndex++;
            bestCode = code;
        }
        else {
            // Use last code as base
            code = prevCode;
            
            // Get Difference of current response and last response
            int pointDifference = getPointDifference(response, lastResponse);
            lastResponse = response;
            // If worse, go back
            if(pointDifference < 0) {
                //System.out.println("Back to " + bestCode);
                code = bestCode;
                lastResponse = bestResponse;
                
                
            }
            else if(pointDifference > 0){
                code = prevCode;
                bestCode = code;
                bestResponse = response;
                if(currentColorIndex > 1 && (!(currentColorIndex == 2) || checkingIndex > 0)){
                    saved = saved.substring(0, lastCheckingIndex) + "x" + saved.substring(lastCheckingIndex+1);
                    System.out.println("Greater than: " + saved);
                }
            }
            else {
                saved = saved.substring(0, lastCheckingIndex) + "o" + saved.substring(lastCheckingIndex+1);
                System.out.println("Equals: " + saved);
            }
            if(saved.charAt(checkingIndex) == 'x'){
                checkingIndex++;
                if(checkingIndex >= codeLength){
                    checkingIndex = 0;
                    currentColorIndex++;
                }
            }
            if(currentColorIndex >= colors.length()){
                currentColorIndex = 0;
            }
            currentColor = colors.charAt(currentColorIndex);
            code = code.substring(0, checkingIndex) + currentColor + code.substring(checkingIndex+1);
            lastCheckingIndex = checkingIndex;
            checkingIndex++;
            if(checkingIndex >= codeLength){
                checkingIndex = 0;
                currentColorIndex++;
            }
            
        }
        prevCode = code;
        return code;
    }
    
    private long number = 0;
    private ArrayList<String> lastCodes = new ArrayList<String>();
    private ArrayList<String> lastResponses = new ArrayList<String>();
    
    
    private String nextCodeWithSameResponse(){
        String code = "";
        boolean isInLastCodes = true;
        do{
            isInLastCodes = true;
            code = "";
            long currentNumber = number++;
            for(int i = 0; i < codeLength; ++i){
                code += colors.charAt((int)(currentNumber % colors.length()));
                currentNumber /= colors.length();
            }
            for(int i = 0; i < lastCodes.size(); ++i){
                if(!lastResponses.get(i).equals(Server.checkKey(lastCodes.get(i), code))){
                    isInLastCodes = false;
                    break;
                }
            }
        }while(!isInLastCodes);
        return code;
    }
    
    public String next2(String response){
        String code = "";
        if(response == null || response.length() == 0){
            for(int i = 0; i < codeLength; ++i){
                code += colors.charAt(i % colors.length());
            }
        }
        else{
            lastResponses.add(response);
            Random rand = new Random();
            number = rand.nextInt((int) Math.pow(colors.length(), codeLength));
            code = nextCodeWithSameResponse();
        }
        lastCodes.add(code);
        return code;
    }

   
    
}
