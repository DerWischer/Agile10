const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functionshttps://codepad.remoteinterview.io/UCRXYNWFGM
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });

class Transaction {
  constructor(fromUser, toUser, amount) {
    this.fromUser = fromUser;
    this.fromUser = fromUser;
    this.amount = amount;
    this.settled = false;
  }
}


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

exports.requestSettlements = functions.https.onRequest((request, response) => {
    var groupId = request.query.groupId;
//    console.log("groupId = " + groupId);
    if (groupId != null) {
      solveSettlement(groupId);
//      var transactions = solveSettlement(groupId);
//      console.log("transactions = " + transactions);
    }

    response.end();
});


function setBalance(userId, groupId, newBalance){
    admin.database().ref("/GROUPUSER/" + groupId + "/" + userId).update({
      BALANCE : newBalance
    });
    //set the balance of the user in the group to newBalance
}

function solveSettlement(groupId){
//    console.log("starting solveSettlement " + groupId);
    var database = admin.database();
    database.ref('/GROUPUSER/' + groupId).once('value').then(function(snapshot) {
//        var groupId = events.params.groupId;
        console.log("group id = " + groupId);
        var userList = snapshot.val();      // userList = all data about this group's users
        var keys = Object.keys(userList);   // keys = list of user names

        var users = [];
        var userBalances = [];
        for (var i = 0; i < keys.length; i++) {
            var user = userList[keys[i]];
            var balance = user["BALANCE"];
            if (Math.abs(balance) > 0.01) {
                users.push(keys[i]);
                userBalances.push(balance);
//                console.log("adding " + keys[i] + " with balance " + balance);
            }
        }

        var solvedTransactions = [];

/*        for (var j = 0; j < userBalances.length; j++){    // check for users with same absolute balance to minimize transactions
          var jBalance = userBalances[j];
          for(var i = 0; i < userBalances.length; i++){
            var iBalance = userBalances[i];
            if(Math.abs(jBalance) == Math.abs(iBalance) && !(j == i) && !(jBalance == 0) && jBalance != iBalance){
                if(jBalance > iBalance){
//                    solvedTransactions[solvedTransactions.length + 1] = new Transaction(users[i], users[j], jBalance);
                    solvedTransactions.push(new Transaction(users[i], users[j], jBalance));
                    userBalances.splice(i, 1);
                    userBalances.splice(j, 1);
                    users.splice(i, 1);
                    users.splice(j, 1);
                } else if (iBalance > jBalance) {
//                    solvedTransactions[solvedTransactions.length + 1] = new Transaction(users[j], users[i], jBalance);
                    solvedTransactions.push(new Transaction(users[j], users[i], iBalance));
                    userBalances.splice(i, 1);
                    userBalances.splice(j, 1);
                    users.splice(i, 1);
                    users.splice(j, 1);
                }
            }
          }
        }*/
        // while not done {
        //   get maximum
        //   get minimum
        //   pay min(max, abs(min)) from minimum to maximum
        // }
        var max, min, maxIndex, minIndex;
        while (userBalances.length > 0) {
          console.log("userBalances.length = " + userBalances.length);
          maxIndex = indexOfMax(userBalances);
          minIndex = indexOfMin(userBalances);
          max = userBalances[maxIndex];
          min = userBalances[minIndex];
          console.log("max = " + max);
          console.log("min = " + min);

          var transactionAmount = Math.min(max, Math.abs(min));

          solvedTransactions.push(new Transaction(users[minIndex], users[maxIndex], transactionAmount));
          console.log("sender = " + users[minIndex] + " receiver = " + users[maxIndex] + " amount = " + transactionAmount);
          console.log("solvedTransactions = " + JSON.stringify(solvedTransactions));
          userBalances[minIndex] += transactionAmount;
          userBalances[maxIndex] -= transactionAmount;

          if (Math.abs(userBalances[minIndex]) <= 0.01) {
            console.log("removing user " + users[minIndex]);
            userBalances.splice(minIndex, 1);
            users.splice(minIndex, 1);
          }
          if (Math.abs(userBalances[maxIndex]) <= 0.01) {
            if (minIndex < maxIndex) {
              maxIndex--;
            }
            console.log("removing user " + users[maxIndex]);
            userBalances.splice(maxIndex, 1);
            users.splice(maxIndex, 1);
          }
          console.log("userBalances.length = " + userBalances.length);
        }
        saveTransactions(groupId, solvedTransactions);
//        return solvedTransactions;
/*        for (var j = 0; j < userBalances.length; j++){
          if(userBalances[j] == Math.min.apply(Math, userBalances)){
            for (var i = 0; i < userBalances.length; i++){
                if(userBalances[i] == Math.max.apply(Math, userBalances) && !(j == i) && !(jBalance == 0)){
                    solvedTransactions[solvedTransactions.length + 1] = new Transaction(users[j], users[i], jBalance);
                    userBalances[i] = 0;
                    userBalances[j] = 0;
                }
            }
        }
      }*/
    });
}

function saveTransactions(groupId, transactions) {
    var database = admin.database();
    admin.database().ref("/transactions/" + groupId).set(transactions);
}

function indexOfMax(arr) {
    if (arr.length === 0) {
        return -1;
    }

    var max = arr[0];
    var maxIndex = 0;

    for (var i = 1; i < arr.length; i++) {
        if (arr[i] > max) {
            maxIndex = i;
            max = arr[i];
        }
    }

    return maxIndex;
}
function indexOfMin(arr) {
    if (arr.length === 0) {
        return -1;
    }

    var min = arr[0];
    var minIndex = 0;

    for (var i = 1; i < arr.length; i++) {
        if (arr[i] < min) {
            minIndex = i;
            min = arr[i];
        }
    }

    return minIndex;
}
