var stompClient = null;
var name = "Pavel"

// function setConnected(connected) {
//     $("#connect").prop("disabled", connected);
//     $("#disconnect").prop("disabled", !connected);
//     if (connected) {
//         $("#conversation").show();
//     }
//     else {
//         $("#conversation").hide();
//     }
//     $("#greetings").html("");
// }

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        // setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function(payload) {
                JSON.parse(payload.body).model.messages.forEach(el => {
                    if (document.getElementById("messageId"+el.id) == null){
                        var divBlock1 = document.createElement("div");
                            divBlock1.className = "col-2";
                            var divBlock2 = document.createElement("div");
                            divBlock2.className = "col-10";
                        let a1 = divBlock1.innerHTML;
                        let b1 = divBlock1.innerHTML;
                            let a2 = divBlock2.innerHTML;
                            let b2 = divBlock2.innerHTML;
                            let c2 = divBlock2.innerHTML;
                            let d2 = divBlock2.innerHTML;
                            a1 = "<span name='send'>" + el.messageRoom.senderId + "</span>";
                            b1 = "<input type='hidden' id='messageId" + el.id + "' value='" + el.id + "'>"
                            a2 = "<span onclick='edit(" + el.id + ")' style='font-weight: bolder'>" + el.date + " Тема:" + el.topic + "</span>";
                            b2 = "<p id='message" + el.id + "' style='display: none; font-style: italic'>" + el.message + "</p>";
                            c2 = "<input type='hidden' id='editMessage" + el.id + "' value='" + el.message + "'>";
                            d2 = "<button onclick='editMessage(" + el.id + ")' type='submit' class='btn btn-primary' id='edit" + el.id + "' style='display: none'>Edit</button>";
                            divBlock1.innerHTML = a1 + b1;
                            divBlock2.innerHTML = a2 + b2 + c2 + d2;
                            document.getElementById("divMessages").append(divBlock1);
                            document.getElementById("divMessages").append(divBlock2);
                    }
                    });
            });

            // document.getElementById("divMessages").innerText = payload;
            // showGreeting(JSON.parse(greeting.body).content);
        });
    };


function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName(senderId, recipientId) {
    stompClient.send("/app/hello", {}, JSON.stringify({id: this.id, senderId: senderId,  recipientId: recipientId}));
}
// JSON.stringify({id: this.id, message: message,  date: this.date, topic: topic, messageRoom: this.messageRoom} )
// function showGreeting(message) {
//     $("#greetings").append("<tr><td>" + message + "</td></tr>");
// }
//
// $(function () {
//     $("form").on('submit', function (e) {
//         e.preventDefault();
//     });
//     $( "#connect" ).click(function() { connect(); });
//     $( "#disconnect" ).click(function() { disconnect(); });
//     $( "#send" ).click(function() { sendName(); });
// });

connect()
// setTimeout(() => {
//     console.log("мир");
//     sendName();
//     },
//     10000);


setInterval(() => {
    sendName(document.getElementById("senderId").value, document.getElementById("recipientId").value, )
}, 5000);

