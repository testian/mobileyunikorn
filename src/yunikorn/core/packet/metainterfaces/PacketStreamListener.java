package yunikorn.core.packet.metainterfaces;


/**
 * @author testi
 * 
 * A PacketStreamListener listens for incoming streams. A PacketObservable is forwarded via onIncomingStream, before it starts reading the stream.
 * A class implementing this interface should add PacketListener objects to the observable an return. 
 * @param <E> Describes what type the object is, that describes the source of this stream  or provides information about it e.g. InetAddress
 * @param <T>
 */
public interface PacketStreamListener {

	public void onIncomingStream(Object sourceDescription, PacketObservable packetObservable);
}
