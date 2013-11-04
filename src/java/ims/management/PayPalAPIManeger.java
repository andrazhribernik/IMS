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
 *
 * @author andrazhribernik
 */
public class PayPalAPIManeger {
    
    private final static String URL = "https://api-3t.sandbox.paypal.com/nvp";
    private final static String USER = "andraz.hribernik-facilitator_api1.gmail.com";
    private final static String PWD = "1383398594";
    private final static String SIGNATURE = "AFcWxV21C7fd0v3bYYYRCpSSRl31Ab33ZAHJIBQZeIG9T5kJFybReg-v";
    private final static String METHOD = "GetTransactionDetails";
    private final static String VERSION = "94";
    
    public boolean checkTransactionWithItem(String transaction, String itemId){
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
            for(int i=0; i<values.length; i++){
                String[] splitValue = values[i].split("=");
                if(splitValue[0].equals("L_NUMBER0")){
                    System.out.println(splitValue[1]);
                    if(splitValue[1].equals(itemId)){
                        return true;
                    }
                }
            }
            
            response.close();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(PayPalAPIManeger.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PayPalAPIManeger.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        return false;
    }
    
    public static void main(String[] args){
        PayPalAPIManeger ppm = new PayPalAPIManeger();
        ppm.checkTransactionWithItem("8D022885V9227494B", "1");
    }
    
}
