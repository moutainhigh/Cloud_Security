var webSocketConnecting = false;
var socket = null;
function connect() {
    if('WebSocket' in window){
          console.log('Websocket supported');
          socket = new WebSocket("ws:/localhost:8080/cloudsec/websocket");
          console.log('Connection attempted');
          socket.onopen = function(){
               console.log('Connection open!');
               webSocketConnecting = true;
          };
          socket.onclose = function(){
              console.log('Disconnecting connection');
          };
          socket.onmessage = function (evt){
              var received_msg = evt.data;
              console.log(received_msg);
              console.log('message received!');
              showMessage(received_msg);
          };
     }else{
       console.log('Websocket not supported');
     }
}
connect();
function sendName() {
    socket.send(JSON.stringify({'message': "session"}));
}
function showMessage(message) {
	receiveMessage(message);
}