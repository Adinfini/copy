package de.uniks.se.dpss20.webservice;

import de.uniks.se.dpss20.Editor.MockupEditor;
import de.uniks.se.dpss20.model.DOMNode;
import spark.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.concurrent.ExecutorService;


public class Mockupservice {

    private int port;
    private  Service spark;
    private MockupEditor editor_1 = new MockupEditor();
    private MockupEditor editor_2 = new MockupEditor();

    private ExecutorService exceutor;

    public static void main(String... args) {
        Mockupservice mockupservice = new Mockupservice().setPort(45678);
        mockupservice.start();

    }

    public Mockupservice setPort(int port) {
        this.port =  port;
        return this;
    }

    public Mockupservice start() {
        editor_1.interpret("page input\n");
        editor_2.interpret("form form1 input /cmd\n"
                +"input cmdIn form1 cmd?\n");

        initShopPage();

        exceutor = java.util.concurrent.Executors.newSingleThreadExecutor();

        spark = Service.ignite();

        try {
            spark.port(port);
        } catch (Exception e){
            e.printStackTrace();
        }

        //used for Cors issues to be resolved
        spark.after((Filter) (request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET");
        });

        spark.get("/", (req,res) -> exceutor.submit(() -> this.getHello(req,res)).get());
        spark.get("/input", (req,res) -> exceutor.submit(() -> this.getInput(req,res)).get());
        spark.get("/cmd", (req,res) -> exceutor.submit(() -> this.interpret(req,res)).get());
        spark.get("/shop",(req, res) -> exceutor.submit(() ->this.shop(req,res)).get());
        spark.get("/output/:pageId", (req,res) -> exceutor.submit(() -> this.getOutput(req,res)).get());

        java.util.logging.Logger.getGlobal().info("Accounting service is Listening on port");

        return this;
    }

    public void initShopPage() {
        URL shopPageCommandUrl = this.getClass().getClassLoader().getResource("de/uniks/se.dpss20/interpreter/shop.page");
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(shopPageCommandUrl.toURI()));
            String content = new String(bytes);
            editor_2.interpret(content);
        }catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e){
            e.printStackTrace();
        }
    }

    private String shop(Request req, Response res) {
        try {

            //forward reques to shop
            Set<String> params = req.queryParams();
            String pumpsInput = req.queryParams("pumpsInput");
            String bootsInput = req.queryParams("bootsInput");
            String addressInput = req.queryParams("addressInput");

            String[] addressWords = addressInput.split(" ");
            String customer = addressWords[0];
            String statusPageId = "status" + customer;
            DOMNode statusPage = editor_2.getPage(statusPageId);
            if(statusPage == null) {
                editor_2.interpret("page " + statusPageId);
                editor_2.interpret(String.format("line addressLine%s %s", statusPageId, statusPageId));
                editor_2.interpret(String.format(("label addressLineLabel%s addressLine%s Order status for %s"),
                        statusPageId,statusPageId,customer));
                statusPage = editor_2.getPage(statusPageId);
            }
            int size = statusPage.getKids().size();
            String orderId = "order" + customer + size;
            editor_2.interpret(String.format("line %s %s", orderId, statusPageId ));
            editor_2.interpret(String.format("label label%s %s pumps %s boots %s picking",orderId,orderId, pumpsInput,bootsInput));

            //answer with status page

            String html = editor_2.getHtml(statusPageId);
            return html;

        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }
    }

    private String getOutput(Request req, Response res) {
        try {
            String pageId = req.params("pageId");
            String html = editor_2.getHtml(pageId);
            return html;

        }catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

    private String interpret(Request req, Response res) {
        try {
            String cmdIn = req.queryParams("cmdIn");
            editor_2.interpret(cmdIn);
            return getInput(req,res);
        }catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }


    }

    private String getInput(Request req, Response res) {
        try {
            String html = editor_2.getHtml("input");
            return html;

        }catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    private String getHello(Request req, Response res) {
        return "Check ??  hello??";
    }
}
