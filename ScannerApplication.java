package se.sciens;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Random;

public class ScannerApplication {

    public static void main(String[] args) {
        try{
            String fileName = "image/png";
            String extension = fileName.replaceAll(".*\\/", "");
            System.out.println(extension);
//            SchedulerFactory schedulerFactory=new StdSchedulerFactory();
//            //Get scheduler
//            Scheduler scheduler= schedulerFactory.getScheduler();
//            JobDetail job = JobBuilder.newJob(FileContentReader.class)
//                    .withIdentity(UtilConstant.JOB_NAME, UtilConstant.GROUP_NAME)
//                    .build();
//            //Associate Trigger to the Job
//            CronTrigger trigger = TriggerBuilder.newTrigger()
//                    .withIdentity(UtilConstant.TRIGGER_NAME, UtilConstant.GROUP_NAME)
//                    .withSchedule(CronScheduleBuilder.cronSchedule(UtilConstant.CRON_JOB))
//                    .forJob(UtilConstant.JOB_NAME, UtilConstant.GROUP_NAME)
//                    .build();
//            scheduler.scheduleJob(job,trigger);
//            scheduler.start();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
