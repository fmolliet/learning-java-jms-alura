package br.com.alura.jms;

import java.io.Serializable;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;

import br.com.alura.jms.modelo.Pedido;


public class TesteConsumidorTopicoComercial {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {

		InitialContext context = new InitialContext();

		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection("admin", "senha");
		connection.setClientID("comercial");
		
		connection.start();
		
		// Abstrai a parte transacional e configurar como ser? o recebimento da mensagem
		// o Session.AUTO_ACKNOWLEDGE ele vai confirmar o recebimento da mensagem
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		// Precisamos falar no contexto qual a queue
		//Destination topico = (Destination) context.lookup("loja");
		//MessageConsumer consumer = session.createConsumer( topico );
		// Para utilizar topicos duraveis usamos esse metodo que recebe um topic nao um destination
		Topic topico = (Topic) context.lookup("loja");
		MessageConsumer consumer = session.createDurableSubscriber( topico, "assinatura" );
		
		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				
				ObjectMessage objectMessage = (ObjectMessage) message;
				
				try {
					Pedido pedido = (Pedido) objectMessage.getObject();
					System.out.println(pedido.getCodigo());
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		
		new Scanner(System.in).nextLine();
		
		session.close();
		connection.close();
		context.close();
	}

}
