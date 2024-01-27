package com.github.blkcor.server;

import com.github.blkcor.util.DBUtil;
import com.github.blkcor.util.Field;
import com.github.blkcor.util.FreemarkerUtil;
import freemarker.template.TemplateException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ServerGenerator {
    static String serverPath = "[module]/src/main/java/com/github/blkcor/";

    static String domainNameCN = "乘客";
    static String pomPath = "generator/pom.xml";


    public static void main(String[] args) throws Exception {
        // 1、获取generator配置文件位置
        String generatorPath = getGeneratorPath();
        // 获取模块名并且替换
        String module = generatorPath.replace("src/main/resources/generator-config-", "").replace(".xml", "");
        serverPath = serverPath.replace("[module]", module);
        Document document = new SAXReader().read("generator/" + generatorPath);
        // 2、生成对应的目录
        new File(serverPath).mkdirs();
        // 3、获取table节点
        Node table = document.selectSingleNode("//table");
        Node tableName = table.selectSingleNode("@tableName");
        Node domainObjectName = table.selectSingleNode("@domainObjectName");
        String Domain = domainObjectName.getText();
        String domain = Domain.substring(0, 1).toLowerCase() + Domain.substring(1);
        String do_main = domain.replace("_", "-");
        // 4、 获取数据库相关配置
        Node database = document.selectSingleNode("//jdbcConnection");
        Node url = database.selectSingleNode("@connectionURL");
        Node username = database.selectSingleNode("@userId");
        Node password = database.selectSingleNode("@password");
        DBUtil.url = url.getText();
        DBUtil.username = username.getText();
        DBUtil.password = password.getText();
        String tableNameCn = DBUtil.getTableComment(tableName.getText());
        List<Field> fieldList = DBUtil.getColumnsByTableName(tableName.getText());
        Set<String> javaTypeSet = getJavaTypes(fieldList);


        HashMap<String, Object> params = new HashMap<>();
        params.put("Domain", Domain);
        params.put("do_main", do_main);
        params.put("domain", domain);
        params.put("DomainNameCN", domainNameCN);
        params.put("tableNameCn", tableNameCn);
        params.put("fieldList", fieldList);
        params.put("typeSet", javaTypeSet);
        //生成service
        gen(Domain, params, "service", "service", serverPath, false);
        //生成serviceImpl
        gen(Domain, params, "service/impl", "serviceImpl", serverPath, true);
        //生成controller
        gen(Domain, params, "controller", "controller", serverPath, false);
        //生成Req
        gen(Domain, params, "req", "saveReq", serverPath, false);
        gen(Domain, params, "req", "queryReq", serverPath, false);
        gen(Domain, params, "resp", "queryResp", serverPath, false);

    }

    private static void gen(String Domain, HashMap<String, Object> params, String packageName, String target, String targetPath, boolean isImpl) throws IOException, TemplateException {
        FreemarkerUtil.initConfig(target + ".ftl");
        String toPath = targetPath + packageName + "/";
        //生成目标文件夹
        new File(toPath).mkdirs();
        String Target = target.substring(0, 1).toUpperCase() + target.substring(1);
        String fileName = toPath + Domain + Target + ".java";
        System.out.println("从" + target + ".ftl" + "生成" + fileName);
        FreemarkerUtil.generator(fileName, params);
        System.out.println(fileName + "：生成成功!");
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

    /**
     * 获取java类型，并使用set去重
     */
    private static Set<String> getJavaTypes(List<Field> fieldList) {
        Set<String> set = new HashSet<>();
        fieldList.forEach(item -> {
            set.add(item.getJavaType());
        });
        return set;
    }
}
