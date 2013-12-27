/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.management;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicNameValuePair;


/**
 *This class provides connection to PayPalAPI. 
 * All credentials are predefined.
 * @author andrazhribernik
 */
public class PayPalAPIManeger {
    
    private final static String URL = "https://api-3t.sandbox.paypal.com/nvp";
    private final static String USER = "andraz.hribernik-facilitator_api1.gmail.com";
    private final static String PWD = "1383398594";
    private final static String SIGNATURE = "AFcWxV21C7fd0v3bYYYRCpSSRl31Ab33ZAHJIBQZeIG9T5kJFybReg-v";
    private final static String METHOD = "GetTransactionDetails";
    private final static String VERSION = "94";
    private final static String AMOUNT = "1%2e00";
    
    /**
     * This method checks validity of PayPal transaction.
     * User has to give an argument of transaction and id of image that was bought 
     * in specified transaction. If transaction exists, item number is the same as specified 
     * imageId and amount of money was 1 EUR, then method return true, otherwise false.
     * @param transaction defines PayPal transaction that method queries about.
     * @param itemId defines the image that we would like to check if that image 
     * was involved in specified transaction. 
     * @return If item is involved in specified transaction, method returns true, 
     * otherwise false
     */
    
    public boolean checkTransactionWithItem(String transaction, String itemId, Double itemPrice){
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(URL);
        List <NameValuePair> nvps = new ArrayList <NameValuePair>();
        nvps.add(new BasicNameValuePair("USER", USER));
        nvps.add(new BasicNameValuePair("PWD", PWD));
        nvps.add(new BasicNameValuePair("SIGNATURE", SIGNATURE));
        nvps.add(new BasicNameValuePair("METHOD", METHOD));
        nvps.add(new BasicNameValuePair("VERSION", VERSION));
        nvps.add(new BasicNameValuePair("TRANSACTIONID", transaction));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            CloseableHttpResponse response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            byte[] byteContent = new byte[(int)entity.getContentLength()];
            entity.getContent().read(byteContent, 0, (int)entity.getContentLength());
            String content = new String(byteContent);
            String[] values = content.split("&");
            boolean valid = false;
            for(int i=0; i<values.length; i++){
                String[] splitValue = values[i].split("=");
                if(splitValue[0].equals("L_NUMBER0")){
                    System.out.println(splitValue[1]);
                    if(!splitValue[1].equals(itemId)){
                        return false;
                    }
                    else{
                        valid = true;
                    }
                }
                if(splitValue[0].equals("AMT")){
                    String amount = splitValue[1].replace("%2e", ".");
                    if(!(Double.valueOf(amount).equals(itemPrice))){
                        return false;
                    }
                }
            }
            
            
            response.close();
            return valid;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(PayPalAPIManeger.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PayPalAPIManeger.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return false;
    }
    
    
    /**
     * This main method is used to test method checkTransactionWithItem. With 
     * running parameters developer can set transactionId and itemId.  
     * @param args 
     */
    public static void main(String[] args){
        PayPalAPIManeger ppm = new PayPalAPIManeger();
        System.out.println(ppm.checkTransactionWithItem("1YV032075V8482257", "5",1.00));
    }
    
}
