package edu.wgu.d387_sample_code;

import java.time.ZonedDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class D387SampleCodeApplication {

	public static void main(String[] args) throws InterruptedException {
		WelcomeMessage.displayWelcomeMessages();

		ZonedDateTime presentation = ZonedDateTime.of(
				2026, 9, 15, 15, 0, 0, 0, TimeZoneConverter.EASTERN);
		System.out.println(TimeZoneConverter.getPresentationTimes(presentation));

		SpringApplication.run(D387SampleCodeApplication.class, args);
	}

}