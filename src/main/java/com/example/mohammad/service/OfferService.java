package com.example.mohammad.service;


import com.example.mohammad.exception.InvalidEntityException;
import com.example.mohammad.exception.NotFoundException;
import com.example.mohammad.exception.StatusException;
import com.example.mohammad.model.Offer;
import com.example.mohammad.model.Order;
import com.example.mohammad.model.OrderStatus;
import com.example.mohammad.repository.OfferRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OfferService  {
    private final OfferRepository offerRepository;
    private final ExpertService expertService;
    private final OrderService orderService;
   ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
   Validator validator = validatorFactory.getValidator();

//    private final Validator validator ;
    public boolean validate(Offer entity) {

        Set<ConstraintViolation<Offer>> violations = validator.validate(entity);
        if (violations.isEmpty())
            return true;
        else {
            System.out.println("Invalid user data found:");
            for (ConstraintViolation<Offer> violation : violations) {
                System.out.println(violation.getMessage());
            }
            return false;
        }
    }

    public Offer findById(Long id){
        return offerRepository.findById(id).orElseThrow(()-> new NotFoundException(String.format("the entity with %s not found",id)));
    }

    public List<Offer> findAllOfferByOrder(long orderId){
        return offerRepository.findAllByOrder(orderService.findById(orderId));
    }

    public Offer saveOffer(Offer newOffer, long orderId, String expertUsername){
        Offer offer=newOffer;
        offer.setExpert(expertService.findByUsername(expertUsername));
        offer.setOrder((orderService.findById(orderId)));
        if (!validate(offer))
            throw new InvalidEntityException("the offer entity have invalid variable");
        if (!offer.getOrder().getOrderStatus().equals(OrderStatus.WAITING_FOR_CHOOSE_EXPERT))
            orderService.updateOrderStatusToWaitingForChooseExpert(orderId);
        return offerRepository.save(offer);
    }

    public Offer confirmOffer(long offerId){
        Offer offer=findById(offerId);
        if (offer.isConfirmed()==true)
            throw new InvalidEntityException(String.format("the offer with %s must be not confirm",offerId));
        offer.setConfirmed(true);
        orderService.confirmOrder(offer.getOrder(),offer.getExpert().getUsername());
        return offerRepository.save(offer);
    }

}
