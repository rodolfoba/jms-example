package com.github.rodolfoba.jmsexample;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class HelloResource {

    @Inject
    private JMSContext context;

    @Resource(lookup = "java:/jms/queue/DLQ")
    private Queue queue;

    @GET
    @Path("/")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloWorld() {
        String message = "Hello World";
        context.createProducer().send(queue, message);
        return message;
    }

}
