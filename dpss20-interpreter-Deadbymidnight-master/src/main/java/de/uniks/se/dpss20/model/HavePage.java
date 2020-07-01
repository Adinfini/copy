package de.uniks.se.dpss20.model;

import de.uniks.se.dpss20.Editor.MockupEditor;

public class HavePage extends Command
{
    @Override
    public void run(MockupEditor editor) {
        editor.getOrCreate(Page.class, getId());

    }

    @Override
    public void undo(MockupEditor editor) {
        Page page = (Page) editor.getOrCreate(Page.class, getId());
        page.removeYou();
    }

}