package de.uniks.se.dpss20.model;

import de.uniks.se.dpss20.Editor.MockupEditor;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

public class HaveForm extends Command
{

   @Override
   public void run(MockupEditor editor) {
      Form form = (Form) editor.getOrCreate(Form.class,getId());
      DOMNode page= editor.getOrCreate(Page.class, getParentId());
      page.withKids(form);
      form.setContent(getContent());
      form.setUrl(getUrl());
   }

   public static final String PROPERTY_parentId = "parentId";

   private String parentId;

   public String getParentId()
   {
      return parentId;
   }

   public HaveForm setParentId(String value)
   {
      if (value == null ? this.parentId != null : ! value.equals(this.parentId))
      {
         String oldValue = this.parentId;
         this.parentId = value;
         firePropertyChange("parentId", oldValue, value);
      }
      return this;
   }

   public static final String PROPERTY_content = "content";

   private String content;

   public String getContent()
   {
      return content;
   }

   public HaveForm setContent(String value)
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

   public HaveForm setUrl(String value)
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

      result.append(" ").append(this.getParentId());
      result.append(" ").append(this.getContent());
      result.append(" ").append(this.getUrl());


      return result.substring(1);
   }

}