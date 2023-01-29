package nachos.threads;

import java.util.LinkedList;

/**
 * A <i>communicator</i> allows threads to synchronously exchange 32-bit
 * messages. Multiple threads can be waiting to <i>speak</i>, and multiple
 * threads can be waiting to <i>listen</i>. But there should never be a time
 * when both a speaker and a listener are waiting, because the two threads can
 * be paired off at this point.
 */
public class Communicator {
	/**
	 * Allocate a new communicator.
	 */
	public Communicator() {

	}

	/**
	 * Wait for a thread to listen through this communicator, and then transfer
	 * <i>word</i> to the listener.
	 *
	 * <p>
	 * Does not return until this thread is paired up with a listening thread.
	 * Exactly one listener should receive <i>word</i>.
	 *
	 * @param word the integer to transfer.
	 */
	public void speak(int word) {

		LOCK.acquire();
		
		SpeakerCount++;
		MailBox.add(word);
		
		if (ListenerCount == 0) {
			SpeakerCV.sleep();
		}
		
		SpeakerCount--;
		ListenerCV.wake();
		
		LOCK.release();
	}

	/**
	 * Wait for a thread to speak through this communicator, and then return the
	 * <i>word</i> that thread passed to <tt>speak()</tt>.
	 *
	 * @return the integer transferred.
	 */
	public int listen() {

		LOCK.acquire();
		
		ListenerCount++;
		
		if ((MailBox.size() == 0) && (SpeakerCount == 0)) {
			ListenerCV.sleep();
		}
		ListenerCount--;
		SpeakerCV.wake();

		LOCK.release();

		return MailBox.remove();
	}

	private Lock LOCK = new Lock();
	private Condition2 ListenerCV = new Condition2(LOCK);
	private Condition2 SpeakerCV = new Condition2(LOCK);

	private int ListenerCount = 0;
	private int SpeakerCount = 0;
	
	private LinkedList<Integer> MailBox = new LinkedList<Integer>();
}
