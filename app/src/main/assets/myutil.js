var id; // geting id from link
var flag_mute = true;
var flag_talk = true;
var flag_rock = true;
var user_id;
$(function() {
  id = location.hash.substring(1,7);
  console.log(id);
  console.log( "ready!" );
});

function load_all()
{
  drop1();
  $("#f_mute").fadeTo(300, 0.4);
  $("#f_mute1").fadeTo(300, 0.4);
  $("#f_talk").fadeTo(300, 0.4);
  $("#f_talk1").fadeTo(300, 0.4);
  $("#f_rock").fadeTo(300, 0.4);
  $("#f_rock1").fadeTo(300, 0.4);
  $("#rightbar").show();
  checkSetDatabase();
}

function drop1()
{
  $(".fa.fa-chevron-up.fa-2x").toggleClass("rotate-90");
  $( ".action-bar" ).stop().slideToggle();
}


function f_mute() {
  if(flag_mute==true)
  {
    flag_mute = false;
    $("#f_mute").fadeTo(300, 1.0);
    $("#f_mute1").fadeTo(300, 1.0);
    document.getElementById("remoteVideo").muted = true;
  }
  else {
    flag_mute = true;
    $("#f_mute").fadeTo(300, 0.4);
    $("#f_mute1").fadeTo(300, 0.4);
    document.getElementById("remoteVideo").muted = false;
  }
}


function f_talk() {
  if(flag_talk==true)
  {
    flag_talk = false;
    $("#f_talk").fadeTo(300, 1.0);
    $("#f_talk1").fadeTo(300, 1.0);
    UpdateDbData("noty",4);
  }
  else {
    flag_talk = true;
    $("#f_talk").fadeTo(500, 0.4);
    $("#f_talk1").fadeTo(500, 0.4);
    UpdateDbData("noty",3);
  }
}


function f_rock() {
  if(flag_rock==true)
  {
    flag_rock = false;
    $("#f_rock").fadeTo(300, 1.0);
    $("#f_rock1").fadeTo(300, 1.0);
    UpdateDbData("flag_r",1);
  }
  else {
    flag_rock = true;
    $("#f_rock").fadeTo(300, 0.4);
    $("#f_rock1").fadeTo(300, 0.4);
    UpdateDbData("flag_r",0);
  }
}


//==================================== firebase functions =================================================

var firebaseConfig = {
  apiKey: "AIzaSyA3KOd6r7UboAp0fvtnvJSU9umfOXXgNDQ",
  authDomain: "apricotdata.firebaseapp.com",
  databaseURL: "https://apricotdata.firebaseio.com",
  projectId: "apricotdata",
  storageBucket: "apricotdata.appspot.com",
  messagingSenderId: "894906563277",
  appId: "1:894906563277:web:78cfcf05cfe971b2ca0784",
  measurementId: "G-WV774BB6JJ"
};
// Initialize Firebase
firebase.initializeApp(firebaseConfig);
firebase.analytics();


//==== if rock is on make rock highlight 
function checkSetDatabase(){
  var path = "User/"+id;
  //alert(path)
	var dbRef = firebase.database().ref(path);
  var startListening = function() {
     dbRef.on('value', function(snapshot) {
        var warehouse = snapshot.val();    // child id
        if(warehouse.flag_r == 1)
        {
          flag_rock = false;
          $("#f_rock").fadeTo(300, 1.0);
          $("#f_rock1").fadeTo(300, 1.0);
        }
        if(warehouse.flag_r == 0)
        {
          flag_rock = false;
          $("#f_rock").fadeTo(300, 0.4);
          $("#f_rock1").fadeTo(300, 0.4);
        }
		align(warehouse);
	  });
 }
startListening();
}

//====== write flags to firebase
function UpdateDbData(fild_name,fild_value)
{
   var updates = {};
   updates['/User/'+id+'/'+fild_name] =fild_value;
   firebase.database().ref().update(updates);
}