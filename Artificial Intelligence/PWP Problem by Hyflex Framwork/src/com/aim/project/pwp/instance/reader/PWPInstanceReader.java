package com.aim.project.pwp.instance.reader;


import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

import com.aim.project.pwp.interfaces.PWPInstanceInterface;
import com.aim.project.pwp.interfaces.PWPInstanceReaderInterface;


public class PWPInstanceReader implements PWPInstanceReaderInterface {

	@Override
	public PWPInstanceInterface readPWPInstance(Path path, Random random) {

		BufferedReader bfr;
		try {
			bfr = Files.newBufferedReader(path);
			String line;
			int numberOfLocation = 0;
			int [] Locations = {};
            while ((line = bfr.readLine()) != null) {
            	if (line == "NAME" || line == "COMMENT" || line == "POSTAL_OFFICE" || line == "WORKER_ADDRESS" || line == "POSTAL_ADDRESSES"){
            		break;
				}
            	numberOfLocation = numberOfLocation+1;
                System.out.println(line);
            }
            return (PWPInstanceInterface) bfr;
			// TODO read in the PWP instance and create and return a new 'PWPInstance'
			
		} catch (IOException e) {

			e.printStackTrace();
			return null;
		}
	}
}
