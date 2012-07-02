package yunikorn.yukon.event;

import java.io.InputStream;

import yunikorn.yukon.*;
import yunikorn.yukon.io.*;
import yunikorn.core.packet.metainterfaces.*;
import yunikorn.core.*;

import yunikorn.core.packet.metainterfaces.PacketObservable;
import java.util.*;


public class CompressedPacketStreamObservable implements PacketObservable,
		StreamParser {

Vector listeners;
Vector streamListeners;
	public CompressedPacketStreamObservable()
	{
		listeners = new Vector();
		streamListeners = new Vector();
	}

	public void addListener(PacketListener listener) {
		listeners.addElement(listener);
		
	}

	public void removeListener(PacketListener listener) {
		listeners.removeElement(listener);
		
	}

	public void addStreamListener(StreamListener listener) {
		// TODO Auto-generated method stub
		streamListeners.addElement(listener);
	}

	public void readStream(InputStream source) {
		YukonStreamReader reader = new YukonStreamReader(source);
		while (reader.hasNext())
		{
			notifyListeners((RawCompressedPacket)reader.next());
		}
		notifyEndOfStream();

	}
	private void notifyListeners(RawCompressedPacket packet)
	{
		for (int i = 0; i<listeners.size();i++) {
                    CompressedPacketStreamListener t = (CompressedPacketStreamListener)listeners.elementAt(i);
			t.onPacket(packet);
		}
	}
	
	private void notifyEndOfStream()
	{

		for (int i = 0; i<listeners.size();i++) {
                    CompressedPacketStreamListener t = (CompressedPacketStreamListener)listeners.elementAt(i);
			t.onEndOfStream();
		}
		
		for (int i = 0; i<listeners.size();i++) {
                    CompressedPacketStreamListener t = (CompressedPacketStreamListener)listeners.elementAt(i);
			t.onEndOfStream();
		}
	}

	public void removeStreamListener(StreamListener listener) {
		// TODO Auto-generated method stub
		streamListeners.removeElement(listener);
	}

}
