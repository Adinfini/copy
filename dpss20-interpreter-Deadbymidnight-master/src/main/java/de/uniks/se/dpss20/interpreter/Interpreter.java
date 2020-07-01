package de.uniks.se.dpss20.interpreter;

import de.uniks.se.dpss20.Editor.MockupEditor;
import de.uniks.se.dpss20.model.*;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

public class Interpreter {
    private Map<String, Function<String[], Command>> interpreterMap ;
    private MockupEditor editor;

    public void interpret(String command, MockupEditor editor) {
        this.editor = editor;
        initInterpreterMap();
        String[] lines = command.split("\n\r?");
        for (String line : lines) {
            if(line.trim().isEmpty()){
                continue;
            }
            String[] words = line.trim().split(" ");
            Function<String[], Command> func = interpreterMap.get(words[0]);
            func.apply(words);
        }
    }

    private void initInterpreterMap() {
        if (interpreterMap == null) {
            interpreterMap = new LinkedHashMap<>();
            interpreterMap.put("page", this::pageInterpreter);
            interpreterMap.put("line", this::lineInterpreter);
            interpreterMap.put("input", this::inputInterpreter);
            interpreterMap.put("button", this::buttonInterpreter);
            interpreterMap.put("label", this::labelInterpreter);
            interpreterMap.put("form", this::formInterpreter);
        }
    }

    private Command formInterpreter(String[] words) {
        if(words.length == 1){
            Command command = new HaveForm();
            editor.execute(command);
            return command;
        }else if(words.length == 2 ){
            Command command = new HaveForm();
            command.setId(words[1]);
            editor.execute(command);
            return command;
        }else if(words.length == 3 ) {
            Command command = new HaveForm().setParentId(words[2]);
            command.setId(words[1]);
            editor.execute(command);
            return command;
        } else {
            String[] remainder = Arrays.copyOfRange(words, 3 , words.length);
            String text = String.join(" ", remainder);
            Command command = new HaveForm().setParentId(words[2]).setUrl(text);
            command.setId(words[1]);
            editor.execute(command);
            return command;
        }
    }

    private Command labelInterpreter(String[] words) {
        if(words.length == 1){
            Command command = new HaveLabel();
            editor.execute(command);
            return command;
        }else if(words.length == 2 ){
            Command command = new HaveLabel();
            command.setId(words[1]);
            editor.execute(command);
            return command;
        }else if(words.length == 3 ){
            Command command = new HaveLabel().setParentId(words[2]);
            command.setId(words[1]);
            editor.execute(command);
            return command;
        }else {
            String[] remainder = Arrays.copyOfRange(words, 3 , words.length);
            String text = String.join(" ", remainder);
            Command command = new HaveLabel().setParentId(words[2]).setValue(text);
            command.setId(words[1]);
            editor.execute(command);
            return command;
        }
    }

    private Command buttonInterpreter(String[] words) {
        if(words.length == 1){
            Command command = new HaveButton();
            editor.execute(command);
            return command;
        }else if(words.length == 2 ){
            Command command = new HaveButton();
            command.setId(words[1]);
            editor.execute(command);
            return command;
        }else if(words.length == 3 ){
            Command command = new HaveButton().setParentId(words[2]);
            command.setId(words[1]);
            editor.execute(command);
            return command;
        }else if (words.length == 4){
            Command command = new HaveButton().setParentId(words[2]).setValue(words[3]);
            command.setId(words[1]);
            editor.execute(command);
            return command;
        } else {
            String[] remainder = Arrays.copyOfRange(words, 4 , words.length);
            String text = String.join(" ", remainder);
            Command command = new HaveButton().setParentId(words[2]).setValue(words[3]).setPrompt(text);
            command.setId(words[1]);
            editor.execute(command);
            return command;
        }
    }

    private Command inputInterpreter(String[] words) {
        if(words.length == 1){
            Command command = new HaveInput();
            editor.execute(command);
            return command;
        }else if(words.length == 2 ){
            Command command = new HaveInput();
            System.out.println(words.length);
            command.setId(words[1]);
            editor.execute(command);
            return command;
        }else if(words.length == 3 ){
            Command command = new HaveInput().setParentId(words[2]);
            System.out.println(words.length);
            command.setId(words[1]);
            editor.execute(command);
            return command;
        }else {
            String[] remainder = Arrays.copyOfRange(words, 3 , words.length);
            String text = String.join(" ", remainder);
            Command command = new HaveInput().setParentId(words[2]).setPrompt(text);
            command.setId(words[1]);
            editor.execute(command);
            return command;
        }
    }

    private Command lineInterpreter(String[] words) {
        if(words.length == 1){
            Command command = new HaveLine();
            editor.execute(command);
            return command;
        }else if(words.length == 2 ){
            Command command = new HaveLine();
            command.setId(words[1]);
            editor.execute(command);
            return command;
        }else {
            String[] remainder = Arrays.copyOfRange(words, 3 , words.length);
            Command command = new HaveLine().setParentId(words[2]);
            command.setId(words[1]);
            editor.execute(command);
            return command;
        }
    }

    private Command pageInterpreter(String[] words) {
        if(words.length == 1 ){
            Command command = new HavePage();
            editor.execute(command);
            return command;
        }
        Command command = new HavePage().setId(words[1]);
        editor.execute(command);
        return command;
    }
}
