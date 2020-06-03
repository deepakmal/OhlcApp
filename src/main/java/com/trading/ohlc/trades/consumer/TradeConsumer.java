package com.trading.ohlc.trades.consumer;

import java.time.Instant;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.trading.ohlc.model.Trade;
import com.trading.ohlc.model.TradeOHLCResponse;
import com.trading.ohlc.FSM.TradeFSM;
import com.trading.ohlc.trades.broker.TradeBroker;

/**
 * Trade consumer
 */
public class TradeConsumer  implements Runnable{

	private Logger logger = LogManager.getLogger(TradeConsumer.class);

	private TradeBroker tradeBroker;

	private TradeFSM tradeFSM;

	private int barNum = 1;

	private SimpMessagingTemplate simpMessagingTemplate;

	public TradeConsumer(TradeBroker tradeBroker,SimpMessagingTemplate simpMessagingTemplate){
		this.tradeBroker = tradeBroker;
		this.tradeFSM = new TradeFSM();
		this.simpMessagingTemplate = simpMessagingTemplate;
	}
	@Override
	public void run() {
		int count = 1;
		try {
			int interval = tradeBroker.getSubscription().getInterval();

			while(true) {
				Trade  trade = tradeBroker.get();
				//Check the last trade
				if(trade.getStkName().equals("lastTrade")) {
					break;
				}

				long unix_seconds = trade.getTimeStamp().longValueExact();
				Instant start = Instant.ofEpochMilli(unix_seconds / 1000000L);
				Long starting = start.getEpochSecond();

				TradeOHLCResponse ohldRes  = tradeFSM.getOhlc(trade, barNum);

				Instant end = start.plusSeconds(15);
				Long ending = end.getEpochSecond();
				Long total = ending - starting;
				if(total >= interval) {
					interval = tradeBroker.getSubscription().getInterval();
					long unix_secondsa = trade.getTimeStamp().longValueExact();
					start = Instant.ofEpochMilli(unix_secondsa / 1000000L);
					starting = start.getEpochSecond();

					ohldRes.setClose(ohldRes.getLow());
					barNum++;
					tradeFSM.restOld();
				}
				count++;
				logger.info("Ohlc response :{}",ohldRes);

				this.simpMessagingTemplate.convertAndSend("/topic/ohlc", ohldRes);
			}
			logger.info("count: {}",count );
			logger.info("Consumer finished its job; terminating.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


}
