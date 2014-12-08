import java.util.concurrent.Semaphore;

public class ThreadTest extends Thread {

	private Semaphore semaphore;
	private int count;
	private int series;

	public ThreadTest(int threadcount,int series) {
		this.semaphore = new Semaphore(series);
		this.count = threadcount;
		this.series = series;
		
	}

	public  Semaphore getSemaphore() {
		return this.semaphore;
	}

	public int getCount() {
		return count;
	}
	
	public int getSeries(){
		return series;
	}

	public static void main(String[] args) {
		final ThreadTest threadtest = new ThreadTest(10,60);
		for(int i = 0;i<threadtest.getCount();i++){
			Thread th = new Thread(new RunnerThread(threadtest));
			th.start();
		}
	}

}

class RunnerThread implements Runnable {
	private ThreadTest threadtest;

	public RunnerThread(ThreadTest threadtest) {
		this.threadtest = threadtest;
	}
	
	private Semaphore  getSemaphore(){
		return threadtest.getSemaphore();
	}
	
	private int getCount(){
		return threadtest.getCount();
	}
	
	private int getSeries() {
		return threadtest.getSeries();
	}

	@Override
	public void run() {

		while (true) {
			int currentThreadno = Integer.parseInt(Thread.currentThread().getName().substring(7)+ "");
			//int currentSemIndex = getSemaphore().availablePermits()%getCount() + 1;
			int currentSemIndex = (((getSeries()-getSemaphore().availablePermits()))%getCount())+1;
			if (currentThreadno == currentSemIndex) {
				try {
					Thread.sleep(10);
					getSemaphore().acquire();
					
					System.out.println(Thread.currentThread().getName()+" :"+(getSeries()-getSemaphore().availablePermits()));
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			/*if(getSemaphore().availablePermits()==0){
				getSemaphore().release(getCount());
			}*/
		}

	}

}
