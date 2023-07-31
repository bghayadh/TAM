package com.aliat.alm.services;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import com.aliat.alm.services.ItemTest;

public class ItemParameters {
	
	List<Map<String, String>> dictParameter = new ArrayList<Map<String, String>>();
	List<Map<String, String>> dictParameterArea = new ArrayList<Map<String, String>>();
	List<Map<String, String>> dictParameterRegion = new ArrayList<Map<String, String>>();
	List<Map<String, String>> dictParameterTemp = new ArrayList<Map<String, String>>();
	List<Map<String, String>> dictParameterDel = new ArrayList<Map<String, String>>();
	List<Map<String, String>> dictParameterbarcode = new ArrayList<Map<String, String>>();
	List<Map<String, String>> dictParameteritemPartnum = new ArrayList<Map<String, String>>();
	List<Map<String, String>> dictParameternode = new ArrayList<Map<String, String>>();
	List<Map<String, String>> dictParametersite = new ArrayList<Map<String, String>>();
	List<Map<String, String>> dictParameterserialNumber = new ArrayList<Map<String, String>>();
	List<Map<String, String>> dictParameterFixedAssetRegistrySerialNumber = new ArrayList<Map<String, String>>();
	List<Map<String, String>> dictParameterTubes = new ArrayList<Map<String, String>>();
	List<Map<String, String>> dictParameterStrands = new ArrayList<Map<String, String>>();
	List<Map<String, String>> dictParameterTubesAux = new ArrayList<Map<String, String>>();
	List<Map<String, String>> dictParameterStrandsAux = new ArrayList<Map<String, String>>();
	List<Map<String, String>> dictParameterJunct = new ArrayList<Map<String, String>>();
	List<Map<String, String>> dictParameterLoadedDB = new ArrayList<Map<String, String>>();



	public List<Map<String, String>> getDictParameterStrands() {
		return dictParameterStrands;
	}


	public void setDictParameterStrands(List<Map<String, String>> dictParameterStrands) {
		this.dictParameterStrands = dictParameterStrands;
	}


	public List<Map<String, String>> getDictParameterTubes() {
		return dictParameterTubes;
	}

	public List<Map<String, String>> getdictParameterTubesAux() {
		return dictParameterTubesAux;
	}
	
	public List<Map<String, String>> getDictParameterStrandsAux() {
		return dictParameterStrandsAux;
	}


	public void setDictParameterTubes(List<Map<String, String>> dictParameterTubes) {
		this.dictParameterTubes = dictParameterTubes;
	}
	
	public void setdictParameterTubesAux(List<Map<String, String>> dictParameterTubesAux) {
		this.dictParameterTubesAux = dictParameterTubesAux;
	}
	
	public void setDictParameterStrandsAux(List<Map<String, String>> dictParameterStrandsAux) {
		this.dictParameterStrandsAux = dictParameterStrandsAux;
	}
	
	public List<Map<String, String>> getDictParameterFixedAssetRegistrySerialNumber() {
		return dictParameterFixedAssetRegistrySerialNumber;
	}


	public void setDictParameterFixedAssetRegistrySerialNumber(
			List<Map<String, String>> dictParameterFixedAssetRegistrySerialNumber) {
		this.dictParameterFixedAssetRegistrySerialNumber = dictParameterFixedAssetRegistrySerialNumber;
	}


	public List<Map<String, String>> getDictParameteritemPartnum() {
		return dictParameteritemPartnum;
	}


	public void setDictParameteritemPartnum(List<Map<String, String>> dictParameteritemPartnum) {
		this.dictParameteritemPartnum = dictParameteritemPartnum;
	}


	public List<Map<String, String>> getDictParameterbarcode() {
		return dictParameterbarcode;
	}


	public void setDictParameterbarcode(List<Map<String, String>> dictParameterbarcode) {
		this.dictParameterbarcode = dictParameterbarcode;
	}

	public List<Map<String, String>> getDictParameternode() {
		return dictParameternode;
	}
	
	public void setDictParameternode(List<Map<String, String>> dictParameternode) {
		this.dictParameternode = dictParameternode;
	}
	
