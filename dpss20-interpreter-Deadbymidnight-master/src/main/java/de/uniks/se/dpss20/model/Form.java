package de.uniks.se.dpss20.model;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

public class Form extends DOMNode
{

   public static final String PROPERTY_content = "content";

   private String content;

   public String getContent()
   {
      return content;
   }

   public Form setContent(String value)
   {
      if (value == null ? this.content != null : ! value.equals(this.content))
      {
         String oldValue = this.content;
         this.content = value;
         firePropertyChange("content", oldValue, value);
      }
      return this;
   }

   public static final String PROPERTY_url = "url";

   private String url;

   public String getUrl()
   {
      return url;
   }

   public Form setUrl(String value)
   {
      if (value == null ? this.url != null : ! value.equals(this.url))
      {
         String oldValue = this.url;
         this.url = value;
         firePropertyChange("url", oldValue, value);
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

      result.append(" ").append(this.getContent());
      result.append(" ").append(this.getUrl());


      return result.substring(1);
   }

}