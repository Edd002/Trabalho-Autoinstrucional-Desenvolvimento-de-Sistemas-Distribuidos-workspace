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

import app.chat.util.ChatUser;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import javax.swing.SwingConstants;

/* Classe (Frame) para realização do login para entrar na sala de chat */
public class JFrameLogin extends javax.swing.JFrame {

	private javax.swing.JButton btnLogin;
	private javax.swing.JLabel jLabelTitulo;
	private javax.swing.JLabel jLabelSubtitulo;
	private javax.swing.JPanel jPanelTitulo;
	private javax.swing.JPanel jPanelConteudo;
	private javax.swing.JLabel lblIdentificacao;
	private javax.swing.JLabel lblUsername;
	private javax.swing.JTextField txtUsername;

	private static final long serialVersionUID = 1L;

	public JFrameLogin() {
		getContentPane().setPreferredSize(new Dimension(350, 310));
		getContentPane().setFont(new Font("Arial", Font.PLAIN, 12));
		initComponents();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	private void initComponents() {
		jPanelTitulo = new javax.swing.JPanel();
		jPanelTitulo.setBounds(0, 0, 350, 133);
		jLabelTitulo = new javax.swing.JLabel();
		jLabelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelTitulo.setBounds(10, 11, 330, 68);
		jLabelSubtitulo = new javax.swing.JLabel();
		jLabelSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelSubtitulo.setBounds(10, 90, 330, 17);
		jPanelConteudo = new javax.swing.JPanel();
		jPanelConteudo.setBounds(0, 133, 350, 180);
		txtUsername = new javax.swing.JTextField();
		txtUsername.setBounds(90, 89, 150, 20);
		btnLogin = new javax.swing.JButton();
		btnLogin.setBounds(90, 120, 150, 23);
		lblUsername = new javax.swing.JLabel();
		lblUsername.setFont(new Font("Arial", Font.PLAIN, 12));
		lblUsername.setBounds(90, 64, 110, 14);
		lblIdentificacao = new javax.swing.JLabel();
		lblIdentificacao.setHorizontalAlignment(SwingConstants.CENTER);
		lblIdentificacao.setBounds(10, 31, 330, 22);

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Login");
		setBackground(Color.WHITE);
		setFont(new java.awt.Font("Arial", 0, 12));
		setLocation(new java.awt.Point(50, 50));
		setResizable(false);

		jPanelTitulo.setBackground(new java.awt.Color(102, 102, 255));

		jLabelTitulo.setFont(new java.awt.Font("Arial", 1, 60));
		jLabelTitulo.setForeground(new java.awt.Color(255, 255, 255));
		jLabelTitulo.setText("Mensagens");

		jLabelSubtitulo.setFont(new java.awt.Font("Arial", 0, 14));
		jLabelSubtitulo.setForeground(new java.awt.Color(255, 255, 255));
		jLabelSubtitulo.setText("Sistema de Mensagens Utilizando JMS");

		jPanelConteudo.setBackground(new java.awt.Color(255, 255, 248));

		txtUsername.setToolTipText("");
		txtUsername.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				txtUsernameActionPerformed(evt);
			}
		});

		btnLogin.setText("Entrar");
		btnLogin.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnLoginActionPerformed(evt);
			}
		});

		lblUsername.setText("Nome de usuário: ");

		lblIdentificacao.setFont(new java.awt.Font("Arial", 1, 18));
		lblIdentificacao.setText("Identifique-se para entrar no chat");
		getContentPane().setLayout(null);
		getContentPane().add(jPanelTitulo);
		jPanelTitulo.setLayout(null);
		jPanelTitulo.add(jLabelTitulo);
		jPanelTitulo.add(jLabelSubtitulo);
		getContentPane().add(jPanelConteudo);
		jPanelConteudo.setLayout(null);
		jPanelConteudo.add(lblIdentificacao);
		jPanelConteudo.add(lblUsername);
		jPanelConteudo.add(txtUsername);
		jPanelConteudo.add(btnLogin);

		pack();
	}

	// Botão para logar no sistema de mensagens
	private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {
		String username = txtUsername.getText();

		// Validação simples para nome de usuário e inicialização do chat
		if (username.trim().length() < 4) {
			JOptionPane.showMessageDialog(this, "Usuário deve conter no mínimo 4 caracteres.");
		} else {
			ChatUser user = new ChatUser(username);

			JFrameChat chat = new JFrameChat(user);
			chat.setVisible(true);
			this.dispose();
		}
	}

	private void txtUsernameActionPerformed(java.awt.event.ActionEvent evt) {
		btnLogin.doClick();
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
				new JFrameLogin().setVisible(true);
			}
		});
	}
}