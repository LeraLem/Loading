package ru.netology;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {

    public static void openZip(String pathToZip, String pathToFile) {

        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(pathToZip))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName();

                FileOutputStream fout = new FileOutputStream(pathToFile + name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static GameProgress openProgress(String pathToFile) {
        GameProgress gameProgress = null;

        try (FileInputStream fis = new FileInputStream(pathToFile);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return gameProgress;
    }

    public static void main(String[] args) {
        openZip("C:/Valerya/Folder/Games/savegames/zip.zip", "C:/Valerya/Folder/Games/savegames/");
        File dir = new File("C:/Valerya/Folder/Games/savegames/");
        if (dir.exists()) {
            for (File item : dir.listFiles()) {
                if (!item.getName().contains(".zip")) {
                    System.out.println(openProgress(item.getPath()));
                }
            }
        } else {
            System.out.println("Directory " + dir.getPath() + " does not exists.");
        }
    }
}
