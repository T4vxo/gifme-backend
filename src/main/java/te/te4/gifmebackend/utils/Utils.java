/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package te.te4.gifmebackend.utils;

/**
 * Some utilities.
 * @author johan
 */
public abstract class Utils {
    private Utils() {
        
    }
    
    public static <T> T random(T[] array) {
        return array[(int)Math.floor(array.length * Math.random())];
    }
}
