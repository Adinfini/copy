package de.uniks.se.dpss20.model;

import de.uniks.se.dpss20.Editor.MockupEditor;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

public class HaveLabel extends Command
{
   @Override
   public void run(MockupEditor editor) {
      Label label = (Label) editor.getOrCreate(Label.class,getId());
      DOMNode line = editor.getOrCreate(Line.class, getParentId());
      line.withKids(label);
      label.setPrompt(getPrompt());
      label.setValue(getValue());
   }

   @Override
   public void undo(MockupEditor editor) {
      Label label = (Label) editor.getOrCreate(Label.class,getId());
      DOMNode line = editor.getOrCreate(Line.class, getParentId());
      line.withoutKids(label);
   }

   public static final String PROPERTY_parentId = "parentId";

   private String parentId;

   public String getParentId()
   {
      return parentId;
   }

   public HaveLabel setParentId(String value)
   {
      if (value == null ? this.parentId != null : ! value.equals(this.parentId))
      {
         String oldValue = this.parentId;
         this.parentId = value;
         firePropertyChange("parentId", oldValue, value);
      }
      return this;
   }

   public static final String PROPERTY_prompt = "prompt";

   private String prompt;

   public String getPrompt()
   {
      return prompt;
   }

   public HaveLabel setPrompt(String value)
   {
      if (value == null ? this.prompt != null : ! value.equals(this.prompt))
      {
         String oldValue = this.prompt;
         this.prompt = value;
         firePropertyChange("prompt", oldValue, value);
      }
      return this;
   }

   public static final String PROPERTY_value = "value";

   private String value;

   public String getValue()
   {
      return value;
   }

   public HaveLabel setValue(String value)
   {
      if (value == null ? this.value != null : ! value.equals(this.value))
      {
         String oldValue = this.value;
         this.value = value;
         firePropertyChange("value", oldValue, value);
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
      result.append(" ").append(this.getPrompt());
      result.append(" ").append(this.getValue());


      return result.substring(1);
   }

}