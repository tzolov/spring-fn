package org.springframework.fn;

import reactor.core.publisher.Flux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.stream.reactive.StreamEmitter;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

/**
 * @author Christian Tzolov
 */
//@SpringBootApplication
//@EnableBinding(Source.class)
public class StdReactiveSource {

	//@StreamEmitter
	//@Output(Source.OUTPUT)
	public Flux<Message<byte[]>> frp() {
		return Flux.create(emitter -> {
			for (int i = 0; i < Integer.MAX_VALUE; i++) {
				emitter.next(MessageBuilder.withPayload(String.valueOf(i).getBytes()).build());
				System.out.println("SCSt : " + i);
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
		SpringApplication.run(StdReactiveSource.class, args);
	}

}
