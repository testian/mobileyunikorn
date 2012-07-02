package yunikorn.yukon.event;

import yunikorn.yukon.*;
import java.util.*;
import java.io.*;
import yunikorn.core.*;
import yunikorn.core.packet.metainterfaces.*;
import yunikorn.core.packet.*;
import yunikorn.yukon.factory.*;

import yunikorn.yukon.io.YukonStreamReader;

public class SilentYukonPacketObservable implements MediaStreamObservable, StreamParser {

	Vector observers;
	Vector streamListener;
	public SilentYukonPacketObservable() {
		super();

		observers = new Vector();
		streamListener = new Vector();
	}

	public void readStream(InputStream source) {

		YukonVideoHeader currentVideoHeader = null;
		YukonAudioHeader currentAudioHeader = null;

		YukonStreamReader reader = new YukonStreamReader(source);
		while (reader.hasNext()) {
                    //System.out.println("het nexte! mano!");
			RawCompressedPacket current = (RawCompressedPacket)reader.next();
                        //System.out.println("Hesch en: " + current);
			try {
				try {
					currentVideoHeader = VideoHeaderFactory
							.createVideoHeader(current);
					// TODO If we enhance the PacketObserver to be able to
					// receive VideoHeaders, here comes the code

					continue;
				} catch (InvalidTypeException ex) {
				//System.out.println("not a video header");
                                }
				try {
					if (currentVideoHeader != null) {
                                            

						// skip this step as we have no video header
					SilentYukonVideoFrame frame =SilentVideoFrameFactory.createVideoFrame(currentVideoHeader, current);
                                        current = null;
                                            notifyObservers(frame);
                                            continue;
					}
				} catch (InvalidTypeException ex) {
                                //System.out.println("not a video packet");
				}
				try {
					currentAudioHeader = AudioHeaderFactory
							.createAudioHeader(current);
					// TODO notify an enhanced PacketObserver
				} catch (InvalidTypeException ex) {
				//System.out.println("not an audio header");
                                }
				try {
					if (currentAudioHeader != null) {
                                            
                                            
                                            SilentYukonAudioSequence sequence = SilentAudioSequenceFactory
								.createAudioSequence(currentAudioHeader,
										current);
                                            current = null;
                                            notifyObservers(sequence);
                                            continue;

					}
					// TODO notify an enhanced PacketObserver
				} catch (InvalidTypeException ex) {
                                    //System.out.println("not an audio packet");
				}

			} catch (YukonException ex) {
				// We land here if we have more serious problems (thrown from
				// the factories)
                            System.err.println("Invalid packet: " + ex);
			}
            //System.err.println("No match");
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
			for (int i = 0; i< observers.size();i++) {
			MediaStreamListener t = (MediaStreamListener)observers.elementAt(i);
				t.onEndOfStream();
			}
		}
	}

	private void notifyObservers(SilentYukonVideoFrame frame) {
	//System.out.println("notifyObservers, video");
            synchronized (observers) {
		for (int i = 0; i< observers.size();i++) {
			MediaStreamListener t = (MediaStreamListener)observers.elementAt(i);
				t.onVideoFrame(frame);
			}
		}
	}

	private void notifyObservers(SilentYukonAudioSequence sample) {
	//System.out.println("notifyObservers, audio");	
            synchronized (observers) {
			for (int i = 0; i< observers.size();i++) {
			MediaStreamListener t = (MediaStreamListener)observers.elementAt(i);
				t.onAudioSequence(sample);
			}
		}
	}

	public void addListener(
			PacketListener observer) {
		synchronized (observers) {
			observers.addElement(observer);
		}
	}

	/*public void removeListener(
			StreamDataPacketListener<SilentYukonVideoFrame, SilentYukonAudioSequence> listener) {
		// TODO Auto-generated method stub
		
	}*/

	public void removeListener(
			PacketListener observer) {
		synchronized (observers) {
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
