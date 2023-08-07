//package com.wicio.shiplog.config;
//
//import com.wicio.shiplog.route.producer.NewVesselLogEvent;
//import java.util.HashMap;
//import java.util.Map;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.config.KafkaListenerContainerFactory;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//
//@Configuration
//public class KafkaConsumerConfig {
//
//  @Value("${spring.kafka.bootstrap-servers}")
//  private String bootstrapServers;
//
//  public Map<String, Object> consumerConfig() {
//    Map<String, Object> props = new HashMap<>();
//    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//    return props;
//  }
//
//  @Bean
//  public ConsumerFactory<String, NewVesselLogEvent> consumerFactory() {
//    return new DefaultKafkaConsumerFactory<>(consumerConfig(),
//        new StringDeserializer(),
//        new JsonDeserializer<>(NewVesselLogEvent.class));
//  }
//
//  @Bean
//  public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, NewVesselLogEvent>>
//  listenerFactory999(ConsumerFactory<String, NewVesselLogEvent> consumerFactory) {
//    ConcurrentKafkaListenerContainerFactory<String, NewVesselLogEvent> factory
//        = new ConcurrentKafkaListenerContainerFactory<>();
//    factory.setConsumerFactory(consumerFactory);
//    return factory;
//  }
//
//  //TODO
////  @Bean
////  public KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry() {
////    KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry = new KafkaListenerEndpointRegistry();
////    return kafkaListenerEndpointRegistry;
////  }
//}
