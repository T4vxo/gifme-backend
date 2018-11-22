/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package te.te4.gifmebackend.utils;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
}
