package pos.presentation;

import pos.logic.Factura;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

/*Clase AbstractModel, base abstracta. Permite a los modelos gestionar y notificar cambios de propiedades a los listeners
* registrados*/

public abstract class AbstractModel
{
    /*Soporte para la gestion de cambios de propiedades*/
    protected PropertyChangeSupport propertyChangeSupport;

    public AbstractModel()
    {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    /*Añade un listener para recibir notificaciones cuando una propiedad cambia*/
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /*Elimina un listener previamente registrado, para dejar de recibir notificaciones de cambios en las propiedades*/
    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    /*Notifica a todos los listeners registrados de un cambio en una propiedad*/
    protected void firePropertyChange(String propertyName)
    {
        propertyChangeSupport.firePropertyChange(propertyName,null,null);
    }

    protected void firePropertyChange(String propertyName,Object oldValue,Object newValue)
    {
        propertyChangeSupport.firePropertyChange(propertyName,oldValue,newValue);
    }


}
