package yunikorn.core.packet;

import yunikorn.core.StreamParser;
import yunikorn.core.packet.metainterfaces.DataPacketObservable;

public interface MediaStreamObservable extends StreamParser,
		DataPacketObservable {

}
