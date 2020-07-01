package de.uniks.se.dpss20.model;

import de.uniks.se.dpss20.Editor.MockupEditor;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

public class HaveLine extends Command
{
   @Override
   public void run(MockupEditor editor) {
      DOMNode line =  editor.getOrCreate(Line.class, getId());
      DOMNode page = editor.getOrCreate(Page.class, getParentId());
      page.withKids(line);
   }

   @Override
   public void undo(MockupEditor editor) {
      DOMNode line =  editor.getOrCreate(Line.class, getId());
      DOMNode page = editor.getOrCreate(Page.class, getParentId());
      page.withoutKids(line);
   }

   public static final String PROPERTY_parentId = "parentId";

   private String parentId;

   public String getParentId()
   {
      return parentId;
   }

   public HaveLine setParentId(String value)
   {
      if (value == null ? this.parentId != null : ! value.equals(this.parentId))
      {
         String oldValue = this.parentId;
         this.parentId = value;
         firePropertyChange("parentId", oldValue, value);
      }
      return this;
   }

   protected PropertyChangeSupport listeners = null;

   public boolean firePropertyChange(String propertyName, Object oldValue, Object newValue)
   {
      if (listeners != null)
      {
         listeners.firePropertyChange(propertyName, oldValue, newValue);
         return true;
      }
      return false;
   }

   public boolean addPropertyChangeListener(PropertyChangeListener listener)
   {
      if (listeners == null)
      {
         listeners = new PropertyChangeSupport(this);
      }
      listeners.addPropertyChangeListener(listener);
      return true;
   }

   public boolean addPropertyChangeListener(String propertyName, PropertyChangeListener listener)
   {
      if (listeners == null)
      {
         listeners = new PropertyChangeSupport(this);
      }
      listeners.addPropertyChangeListener(propertyName, listener);
      return true;
   }

   public boolean removePropertyChangeListener(PropertyChangeListener listener)
   {
      if (listeners != null)
      {
         listeners.removePropertyChangeListener(listener);
      }
      return true;
   }

   public boolean removePropertyChangeListener(String propertyName,PropertyChangeListener listener)
   {
      if (listeners != null)
      {
         listeners.removePropertyChangeListener(propertyName, listener);
      }
      return true;
   }

   @Override
   public String toString()
   {
      StringBuilder result = new StringBuilder();

      result.append(" ").append(this.getParentId());


      return result.substring(1);
   }

}