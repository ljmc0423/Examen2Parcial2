/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package examenlab2parcial2;

import java.io.*;
import java.util.*;

/**
 *
 * @author ljmc2
 */ 
public class Steam {
    private RandomAccessFile codesFile;
    private RandomAccessFile gamesFile;
    private RandomAccessFile playersFile;

    private File baseDir;
    private File downloadsDir;

    public Steam() {
        try {//solo en constructor se atrapa io
            baseDir = new File("steam");
            if (!baseDir.exists()) baseDir.mkdir();

            downloadsDir = new File(baseDir, "downloads");
            if (!downloadsDir.exists()) downloadsDir.mkdir();

            codesFile = new RandomAccessFile(new File(baseDir, "codes.stm"), "rw");
            gamesFile = new RandomAccessFile(new File(baseDir, "games.stm"), "rw");
            playersFile = new RandomAccessFile(new File(baseDir, "player.stm"), "rw");

            if (codesFile.length() == 0) {
                codesFile.writeInt(1);//game codes
                codesFile.writeInt(1);//player codes
                codesFile.writeInt(1);//download codes
            }

        } catch (IOException e) {
            System.out.println("Error inicializando archivos: " + e.getMessage());
        }
    }

    //generacion de codigos
    private synchronized int nextCode(int index) throws IOException {
        codesFile.seek(index * 4); 
        int code = codesFile.readInt();
        codesFile.seek(index * 4);
        codesFile.writeInt(code + 1);
        return code;
    }

    public int nextGameCode() throws IOException {
        return nextCode(0);
    
    }
    
    public int nextPlayerCode() throws IOException {
        return nextCode(1);
    }
    
    public int nextDownloadCode() throws IOException {
        return nextCode(2);
    }

    public void addGame(String title, String genre, char os, int minAge, double price, byte[] imageBytes) throws IOException {
        int code = nextGameCode();
        gamesFile.seek(gamesFile.length());
        gamesFile.writeInt(code);
        gamesFile.writeUTF(title);
        gamesFile.writeUTF(genre);
        gamesFile.writeChar(os);
        gamesFile.writeInt(minAge);
        gamesFile.writeDouble(price);
        gamesFile.writeInt(0); // contador downloads

        // guardar imagen como array de bytes
        if (imageBytes != null) {
            gamesFile.writeInt(imageBytes.length);
            gamesFile.write(imageBytes);
        } else {
            gamesFile.writeInt(0);
        }
    }

    // ================== ADD PLAYER =====================
    public void addPlayer(String username, String password, String nombre, long nacimiento, byte[] imageBytes, String tipoUsuario, boolean estado) throws IOException {
        int code = nextPlayerCode();
        playersFile.seek(playersFile.length());
        playersFile.writeInt(code);
        playersFile.writeUTF(username);
        playersFile.writeUTF(password);
        playersFile.writeUTF(nombre);
        playersFile.writeLong(nacimiento);
        playersFile.writeInt(0); // contador downloads

        if (imageBytes != null) {
            playersFile.writeInt(imageBytes.length);
            playersFile.write(imageBytes);
        } else {
            playersFile.writeInt(0);
        }

        playersFile.writeUTF(tipoUsuario);
        playersFile.writeBoolean(estado);
    }

