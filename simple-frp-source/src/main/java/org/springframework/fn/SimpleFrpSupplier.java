package org.springframework.fn;

import java.util.function.Supplier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import reactor.core.publisher.Flux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

/**
 * @author Christian Tzolov
 */
@SpringBootApplication
public class SimpleFrpSupplier {

	private static final Log logger = LogFactory.getLog(SimpleFrpSupplier.class);

	@Bean
	public Supplier<Flux<Message<byte[]>>> frp() {
		return () -> Flux.create(emitter -> {
			//for (int i = 0; i < Integer.MAX_VALUE; i++) {
			for (int i = 0; i < 1000; i++) {
				emitter.next(MessageBuilder.withPayload(String.valueOf(i).getBytes()).build());
				System.out.println("FRP: " + i);
				try {
					Thread.sleep(100);
				}
				catch (Exception e) {
					emitter.error(e);
				}
			}

			emitter.onDispose(() -> {
				System.out.println("Dispose");
			});
		});
	}

	public static void main(String[] args) {
		SpringApplication.run(SimpleFrpSupplier.class, args);
	}
}
