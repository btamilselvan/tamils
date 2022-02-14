package cmecf.programs;

import java.time.Instant;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzCronTest {

	private static void quartzTest() {
		try {

			JobDetail job = JobBuilder.newJob(TestJob.class).withIdentity("Test", "mygroup").build();

			Scheduler sched = StdSchedulerFactory.getDefaultScheduler();
			final CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("Test")
					.withSchedule(CronScheduleBuilder.cronSchedule("*/5 * * * * ?")).build();
			sched.scheduleJob(job, cronTrigger);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		quartzTest();
	}

	public static class TestJob implements Job {

		@Override
		public void execute(JobExecutionContext context) throws JobExecutionException {

			System.out.println("hello " + Instant.now());

		}

	}

}
