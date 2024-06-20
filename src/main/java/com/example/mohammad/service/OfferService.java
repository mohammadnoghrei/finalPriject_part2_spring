package com.example.mohammad.service;


import com.example.mohammad.exception.InvalidEntityException;
import com.example.mohammad.exception.NotFoundException;

import com.example.mohammad.model.Offer;

import com.example.mohammad.model.OrderStatus;
import com.example.mohammad.repository.OfferRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Comparator;
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
        List<Offer> allByOrder = offerRepository.findAllByOrder(orderService.findById(orderId));
        allByOrder.sort(Comparator.comparing(Offer::getPrice));
        allByOrder.sort(Comparator.comparing(a->a.getExpert().getAvgScore()));
        return allByOrder;
    }

    public Offer saveOffer(Offer newOffer, long orderId, String expertUsername){
        newOffer.setExpert(expertService.findByUsername(expertUsername));
        newOffer.setOrder((orderService.findById(orderId)));
        if (!validate(newOffer))
            throw new InvalidEntityException("the offer entity have invalid variable");
        if (!newOffer.getOrder().getOrderStatus().equals(OrderStatus.WAITING_FOR_CHOOSE_EXPERT))
            orderService.updateOrderStatusToWaitingForChooseExpert(orderId);
        return offerRepository.save(newOffer);
    }

    public Offer confirmOffer(long offerId){
        Offer offer=findById(offerId);
        if (offer.isConfirmed())
            throw new InvalidEntityException(String.format("the offer with %s must be not confirm",offerId));
        offer.setConfirmed(true);
        orderService.confirmOrder(offer);
        return offerRepository.save(offer);
    }

}
