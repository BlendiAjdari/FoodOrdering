package org.foodordering.service;

import org.foodordering.domain.Card;

import java.util.List;

public interface CardService {
    List<Card> getCards() throws Exception;
    Card getCardById(int id) throws Exception;
    void addCard(Card card) throws Exception;
    void updateCard(Card card) throws Exception;
    void deleteCard(Card card) throws Exception;
    boolean cardCheck(Card card) throws Exception;
}
