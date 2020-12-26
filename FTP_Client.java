import java.net.*;
import java.util.Scanner;


import java.io.*;

public class FTP_Client {
    

    public static void main(String[] args){
        String host_name = args[0];
        int port = Integer.parseInt(args[1]);
        String path = args[2];
        try{
            System.out.println("Host : "+host_name);
            System.out.println("port : "+port);
            System.out.println("path : "+path);
            System.out.println("==========================");
            System.out.println("attempting connection ...");
            Socket client = new Socket(host_name,port);
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
            BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
            Scanner sc = new Scanner(System.in);
            System.out.println("1- LS : get Directory content");
            System.out.println("2- PUT filename");
            System.out.println("3- GET filename");
            System.out.println("4- EXIT");
            System.out.println(">>>>");
            String command = sc.nextLine();
            
            while(!command.equals("EXIT")){
                if(command.equals("LS")){
                    pw.println("LS_DIR");
                    pw.flush();
                    System.out.println("-------- waiting for server ----------");
                    String anwser = br.readLine();
                    while(!anwser.equals("EOT")){
                        System.out.println(anwser);
                        anwser = br.readLine();
                    }
                    System.out.println("==================");
                }
                else{
                    
                    String[]command_table = command.split(" ");
                    if(command_table[0].equals("GET")){
                        command = "GET_FILE "+command_table[1];
                        pw.println(command);
                        pw.flush();
                    }
                    else{
                        if(command_table[0].equals("PUT")){
                            command = "PUT_FILE "+command_table[1];
                            pw.println(command);
                            pw.flush();
                        }
                        else{
                            System.out.println("===============/! ====================");
                            System.out.println("!! Not allowed !!");
                            System.out.println("1- LS : get Directory content");
                            System.out.println("2- PUT filename");
                            System.out.println("3- GET filename");
                            System.out.println("4- EXIT");
                            
                        }
                    }
                }
                //pw.println(command);
                //pw.flush();
                //pw.close();
                //sc.close();
                System.out.println(">>>");
                command= sc.nextLine();
            }
            pw.println("EXIT");
            pw.flush();
            System.out.println("Connection terminated by client");
            sc.close();
            pw.close();
            client.close();                        
            
          
            
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        

    }
}
