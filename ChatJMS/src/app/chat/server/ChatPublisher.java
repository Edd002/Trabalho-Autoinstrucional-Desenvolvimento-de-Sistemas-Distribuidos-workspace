/*
 ===========================================================================================================================================================================
 Name        : ChatJMS
 Author      : Eduardo Augusto Lima Pereira
 Version     : 1.0
 Copyright   : Your copyright notice
 Description : Trabalho Autoinstrucional para a disciplina de Desenvolvimento de Sistemas Distribuídos do Curso de Ciência da Computaçăo (Noturno) da Universidade FUMEC
 ===========================================================================================================================================================================
 */

package app.chat.server;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.swing.JOptionPane;

import org.apache.activemq.ActiveMQConnectionFactory;

import app.chat.util.ChatMessage;
import app.chat.util.ChatUser;

/* Classe responsável pela emissão das mensagens utilizando o servidor de mensagens ActiveMQ */
public class ChatPublisher {

	private TopicConnectionFactory factory;
	private Topic topic;
	private TopicConnection connection;
	private TopicSession session;
	private TopicPublisher publisher;
	private ChatUser user;

	private static final String PROTOCOL = "tcp"; // protocolo
	private static final String IP = "127.0.0.1"; // servidor localhost
	private static final int PORT = 61616; // porta

	/* Construtor para definir o usuário emissário e fazer a conexão com o servidor de mensagem */
	public ChatPublisher(ChatUser charUser) {
		this.user = charUser;

		try {
			// Fábrica
			factory = new ActiveMQConnectionFactory(PROTOCOL + "://" + IP + ":"  + PORT);

			// Conexão
			connection = factory.createTopicConnection();

			// Sessão
			session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

			// Tópico
			topic = session.createTopic("MSGS.PRINCIPAL");

			// Emissor
			publisher = session.createPublisher(topic);
		} catch (JMSException jmsException) {
			//System.err.println("Exception Publisher - " + jmsException.getMessage());
			JOptionPane.showMessageDialog(null, "Erro de conexão (emissor).\nErro: " + jmsException.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(null, "Erro de conexão.\nErro: " + exception.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}

	/* Método para o envio de uma mensagem para um destinatário específico */
	public void send(String text, String destinatarioUUID) {
		try {
			TextMessage message = ChatMessage.makeMessage(session, user, text, destinatarioUUID);
			publisher.publish(message);
		} catch (JMSException jmsException) {
			//System.err.println("Exception send - " + jmsException.getMessage());
			JOptionPane.showMessageDialog(null, "Erro de envio.\nErro: " + jmsException.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(null, "Erro ao manipular a mensagem.\nErro: " + exception.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}

	/* Método para o envio de uma mensagem para todos */
	public void send(String text) {
		try {
			TextMessage message = ChatMessage.makeMessage(session, user, text);
			publisher.publish(message);
		} catch (JMSException jmsException) {
			//System.err.println("Exception send - " + jmsException.getMessage());
			JOptionPane.showMessageDialog(null, "Erro de envio.\nErro: " + jmsException.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(null, "Erro ao manipular a mensagem.\nErro: " + exception.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}

	/* Método para o envio de mensagem de boas vindas */
	public void welcome() {
		try {
			TextMessage message = ChatMessage.makeWelcomeMessage(session, user);
			publisher.publish(message);
		} catch (JMSException jmsException) {
			//System.err.println("Exception send - " + jmsException.getMessage());
			JOptionPane.showMessageDialog(null, "Erro de envio.\nErro: " + jmsException.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(null, "Erro ao manipular a mensagem.\nErro: " + exception.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}
}