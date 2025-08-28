package org.esfe.stayloop.controladores;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.esfe.stayloop.modelos.Reserva;
import org.esfe.stayloop.servicios.interfaces.IReservaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    private final IReservaService reservaService;

    @Value("${stripe.public.key}")
    private String stripePublicKey;

    public CheckoutController(IReservaService reservaService) {
        this.reservaService = reservaService;
    }

    // Cambiado de POST a GET para que el redirect funcione
    @GetMapping("/create-checkout-session/{idReserva}")
    public String createCheckoutSession(@PathVariable Integer idReserva, Model model) throws StripeException {
        Reserva reserva = reservaService.buscarPorId(idReserva);

        if (reserva == null) {
            return "redirect:/reservas?error=notfound";
        }

        // Crear parámetros para Stripe Checkout
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8000/checkout/success?id=" + reserva.getId())
                .setCancelUrl("http://localhost:8000/checkout/cancel?id=" + reserva.getId())
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("usd")
                                                .setUnitAmount(reserva.getTotal().multiply(new java.math.BigDecimal(100)).longValue()) // en centavos
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName("Reserva Hotel - ID " + reserva.getId())
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .build();

        Session session = Session.create(params);

        // Redirigir directamente a Stripe Checkout
        return "redirect:" + session.getUrl();
    }

    @GetMapping("/success")
    public String paymentSuccess(@RequestParam Integer id, Model model) {
        Reserva reserva = reservaService.buscarPorId(id);
        if (reserva != null) {
            model.addAttribute("reserva", reserva);

            // Obtener usuario de la reserva
            model.addAttribute("usuarioActual", reserva.getUsuario());
        }
        return "checkout/success";
    }

    @GetMapping("/cancel")
    public String paymentCancel(@RequestParam Integer id, Model model) {
        Reserva reserva = reservaService.buscarPorId(id);
        if (reserva != null) {
            model.addAttribute("reserva", reserva);

            // También podrías mostrar el usuario si quieres personalizar más el mensaje
            model.addAttribute("usuarioActual", reserva.getUsuario());
        }
        return "checkout/cancel";
    }
}
