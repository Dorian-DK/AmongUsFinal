/**
 * import java.io.IOException;
 * import java.io.ObjectInputStream;
 * import java.io.ObjectOutputStream;
 * import java.net.ServerSocket;
 * import java.net.Socket;
 * import java.util.ArrayList;
 * import java.util.Random;
 * 
 * public class Server {
 * /**
 * 
 * @ASSESSME.INTENSITY:LOW
 * @author Dorian Drazic-Karalic
 * 
 *         private ArrayList<ClientHandler> clients = new
 *         ArrayList<>();
 *         private int numOfClients = 0;
 * 
 *         class ClientHandler implements Runnable {
 *         private Socket socket;
 *         private int clientId;
 *         private ObjectInputStream ois;
 *         private ObjectOutputStream oos;
 * 
 *         public ClientHandler(Socket socket, int ID) {
 *         this.socket = socket;
 *         clientId = ID;
 *         }
 * 
 * @Override
 *           public void run() {
 *           try {
 *           oos = new ObjectOutputStream(socket.getOutputStream());
 *           ois = new ObjectInputStream(socket.getInputStream());
 * 
 *           // Generate unique MultiplayerSetup for this client
 *           MultiplayerSetup setup = new MultiplayerSetup();
 *           setup.ID = clientId;
 *           // randomly assing color and impostor to clients
 *           // Randomly assign color to client
 *           ArrayList<Integer> availableColors = new ArrayList<>();
 *           availableColors.add(1);
 *           availableColors.add(2);
 *           availableColors.add(3);
 *           availableColors.add(4);
 * 
 *           int colorIndex = new Random().nextInt(availableColors.size());
 *           setup.color = availableColors.get(colorIndex);
 * 
 *           for (ClientHandler clientHandler : clients) {
 *           availableColors.remove(Integer.valueOf(setup.color));
 *           }
 * 
 *           // Assign impostor status to client
 *           if (clients.size() > 0) {
 *           setup.Impostor = (clientId == new
 *           Random().nextInt(clients.size()));
 *           } else {
 *           setup.Impostor = false;
 *           }
 * 
 *           oos.writeObject(setup);
 *           oos.flush();
 * 
 *           clients.add(this);
 * 
 *           while (!setup.continueGame) {
 *           setup = (MultiplayerSetup) ois.readObject();
 * 
 *           setup.continueGame = (clients.size() == 4 && setup.name != null);
 *           oos.writeObject(setup);
 *           oos.flush();
 *           }
 *           while (setup.continueGame) {
 *           MultiplayerAttributes attributes = (MultiplayerAttributes)
 *           ois.readObject();
 * 
 *           for (ClientHandler clientHandler : clients) {
 *           clientHandler.oos.writeObject(attributes);
 *           clientHandler.oos.flush();
 *           }
 *           }
 * 
 *           } catch (IOException e) {
 *           // TODO Auto-generated catch block
 *           e.printStackTrace();
 *           } catch (ClassNotFoundException e) {
 *           // TODO Auto-generated catch block
 *           e.printStackTrace();
 *           } finally {
 *           clients.remove(this);
 *           System.out.println("Client " + clientId + " disconnected");
 *           }
 * 
 *           }
 * 
 *           }
 * 
 *           public static void main(String[] args) {
 *           try {
 *           ServerSocket sSocket = new ServerSocket(8888);
 *           int clientId = 0;
 *           System.out.println("Connected");
 *           while (true) {
 *           Socket socket = sSocket.accept();
 *           System.out.println("Client connected ");
 * 
 *           Server server = new Server();
 * 
 *           ClientHandler clientHandler = server.new ClientHandler(socket,
 *           clientId);
 *           new Thread(clientHandler).start();
 *           clientId++;
 *           }
 *           } catch (IOException e) {
 *           // TODO Auto-generated catch block
 *           e.printStackTrace();
 *           }
 * 
 *           }
 * 
 *           }
 */