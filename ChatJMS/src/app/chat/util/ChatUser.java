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

import java.util.UUID;

/* Classe para a estrutura de usuário, nome e user id único para conversas privadas */
public class ChatUser {

    private final String uuid;
    private final String name;

    public ChatUser(String name) {
        this.name = name;
        this.uuid = UUID.randomUUID().toString().substring(0, 6);
    }

    public String getUUID() {
        return this.uuid;
    }

    public String getName() {
        return this.name;
    }
}