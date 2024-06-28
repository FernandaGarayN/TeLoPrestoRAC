const destination = '/user/{username}/queue/session-timeout';

const stompClientSubscribeCallback = function(message) {
    let timeToExpire = JSON.parse(message.body);
    console.log('Session will expire in ' + timeToExpire + ' seconds.');
}

const socket = new SockJS('/session-timeout');
const stompClient = Stomp.over(socket);

stompClient.connect({}, function(frame) {
    console.log('Connected: ', frame.command);
    console.log('Headers: ', frame.headers)
    let realDestination = destination.replace('{username}', frame.headers['user-name']);
    stompClient.subscribe(realDestination, stompClientSubscribeCallback, {
        'ack': 'client',
        'id': 'sub-0'
    });
}, function(error) {
    console.log('STOMP error', error);
});

stompClient.debug = function(str) {
    console.log('STOMP: ' + str);
};