/**
 * @author Dorian Drazic-Karalic
 *         import java.io.Serializable;
 *         import java.util.List;
 * 
 *         public class MultiplayerAttributes extends GamePanel implements
 *         Serializable
 *         {
 *         public boolean sabotaged;
 * 
 *         public int gameState;
 *         public List<User> players;
 * 
 *         public class User extends MultiplayerAttributes implements
 *         Serializable {
 *         public int ID;
 *         public String name;
 *         public int speed;
 *         public int totalTasks = 12;
 *         public int playersAlive;
 *         public boolean impostor;
 *         public int worldXOnline;
 *         public int worldYOnline;
 *         public boolean isDead;
 * 
 *         public User(int id, boolean impostor) {
 *         this.ID = id;
 *         this.impostor = impostor;
 *         DefaultValues();
 *         players.add(id, this);
 *         }
 * 
 *         public void DefaultValues() {
 *         name = player.name;
 *         speed = player.speed;
 *         playersAlive = 4;
 *         totalTasks = 12;
 *         isDead = false;
 * 
 *         }
 * 
 *         }
 *         }
 */