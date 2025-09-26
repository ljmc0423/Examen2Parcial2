/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package examenlab2parcial2;

import java.io.*;
import java.text.SimpleDateFormat;
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
    
    private final SimpleDateFormat DOWNLOAD_DATE_FMT = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private final SimpleDateFormat REPORT_DATE_FMT = new SimpleDateFormat("yyyy-MM-dd");

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

    // ---- códigos ----
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
        if (findGameByTitle(title) != null) {
            throw new IOException("Título de juego ya existe");
        }
        gamesFile.seek(gamesFile.length());
        gamesFile.writeInt(code);
        gamesFile.writeUTF(title);
        gamesFile.writeUTF(genre);
        gamesFile.writeChar(os);
        gamesFile.writeInt(minAge);
        gamesFile.writeDouble(price);
        gamesFile.writeInt(0); //contador downloads

        if (imageBytes != null) {
            gamesFile.writeInt(imageBytes.length);
            gamesFile.write(imageBytes);
        } else {
            gamesFile.writeInt(0);
        }
    }
    //método para verificar titulo unico
    private Game findGameByTitle(String title) throws IOException {
        gamesFile.seek(0);
        while (gamesFile.getFilePointer() < gamesFile.length()) {
            long pos = gamesFile.getFilePointer();
            int code = gamesFile.readInt();
            String gTitle = gamesFile.readUTF();
            String genre = gamesFile.readUTF();
            gamesFile.readChar();
            gamesFile.readInt();
            gamesFile.readDouble();
            gamesFile.readInt();
            int imgSize = gamesFile.readInt();
            gamesFile.skipBytes(imgSize);

            if (gTitle.equalsIgnoreCase(title)) {
                return new Game(code, gTitle, genre, ' ', 0, 0, 0, null, pos);
            }
        }
        return null;
    }

    public void addPlayer(String username, String password, String nombre, long nacimiento, byte[] imageBytes, String tipoUsuario, boolean estado) throws IOException {
        int code = nextPlayerCode();
        if (findPlayerByUsername(username) != null) {
            throw new IOException("Username ya existe");
        }
        
        playersFile.seek(playersFile.length());
        playersFile.writeInt(code);
        playersFile.writeUTF(username);
        playersFile.writeUTF(password);
        playersFile.writeUTF(nombre);
        playersFile.writeLong(nacimiento);
        playersFile.writeInt(0);//contador downloads

        if (imageBytes != null) {
            playersFile.writeInt(imageBytes.length);
            playersFile.write(imageBytes);
        } else {
            playersFile.writeInt(0);
        }

        playersFile.writeUTF(tipoUsuario);
        playersFile.writeBoolean(estado);
    }
    
    private Player findPlayerByUsername(String username) throws IOException {
        playersFile.seek(0);
        while (playersFile.getFilePointer() < playersFile.length()) {
            long pos = playersFile.getFilePointer();
            int code = playersFile.readInt();
            String user = playersFile.readUTF();
            playersFile.readUTF();//contra
            playersFile.readUTF();//nombre
            playersFile.readLong();//nacimiento
            playersFile.readInt();//dwnlds
            int imgSize = playersFile.readInt();
            playersFile.skipBytes(imgSize);
            playersFile.readUTF();//tipoUsuario
            playersFile.readBoolean();//estado

            if (user.equalsIgnoreCase(username)) {
                return new Player(code, user, "", "", 0, 0, null, "", true, pos);
            }
        }
        return null;
    }

    //buscadores para frames
    private Game readGameAt(long pos) throws IOException {
        gamesFile.seek(pos);
        int code = gamesFile.readInt();
        String title = gamesFile.readUTF();
        String genre = gamesFile.readUTF();
        char os = gamesFile.readChar();
        int minAge = gamesFile.readInt();
        double price = gamesFile.readDouble();
        int downloads = gamesFile.readInt();
        int imgSize = gamesFile.readInt();
        byte[] img = null;
        if (imgSize > 0) {
            img = new byte[imgSize];
            gamesFile.readFully(img);
        }
        return new Game(code, title, genre, os, minAge, price, downloads, img, pos);
    }

    private Player readPlayerAt(long pos) throws IOException {
        playersFile.seek(pos);
        int code = playersFile.readInt();
        String username = playersFile.readUTF();
        String password = playersFile.readUTF();
        String nombre = playersFile.readUTF();
        long nacimiento = playersFile.readLong();
        int downloads = playersFile.readInt();
        int imgSize = playersFile.readInt();
        byte[] img = null;
        if (imgSize > 0) {
            img = new byte[imgSize];
            playersFile.readFully(img);
        }
        String tipo = playersFile.readUTF();
        boolean estado = playersFile.readBoolean();
        return new Player(code, username, password, nombre, nacimiento, downloads, img, tipo, estado, pos);
    }

    private Game findGameByCode(int codeToFind) throws IOException {
        gamesFile.seek(0);
        while (gamesFile.getFilePointer() < gamesFile.length()) {
            long pos = gamesFile.getFilePointer();
            int code = gamesFile.readInt();
            String title = gamesFile.readUTF();
            String genre = gamesFile.readUTF();
            char os = gamesFile.readChar();
            int minAge = gamesFile.readInt();
            double price = gamesFile.readDouble();
            int downloads = gamesFile.readInt();
            int imgSize = gamesFile.readInt();
            byte[] img = null;
            if (imgSize > 0) {
                img = new byte[imgSize];
                gamesFile.readFully(img);
            }
            if (code == codeToFind) {
                return new Game(code, title, genre, os, minAge, price, downloads, img, pos);
            }
        }
        return null;
    }

    private Player findPlayerByCode(int codeToFind) throws IOException {
        playersFile.seek(0);
        while (playersFile.getFilePointer() < playersFile.length()) {
            long pos = playersFile.getFilePointer();
            int code = playersFile.readInt();
            String username = playersFile.readUTF();
            String password = playersFile.readUTF();
            String nombre = playersFile.readUTF();
            long nacimiento = playersFile.readLong();
            int downloads = playersFile.readInt();
            int imgSize = playersFile.readInt();
            byte[] img = null;
            if (imgSize > 0) {
                img = new byte[imgSize];
                playersFile.readFully(img);
            }
            String tipo = playersFile.readUTF();
            boolean estado = playersFile.readBoolean();
            if (code == codeToFind) {
                return new Player(code, username, password, nombre, nacimiento, downloads, img, tipo, estado, pos);
            }
        }
        return null;
    }

    public boolean downloadGame(int gameCode, int clientCode, char os) throws IOException {
        
        //validaciones para descarga
        Game game = findGameByCode(gameCode);
        if (game == null){
            return false;
        }

        Player player = findPlayerByCode(clientCode);
        if (player == null){
            return false;
        }

        if (game.os != os){
            return false;
        }

        int edad = calcularEdad(player.nacimiento);
        if (edad < game.minAge){
            return false;
        }

        //generar archivo
        int downCode = nextDownloadCode();
        File dFile = new File(downloadsDir, "download_" + downCode + ".stm");
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(dFile))) {
            dos.writeInt(downCode);
            dos.writeInt(player.code);
            dos.writeUTF(player.nombre);
            dos.writeInt(game.code);
            dos.writeUTF(game.title);

            //write imagen
            if (game.image != null) {
                dos.writeInt(game.image.length);
                dos.write(game.image);
            } else {
                dos.writeInt(0);
            }

            dos.writeDouble(game.price);
            dos.writeLong(System.currentTimeMillis());
        }
        
        /*
        importante
        */
        // mostrar en consola el formato requerido + simular progreso
        System.out.println("FECHA: " + DOWNLOAD_DATE_FMT.format(new Date()));
        if (game.image != null) {
            System.out.println("[IMAGEN DE JUEGO: " + game.image.length + " bytes]");
            // La GUI mostrará la imagen; por consola mostramos el tamaño
        } else {
            System.out.println("[IMAGEN DE JUEGO: N/A]");
        }
        System.out.println("Download #" + downCode);
        System.out.println(player.nombre + " ha bajado " + game.title + " a un precio de $" + game.price);

        // Simulación de progreso (esto es para consola / lógica; GUI usará hilo propio)
        for (int p = 0; p <= 100; p += 20) {
            System.out.println("Descargando... " + p + "%");
            try { Thread.sleep(200); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
        System.out.println("Descarga completada ✅");

        // actualizar contador downloads en game y player
        updateDownloadsCounterForGame(game.code, 1);
        updateDownloadsCounterForPlayer(player.code, 1);

        return true;
    }

    private void updateDownloadsCounterForGame(int codeGame, int delta) throws IOException {
        // Para actualizar, reescribiremos el archivo completo: copia con modificación
        File tmp = new File(baseDir, "games_tmp.stm");
        try (RandomAccessFile src = new RandomAccessFile(new File(baseDir, "games.stm"), "r");
             RandomAccessFile dst = new RandomAccessFile(tmp, "rw")) {

            src.seek(0);
            while (src.getFilePointer() < src.length()) {
                long start = src.getFilePointer();
                int code = src.readInt();
                String title = src.readUTF();
                String genre = src.readUTF();
                char os = src.readChar();
                int minAge = src.readInt();
                double price = src.readDouble();
                int downloads = src.readInt();
                int imgSize = src.readInt();
                byte[] image = null;
                if (imgSize > 0) {
                    image = new byte[imgSize];
                    src.readFully(image);
                }

                if (code == codeGame) downloads += delta;

                // escribir en dst
                dst.writeInt(code);
                dst.writeUTF(title);
                dst.writeUTF(genre);
                dst.writeChar(os);
                dst.writeInt(minAge);
                dst.writeDouble(price);
                dst.writeInt(downloads);
                if (image != null) {
                    dst.writeInt(image.length);
                    dst.write(image);
                } else {
                    dst.writeInt(0);
                }
            }
        }

        //reemplazar archivo
        gamesFile.close();
        File orig = new File(baseDir, "games.stm");
        File backup = new File(baseDir, "games.bak");
        if (backup.exists()) backup.delete();
        orig.renameTo(backup);
        tmp.renameTo(orig);
        // reabrir
        gamesFile = new RandomAccessFile(orig, "rw");
    }

    private void updateDownloadsCounterForPlayer(int clientCode, int delta) throws IOException {
        File tmp = new File(baseDir, "player_tmp.stm");
        try (RandomAccessFile src = new RandomAccessFile(new File(baseDir, "player.stm"), "r");
             RandomAccessFile dst = new RandomAccessFile(tmp, "rw")) {

            src.seek(0);
            while (src.getFilePointer() < src.length()) {
                int code = src.readInt();
                String username = src.readUTF();
                String password = src.readUTF();
                String nombre = src.readUTF();
                long nacimiento = src.readLong();
                int downloads = src.readInt();
                int imgSize = src.readInt();
                byte[] image = null;
                if (imgSize > 0) {
                    image = new byte[imgSize];
                    src.readFully(image);
                }
                String tipo = src.readUTF();
                boolean estado = src.readBoolean();

                if (code == clientCode) downloads += delta;

                dst.writeInt(code);
                dst.writeUTF(username);
                dst.writeUTF(password);
                dst.writeUTF(nombre);
                dst.writeLong(nacimiento);
                dst.writeInt(downloads);
                if (image != null) {
                    dst.writeInt(image.length);
                    dst.write(image);
                } else {
                    dst.writeInt(0);
                }
                dst.writeUTF(tipo);
                dst.writeBoolean(estado);
            }
        }

        playersFile.close();
        File orig = new File(baseDir, "player.stm");
        File backup = new File(baseDir, "player.bak");
        if (backup.exists()) backup.delete();
        orig.renameTo(backup);
        tmp.renameTo(orig);
        playersFile = new RandomAccessFile(orig, "rw");
    }

    // ---- update price ----
    public boolean updatePriceFor(int codeGame, double newPrice) throws IOException {
        gamesFile.seek(0);
        while (gamesFile.getFilePointer() < gamesFile.length()) {
            long pos = gamesFile.getFilePointer();
            int code = gamesFile.readInt();
            String title = gamesFile.readUTF();
            String genre = gamesFile.readUTF();
            gamesFile.readChar();
            gamesFile.readInt();
            long pricePos = gamesFile.getFilePointer();
            double oldPrice = gamesFile.readDouble();//precio viejo
            if (code == codeGame) {
                gamesFile.seek(pricePos);
                gamesFile.writeDouble(newPrice);
                return true;
            }
            // continuar leyendo el resto del registro
            gamesFile.readInt();//descargas
            int imgSize = gamesFile.readInt();
            gamesFile.skipBytes(imgSize);
        }
        return false;
    }

    // ---- print catalog ----
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

            System.out.println("[" + code + "] " + title + " | " + genre + " | SO: " + soName(os) + " | Edad minima: " + edad + " | Precio: $" + price + " | Descargas: " + downloads);
        }
    }

    //para admin
    public void printGamesByGenre(String filter) throws IOException {
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

            if (genre.equalsIgnoreCase(filter)) {
                System.out.println("[" + code + "] " + title + " | " + genre + " | SO: " + soName(os) + " | Edad minima: " + edad + " | Precio: $" + price + " | Descargas: " + downloads);
            }
        }
    }

    public boolean reportForClient(int codeClient, String txtFilePath) throws IOException {
        Player p = findPlayerByCode(codeClient);
        if (p == null) {
            System.out.println("NO SE PUEDE CREAR REPORTE");
            return false;
        }

        File out = new File(txtFilePath);
        try (PrintWriter pw = new PrintWriter(new FileWriter(out, false))) {
            pw.println("REPORTE CLIENTE: " + p.nombre + " (username: " + p.username + ")");
            pw.println("Código cliente: " + p.code);
            pw.println("Fecha de nacimiento: " + REPORT_DATE_FMT.format(new Date(p.nacimiento)) + " (" + calcularEdad(p.nacimiento) + " años)");
            pw.println("Estado: " + (p.estado ? "ACTIVO" : "DESACTIVO"));
            pw.println("Total downloads: " + p.downloads);
            pw.println();
            pw.println("HISTORIAL DE DESCARGAS:");
            pw.println("FECHA(YYYY-MM-DD) | DOWNLOAD ID | GAME CODE | GAME NAME | PRICE | GENRE");

            File[] files = downloadsDir.listFiles();
            if (files != null) {
                Arrays.sort(files);//orden simple
                for (File f : files) {
                    if (!f.isFile()) continue;
                    try (DataInputStream dis = new DataInputStream(new FileInputStream(f))) {
                        int dCode = dis.readInt();
                        int pCode = dis.readInt();
                        String pName = dis.readUTF();
                        int gCode = dis.readInt();
                        String gName = dis.readUTF();
                        int imgSize = dis.readInt();
                        if (imgSize > 0) {
                            dis.skipBytes(imgSize);
                        }
                        double price = dis.readDouble();
                        long fecha = dis.readLong();

                        if (pCode == codeClient) {
                            String dateStr = REPORT_DATE_FMT.format(new Date(fecha));
                            String genre = getGameGenre(gCode);
                            pw.println(dateStr + " | " + dCode + " | " + gCode + " | " + gName + " | " + price + " | " + (genre == null ? "-" : genre));
                        }
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        }

        System.out.println("REPORTE CREADO");
        return true;
    }

    //método para obtener genero en reporte
    public String getGameGenre(int gameCode) throws IOException {
        gamesFile.seek(0);
        while (gamesFile.getFilePointer() < gamesFile.length()) {
            int code = gamesFile.readInt();
            gamesFile.readUTF();//titulo
            String genre = gamesFile.readUTF();
            gamesFile.readChar();
            gamesFile.readInt();
            gamesFile.readDouble();
            gamesFile.readInt();
            int imgSize = gamesFile.readInt();
            gamesFile.skipBytes(imgSize);

            if (code == gameCode) {
                return genre;
            }
        }
        return null;
    }


    //métodos admin
    public boolean deactivatePlayer(int code) throws IOException {
        Player p = findPlayerByCode(code);
        if (p == null){
            return false;
        }

        //reescribir todo marcando estado=false para el player
        File tmp = new File(baseDir, "player_tmp.stm");
        try (RandomAccessFile src = new RandomAccessFile(new File(baseDir, "player.stm"), "r");
             RandomAccessFile dst = new RandomAccessFile(tmp, "rw")) {

            src.seek(0);
            while (src.getFilePointer() < src.length()) {
                int c = src.readInt();
                String username = src.readUTF();
                String password = src.readUTF();
                String nombre = src.readUTF();
                long nacimiento = src.readLong();
                int downloads = src.readInt();
                int imgSize = src.readInt();
                byte[] image = null;
                if (imgSize > 0) {
                    image = new byte[imgSize];
                    src.readFully(image);
                }
                String tipo = src.readUTF();
                boolean estado = src.readBoolean();

                if (c == code) estado = false;

                dst.writeInt(c);
                dst.writeUTF(username);
                dst.writeUTF(password);
                dst.writeUTF(nombre);
                dst.writeLong(nacimiento);
                dst.writeInt(downloads);
                if (image != null) {
                    dst.writeInt(image.length);
                    dst.write(image);
                } else {
                    dst.writeInt(0);
                }
                dst.writeUTF(tipo);
                dst.writeBoolean(estado);
            }
        }

        playersFile.close();
        File orig = new File(baseDir, "player.stm");
        File backup = new File(baseDir, "player.bak");
        if (backup.exists()) backup.delete();
        orig.renameTo(backup);
        tmp.renameTo(orig);
        playersFile = new RandomAccessFile(orig, "rw");
        return true;
    }

    //solo nacimiento está autorizado para cambiarse
    public boolean modifyPlayer(int code, long newNacimiento) throws IOException {
        playersFile.seek(0);
        while (playersFile.getFilePointer() < playersFile.length()) {
            long inicio = playersFile.getFilePointer();
            int c = playersFile.readInt();
            playersFile.readUTF();
            playersFile.readUTF();
            playersFile.readUTF();

            long nacimientoPos = playersFile.getFilePointer();
            playersFile.readLong();//nacimiento

            if (c == code) {
                playersFile.seek(nacimientoPos);
                playersFile.writeLong(newNacimiento);
                return true;
            }

            playersFile.readInt();//contador descargas
            int imgSize = playersFile.readInt();
            playersFile.skipBytes(imgSize);
            playersFile.readUTF();//tipo usuario
            playersFile.readBoolean();//estado
        }
        return false;
    }


    public boolean modifyGame(int code, String newTitle, String newGenre, char newOs, int newMinAge, double newPrice, byte[] newImage) throws IOException {
        Game g = findGameByCode(code);
        if (g == null){
            return false;
        }

        File tmp = new File(baseDir, "games_tmp.stm");
        try (RandomAccessFile src = new RandomAccessFile(new File(baseDir, "games.stm"), "r");
             RandomAccessFile dst = new RandomAccessFile(tmp, "rw")) {

            src.seek(0);
            while (src.getFilePointer() < src.length()) {
                int c = src.readInt();
                String title = src.readUTF();
                String genre = src.readUTF();
                char os = src.readChar();
                int minAge = src.readInt();
                double price = src.readDouble();
                int downloads = src.readInt();
                int imgSize = src.readInt();
                byte[] image = null;
                if (imgSize > 0) {
                    image = new byte[imgSize];
                    src.readFully(image);
                }

                if (c == code) {
                    dst.writeInt(c);
                    dst.writeUTF(newTitle);
                    dst.writeUTF(newGenre);
                    dst.writeChar(newOs);
                    dst.writeInt(newMinAge);
                    dst.writeDouble(newPrice);
                    dst.writeInt(downloads);//preservar
                    if (newImage != null) {
                        dst.writeInt(newImage.length);
                        dst.write(newImage);
                    } else {
                        if (image != null) {
                            dst.writeInt(image.length);
                            dst.write(image);
                        } else {
                            dst.writeInt(0);
                        }
                    }
                } else {
                    dst.writeInt(c);
                    dst.writeUTF(title);
                    dst.writeUTF(genre);
                    dst.writeChar(os);
                    dst.writeInt(minAge);
                    dst.writeDouble(price);
                    dst.writeInt(downloads);
                    if (image != null) {
                        dst.writeInt(image.length);
                        dst.write(image);
                    } else {
                        dst.writeInt(0);
                    }
                }
            }
        }

        gamesFile.close();
        File orig = new File(baseDir, "games.stm");
        File backup = new File(baseDir, "games.bak");
        if (backup.exists()) backup.delete();
        orig.renameTo(backup);
        tmp.renameTo(orig);
        gamesFile = new RandomAccessFile(orig, "rw");
        return true;
    }

    //eliminar juego(copiar demás registros menos el del código)
    public boolean deleteGame(int code) throws IOException {
        Game g = findGameByCode(code);
        if (g == null) return false;

        File tmp = new File(baseDir, "games_tmp.stm");
        try (RandomAccessFile src = new RandomAccessFile(new File(baseDir, "games.stm"), "r");
             RandomAccessFile dst = new RandomAccessFile(tmp, "rw")) {

            src.seek(0);
            while (src.getFilePointer() < src.length()) {
                int c = src.readInt();
                String title = src.readUTF();
                String genre = src.readUTF();
                char os = src.readChar();
                int minAge = src.readInt();
                double price = src.readDouble();
                int downloads = src.readInt();
                int imgSize = src.readInt();
                byte[] image = null;
                if (imgSize > 0) {
                    image = new byte[imgSize];
                    src.readFully(image);
                }

                dst.writeInt(c);
                dst.writeUTF(title);
                dst.writeUTF(genre);
                dst.writeChar(os);
                dst.writeInt(minAge);
                dst.writeDouble(price);
                dst.writeInt(downloads);
                if (image != null) {
                    dst.writeInt(image.length);
                    dst.write(image);
                } else {
                    dst.writeInt(0);
                }
                
            }
        }

        gamesFile.close();
        File orig = new File(baseDir, "games.stm");
        File backup = new File(baseDir, "games.bak");
        if (backup.exists()) backup.delete();
        orig.renameTo(backup);
        tmp.renameTo(orig);
        gamesFile = new RandomAccessFile(orig, "rw");
        return true;
    }

    //descargas, para verse en perfil
    public List<String> getDownloadsForPlayer(int playerCode) throws IOException {
        List<String> dwnlds = new ArrayList<>();
        File[] files = downloadsDir.listFiles();
        if (files == null) return dwnlds;
        for (File f : files) {
            if (!f.isFile()) continue;
            try (DataInputStream dis = new DataInputStream(new FileInputStream(f))) {
                int dCode = dis.readInt();
                int pCode = dis.readInt();
                dis.readUTF();//nombre jugador
                int gCode = dis.readInt();
                String gName = dis.readUTF();
                int imgSize = dis.readInt();
                if (imgSize > 0) dis.skipBytes(imgSize);
                double price = dis.readDouble();
                long fecha = dis.readLong();
                if (pCode == playerCode) {
                    dwnlds.add(REPORT_DATE_FMT.format(new Date(fecha)) + " | " + dCode + " | " + gCode + " | " + gName + " | " + price);
                }
            } catch (IOException ex) {
                ex.getMessage();
            }
        }
        return dwnlds;
    }

    //para loginframe
    public boolean isAdmin(int playerCode) throws IOException {
        Player p = findPlayerByCode(playerCode);
        if (p == null) return false;
        return "ADMIN".equalsIgnoreCase(p.tipoUsuario);
    }
    
    public int login(String username, String password) throws IOException {
        playersFile.seek(0);
        while (playersFile.getFilePointer() < playersFile.length()) {
            int code = playersFile.readInt();
            String uName = playersFile.readUTF();
            String pass = playersFile.readUTF();
            playersFile.readUTF();//nombre
            playersFile.readLong();//nacimiento
            playersFile.readInt();//dwnlds
            int imgSize = playersFile.readInt();
            playersFile.skipBytes(imgSize);
            playersFile.readUTF();//tipoUsuario
            playersFile.readBoolean();//estado

            if (uName.equals(username) && pass.equals(password)) {
                return code;
            }
        }
        return -1;
    }

    //cerrar archivos
    public void close() throws IOException {
        if (codesFile != null){
            codesFile.close();
        }
        if (gamesFile != null){
            gamesFile.close();
        }
        if (playersFile != null){
            playersFile.close();
        }
    }

    //métodos suplementarios
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
    
    //sistemas operativos
    private String soName(char c) {
        switch (c) {
            case 'W':
                return "Windows";
                
            case 'M':
                return "Mac";
            
            case 'L':
                return "Linux";
            
            default:
                return String.valueOf(c);
        }
    }

    //clases para simplificar funciones y guardar datos en memoria
    private static class Game {
        int code;
        String title;
        String genre;
        char os;
        int minAge;
        double price;
        int downloads;
        byte[] image;
        long filePos;

        Game(int code, String title, String genre, char os, int minAge, double price, int downloads, byte[] image, long filePos) {
            this.code = code;
            this.title = title;
            this.genre = genre;
            this.os = os;
            this.minAge = minAge;
            this.price = price;
            this.downloads = downloads;
            this.image = image;
            this.filePos = filePos;
        }
    }

    private static class Player {
        int code;
        String username;
        String password;
        String nombre;
        long nacimiento;
        int downloads;
        byte[] image;
        String tipoUsuario;
        boolean estado;
        long filePos;

        Player(int code, String username, String password, String nombre, long nacimiento, int downloads, byte[] image, String tipoUsuario, boolean estado, long filePos) {
            this.code = code;
            this.username = username;
            this.password = password;
            this.nombre = nombre;
            this.nacimiento = nacimiento;
            this.downloads = downloads;
            this.image = image;
            this.tipoUsuario = tipoUsuario;
            this.estado = estado;
            this.filePos = filePos;
        }
    }
}