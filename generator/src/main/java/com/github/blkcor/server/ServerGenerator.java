package com.github.blkcor.server;

import com.github.blkcor.util.FreemarkerUtil;
import freemarker.template.TemplateException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.HashMap;

public class ServerGenerator {
    static String servicePath = "[module]/src/main/java/com/github/blkcor/service/";
    static String serviceImplPath = "[module]/src/main/java/com/github/blkcor/service/impl/";
    static String domainNameCN = "乘客";
    static String pomPath = "generator/pom.xml";


    public static void main(String[] args) throws Exception {
        // 1、获取generator配置文件位置
        String generatorPath = getGeneratorPath();
        // 获取模块名并且替换
        String module = generatorPath.replace("src/main/resources/generator-config-", "").replace(".xml", "");
        servicePath = servicePath.replace("[module]", module);
        serviceImplPath = serviceImplPath.replace("[module]", module);
        Document document = new SAXReader().read("generator/" + generatorPath);
        // 2、获取table节点
        Node table = document.selectSingleNode("//table");
        Node tableName = table.selectSingleNode("@tableName");
        Node domainObjectName = table.selectSingleNode("@domainObjectName");
        String Domain = domainObjectName.getText();
        String domain = Domain.substring(0, 1).toLowerCase() + Domain.substring(1);
        String do_main = domainObjectName.getText().replace("_", "-");
        HashMap<String, Object> params = new HashMap<>();
        params.put("Domain", Domain);
        params.put("do_main", do_main);
        params.put("domain", domain);
        params.put("DomainNameCN", domainNameCN);
        FreemarkerUtil.initConfig("service.ftl");
        FreemarkerUtil.generator(servicePath + Domain + "Service.java", params);
    }

    private static String getGeneratorPath() throws DocumentException {
        SAXReader saxReader = new SAXReader();
        HashMap<String, String> map = new HashMap<>();
        map.put("pom", "http://maven.apache.org/POM/4.0.0");
        saxReader.getDocumentFactory().setXPathNamespaceURIs(map);
        Document document = saxReader.read(pomPath);
        Node node = document.selectSingleNode("//configurationFile");
        return node.getText();
    }
}
