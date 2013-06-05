#pragma strict
var count : int = 0;
var ColliderCar : Collider;


function Start () {

}

function Update () {



}

function OnCollisionEnter( hit : Collision ){
    if(hit.gameObject.name == ColliderCar){
    	count++;
    }
 }