	public List<Map<String, String>> getDictParametersite() {
		return dictParametersite;
	}
	
	public void setDictParametersite(List<Map<String, String>> dictParametersite) {
		this.dictParametersite = dictParametersite;
	}
	public List<Map<String, String>> getDictParameterserialNumber() {
		return dictParameterserialNumber;
	}


	public void setDictParameterserialNumber(List<Map<String, String>> dictParameterserialNumber) {
		this.dictParameterserialNumber = dictParameterserialNumber;
	}
	
	public List<Map<String, String>> getDictParameterArea() {
		return dictParameterArea;
	}


	public void setDictParameterArea(List<Map<String, String>> dictParameterArea) {
		this.dictParameterArea = dictParameterArea;
	}
	public List<Map<String, String>> getDictParameterRegion() {
		return dictParameterRegion;
	}


	public void setDictParameterRegion(List<Map<String, String>> dictParameterRegion) {
		this.dictParameterRegion = dictParameterRegion;
	}
	
	
		
	public List<Map<String, String>> getDictParameterTemp() {
		return dictParameterTemp;
	}
	
	public List<Map<String, String>> getDictParameterDel() {
		return dictParameterDel;
	}

	public void setDictParameterTemp(List<Map<String, String>> dictParameterTemp) {
		this.dictParameterTemp = dictParameterTemp;
	}
	
	public void setDictParameterDel(List<Map<String, String>> dictParameterDel) {
		this.dictParameterDel = dictParameterDel;
	}

	public List<Map<String, String>> getDictParameterJunct() {
		return dictParameterJunct;
	}


	public void setDictParameterJunct(List<Map<String, String>> dictParameterJunct) {
		this.dictParameterJunct = dictParameterJunct;
	}
	
	public List<Map<String, String>> getDictParameterLoadedDB() {
		return dictParameterLoadedDB;
	}


	public void setDictParameterLoadedDB(List<Map<String, String>> dictParameterLoadedDB) {
		this.dictParameterLoadedDB = dictParameterLoadedDB;
	}


	public ItemParameters() {
		super();
		// TODO Auto-generated constructor stub
	}


	public ItemParameters(List<Map<String, String>> dictParameter,List<Map<String, String>> dictParameterTemp, List<Map<String,String>> dictParameterDel,List<Map<String, String>> dictParameterArea,List<Map<String, String>> dictParameterRegion, List<Map<String, String>> dictParameterbarcode,List<Map<String, String>> dictParameteritemPartnum, List<Map<String, String>> dictParameternode, List<Map<String, String>> dictParametersite, List<Map<String, String>> dictParameterserialNumber, List<Map<String, String>> dictParameterFixedAssetRegistrySerialNumber, List<Map<String, String>> dictParameterTubes,List<Map<String, String>> dictParameterTubesAux,List<Map<String, String>> dictParameterStrandsAux, List<Map<String, String>> dictParameterStrands) {
		super();
		this.dictParameter = dictParameter;
		this.dictParameterArea = dictParameterArea;
		this.dictParameterRegion = dictParameterRegion;
		this.dictParameterbarcode = dictParameterbarcode;
		this.dictParameteritemPartnum = dictParameteritemPartnum;	
		this.dictParameternode = dictParameternode ;
		this.dictParametersite = dictParametersite ;
		this.dictParameterserialNumber = dictParameterserialNumber ;
		this.dictParameterFixedAssetRegistrySerialNumber = dictParameterFixedAssetRegistrySerialNumber;
		this.dictParameterStrands = dictParameterStrands;
		this.dictParameterTubes = dictParameterTubes;
		this.dictParameterTubesAux = dictParameterTubesAux;
		this.dictParameterStrandsAux = dictParameterStrandsAux;
		this.dictParameterTemp = dictParameterTemp;
		this.dictParameterDel = dictParameterDel;

	}


	public List<Map<String, String>> getDictParameter() {
		return dictParameter;
	}


	public void setDictParameter(List<Map<String, String>> dictParameter) {
		this.dictParameter = dictParameter;
	}

	

}
