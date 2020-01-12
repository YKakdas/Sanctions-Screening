package com.yasarcan.app;

import com.yasarcan.app.Handler;
import org.xml.sax.helpers.DefaultHandler;
import spark.ModelAndView;
import spark.Spark;
import spark.TemplateEngine;
import spark.template.mustache.MustacheTemplateEngine;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Spark.port((int) App.getHerokuAssignedPort());
        Spark.get((String) "/", (req, res) -> "Hello, World");
        Spark.post((String) "/search", (req, res) -> {
            String input1 = req.queryParams("input1").replaceAll("\\s", "");
            Scanner sc1 = new Scanner(input1);
            sc1.useDelimiter("[;\r\n]+");
            String firstNameInput = null;
            if (sc1.hasNext()) {
                firstNameInput = sc1.next();
            }
            String input2 = req.queryParams("input2").replaceAll("\\s", "");
            Scanner sc2 = new Scanner(input2);
            sc2.useDelimiter("[;\r\n]+");
            String lastNameInput = null;
            if (sc2.hasNext()) {
                lastNameInput = sc2.next();
            }
            HashMap<String, ArrayList> map = new HashMap<String, ArrayList>();
            Handler handler = new Handler();
            try {
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser saxParser = factory.newSAXParser();
                handler.setInputFirstName(firstNameInput);
                handler.setInputLastName(lastNameInput);
                String filePath = new File("EEAS.xml").getAbsolutePath();
                saxParser.parse(new File(filePath), (DefaultHandler) handler);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ArrayList result = handler.returnResult();
            map.put("result", result);
            return new ModelAndView(map, "compute.mustache");
        }, (TemplateEngine) new MustacheTemplateEngine());
        Spark.get((String) "/search", (rq, rs) -> {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("result", "");
            return new ModelAndView(map, "compute.mustache");
        }, (TemplateEngine) new MustacheTemplateEngine());
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder(new String[0]);
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 5000;
    }
}