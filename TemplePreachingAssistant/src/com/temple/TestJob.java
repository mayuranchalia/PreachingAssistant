package com.temple;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TestJob implements Job {

    @Override
    public void execute(final JobExecutionContext ctx)
            throws JobExecutionException {

        System.out.println("Executing Job");

    }
    
    public static void main(String[] args) {
		String data = "974359559-1 +919686577970 DELIVRD";
		System.out.println(data.substring(data.lastIndexOf(" ")+1));
	}

}