/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ext;

/**
 *
 * @author testi
 */
public class ByteBuffer {
    private byte[] array;
    private int index;
    private ByteBuffer(byte[] array)
    {
    this.array = array;
    index=0;
    }
    
    public static ByteBuffer wrap(byte[] array)
    {
    return new ByteBuffer(array);
    }
    public IntBuffer asIntBuffer()
    {
    return IntBuffer.decorate(this);
    }
    public byte get()
    {
    return array[index++];
    }
    public byte get(int i)
    {
    return array[i];
    }

}
