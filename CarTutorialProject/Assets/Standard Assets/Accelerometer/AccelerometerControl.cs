using System;
using System.ComponentModel;
using System.Text;
using System.Net;
using System.Net.Sockets;
using System.Collections;
using UnityEngine;

namespace AssemblyCSharpfirstpass
{
	public class AccelerometerControl
	{
		private byte[] data;
		private UdpClient main_socket;
		private IPEndPoint main_ip_endpoint;
		private float gyrox = 0.0f;
		private float gyroy = 0.0f;
		private float gyroz = 0.0f;
		private float accelx = 0.0f;
		private float accely = 0.0f;
		private float accelz = 0.0f;
		//private bool use_accelerometer = false;
		private BackgroundWorker bwUDPServer = new BackgroundWorker();
		
		public AccelerometerControl ()
		{
			data = new byte[1024];
			main_ip_endpoint = new IPEndPoint(IPAddress.Any, 5555);
			main_socket = new UdpClient(main_ip_endpoint);
			bwUDPServer.RunWorkerAsync();
		}
		
		private void bwUDPServer_DoWork(object sender, DoWorkEventArgs e)
		{
		    while (true)
		    {
		        data = main_socket.Receive(ref main_ip_endpoint);
		        string[] data_in = Encoding.ASCII.GetString(data, 0, data.Length).Split('|');
		        if (data_in.Length == 6)
		        {
		            if (!float.TryParse(data_in[3], out accelx))
		                accelx = float.Parse(data_in[3].Replace('.', ','));
		            if (!float.TryParse(data_in[4], out accely))
		                accely = float.Parse(data_in[4].Replace('.', ','));
		            if (!float.TryParse(data_in[5], out accelz))
		                accelz = float.Parse(data_in[5].Replace('.', ','));
					
					
		            //Cursor.Position = new Point(Cursor.Position.X - (int)(accelx * 5.0f), 
		            //Cursor.Position.Y - (int)(accely * 5.0f));
		        }
		    }
		}
		
		public float getAccelX()
		{
			return accelx;
		}
		
		public float getAccelY()
		{
			return accely;
		}
		
		public float getAccelZ()
		{
			return accelz;
		}
		
		void Start()
		{
			
		}
	}
}

