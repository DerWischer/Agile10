//var functions = require('firebase-functions');
const functions = require('firebase-functions');

const admin = require('firebase-admin');
//const firebase = require('firebase');
admin.initializeApp(functions.config().firebase);

//var database = firebase.database();

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functionshttps://codepad.remoteinterview.io/UCRXYNWFGM
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });


exports.updadeBalance = functions.database.ref('/GROUPUSER/{groupName}/{userName}/TRANSACTIONS/{transactionId}').onWrite(event =>
{

//    var data : functions.database.DeltaSnapshot = event.data;
//    var adminRef : admin.database.Reference = data.adminRef;    // reference to the database location

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
//          console.log(JSON.stringify(userList));

          var keys = Object.keys(userList);   // keys = list of user names
          for (var i = 0; i < keys.length; i++) {
//            console.log(keys[i]);
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

/*    var groupmembers[] = getGroupMembers(groupId);

    if(groupmembers.length() == 0){  // 0 check!
        changeInPrice = 0;
    }else{
        changeInPrice = (price/groupmembers.length());
    }

    var member;
    for(member of groupmembers){
        if(member.Id == userId){
            var newPrice = getBalance(userId,groupId) - changeInPrice + price;
        }
        else{
            var newPrice = getBalance(userId,groupId) - changeInPrice;
        }
        setBalance(userId,groupId,newPrice);
    }*/


});

/*
exports.calculateTransaction = functions.database.ref('agile10-768ca/GroupsAndUsers/').onRequest((request, response) => {

    // TODO!
});

float getBalance(int userId, int groupId){

    //return the balance of the user in the group

    return 0;
}*/
function setBalance(userId, groupId, newBalance){
    admin.database().ref("/GROUPUSER/" + groupId + "/" + userId).update({
      BALANCE : newBalance
    });
    //set the balance of the user in the group to newPrice
}/*
function writeUserData(userId, name, email, imageUrl) {
  firebase.database().ref('users/' + userId).set({
    username: name,
    email: email,
    profile_picture : imageUrl
  });
}*/
/*
user[] getGroupMembers(int groupId){

    // return the members of group
    return [];
}*/


/*

    //functions needed: getGroupMembers(groupId) , getBalance(userId,groupId) , setBalance(userId,newPrice,groupId)

    var groupmembers<user>[] = getGroupMembers(groupId);






*/
