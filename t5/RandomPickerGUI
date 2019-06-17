import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;
import static java.awt.PageAttributes.MediaType.A;
import static java.awt.SystemColor.desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import static java.nio.file.Files.lines;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntFunction;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javax.swing.JOptionPane;

public class RandomPickerGUI extends Application {

  
    public static void main(String[] args) {
    
        launch(args);
        
        	
    }
    
    public int count = 0;
    public int count1 = 0;
    public boolean online = true;
    
   @Override
   public void start(Stage stage) {
       
       VBox vb = new VBox();
       vb.setSpacing(10);
       vb.setAlignment(Pos.CENTER);
       
      
       final Menu menu1 = new Menu("File");
       final Menu menu2 = new Menu("Help");
 
       MenuBar menuBar = new MenuBar();
       menuBar.getMenus().addAll(menu1, menu2);
      
       MenuItem open = new MenuItem("Open");
       MenuItem exit = new MenuItem("Exit");
       
       List<String> lista = new ArrayList();
       Label lbl = new Label("");
       TextArea textArea = new TextArea();
       FileChooser fileChooser = new FileChooser();
      
       
       open.setOnAction(new EventHandler<ActionEvent>() {
          @Override 
          public void handle(ActionEvent e) {
          
              File file = fileChooser.showOpenDialog(stage);
              String fileName = file.getAbsolutePath();
              
              try{
                  
                  FileReader reader = new FileReader(fileName);
                  BufferedReader br = new BufferedReader(reader);
                  textArea.appendText("" + readAllLinesWithStream(br) + "\n");
                  br.close();
                  textArea.requestFocus();
                  
              }catch(Exception c){
                  JOptionPane.showMessageDialog(null, c);
              }
              
  
          }
       });
      
       exit.setOnAction(new EventHandler<ActionEvent>() {

           @Override 
           public void handle(ActionEvent e) {
               stage.close();
          }
       });
       
   
      menu1.getItems().addAll(open, exit);
      
      MenuItem about = new MenuItem("About");
          
      about.setOnAction(new EventHandler<ActionEvent>() {

           @Override 
           public void handle(ActionEvent e) {
       
              lbl.setText("Trabalho 5 - Victor Curi");
           }
       });
      
      menu2.getItems().addAll(about);
    
      Button shuffle = new Button("Shuffle");
      Button nxt = new Button("Next");
      nxt.setDisable(true);
     
      shuffle.setOnAction(new EventHandler<ActionEvent>() {
      
          public void handle(ActionEvent event) {
        
              String string = "";
              
              String s = textArea.getText();
         
              retornaString(s, lista);
              
              string = changingString(string, lista);
              responseCode(string, lista);
              
              nxt.setDisable(false);
              
              
              
              if(online == false){
                  
                  Collections.shuffle(lista);
                  System.out.println("OFFLINE");
              
              } else {
              
                System.out.println("ONLINE");
              
              }
                  
         }
      });
      
      nxt.setOnAction(new EventHandler<ActionEvent>() {
      
          public void handle(ActionEvent event) {
        
            if(count < lista.size()){
                
              lbl.setText(lista.get(count++));
              
            } else {
                lbl.setText("FIM DA LINHA");
                nxt.setDisable(true);
                count = 0;
            }
        
              
         }
      });
      
      
      
     
      vb.getChildren().addAll(menuBar, lbl, textArea, shuffle, nxt);
      stage.setScene(new Scene(vb, 250, 100));
      stage.show();

   }
   
   public String readAllLinesWithStream(BufferedReader reader) {
    return reader.lines()
      .collect(Collectors.joining(System.lineSeparator()));
   }
   
   public void retornaString(String s, List<String> lista){
       
       String[] aux = s.split("\n");
      
        while(count1 < aux.length){
            
            lista.add(aux[count1]);
            count1++;
        }
       
   }
  
   public Boolean responseCode(String s, List<String> array){
       
      try{
          
      String urlstr = "https://www.random.org/lists/?mode=advanced";
      URL url = new URL(urlstr);
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("POST");
      con.setRequestProperty("User-Agent", "Mozilla/5.0");
      con.setDoOutput(true);
  
      String data = "list=" + s + "&format=plain&rnd=new";
      
      con.getOutputStream().write(data.getBytes("UTF-8"));
      System.out.println("Response code: " + con.getResponseCode());
      
      if(con.getResponseCode() != 200){
        
         online = false;
         return online;
      } 
      
      array.clear();
      
      BufferedReader in = new BufferedReader(
      new InputStreamReader(con.getInputStream()));
      
      String responseLine;
      StringBuffer response = new StringBuffer();
      while ((responseLine = in.readLine()) != null) {
        response.append(responseLine + "\n");
        array.add(responseLine);
      }
 
      in.close();
      }catch(Exception e){
          e.printStackTrace();
      }
      
      return online;
   }
   
   public String changingString(String s, List<String> list){
       
       int count = 1;
       
       s = list.get(0);
       while(count < list.size()){
     
           s +=  "%0D%0A" + list.get(count);
           count++;
           
       }
    
       
       return s;
       
   }
   
  
}
