package yunikorn.yukon.factory;
import yunikorn.yukon.event.SilentYukonPacketObservable;
import yunikorn.core.packet.*;
import yunikorn.core.packet.metainterfaces.*;
import yunikorn.yukon.*;
public class SilentYukonPacketObservableFactory implements PacketObservableFactory {

	public  PacketObservable createPacketObservable() {
		// TODO Auto-generated method stub
		return new SilentYukonPacketObservable();
	}




}
