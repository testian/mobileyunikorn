package yunikorn.core.packet.metainterfaces;

public interface PacketObservable {
	public void addListener(PacketListener listener);
	public void removeListener(PacketListener listener);
}
