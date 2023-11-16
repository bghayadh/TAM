package com.aliat.alm.models;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CLIENTS")
public class ClientListViewAPI {
               @Id
               @Column(name = "AGENT_NUMBER")
               private String agentNumber;
               
               @Column(name= "CLIENT_ID")
               private String clientid;
               
               private int vfrom;
               private int vto;
               private int val1;
               private String date;
               private String vsimregid;
               private String vcolname;
               private String vcolname1;
               private String val2;
               
               
			public ClientListViewAPI() {
				super();
				// TODO Auto-generated constructor stub
			}


			public ClientListViewAPI(String agentNumber, String clientid, int vfrom, int vto, int val1, String date,
					String vsimregid, String vcolname, String vcolname1, String val2) {
				super();
				this.agentNumber = agentNumber;
				this.clientid = clientid;
				this.vfrom = vfrom;
				this.vto = vto;
				this.val1 = val1;
				this.date = date;
				this.vsimregid = vsimregid;
				this.vcolname = vcolname;
				this.vcolname1 = vcolname1;
				this.val2 = val2;
			}


			public String getAgentNumber() {
				return agentNumber;
			}


			public void setAgentNumber(String agentNumber) {
				this.agentNumber = agentNumber;
			}


			public String getClientid() {
				return clientid;
			}


			public void setClientid(String clientid) {
				this.clientid = clientid;
			}


			public int getVfrom() {
				return vfrom;
			}


			public void setVfrom(int vfrom) {
				this.vfrom = vfrom;
			}


			public int getVto() {
				return vto;
			}


			public void setVto(int vto) {
				this.vto = vto;
			}


			public int getVal1() {
				return val1;
			}


			public void setVal1(int val1) {
				this.val1 = val1;
			}


			public String getDate() {
				return date;
			}


			public void setDate(String date) {
				this.date = date;
			}


			public String getVsimregid() {
				return vsimregid;
			}


			public void setVsimregid(String vsimregid) {
				this.vsimregid = vsimregid;
			}


			public String getVcolname() {
				return vcolname;
			}


			public void setVcolname(String vcolname) {
				this.vcolname = vcolname;
			}


			public String getVcolname1() {
				return vcolname1;
			}


			public void setVcolname1(String vcolname1) {
				this.vcolname1 = vcolname1;
			}


			public String getVal2() {
				return val2;
			}


			public void setVal2(String val2) {
				this.val2 = val2;
			}
			
			
               
               
               
               
               
               
}
