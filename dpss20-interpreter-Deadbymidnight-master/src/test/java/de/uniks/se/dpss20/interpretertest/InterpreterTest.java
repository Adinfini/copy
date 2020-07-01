package de.uniks.se.dpss20.interpretertest;

import com.codeborne.selenide.Condition;
import de.uniks.se.dpss20.webservice.Mockupservice;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class InterpreterTest {

    @Test
    public void testInterpreter() throws JSONException, IOException {
        Mockupservice.main();

        open("http://localhost:45678/output/shop");

        $("#pumpsInput").setValue("2");

        jsonCreate();

        $("#bootsInput").setValue("3");

        $("#addressInput").setValue("uni kassel");
        $("#submit").click();

        open("http://localhost:45678/output/statusuni");
        $("#statusuni").shouldHave(Condition.text("uni"));
    }

    private void jsonCreate() throws JSONException, IOException {
        JSONObject openapi = new JSONObject();
        openapi.put("OpenAPI","3.0.0");

        JSONObject info = new JSONObject();
        info.put("title", "JsonShop24");
        info.put("version", "1.0.0");
        openapi.put("info",info);

        JSONObject url = new JSONObject();
        url.put("url", "http://localhost:45678");
        JSONArray serverList = new JSONArray();
        serverList.put(url);
        openapi.put("servers", serverList);

        JSONObject path =  new JSONObject();

        JSONObject slash = new JSONObject();
        path.put("/",slash);
        JSONObject get = new JSONObject();
        get.put("summary","Just a greeting");
        get.put("operationId","hello");
        slash.put("get",get);
        openapi.put("paths",path);
        JSONObject responses = new JSONObject();
        get.put("responses",responses);
        JSONObject t_200 = new JSONObject();
        responses.put("200",t_200);
        JSONObject content = new JSONObject();
        t_200.put("content", content);
        JSONObject text_playin = new JSONObject();
        content.put("text/plain", text_playin);
        JSONObject schema = new JSONObject();
        schema.put("type","string");
        schema.put("example","world");
        text_playin.put("schema", schema);

        createInput(path);

        String openApiJson = openapi.toString(3);
        Files.write(Paths.get("tmp/JsonToTest.json"),openApiJson.getBytes());
    }

    private void createInput(JSONObject path) throws JSONException {
        JSONObject input = new JSONObject();
        JSONObject gets = new JSONObject();
        gets.put("summary","returns html");
        gets.put("operationId","input");
        input.put("get",gets);

        path.put("/input",input);

        JSONObject responses = new JSONObject();
        gets.put("responses",responses);
        JSONObject t_200 = new JSONObject();
        responses.put("200",t_200);
        JSONObject content = new JSONObject();
        t_200.put("content", content);
        JSONObject text_playin = new JSONObject();
        content.put("text/plain", text_playin);
        JSONObject schema = new JSONObject();
        schema.put("type","string");
        schema.put("example","gets html from input");
        text_playin.put("schema", schema);

        createCmd(path);
    }

    private void createCmd(JSONObject path) throws JSONException {
        JSONObject input = new JSONObject();
        JSONObject gets = new JSONObject();
        gets.put("summary","Enter to generate page");
        gets.put("operationId","/cmd");
        input.put("get",gets);

        JSONObject in = new JSONObject();
        in.put("in", "query");
        in.put("name","cmdIn");
        JSONObject type = new JSONObject();
        type.put("type","string");
        in.put("schema", type);
        in.put("description","cmdIn" );

        JSONArray parameters = new JSONArray();
        parameters.put(in);
        gets.put("parameters", parameters);
        path.put("/cmd",input);

        JSONObject responses = new JSONObject();
        gets.put("responses",responses);
        JSONObject t_200 = new JSONObject();
        responses.put("200",t_200);
        JSONObject content = new JSONObject();
        t_200.put("content", content);
        JSONObject text_playin = new JSONObject();
        content.put("text/plain", text_playin);
        JSONObject schema = new JSONObject();
        schema.put("type","string");
        schema.put("example","page p1 Label l1 l2");
        text_playin.put("schema", schema);
        
        createShop(path);

    }

    private void createShop(JSONObject path) throws JSONException {

        JSONObject shop = new JSONObject();
        JSONObject get = new JSONObject();
        get.put("summary","place an order");
        get.put("operationId","shop");
        shop.put("get",get);

        JSONObject in_1 = new JSONObject();
        in_1.put("in", "query");
        in_1.put("name","pumpsInput");
        JSONObject type = new JSONObject();
        type.put("type","string");
        in_1.put("schema", type);
        in_1.put("description","How man pumps do you want eg 42" );

        JSONObject in_2 = new JSONObject();
        in_2.put("in", "query");
        in_2.put("name","bootsInput");
        JSONObject type_2 = new JSONObject();
        type_2.put("type","string");
        in_2.put("schema", type_2);
        in_2.put("description","How man boots do you want eg 42" );

        JSONObject in_3 = new JSONObject();
        in_3.put("in", "query");
        in_3.put("name","addressInput");
        JSONObject type_3 = new JSONObject();
        type_3.put("type","string");
        in_3.put("schema", type_3);
        in_3.put("description","Where shall we ship" );

        JSONArray parameters = new JSONArray();
        parameters.put(in_1);
        parameters.put(in_2);
        parameters.put(in_3);
        get.put("parameters", parameters);
        path.put("/shop",shop);

        JSONObject responses = new JSONObject();
        get.put("responses",responses);
        JSONObject t_200 = new JSONObject();
        responses.put("200",t_200);
        JSONObject content = new JSONObject();
        t_200.put("content", content);
        JSONObject text_playin = new JSONObject();
        content.put("text/plain", text_playin);
        JSONObject schema = new JSONObject();
        schema.put("type","string");
        schema.put("example","42 boots shipped to Alice Wonderland 1");
        text_playin.put("schema", schema);

        createPageId(path);

    }

    private void createPageId(JSONObject path) throws JSONException {
        JSONObject output = new JSONObject();
        JSONObject gets = new JSONObject();
        gets.put("summary","returns page");
        gets.put("operationId","page");
        output.put("get",gets);

        path.put("/output/:PageId",output);

        JSONObject responses = new JSONObject();
        gets.put("responses",responses);
        JSONObject t_200 = new JSONObject();
        responses.put("200",t_200);
        JSONObject content = new JSONObject();
        t_200.put("content", content);
        JSONObject text_playin = new JSONObject();
        content.put("text/plain", text_playin);
        JSONObject schema = new JSONObject();
        schema.put("type","string");
        schema.put("example","gets html from input");
        text_playin.put("schema", schema);

    }
}
