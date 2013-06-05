#pragma strict

var guiSkin: GUISkin;
var nativeVerticalResolution = 1200;
var isPaused : boolean = false;

function Start () {

}


 
function Update()
{
 
     if(Input.GetKeyDown("escape") && !isPaused)
   {
      print("Paused");
      Time.timeScale = 0.0;
      isPaused = true;
   }
   else if(Input.GetKeyDown("escape") && isPaused)
   {
      print("Unpaused");
      Time.timeScale = 1.0;
      isPaused = false;    
   } 
}
 
function OnGUI ()
{
 

    // Set up gui skin
    GUI.skin = guiSkin;
          
     GUI.matrix = Matrix4x4.TRS (Vector3(0, 0, 0), Quaternion.identity, Vector3 (Screen.height / nativeVerticalResolution, Screen.height / nativeVerticalResolution, 1));

    if(isPaused)
    {
      // RenderSettings.fogDensity = 1;
      if(GUI.Button (Rect((Screen.width)/2,320,140,70), "Quit" ))
      {
         print("Quit!");
         Application.Quit();
      }
  	if(GUI.Button (Rect((Screen.width)/2,400,140,70), "Restart"))
      {
         
         print("Restart");
         Application.LoadLevel(0);
         Time.timeScale = 1.0;
         isPaused = false;
      }
  	
    if(GUI.Button (Rect((Screen.width)/2,480,140,70), "Continue"))
      {
         print("Continue");
         Time.timeScale = 1.0;
         isPaused = false;   
      }
   } 
 
 
}
 
 
@script AddComponentMenu ("GUI/Pause GUI")