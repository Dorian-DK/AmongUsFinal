import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

/**
 * @ASSESSME.INTENSITY:LOW
 * @author Dorian Drazic-Karalic
 *         The UI class handles the graphical user interface of
 *         the game.
 *         It draws different screens depending on the game
 *         state and also handles the
 *         task images.
 */
public class UI {

    /*
     * mp- tag that would be used if the server worked
     */
    GamePanel gp;
    Graphics2D g;
    Font fontUi = new Font("Arial", Font.PLAIN, 40);
    Font alert = new Font("Arial", Font.PLAIN, 20);
    Font winFont = new Font("Arial", Font.BOLD, 60);
    Font chatFont = new Font("Arial", Font.PLAIN, 16);
    // MultiplayerSetup mSetup = new MultiplayerSetup(); mp
    public File xmlFile = new File("config.xml");
    public boolean dispMessage = false;
    public String message = "";
    public boolean gameOver = false;
    public boolean crewmateWin = false;
    public boolean impostorWin = false;
    public String[] chatMessages = new String[5];
    public Boolean votingOpen = true;
    public Boolean reported = false;
    public int commandNum = 0;
    public int titleScreenState = 0;
    public int taskNum = 0;
    public int task3Click = 0;
    public boolean download = false;
    public int counterTask3 = 0;

    /**
     * Constructor for the UI class. Takes a GamePanel object as a parameter.
     * 
     * @param gp the GamePanel object to associate with the UI
     */
    public UI(GamePanel gp) {
        this.gp = gp;

    }

    /**
     * Sets the message to be displayed on the UI and also sets the display message
     * flag to true.
     * 
     * @param text        the message to be displayed
     * @param dispMessage true if the message should be displayed, false otherwise
     * @param impostor    true if the player is an impostor, false otherwise
     */
    public void setMessage(String text, boolean dispMessage, boolean impostor) {
        if (impostor) {
            message = text;
            this.dispMessage = dispMessage;
            int x = gp.SCREEN_WIDTH / 5;
            int y = gp.SCREEN_HEIGHT / 2;
        }

    }

    /**
     * Draws the UI.
     * 
     * @param g the Graphics2D object used to draw the UI
     */
    public void draw(Graphics2D g) {

        if (gameOver) {

            g.setFont(fontUi);
            g.setColor(Color.RED);
            g.setBackground(Color.BLACK);
            int x = gp.SCREEN_WIDTH / 5;
            int y = gp.SCREEN_HEIGHT / 2;

            // if(crewmateWin) would be used instead of this if i had the server as it would
            // be set when all the crewmates finished the tasks
            if (crewmateWin) {
                g.setFont(winFont);
                g.drawString("CREWMATES WIN!", x, y);

                gp.gamThread = null;

            } else if (impostorWin) {
                g.setFont(winFont);
                g.drawString("IMPOSTOR WINS", x, y);
                gp.gamThread = null;
            }
        }

        if (gp.gameState == gp.TITLE_STATE) {
            drawTitleScreen(g);
        }

        if (gp.gameState == gp.PLAY_STATE) {
            if (taskNum == 0) {
                g.setFont(fontUi);
                g.setColor(Color.MAGENTA);
                g.drawString("Tasks left " + gp.player.tasks, 20, 50);
                gp.player.speed = 2;
            }

            if (taskNum == 1 && !gp.player.didTask1) {
                doTask1(g);
            } else if (taskNum == 2 && !gp.player.didTask2) {
                doTask2(g);
            } else if (taskNum == 3 && !gp.player.didTask3) {
                doTask3(g);
            }
        }
        if (gp.gameState == gp.PAUSE_STATE) {
            if (gp.player.bodyFound) {
                drawChat(g);

                drawVoting();
            }

        }

        if (dispMessage) {
            g.drawString(message, 450, 550);
        }

    }

