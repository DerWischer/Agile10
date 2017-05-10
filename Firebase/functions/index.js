const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functionshttps://codepad.remoteinterview.io/UCRXYNWFGM
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send('Hello from Firebase!');
// });

class Transaction {
  constructor(fromUser, toUser, amount) {
    this.fromUser = fromUser;
    this.toUser = toUser;
    this.amount = amount;
    this.settled = false;
  }
}

// Cloud Messaging Methods
// N0
exports.notifyShoppingListChanged = functions.database.ref('shopping-list/{group}/{transactionId}').onWrite(event => {
  var group = event.params.group;
  var transactionId = event.params.transactionId;
  if (event.data.previous.exists()) {
    var previousEntry = event.data.previous.val();
    if (previousEntry == null) {
      console.log('previousEntry is null');
    }
    var newEntry = event.data.val();
    if (newEntry == null) {
      const notification = {
        notification : {
          title: 'Removed or bought Shopping List Entry',
          body: name + ' was bought or removed from the shopping list of group ' + group,
          sound: 'default'
        }
      };
      return admin.messaging().sendToTopic(group, notification);
    }
    var wasBlocked = (previousEntry.blockedBy == null && newEntry.blockedBy != null);
    var wasUnblocked = (previousEntry.blockedBy != null && newEntry.blockedBy == null);
    var changedName = (previousEntry.name != newEntry.name);
    var changedAmount = (previousEntry.amount != newEntry.amount);
    var msgTitle = '';
    var msgBody = '';
    if (wasBlocked) {
      msgTitle = 'Entry (un)blocked';
      msgBody = 'entry ' + newEntry.name + ' in group ' + group + ' was blocked by ' + newEntry.blockedBy;
    }
    if (wasUnblocked) {
      msgTitle = 'Entry (un)blocked';
      msgBody = 'entry ' + newEntry.name + ' in group ' + group + ' was unblocked by ' + previousEntry.blockedBy;
    }
    if (changedAmount || changedName) {
      msgTitle = 'Entry details changed';
      msgBody = '' + previousEntry.amount + 'x ' + previousEntry.name + ' was changed to ' + newEntry.amount + 'x ' + newEntry.name;
    }
    if (msgTitle != '') {
      const notification = {
        notification : {
          title: msgTitle,
          body: msgBody,
          sound: 'default'
        }
      }
      return admin.messaging().sendToTopic(group, notification);
    }
  } else {
    var newEntry = event.data.val();
    var name = newEntry.name;
    var amount = newEntry.amount;

    const notification = {
      notification : {
        title: 'New Shopping List Entry',
        body: '' + amount + 'x ' + name + ' was added to the shopping list of group ' + group,
        sound: 'default'
      }
    };
    return admin.messaging().sendToTopic(group, notification);
  }
});
// N1
exports.notifyPendingSettlementRequest = functions.database.ref('/transactions/{group}/{transNum}/{fromUser}').onWrite(event => {
	console.log('Notify user ' + event.params.fromUser + ' in group ' + event.params.group + ' about pending settlement requests');
	// TODO N1
});
// N2 + N3
exports.notifyNewUserInGroup = functions.database.ref('GROUPUSER/{group}/{user}').onWrite(event => {
  if (event.data.previous.exists()) {
    return;
  } else {
    var group = event.params.group;
    var user = event.params.user;
    const notification = {
      notification : {
        title: 'New user in group',
        body: 'User ' + user + ' joined group ' + group,
        sound: 'default'
      }
    };
    return admin.messaging().sendToTopic(group, notification);
  }
	console.log('Notify group ' + event.params.group + ' that user ' + event.params.user + ' has joined the group');
	// TODO N2

	console.log('Notify user ' + event.params.user + ' that he or she was added to group ' + event.params.group);
	// TODO N3
});
// N4 Currently, users cannot subscribe to an item, so notifying them is not possible.

// N5 (is called from acceptPayment-function)
function notifyPaymentAccepted(fromUser, toUser){
	console.log('Notify user ' + fromUser + ' that his payment was accepted by ' + toUser);
	// TODO N5
};

exports.acceptPayment = functions.database.ref('/transactions/{group}/{transNum}').onWrite(event => {
		var settled = event.data.child('settled').val();
		if (settled == false){
			console.log('Payment not accepted');
			return;
		}

		var fromUser = event.data.child('fromUser').val();
		var toUser = event.data.child('toUser').val();
		var amount = event.data.child('amount').val();
		console.log('Transfering ' + amount + ' from ' + fromUser + ' to ' + toUser);

		var database = admin.database();
		database.ref('/GROUPUSER/' + event.params.group + '/' + toUser)
			.once('value').then(function(snapshot){
				var user = snapshot.val();

				var balance = user['BALANCE'] - amount;
				setBalance(toUser, event.params.group, balance);
				console.log('New balance of ' + toUser + ':' + balance);
			});

		database.ref('/GROUPUSER/' + event.params.group + '/' + fromUser)
			.once('value').then(function(snapshot){
				var user = snapshot.val();

				var balance = user['BALANCE'] + amount;
				setBalance(fromUser, event.params.group, balance);
				console.log('New balance of ' + fromUser + ':' + balance);
			});

		notifyPaymentAccepted(fromUser, toUser);
		// remove transaction
		return event.data.ref.set(null);
});

