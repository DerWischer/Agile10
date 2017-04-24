const functions = require('firebase-functions');

const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functionshttps://codepad.remoteinterview.io/UCRXYNWFGM
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });


exports.updadeBalance = functions.database.ref('/GROUPUSER/{groupName}/{userName}/TRANSACTIONS/{transactionId}').onWrite(event =>
{
    var groupId = event.params.groupName;
    var userId = event.params.userName;
    var transactionId = event.params.transactionId;
    var price = event.data.val();
    var changeInPrice;

    console.log(groupId + " " + userId + " " + transactionId + " " + price);
    if (price != null) {
      var database = admin.database();

      database.ref('/GROUPUSER/' + groupId).once('value').then(function(snapshot) {
          var userList = snapshot.val();      // userList = all data about this group's users

          var keys = Object.keys(userList);   // keys = list of user names
          for (var i = 0; i < keys.length; i++) {
            var user = userList[keys[i]];     // user = all info about 1 user
            var balance = user["BALANCE"];
            console.log("user = " + keys[i]  + " old balance = " + balance);

            if(keys.length == 0){  // 0 check!
                changeInPrice = 0;
            } else {
                changeInPrice = (price/keys.length);
            }
            console.log("user = " + keys[i] + " userId = " + userId);
            if (keys[i] == userId) {
              balance = balance - changeInPrice + price;
            } else {
              balance = balance - changeInPrice;
            }
            setBalance(keys[i], groupId, balance);
            console.log("user = " + keys[i] + " new balance = " + balance);
          }
      });
    }
});

function setBalance(userId, groupId, newBalance){
    admin.database().ref("/GROUPUSER/" + groupId + "/" + userId).update({
      BALANCE : newBalance
    });
    //set the balance of the user in the group to newBalance
}
