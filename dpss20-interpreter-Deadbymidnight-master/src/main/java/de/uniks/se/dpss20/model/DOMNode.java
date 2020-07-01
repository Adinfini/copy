package de.uniks.se.dpss20.model;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

public class DOMNode 
{

   public static final String PROPERTY_id = "id";

   private String id;

   public String getId()
   {
      return id;
   }

   public DOMNode setId(String value)
   {
      if (value == null ? this.id != null : ! value.equals(this.id))
      {
         String oldValue = this.id;
         this.id = value;
         firePropertyChange("id", oldValue, value);
      }
      return this;
   }

   public static final java.util.ArrayList<DOMNode> EMPTY_kids = new java.util.ArrayList<DOMNode>()
   { @Override public boolean add(DOMNode value){ throw new UnsupportedOperationException("No direct add! Use xy.withKids(obj)"); }};

   public static final String PROPERTY_kids = "kids";

   private java.util.ArrayList<DOMNode> kids = null;

   public java.util.ArrayList<DOMNode> getKids()
   {
      if (this.kids == null)
      {
         return EMPTY_kids;
      }

      return this.kids;
   }

   public DOMNode withKids(Object... value)
   {
      if(value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withKids(i);
            }
         }
         else if (item instanceof DOMNode)
         {
            if (this.kids == null)
            {
               this.kids = new java.util.ArrayList<DOMNode>();
            }
            if ( ! this.kids.contains(item))
            {
               this.kids.add((DOMNode)item);
               ((DOMNode)item).setParentId(this);
               firePropertyChange("kids", null, item);
            }
         }
         else throw new IllegalArgumentException();
      }
      return this;
   }


   public DOMNode withoutKids(Object... value)
   {
      if (this.kids == null || value==null) return this;
      for (Object item : value)
      {
         if (item == null) continue;
         if (item instanceof java.util.Collection)
         {
            for (Object i : (java.util.Collection) item)
            {
               this.withoutKids(i);
            }
         }
         else if (item instanceof DOMNode)
         {
            if (this.kids.contains(item))
            {
               this.kids.remove((DOMNode)item);
               ((DOMNode)item).setParentId(null);
               firePropertyChange("kids", item, null);
            }
         }
      }
      return this;
   }

   public static final String PROPERTY_parentId = "parentId";

   private DOMNode parentId = null;

   public DOMNode getParentId()
   {
      return this.parentId;
   }

   public DOMNode setParentId(DOMNode value)
   {
      if (this.parentId != value)
      {
         DOMNode oldValue = this.parentId;
         if (this.parentId != null)
         {
            this.parentId = null;
            oldValue.withoutKids(this);
         }
         this.parentId = value;
         if (value != null)
         {
            value.withKids(this);
         }
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

      result.append(" ").append(this.getId());


      return result.substring(1);
   }

   public void removeYou()
   {
      this.setParentId(null);

      this.withoutKids(this.getKids().clone());


   }

}