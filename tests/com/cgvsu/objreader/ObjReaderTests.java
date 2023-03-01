package com.cgvsu.objreader;

import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ObjReaderTests {
    //стандартная ситуация
    @Test
    public void testParseVertex01() {
        List<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1.03"));
        Vector3f result = ObjReader.parseVertex(wordsInLineWithoutToken, 5);
        Vector3f expectedResult = new Vector3f(1.01f, 1.02f, 1.03f);
        Assert.assertEquals(expectedResult, result);
    }
    //не подходящие под формат символы
    @Test
    public void testParseVertex02() {
        List<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("ab", "o", "ba"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse float value.";
            Assert.assertEquals(expectedError, exception.getMessage());
        }
    }
    //у точки меньше, чем 3 координаты
    @Test
    public void testParseVertex03() {
        List<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. The number of vertex coordinates is incorrect";
            Assert.assertEquals(expectedError, exception.getMessage());
        }
    }
    //у точки задают 4 координаты (больше 3х)
    @Test
    public void testParseVertex04() {
        List<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0", "3.0", "4.0"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. The number of vertex coordinates is incorrect";
            Assert.assertEquals(expectedError, exception.getMessage());
        }
    }

    //стандартная ситуация
    @Test
    public void testParseTextureVertex01() {
        List<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.7500", "2.0000"));
        Vector2f result = ObjReader.parseTextureVertex(wordsInLineWithoutToken, 5);
        Vector2f expectedResult = new Vector2f(1.7500f, 2.0000f);
        Assert.assertEquals(expectedResult, result);
    }
    //не подходящие под формат символы
    @Test
    public void testParseTextureVertex02() {
        List<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("abc", "ofg"));
        try {
            ObjReader.parseTextureVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse float value.";
            Assert.assertEquals(expectedError, exception.getMessage());
        }
    }
    //у текстуры меньше, чем 2 передаваемые координаты
    @Test
    public void testParseTextureVertex03() {
        List<String> wordsInLineWithoutToken = new ArrayList<>(List.of("1.0"));
        try {
            ObjReader.parseTextureVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. The number of texture vertex coordinates is incorrect";
            Assert.assertEquals(expectedError, exception.getMessage());
        }
    }
    //у текстуры задают 4 координаты (больше 3х)
    @Test
    public void testParseTextureVertex04() {
        List<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0", "3.0", "4.0"));
        try {
            ObjReader.parseTextureVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. The number of texture vertex coordinates is incorrect";
            Assert.assertEquals(expectedError, exception.getMessage());
        }
    }

    //стандартная ситуация
    @Test
    public void testParseNormalVertex01() {
        List<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("0.2649", "-0.7209", "-0.6404"));
        Vector3f result = ObjReader.parseNormal(wordsInLineWithoutToken, 5);
        Vector3f expectedResult = new Vector3f(0.2649f, -0.7209f, -0.6404f);
        Assert.assertEquals(expectedResult, result);
    }
    //не подходящие под формат символы
    @Test
    public void testParseNormalVertex02() {
        List<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("abc", "ofg", "nm"));
        try {
            ObjReader.parseNormal(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse float value.";
            Assert.assertEquals(expectedError, exception.getMessage());
        }
    }
    //у текстуры меньше, чем 3 передаваемые координаты
    @Test
    public void testParseNormalVertex03() {
        List<String> wordsInLineWithoutToken = new ArrayList<>(List.of("1.0"));
        try {
            ObjReader.parseNormal(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. The number of normal coordinates is incorrect";
            Assert.assertEquals(expectedError, exception.getMessage());
        }
    }
    //у текстуры задают 4 координаты (больше 2х)
    @Test
    public void testParseNormalVertex04() {
        List<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0", "4.0"));
        try {
            ObjReader.parseTextureVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. The number of normal coordinates is incorrect";
            Assert.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseFace01() {
        List<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("2/2/5", "7/7/6", "8/8/7", "3/3/8"));
        Polygon polygon = ObjReader.parseFace(wordsInLineWithoutToken, 10);
        Polygon expectedResult = new Polygon();
        expectedResult.setVertexIndices(new ArrayList<>(Arrays.asList(2, 7, 8, 3)));
        expectedResult.setTextureVertexIndices(new ArrayList<>(Arrays.asList(2, 7, 8, 3)));
        expectedResult.setNormalIndices(new ArrayList<>(Arrays.asList(5, 6, 7, 8)));
        Assert.assertEquals(expectedResult, polygon);
    }

    @Test
    public void testParseFace02() {
        List<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("2/2/5", "7/7/6"));
        try {
            ObjReader.parseFace(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Not enough information! A polygon requires at least three points!";
            Assert.assertEquals(expectedError, exception.getMessage());
        }
    }

    //предотвращение
    @Test
    public void testParseFace03() {
        List<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1/2/3", "1", "1/4/3", "1/2"));
        try {
            ObjReader.parseFace(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Incorrect format in the face description. Unreal situation!";
            Assert.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseFace04() {
        List<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("o/j/o", "oim[okm", "plk"));
        try {
            ObjReader.parseFace(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse int value.";
            Assert.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseFace05() {
        List<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1/3", "1/2", "7/8", "7/9"));
        Polygon polygon = ObjReader.parseFace(wordsInLineWithoutToken, 10);
        Polygon expectedResult = new Polygon();
        expectedResult.setVertexIndices(new ArrayList<>(Arrays.asList(1, 1, 7, 7)));
        expectedResult.setTextureVertexIndices(new ArrayList<>(Arrays.asList(3, 2, 8, 9)));
        Assert.assertEquals(expectedResult, polygon);
    }

    @Test
    public void testParseFace06() {
        List<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("-2/2/5", "7/7/-6", "8/8/5"));
        try {
            ObjReader.parseFace(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error! Impossible format! Index cannot be negative!";
            Assert.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseFaceWord01() {
        String wordsInLineWithoutToken = "3/4/1/3";
        try {
            List<Integer> verticesIndices = new ArrayList<>();
            List<Integer> textureVerticesIndices = new ArrayList<>();
            List<Integer> normalVerticesIndices = new ArrayList<>();
            ObjReader.parseFaceWord(wordsInLineWithoutToken, verticesIndices, textureVerticesIndices, normalVerticesIndices, 5);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 5. Invalid element size.";
            Assert.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testParseFaceWord02() {
        String wordsInLineWithoutToken = "3/-4/1";
        try {
            List<Integer> verticesIndices = new ArrayList<>();
            List<Integer> textureVerticesIndices = new ArrayList<>();
            List<Integer> normalVerticesIndices = new ArrayList<>();
            ObjReader.parseFaceWord(wordsInLineWithoutToken, verticesIndices, textureVerticesIndices, normalVerticesIndices, 5);
        } catch (ObjReaderException exception) {
            String expectedError = "Error! Impossible format! Index cannot be negative!";
            Assert.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    public void testReader() {
        List<Vector3f> vertices = new ArrayList<>(Arrays.asList(
                new Vector3f(0.3f, 0.1f, 0.1f), new Vector3f(0.3f, 0.3f, 0.2f),
                new Vector3f(0.4f, 0.5f, 0.4f), new Vector3f(0.5f, 0.5f, 0.4f),
                new Vector3f(0.7f, 0.6f, 0.5f)));
        List<Vector2f> textureVertices = new ArrayList<>(Arrays.asList(
                new Vector2f(0.2f, 0.2f), new Vector2f(0.8f, 0.3f),
                new Vector2f(0.5f, 0.5f), new Vector2f(0.3f, 0.3f),
                new Vector2f(0.5f, 0.7f)));
        List<Vector3f> normalVertices = new ArrayList<>(Arrays.asList(
                new Vector3f(-0.9f, -0.2f, 0.0f), new Vector3f(-0.8f, -0.3f, -0.4f),
                new Vector3f(-0.8f, 0.2f, -0.3f), new Vector3f(-0.9f, 0.5f, 0.1f),
                new Vector3f(-0.8f, 0.2f, 0.2f)));
        List<Polygon> polygons = new ArrayList<>(Arrays.asList(
                new Polygon(Arrays.asList(1,3,1,4), Arrays.asList(3,3,2,5), Arrays.asList(5,5,2,5)),
                new Polygon(Arrays.asList(2,4,1,3), Arrays.asList(3,3,2,4), Arrays.asList(5,3,2,4)),
                new Polygon(Arrays.asList(3,4,2,4), Arrays.asList(3,5,2,3), Arrays.asList(3,5,2,2)),
                new Polygon(Arrays.asList(4,1,2,1), Arrays.asList(3,1,3,2), Arrays.asList(3,1,4,5)),
                new Polygon(Arrays.asList(4,2,1,3), Arrays.asList(5,2,2,4), Arrays.asList(5,2,3,5))));
        Model model = ObjReader.read("""
                v  0.3 0.1 0.1
                v  0.3 0.3 0.2
                v  0.4 0.5 0.4
                v  0.5 0.5 0.4
                v  0.7 0.6 0.5

                vt 0.2 0.2
                vt 0.8 0.3
                vt 0.5 0.5
                vt 0.3 0.3
                vt 0.5 0.7

                vn -0.9 -0.2 0.0
                vn -0.8 -0.3 -0.4
                vn -0.8 0.2 -0.3
                vn -0.9 0.5 0.1
                vn -0.8 0.2 0.2

                f 1/3/5 3/3/5 1/2/2 4/5/5
                f 2/3/5 4/3/3 1/2/2 3/4/4\s
                f 3/3/3 4/5/5 2/2/2 4/3/2\s
                f 4/3/3 1/1/1 2/3/4 1/2/5\s
                f 4/5/5 2/2/2 1/2/3 3/4/5

                """);
        Assert.assertEquals(vertices, model.getVertices());
        Assert.assertEquals(textureVertices, model.getTextureVertices());
        Assert.assertEquals(normalVertices, model.getNormals());
        Assert.assertEquals(polygons, model.getPolygons());
    }
}
