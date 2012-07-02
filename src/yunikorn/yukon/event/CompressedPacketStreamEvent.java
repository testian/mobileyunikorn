package yunikorn.yukon.event;

import java.io.IOException;
import java.io.OutputStream;
import yunikorn.yukon.*;
import yunikorn.yukon.io.*;

import yunikorn.core.chunkstream.StreamEvent;

public class CompressedPacketStreamEvent implements StreamEvent {

	RawCompressedPacket packet;
	
	

	public CompressedPacketStreamEvent(RawCompressedPacket packet) {
		super();
		this.packet = packet;
	}

	public boolean prepare() { //Does nothing
		// TODO Auto-generated method stub
		return true;
	}

	public void write(OutputStream out) throws IOException {
		YukonStreamWriter writer = new YukonStreamWriter(out);
		writer.write(packet);

	}

}
