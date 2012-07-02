package yunikorn.yukon.event;

import java.util.*;
import java.io.*;

import yunikorn.core.*;
import yunikorn.core.packet.*;
import yunikorn.core.packet.metainterfaces.*;
import yunikorn.yukon.InvalidTypeException;
import yunikorn.yukon.RawCompressedPacket;
import yunikorn.yukon.RawUncompressedPacket;
import yunikorn.yukon.YukonAudioHeader;
import yunikorn.yukon.YukonAudioSequence;
import yunikorn.yukon.YukonException;
import yunikorn.yukon.YukonVideoFrame;
import yunikorn.yukon.YukonVideoHeader;
import yunikorn.yukon.factory.*;
import yunikorn.yukon.io.YukonStreamReader;

public class YukonPacketObservable
		implements MediaStreamObservable, StreamParser {


	
	Vector observers;
	Vector streamListener;
	public YukonPacketObservable() {
		super();

		observers = new Vector();
		streamListener = new Vector();
	}

	public void readStream(InputStream source) {
		
		YukonVideoHeader currentVideoHeader=null;
		YukonAudioHeader currentAudioHeader=null;
		
		YukonStreamReader reader = new YukonStreamReader(source);
		while (reader.hasNext()) {
			RawCompressedPacket current = (RawCompressedPacket)reader.next();

			
                        try {
                        RawUncompressedPacket rawUPacket = UncompressedPacketFactory
					.createPacket(current);
			
				try {
					currentVideoHeader = VideoHeaderFactory
							.createVideoHeader(rawUPacket);
					// TODO If we enhance the PacketObserver to be able to
					// receive VideoHeaders, here comes the code

					continue;
				} catch (InvalidTypeException ex) {
				}
				try {
					if (currentVideoHeader != null) {
						
					 // skip this step as we have no video header
					notifyObservers(VideoFrameFactory.createVideoFrame(
							currentVideoHeader, rawUPacket));
					}
				} catch (InvalidTypeException ex) {
					
				}
				try {
					currentAudioHeader = AudioHeaderFactory.createAudioHeader(rawUPacket);
					//TODO notify an enhanced PacketObserver
				}
				catch (InvalidTypeException ex){}
				try {
					if (currentAudioHeader != null) {
					notifyObservers(AudioSequenceFactory.createAudioSequence(currentAudioHeader, rawUPacket));
					}
					//TODO notify an enhanced PacketObserver
				}
				catch (InvalidTypeException ex){}


			} catch (YukonException ex) {
				// We land here if we have more serious problems (thrown from
				// the factories)
			}

		}
		notifyEndOfStream();

	}
	
	
	private void notifyEndOfStream() {
		synchronized (observers) {
		for (int i = 0; i< observers.size();i++) {
                MediaStreamListener t = (MediaStreamListener)observers.elementAt(i);
				t.onEndOfStream();
			}
		}
		synchronized(streamListener)
		{
		for (int i = 0; i< streamListener.size();i++) {
                StreamListener t = (StreamListener)streamListener.elementAt(i);
				t.onEndOfStream();
			}
		}
	}
	
	private void notifyObservers(YukonVideoFrame frame)
	{
		synchronized (observers) {
		for (int i = 0; i< observers.size();i++) {
                DataPacketListener t = (DataPacketListener)observers.elementAt(i);
			t.onVideoFrame(frame);
		}
		}
	}
	private void notifyObservers(YukonAudioSequence sample)
	{
		synchronized (observers) {
		for (int i = 0; i< observers.size();i++) {
                DataPacketListener t = (DataPacketListener)observers.elementAt(i);
			t.onAudioSequence(sample);
		
		}
               }
	}

	public void addListener(PacketListener observer) {
		synchronized(observers) {
		observers.addElement(observer);
		}
	}

	public void removeListener(PacketListener observer) {
		synchronized(observers) {
		observers.removeElement(observer);
		}
	}
	
	public void addStreamListener(StreamListener listener) {
		streamListener.addElement(listener);
		
	}
	public void removeStreamListener(StreamListener listener) {
		streamListener.removeElement(listener);
		
	}
	
	

}
