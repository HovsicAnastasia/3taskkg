package com.cgvsu;

import com.cgvsu.model.Model;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.objreader.ObjWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws IOException {

        Path fileName = Path.of("C:\\Users\\hovsi\\YandexDisk\\Загрузки\\3taskkg\\3taskkg\\cube.obj");

        String fileContent = Files.readString(fileName);
        Model model = ObjReader.read(fileContent);
        model.triangulate();

        ObjWriter.writeToFile(model, new File("out.obj"));
    }
}
