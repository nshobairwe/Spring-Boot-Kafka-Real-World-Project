package net.javaguides.springboot;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import okhttp3.HttpUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@Service
public class WikimediaChangesProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(WikimediaChangesProducer.class);

    //inject kafka template, this will help to send sms to kafka brocker
    private KafkaTemplate<String, String> kafkaTemplate;

    public WikimediaChangesProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    //a method that will read a real time stream data from wikimedia
    public void sendMessage(){
        String topic = "wikimedia_recentchange";

        //to read real time stream data from wikimedia,we use event source
        EventHandler eventHandler = new WikimediaChangesHandler(kafkaTemplate, topic);
        String url = "https://stream.wikimedia.org/v2/stream/recentchange";

        //event source that connect to the wikimedia source
        try {
            // EventSource that connects to the Wikimedia source
            EventSource.Builder builder = new EventSource.Builder(eventHandler, HttpUrl.get(new URL(url)));
            EventSource eventSource = builder.build();
            eventSource.start();
        } catch (MalformedURLException e) {
            LOGGER.error("Invalid URL: {}", url, e);
        }

        try {
            TimeUnit.MINUTES.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}
