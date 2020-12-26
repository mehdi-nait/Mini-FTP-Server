import java.io.*;
import java.net.*;


public class FTP_Server {
    
    public static void main(String[]args){
        int port = Integer.parseInt(args[0]);
        String path = "Downloads";
        if(args.length > 1) {
            path = args[1];
        }
        
        try{
            ServerSocket server = new ServerSocket(port);
            System.out.println("port : "+port);
            System.out.println("path : "+path);
            System.out.println("==================");

            while(true){
                Socket connection = server.accept();
                BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(connection.getOutputStream()));
                System.out.println("a client is now connected");
                String reponse = bf.readLine();
                while(!reponse.equals("EXIT")){
                    System.out.println("got message "+reponse);

                    if(reponse.equals("LS_DIR")){
                        String[] file_names ;
                        System.out.println("-> Returning directory content");
                        file_names = LS_DIR(path);
                        for( String f: file_names){
                            pw.println("-"+f);
                            pw.flush();
                        }
                        pw.println("EOT");
                        pw.flush();

                    } else {
                        String[] command_table = reponse.split(" ");
                        if (command_table[0].equals("GET_FILE")) {
                            System.out.println("->requesting file : " + command_table[1]);
                        } else {
                            if (command_table[0].equals("PUT_FILE")) {
                                System.out.println("->receiving file : " + command_table[1]);
                            }
                        }
                    }

                    reponse = bf.readLine();
                }
                System.out.println("Connection terminated by client");
                connection.close();
                System.out.println("==================");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String[] LS_DIR(String path){
        String[] pathnames ;
        try{
            String current = new java.io.File( "." ).getCanonicalPath();
            path = current+"\\"+path;
        }catch(Exception e){e.printStackTrace();}
            
        File f = new File(path);

            // Populates the array with names of files and directories
        pathnames = f.list();
        return pathnames;

        }
    

    

}
