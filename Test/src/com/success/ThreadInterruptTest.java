package com.success;

/**
 *
 *
 * <pre>
 * Thread.interrupted -> Checks whether the current thread is interrupted and clear the interrupted status.
 * Thread.currentThread.isInterrupted -> Checks whether the current thread is interrupted.
 * Thread.currentThread.interrupt -> Interrupts this thread. Set the interrupted status.
 *
 * When InterruptedException occurs - if any thread has interrupted the current thread,
 * the interrupted status of the current thread is cleared when this exception is thrown.
 *
 * So, catch the exception and mark the thread interrupted again using, Thread.currentThread.interrupt, or re-throw the exception, 
 * so that the caller will handle the interrupted scenario gracefully.
 * </pre>
 *
 * @author Tamil
 */
public class ThreadInterruptTest {

  public static void main(String[] args) {

    System.out.println("main thread id " + Thread.currentThread().getId());
    Runnable r = ThreadInterruptTest::run;
    Thread t1 = new Thread(r);
    t1.start();
    System.out.println("0 new thread isInterrupted? " + t1.isInterrupted());
    t1.interrupt(); // (from the main thread) interrupt thread t1.
    System.out.println("0 new thread isInterrupted? " + t1.isInterrupted());
    System.out.println("main thread id " + Thread.currentThread().getId());
    System.out.println("end");
  }

  static void run() {
    System.out.println("new thread id " + Thread.currentThread().getId());
    System.err.println("01 new thread isInterrupted? " + Thread.currentThread().isInterrupted());
    try {
      System.out.println("sleeping....");
      Thread.sleep(5000);
      System.out.println("wokeup....");
    } catch (InterruptedException e) {
      // when InterruptedException occurs - if any thread has interrupted the current thread. The
      // interrupted status of the current thread is cleared when this exception is thrown.
      System.err.println("11 new thread id? " + Thread.currentThread().getId());
      System.err.println(
          "11 new thread isInterrupted? "
              + Thread.currentThread().isInterrupted()); // this would return false.
      Thread.currentThread()
          .interrupt(); // main thread interrupted this thread. so mark this thread as interrupted.
      // (status was cleared previously when InterruptedException occurred)
      System.err.println(e.getMessage());
    }
    System.err.println("4 new thread id? " + Thread.currentThread().getId());
    System.err.println(
        "5 new thread isInterrupted? "
            + Thread.currentThread().isInterrupted()); // this would return true
  }
}
