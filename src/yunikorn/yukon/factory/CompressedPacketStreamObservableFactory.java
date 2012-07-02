package yunikorn.yukon.factory;


import yunikorn.core.packet.metainterfaces.PacketObservable;
import yunikorn.core.packet.metainterfaces.PacketObservableFactory;
import yunikorn.yukon.event.*;

public class CompressedPacketStreamObservableFactory implements
		PacketObservableFactory {

    public PacketObservable createPacketObservable() {
        return new CompressedPacketStreamObservable();
    }

	//public PacketObservable createPacketObservable() {
		// TODO Auto-generated method stub
		//return new CompressedPacketStreamObservable();
	//}
    
    
    

}
