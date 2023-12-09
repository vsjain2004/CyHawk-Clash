package com.CyHawkClash.Backend.Websocket;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@ServerEndpoint("/chat/{game_id}/{username}")
@Component
public class ChatServer {




    private static Map<String, Integer> usernameGameMap = new Hashtable<>();

    private static Map<Integer, ArrayList<String>> gameUsernameMap = new Hashtable<>();

    private static Map<String, Session> usernameSessionMap = new Hashtable<>();

    private static Map<Session, String> sessionUsernameMap = new Hashtable<>();



    private static final Logger logger = LoggerFactory.getLogger(ChatServer.class);


    @OnOpen
    public void onOpen(Session session,@PathParam("game_id") int game_id, @PathParam("username") String username) throws IOException {
        // server side log
        logger.info("[onOpen]  username: " + username + " Joined game " + game_id);

        /**
         * Handles if a User tries to join the websocket multiple times.
         */
        if (gameUsernameMap.containsKey(game_id)) {

            if(usernameGameMap.containsKey(username)){
                onClose(usernameSessionMap.get(username));
            }

            usernameGameMap.put(username, game_id);

        }
        else {
            //Map session and game id together

            //Map game and usernames together
            usernameGameMap.put(username, game_id);

            //Creates new arrayList for the map
            gameUsernameMap.put(game_id, new ArrayList<String>());
            //Updates the arrayList


            // send to everyone in the chat
        }
        gameUsernameMap.get(game_id).add(username);
        usernameSessionMap.put(username, session);
        sessionUsernameMap.put(session, username);
        broadcastViaGameId("User: " + username + " has Joined the Chat", game_id);
    }


    /**
     * Handles incoming WebSocket messages from a client.
     *
     * @param session The WebSocket session representing the client's connection.
     * @param message The message received from the client.
     */
    @OnMessage
    public void onMessage(Session session, String message) throws IOException {

        // get the username by session
        String username = sessionUsernameMap.get(session);
        int game = usernameGameMap.get(username);

        // server side log
        logger.info("[onMessage] " + username + ": " + message);

        broadcastViaGameId(username + ": " + message, game);
    }


    /**
     * Handles the closure of a WebSocket connection.
     *
     * @param session The WebSocket session that is being closed.
     */
    @OnClose
    public void onClose(Session session) throws IOException {

        // get the username from session-username mapping
        String username = sessionUsernameMap.get(session);
        int game = usernameGameMap.get(username);

        // server side log
        logger.info("[onClose] " + username + "\n\n");
        logger.info(sessionUsernameMap.toString());
        logger.info(usernameGameMap.toString());
        logger.info(usernameSessionMap.toString());
        logger.info(gameUsernameMap.toString());

        // remove user from memory mappings
        sessionUsernameMap.remove(session);
        usernameGameMap.remove(username);
        usernameSessionMap.remove(username);
        gameUsernameMap.get(game).remove(username);

        // send the message to chat
        broadcastViaGameId(username + " disconnected", game);
    }

    /**
     * Handles WebSocket errors that occur during the connection.
     *
     * @param session   The WebSocket session where the error occurred.
     * @param throwable The Throwable representing the error condition.
     */
    @OnError
    public void onError(Session session, Throwable throwable) {

        // get the username from session-username mapping
        String username = sessionUsernameMap.get(session);
        int game = usernameGameMap.get(username);

        // do error handling here
        logger.info("[onError]" + username + " game: " + game +": " + throwable.getMessage());
    }

    /**
     * Broadcasts a message to all users in a game chat.
     *
     * @param message The message to be broadcasted to all users.
     */
    public static void broadcastViaGameId(String message, int id) {

        ArrayList<String> users = gameUsernameMap.get(id);
        for (String user : users) {
            try {
                usernameSessionMap.get(user).getBasicRemote().sendText(message);
            } catch (IOException e) {
                logger.info("[Broadcast Exception] " + e.getMessage());
            }
        }

    }

}