exports.updadeBalance = functions.database.ref('/GROUPUSER/{groupName}/{userName}/TRANSACTIONS/{transactionId}').onWrite(event =>
{
    var groupId = event.params.groupName;
    var userId = event.params.userName;
    var transactionId = event.params.transactionId;
    var price = event.data.val();
    var changeInPrice;

    console.log(groupId + ' ' + userId + ' ' + transactionId + ' ' + price);
    if (price != null) {
      var database = admin.database();

      database.ref('/GROUPUSER/' + groupId).once('value').then(function(snapshot) {
          var userList = snapshot.val();      // userList = all data about this group's users

          var keys = Object.keys(userList);   // keys = list of user names
          for (var i = 0; i < keys.length; i++) {
            var user = userList[keys[i]];     // user = all info about 1 user
            var balance = user['BALANCE'];
            if (balance == null || isNaN(balance)) {
              balance = 0;
            }
            console.log('user = ' + keys[i]  + ' old balance = ' + balance);

            if(keys.length == 0){  // 0 check!
                changeInPrice = 0;
            } else {
                changeInPrice = (price/keys.length);
            }
            console.log('user = ' + keys[i] + ' userId = ' + userId);
            if (keys[i] == userId) {
              balance = balance - changeInPrice + price;
            } else {
              balance = balance - changeInPrice;
            }
            setBalance(keys[i], groupId, balance);
            console.log('user = ' + keys[i] + ' new balance = ' + balance);
          }
      });
    }
});

exports.requestSettlements = functions.database.ref('/updateTransactions').onWrite(event => {
//exports.requestSettlements = functions.https.onRequest((request, response) => {
    var groupId = event.data.val();
    if (groupId == null || groupId == 'null' || groupId == 'false' || groupId == 'none') {
      return;
    }
    if (groupId != null) {
      solveSettlement(groupId);
    }
    admin.database().ref().update({updateTransactions : 'none'});
});


function setBalance(userId, groupId, newBalance){
    admin.database().ref('/GROUPUSER/' + groupId + '/' + userId).update({
      BALANCE : newBalance
    });
    //set the balance of the user in the group to newBalance
}

function solveSettlement(groupId){
//    console.log('starting solveSettlement ' + groupId);
    var database = admin.database();
    database.ref('/GROUPUSER/' + groupId).once('value').then(function(snapshot) {
/*      console.log('/GROUPUSER/' + 'groupId');
      console.log('console.log');
      console.log(snapshot);
      console.log(snapshot.toString());
      console.log(snapshot.val());*/
        var userList = snapshot.val();      // userList = all data about this group's users
        var keys = Object.keys(userList);   // keys = list of user names

        var users = [];
        var userBalances = [];
        var sum = 0;
        console.log('hello');
        console.log('keys.length = ' + keys.length);
        for (var i = 0; i < keys.length; i++) {
            var user = userList[keys[i]];
            var balance = user['BALANCE'];
            sum += balance;
            console.log('user = ' + keys[i] + '  balance = ' + balance);
            if (balance == null || isNaN(balance)) {
              user['BALANCE'] = 0;
              balance = 0;
            }
            if (Math.abs(balance) > 0.01) {
                users.push(keys[i]);
                userBalances.push(balance);
            }
        }
        if (Math.abs(sum) > 0.01 * keys.length) {
          console.error("sum is not zero");
          return;
        }

        var solvedTransactions = null;
        solvedTransactions = [];

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
//          console.log('userBalances.length = ' + userBalances.length);
          maxIndex = indexOfMax(userBalances);
          minIndex = indexOfMin(userBalances);
          max = userBalances[maxIndex];
          min = userBalances[minIndex];
//          console.log('max = ' + max);
//          console.log('min = ' + min);

          var transactionAmount = Math.min(max, Math.abs(min));

          if (transactionAmount != 0.0) {
            solvedTransactions.push(new Transaction(users[minIndex], users[maxIndex], transactionAmount));
          }
//          console.log('sender = ' + users[minIndex] + ' receiver = ' + users[maxIndex] + ' amount = ' + transactionAmount);
//          console.log('solvedTransactions = ' + JSON.stringify(solvedTransactions));
          userBalances[minIndex] += transactionAmount;
          userBalances[maxIndex] -= transactionAmount;

          if (Math.abs(userBalances[minIndex]) <= 0.01) {
//            console.log('removing user ' + users[minIndex]);
            userBalances.splice(minIndex, 1);
            users.splice(minIndex, 1);
          }
          if (Math.abs(userBalances[maxIndex]) <= 0.01) {
            if (minIndex < maxIndex) {
              maxIndex--;
            }
//            console.log('removing user ' + users[maxIndex]);
            userBalances.splice(maxIndex, 1);
            users.splice(maxIndex, 1);
          }
//          console.log('userBalances.length = ' + userBalances.length);
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
    admin.database().ref('/transactions/' + groupId).set(null);
    admin.database().ref('/transactions/' + groupId).set(transactions);
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
