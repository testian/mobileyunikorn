package yunikorn.yukon;

//import yunikorn.core.StreamListener;
import yunikorn.core.chunkstream.StreamEventListener;
import yunikorn.core.chunkstream.StreamEventObservable;
import yunikorn.yukon.event.*;
import java.util.*;

public class YukonStreamRepeater implements CompressedPacketStreamListener, StreamEventObservable {

	RawCompressedPacket videoHeader;
	RawCompressedPacket audioHeader;
	Vector listeners;
	
boolean closed;
	public YukonStreamRepeater()
	{
		closed=false;
		videoHeader = null;
		audioHeader = null;
		listeners = new Vector();
	}
	
	public void onEndOfStream() {
		
		synchronized(listeners) {
		closed=true;
		for (int i = 0; i<listeners.size();i++) {
                    StreamEventListener t = (StreamEventListener)listeners.elementAt(i);
                
                
	
		
		t.onEndOfStream();
	}
		}
	}

	public void onPacket(RawCompressedPacket packet) {

		CompressedPacketStreamEvent event = new CompressedPacketStreamEvent(packet);
		synchronized(listeners) {
		closed=false;
	for (int i = 0; i<listeners.size();i++) {
        StreamEventListener t = (StreamEventListener)listeners.elementAt(i);
	t.onStreamEvent(event);
}
}


if (packet.getType() == RawCompressedPacket.VIDEO_HEADER)
{
	videoHeader = packet;
}
else if (packet.getType() == RawCompressedPacket.AUDIO_HEADER)
{
	audioHeader = packet;
}
	}

	public void addStreamEventListener(StreamEventListener listener) {

		synchronized (listeners) {
			if (closed){listener.onEndOfStream();return;}
		if (videoHeader!= null) {
			listener.onStreamEvent(new CompressedPacketStreamEvent(videoHeader));
			//System.out.println("video header first");
		}
		if (audioHeader!= null) {
			listener.onStreamEvent(new CompressedPacketStreamEvent(audioHeader));
			//System.out.println("audio header first");

		}
		
		listeners.addElement(listener);
		}


//TODO: if stream already started (video, audio headers not null)

	}

	public void removeStreamEventListener(StreamEventListener listener) {
		// TODO Auto-generated method stub
		synchronized(listeners) {
		listeners.removeElement(listener);
		}
	}

}
