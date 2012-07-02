package yunikorn.core.packet;
import java.io.*;
import javax.microedition.lcdui.*;

//import javax.media.format.VideoFormat;

public interface VideoFrame extends DataPacket {

	
	public int getWidth();
	public int getHeight();
	
	//public Image getBufferedImage();
	//public void writeJpg(File jpgfile) throws IOException;
	public byte[] getBuffer();
	public byte[] getRGBBuffer();
        public int[] getARGBBuffer();
	//public VideoFormat getFormat();
	//public VideoFormat getARGBFormat();
	
}
