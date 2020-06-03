#### Read Me
Spring boot Websocket project with Maven
#### Build
``mvn clean install``
#### How to start the application
* Go to ``OhlcApplication.java`` and run using java main method
#### For handshaking
* ``ws://localhost:8080/trading/websocket``
#### Websocker Configuration to check the end-points configuration
* ``WebSocketConfig.java``
#### Websocket Controller
* ``TradeController.java`` have websocket endpoint /app/trades
#### Subscription Endpoint
* /topic/ohlc
#### How to test
**Step 1:** Start the ``OhlcApplication.java`` application using java main method.
**Step 2:** Start the test ``WebsocketClient.java`` Client using java main method.
#### How its works
* ``TradeStompSessionHandler.java`` is the test handler which contains dummy ``Subscription.java`` and handleFrame method show the subscribe data (``TradeOHLCResponse.java``).

