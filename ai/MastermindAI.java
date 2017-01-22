/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai;

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
    
    private int getBlackDifference(String response1, String response2){
        int black1 = 0;
        int black2 = 0;
        for(int i = 0; i < response1.length(); ++i) {
            black1 += response1.charAt(i) == 'B' ? 1 : 0;
        }
        for(int i = 0; i < response2.length(); ++i) {
            black2 += response2.charAt(i) == 'B' ? 1 : 0;
        }
        return black1 - black2;
    }
    
    public String next(String response){
        String code = "";
        if(prevCode == null) {
            currentColor = colors.charAt(currentColorIndex);
            for(int i = 0; i < codeLength; ++i){
                code += currentColor;
            }
            currentColorIndex++;
            bestCode = code;
        }
        else {
            code = prevCode;
            
            //System.out.println(code);
            int blackDifference = getBlackDifference(response, lastResponse);
            lastResponse = response;
            if(blackDifference < 0) {
                System.out.println("Back to " + bestCode);
                code = bestCode;
                lastResponse = bestResponse;
                //saved = saved.substring(0, lastCheckingIndex) + "x" + saved.substring(lastCheckingIndex+1);
                //System.out.println("Less than: " + saved);
                
                
            }
            else if(blackDifference > 0){
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
    
}
