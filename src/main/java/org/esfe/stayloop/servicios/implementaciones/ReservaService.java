package org.esfe.stayloop.servicios.implementaciones;

import org.esfe.stayloop.modelos.Reserva;
import org.esfe.stayloop.repositorios.IReservaRepository;
import org.esfe.stayloop.servicios.interfaces.IReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaService implements IReservaService {

    @Autowired
    private IReservaRepository reservaRepository;

    @Override
    public List<Reserva> obtenerTodas() {
        return reservaRepository.findAll();
    }

    @Override
    public Page<Reserva> buscarPaginados(
            LocalDateTime fechaInicioStart,
            LocalDateTime fechaInicioEnd,
            LocalDateTime fechaFinStart,
            LocalDateTime fechaFinEnd,
            Integer idUsuario,
            Integer idHotel,
            BigDecimal total,
            Pageable pageable
    ) {
        return reservaRepository.findByFechaInicioBetweenAndFechaFinBetweenAndIdUsuarioAndIdHotelAndTotalGreaterThanOrderByIdDesc(
                fechaInicioStart,
                fechaInicioEnd,
                fechaFinStart,
                fechaFinEnd,
                idUsuario,
                idHotel,
                total,
                pageable
        );
    }

    @Override
    public Reserva buscarPorId(Integer id) {
        Optional<Reserva> reserva = reservaRepository.findById(id);
        return reserva.orElse(null);
    }

    @Override
    public Reserva crearOEditar(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    @Override
    public void eliminarPorId(Integer id) {
        reservaRepository.deleteById(id);
    }
}
