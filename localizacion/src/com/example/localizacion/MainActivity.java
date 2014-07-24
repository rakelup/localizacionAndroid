package com.example.localizacion;

import java.util.List;

import android.app.Activity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


public class MainActivity extends Activity implements LocationListener {
	   private static final long TIEMPO_MIN = 10 * 1000 ; // 10 segundos
	   private static final long DISTANCIA_MIN = 5 ; // 5 metros
	   private static final String[] A = { "n/d", "preciso", "impreciso" };
	   private static final String[] P = { "n/d", "bajo", "medio","alto" };
	   private static final String[] E = { "fuera de servicio",
	                        "temporalmente no disponible ","disponible" };
	   private LocationManager manejador;
	   private String proveedor;
	   private TextView salida;
	   
	   public static final String TAG = "LOCATION";
	 
	   @Override
	   public void onCreate(Bundle savedInstanceState) {
		  
		   
	      super.onCreate(savedInstanceState);
	      Log.d(TAG,"EN ONCREATE");  
	      setContentView(R.layout.activity_main);
	      salida = (TextView) findViewById(R.id.salida);

	      manejador = (LocationManager) getSystemService(LOCATION_SERVICE);
	      log("Proveedores de localización: \n ");
	      muestraProveedores();
	      muestraMejorProveedor();
	      log("Comenzamos con la última localización conocida:");
	      Location localizacion = manejador.getLastKnownLocation(proveedor);
	      muestraLocaliz(localizacion);
	   }
	      
	   
	   
	      @Override protected void onResume() {
	             super.onResume();
	             // Cuando se reinicia la actividad se actualiza la ubicacion
	             manejador.requestLocationUpdates(proveedor, TIEMPO_MIN, DISTANCIA_MIN, this);
	             Log.d(TAG,"EN ONRESUME");  
	       }
	      
	      @Override protected void onPause() {
	            
	    	  Log.d(TAG,"EN ONpause");  
	    	  	super.onPause();
	            manejador.removeUpdates(this);
	       }
	      
	      public void muestraMejorProveedor()
	      {
	    	  Criteria criterio = new Criteria();
		      criterio.setCostAllowed(false);
		      criterio.setAltitudeRequired(false);
		      criterio.setAccuracy(Criteria.ACCURACY_FINE);
		      proveedor = manejador.getBestProvider(criterio, true);
		      log("Mejor proveedor: " + proveedor + "\n");
	    	  
	      }
	      
	      
	      
	      public void log(String cadena)
	      {
	    	  salida.append(cadena +"\n");
	      }
	      
	      public void muestraProveedores()
	      {
	    	  List <String> proveedores=manejador.getAllProviders();
	    	  for (String proveedor: proveedores)
	    		  muestraProveedor(proveedor);
	    	  
	    	  
	      }
	      
	      public void muestraProveedor(String proveedor)
	      {
	    	  LocationProvider info = manejador.getProvider(proveedor);
	    	  
	    	  Log.d(TAG,"EN muestraproveedor");  
	          log("LocationProvider[ "+"getName=" + info.getName()
	                    + ", isProviderEnabled=" 
	                    + manejador.isProviderEnabled(proveedor)+", getAccuracy="
	                    + A[Math.max(0, info.getAccuracy())]+ ", getPowerRequirement="
	                    + P[Math.max(0, info.getPowerRequirement())]
	                    +", hasMonetaryCost=" + info.hasMonetaryCost()
	                    + ", requiresCell=" + info.requiresCell()
	                    + ", requiresNetwork=" + info.requiresNetwork()
	                    + ", requiresSatellite=" + info.requiresSatellite()
	                    + ", supportsAltitude=" + info.supportsAltitude()
	                    + ", supportsBearing=" + info.supportsBearing()
	                    + ", supportsSpeed=" + info.supportsSpeed()+" ]\n");
	       }
	      
	      public void muestraLocaliz(Location localizacion)
	      {
	    	  
	    	  if (localizacion==null)
	    		  log("Localizacion desconocida\n");   		  
	    	 else
	    		 log(localizacion.toString()+"\n");   	
	    		 
	    			  
	      }



	   // Métodos de la interfaz LocationListener
	     
	      public void onLocationChanged(Location location) {
              log("Nueva localización: ");
             muestraLocaliz(location);
	      }
	 
	       public void onProviderDisabled(String proveedor) {
	             log("Proveedor deshabilitado: " + proveedor + "\n");
	       }
	 
	       public void onProviderEnabled(String proveedor) {
	             log("Proveedor habilitado: " + proveedor + "\n");
	       }
	 
	       public void onStatusChanged(String proveedor, int estado,
	             Bundle extras) {
	             log("Cambia estado proveedor: " + proveedor + ", estado="
	                           + E[Math.max(0,estado)] + ", extras=" + extras +"\n");
	       }
	    	  
}
 
	      
	 
