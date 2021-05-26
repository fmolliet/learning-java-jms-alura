package br.com.alura.jms;

import java.io.StringWriter;
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
import javax.xml.bind.JAXB;

import br.com.alura.jms.modelo.Pedido;
import br.com.alura.jms.modelo.PedidoFactory;


public class TesteProdutorTopico {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {

		InitialContext context = new InitialContext();

		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection("admin","senha");
		connection.start();
		
		// Abstrai a parte transacional e configurar como será o recebimento da mensagem
		// o Session.AUTO_ACKNOWLEDGE ele vai confirmar o recebimento da mensagem
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		// Precisamos falar no contexto qual a queue
		Destination topico = (Destination) context.lookup("loja");
		
		MessageProducer producer = session.createProducer(topico);
		
		Pedido pedido = new PedidoFactory().geraPedidoComValores();
		
		//StringWriter writer = new StringWriter();
		//JAXB.marshal(pedido, writer);
		
		//String xml = writer.toString();
		//System.out.println(xml);
		
		Message message = session.createObjectMessage(pedido);
		//message.setBooleanProperty("ebook", false);
		
		producer.send(message);
		
		//new Scanner(System.in).nextLine();
		
		session.close();
		connection.close();
		context.close();
	}

}
