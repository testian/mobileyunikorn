package yunikorn.sink;

import java.io.*;
import java.util.*;


public class DynamicOutputStreamMulticaster extends OutputStream {

	Vector observers;
	Vector waitQueue;
	boolean queue;
	boolean closed;
	boolean threaded;
	public DynamicOutputStreamMulticaster(boolean threaded) {
		super();
		observers = new Vector();
		waitQueue = new Vector();
		queue = false;
		this.threaded=threaded;
		closed=false;
	}
	public DynamicOutputStreamMulticaster()
	{
		this(false);
	}

	public void queueObservers() {
		queue = true;
	}

	public void flushObservers() {
		queue = false;
		synchronized (observers)
		{
			synchronized (waitQueue)
			{
				
                            for (int i = 0; i<waitQueue.size();i++)
                            {
                                observers.addElement(waitQueue.elementAt(i));
                            }
                            
				waitQueue.removeAllElements();
			}
		}
	}

	
	public void write(int b) {
		synchronized (observers) {
			Vector problematic = new Vector();
			for (int i = 0;i<observers.size();i++) {
			OutputStream t = (OutputStream)observers.elementAt(i);
                            try {
					t.write(b);
				} catch (IOException ex) {
					problematic.addElement(t);
					System.err.println(ex);
				}
				/*catch (RuntimeException ex)
				{
					problematic.add(t);
					System.err.println("Multiplexer write - Workaround catch: " + ex);
				}*/
			}
			for (int i = 0;i<problematic.size();i++)
			{
				observers.removeElement(problematic.elementAt(i));
			}
			
		}

	}
	public void clear()
	{
		synchronized(observers)
		{
			observers.removeAllElements();
		}
	}

	public void close() {
		synchronized (observers) {
			closed=true;
			for (int i = 0;i<observers.size();i++) {
			OutputStream t = (OutputStream)observers.elementAt(i);	
                            try {
					t.close();
				} catch (IOException ex) {
					System.err.println(ex);
				}
				/*catch (RuntimeException ex) {
				System.err.println("Multiplexer close - Workaround catch: " + ex);
				}*/
			}
			observers.removeAllElements();
		}
	}
	public void reopen()
	{
		closed=false;
	}
	
	
	
	
	public void flush() throws IOException {
		synchronized (observers) {
			Vector problematic = new Vector();
			for (int i = 0;i<observers.size();i++) {
                            OutputStream t = (OutputStream)observers.elementAt(i);	
				try {
					t.flush();
				} catch (IOException ex) {
					problematic.addElement(t);
					System.err.println(ex);
				}
				/*catch (RuntimeException ex)
				{
					problematic.add(t);
					System.err.println("Multiplexer write - Workaround catch: " + ex);
				}*/
			}
			for (int i = 0;i<problematic.size();i++)
			{
				observers.removeElement(problematic.elementAt(i));
			}
			
		}

	}
	
	
	

	public void addStream(OutputStream stream) {
		OutputStream e;
		if (threaded)
		{
			e = new ThreadedOutputStream(stream,true);
		} else {
			e = stream;
		}
		
		if (!queue) {
			synchronized (observers) {
				if (closed) {
					try {
					stream.close();
					return;
					} catch (IOException ex){System.err.println(ex);}
				}
			observers.addElement(e);
			}
		} else
		{
			synchronized (waitQueue) { //!
			waitQueue.addElement(e);
			}
		}
	}

	/*private void remove(Object o) {

		synchronized (observers) {
		if (!observers.remove(o)) {
			synchronized (waitQueue) {
			waitQueue.remove(o);
			}
		}
	}
	}*/
	public boolean isEmpty()
	{
		synchronized (observers) {
			return observers.isEmpty();
		}
	}

	
	public void write(byte[] b, int off, int len) throws IOException {
		// TODO Auto-generated method stub
		//Optimization-Override
		byte[] newb;
		int newoff;
		
		if (threaded) {
		newb = new byte[len];
		System.arraycopy(b, off, newb, 0, len); //Required because byte array isn't immutable and could be changed if ThreadedOutputStream is used (alternate is to use CopyThreadedOutputStream, which would cause more arraycopy calls if there is more than one OutputStream)
		newoff = 0;
		} else {
			newb = b;
			newoff = off;
		}

			synchronized (observers) {
				Vector problematic = new Vector();
				for (int i = 0;i<observers.size();i++) {
                            OutputStream t = (OutputStream)observers.elementAt(i);	
					try {
						t.write(newb,newoff,len);
					} catch (IOException ex) {
						problematic.addElement(t);
						System.err.println(ex);
					}
					/*catch (RuntimeException ex)
					{
						problematic.add(t);
						System.err.println("Multiplexer write - Workaround catch: " + ex);
					}*/
				}
			for (int i = 0;i<problematic.size();i++)
			{
				observers.removeElement(problematic.elementAt(i));
			}
				
			}

		
		
		
		
	}

	
	public void write(byte[] b) throws IOException {
		// TODO Auto-generated method stub
		write(b,0,b.length);
	}
	public int count()
	{
		return observers.size() + waitQueue.size();
	}
	
	

}
