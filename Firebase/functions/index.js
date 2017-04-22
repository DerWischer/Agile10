var functions = require('firebase-functions');

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });



var functions = require('firebase-functions');

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functionshttps://codepad.remoteinterview.io/UCRXYNWFGM
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });


exports.uptadeBalance = functions.database.ref('agile10-768ca/GroupsAndUsers/').onWrite(event => {
    /*

    TODO:
    get path of event, from Path, get Group and User
    creat e

    */

    var data : functions.database.DeltaSnapshot = event.data;
    var adminRef : admin.database.Reference = data.adminRef;    // reference to the database location

    var groupId = 0; // TODO: get group ID from event
//    var price = 0; // TODO: get price from event
    var userId = 0; // TODO: get price from event
    var groupmembers[] = getGroupMembers(groupId);
/*    foreach (groupMember in groupmembers) {
        var records[[]] = []; // TODO: get records
        var balance = 0;
        foreach (record in records) {
            balance += record.amount;
        }

        // notify every concerned phone
    }*/
    for(int i = 0; i< groupmembers.lenght(); i++){
        var balance = 0;

        balance += sum(records[i])/groupmembers.length();
        for(int j = 0; j < groupmembers.lenght(); j++){
            if(!(j == i)){
                 balance -= sum(records[j])/groupmembers.length();
            }
        }
        // update balance in database
        groupmembers[i].setBalance(balance);
    }


});


exports.calculateTransaction = functions.database.ref('agile10-768ca/GroupsAndUsers/').onRequest((request, response) => {


});


transaction[] solvetransactions(users[user]){

    var solvedTransactions<transaction>[list];





/*

    //functions needed: getGroupMembers(groupId) , getBalance(userId,groupId) , setBalance(userId,newPrice,groupId)

    var groupmembers<user>[] = getGroupMembers(groupId);
    var changeInPrice = (price/groupmembers.length()); // May be 0, caution!

    foreach(member in groupmembers){
        if(member.Id == userId){
            var newPrice = getBalance(userId,groupId) - changeInPrice + price;
        }
        else{
            var newPrice = getBalance(userId,groupId) - changeInPrice;
        }
        setBalance(userId,newPrice,groupId);
    }




*/
