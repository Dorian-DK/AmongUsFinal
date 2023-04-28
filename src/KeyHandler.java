
/**
 * The KeyHandler class implements KeyListener and handles keyboard input for a game.
 */

/**
 * The KeyHandler class implements KeyListener and handles keyboard input for a game.
 */
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @ASSESSME.INTENSITY:LOW
 * @author Dorian Drazic-Karalic
 *         KeyHandler class that implements KeyListener and
 *         handles keyboard input for
 *         the game.
 */

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, moving, rPressed, spacePressed, kPressed,
            enterPressed, impostor;
    int counter = 0;

    /**
     * Constructor for KeyHandler class that takes in a GamePanel object.
     * 
     * @param gp The GamePanel object used in the game.
     */
    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * This method overrides the default implementation of the keyPressed method
     * in order to handle user input events for the game.
     *
     * @param e the KeyEvent object representing the user input event.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (gp.gameState == gp.TITLE_STATE) {
            if (gp.ui.titleScreenState == 2) {
                if (code == KeyEvent.VK_W) {
                    gp.ui.commandNum--;
                    if (gp.ui.commandNum < 0) {
                        gp.ui.commandNum = 3;
                    }

                }

                if (code == KeyEvent.VK_S) {
                    gp.ui.commandNum++;
                    if (gp.ui.commandNum > 3) {
                        gp.ui.commandNum = 0;
                    }
                }
                if (code == KeyEvent.VK_ENTER) {
                    if (gp.ui.commandNum == 0) {
                        gp.gameState = gp.PLAY_STATE;
                    }
                    if (gp.ui.commandNum == 1) {

                        gp.ui.titleScreenState = 1;
                    }
                    if (gp.ui.commandNum == 2) {
                        gp.ui.titleScreenState = 3;
                    }
                    if (gp.ui.commandNum == 3) {
                        System.exit(0);
                    }
                }

            } else if (gp.ui.titleScreenState == 1) {
                if (code == KeyEvent.VK_ENTER) {
                    gp.ui.titleScreenState = 0;
                }
            } else if (gp.ui.titleScreenState == 0) {
                if (gp.ui.xmlFile.exists()) {
                    gp.player.name = gp.config.getName();
                    gp.ui.titleScreenState = 2;
                }

                if (Character.isLetterOrDigit(e.getKeyChar())) {
                    gp.player.name += e.getKeyChar();
                } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && gp.player.name.length() > 0) {
                    gp.player.name = gp.player.name.substring(0, gp.player.name.length() - 1);
                }
                if (gp.player.name == null) {
                    gp.player.name = "Please enter a name.";
                }
                if (gp.player.name.startsWith("Please enter a name.")) {
                    if (counter > 1) {
                        gp.player.name = "";
                    } else
                        gp.player.name = "Please enter a name.";
                    counter++;

                }
                if (code == KeyEvent.VK_ENTER && !gp.player.name.isEmpty() && !gp.player.name.contains("null")
                        && !gp.player.name.equals("Please enter a name.")) {
                    gp.config.setName(gp.player.name);
                    gp.config.saveConfig();
                    gp.ui.titleScreenState = 2;

                }

                if (gp.player.name.contains("null")) {
                    gp.player.name = "";
                }

            } else if (gp.ui.titleScreenState == 3) {
                if (code == KeyEvent.VK_W) {
                    gp.ui.commandNum--;
                    if (gp.ui.commandNum < 0) {
                        gp.ui.commandNum = 3;
                    }

                }

                if (code == KeyEvent.VK_S) {
                    gp.ui.commandNum++;
                    if (gp.ui.commandNum > 3) {
                        gp.ui.commandNum = 0;
                    }
                }
                if (code == KeyEvent.VK_ENTER) {
                    if (gp.ui.commandNum == 1) {
                        gp.impostor = true;
                        gp.config.setImpostor(true);
                        gp.config.saveConfig();
                        gp.player.impostor = true;
                        gp.ui.titleScreenState = 2;
                    }
                    if (gp.ui.commandNum == 2) {

                        gp.config.setImpostor(false);
                        gp.config.saveConfig();
                        gp.player.impostor = false;
                        gp.impostor = false;

                        gp.ui.titleScreenState = 2;
                    }
                    if (gp.ui.commandNum == 3) {
                        gp.ui.titleScreenState = 2;
                    }

                }

            }
        } else if (gp.gameState == gp.PLAY_STATE) {
            if (!gp.player.didTask2 && gp.ui.taskNum == 2) {
                if (Character.isLetterOrDigit(e.getKeyChar())) {
                    gp.player.task2 += e.getKeyChar();
                } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && gp.player.task2.length() > 0) {
                    gp.player.task2 = gp.player.task2.substring(0, gp.player.task2.length() - 1);
                }
                if (gp.player.task2 == null) {
                    gp.player.task2 = "Please enter the password.";
                }
                if (gp.player.task2.startsWith("Please enter the password")) {
                    gp.player.task2 = "Please enter the password.";
                }
                if (code == KeyEvent.VK_ENTER && !gp.player.task2.isEmpty() && !gp.player.task2.contains("null")
                        && !gp.player.task2.equals("Please enter the password.")) {

                    gp.gameState = gp.PLAY_STATE;

                }

                if (gp.player.task2.contains("null")) {
                    gp.player.task2 = "";
                }

            } else if (!gp.player.didTask3 && gp.ui.taskNum == 3) {
                if (code == KeyEvent.VK_ENTER) {

                    gp.ui.download = true;

                }

            }

            if (code == KeyEvent.VK_W) {
                upPressed = true;
                moving = true;
            }

            if (code == KeyEvent.VK_S) {
                downPressed = true;
                moving = true;
            }

            if (code == KeyEvent.VK_D) {
                rightPressed = true;
                moving = true;
            }

            if (code == KeyEvent.VK_A) {
                leftPressed = true;
                moving = true;
            }
            if (code == KeyEvent.VK_K) {
                kPressed = true;
            }
            if (code == KeyEvent.VK_SPACE) {
                spacePressed = true;
            }
            if (code == KeyEvent.VK_R) {
                rPressed = true;
            }
            if (code == KeyEvent.VK_ESCAPE) {
                if (gp.gameState == gp.PLAY_STATE) {
                    if (gp.player.isKilled) {
                        gp.gameState = gp.PAUSE_STATE;
                    }

                }
            }

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            upPressed = false;
            moving = false;
        }

        if (code == KeyEvent.VK_S) {
            downPressed = false;
            moving = false;
        }

        if (code == KeyEvent.VK_D) {
            rightPressed = false;
            moving = false;
        }

        if (code == KeyEvent.VK_A) {
            leftPressed = false;
            moving = false;
        }
        if (code == KeyEvent.VK_K) {
            kPressed = false;
        }
        if (code == KeyEvent.VK_SPACE) {
            spacePressed = false;
        }
        if (code == KeyEvent.VK_R) {
            rPressed = false;
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

}
