package br.com.alura.jms;

import java.util.Scanner;

import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.InitialContext;

import org.apache.activemq.Message;

public class QueueReceiverTest {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		InitialContext ctx = new InitialContext();
        QueueConnectionFactory cf = (QueueConnectionFactory)ctx.lookup("ConnectionFactory");
        QueueConnection conexao = cf.createQueueConnection();
        conexao.start();

        QueueSession sessao = conexao.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue fila = (Queue) ctx.lookup("financeiro");
        QueueReceiver receiver = (QueueReceiver) sessao.createReceiver(fila );

        Message message = (Message) receiver.receive();
        System.out.println(message);

        new Scanner(System.in).nextLine();

        sessao.close();
        conexao.close();    
        ctx.close();
	}

}
