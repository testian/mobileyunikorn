package yunikorn.core.packet.metainterfaces;

import yunikorn.core.packet.AudioSequence;
import yunikorn.core.packet.VideoFrame;

/**
 * @author testi
 * Use StreamDataPacketListener if you read from a video stream.
 * @param <T>
 * @param <E>
 */
public interface DataPacketListener extends PacketListener {


public void onVideoFrame(VideoFrame frame);
public void onAudioSequence(AudioSequence sequence);
//public void onEndOfStream();
}
