package nachos.threads;

import nachos.machine.*;
import java.util.LinkedList;

/**
 * Uses the hardware timer to provide preemption, and to allow threads to sleep
 * until a certain time.
 */
public class Alarm {
	/**
	 * Allocate a new Alarm. Set the machine's timer interrupt handler to this
	 * alarm's callback.
	 *
	 * <p>
	 * <b>Note</b>: Nachos will not function correctly with more than one alarm.
	 */
	public Alarm() {
		Machine.timer().setInterruptHandler(new Runnable() {
			public void run() {
				timerInterrupt();
			}
		});
	}

	/**
	 * The timer interrupt handler. This is called by the machine's timer
	 * periodically (approximately every 500 clock ticks). Causes the current thread
	 * to yield, forcing a context switch if there is another thread that should be
	 * run.
	 */
	public void timerInterrupt() {
		int size = SavedInfo.size();
		for (int i = size; i > 0; i--) {
			Tuple T = SavedInfo.get(size - i);
			if (T.wakeUpTime <= Machine.timer().getTime()) {
				Machine.interrupt().disable();
				T.ThreadPointer.ready();
				SavedInfo.remove(size - i);
				Machine.interrupt().enable();
			}
		}
	}

	/**
	 * Put the current thread to sleep for at least <i>x</i> ticks, waking it up in
	 * the timer interrupt handler. The thread must be woken up (placed in the
	 * scheduler ready set) during the first timer interrupt where
	 *
	 * <p>
	 * <blockquote> (current time) >= (WaitUntil called time)+(x) </blockquote>
	 *
	 * @param x the minimum number of clock ticks to wait.
	 *
	 * @see nachos.machine.Timer#getTime()
	 */
	@SuppressWarnings("static-access")
	public void waitUntil(long x) {
		Machine.interrupt().disable();
		long wakeTime = Machine.timer().getTime() + x;
		Tuple tempTuple = new Tuple(wakeTime, KThread.currentThread());
		SavedInfo.add(tempTuple);
		tempTuple.ThreadPointer.sleep();
		Machine.interrupt().enable();

	}

	private static LinkedList<Tuple> SavedInfo = new LinkedList<Tuple>();

	class Tuple {
		private long wakeUpTime;
		private KThread ThreadPointer;

		public Tuple(long WT, KThread TP) {
			wakeUpTime = WT;
			ThreadPointer = TP;
		}
	}

}