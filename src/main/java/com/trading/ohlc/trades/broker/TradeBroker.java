package com.trading.ohlc.trades.broker;

import java.util.concurrent.ArrayBlockingQueue;

import com.trading.ohlc.model.Subscription;
import com.trading.ohlc.model.Trade;

/**
 * Message broker in pub-sub
*/
public class TradeBroker {
	
	public ArrayBlockingQueue<Trade> queue = new ArrayBlockingQueue<>(1);
	private Subscription subscription;
	
	public TradeBroker(Subscription subscription){
		this.subscription = subscription;
	}
 
    public void put(Trade data) throws InterruptedException
    {
        this.queue.put(data);
    }
 
    public Trade get() throws InterruptedException
    {
        return this.queue.take();
    }
    
    public Subscription getSubscription()
    {
        return this.subscription;
    }
}
