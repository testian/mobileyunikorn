package yunikorn.yukon;

import java.io.*;
//import java.awt.image.*;
//import java.awt.geom.*;
//import java.awt.*;
//import java.awt.color.*;
//import java.awt.Dimension;
//import javax.imageio.ImageIO;
//import javax.media.format.*;
import javax.microedition.lcdui.*;
import yunikorn.core.packet.VideoFrame;

public class YukonVideoFrame implements VideoFrame {
YukonVideoHeader header;
long timestamp;
byte[] buffer;
//byte[] image;
//int[] argbImage;
//BufferedImage compatibleImage;




public long getTimestamp() {
	return timestamp;
}
public YukonVideoFrame(YukonVideoHeader header, long timestamp, byte[] buffer) {
	super();
	this.header = header;
	this.timestamp = timestamp;
	this.buffer = buffer;
	//image=null;
        //argbImage = null;
	//compatibleImage=null;
}
public int getFps() {
	return header.getFps();
}
public int getHeight() {
	return header.getHeight();
}
public int getWidth() {
	return header.getWidth();
}
public byte[] getBuffer() {
	return buffer;
}
public YukonVideoHeader getHeader() {
	return header;
}

public String toString()
{
return "VideoFrame(" + header.toString() + ", Timestamp(" + timestamp + "), Buffersize(" + buffer.length + "))";
}

public byte[] getRGBBuffer() {
    //if (image == null) {
	/*image =*/return ColorConversion.I420toRGB(buffer, getWidth(), -getHeight());
    //}
    //return image;
}


public int[] getARGBBuffer() {
  //  if (argbImage == null) {
	/*argbImage =*/return ColorConversion.I420toARGB(buffer, getWidth(), -getHeight());
    //}
    //return argbImage;
}




}
