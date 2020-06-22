package hufs.ces.cirbuf;

public class CircularBuffer<T> implements Buffer<T> {

	// each array element is a buffer 
	private final static int DEFAULT_BUFFER_COUNT = 10;
	private T buffers[];
	private int bufSize = DEFAULT_BUFFER_COUNT;

	// occupiedBufferCount maintains count of occupied buffers
	private int occupiedBufferCount = 0;

	// variables that maintain read and write buffer locations
	private int front = 0;
	private int rear = 0;

	// constructor

	public CircularBuffer(int bufSize) {
		this.bufSize = bufSize;
		buffers = (T[]) new Object[bufSize];
		front = 0;
		rear = 0;
		occupiedBufferCount = 0;
	}

	public CircularBuffer() {
		this(DEFAULT_BUFFER_COUNT);
	}

	// place value into buffer
	public synchronized void write(T value) {
		// while there are no empty locations, place thread in waiting state
		while ( occupiedBufferCount == buffers.length ) {

			// output thread information and buffer information, then wait
			try {
				wait();
			}

			// if waiting thread interrupted, print stack trace
			catch ( InterruptedException exception )
			{
				exception.printStackTrace();
			}

		} // end while

		// place value in writeLocation of buffers
		buffers[ rear ] = value;
		// just produced a value, so increment number of occupied buffers
		++occupiedBufferCount;

		// update writeLocation for future write operation
		rear = ( rear + 1 ) % buffers.length;

		//System.out.println("Write:" + this.createStateOutput());                      
		notify(); // return waiting thread (if there is one) to ready state

	} // end method set

	// return value from buffer
	public synchronized T read() {  

		// while no data to read, place thread in waiting state
		while ( occupiedBufferCount == 0 ) {

			// output thread information and buffer information, then wait
			try {        	 

				wait();
			}

			// if waiting thread interrupted, print stack trace
			catch ( InterruptedException exception ) {
				exception.printStackTrace();
			}

		} // end while

		// obtain value at current readLocation
		T readValue = buffers[ front ];

		// just consumed a value, so decrement number of occupied buffers
		--occupiedBufferCount;

		// update readLocation for future read operation
		front = ( front + 1 ) % buffers.length;

		//System.out.println("Read:" + this.createStateOutput());
		notify(); // return waiting thread (if there is one) to ready state

		return readValue;

	} // end method get


	public T[] getBuffers() {
		return buffers;
	}

	public int getBufSize() {
		return bufSize;
	}

	public synchronized void setBufSize(int bufSize) {
		this.bufSize = bufSize;
		buffers = (T[]) new Object[bufSize];
		front = 0;
		rear = 0;
		occupiedBufferCount = 0;
	}

	public int getOccupiedBufferCount() {
		return occupiedBufferCount;
	}

	public int getFront() {
		return front;
	}

	public int getRear() {
		return rear;
	}

} // end class CircularBuffer
