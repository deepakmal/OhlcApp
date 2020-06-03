package com.trading.ohlc.controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.trading.ohlc.model.Subscription;
import com.trading.ohlc.trades.broker.TradeBroker;
import com.trading.ohlc.trades.consumer.TradeConsumer;
import com.trading.ohlc.trades.producer.TradeProducer;

/**
 * Websocket controller to check the trading
 */
@Controller
public class TradeController {	 
	 
	@Autowired
	private SimpMessagingTemplate  simpMessagingTemplate;
	 
	@MessageMapping("/trades")
	 public void getOhlc(final Subscription subscription)  {
			TradeBroker tradeBroker = new TradeBroker(subscription);
			TradeProducer producer = new TradeProducer(tradeBroker);
			TradeConsumer consumer = new TradeConsumer(tradeBroker,simpMessagingTemplate);
			ExecutorService service = Executors.newFixedThreadPool(2);
			service.execute(producer);
			service.execute(consumer);
	 }	
}