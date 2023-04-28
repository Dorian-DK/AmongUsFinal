/**
 * @author Dorian Drazic-Karalic
 *         import java.io.*;
 *         import java.net.*;
 * 
 *         public class Client extends GamePanel {
 *         public static String name;
 *         public static GamePanel gp = new GamePanel();
 *         public MultiplayerAttributes mpAttributes = new
 *         MultiplayerAttributes();
 * 
 *         public static void main(String[] args) {
 *         try {
 *         Socket socket = new Socket("localhost", 8888);
 *         ObjectOutputStream out = new
 *         ObjectOutputStream(socket.getOutputStream());
 *         ObjectInputStream in = new
 *         ObjectInputStream(socket.getInputStream());
 * 
 *         MultiplayerSetup setup = (MultiplayerSetup) in.readObject();
 * 
 *         setup.name = gp.player.name;
 *         // Send the client's name to the server
 *         out.writeObject(setup);
 *         out.flush();
 * 
 *         setup = (MultiplayerSetup) in.readObject();
 * 
 *         } catch (IOException e) {
 *         e.printStackTrace();
 *         } catch (ClassNotFoundException e) {
 *         // TODO Auto-generated catch block
 *         e.printStackTrace();
 *         }
 *         }
 *         }
 */