/*
 ===========================================================================================================================================================================
 Name        : ChatJMS
 Author      : Eduardo Augusto Lima Pereira
 Version     : 1.0
 Copyright   : Your copyright notice
 Description : Trabalho Autoinstrucional para a disciplina de Desenvolvimento de Sistemas Distribuídos do Curso de Ciência da Computaçăo (Noturno) da Universidade FUMEC
 ===========================================================================================================================================================================
 */

package app.chat.view;

import javax.swing.JOptionPane;

import app.chat.server.ChatConsumer;
import app.chat.server.ChatPublisher;
import app.chat.util.ChatUser;
import java.awt.Font;
import java.awt.Dimension;
import javax.swing.SwingConstants;

/* Classe (Frame) da sala de chat, se executada diretamente é logado como um usuário anônimo */
public class JFrameChat extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;

	private final ChatPublisher publisher;

	private javax.swing.JButton btnEnviar;
	private javax.swing.JLabel jLabelTitulo;
	private javax.swing.JPanel jPanelTitulo;
	private javax.swing.JPanel jPanelConteudo;
	private javax.swing.JScrollPane jScrollPaneConteudo;
	private javax.swing.JLabel lblUsername;
	private javax.swing.JTextField txtMensagem;
	private javax.swing.JTextArea txtMensagensPanel;

	public JFrameChat(ChatUser user) {
		getContentPane().setPreferredSize(new Dimension(650, 400));
		getContentPane().setFont(new Font("Arial", Font.PLAIN, 12));
		setResizable(false);
		initComponents();
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		lblUsername.setText("Logado como: @" + user.getName().toUpperCase());
		new ChatConsumer(this, user);

		publisher = new ChatPublisher(user);
		publisher.welcome();
	}

	public JFrameChat() {
		getContentPane().setPreferredSize(new Dimension(650, 400));
		getContentPane().setFont(new Font("Arial", Font.PLAIN, 12));
		setResizable(false);
		initComponents();
		this.setLocationRelativeTo(null);
		this.setVisible(true);

		ChatUser user = new ChatUser("Anônimo");
		new ChatConsumer(this, user);

		publisher = new ChatPublisher(user);
		publisher.welcome();
	}

	private void initComponents() {
		jPanelTitulo = new javax.swing.JPanel();
		jPanelTitulo.setBounds(0, 0, 652, 133);
		lblUsername = new javax.swing.JLabel();
		lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsername.setBounds(20, 65, 622, 29);
		jLabelTitulo = new javax.swing.JLabel();
		jLabelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelTitulo.setBounds(10, 11, 632, 43);
		jPanelConteudo = new javax.swing.JPanel();
		jPanelConteudo.setBounds(0, 133, 652, 267);
		jScrollPaneConteudo = new javax.swing.JScrollPane();
		jScrollPaneConteudo.setBounds(32, 11, 587, 202);
		txtMensagensPanel = new javax.swing.JTextArea();
		txtMensagem = new javax.swing.JTextField();
		txtMensagem.setBounds(32, 224, 515, 21);
		btnEnviar = new javax.swing.JButton();
		btnEnviar.setBounds(557, 224, 63, 23);

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jPanelTitulo.setBackground(new java.awt.Color(102, 102, 255));

		lblUsername.setFont(new java.awt.Font("Arial", 1, 24));
		lblUsername.setForeground(new java.awt.Color(255, 255, 255));
		lblUsername.setText("Logado como: @ANÔNIMO");

		jLabelTitulo.setFont(new java.awt.Font("Arial", 1, 36));
		jLabelTitulo.setForeground(new java.awt.Color(255, 255, 255));
		jLabelTitulo.setText("Sistema de Mensagens");

		jPanelConteudo.setBackground(new java.awt.Color(255, 255, 248));

		txtMensagensPanel.setEditable(false);
		txtMensagensPanel.setColumns(20);
		txtMensagensPanel.setFont(new java.awt.Font("Arial", 0, 12));
		txtMensagensPanel.setRows(5);
		txtMensagensPanel.setText("Instruções de utilização do chat\n- Digite no campo abaixo as mensagens que deseja enviar para todos receberem.\n- As mensagens privadas podem ser enviadas no formato \"@idusuario mensagem\".\n  Onde \"idusuario\" você deve substituir pelo id de 6 digitos do destinatário.\n\n");
		txtMensagensPanel.setToolTipText("");
		txtMensagensPanel.setFocusable(false);
		jScrollPaneConteudo.setViewportView(txtMensagensPanel);

		txtMensagem.setFont(new java.awt.Font("Arial", 0, 13));
		txtMensagem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				txtMensagemActionPerformed(evt);
			}
		});

		btnEnviar.setText("Enviar");
		btnEnviar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnEnviarActionPerformed(evt);
			}
		});
		getContentPane().setLayout(null);
		getContentPane().add(jPanelTitulo);
		jPanelTitulo.setLayout(null);
		jPanelTitulo.add(lblUsername);
		jPanelTitulo.add(jLabelTitulo);
		getContentPane().add(jPanelConteudo);
		jPanelConteudo.setLayout(null);
		jPanelConteudo.add(jScrollPaneConteudo);
		jPanelConteudo.add(txtMensagem);
		jPanelConteudo.add(btnEnviar);

		pack();
	}

	// Botão para envio da mensagem
	private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {
		String message = txtMensagem.getText();

		try {
			/* 	
				Verificação se a mensagem vai ser para um destinatário específico (privada)
				ou vai ser um para todos (global) por meio do caracter @ no início da String
			*/
			if (message.charAt(0) == '@') {
				String uuid = message.substring(1, message.indexOf(' '));
				message = message.substring(message.indexOf(' ') + 1);

				publisher.send(message, uuid);
			} else {
				publisher.send(message);
			}
		} catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
			JOptionPane.showMessageDialog(this, "Mensagem privada deve ser no modelo \"@id mensagem\"");
		}

		txtMensagem.setText("");
	}

	private void txtMensagemActionPerformed(java.awt.event.ActionEvent evt) {
		btnEnviar.doClick();
	}

	public static void main(String args[]) {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Windows".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException classNotFoundException) {
			//java.util.logging.Logger.getLogger(JFrameLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, classNotFoundException);
			JOptionPane.showMessageDialog(null, "Erro dependência ao iniciar a aplicação.\nErro: " + classNotFoundException.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		} catch (InstantiationException instantiationException) {
			//java.util.logging.Logger.getLogger(JFrameLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, instantiationException);
			JOptionPane.showMessageDialog(null, "Erro instânciação ao iniciar a aplicação.\nErro: " + instantiationException.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		} catch (IllegalAccessException illegalAccessException) {
			//java.util.logging.Logger.getLogger(JFrameLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, illegalAccessException);
			JOptionPane.showMessageDialog(null, "Erro de acesso ao iniciar a aplicação.\nErro: " + illegalAccessException.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		} catch (javax.swing.UnsupportedLookAndFeelException unsupportedLookAndFeelException) {
			//java.util.logging.Logger.getLogger(JFrameLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, unsupportedLookAndFeelException);
			JOptionPane.showMessageDialog(null, "Erro de tema ao iniciar a aplicação.\nErro: " + unsupportedLookAndFeelException.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(null, "Erro ao iniciar a aplicação.\nErro: " + exception.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new JFrameChat().setVisible(true);
			}
		});
	}

	public void writeMessage(String message) {
		this.txtMensagensPanel.append(message);
	}
}