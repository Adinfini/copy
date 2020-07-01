package de.uniks.se.dpss20.model;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

public class Button extends DOMNode
{

   public static final String PROPERTY_prompt = "prompt";

   private String prompt;

   public String getPrompt()
   {
      return prompt;
   }

   public Button setPrompt(String value)
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

   public Button setValue(String value)
   {
      if (value == null ? this.value != null : ! value.equals(this.value))
      {
         String oldValue = this.value;
         this.value = value;
         firePropertyChange("value", oldValue, value);
      }
      return this;
   }

   public static final String PROPERTY_script = "script";

   private String script;

   public String getScript()
   {
      return script;
   }

   public Button setScript(String value)
   {
      if (value == null ? this.script != null : ! value.equals(this.script))
      {
         String oldValue = this.script;
         this.script = value;
         firePropertyChange("script", oldValue, value);
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

      result.append(" ").append(this.getPrompt());
      result.append(" ").append(this.getValue());
      result.append(" ").append(this.getScript());


      return result.substring(1);
   }

}