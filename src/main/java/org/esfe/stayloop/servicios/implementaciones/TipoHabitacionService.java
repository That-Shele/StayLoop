package org.esfe.stayloop.servicios.implementaciones;

import org.esfe.stayloop.modelos.TipoHabitacion;
import org.esfe.stayloop.repositorios.ITipoHabitacionRepository;
import org.esfe.stayloop.servicios.interfaces.ITipoHabitacionService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

 @Service
 public class TipoHabitacionService  implements ITipoHabitacionService {

     @Autowired
     private ITipoHabitacionRepository tipoHabitacionRepository;

     @Override
     public  List<TipoHabitacion> obtenerTodos() {
         return  tipoHabitacionRepository.findAll();
     }

     @Override
     public  List<TipoHabitacion> buscarPorCantidadHabitacion(byte cantHab) {
         return  tipoHabitacionRepository.findByCantHabGreaterThanEqual(cantHab);
     }

     @Override
     public  List<TipoHabitacion> buscarPorCantidadPersona (byte cantPersonas) {
         return  tipoHabitacionRepository.findByCantPersonasGreaterThanEqual(cantPersonas);
     }

     @Override
     public  List<TipoHabitacion> buscarPorCosto (BigDecimal costo) {
         return  tipoHabitacionRepository.findByCostoGreaterThan(costo);
     }


     @Override
     public  TipoHabitacion buscarPorId(Integer id) {
         return  tipoHabitacionRepository.findById(id).get();
     }

     @Override
     public   TipoHabitacion crearOEditar (TipoHabitacion tipoHabitacion) {
         return  tipoHabitacionRepository.save(tipoHabitacion);
     }

     @Override
     public void  eliminarPorId (Integer id) {
          tipoHabitacionRepository.deleteById(id);
     }
}