package vn.shortclips.application;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import vn.shortclips.infrastructure.datasource.DataSourceConfiguration;

public class BatchRunner {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
				DataSourceConfiguration.class);
		VideoBatch batch = context.getBean(VideoBatch.class);
		try {
			batch.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
		context.registerShutdownHook();
	}
}
