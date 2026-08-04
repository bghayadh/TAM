package com.aliat.alm.physLayer;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

public class physicalCommon {
	
	public physicalCommon() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public String[] findListId(List<?> ListOfObjects, String Target) throws JsonProcessingException {

		Object[] objectIdArray = new Object[ListOfObjects.size()];

		for (int i = 0; i < ListOfObjects.size(); i++) {
			if (Target == "FiberCable") {
				objectIdArray[i] = (String) ((Object[]) ListOfObjects.get(i))[4];
			} else {
				objectIdArray[i] = (String) ((Object[]) ListOfObjects.get(i))[0];
			}

		}
		String[] stringArray = Arrays.copyOf(objectIdArray, objectIdArray.length, String[].class);
		// System.out.println("stringArray "+mapper.writeValueAsString(stringArray));

		return stringArray;
	}
}
