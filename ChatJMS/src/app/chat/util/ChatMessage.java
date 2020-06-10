/*
 ===========================================================================================================================================================================
 Name        : ChatJMS
 Author      : Eduardo Augusto Lima Pereira
 Version     : 1.0
 Copyright   : Your copyright notice
 Description : Trabalho Autoinstrucional para a disciplina de Desenvolvimento de Sistemas Distribuídos do Curso de Ciência da Computaçăo (Noturno) da Universidade FUMEC
 ===========================================================================================================================================================================
 */

package app.chat.util;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.swing.JOptionPane;

/* Classe para criar e formatar as mensagens, sejam de boas-vindas, públicas, privadas, etc. */
public class ChatMessage {

	public static final String TYPE_WELCOME = "Welcome";
	public static final String TYPE_GLOBAL = "Global";
	public static final String TYPE_PRIVATE = "Private";

	/* Criar mensagem de boas vinda padrão (TYPE_WELCOME) */
	public static TextMessage makeWelcomeMessage(Session session, ChatUser chatUser) {
		TextMessage message = null;

		try {
			message = session.createTextMessage();
			message.setStringProperty("Type", TYPE_WELCOME);
			message.setStringProperty("RemetenteName", chatUser.getName());
			message.setStringProperty("RemetenteUID", chatUser.getUUID());
		} catch (JMSException jmsException) {
			//System.err.println("Erro ao criar mensagem: " + jmsException.getMessage());
			JOptionPane.showMessageDialog(null, "Erro ao criar mensagem.\nErro: " + jmsException.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(null, "Erro ao manipular a mensagem.\nErro: " + exception.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}

		return message;
	}

	/* Criar uma mensagem que todos receberão (TYPE_GLOBAL) */
	public static TextMessage makeMessage(Session session, ChatUser chatUser, String text) {
		TextMessage message = null;

		try {
			message = session.createTextMessage();
			message.setText(text);
			message.setStringProperty("Type", TYPE_GLOBAL);
			message.setStringProperty("RemetenteName", chatUser.getName());
			message.setStringProperty("RemetenteUID", chatUser.getUUID());
		} catch (JMSException jmsException) {
			//System.err.println("Erro ao criar mensagem: " + jmsException.getMessage());
			JOptionPane.showMessageDialog(null, "Erro ao criar mensagem.\nErro: " + jmsException.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(null, "Erro ao manipular a mensagem.\nErro: " + exception.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}

		return message;
	}

	/* Criar uma mensagem específica para um destinatário (TYPE_PRIVATE)*/
	public static TextMessage makeMessage(Session session, ChatUser chatUser, String text, String destinatario) {
		TextMessage message = null;

		try {
			message = session.createTextMessage();
			message.setText(text);
			message.setStringProperty("Type", TYPE_PRIVATE);
			message.setStringProperty("RemetenteName", chatUser.getName());
			message.setStringProperty("RemetenteUID", chatUser.getUUID());
			message.setStringProperty("Destinatario", destinatario);
		} catch (JMSException jmsException) {
			//System.err.println("Erro ao criar mensagem: " + jmsException.getMessage());
			JOptionPane.showMessageDialog(null, "Erro ao criar mensagem.\nErro: " + jmsException.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(null, "Erro ao manipular a mensagem.\nErro: " + exception.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}

		return message;
	}

	/* Formatar mensagem global para exibição */
	public static String formatMessage(TextMessage message) {
		try {
			return message.getStringProperty("RemetenteName") + " [" + message.getStringProperty("RemetenteUID") + "]: " + message.getText() + "\n";
		} catch (JMSException jmsException) {
			//System.err.println("Erro ao ler mensagem: " + jmsException.getMessage());
			JOptionPane.showMessageDialog(null, "Erro ao ler mensagem.\nErro: " + jmsException.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(null, "Erro ao manipular a mensagem.\nErro: " + exception.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}

		return null;
	}

	/* Formatar mensagem privada para exibição */
	public static String formatPrivateMessage(TextMessage message) {
		try {
			return message.getStringProperty("RemetenteName") + " [" + message.getStringProperty("RemetenteUID") + "] privadamente: " + message.getText() + "\n";
		} catch (JMSException jmsException) {
			//System.err.println("Erro ao ler mensagem: " + jmsException.getMessage());
			JOptionPane.showMessageDialog(null, "Erro ao ler mensagem.\nErro: " + jmsException.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(null, "Erro ao manipular a mensagem.\nErro: " + exception.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}

		return null;
	}

	/* Formatar mensagem de boas vindas para exibição */
	public static String formatWelcomeMessage(TextMessage message) {
		try {
			return message.getStringProperty("RemetenteName") + " [" + message.getStringProperty("RemetenteUID") + "] acabou de entrar na sala.\n";
		} catch (JMSException jmsException) {
			//System.err.println("Erro ao ler mensagem: " + jmsException.getMessage());
			JOptionPane.showMessageDialog(null, "Erro ao ler mensagem.\nErro: " + jmsException.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(null, "Erro ao manipular a mensagem.\nErro: " + exception.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}

		return null;
	}
}