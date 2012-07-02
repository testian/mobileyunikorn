/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ext;

/**
 *
 * @author testi
 */
public class MyInteger {

    
    
    public static int reverseBytes(int i)
    {
    //return i;
   int r;
    
    r = (i << 24);
    r = r | (i >>> 24);
    r = r | (0x00ff0000 & (i << 8));
    r = r | (0x0000ff00 & (i >> 8));


    return r;
    }
}
