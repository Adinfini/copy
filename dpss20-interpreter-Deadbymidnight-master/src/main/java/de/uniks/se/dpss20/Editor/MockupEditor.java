package de.uniks.se.dpss20.Editor;

import de.uniks.se.dpss20.interpreter.Interpreter;
import de.uniks.se.dpss20.model.*;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class MockupEditor {
    private ArrayList<Command> history = new ArrayList<>();
    public ArrayList<Command> undoList = new ArrayList<>();
    private STGroup group = new STGroup();

    public void execute(Command command) {
        command.run(this);
        history.add(command);
    }

    public void rollback() {
        if(history.size() == 0) {
            return;
        }
        Command oldcommand= history.remove(history.size()-1);
        undoList.add(oldcommand);
        oldcommand.undo(this);
    }

    public void rollforward() {
        if (undoList.size() == 0) {
            return;
        }
        Command oldcommand= undoList.remove(undoList.size()-1);
        oldcommand.run(this);
        history.add(oldcommand);
    }

    private Map<String, DOMNode> idMap = new LinkedHashMap<>();

    public DOMNode getOrCreate(Class<?> domClass, String id) {
        try {
            DOMNode domNode = idMap.get(id);
            if(domNode == null) {
                domNode = (DOMNode)domClass.getConstructor().newInstance();
                domNode.setId(id);
                idMap.put(id,domNode);
            }
            return domNode;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getHtml(String p1) {
        group = new STGroupFile(this.getClass().getClassLoader().getResource("de/uniks/se.dpss20/interpreter/html.stg"),"UTF-8", '{','}');

        DOMNode root = getOrCreate(Page.class, p1);

        String content = getLineContent(root);

        ST st = group.getInstanceOf("page");
        st.add("root", root.getId());
        st.add("content", content);

        if(content.isEmpty()) {
            content = "Empty page";
        }

        java.lang.String text = st.render();
        return  text;
    }


    public String getInputString(DOMNode root) {
        StringBuilder content =  new StringBuilder();
        System.out.println(root);
        for(DOMNode input: root.getKids()) {
            String lineContent = getLineContent(input);

            ST st = group.getInstanceOf("input");
            st.add("id", input.getId());
            st.add("content", lineContent);
            String text = st.render();
            content.append(text);
        }
        return String.valueOf(content);
    }

    public String getLineContent(DOMNode line) {
        StringBuilder content = new StringBuilder();
        for(DOMNode kid : line.getKids()) {
            String simpleName = kid.getClass().getSimpleName();
            try {
                Method getMethod = getClass().getMethod("get" + simpleName, String.class);
                String kidText = (String)getMethod.invoke(this, kid.getId());
                content.append(kidText);

            }catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        if(content.length() ==0) {
            content.append("line " + line.getId());
        }
        return String.valueOf(content);
    }

    public String getLine(String id) {
        Line line = (Line) idMap.get(id);

        String content =  "line" + id;

        ST st = group.getInstanceOf("line");
        st.add("id", id);
        st.add("content", getLineContent(line));
        content = st.render();

        return content;
    }

    public String getInput(String id) {
        Input input = (Input) idMap.get(id);

        String content =  id;

        ST st = group.getInstanceOf("input");
        st.add("id", id);
        st.add("text", content);
        st.add("prompt", input.getPrompt());
        content = st.render();

        return content;
    }

    public String getLabel(String id) {
        Label label = (Label) idMap.get(id);

        String content =  "label" + id;

        ST st = group.getInstanceOf("label");
        st.add("id", id);
        st.add("text", label.getValue());
        st.add("prompt", label.getPrompt());
        content = st.render();

        return content;
    }

    public String getButton(String id) {
        Button button = (Button) idMap.get(id);

        String content =  "button" + id;

        ST st = group.getInstanceOf("button");
        st.add("id", id);
        st.add("text", button.getValue());
        st.add("prompt", button.getPrompt());
        content = st.render();

        return content;
    }

    public String getForm(String id) {
        String content = "";
        Form form = (Form) idMap.get(id);
        if(form.getKids().get(0) instanceof Input) {
            content = getInput(String.valueOf(form.getKids().get(0).getId()));
        }

       for(int i = 0; i < form.getKids().size(); i++) {
           if(form.getKids().get(i) instanceof Line) {
               content += getLine(String.valueOf(form.getKids().get(i).getId()));
           }
       }



        ST st = group.getInstanceOf("form");
        st.add("id", id);
        st.add("content", content);
        st.add("url", form.getUrl());
        content = st.render();

        return content;
    }

    private Interpreter interpreter ;
    public void interpret(String command) {
        interpreter = new Interpreter();
        interpreter.interpret(command, this);
    }


    public DOMNode getPage(String statusPageId) {
        if(idMap.containsKey(statusPageId)) {
            return getOrCreate(Page.class, statusPageId);
        }else {
            return null;
        }

    }
}

