/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package te.te4.gifmebackend.utils;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Some Jsoup utilities.
 * @author Johan Svensson
 */
public abstract class JsoupUtils {
    private JsoupUtils(){
        
    }
    
    public static Element random(Elements from) {
        return from.get((int)Math.floor(from.size() * Math.random()));
    }
    
    /**
     * 
     * @return A document after JS has loaded the page.
     */
    public static Document getDocument(String url) throws IOException {
        //WebDriver driver = new Ghost();
        //driver.get(url);
        return null; //Jsoup.parse(driver.getPageSource());
    }
}
