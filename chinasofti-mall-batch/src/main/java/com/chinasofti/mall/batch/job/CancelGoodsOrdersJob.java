package com.chinasofti.mall.batch.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.chinasofti.mall.batch.util.DateUtils;

public class CancelGoodsOrdersJob {
	
	private static Logger logger = LoggerFactory.getLogger(CancelGoodsOrdersJob.class);
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("job/CancelGoodsOrdersJob.xml");
		
		JobLauncher launcher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean("cancelGoodsOrdersJob");
		
		try {
			logger.info("CancelGoodsOrdersJob在 "+DateUtils.getCurrentTime()+"开始执行...");
			/* 运行Job */
			JobExecution result = launcher.run(job,new JobParametersBuilder()
									.addString("handleDate", "20171129")
									.toJobParameters());
			/* 处理结束，控制台打印处理结果 */
			logger.info(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			((ConfigurableApplicationContext)context).close();
		}
	}
}