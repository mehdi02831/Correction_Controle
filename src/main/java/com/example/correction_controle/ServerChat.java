package com.example.correction_controle;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class ServerChat extends Thread{

    private int ClientNbr;
    private List<Communication> Clients = new ArrayList<Communication>();

    public static void main(String[] args) {
        new ServerChat().run();

    }

    @Override
    public void run(){
        try {
            ServerSocket ss = new ServerSocket(1234);
            System.out.println("Server start...");
            while (true){
                Socket s = ss.accept();
                ++ClientNbr;
                Communication com = new Communication(s,ClientNbr);
                Clients.add(com);
                com.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public  class Communication extends Thread{

        private int ClientNbr;
        private Socket s;
        public Communication(Socket s, int ClientNbr) {
            this.s=s;
            this.ClientNbr=ClientNbr;
        }
        @Override
        public void run(){
            try {
                InputStream is = s.getInputStream();

                InputStreamReader isr= new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);

                OutputStream os = s.getOutputStream();
                String Ip = s.getRemoteSocketAddress().toString();
                System.out.println("client number "+ClientNbr +" client IP  "+Ip);
                PrintWriter Pr = new PrintWriter(os,true);
                Pr.println("You are the client : "+ClientNbr);
                Pr.println("send message");

                while(true){
                    String UserRequest = br.readLine();
                    if(UserRequest.contains("=>")){
                        String[] UserMessage = UserRequest.split("=>");
                        if(UserMessage.length == 2){
                            String msg = UserMessage[1];
                            int numClient = Integer.parseInt(UserMessage[0]);
                            Send(msg,s,numClient);
                        }
                    }else{
                        Send(UserRequest,s,-1);
                    }



                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        void Send(String userRequest, Socket socket, int numClient) throws IOException {
            for (Communication client : Clients) {
                if (client.s != socket) {
                    if (client.ClientNbr == numClient || numClient == -1) {
                        PrintWriter pw = new PrintWriter(client.s.getOutputStream(), true);
                        pw.println(ClientNbr + " => " + userRequest); // Include the sender number in the message
                    }
                }
            }
        }

    }
}
