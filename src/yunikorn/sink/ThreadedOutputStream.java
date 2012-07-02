package yunikorn.sink;


import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

public class ThreadedOutputStream extends OutputStream {

    private OutputStream out;
	private class WriterThread implements Runnable {

		public void run()
		{
			try {
			while(open && !excepted) {
				OutputStreamCommand command = null;
				synchronized(buffers)
				{
					if (buffers.isEmpty()) {
						buffers.wait();
					}
					if (buffers.size()>0) {
                                        command = (OutputStreamCommand)(buffers.firstElement());
                                        buffers.removeElementAt(0);
                                        }
				}
				if (command == null){excepted=true;buffers.removeAllElements();return;} //In most cases it doesn't matter if you clear buffers, because I/O-Streams throwing exceptions are usually disbanded -> garbage collected
				try {
				command.execute();
				}catch(RuntimeException ex){
					System.err.println("Workaround catch: " + ex);
					excepted=true;
				}
			}
			} catch (InterruptedException ex){
				excepted=true;
			}
		}

	}

	
	private interface OutputStreamCommand
	{
		public void execute();
	}
	private class CloseCommand implements OutputStreamCommand
	{

		public void execute() {
			try {
			out.close();
			}catch(IOException ex)
			{excepted=true;}
		}
		
	}
	private class FlushCommand implements OutputStreamCommand
	{

		public void execute() {
			try {
			out.flush();
			}catch(IOException ex)
			{excepted=true;}
		}
		
	}
	private class WriteByteCommand implements OutputStreamCommand
	{
		
		int write;
		public WriteByteCommand(int b)
		{
		write = b;
		}
		public void execute() {
			try {
			out.write(write);
			}catch(IOException ex)
			{excepted=true;}
		}
		
	}
	private class WriteByteArrayCommand implements OutputStreamCommand
	{
		
		byte[] write;
		public WriteByteArrayCommand(byte[] array)
		{
		write = array;
		}
		public void execute() {
			try {
			out.write(write);
			}catch(IOException ex)
			{excepted=true;}
		}
		
	}
	private class WriteOffsetByteArrayCommand implements OutputStreamCommand
	{
		
		byte[] write;
		int offset;
		int length;
		public WriteOffsetByteArrayCommand(byte[] array, int offset, int length)
		{
		write = array;
		this.offset=offset;
		this.length=length;;
		}
		public void execute() {
			try {
			out.write(write,offset,length);
			}catch(IOException ex)
			{excepted=true;}
		}
		
	}

	Vector buffers;
	private boolean open;
	private boolean excepted;
	private boolean started;
	//private OutputStream out;
	//WriterThread writer;

	public ThreadedOutputStream(OutputStream out, boolean standalone) {
		super();
                this.out = out;
		buffers = new Vector();
		excepted = false;
		open=true;
		started=standalone;
		if (standalone) {
		new Thread(new WriterThread()).start();
		}

	}
	public void start()
	{
		if (started){return;}
		started=true;
		new WriterThread().run();
	}

	
	public void close() throws IOException {
		// TODO Auto-generated method stub
		process(new CloseCommand());
		
	}

	
	public void flush() throws IOException {
		// TODO Auto-generated method stub
		process(new FlushCommand());
	}

	
	public void write(byte[] b, int off, int len) throws IOException {
		/*byte[] newb = new byte[len];
		System.arraycopy(b, off, newb, 0, len);*/
		process(new WriteOffsetByteArrayCommand(b,off,len));
	}

	
	public void write(byte[] b) throws IOException {
		/*byte[] newb = new byte[b.length];
		System.arraycopy(b, 0, newb, 0, b.length);*/
		process(new WriteByteArrayCommand(b));
	}

	
	public void write(int b) throws IOException {
		process(new WriteByteCommand(b));

	}
	private void process(OutputStreamCommand command) throws IOException
	{
		if (excepted){throw new IOException();}
		synchronized (buffers) {
			buffers.addElement(command);
			buffers.notify();
		}
	}

}
