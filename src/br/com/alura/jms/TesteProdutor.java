package br.com.alura.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;


public class TesteProdutor {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {

		InitialContext context = new InitialContext();

		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		connection.start();
		
		// Abstrai a parte transacional e configurar como será o recebimento da mensagem
		// o Session.AUTO_ACKNOWLEDGE ele vai confirmar o recebimento da mensagem
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		// Precisamos falar no contexto qual a queue
		Destination fila = (Destination) context.lookup("financeiro");
		
		MessageProducer producer = session.createProducer(fila);
		
		Message message = session.createTextMessage("<pedido><id>1234</id></pedido>");
				
		producer.send(message);
		
		new Scanner(System.in).nextLine();
		
		session.close();
		connection.close();
		context.close();
	}

}
