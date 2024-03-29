package yunikorn.yukon.factory;
//import java.nio.*;

import yunikorn.yukon.AbstractRawPacket;
import yunikorn.yukon.InvalidTypeException;
import yunikorn.yukon.RawCompressedPacket;
import yunikorn.yukon.RawUncompressedPacket;
import yunikorn.yukon.YukonException;
import yunikorn.yukon.YukonVideoHeader;
import ext.*;
//import java.nio.*;
public class VideoHeaderFactory {

	public static YukonVideoHeader createVideoHeader(RawUncompressedPacket packet) throws YukonException
	{
		if (packet.getType() != AbstractRawPacket.VIDEO_HEADER) {throw new InvalidTypeException();}
		return create(packet);
	}
	
	public static YukonVideoHeader createVideoHeader(RawCompressedPacket packet) throws YukonException
	{
		if (packet.getType() != AbstractRawPacket.VIDEO_HEADER) { throw new InvalidTypeException();}
		return create(UncompressedPacketFactory.createPacket(packet));
	}
	public static YukonVideoHeader create(RawUncompressedPacket packet) throws YukonException
	{
		if (packet.getBuffer().length != 16){throw new YukonException();}
		//System.out.println(testing.testing.byteArrayToString(packet.getBuffer()));
		IntBuffer intBuf = ByteBuffer.wrap(packet.getBuffer()).asIntBuffer();
		int scale = /*MyInteger.reverseBytes(*/intBuf.get()/*)*/;
		int width = /*MyInteger.reverseBytes(*/intBuf.get()/*)*/;
		int height = /*MyInteger.reverseBytes(*/intBuf.get()/*)*/;
		int fps = /*MyInteger.reverseBytes(*/intBuf.get()/*)*/;
		return new YukonVideoHeader(packet.getTime(),fps, width, height, scale);
	}
	
}
