/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ext;

/**
 *
 * @author testi
 */
public class IntBuffer {

    private ByteBuffer decorate;
    public static IntBuffer decorate(ByteBuffer toDecorate)
    {
    return new IntBuffer(toDecorate);
    }
    private IntBuffer(ByteBuffer decorate)
    {
    this.decorate = decorate;
    }
    
    public int get()
    {
        //System.out.println("LOL");
        int read = 0;
                
                for (int i = 0;i<4;i++)
                {
                
                    read = (decorate.get() << 24) | (read >>> 8);
                
                }
        
    return read;
        
        
    }
    
    public int get(int index)
    {
            int read = 0;
                
                for (int i = 0;i<4;i++)
                {
                
                read = (decorate.get(index*4+i) << 24) | (read >>> 8);
                
                }
        
    return read;
    }
}