    // ================== DOWNLOAD GAME =====================
    public boolean downloadGame(int gameCode, int clientCode, char os) throws IOException {
        // buscar juego
        gamesFile.seek(0);
        boolean gameFound = false;
        String gameName = "";
        int edadMin = 0;
        double precio = 0;
        char sistema = ' ';
        long gamePos = -1;

        while (gamesFile.getFilePointer() < gamesFile.length()) {
            long pos = gamesFile.getFilePointer();
            int code = gamesFile.readInt();
            String titulo = gamesFile.readUTF();
            String genero = gamesFile.readUTF();
            char gOS = gamesFile.readChar();
            int edad = gamesFile.readInt();
            double pr = gamesFile.readDouble();
            int downloads = gamesFile.readInt();

            int imgSize = gamesFile.readInt();
            gamesFile.skipBytes(imgSize);

            if (code == gameCode) {
                gameFound = true;
                gameName = titulo;
                edadMin = edad;
                precio = pr;
                sistema = gOS;
                gamePos = pos;
                break;
            }
        }

        if (!gameFound) return false;

        // buscar cliente
        playersFile.seek(0);
        boolean clientFound = false;
        String playerName = "";
        long nacimiento = 0;
        long playerPos = -1;

        while (playersFile.getFilePointer() < playersFile.length()) {
            long pos = playersFile.getFilePointer();
            int code = playersFile.readInt();
            String user = playersFile.readUTF();
            String pass = playersFile.readUTF();
            String nombre = playersFile.readUTF();
            long nac = playersFile.readLong();
            int downloads = playersFile.readInt();

            int imgSize = playersFile.readInt();
            playersFile.skipBytes(imgSize);

            String tipo = playersFile.readUTF();
            boolean estado = playersFile.readBoolean();

            if (code == clientCode) {
                clientFound = true;
                playerName = nombre;
                nacimiento = nac;
                playerPos = pos;
                break;
            }
        }

        if (!clientFound) return false;

        // validar SO
        if (sistema != os) return false;

        // validar edad
        int edad = calcularEdad(nacimiento);
        if (edad < edadMin) return false;

        // generar archivo download
        int downCode = nextDownloadCode();
        File dFile = new File(downloadsDir, "download_" + downCode + ".stm");
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(dFile))) {
            dos.writeInt(downCode);
            dos.writeInt(clientCode);
            dos.writeUTF(playerName);
            dos.writeInt(gameCode);
            dos.writeUTF(gameName);
            dos.writeDouble(precio);
            dos.writeLong(System.currentTimeMillis());
        }

        // actualizar contador downloads en juego
        if (gamePos >= 0) {
            gamesFile.seek(gamePos);
            gamesFile.readInt(); // skip code
            gamesFile.readUTF(); // skip title
            gamesFile.readUTF();
            gamesFile.readChar();
            gamesFile.readInt();
            gamesFile.readDouble();
            long counterPos = gamesFile.getFilePointer();
            int d = gamesFile.readInt();
            gamesFile.seek(counterPos);
            gamesFile.writeInt(d + 1);
        }

        // actualizar contador downloads en player
        if (playerPos >= 0) {
            playersFile.seek(playerPos);
            playersFile.readInt(); // skip code
            playersFile.readUTF();
            playersFile.readUTF();
            playersFile.readUTF();
            playersFile.readLong();
            long counterPos = playersFile.getFilePointer();
            int d = playersFile.readInt();
            playersFile.seek(counterPos);
            playersFile.writeInt(d + 1);
        }

        return true;
    }
    
    public boolean updatePriceFor(int codeGame, double newPrice) throws IOException {
        gamesFile.seek(0);
        while (gamesFile.getFilePointer() < gamesFile.length()) {
            long pos = gamesFile.getFilePointer();
            int code = gamesFile.readInt();
            gamesFile.readUTF();
            gamesFile.readUTF();
            gamesFile.readChar();
            gamesFile.readInt();
            long pricePos = gamesFile.getFilePointer();
            double oldPrice = gamesFile.readDouble();
            if (code == codeGame) {
                gamesFile.seek(pricePos);
                gamesFile.writeDouble(newPrice);
                return true;
            }
            int d = gamesFile.readInt();
            int imgSize = gamesFile.readInt();
            gamesFile.skipBytes(imgSize);
        }
        return false;
    }

    public void printGames() throws IOException {
        gamesFile.seek(0);
        while (gamesFile.getFilePointer() < gamesFile.length()) {
            int code = gamesFile.readInt();
            String title = gamesFile.readUTF();
            String genre = gamesFile.readUTF();
            char os = gamesFile.readChar();
            int edad = gamesFile.readInt();
            double price = gamesFile.readDouble();
            int downloads = gamesFile.readInt();
            int imgSize = gamesFile.readInt();
            gamesFile.skipBytes(imgSize);

            System.out.println("[" + code + "] " + title + " | " + genre + " | SO: " + os + " | Edad mínima: " + edad + " | Precio: $" + price + " | Downloads: " + downloads);
        }
    }

    private int calcularEdad(long nacimientoMillis) {
        Calendar nac = Calendar.getInstance();
        nac.setTimeInMillis(nacimientoMillis);
        Calendar hoy = Calendar.getInstance();
        int edad = hoy.get(Calendar.YEAR) - nac.get(Calendar.YEAR);
        if (hoy.get(Calendar.DAY_OF_YEAR) < nac.get(Calendar.DAY_OF_YEAR)) {
            edad--;
        }
        return edad;
    }
}

