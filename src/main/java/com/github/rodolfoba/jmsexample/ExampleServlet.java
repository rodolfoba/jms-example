package com.github.rodolfoba.jmsexample;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/example")
public class ExampleServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final int MSG_COUNT = 100;

    @Inject
    private JMSContext context;

    @Resource(lookup = "java:/jms/queue/DLQ")
    private Queue queue;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.write("<h1>Quickstart: Example demonstrates the use of <strong>JMS 2.0</strong> and <strong>EJB 3.2 Message-Driven Bean</strong> in WildFly 8.</h1>");
        try {
            out.write("<p>Sending messages to <em>" + queue + "</em></p>");
            out.write("<h2>Following messages will be send to the destination:</h2>");
            
            JMSProducer producer = context.createProducer();
            for (int i = 0; i < MSG_COUNT; i++) {
                String text = "This is message " + (i + 1);
                producer.send(queue, text);
                out.write("Message (" + i + "): " + text + "</br>");
            }
            out.write("<p><i>Go to your WildFly Server console or Server log to see the result of messages processing</i></p>");
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
