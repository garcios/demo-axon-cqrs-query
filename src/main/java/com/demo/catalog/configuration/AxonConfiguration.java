package com.demo.catalog.configuration;

import com.demo.catalog.AxonCqrsQuerySideApplication;
import com.rabbitmq.client.Channel;

import org.axonframework.amqp.eventhandling.DefaultAMQPMessageConverter;
import org.axonframework.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(AxonCqrsQuerySideApplication.class);

    @Bean
    public SpringAMQPMessageSource eventsMethod(Serializer serializer) {
        return new SpringAMQPMessageSource(new DefaultAMQPMessageConverter(serializer)) {

            @RabbitListener(queues = "${axon.amqp.exchange}")
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                LOG.debug("Event Received: {}", message.getBody().toString());
                super.onMessage(message, channel);
            }
        };
    }
    
    

	@Bean
	Serializer axonJsonSerializer() {
		return new JacksonSerializer();
	}

}
