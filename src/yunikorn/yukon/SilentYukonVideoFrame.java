package yunikorn.yukon;

//import java.awt.Dimension;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;

//import javax.media.format.*;
import javax.microedition.lcdui.*;
import yunikorn.core.packet.VideoFrame;
import yunikorn.yukon.factory.*;

public class SilentYukonVideoFrame implements VideoFrame {

	YukonVideoHeader header;
	RawCompressedPacket packet;
	boolean decompressed;
	YukonVideoFrame decorate;
	
	public SilentYukonVideoFrame(YukonVideoHeader header, RawCompressedPacket packet) {
		super();
		this.header = header;
		this.packet = packet;
		decompressed=false;
		decorate=null;
	}



	public int getHeight() {
		// TODO Auto-generated method stub
		return header.getHeight();
	}

	public int getWidth() {
		// TODO Auto-generated method stub
		return header.getWidth();
	}



	public long getTimestamp() {
		// TODO Auto-generated method stub
		if (!decompressed)
		{
			return packet.getTime();
		} else {
			return decorate.getTimestamp();
		}
	}
	public byte[] getBuffer()
	{
		try {
			decompress();
		
		}
		catch (YukonException ex){
			return new byte[0];
		}
		return decorate.getBuffer();
	}
	public void decompress() throws YukonException
	{
		if (decompressed){return;}
		decorate = VideoFrameFactory.createVideoFrame(header, UncompressedPacketFactory.createPacket(packet));
		
		
		decompressed=true;
		packet=null;
		
	}



	public byte[] getRGBBuffer() {
		// TODO Auto-generated method stub
		try {
			decompress();
		}catch (YukonException ex){return new byte[0];}
		return decorate.getRGBBuffer();
	}
        
        public int[] getARGBBuffer() {
		// TODO Auto-generated method stub
		try {
			decompress();
		}catch (YukonException ex){return new int[0];}
		return decorate.getARGBBuffer();
	}


	
	

}
