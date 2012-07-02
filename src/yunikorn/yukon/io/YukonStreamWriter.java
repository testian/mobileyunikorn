package yunikorn.yukon.io;
import java.io.*;
import ext.*;
import yunikorn.yukon.RawCompressedPacket;
public class YukonStreamWriter {
OutputStream sink;

public YukonStreamWriter(OutputStream sink) {
	super();
	this.sink = sink;
}

public void close() throws IOException {
	sink.close();
}
public void write(RawCompressedPacket packet) throws IOException
{ //TODO
	DataOutputStream out = new DataOutputStream(sink);
	out.writeByte(packet.getType());
	out.writeLong(MyInteger.reverseBytes((int)packet.getTime()));
        out.writeInt(0);
        
	out.writeLong(MyInteger.reverseBytes((int)packet.getDecompressedSize()));
        out.writeInt(0);
        
	out.writeLong(MyInteger.reverseBytes((int)packet.getCompressedSize()));
        out.writeInt(0);
        
	sink.write(packet.getBuffer());
}

}