    private void doTask3(Graphics2D g2) {
        if (taskImages == null) {
            gp.player.speed = 0;
            // Load the task images

            try {
                BufferedImage taskImage1 = ImageIO.read(new File("task2.png"));
                if (!download) {

                    g2.drawImage(taskImage1, 0, 0, null);
                    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 60F));
                    String text = "DOWNLOAD VIRUS";
                    int x = 100;
                    int y = gp.SCREEN_HEIGHT / 3;
                    y -= 50;
                    // Shadow
                    g2.setColor(Color.DARK_GRAY);
                    g2.drawString(text, x + 5, y + 5);
                    // Main color
                    g2.setColor(Color.WHITE);
                    g2.drawString(text, x, y);

                    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
                    text = "DOWNLOAD";
                    y += gp.TILESIZE * 2;
                    g2.drawString(text, x + 150, y);
                    if (commandNum == 0) {
                        g2.drawString(">", x - gp.TILESIZE + 150, y);

                    }
                } else {
                    g2.drawImage(taskImage1, 0, 0, null);
                    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 90F));
                    String text = "DOWNLOADING";
                    int x = 30;
                    int y = gp.SCREEN_HEIGHT / 2;
                    // Shadow
                    g2.setColor(Color.DARK_GRAY);
                    g2.drawString(text, x + 5, y + 5);
                    g2.setColor(Color.WHITE);
                    g2.drawString(text, x, y);

                    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
                    text = "PLEASE WAIT";
                    y += gp.TILESIZE * 2;
                    g2.drawString(text, x + 200, y);
                }

                if (download) {
                    // Start the image timer if it hasn't been started yet
                    if (imageTimer == null) {

                        imageTimer = new Timer(3000, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {

                                counterTask3++;
                                gp.repaint();
                            }
                        });

                        imageTimer.start();
                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (counterTask3 >= 3) {
            gp.player.didTask3 = true;
            gp.player.speed = 2;
            taskNum = 0;
            imageTimer = null;

        }
    }

    private void doTask2(Graphics2D g2) {
        try {
            gp.player.speed = 0;
            BufferedImage image = ImageIO.read(new File("task1.png"));
            g2.drawImage(image, 0, 0, null);
            gp.player.speed = 0;
            String text = "";
            g2.setColor(Color.BLACK);
            int x = 250;
            int y = 250;
            g2.drawString(text, x, y);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            if (gp.player.task2 != null) {

                text = gp.player.task2;
                text = "";
                text = gp.player.task2;
                if (gp.player.task2.equals("monday")) {
                    taskNum = 0;
                    gp.player.didTask2 = true;
                    gp.player.speed = 2;
                }

            } else {
                text = "";
            }

            y += gp.TILESIZE * 2;
            g2.drawString(text, x, y);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private BufferedImage[] taskImages;
    private int currentImageIndex;
    private Timer imageTimer;

    private void doTask1(Graphics2D g2) {
        if (taskImages == null) {
            gp.player.speed = 0;
            try {
                // Load the task images
                taskImages = new BufferedImage[4];
                taskImages[0] = ImageIO.read(new File("task3_full.png"));
                taskImages[1] = ImageIO.read(new File("task3_half.png"));
                taskImages[2] = ImageIO.read(new File("task3_eaten.png"));
                taskImages[3] = ImageIO.read(new File("empty.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Start the image timer if it hasn't been started yet
        if (imageTimer == null) {
            currentImageIndex = 0;
            imageTimer = new Timer(3000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Increment the image index and wrap around if necessary
                    currentImageIndex = (currentImageIndex + 1) % taskImages.length;
                    gp.repaint();
                }
            });
            imageTimer.start();
        }

        // Draw the current image
        BufferedImage currentImage = taskImages[currentImageIndex];
        g2.drawImage(currentImage, 0, 0, null);
        if (currentImageIndex == 3) {
            gp.player.didTask1 = true;
            gp.player.speed = 2;
            taskNum = 0;
            imageTimer = null;
        }
    }

    /**
     * Draws the title screen with different options based on the title screen
     * state.
     *
     * @param g Graphics2D object used to draw the title screen
     */
    private void drawTitleScreen(Graphics2D g) {
        // Title name
        if (titleScreenState == 2) {
            g.setColor(new Color(70, 120, 80));
            g.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);
            g.setFont(g.getFont().deriveFont(Font.BOLD, 96F));
            String text = "Party poppers";
            int x = 75;
            int y = gp.SCREEN_HEIGHT / 3;
            y -= 50;
            // Shadow
            g.setColor(Color.DARK_GRAY);
            g.drawString(text, x + 5, y + 5);
            // Main color
            g.setColor(Color.WHITE);
            g.drawString(text, x, y);

            g.setFont(g.getFont().deriveFont(Font.BOLD, 48F));
            text = "START GAME";
            y += gp.TILESIZE * 2;
            g.drawString(text, x, y);
            if (commandNum == 0) {
                g.drawString(">", x - gp.TILESIZE, y);

            }

            g.setFont(g.getFont().deriveFont(Font.BOLD, 48F));
            text = "INSTRUCTIONS";
            y += gp.TILESIZE * 2;
            g.drawString(text, x, y);
            if (commandNum == 1) {
                g.drawString(">", x - gp.TILESIZE, y);

            }
            g.setFont(g.getFont().deriveFont(Font.BOLD, 48F));
            text = "SETTINGS";
            y += gp.TILESIZE * 2;
            g.drawString(text, x, y);
            if (commandNum == 2) {
                g.drawString(">", x - gp.TILESIZE, y);

            }

            g.setFont(g.getFont().deriveFont(Font.BOLD, 48F));
            text = "QUIT";
            y += gp.TILESIZE * 2;
            g.drawString(text, x, y);
            if (commandNum == 3) {
                g.drawString(">", x - gp.TILESIZE, y);

            }
        } else if (titleScreenState == 1) {
            g.setColor(new Color(70, 120, 80));
            g.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);
            g.setFont(g.getFont().deriveFont(Font.BOLD, 96F));
            String text = "Instructions";
            int x = 75;
            int y = gp.SCREEN_HEIGHT / 3;
            y -= 50;
            // Shadow
            g.setColor(Color.DARK_GRAY);
            g.drawString(text, x + 5, y + 5);
            // Main color
            g.setColor(Color.WHITE);
            g.drawString(text, x, y);
            g.setFont(g.getFont().deriveFont(Font.BOLD, 20F));
            text = "Press escape to report a dead body";
            y += gp.TILESIZE * 2;
            g.drawString(text, x, y);

            g.setFont(g.getFont().deriveFont(Font.BOLD, 20F));
            text = "If impostor press space to sabotage";
            y += gp.TILESIZE * 2;
            g.drawString(text, x, y);

            g.setFont(g.getFont().deriveFont(Font.BOLD, 20F));
            text = "If impostor press k to kill";
            y += gp.TILESIZE * 2;
            g.drawString(text, x, y);
            g.setFont(g.getFont().deriveFont(Font.BOLD, 48F));
            text = "RETURN";
            y += gp.TILESIZE * 2;
            g.drawString(text, x, y);
            g.drawString(">", x - gp.TILESIZE, y);
        } else if (titleScreenState == 0) {
            if (!xmlFile.exists()) {
                g.setColor(new Color(70, 120, 80));
                g.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);
                g.setFont(g.getFont().deriveFont(Font.BOLD, 96F));
                String text = "Name:";
                int x = 75;
                int y = gp.SCREEN_HEIGHT / 3;
                y -= 50;
                // Shadow
                g.setColor(Color.DARK_GRAY);
                g.drawString(text, x + 5, y + 5);
                // Main color
                g.setColor(Color.WHITE);
                g.drawString(text, x, y);
                g.setFont(g.getFont().deriveFont(Font.BOLD, 20F));
                if (gp.player.name != null) {

                    text = gp.player.name;
                    text = "";
                    text = gp.player.name;

                } else {
                    text = "";
                }

                y += gp.TILESIZE * 2;
                g.drawString(text, x, y);

                text = "CONTINUE";
                y += gp.TILESIZE * 2;
                g.drawString(text, x, y);
                g.drawString(">", x - gp.TILESIZE, y);
            } else {
                titleScreenState = 2;
            }

        } else if (titleScreenState == 3) {
            g.setColor(new Color(70, 120, 80));
            g.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);
            g.setFont(g.getFont().deriveFont(Font.BOLD, 48F));
            String text = "Settings";
            int x = 75;
            int y = gp.SCREEN_HEIGHT / 3;
            y -= 100;
            // Shadow
            g.setColor(Color.DARK_GRAY);
            g.drawString(text, x + 5, y + 5);
            // Main color
            g.setFont(g.getFont().deriveFont(Font.BOLD, 20F));
            x = 75;
            y += gp.TILESIZE * 2;
            text = "Impostor:";
            g.setColor(Color.WHITE);
            g.drawString(text, x, y);
            g.setFont(g.getFont().deriveFont(Font.BOLD, 20F));

            x += 150;

            text = "Yes";
            g.setColor(Color.WHITE);
            g.drawString(text, x, y);
            if (commandNum == 1) {
                g.drawString(">", x - gp.TILESIZE, y);

            }
            text = "No";
            y += gp.TILESIZE * 2;
            y -= 50;
            g.drawString(text, x, y);
            if (commandNum == 2) {
                g.drawString(">", x - gp.TILESIZE, y);

            }

            text = "RETURN";
            x = 75;
            y += gp.TILESIZE * 2;
            g.drawString(text, x, y);
            if (commandNum == 3) {
                g.drawString(">", x - gp.TILESIZE, y);

            }
        }

    }

    /**
     * 
     * Draws the chat messages on the screen.
     * 
     * @param g the Graphics2D object used for rendering
     */
    public void drawChat(Graphics2D g) {

        g.setFont(chatFont);
        g.setColor(Color.WHITE);
        for (int i = 0; i < chatMessages.length; i++) {
            String message = chatMessages[i];
            if (message != null) {
                g.drawString(message, 20, 100 + (i * 20));
            }
        }
    }

    /**
     * 
     * Draws the voting window on the screen if voting is open and a body has been
     * found.
     */
    public void drawVoting() {

        if (votingOpen && gp.player.bodyFound) {
            votingOpen = false;
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frame.setResizable(false);
            frame.setTitle("Voting");

            // Create a panel to hold the chat and voting elements
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());

            // Create a text area for the chat
            JTextArea chatArea = new JTextArea(20, 50);
            JScrollPane chatScrollPane = new JScrollPane(chatArea);
            chatArea.setEditable(false);

            // Create a panel to hold the vote buttons
            JPanel votePanel = new JPanel();
            votePanel.setLayout(new GridLayout(gp.entities.length, 2));
            JButton[] voteButtons = new JButton[gp.entities.length];
            int[] voteCounts = new int[gp.entities.length];
            for (int i = 0; i < gp.entities.length; i++) {
                Entitiy player = gp.entities[i];
                voteButtons[i] = new JButton(player.name);
                voteButtons[i].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Increment the vote count for the selected player
                        for (int j = 0; j < gp.entities.length; j++) {
                            if (e.getSource() == voteButtons[j]) {
                                voteCounts[j]++;
                                chatArea.append(player.name + " received a vote " + voteCounts[j] + " votes\n");
                                break;
                            }
                        }
                    }
                });
                votePanel.add(voteButtons[i]);
                votePanel.add(new JLabel("Votes: " + voteCounts[i]));
            }

            // Add the chat and voting elements to the panel
            panel.add(chatScrollPane, BorderLayout.NORTH);
            panel.add(votePanel, BorderLayout.CENTER);

            // Add the panel to the frame and show the window
            frame.getContentPane().add(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // Close the window after 30 seconds
            int delay = 30000; // 30 seconds
            ActionListener taskPerformer = new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    frame.dispose();
                    votingOpen = false;
                    gp.gameState = gp.PLAY_STATE;

                }
            };
            new Timer(delay, taskPerformer).start();

        }

    }
}
