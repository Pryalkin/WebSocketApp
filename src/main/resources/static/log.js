console.log("Привет");
function editMessage(id){
    document.getElementById("formEditMessage" + id).style.display = "block";
    document.getElementById("formEditMessage" + id).action = "/users/" + document.getElementById("senderId").value + "/" + document.getElementById("recipientId").value;
}

document.getElementsByName("send").forEach(el => {
    if (el.innerText ==  document.getElementById("senderId").value){
        el.innerText = "I:";
    }   else el.innerText = "Friend:";
});

var col = false;
function edit(id) {
    if (document.getElementById("message" + id).style.display == "none") {
        document.getElementById("message" + id).style.display = "block";
        document.getElementById("edit" + id).style.display = "block";
    } else {
        document.getElementById("message" + id).style.display = "none"
        document.getElementById("edit" + id).style.display = "none";
        document.getElementById("formEditMessage" + id).style.display = "none";
    }
}
