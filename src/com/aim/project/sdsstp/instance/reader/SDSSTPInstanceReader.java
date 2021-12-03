package com.aim.project.sdsstp.instance.reader;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Random;

import com.aim.project.sdsstp.SDSSTPObjectiveFunction;
import com.aim.project.sdsstp.instance.SDSSTPInstance;
import com.aim.project.sdsstp.instance.SDSSTPLocation;
import com.aim.project.sdsstp.interfaces.ObjectiveFunctionInterface;
import com.aim.project.sdsstp.interfaces.SDSSTPInstanceInterface;
import com.aim.project.sdsstp.interfaces.SDSSTPInstanceReaderInterface;

/**
 * 
 * @author Warren G. Jackson
 * @since 26/03/2021
 * 
 * Methods needing to be implemented:
 * - public SDSSTPInstanceInterface readSDSSTPInstance(Path path, Random random)
 */
public class SDSSTPInstanceReader implements SDSSTPInstanceReaderInterface {

	private static SDSSTPInstanceReader oInstance;
	
	private SDSSTPInstanceReader() {
		
	}
	
	public static SDSSTPInstanceReader getInstance() {
		
		if(oInstance == null) {
			
			oInstance = new SDSSTPInstanceReader();
		}
		
		return oInstance;
	}
	
	
	@Override
	public SDSSTPInstanceInterface readSDSSTPInstance(Path path, Random random) {
		
		// TODO
		File file;  
		ArrayList<String> content = new ArrayList<>();
		try {
			String SEP = FileSystems.getDefault().getSeparator();
			file = new File(System.getProperty("user.dir")+ SEP + path);
			if (file.isFile() && file.exists()) {
				FileInputStream input = new FileInputStream(file);
				InputStreamReader read = new InputStreamReader(input);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					content.add(lineTxt);
					//System.out.println(lineTxt);
				}
				read.close();
			} else {
				System.out.println(file);
			}
		} catch (Exception e) {
			System.out.println("Read file failed");
			e.printStackTrace();
		}
		
		String strInstanceName = content.get(1);
		int iNumberOfLandmarks = Integer.parseInt(content.get(3));
		
		String [] Office = content.get(12+iNumberOfLandmarks).split(" ");
		SDSSTPLocation oTourOffice = new SDSSTPLocation(Integer.parseInt(Office[0]), Integer.parseInt(Office[1]));
		
		SDSSTPLocation[] aoLandmarks = new SDSSTPLocation[iNumberOfLandmarks];
		for(int i = 0; i < iNumberOfLandmarks; i++) {
			String [] Landmark = content.get(14+iNumberOfLandmarks+i).split(" ");
			aoLandmarks[i] = new SDSSTPLocation(Integer.parseInt(Landmark[0]), Integer.parseInt(Landmark[1]));
			//System.out.println(aoLandmarks[i].getX()+""+aoLandmarks[i].getY());
		}
		
		int[][] aiTimeDistanceMatrix = new int[iNumberOfLandmarks][iNumberOfLandmarks];
		for(int i = 0; i < iNumberOfLandmarks; i++) {	
			String [] Matrix = content.get(5+i).split(" ");
			for (int j = 0; j < iNumberOfLandmarks; j++) {
				aiTimeDistanceMatrix[i][j] = Integer.parseInt(Matrix[j]);
				//System.out.print(aiTimeDistanceMatrix[i][j]);
			}
			//System.out.println("");
		}
		
		int[] aiTimeDistancesFromTourOffice = new int[iNumberOfLandmarks];
		String [] FromOffice = content.get(6+iNumberOfLandmarks).split(" ");
		for(int i = 0; i < iNumberOfLandmarks; i++) {		
			aiTimeDistancesFromTourOffice[i] = Integer.parseInt(FromOffice[i]);
			//System.out.println(aiTimeDistancesFromTourOffice[i]);
		}
		
		int[] aiTimeDistancesToTourOffice = new int[iNumberOfLandmarks];
		String [] ToOffice = content.get(8+iNumberOfLandmarks).split(" ");
		for(int i = 0; i < iNumberOfLandmarks; i++) {		
			aiTimeDistancesToTourOffice[i] = Integer.parseInt(ToOffice[i]);
			//System.out.println(aiTimeDistancesToTourOffice[i]);
		}
		
		int[] aiVisitingDurations = new int[iNumberOfLandmarks];
		String [] Visit = content.get(10+iNumberOfLandmarks).split(" ");
		for(int i = 0; i < iNumberOfLandmarks; i++) {		
			aiVisitingDurations[i] = Integer.parseInt(Visit[i]);
			//System.out.println(aiVisitingDurations[i]);
		}
		
		ObjectiveFunctionInterface oObjectiveFunction = new SDSSTPObjectiveFunction(aiTimeDistanceMatrix, aiTimeDistancesFromTourOffice,
				aiTimeDistancesToTourOffice, aiVisitingDurations);
		
		//System.out.println(strInstanceName);
		//System.out.println(iNumberOfLandmarks);
		//System.out.println(oTourOffice.getX());
		//System.out.println(oTourOffice.getY());
				
		return new SDSSTPInstance(strInstanceName, iNumberOfLandmarks, oTourOffice, aoLandmarks, random, oObjectiveFunction);
	}
}
