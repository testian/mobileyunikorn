package yunikorn.core.packet;

import yunikorn.core.StreamListener;
import yunikorn.core.packet.metainterfaces.DataPacketListener;

/**
 * @author testi
 * This interface is used for video streams.
 *
 * @param <T>
 * @param <E>
 */
public interface MediaStreamListener extends DataPacketListener,
		StreamListener {

}
