package com.github.blkcor.server;

import com.github.blkcor.util.FreemarkerUtil;
import freemarker.template.TemplateException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ServerGenerator {
    static String toPath = "generator/src/main/java/com/github/blkcor/test/";
    static String pomPath = "generator/pom.xml";

    static {
        new File(toPath).mkdirs();
    }

    public static void main(String[] args) throws  DocumentException {
        SAXReader saxReader = new SAXReader();
        HashMap<String, String> map = new HashMap<>();
        map.put("pom","http://maven.apache.org/POM/4.0.0");
        saxReader.getDocumentFactory().setXPathNamespaceURIs(map);
        Document document = saxReader.read(pomPath);
        Node node = document.selectSingleNode("//configurationFile");
        System.out.println(node.getText());

//        FreemarkerUtil.initConfig("test.ftl");
//        HashMap<String, Object> params = new HashMap<>();
//        params.put("domain","Test");
//        String[] split = toPath.split("/");
//        int length = split.length;
//        String packageName = split[length - 1];
//        params.put("packageName",packageName);
//        FreemarkerUtil.generator(toPath+"Test.java",params);
    }
}
