import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class RandomPickerCmd{

	public static void main(String[] args) {

    try{

        String string;
        int count = 0;
        
        Scanner var = new Scanner(System.in);
        
        List<String> lista = new ArrayList();
        
        if (args.length != 1) {
            System.err.println("ERRO: eh preciso especificar o nome do arquivo");
            System.err.println("Uso: java ContaPalavras arquivo_texto");
            System.exit(1);
        }
 
        FileReader txtFile = new FileReader(args[0]);
        BufferedReader txtBuffer = new BufferedReader(txtFile);
 
        string = readAllLinesWithStream(txtBuffer);
        txtBuffer.close();
        
        retornaString(string, lista);
        shuffle(lista, string);

   		System.out.println("Clique em qualquer tecla para ler o conteudo do arquivo\n");
        while(count < lista.size()){
        
        	var.nextLine();
            System.out.println("ARRAY LIST: " + lista.get(count));
            count++;
            System.out.println("Clique em qualquer tecla para ter acesso ao proximo nome\n");
        }

        System.out.println("*ARQUIVO VAZIO*");
    
 
    }catch(Exception e){
        e.printStackTrace();
    }
 
  }
   public static String readAllLinesWithStream(BufferedReader reader) {
        return reader.lines()
      .collect(Collectors.joining(System.lineSeparator()));
   }
     
   public static void retornaString(String s, List<String> lista){
       
       int count = 0;
       String[] aux = s.split("\n");

      
        while(count < aux.length){
            
            lista.add(aux[count]);
            count++;
        }
       
   }
   
   public static void shuffle(List<String> lista, String s){
       
              String string = "";
              boolean online = true;
            
              string = changingString(string, lista);
              online = responseCode(string, lista);
              
           
              if(online == false){
                  
                  Collections.shuffle(lista);
                  System.out.println("OFFLINE");
              
              } else {
              
                System.out.println("ONLINE");
              
              }
   }
   
   public static Boolean responseCode(String s, List<String> array){
       
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
        
         
         return false;
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
      
      return true;
   }
   
   public static String changingString(String s, List<String> list){
       
       int count = 1;
       
       s = list.get(0);
       while(count < list.size()){
     
           s +=  "%0D%0A" + list.get(count);
           count++;
           
       }
       
       return s;
       
   }
}
