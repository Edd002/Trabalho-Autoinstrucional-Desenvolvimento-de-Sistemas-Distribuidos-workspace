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
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.swing.JOptionPane;

import org.apache.activemq.ActiveMQConnectionFactory;

import app.chat.util.ChatMessage;
import app.chat.util.ChatUser;
import app.chat.view.JFrameChat;

/* Classe responsável pela recpção das mensagens utilizando o servidor de mensagens ActiveMQ */
public class ChatConsumer {

	private TopicConnectionFactory factory;
	private Topic topic;
	private TopicConnection connection;
	private TopicSession session;

	private static final String PROTOCOL = "tcp"; // protocolo
	private static final String IP = "127.0.0.1"; // servidor localhost
	private static final int PORT = 61616; // porta

	/* Construtor para fazer a conexão com o servidor de mensagem e receber a mensagem assim que chegar (esperando por meio de um listener) */
	public ChatConsumer(JFrameChat jFrameChat, ChatUser charUser) {
		try {
			// Fábrica
			factory = new ActiveMQConnectionFactory(PROTOCOL + "://" + IP + ":"  + PORT);

			// Conexão
			connection = factory.createTopicConnection();

			// Sessão
			session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

			// Tópico
			topic = session.createTopic("MSGS.PRINCIPAL");

			// Receptor
			TopicSubscriber subscriber = session.createSubscriber(topic);

			// Seta um MessageListener para recebimento de mensagens
			// onMessage() sendo implementado
			subscriber.setMessageListener(new MessageListener() {
				@Override
				public void onMessage(Message received) {
					try {
						TextMessage message = (TextMessage) received;
						String type = message.getStringProperty("Type");

						if (type.equals(ChatMessage.TYPE_WELCOME))
							jFrameChat.writeMessage(ChatMessage.formatWelcomeMessage(message));
						else if (type.equals(ChatMessage.TYPE_GLOBAL))
							jFrameChat.writeMessage(ChatMessage.formatMessage(message));
						else if (type.equals(ChatMessage.TYPE_PRIVATE) && 
								(message.getStringProperty("Destinatario").equals(charUser.getUUID()) || message.getStringProperty("RemetenteUID").equals(charUser.getUUID())))
							jFrameChat.writeMessage(ChatMessage.formatPrivateMessage(message));
					} catch (Exception exception) {
						//System.out.println("Erro onMessage: " + exception.getMessage());
						JOptionPane.showMessageDialog(null, "Erro de mensagem.\nErro: " + exception.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
						System.exit(1);
					}
				}
			});

			// Inicia uma conexão
			connection.start();
		} catch (JMSException jmsException) {
			//System.err.println("Erro Consumer: " + jmsException.getMessage());
			JOptionPane.showMessageDialog(null, "Erro de conexão (receptor).\nErro: " + jmsException.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(null, "Erro de conexão.\nErro: " + exception.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}
}
