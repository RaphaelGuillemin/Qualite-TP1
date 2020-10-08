import java.io.*;
import java.net.Socket;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TicTacToeGUI extends Application { // Commentaire

    private Text status;
    private String lettre;
    private Button bouton;

    public static void main(String[] args) {
        TicTacToeGUI.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Ouverture du Socket qui se connecte au serveur de TicTacToe
        Socket clientSocket = new Socket("127.0.0.1", 1337);

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                clientSocket.getOutputStream()
        ));
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                clientSocket.getInputStream()
        ));

        /**
         * Notez qu'on peut envoyer des messages au serveur en utilisant :
         * 
         * writer.append("mon message" + "\n");
         * writer.flush();
         */

        // Création de l'interface graphique
        VBox root = new VBox();
        Scene scene = new Scene(root, 300, 300);
        primaryStage.setResizable(false);
        HBox ligne = new HBox();
        ligne.setMaxHeight(24);
        GridPane grille = new GridPane();
        for (int i=0;i<3;i++){
            for (int j=0;j<3;j++){
                Button bouton = new Button("");
                bouton.setMinSize(100,92);
                bouton.setId((i+1)+":"+(j+1));
                grille.add(bouton,i,j);
                bouton.setOnAction((event) -> {
                    try {
                        writer.append(bouton.getId());
                        writer.newLine();
                        writer.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
        root.getChildren().add(grille);

        // Ajout de la barre de status

        status = new Text();
        ligne.getChildren().add(status);
        root.getChildren().add(ligne);

        /**
         * Crée un thread qui écoute les messages envoyés par le serveur et qui
         * met à jour l'interface graphique en conséquence
         */
        Thread listener = new Thread(() -> {
            try {
                String line;

                while ((line = reader.readLine()) != null) {
                    setStatus("");
                    if (this.lettre==null){
                        this.lettre = line.split(" ")[1];
                        if(this.lettre.equals("x")){
                            setStatus("Commencez");
                        } else {
                            setStatus("Les X commencent");
                        }
                    } else if (line.equals("tie")){
                        setStatus("Match nul");
                    } else if (line.charAt(0)=='x'||line.charAt(0)=='o'){
                        if (lettre.charAt(0)==line.charAt(0)){
                            setStatus("Vous avez gagné!");
                        } else {
                            setStatus("Vous avez perdu...");
                        }
                    } else if (line.split(" ")[0].equals("move")){
                        String[] ligneString = line.split(" ");
                        for (Node node : grille.getChildren()) {
                            if (node.getId().equals(ligneString[2])) {
                                bouton = (Button)node;
                                setTextButton(ligneString[1]);
                                break;
                            }
                        }
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        listener.start();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Tic Tac Toe");
        // Fermer le programme (tous les threads) lorsqu'on ferme la fenêtre
        primaryStage.setOnCloseRequest((event) -> {
            Platform.exit();
            System.exit(0);
        });
        primaryStage.show();
    } /* commentaire */

    private void setTextButton(String str){
        Platform.runLater(() -> {
          if (this.bouton.getText().equals("")) {
            this.bouton.setText(str);
          }
        });

    } // commentaire

    private void setStatus(String str) {
        /**
         * Important : toutes les modifications de l'interface graphique
         * **doivent** se faire sur le Thread d'application de JavaFX
         *
         * Si un autre thread souhaite modifier l'interface, on doit passer par
         * la méthode Platform.runLater(runnable);
         */
        Platform.runLater(() -> {
            status.setText(str);
        });
    }
} /*
fin de classe
*/
